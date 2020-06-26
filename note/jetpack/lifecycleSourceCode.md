# Lifecycle源码分析

lifecycle源码版本

```groovy
 implementation 'androidx.appcompat:appcompat:1.1.0'
```

### 1.Lifecycle的获取

简单的获取Lifecycle

```java
getLifecycle().addObserver(new SampleObserver();
```

点进去发现**getLifecycle**是在**ComponentActivity**中并且实现了LifecycleOwner接口

```java
public class ComponentActivity extends androidx.core.app.ComponentActivity implements
        LifecycleOwner{
  //#1
  private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
  
   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ···
        //#2  
        ReportFragment.injectIfNeededIn(this);
        ···
    }
  
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }       
}
```

在**#1** 处构建了LifecycleRegistry对象 这个是负责分发组件状态的

ComponentActivity中有用的就这几行代码那么Lifecycle是如何获取Activity的状态并且分发的呢？

重点在**#2**处Lifecycle使用了和**Glide**相同的做法，使用Fragment来分发状态这样的好处就是可以复用，即**ReportFragment**

ReportFragment是怎么做的呢 点进去看

```java
public class ReportFragment extends Fragment {
    private static final String REPORT_FRAGMENT_TAG = "androidx.lifecycle"
            + ".LifecycleDispatcher.report_fragment_tag";
   //#1 把Fragment加入到Activity中
   public static void injectIfNeededIn(Activity activity) {
        android.app.FragmentManager manager = activity.getFragmentManager();
        if (manager.findFragmentByTag(REPORT_FRAGMENT_TAG) == null) {
          //加入了一个空的Fragment
            manager.beginTransaction().add(new ReportFragment(), REPORT_FRAGMENT_TAG).commit();
            // Hopefully, we are the first to make a transaction.
            manager.executePendingTransactions();
        }
   }
  
   @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //#2 分发状态
        dispatch(Lifecycle.Event.ON_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        dispatchStart(mProcessListener);
        dispatch(Lifecycle.Event.ON_START);
    }

    ···

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispatch(Lifecycle.Event.ON_DESTROY);
         ···
    }

    private void dispatch(Lifecycle.Event event) {
        Activity activity = getActivity();
      //已经过时忽略
        if (activity instanceof LifecycleRegistryOwner) {
            ((LifecycleRegistryOwner) 		       activity).getLifecycle().handleLifecycleEvent(event);
            return;
        }

        if (activity instanceof LifecycleOwner) {
            Lifecycle lifecycle = ((LifecycleOwner) activity).getLifecycle();
            if (lifecycle instanceof LifecycleRegistry) {
              //分发生命周期
                ((LifecycleRegistry) lifecycle).handleLifecycleEvent(event);
            }
        }
    }
}
```

**#1** 在ComponentActivity中调用了ReportFragment#injectIfNeededIn方法把Fragment加入到了Activity中 用Fragment来感知Activity的生命周期

**#2**在Fragment生命周期发生变化时调用dispatch方法来分发生命周期 在里面调用了**LifecycleRegistry**的**handleLifecycleEvent**方法

现在如何感知Activity的生命周期已经清楚了，那么最终是怎么分发到**LifecycleObserve**的呢？

### 2.Lifecycle分发Observe

在上面我们可以看到最后实际是调用了**LifecycleRegistry**#**handleLifecycleEvent**方法 点进去

