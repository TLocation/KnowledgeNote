## 布局过程

#### 两个阶段：测量阶段和布局阶段



##### 测量阶段：从上到下递归地调用每个 View 或者 ViewGroup 的 measure() 方法，测量他们的尺寸并计算它们的位置

**View**的测量过程

1.测量阶段，measure() 方法被父 View 调用，在 measure() 中做一些准备和优 化工作后，调用 onMeasure() 来进行实际的自我测量。

2.View 在 onMeasure() 中会计算出自己的尺寸然后保存； 

**ViewGroup**的测量过程

<u>1.ViewGroup 在 onMeasure() 中会调用所有子 View 的measure() 让它们进行自我测量</u>

<u>2.并根据子 View 计算出的期望尺寸来计算 出它们的实际尺寸和位置然后保存</u>

<u>3.根据子 View 的尺寸和位置来计算出自己的尺寸然后保存</u>

##### 布局阶段：从上到下递归地调用每个 View 或者 ViewGroup 的 layout() 方法，把测得的它们的尺寸和位置赋值给它们。 

布局阶段，layout() 方法被父 View 调用，在 layout() 中它会保存父 View传进来的自己的位置和尺寸，并且调用 onLayout() 来进行实际的内部布局。

###### 由于没有子 View，所以 View 的 onLayout() 什么也不做。

##### ViewGroup 在 onLayout() 中会调用自己的所有子 View 的layout() 方法，把它们的尺寸和位置传给它们，让它们完成自我的内部布局

#### **布局过程自定义的方式** 

三类： 

1. 重写 onMeasure() 来修改已有的 View 的尺寸； 

2. 重写 onMeasure() 来全新定制自定义 View 的尺寸； 

3. 重写 onMeasure() 和 onLayout() 来全新定制自定义 ViewGroup 的内部布局。

第一类：

重写 onMeasure() 来**修改**已有的 View 的尺寸的具体做法：

1. 重写 onMeasure() 方法，并在里面调用 super.onMeasure() ，触发原有的自我测量； 

   2.在 super.onMeasure() 的下面用 getMeasuredWidth() 和getMeasuredHeight() 来获取到之前的测量结果，     并使用自己的算法，根据测量结果计算出新的结果

   3.调用 setMeasuredDimension() 来保存新的结果

第二类：

重写 onMeasure() 来**全新定制**自定义 View的尺寸

1. 重新 onMeasure() ，并计算出 View 的尺寸； 

2. 使用 resolveSize() 来让子 View 的计算结果符合父 View 的限制
3. 调用 setMeasuredDimension() 来保存新的结果

第三类：

**重写 onMeasure() 和 onLayout() 来全新定制自定义 ViewGroup 的内部布局。**

重写 onMeasure() 来计算内部布局

1. 调用每个子 View 的 measure() 来计算子 View 的尺寸

2. 计算子 View 的位置并保存子 View 的位置和尺寸

3. 计算自己的尺寸并用 setMeasuredDimension() 保存 

重写 onLayout() 来摆放子 View

   在 onLayout() 里调用每个子 View 的 layout() ，让它们保存自己的位置和尺寸。

**全新定制尺寸和修改尺寸的最重要区别** 

计算的同时，保证计算结果满足父 View 给出的的尺寸限制 

父 **View** 的尺寸限制 

