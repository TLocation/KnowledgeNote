### Http的工作方式

Eg 打开一个网页
     浏览器把地址转成一个请求给服务器 服务器响应把body返回给浏览器 浏览器使用渲染引擎把界面展示出来

url转换到http请求
http://www.baidu.com/path?page=1
http 协议类型 www.baidu.com 服务器地址 后面的路径path

### 请求体(Request)

请求方式 路径 请求版本
Header

GET /list HTTP/1.1
Host : www.baidu.com
body

![request](Http的工作方式.assets/request.png)


### 响应体
HTTP/1.1 200 OK
Header
body

![response](Http的工作方式.assets/response.png)

请求流程
![app请求图](Http的工作方式.assets/app请求图.png)

### Http的请求方法

#### 1.GET 
- 获取资源
- 只有**url**没有**body**

#### 2.POST
- 增加或修改资源
- 有body

#### 3.PUT

- 修改资源
- 有body
- 具有幂等性 多次同样的修改只生效一次

#### 4.DELETE

- 删除资源
- 没有body

#### 5.HEAD

- 获取资源
- 响应里面没有body

### HTTP的Body和HEADER

#### 1.Header中的Host作用

1. 拿到host通过Dimain Name System 查询IP地址
2. 到达ip地址服务器 服务器通过host分配到对应的主机上

#### 2.Content-Type 和 Content-Length（字节） 
​    body的类型和长度

##### 2.1Content-Type 代表body类型

1. text/html



