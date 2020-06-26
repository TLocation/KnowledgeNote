### 原因

[Android9行为变更](https://developer.android.google.cn/about/versions/pie/android-9.0-changes-28?hl=zh_cn)

> 在Android9上面 安卓默认启动了网络传输层安全协议 (TLS)
>
> 导致请求网络数据失败

### 适配

#### 方案一

1.在res/xml下面创建文件 [参考网址](https://developer.android.google.cn/training/articles/security-config.html?hl=zh_cn)

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
	<base-config cleartextTrafficPermitted="true" />
</network-security-config>
```

2.在manifest文件中配置

```xml
<application
		android:networkSecurityConfig="@xml/network_security_config"/>
```

#### 方案二

```xml
<application
 android:usesCleartextTraffic="true"
    />
```