![img](https://upload-images.jianshu.io/upload_images/944365-e631b96ea1906e34.png?imageMogr2/auto-orient/strip|imageView2/2/w/960/format/webp)

`MeasureSpec` 被封装在`View`类中的一个内部类里：`MeasureSpec`类

测量规格`（MeasureSpec）` = 测量模式`（mode）` + 测量大小`（size）`

![img](https://upload-images.jianshu.io/upload_images/944365-7d0f873cee3912bb.png?imageMogr2/auto-orient/strip|imageView2/2/w/380/format/webp)

MeasureSpec类 用1个变量封装了2个数据`（size，mode）`：**通过使用二进制**，将测量模式`（mode）` & 测量大小`(size）`打包成一个`int`值    **减少对象内存分配**

### MeasureSpec值的计算

根据**子View的布局参数（LayoutParams）和父容器的MeasureSpec值**计算得来

![img](https://upload-images.jianshu.io/upload_images/944365-d059b1afdeae0256.png?imageMogr2/auto-orient/strip|imageView2/2/w/470/format/webp)

总结规律

子view采用精确值：测量模式=EXACTLY  测量大小=自身的数值

子view采用match_parent：测量模式=父容器的测量模式   

​                                                测量大小= 父容器模式（EXACTLY）父容器的剩余空间

​                                                测量大小=父容器模式（ATMOST）不超过父容器的剩余空间

子view采用wrap_parent：测量模式=ATMOST  测量大小=不超过父容器的剩余空间

![img](https://upload-images.jianshu.io/upload_images/944365-6088d2d291bbae09.png?imageMogr2/auto-orient/strip|imageView2/2/w/660/format/webp)

# measure过程详解

`measure`过程 根据**View的类型**分为2种情况：

单一view：只测量自身

viewgroup：对viewgroup中的所有子view进行测量‘

## 单一View的measure过程

```dart
/**
  * 源码分析：measure（）
  * 定义：Measure过程的入口；属于View.java类 & final类型，即子类不能重写此方法
  * 作用：基本测量逻辑的判断
  **/ 

    public final void measure(int widthMeasureSpec, int heightMeasureSpec) {

        // 参数说明：View的宽 / 高测量规格

        ...

        int cacheIndex = (mPrivateFlags & PFLAG_FORCE_LAYOUT) == PFLAG_FORCE_LAYOUT ? -1 :
                mMeasureCache.indexOfKey(key);

        if (cacheIndex < 0 || sIgnoreMeasureCache) {
            
            onMeasure(widthMeasureSpec, heightMeasureSpec);
            // 计算视图大小 ->>分析1

        } else {
            ...
      
    }
```

**基本测量逻辑的判断调用onMeasure进行下一步测量**

```dart
/**
  * 分析1：onMeasure（）
  * 作用：a. 根据View宽/高的测量规格计算View的宽/高值：getDefaultSize()
  *      b. 存储测量后的View宽 / 高：setMeasuredDimension()
  **/ 
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
    // 参数说明：View的宽 / 高测量规格

    setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),  
                         getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));  
    // setMeasuredDimension() ：获得View宽/高的测量值 ->>分析2
    // 传入的参数通过getDefaultSize()获得 ->>分析3
}
```

作用：a. 根据View宽/高的测量规格计算View的宽/高值：getDefaultSize()

​            b. 存储测量后的View宽 / 高：setMeasuredDimension()

```dart
：getDefaultSize()
* 作用：根据View宽/高的测量规格计算View的宽/高值
```

`getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec)`中传入的默认大小是`getSuggestedMinimumWidth()`。

```csharp
protected int getSuggestedMinimumWidth() {
    return (mBackground == null) ? mMinWidth : max(mMinWidth,mBackground.getMinimumWidth());
}
```

若 `View` 无设置背景，那么`View`的宽度 = `mMinWidth`

若 `View`设置了背景，`View`的宽度为`mMinWidth`和`mBackground.getMinimumWidth()`中的最大值

```java
public int getMinimumWidth() {
    final int intrinsicWidth = getIntrinsicWidth();
    //返回背景图Drawable的原始宽度
    return intrinsicWidth > 0 ? intrinsicWidth :0 ;
}
```

```java
mBackground.getMinimumWidth()的大小 = 背景图Drawable的原始宽度
```

**最后 setMeasuredDimension：存储最后的子view的宽/高**

# ViewGroup的measure过程

在自定义`ViewGroup`中，关键在于：**根据需求复写`onMeasure()`从而实现你的子View测量逻辑**

```dart
/**
  * 根据自身的测量逻辑复写onMeasure（），分为3步
  * 1. 遍历所有子View & 测量：measureChildren（）
  * 2. 合并所有子View的尺寸大小,最终得到ViewGroup父视图的测量值（自身实现）
  * 3. 存储测量后View宽/高的值：调用setMeasuredDimension()  
  **/ 

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  

        // 定义存放测量后的View宽/高的变量
        int widthMeasure ;
        int heightMeasure ;

        // 1. 遍历所有子View & 测量(measureChildren（）)
        // ->> 分析1
        measureChildren(widthMeasureSpec, heightMeasureSpec)；

        // 2. 合并所有子View的尺寸大小，最终得到ViewGroup父视图的测量值
         void measureCarson{
             ... // 自身实现
         }

        // 3. 存储测量后View宽/高的值：调用setMeasuredDimension()  
        // 类似单一View的过程，此处不作过多描述
        setMeasuredDimension(widthMeasure,  heightMeasure);  
  }
```

1. 遍历所有子View & 测量(measureChildren（）)

2. 合并所有子View的尺寸大小，最终得到ViewGroup父视图的测量值

   3.存储测量后View宽/高的值：调用setMeasuredDimension()  

```dart
/**
  * 分析1：measureChildren()
  * 作用：遍历子View & 调用measureChild()进行下一步测量
  **/ 

    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        // 参数说明：父视图的测量规格（MeasureSpec）

                final int size = mChildrenCount;
                final View[] children = mChildren;

                // 遍历所有子view
                for (int i = 0; i < size; ++i) {
                    final View child = children[i];
                     // 调用measureChild()进行下一步的测量 ->>分析1
                    if ((child.mViewFlags & VISIBILITY_MASK) != GONE) {
                        measureChild(child, widthMeasureSpec, heightMeasureSpec);
                    }
                }
            }
```

   1.遍历所有子view

2. 调用measureChild()进行下一步的测量 

```dart
/**
  * 分析2：measureChild()
  * 作用：a. 计算单个子View的MeasureSpec
  *      b. 测量每个子View最后的宽 / 高：调用子View的measure()
  **/ 
  protected void measureChild(View child, int parentWidthMeasureSpec,
            int parentHeightMeasureSpec) {

        // 1. 获取子视图的布局参数
        final LayoutParams lp = child.getLayoutParams();

        // 2. 根据父视图的MeasureSpec & 布局参数LayoutParams，计算单个子View的MeasureSpec
        // getChildMeasureSpec() 请看上面第2节储备知识处
        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,// 获取 ChildView 的 widthMeasureSpec
                mPaddingLeft + mPaddingRight, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,// 获取 ChildView 的 heightMeasureSpec
                mPaddingTop + mPaddingBottom, lp.height);

        // 3. 将计算好的子View的MeasureSpec值传入measure()，进行最后的测量
        // 下面的流程即类似单一View的过程，此处不作过多描述
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }
```

1.计算单个子view的MeasureSpec getChildMeasureSpec（）

2.调用每个子view的measure()，进行最后的测量

**最后 setMeasuredDimension：存储最后的子view的宽/高**

# layout过程详解

`layout`过程根据**View的类型**分为2种情况：

单一view：仅仅算本身view的位置

viewgroup：计算自身的位置，计算确定子view在父容器的位置

### 单一View的layout过程

```java
/**
  * 源码分析：layout（）
  * 作用：确定View本身的位置，即设置View本身的四个顶点位置
  */ 
  public void layout(int l, int t, int r, int b) {  

    // 当前视图的四个顶点
    int oldL = mLeft;  
    int oldT = mTop;  
    int oldB = mBottom;  
    int oldR = mRight;  
      
    // 1. 确定View的位置：setFrame（） / setOpticalFrame（）
    // 即初始化四个顶点的值、判断当前View大小和位置是否发生了变化 & 返回 
    // ->>分析1、分析2
    boolean changed = isLayoutModeOptical(mParent) ?
            setOpticalFrame(l, t, r, b) : setFrame(l, t, r, b);

    // 2. 若视图的大小 & 位置发生变化
    // 会重新确定该View所有的子View在父容器的位置：onLayout（）
    if (changed || (mPrivateFlags & PFLAG_LAYOUT_REQUIRED) == PFLAG_LAYOUT_REQUIRED) {  

        onLayout(changed, l, t, r, b);  
        // 对于单一View的laytou过程：由于单一View是没有子View的，故onLayout（）是一个空实现->>分析3
        // 对于ViewGroup的laytou过程：由于确定位置与具体布局有关，所以onLayout（）在ViewGroup为1个抽象方法，需重写实现（后面会详细说）
  ...

} 
```

layout()计算自身view 的位置（调用setFrame（） / setOpticalFrame（））

onLayout(changed, l, t, r, b);   空实现

### ViewGroup的layout过程

1. 计算自身`ViewGroup`的位置：`layout（）`
2. 遍历子`View` & 确定自身子View在`ViewGroup`的位置（调用子`View` 的 `layout（）`）：`onLayout（）`

**此处需注意：****

一开始计算`ViewGroup`位置时，调用的是`ViewGroup`的`layout（）`和`onLayout()`；

当开始遍历子`View` & 计算子`View`位置时，调用的是子`View`的`layout（）`和`onLayout()`

```dart
/**
  * 分析3：onLayout（）
  * 作用：计算该ViewGroup包含所有的子View在父容器的位置（）
  * 注： 
  *      a. 定义为抽象方法，需重写，因：子View的确定位置与具体布局有关，所以onLayout（）在ViewGroup没有实现
  *      b. 在自定义ViewGroup时必须复写onLayout（）！！！！！
  *      c. 复写原理：遍历子View 、计算当前子View的四个位置值 & 确定自身子View的位置（调用子View layout（））
  */ 
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

     // 参数说明
     // changed 当前View的大小和位置改变了 
     // left 左部位置
     // top 顶部位置
     // right 右部位置
     // bottom 底部位置

     // 1. 遍历子View：循环所有子View
          for (int i=0; i<getChildCount(); i++) {
              View child = getChildAt(i);   

              // 2. 计算当前子View的四个位置值
                // 2.1 位置的计算逻辑
                ...// 需自己实现，也是自定义View的关键

                // 2.2 对计算后的位置值进行赋值
                int mLeft  = Left
                int mTop  = Top
                int mRight = Right
                int mBottom = Bottom

              // 3. 根据上述4个位置的计算值，设置子View的4个顶点：调用子view的layout() & 传递计算过的参数
              // 即确定了子View在父容器的位置
              child.layout(mLeft, mTop, mRight, mBottom);
              // 该过程类似于单一View的layout过程中的layout（）和onLayout（），此处不作过多描述
          }
      }
```

layout():完成父view 的位置

onLayout（）：确定子view在父容器的位置 （需复写调用子view的 layout）

子view layout（）：计算自身的位置

ziview onLayout（）：空实现