## LiveData

liveData是一个可以被观察的数据存储类 与普通的可观察数据存储类（RxJava/EventBus）不同
它具有生命周期感知能力，在组件（Fragment Activity Service）处于活跃的状态下才会发送消息 ,
处于不活跃的组件将不会收到消息

当观察者（组件）处于**start、resume**的状态，LiveData会认为组件处于活跃状态。

当组件的生命周期状态变为**onDestroy** 状态时，便移除观察者，这样，在Activity、Fragment、Service中
可以任意的观察LiveData而不需要担心内存泄漏，仅仅适用于**observe**函数

google原文

> [`LiveData`](https://developer.android.com/reference/androidx/lifecycle/LiveData) 是一种可观察的数据存储器类。与常规的可观察类不同，LiveData 具有生命周期感知能力，意指它遵循其他应用组件（如 Activity、Fragment 或 Service）的生命周期。这种感知能力可确保 LiveData 仅更新处于活跃生命周期状态的应用组件观察者。
>
> **注意**：如需将 LiveData 组件导入您的 Android 项目，请参阅[向项目添加组件](https://developer.android.com/topic/libraries/architecture/adding-components#lifecycle)。
>
> 如果观察者（由 [`Observer`](https://developer.android.com/reference/androidx/lifecycle/Observer) 类表示）的生命周期处于 [`STARTED`](https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State#STARTED) 或 [`RESUMED`](https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State#RESUMED) 状态，则 LiveData 会认为该观察者处于活跃状态。LiveData 只会将更新通知给活跃的观察者。为观察 [`LiveData`](https://developer.android.com/reference/androidx/lifecycle/LiveData) 对象而注册的非活跃观察者不会收到更改通知。
>
> 您可以注册与实现 [`LifecycleOwner`](https://developer.android.com/reference/androidx/lifecycle/LifecycleOwner) 接口的对象配对的观察者。有了这种关系，当相应的 [`Lifecycle`](https://developer.android.com/reference/androidx/lifecycle/Lifecycle) 对象的状态变为 [`DESTROYED`](https://developer.android.com/reference/androidx/lifecycle/Lifecycle.State#DESTROYED) 时，便可移除此观察者。 这对于 Activity 和 Fragment 特别有用，因为它们可以放心地观察 [`LiveData`](https://developer.android.com/reference/androidx/lifecycle/LiveData) 对象而不必担心泄露（当 Activity 和 Fragment 的生命周期被销毁时，系统会立即退订它们）

### 使用LiveData的优势

**确保界面符合数据状态**

LiveData 遵循观察者模式。当生命周期状态发生变化时，LiveData 会通知 [`Observer`](https://developer.android.com/reference/androidx/lifecycle/Observer) 对象。您可以整合代码以在这些 `Observer` 对象中更新界面。观察者可以在每次发生更改时更新界面，而不是在每次应用数据发生更改时更新界面。

**不会发生内存泄漏**

观察者会绑定到 [`Lifecycle`](https://developer.android.com/reference/androidx/lifecycle/Lifecycle) 对象，并在其关联的生命周期遭到销毁后进行自我清理。

**不会因 Activity 停止而导致崩溃**

如果观察者的生命周期处于非活跃状态（如返回栈中的 Activity），则它不会接收任何 LiveData 事件。

**不再需要手动处理生命周期**

界面组件只是观察相关数据，不会停止或恢复观察。LiveData 将自动管理所有这些操作，因为它在观察时可以感知相关的生命周期状态变化。

**数据始终保持最新状态**

如果生命周期变为非活跃状态，它会在再次变为活跃状态时接收最新的数据。例如，曾经在后台的 Activity 会在返回前台后立即接收最新的数据。

**适当的配置更改**

如果由于配置更改（如设备旋转）而重新创建了 Activity 或 Fragment，它会立即接收最新的可用数据。

**共享资源(参考UserDataManager或者后续ViewModle)**

您可以使用单一实例模式扩展 [`LiveData`](https://developer.android.com/reference/androidx/lifecycle/LiveData) 对象以封装系统服务，以便在应用中共享它们。`LiveData` 对象连接到系统服务一次，然后需要相应资源的任何观察者只需观察 `LiveData` 对象。

### LiveData的使用

1. 创建 `LiveData` 实例以存储某种类型的数据。这通常在 [`ViewModel`](https://developer.android.com/reference/androidx/lifecycle/ViewModel) 类中完成。

2. 创建可定义 [`onChanged()`](https://developer.android.com/reference/androidx/lifecycle/Observer#onChanged(T)) 方法的 [`Observer`](https://developer.android.com/reference/androidx/lifecycle/Observer) 对象，该方法可以控制当 `LiveData` 对象存储的数据更改时会回调该对象。通常情况下，您可以在界面控制器（如 Activity 或 Fragment）中创建 `Observer` 对象。

3. 使用 [`observe()`](https://developer.android.com/reference/androidx/lifecycle/LiveData#observe(android.arch.lifecycle.LifecycleOwner, android.arch.lifecycle.Observer)) 方法将 `Observer` 对象附加到 `LiveData` 对象。`observe()` 方法会采用 [`LifecycleOwner`](https://developer.android.com/reference/androidx/lifecycle/LifecycleOwner) 对象。这样会使 `Observer` 对象订阅 `LiveData` 对象，以使其收到有关更改的通知。通常情况下，您可以在界面控制器（如 Activity 或 Fragment）中附加 `Observer` 对象。

   **注意**：您可以使用 [`observeForever(Observer)`](https://developer.android.com/reference/androidx/lifecycle/LiveData#observeForever(android.arch.lifecycle.Observer)) 方法来注册未关联 [`LifecycleOwner`](https://developer.android.com/reference/androidx/lifecycle/LifecycleOwner) 对象的观察者。在这种情况下，观察者会被视为始终处于活跃状态，因此它始终会收到关于修改的通知。您可以通过调用 [`removeObserver(Observer)`](https://developer.android.com/reference/androidx/lifecycle/LiveData#removeObserver(android.arch.lifecycle.Observer)) 方法来移除这些观察者。

当您更新存储在 `LiveData` 对象中的值时，它会触发所有已注册的观察者（只要附加的 `LifecycleOwner` 处于活跃状态）。

LiveData 允许界面控制器观察者订阅更新。当 `LiveData` 对象存储的数据发生更改时，界面会自动更新以做出响应。

#### 1.创建LiveData对象

```kotlin
//创建LiveData 使用子类MutableLiveData 
val liveData  = MutableLiveData<String>()
```

- LiveData 对象不建议在Activity/Fragment中创建 这样当activity fragment重启后这些数据依然存在 而不会二次创建
- Activity/Fragment应该只负责UI状态 而不应该负责数据存储相关
- 代码移植性 数据存储在ViewModle中 可以多个Activity Fragment共用 详情参考ViewModle文章

#### 2.监听LiveData

```kotlin
class LiveDataExtendActivity : JetpackBaseActivity() {
  //获取viewModle 详情查看后面ViewModel
 private val viewmodel by viewModels<LiveDataExtendViewModel>()

  //声明Observer
 private val userObserver = Observer<UserData> {
        binding.extendLivedataName.text = "姓名:${it.name}"
        binding.extendLivedataAge.text = "年龄:${it.age}"
 }
    
   override fun onCreate(savedInstanceState: Bundle?) {
     //在onCreate里面添加监听器 不需要取消监听 得益于Lifecycle在组件销毁时会自动取消监听
     viewmodel.userDataManager.observe(this, userObserver)
   } 
}
```

#### 更新LiveData对象

```kotlin
 binding.fragment1SendBtn.setOnClickListener {
            /**
             * 查看LiveData setValue postValue
             * setValue 主线程调用
             * postValue 子线程调用
             * 下面时postValue主要源码 通过这个函数调用到
             * ArchTaskExecutor.getInstance().postToMainThread
             */
            ( activity as LiveDataActivity).liveData.value = binding.fragmentEdittext.text.toString()
            binding.fragmentEdittext.text = null
        }
```

- LiveData没有公开函数更新数据 需要使用子类MutableLiveData来更新LiveData
- 在主线程调用setValue(value:T?)来更新数据
- 在工作线程使用postValue()来更新数据 注意postValue会先把数据放到主线程队列 然后更新 多次同时PostValue只会生效一次

### LiveData注意事项

- 使用LiveData时 确保Observer函数只会调用一次 不要多次调用
- **observeForever**使用这个函数后 观察者会永远处于活跃状态 必须手动调用**removeObserver**来取消注册
- LiveData默认为粘性事件 当观察者第一次处于活跃状态时 或者数据已经发生变化时 观察者重新回到活跃状态时，就会收到更新

### LiveData扩展

1. **onActive** 当LiveData具有活跃观察者时 会被调用 即活跃者>0
2. **onInactive** 当LiveData从活跃变为不活跃时调用 即活跃者从>0变为0

```kotlin
/**
 *
 * @author tianxiaolong
 * time：2020/7/4 6:42 PM
 * description：
 * 扩展LiveData 实现用户数据子增长 当有人观察时 数据开始变化 当没有观察时停止
 */
private const val  DELAY_TIME = 1000L

class UserDataManager private constructor():LiveData<UserData>() {

    private val handler by  lazy { Handler() }
    private val userData by lazy { UserData("make",12) }
    private  val runnable = Runnable {
        userData.age+=1
        super.setValue(userData)
        start()
    }

    private fun  start(){
        handler.postDelayed(runnable, DELAY_TIME)
    }

    private fun stop(){
        handler.removeCallbacks(runnable)
    }


    override fun setValue(value: UserData?) {
      throw  IllegalStateException("UserDataManager not support setValue")
    }

    override fun postValue(value: UserData?) {
      throw  IllegalStateException("UserDataManager not support postValue")
    }

    override fun onActive() {
        super.onActive()
        start()
        debugLog("onActive")
    }

    override fun onInactive() {
        super.onInactive()
        debugLog("onInactive")
        stop()
    }

    companion object{
        private  lateinit var  instance:UserDataManager
       fun INSTANCE():UserDataManager {
           instance =  if(::instance.isInitialized) instance else UserDataManager()
           return instance
       }
    }

}
```

这里设计了一个懒惰的用户信息管理类

当有人关注时 数据才开始更新 当没有人关注时 就停止数据更新

### LiveData转换

#### map

如果你希望在拿到数据之前对数据进行转换可以使用map函数

```kotlin
//当用户信息发生改变时 获取用户的年龄 转化成合适的年龄状态 
val userAge = Transformations.map(userDataManager) {
        when (it.age) {
            in 0..7 -> "小孩"
            in 8..18 -> "青少年"
            in 19..30 -> "奋斗青年"
            in 31..50 -> "养育孩子中"
            in 51..80 -> "养老"
            else -> "已入土"
        }
    }
```

#### switchMap
> 您可以使用转换方法在观察者的生命周期内传送信息。除非观察者正在观察返回的 LiveData 对象，否则不会计算转换。因为转换是以延迟的方式计算，所以与生命周期相关的行为会隐式传递下去，而不需要额外的显式调用或依赖项。

这是google原文 switchMap可以延迟的获取数据 但是必须返回LiveData对象 

这里对SwitchMap只有比较浅的理解  最大的用处为可以当监听的LiveData发生变化时可以用LiveData的属性去网络/后台去获取其他信息

下方是 当用户信息发生变化时就去获取新的用户ID

```kotlin
private fun getUserId(): LiveData<Int> {
    val data = MutableLiveData<Int>()
    Thread(Runnable {
       //模拟从网咯获取
        Thread.sleep(100)
        data.postValue(Random.nextInt())
    }).start()
    return data
}
 val userId = Transformations.switchMap(userDataManager) {
        getUserId()
 }
```

