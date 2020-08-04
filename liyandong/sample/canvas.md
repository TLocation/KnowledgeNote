

# Canvas的基础使用

### 自定义绘制：提前创建好 `Paint` 对象，重写 `onDraw()`

```java
Paint paint = new Paint();

@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    // 绘制一个圆
    canvas.drawCircle(300, 300, 200, paint);
}
```

## 绘制调用Canvas.drawXXX() 系列方法

### Canvas.drawColor(@ColorInt int color) 颜色填充

在整个绘制区域统一涂上指定的颜色。

```java
drawColor(Color.BLACK);  // 纯黑
```

```java
drawColor(Color.parse("#88880000"); // 半透明红色
```

类似的还有

```java
canvas.drawRGB(100, 200, 100);
canvas.drawARGB(100, 100, 200, 100);
```

### drawCircle(float centerX, float centerY, float radius, Paint paint) 画圆

`centerX` `centerY` 是圆心的坐标，第三个参数 `radius` 是圆的半径，单位都是像素

第四个参数 `paint`  提供基本信息之外的所有风格信息，例如颜色、线条粗细等等

```java
canvas.drawCircle(300, 300, 200, paint);
```

### drawRect(float left, float top, float right, float bottom, Paint paint) 画矩形

float left, float top, float right, float bottom  是矩形四条边的坐标。

```java
canvas.drawRect(700, 100, 1100, 500, paint);
```

### drawPoint(float x, float y, Paint paint) 画点

`x` 和 `y` 是点的坐标。

```java
canvas.drawPoint(50, 50, paint);
```

### drawPoints(float[] pts, int offset, int count, Paint paint) / drawPoints(float[] pts, Paint paint) 画点（批量）

`drawPoint()` 的区别是可以画多个点。 `pts` 这个数组是点的坐标 两个组成一对  offset 跳过数组的前几个数开始

count 绘制几个点

```java
float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
// 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)
canvas.drawPoints(points, 2 /* 跳过两个数，即前两个 0 */,
          8 /* 一共绘制 8 个数（4 个点）*/, paint);
```

### drawOval(float left, float top, float right, float bottom, Paint paint) 画椭圆

float left, float top, float right, float bottom   椭圆的左、上、右、下四个边界点的坐标。

```java
canvas.drawOval(400, 50, 700, 200, paint);
```

### drawLine(float startX, float startY, float stopX, float stopY, Paint paint) 画线

float startX, float startY, float stopX, float stopY,  起点坐标  终点坐标

```java
canvas.drawLine(200, 200, 800, 500, paint);
```

### drawLines(float[] pts, int offset, int count, Paint paint) / drawLines(float[] pts, Paint paint) 画线（批量）

### drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint) 画圆角矩形

`left`, `top`, `right`, `bottom` 是四条边的坐标，`rx` 和 `ry` 是圆角的横向半径和纵向半径。

```java
canvas.drawRoundRect(100, 100, 500, 300, 50, 50, paint);
```

### drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter, Paint paint) 绘制弧形或扇形

`left`, `top`, `right`, `bottom` 描述的弧形所在的椭圆；startAngle 弧形的起始角度  `sweepAngle` 是弧形划过的角度  `useCenter` 表示是否连接到圆心，如果不连接到圆心，就是弧形，如果连接到圆心，就是扇形

```java
canvas.drawArc(200, 100, 800, 500, -110, 100, true, paint); // 绘制扇形
canvas.drawArc(200, 100, 800, 500, 20, 140, false, paint); // 绘制弧形
```

### drawBitmap(Bitmap bitmap, float left, float top, Paint paint) 画 Bitmap

`left` 和 `top` 是要把 `bitmap` 绘制到的位置坐标。

```java
drawBitmap(bitmap, 200, 100, paint);
```

### drawText(String text, float x, float y, Paint paint) 绘制文字

`text` 是用来绘制的字符串，`x` 和 `y` 是绘制的起点坐标。

```java
canvas.drawText(text, 200, 100, paint);
```

### drawPath(Path path, Paint paint) 画自定义图形

`Path` 可以描述直线、二次曲线、三次曲线、圆、椭圆、弧形、矩形、圆角矩形。

#### Path 方法第一类：直接描述路径。

该类方法还可以细分为两组：添加子图形和画线（直线或曲线）

##### `ddXxx()` ——添加子图形

**addCircle(float x, float y, float radius, Direction dir) 添加圆**

**addOval(float left, float top, float right, float bottom, Direction dir) / addOval(RectF oval, Direction dir) 添加椭圆**

**addRect(float left, float top, float right, float bottom, Direction dir) / addRect(RectF rect, Direction dir) 添加矩形**

.

.

##### 第二组：`xxxTo()` ——画线（直线或曲线）

**lineTo(float x, float y) / rLineTo(float x, float y) 画直线**

**quadTo(float x1, float y1, float x2, float y2) / rQuadTo(float dx1, float dy1, float dx2, float dy2) 画二次贝塞尔曲线**  

`以当前位置为起点，x1`, `y1` 和 `x2`, `y2` 则分别是控制点和终点的坐标。

**cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) / rCubicTo(float x1, float y1, float x2, float y2, float x3, float y3) 画三次贝塞尔曲线**

**moveTo(float x, float y) / rMoveTo(float x, float y) 移动到目标位置**

第二组还有两个特殊的方法： `arcTo()` 和 `addArc()`。它们也是用来画线的，但并不使用当前位置作为弧线的起点。

**arcTo** 和 `Canvas.drawArc()` 比起来，少了一个参数 `useCenter`，而多了一个参数 `forceMoveTo` 。

`forceMoveTo` 参数的意思是，绘制是要「抬一下笔移动过去」，还是「直接拖着笔过去」，区别在于是否留下移动的痕迹。

```java
path.lineTo(100, 100);
path.arcTo(100, 100, 300, 300, -90, 90, true); // 强制移动到弧形起点（无痕迹）
```

![img](https://wx4.sinaimg.cn/large/006tNc79ly1fig7yl6oc1j30ah07j0so.jpg)

```java
path.lineTo(100, 100);
path.arcTo(100, 100, 300, 300, -90, 90, false); // 直接连线连到弧形起点（有痕迹）
```

![img](https://wx3.sinaimg.cn/large/006tNc79ly1fig7z1kr1ij309h06xq2v.jpg)

**addArc** 一个弧形的方法。 上一个也是 区别：`addArc()` 只是一个直接使用了 `forceMoveTo = true` 的简化版 `arcTo()` 。

**close() 封闭当前子图形**

作用是把当前的子图形封闭，即由当前位置向当前子图形的起点绘制一条直线。

```java
path.moveTo(100, 100);
path.lineTo(200, 100);
path.lineTo(150, 150);
path.close(); // 使用 close() 封闭子图形。等价于 path.lineTo(100, 100)
```

![img](https://wx2.sinaimg.cn/large/006tNc79ly1fig7zqj7llj304n03aglh.jpg)

