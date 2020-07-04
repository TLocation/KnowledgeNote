

# Paint Api的使用总结

## 1.颜色

### 1.1 设置基本颜色

##### setColor(int color)

```java
paint.setColor(Color.parseColor("#009688"));
canvas.drawRect(30, 30, 230, 180, paint);
```

##### setARGB(int a, int r, int g, int b)

```java
paint.setARGB(100, 255, 0, 0);
canvas.drawRect(0, 0, 200, 200, paint);
```

#### setShader(Shader shader) 设置 Shader  <u>**（着色器）**</u>

使用：并不直接用 `Shader` 这个类，而是用它的几个子类

##### LinearGradient 线性渐变

设置两个点和两种颜色 两个点作为端点，使用两种颜色的渐变来绘制颜色

`LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1, Shader.TileMode tile)` 。

参数：
`x0` `y0` `x1` `y1`：渐变的两个端点的位置
`color0` `color1` 是端点的颜色

```
tile`：端点范围之外的着色规则，类型是 `TileMode`。`TileMode` 一共有 3 个值可选： `CLAMP`, `MIRROR` 和 `REPEAT 
CLAMP 会在端点之外延续端点处的颜色
MIRROR 是镜像模式；
REPEAT 是重复模式
```

```java
Shader shader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"),
        Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
paint.setShader(shader);
     
...
     
canvas.drawCircle(300, 300, 200, paint);
```

