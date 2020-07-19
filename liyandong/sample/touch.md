# 自定义  触摸反馈

自定义触摸反馈的关键：

重写 `onTouchEvent()`，在里面写上你的触摸反馈算法，并返回 `true`（关键是 `ACTION_DOWN` 事件时返回 `true`）。

如果是会发生触摸冲突的 `ViewGroup`，还需要重写 `onInterceptTouchEvent()`，在事件流开始时返回 `false`，并在确认接管事件流时返回一次 `true`，以实现对事件的拦截。

当子 View 临时需要阻止父 View 拦截事件流时，可以调用父 View 的 `requestDisallowInterceptTouchEvent()` ，通知父 View 在当前事件流中不再尝试通过 `onInterceptTouchEvent()` 来拦截。

Android将触摸事件统一封装成MontionEvent类，以Down事件开始，Up事件结束，中间可能会产生其实事件(例如Move)，组成一个完整的事件系列。

**从手触摸屏幕开始所产生一系列MotionEvent事件，将事件传递到具体的某一个View的过程就叫做事件分发。**

事件分发又有三个重要的方法：

##### boolean dispatchTouchEvent(MotionEvent e)

如果事件能够传递给当前View，此方法一定会被调用，意味着当前View接受到事件。

##### boolean onInterceptTouchEvent()

在dispatchTouchEvent方法中调用，用来判断是否要拦截当前事件。基本上不拦截事件。

##### boolean onTouchEvent()

在dispatchTouchEvent方法中调用，用来处理点击事件。返回ture则意味事件被消费。

**点击事件（`Touch`事件）**

定义
当用户触摸屏幕时（`View` 或 `ViewGroup`派生的控件），将产生点击事件（`Touch`事件）

事件类型 ： MotionEvent.ACTION_DOWN     MotionEvent.ACTION_UP   MotionEvent.ACTION_MOVE  MotionEvent.ACTION_CANCEL

具体动作   ： 按下View（所有事件的开始） 抬起View（与DOWN对应）   滑动View   结束事件（非人为原因）

### 事件分发的本质

**将点击事件（MotionEvent）传递到某个具体的`View` & 处理的整个过程**

### 事件在哪些对象之间进行传递？

**Activity、ViewGroup、View**

![img](https://upload-images.jianshu.io/upload_images/944365-02c588300f6ad741.png?imageMogr2/auto-orient/strip|imageView2/2/w/590/format/webp)

### 事件分发的顺序

即 事件传递的顺序：`Activity` -> `ViewGroup` -> `View`

### 事件分发过程由哪些方法协作完成？

**dispatchTouchEvent() 、onInterceptTouchEvent()和onTouchEvent()**

![img](https://upload-images.jianshu.io/upload_images/944365-7c6642f518ffa3d2.png?imageMogr2/auto-orient/strip|imageView2/2/w/675/format/webp)

### Activity的事件分发机制

当一个点击事件发生时，事件最先传到`Activity`的`dispatchTouchEvent()`进行事件分发