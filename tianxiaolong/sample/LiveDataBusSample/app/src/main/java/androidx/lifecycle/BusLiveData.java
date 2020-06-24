package androidx.lifecycle;


import com.sun.android.util.Logs;

import androidx.annotation.NonNull;

/**
 * @author tianxiaolong
 * time：2019/12/3 16:29
 * description：
 */
 class BusLiveData<T> extends LiveData<T> {

	private  final String key;

	public BusLiveData(String key) {
		super();
		this.key = key;
	}




	@Override
	public void removeObserver(@NonNull Observer<? super T> observer) {
		super.removeObserver(observer);
		removeLiveData();
	}

	@Override
	public void removeObservers(@NonNull LifecycleOwner owner) {
		super.removeObservers(owner);
		removeLiveData();
	}

	public int getCurrentVersion(){
		return getVersion();
	}

	@Override
	protected void onInactive() {
		super.onInactive();
		removeLiveData();
	}

	private void removeLiveData() {
		if(!hasObservers()){
			LiveDataBus.getDefault().removeLiveData(key);
			Logs.INSTANCE.I("onInactive");
		}
	}
}
