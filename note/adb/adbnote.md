### 常用命令

```
设置超时时间
adb shell settings put system screen_off_timeout 90000000
获取设备分辨率
adb shell wm size
获取屏幕密度
adb shell wm density
获取activity切换用时
adb logcat | grep Displayed
获取内存信息
adb shell dumpsys meminfo pkg
fps信息
adb shell dumpsys gfxinfo

adb logcat -G 2m
```

### apk 操作

```
清除apk信息 包含数据
adb shell pm clear pkg
卸载apk
adb uninstall pkg
卸载apk 保留数据
adb uninstall -k pkg

```

### 事件操作

```
输入文本信息
adb shell input text "insert%syour%stext%shere" %s 表示空格
点击屏幕
adb shell input tap x y
滑动屏幕
adb shell input swipe oldX oldY newX newY

```

