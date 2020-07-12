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

#### 从哪儿开始发事件？

Activity。
产生事件最先会交给Activity，再依次向下传递。

//Activity
public boolean dispatchTouchEvent(MotionEvent ev) {
  if (ev.getAction() == MotionEvent.ACTION_DOWN) {
    *//空实现*
    onUserInteraction();
  }
  if (getWindow().superDispatchTouchEvent(ev)) {
    return true;
  }
  return onTouchEvent(ev);
}

//DecorView

public boolean superDispatchTouchEvent(MotionEvent event) {

  return super.dispatchTouchEvent(event);
}

//PhoneWindow

@Override
public boolean superDispatchTouchEvent(MotionEvent event) {
  return mDecor.superDispatchTouchEvent(event);
}

Activity会将事件传递给Window，window又会向下继续传递。如果最终都没有View消费事件(superDispatchTouchEvent返回false)，则Activity会自己调用onTouchEvent()方法处理事件。

ViewGroup的dispatchTouchEvent()方法会先判断自己是否要拦截当前事件，是否拦截的作用在于，是自己处理事件，还是要将事件传递下去。即intercepted为true自己处理，为false则寻找子View向下传递。当然如果没有符合传递要求的子View，事件还是会由当前View自己处理。

ViewGroup，挑选传递事件的子View要符合两个条件:

**可见状态**
**事件的坐标在子View范围**

符合这两个条件,则调用dispatchTransformedTouchEvent()方法把事件传递给子View。

dispatchTransformedTouchEvent()方法

当子View为null时调用View的dispatchTouchEvent()传递事件，意味当前View自己处理事件。child不为null的情况下，则调用child的dispatchTouchEvent()把事件交给子View。

View和ViewGroup不同，View的dispatchTouchEvent()方法，意味将准备开始处理事件了。

如果我们给View设置了onTouchListener监听器，则优先会回调Listener的onTouch()方法。如果onTouch()方法返回了false,则还是会执行onTouchEvent()方法