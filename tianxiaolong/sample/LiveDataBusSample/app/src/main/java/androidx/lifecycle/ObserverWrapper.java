package androidx.lifecycle;


/**
 * @author tianxiaolong
 * time：2019/12/3 16:26
 * description：
 */
 class ObserverWrapper<T>
		implements Observer<Object> {

	private Observer<T> wrapper;

	BusLiveData<Object> liveData;

	private int mCurrentVersion;

	private boolean stick;

	public ObserverWrapper(Observer<T> wrapper, BusLiveData<Object> liveData) {
		this.wrapper = wrapper;
		this.liveData = liveData;
		mCurrentVersion = this.liveData.getCurrentVersion();
	}


	@Override
	public void onChanged(Object t) {
		if (isStick()) {
			wrapper.onChanged((T) t);
		} else {
			if (mCurrentVersion != liveData.getCurrentVersion()) {
				wrapper.onChanged((T) t);
			}
		}

	}

	 boolean isStick() {
		return stick;
	}

	void setStick(boolean stick) {
		this.stick = stick;
	}

	void openStick(){
		this.stick = true;
	}


}
