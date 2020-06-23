package androidx.lifecycle;

/**
 * @author tianxiaolong
 * time：2019/12/3 19:22
 * description：
 */
 class ObserverWrapperLifecycle<T> extends ObserverWrapper<T> implements  LifecycleObserver{

	private final LifecycleOwner lifecycleOwner;

	public ObserverWrapperLifecycle(Observer<T> wrapper, BusLiveData<Object> liveData, LifecycleOwner lifecycleOwner) {
		super(wrapper, liveData);
		this.lifecycleOwner = lifecycleOwner;
		this.lifecycleOwner.getLifecycle().addObserver(this);
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	public void onDestroy(){
		liveData.removeObserver(this);
	}


}
