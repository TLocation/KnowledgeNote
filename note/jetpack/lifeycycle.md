## lifecycle是什么

 lifecycle是一个生命周期感知组件 它可以感知Fragment/Activity的生命周期 可以无侵入式的写出更优雅的代码，并且会防止内存泄漏，

## Lifecycle版本

```groovy
 implementation 'androidx.appcompat:appcompat:1.1.0'
 //编译时生成调用代码 避免反射 可选
 kapt "androidx.lifecycle:lifecycle-compiler:2.2.0"
 //可选
 implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
//支持协程 可选
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.2.0'
```



## 为什么要用

### lifecycle出来之前

```java
@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter.register();
		locationManager.oncreate(this);
		...
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.unregister();
		locationManager.onDestroy();
		...
	}
```



这样在实际生产环境中，代码会越来越臃肿，需要在每个的Activity中 **onCreate onDestroy**中调用相应的方法，代码不易维护。

Lifecycle可以感知组件的生命周期，并对注册组件的接收者来分发生命周期状态

## Lifecycle的用法

### 1. 构建Observer

```kotlin
//自定义类实现LifecycleObserver接口
class LifecycleOwnerImpl:LifecycleObserver {
   /**
	 * 在需要调用的方法上使用OnLifecycleEvent注解来告诉Lifecycle当前方法的事件类型
	 * Lifecycle支持的事件类型后续会以表格写出
	 */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
       debugLog("onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        debugLog("onStart")
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
       debugLog("onResume")
    }
}
```

### 2. 注册Observer

```kotlin
//在Activity/Fragment中注册observer 在组件生命周期发生变化时会回调相应的函数
 lifecycle.addObserver(LifecycleOwnerImpl())
```



### Lifecycle支持的生命周期类型

| Activity/Fragment的生命周期    | Lifecycle的Event事件 | Lifecycle的State |
| ------------------------------ | -------------------- | ---------------- |
| onCreate()/onActivityCreated() | ON_CREATE            | CREATED          |
| onStart()                      | ON_START             | STARTED          |
| onResume()                     | ON_RESUME            | RESUMED          |
| onPause()                      | ON_PAUSE             | STARTED          |
| onStop()                       | ON_STOP              | CREATED          |
| onDestroy()                    | ON_DESTROY           | DESTROYED        |

- Lifecycle.STATE.INITIALIZED是尚未收到组件生命周期回调时的初始状态
- Lifecycle.Event.ON_ANY 只要组件生命周期发生变化，就会回调
- Lifecycle的Event事件属于被动回调 State状态属于主动获取
- 由表格可以看出只有State在RESUME的时候组件才可以与用户进行交互
- Event事件的回调和组件是属于串行 所以在Event事件里面不建议做过多的耗时操作

## Lifecycle使用扩展

### 1.使用注解处理器提高性能

> Lifecycle 已经默认包含在AndroidX的库中

lifecycle默认是使用**反射**来调用Observer的方法的，这样会对性能造成影响，导致程序变慢。

可以导入Lifecycle的注解处理器来提高性能 在编译期间生成Adapter类来避免反射

```groovy
//在开头需要的依赖中已经给出
kapt "androidx.lifecycle:lifecycle-compiler:2.2.0"
```

编译工程后会根据工程里面包含的LifecycleObserver 生成**类名\_LifeCycleAdapter**的辅助类

lifecycle是如何处理反射和适配器的后面会在源码分析中给出

###  2.LifecycleObserver使用扩展

#### a.LifecycleEventObserver 

> 用于接受State事件回调 不可再注册Event事件

```kotlin
class LifecycleEventImpl:LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        debugLog("event=${event.name}")
    }
}
```

## 使用案例

实现计时器 当activity创建的时候初始化计时器 在activity处于前台时开启计时器 退到后台时停止计时器

[代码地址](https://gitee.com/AndroidGrowthNote/KnowledgeNote/tree/2020/q3/1/tianxiaolong/tianxiaolong/sample/WanAndroid)

```kotlin
/**
 *
 * @author tianxiaolong
 * time：2020/7/4 4:06 PM
 * description：
 * 模拟获取用户信息类
 * 这里假设有一个计时器 当activity在前台时开始计时 退到后台时停止
 */

object UserManager : LifecycleObserver {
    private const val DELAY_TIMER = 1000L
    var timer = 0
        private set

    val runnable = Runnable {
        timer += 1
        debugLog("current timer=$timer")
        startRunnable()
    }

    fun startRunnable() {
        handler.postDelayed(runnable, DELAY_TIMER)
    }

    val handlerThread = HandlerThread("UserManager")
    lateinit var handler: Handler

    fun register(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }
    
  //初始化计时器
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }
    //开启计时器
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        debugLog("start timer")

        handler.postDelayed(runnable, DELAY_TIMER)
    }

    //停止计时器  
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        debugLog("stop timer")
        handler.removeCallbacks(runnable)


    }

    //销毁计时器
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        handlerThread.quitSafely()
    }
}

class LifecycleActivity : JetpackBaseActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityLifycleBinding.inflate(LayoutInflater.from(this))
        setContentView(inflate.root)
        //注册计时器 不需要担心内存泄漏
        UserManager.register(this)

    }
```

