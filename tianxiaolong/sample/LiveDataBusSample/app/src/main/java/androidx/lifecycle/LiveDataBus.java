package androidx.lifecycle;

import android.os.Looper;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author tianxiaolong
 * time：2019/12/2 20:23
 * description：
 */
public class LiveDataBus {
	private static volatile LiveDataBus instalce;

	public static LiveDataBus getDefault() {
		if (instalce == null) {
			synchronized (LiveDataBus.class) {
				if (instalce == null) {
					instalce = new LiveDataBus();
				}
			}
		}
		return instalce;
	}

	private Map<String, BusLiveData<Object>> channelMap;


	private Map<Observer, ObserverWrapper> foreverMap;

	{
		foreverMap = new HashMap<>();
	}

	private BusLiveData<Object> getChannelLiveData(final String key) {
		if(channelMap == null){
			channelMap = new HashMap<>();
		}
		return channelMap.computeIfAbsent(key, new Function<String, BusLiveData<Object>>() {
			@Override
			public BusLiveData<Object> apply(String s) {
				return new BusLiveData<>(key);
			}
		});
	}

	public <T> void observerStick(String key, LifecycleOwner lifecycleOwner, Observer<T> observer) {
		checkNull(key, "observer key is null");
		BusLiveData<Object> channelStickLiveData = getChannelLiveData(key);
		ObserverWrapperLifecycle<T> wrapperLifecycle = new ObserverWrapperLifecycle<>(observer, channelStickLiveData, lifecycleOwner);
		wrapperLifecycle.openStick();
		channelStickLiveData.observe(lifecycleOwner, wrapperLifecycle);
	}


	public <T> void observer(String key, LifecycleOwner lifecycleOwner, Observer<T> observer) {
		checkNull(key, "observer key is null");
		BusLiveData<Object> objectBusLiveData = getChannelLiveData(key);
		objectBusLiveData.observe(lifecycleOwner, new ObserverWrapperLifecycle<>(observer, objectBusLiveData, lifecycleOwner));
	}


	public <T> void observerForever(String key, Observer<T> observer) {
		checkNull(key, "observer key is null");
		BusLiveData<Object> channelLiveData = getChannelLiveData(key);
		ObserverWrapper<T> observerWrapper = new ObserverWrapper<>(observer, channelLiveData);
		putObserver(observer, channelLiveData, observerWrapper);
		channelLiveData.observeForever(observerWrapper);
	}


	public <T> void observerForeverStick(String key, Observer<T> observer) {
		checkNull(key, "observer key is null");
		BusLiveData<Object> channelLiveData = getChannelLiveData(key);
		ObserverWrapper<T> observerWrapper = new ObserverWrapper<>(observer, channelLiveData);
		putObserver(observer, channelLiveData, observerWrapper);
		observerWrapper.openStick();
		channelLiveData.observeForever(observerWrapper);
	}

	private <T> void putObserver(Observer<T> observer, BusLiveData<Object> channelLiveData, ObserverWrapper<T> observerWrapper) {
		ObserverWrapper oldWrapper = foreverMap.put(observer, observerWrapper);
		if (oldWrapper != null) {
			channelLiveData.removeObserver(oldWrapper);
		}
	}


	public void unregistObserver(String key, Observer observer) {
		checkNull(key, "observer key is null");
		BusLiveData<Object> channelLiveData = getChannelLiveData(key);
		ObserverWrapper removeWrapper = foreverMap.remove(observer);
		if (removeWrapper != null) {
			channelLiveData.removeObserver(removeWrapper);
		}

	}

	private void checkNull(String checkMsg, String msg) {
		if (TextUtils.isEmpty(checkMsg)) {
			throw new NullPointerException(msg);
		}
	}

	void removeLiveData(String key) {
		if (channelMap != null) {
			channelMap.remove(key);
		}
	}


	public void postValue(String key, Object value) {
		BusLiveData<Object> channelLiveData = getChannelLiveData(key);
		if (isMainThread()) {
			channelLiveData.setValue(value);
		} else {
			channelLiveData.postValue(value);
		}
	}


	private boolean isMainThread() {
		return Looper.getMainLooper() == Looper.myLooper();
	}


}
