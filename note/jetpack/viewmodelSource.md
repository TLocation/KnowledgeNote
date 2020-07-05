## ViewModel

**ViewModel**是一个负责管理和存储与界面相关的数据的类
ViewModel类保存的数据在界面发生旋转（配置改变）后台进程被杀后数据依然存在

如果一个界面需要从网络数据获取一个列表 那么网络请求是需要等待的，一般使用异步回调处理

同时 如果用户的界面发生旋转，那么在界面重新onCreate的时候是不应该再进行网络请求的
而这一切使用普通的Activty做的话会异常繁琐 ，屏幕旋转后需要在[onSaveInstanceState()](https://developer.android.com/reference/android/app/Activity#onSaveInstanceState(android.os.Bundle))中存储数据 在onCreate的时候，
再对其进行恢复数据，而且，此方法只能存储少量的数据 序列化的数据



### 1.实现ViewModel

```kotlin
class OrdinaryViewModel :ViewModel() {
    private var isStart  = false
     val numberLiveData = MutableLiveData<Int>()

    fun add(){
        val oldValue = numberLiveData.value?:0
        numberLiveData.value = oldValue + 1
    }

    fun reduce(){
        val oldValue = numberLiveData.value?:0
        numberLiveData.value = oldValue - 1
    }
    //在viewmodel销毁时调用 在此方法内做内存 数据回收
    override fun onCleared() {
        super.onCleared()
    }
}
```

### 2.在Activity/Fragment获取ViewModel

```kotlin

//activity中获取ViewModel
private  val ordViewModle by viewModels<SaveStateViewModle>()

//在fragment中获取viewmodle
val modle by viewModels<SaveStateViewModle>()
 
//在Fragment中获取宿主Activity的ViewModel 用于多个Fragment数据共享
val m2 by activityViewModels<SaveStateViewModle>()
```

### ViewModel中获取**Application**

- 继承AndroidViewModle

```kotlin
class AndroidSmapleViewModele(application: Application):AndroidViewModel(application){
  
}
//获取方法相同
```



### ViewModel保存状态（后台杀死恢复数据）

```
class SaveStateViewModle(private  val savedStateHandle: SavedStateHandle) :
  ViewModel()
  
 class SaveStateViewModle(application: Application,private  val savedStateHandle: SavedStateHandle) :
  AndroidViewModel(application) 
```

- 继承自AndroidViewModel还需要保存数据的 构造函数必须是Application在前，SavedStateHandle在后

- **SavedStateHandle** 只可以存储少量数据 不建议存储大数据 可以存储LiveData

- **Application**可以开启服务 ViewModel不可以持有Lifecycle Activity/Fragment实例

### ViewModel 自定义工厂类

```kotlin
class CustomFactory(private val application: Application, val name:String):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AndroidSmapleViewModele::class.java)) return  AndroidSmapleViewModele(application,name)  as T
        return super.create(modelClass)
    }
}

  private  val androidViewModle by  viewModels<AndroidSmapleViewModele>(factoryProducer = { CustomFactory(application,"test") })

```

- 继承NewInstanceFactory类 实现create函数
-  也可以继承其他Factory类进行扩展


