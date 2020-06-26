失败日志

```java
Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored
```



1. 更新版本至（更新时间2019/12/28）[参考网址](https://github.com/bumptech/glide)

```groovy
 implementation 'com.github.bumptech.glide:glide:4.10.0'
 annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'
 mavenCentral()
```

2. 或者配置AppModle

```java
@GlideModule
public final class MyAppGlideModule extends AppGlideModule {

}
```