```java
public void handleLifecycleEvent(@NonNull Lifecycle.Event event) {
       // 这里根据Event状态来获取State状态
        State next = getStateAfter(event);
       //分发状态
        moveToState(next);
    }

private void moveToState(State next) {
        ···    
        mState = next;
       //如果正在分发状态或者没有注册Observer则终止分发
        if (mHandlingEvent || mAddingObserverCounter != 0) {
          ···
        }
        mHandlingEvent = true;
  			//重点
        sync();
        mHandlingEvent = false;
    }

private boolean isSynced() {
        if (mObserverMap.size() == 0) {
            return true;
        }
        //#1 这里再次判断是否在分发状态 并且Observer的状态是否和要分发的状态一致
        State eldestObserverState = mObserverMap.eldest().getValue().mState;
        State newestObserverState = mObserverMap.newest().getValue().mState;
        return eldestObserverState == newestObserverState && mState == newestObserverState;
    }

  private void sync() {
        LifecycleOwner lifecycleOwner = mLifecycleOwner.get();
        ···
        while (!isSynced()) {
           ···
            
          // 获取最新的Observer的状态 当前状态在Observer状态前面 
            if (mState.compareTo(mObserverMap.eldest().getValue().mState) < 0) {
                backwardPass(lifecycleOwner);
            }
            Entry<LifecycleObserver, ObserverWithState> newest = mObserverMap.newest();
          //最新的Observer状态在Observer状态后面
            if (!mNewEventOccurred && newest != null
                    && mState.compareTo(newest.getValue().mState) > 0) {
                forwardPass(lifecycleOwner);
            }
        }
       ···
    }

 private void forwardPass(LifecycleOwner lifecycleOwner) {
        Iterator<Entry<LifecycleObserver, ObserverWithState>> ascendingIterator =
                mObserverMap.iteratorWithAdditions();
        while (ascendingIterator.hasNext() && !mNewEventOccurred) {
            Entry<LifecycleObserver, ObserverWithState> entry = ascendingIterator.next();
            //#3 
            ObserverWithState observer = entry.getValue();
            while ((observer.mState.compareTo(mState) < 0 && !mNewEventOccurred
                    && mObserverMap.contains(entry.getKey()))) {
                pushParentState(observer.mState);
                 // #2 获取到每个observer分发状态
                observer.dispatchEvent(lifecycleOwner, upEvent(observer.mState));
                popParentState();
            }
        }
    }
static State getStateAfter(Event event) {
        switch (event) {
            case ON_CREATE:
            case ON_STOP:
                return CREATED;
            case ON_START:
            case ON_PAUSE:
                return STARTED;
            case ON_RESUME:
                return RESUMED;
            case ON_DESTROY:
                return DESTROYED;
            case ON_ANY:
                break;
        }
     ···
}
```

调用handleLifecycleEvent方法后会根据当前的Event事件来获取到最新的State然后根据最新的State和Observer的State做比较来进行状态的分发