![img](https://wx3.sinaimg.cn/large/52eb2279ly1fig6dq7wudj206l06875e.jpg)

##### RadialGradient 辐射渐变

从中心向周围辐射状的渐变
`RadialGradient(float centerX, float centerY, float radius, int centerColor, int edgeColor, TileMode tileMode)`。

`centerX` `centerY`：辐射中心的坐标
`radius`：辐射半径
`centerColor`：辐射中心的颜色
`edgeColor`：辐射边缘的颜色
`tileMode`：辐射范围之外的着色模式。

```java
Shader shader = new RadialGradient(300, 300, 200, Color.parseColor("#E91E63"),
        Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
paint.setShader(shader);
     
...
     
canvas.drawCircle(300, 300, 200, paint);
```

![img](https://wx3.sinaimg.cn/large/52eb2279ly1fig6ewf1o5j206d066q4a.jpg)

##### SweepGradient 扫描渐变

```
SweepGradient(float cx, float cy, int color0, int color1)
```

`cx` `cy` ：扫描的中心
`color0`：扫描的起始颜色
`color1`：扫描的终止颜色

```java
Shader shader = new SweepGradient(300, 300, Color.parseColor("#E91E63"),
        Color.parseColor("#2196F3"));
paint.setShader(shader);
     
...
     
canvas.drawCircle(300, 300, 200, paint);
```

![img](https://wx3.sinaimg.cn/large/52eb2279ly1fig6fmbemdj206u061my4.jpg)

##### BitmapShader

用 `Bitmap` 来着色

BitmapShader(Bitmap bitmap, Shader.TileMode tileX, Shader.TileMode tileY)

`bitmap`：用来做模板的 `Bitmap` 对象
`tileX`：横向的 `TileMode`
`tileY`：纵向的 `TileMode`。

```java
Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
paint.setShader(shader);
     
...
     
canvas.drawCircle(300, 300, 200, paint);
```

##### ComposeShader 混合着色器

两个 `Shader` 一起使用。

```
ComposeShader(Shader shaderA, Shader shaderB, PorterDuff.Mode mode)
```


`shaderA`, `shaderB`：两个相继使用的 `Shader`

`mode`: 两个 `Shader` 的叠加模式，

### 1.2 setColorFilter(ColorFilter colorFilter)

设置颜色过滤。

#### LightingColorFilter

模拟简单的光照效果的。

LightingColorFilter(int mul, int add)

`mul` 和 `add` 都是和颜色值格式相同的 int 值

`mul` 用来和目标像素相乘，`add` 用来和目标像素相加

```java
ColorFilter lightingColorFilter = new LightingColorFilter(0x00ffff, 0x000000);
paint.setColorFilter(lightingColorFilter);
```

#### PorterDuffColorFilter

使用一个指定的颜色和一种指定的 `PorterDuff.Mode` 来与绘制对象进行合成

PorterDuffColorFilter(int color, PorterDuff.Mode mode)

```
color` 参数是指定的颜色， `mode` 参数是指定的 `Mode
```

#### ColorMatrixColorFilter

使用一个 `ColorMatrix` 4x5 的矩阵 来对颜色进行处理

### 1.3 setXfermode(Xfermode xfermode)

Xfermode

设置绘制内容和view中已有内容的混合计算方式

创建 `Xfermode` 的时候其实是创建的它的子类 `PorterDuffXfermode`

```java
Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

...

canvas.drawBitmap(rectBitmap, 0, 0, paint); // 画方
paint.setXfermode(xfermode); // 设置 Xfermode
canvas.drawBitmap(circleBitmap, 0, 0, paint); // 画圆
paint.setXfermode(null); // 用完及时清除 Xfermode
```

#### Xfermode 注意事项

##### 使用离屏缓冲（Off-screen Buffer）

使用离屏缓冲，把要绘制的内容单独绘制在缓冲层   Canvas.saveLayer()

## 2.效果

### 2.1 setAntiAlias (boolean aa) 设置抗锯齿

抗锯齿默认是关闭的，如果需要抗锯齿，需要显式需要打开。

```java
Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
```

构造方法的参数里加一个 `ANTI_ALIAS_FLAG` 的 flag

就可以在初始化时候开启抗锯齿

### 2.2 setStyle(Paint.Style style)

```
FILL 模式，填充
```

```
STROKE 模式，画线
```

```
 FILL_AND_STROKE 模式，填充 + 画线
```

### 2.3线条形状

#### setStrokeWidth(float width) 线条宽度

#### setStrokeCap(Paint.Cap cap)   线头的形状

`线头形状：BUTT` 平头、`ROUND` 圆头、`SQUARE` 方头。默认为 `BUTT`。

#### setStrokeJoin(Paint.Join join) 设置拐角的形状

```
MITER` 尖角、 `BEVEL` 平角和 `ROUND` 圆角。默认为 `MITER
```

#### setStrokeMiter(float miter) 设置 `MITER` 型拐角的延长线的最大值

![img](https://wx4.sinaimg.cn/large/006tNc79ly1fig7bdt8wxj30eg0673yl.jpg)

### 2.4 色彩优化

#### setDither(boolean dither)  设置图像的抖动。

#### setFilterBitmap(boolean filter)  设置是否使用双线性过滤来绘制 `Bitmap` 

### 2.5setPathEffect(PathEffect effect)

用 `PathEffect` 来给图形的轮廓设置效果

#### CornerPathEffect  把所有拐角变成圆角。

```java
PathEffect pathEffect = new CornerPathEffect(20);
paint.setPathEffect(pathEffect);

...

canvas.drawPath(path, paint);
```

####  DiscretePathEffect  把线条进行随机的偏离

```java
PathEffect pathEffect = new DiscretePathEffect(20, 5);
paint.setPathEffect(pathEffect);

...

canvas.drawPath(path, paint);
```

20是用来拼接的每个线段的长度， 5 是偏离量

#### DashPathEffect  用虚线来绘制线条。

```java
PathEffect pathEffect = new DashPathEffect(new float[]{20, 10, 5, 10}, 0);
paint.setPathEffect(pathEffect);

...

canvas.drawPath(path, paint);
```

#### PathDashPathEffect  使用一个 `Path` 来绘制「虚线」

#### SumPathEffect  两种 `PathEffect` 分别对目标进行绘制

#### ComposePathEffect   

先对目标 `Path` 使用一个 `PathEffect`，然后再对这个改变后的 `Path` 使用另一个 `PathEffect`

```java
PathEffect dashEffect = new DashPathEffect(new float[]{20, 10}, 0);
PathEffect discreteEffect = new DiscretePathEffect(20, 5); 
pathEffect = new ComposePathEffect(dashEffect, discreteEffect);

...

canvas.drawPath(path, paint);
```

### 2.6 setShadowLayer   绘制层下面加一层阴影。

### 2.7 setMaskFilter(MaskFilter maskfilter)   绘制层上方的附加效果。

#### BlurMaskFilter  模糊效果的 

#### EmbossMaskFilter  浮雕效果

`Paint` 有些设置是文字绘制相关的  文字大小 文字间隔 文字效果