**#1** FastSafeIterableMap SafeIterableMap 是jetpack新加的一个链表结构模仿HashMap 适用于小量的Map结构
     支持在遍历中增删元素 是线程不安全的 [源码分析](https://blog.csdn.net/llew2011/article/details/85222413)

**#2** 这里会根据最新的State来获取最新的Event进行分发

**#3** 这里从map集合里面拿到的是一个ObserverWithState 那么ObserverWithState是什么呢

### 3. ObserverWithState

ObserverWithState是LifecycleRegistry里面的一个静态内部类

```java

    static class ObserverWithState {
        //State 状态
        State mState;
        //#1
        LifecycleEventObserver mLifecycleObserver;

        ObserverWithState(LifecycleObserver observer, State initialState) {
            mLifecycleObserver = Lifecycling.lifecycleEventObserver(observer);
            mState = initialState;
        }

        void dispatchEvent(LifecycleOwner owner, Event event) {
            State newState = getStateAfter(event);
            mState = min(mState, newState);
            mLifecycleObserver.onStateChanged(owner, event);
            mState = newState;
        }
    }
```

ObserverWithState 包含两个参数State LifecycleEventObserver

**#1** **LifecycleEventObserver** 是继承自**LifecycleObserver**的接口 声明了**onStateChanged**()函数

在调用dispatchEvent的时候间接调用了**LifecycleEventObserver**的**onStateChanged**函数

那么我们自己**LifecycleObserver**在哪呢 ObserverWithState 是在哪里实例化的呢

通过追踪代码 我们发现

在LifecycleRegistry#**addObserver**中实例化了**ObserverWithState**对象

```java
 @Override
    public void addObserver(@NonNull LifecycleObserver observer) {
        State iniObserverWithStatetialState = mState == DESTROYED ? DESTROYED : INITIALIZED;
      //构造ObserverWithState
        ObserverWithState statefulObserver = new ObserverWithState(observer, initialState);
      //这里查看jetpack新加的两个链表
        ObserverWithState previous = mObserverMap.putIfAbsent(observer, statefulObserver);

        if (previous != null) {
            return;
        }
        ···

        ···
        mAddingObserverCounter--;
    }
```



在ObserverWithState的构造方法中调用了

> mLifecycleObserver = Lifecycling.lifecycleEventObserver(observer);

追踪代码

重点来了 注意

```java
 @NonNull
    static LifecycleEventObserver lifecycleEventObserver(Object object) {
        boolean isLifecycleEventObserver = object instanceof LifecycleEventObserver;
        //包安全的类 不用关注
        boolean isFullLifecycleObserver = object instanceof FullLifecycleObserver;
        
        ···
        // 如果Observer 实现的是LifecycleEventObserver 则不会处理注解信息直接返回
        if (isLifecycleEventObserver) {
            return (LifecycleEventObserver) object;
        }

        final Class<?> klass = object.getClass();
      //获取类型 是否包含注解处理器
        int type = getObserverConstructorType(klass);
      
        if (type == GENERATED_CALLBACK) {
          //这里是包含注解处理器 返回SingleGeneratedAdapterObserver 或者CompositeGeneratedAdaptersObserver
            List<Constructor<? extends GeneratedAdapter>> constructors =
                    sClassToAdapters.get(klass);
            if (constructors.size() == 1) {
                GeneratedAdapter generatedAdapter = createGeneratedAdapter(
                        constructors.get(0), object);
                return new SingleGeneratedAdapterObserver(generatedAdapter);
            }
            GeneratedAdapter[] adapters = new GeneratedAdapter[constructors.size()];
            for (int i = 0; i < constructors.size(); i++) {
                adapters[i] = createGeneratedAdapter(constructors.get(i), object);
            }
            return new CompositeGeneratedAdaptersObserver(adapters);
        }
      //通过反射调用方法
        return new ReflectiveGenericLifecycleObserver(object);
    }


// 这里会通过Observer的“类名__LifecycleAdapter” 去包里寻找可以找到说面有注解处理器
  @Nullable
    private static Constructor<? extends GeneratedAdapter> generatedConstructor(Class<?> klass) {
        try {
            Package aPackage = klass.getPackage();
            String name = klass.getCanonicalName();
            final String fullPackage = aPackage != null ? aPackage.getName() : "";
            final String adapterName = getAdapterName(fullPackage.isEmpty() ? name :
                    name.substring(fullPackage.length() + 1));

            @SuppressWarnings("unchecked") final Class<? extends GeneratedAdapter> aClass =
                    (Class<? extends GeneratedAdapter>) Class.forName(
                            fullPackage.isEmpty() ? adapterName : fullPackage + "." + adapterName);
            Constructor<? extends GeneratedAdapter> constructor =
                    aClass.getDeclaredConstructor(klass);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (NoSuchMethodException e) {
            // this should not happen
            throw new RuntimeException(e);
        }
    }
```

流程大致如下

1. 判断该Observer是否是LifecycleEventObserver 是的话返回本身
2. 判断是否包含注解处理器 查找是否包含“类名__LifecycleAdapter”的类 包含并且有OnLifecycleEvent注解则返回SingleGeneratedAdapterObserver/CompositeGeneratedAdaptersObserver
3. 如果以上提交都不满足就通过反射调用回调方法

查看SingleGeneratedAdapterObserver

```java
class SingleGeneratedAdapterObserver implements LifecycleEventObserver {

    private final GeneratedAdapter mGeneratedAdapter;

    SingleGeneratedAdapterObserver(GeneratedAdapter generatedAdapter) {
        mGeneratedAdapter = generatedAdapter;
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        mGeneratedAdapter.callMethods(source, event, false, null);
        mGeneratedAdapter.callMethods(source, event, true, null);
    }
}
```

这里就脉络就清楚了通过ObserverWithState#dispatchEvent方法最后调用的实际是**SingleGeneratedAdapterObserver**里面的**onStateChanged**方法。在**SingleGeneratedAdapterObserver**里面调用了Adapter的**callMethods**方法 关于注解处理器生成的adapter请自行分析



**通过反射调用回调函数**

```java
class ReflectiveGenericLifecycleObserver implements LifecycleEventObserver {
    private final Object mWrapped;
    private final CallbackInfo mInfo;

    ReflectiveGenericLifecycleObserver(Object wrapped) {
        mWrapped = wrapped;
        mInfo = ClassesInfoCache.sInstance.getInfo(mWrapped.getClass());
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Event event) {
        mInfo.invokeCallbacks(source, event, mWrapped);
    }
}
```

追踪代码查看 **ClassesInfoCache.sInstance.getInfo(mWrapped.getClass())**;

```java
// 先从map里拿 拿不到通过createInfo函数扫描类里面的方法
CallbackInfo getInfo(Class klass) {
        CallbackInfo existing = mCallbackMap.get(klass);
        if (existing != null) {
            return existing;
        }
        existing = createInfo(klass, null);
        return existing;
    }

 private CallbackInfo createInfo(Class klass, @Nullable Method[] declaredMethods) {
        Class superclass = klass.getSuperclass();
        Map<MethodReference, Lifecycle.Event> handlerToEvent = new HashMap<>();
        ··· 获取父类里面的method

        ··· 获取接口里面的method

        Method[] methods = declaredMethods != null ? declaredMethods : getDeclaredMethods(klass);
        boolean hasLifecycleMethods = false;
        for (Method method : methods) {
          //获取当前方法是否有OnLifecycleEvent注解
            OnLifecycleEvent annotation = method.getAnnotation(OnLifecycleEvent.class);
            if (annotation == null) {
                continue;
            }
            hasLifecycleMethods = true;
            Class<?>[] params = method.getParameterTypes();
          //没有参数
            int callType = CALL_TYPE_NO_ARG;
          //有参数的情况下第一个必须是LifecycleOwner
            if (params.length > 0) {
                callType = CALL_TYPE_PROVIDER;
                if (!params[0].isAssignableFrom(LifecycleOwner.class)) {
                    throw new IllegalArgumentException(
                            "invalid parameter type. Must be one and instanceof LifecycleOwner");
                }
            }
            Lifecycle.Event event = annotation.value();
            //有两个参数的情况下第二个参数类型必须是Lifecycle.Event.ANY
            if (params.length > 1) {
                callType = CALL_TYPE_PROVIDER_WITH_EVENT;
                if (!params[1].isAssignableFrom(Lifecycle.Event.class)) {
                    throw new IllegalArgumentException(
                            "invalid parameter type. second arg must be an event");
                }
                if (event != Lifecycle.Event.ON_ANY) {
                    throw new IllegalArgumentException(
                            "Second arg is supported only for ON_ANY value");
                }
            }
          //如果大于两个参数 直接抛出异常
            if (params.length > 2) {
                throw new IllegalArgumentException("cannot have more than 2 params");
            }
            MethodReference methodReference = new MethodReference(callType, method);
            verifyAndPutHandler(handlerToEvent, methodReference, event, klass);
        }
        CallbackInfo info = new CallbackInfo(handlerToEvent);
        mCallbackMap.put(klass, info);
        mHasLifecycleMethods.put(klass, hasLifecycleMethods);
        return info;
    }
    
```



### 总结

1. Lifecycle是在Activity里面放置一个空的Fragment来监听生命周期变化
2. 当**addObserver**的时候最后实际传入的是一个包装好的**ObserverWithState**对象 然后调用**onStateChanged**方法来分发状态
3. 使用处理器来提高性能 避免反射造成的性能消耗