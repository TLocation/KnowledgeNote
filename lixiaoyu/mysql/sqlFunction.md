## 变量

### 系统变量

```
说明：变量由系统提供，不是用户自定义，属于服务器层面。(比如用户个隔离级别...等等)

全局变量：
	语法：
		1.查看所有的系统变量
			show global|session variables;
		2.查看满足条件的部分变量
			show global|session variables like '%char%';  -- '%char%' 模糊查询 char某字段.
		3.查看指定的某个系统变量的值
			select @@系统变量名;
		4.为某个系统变量赋值
			set global|session 系统变量名 = 值；
		    
			set @@global|session 系统变量 = 值；

会话变量：
作用域：仅仅针对于 当前会话(连接)有效

	
	1.查看所有的变量
		show varibales;
		show session variables;
	2.查看部分的会话变量
		show variables like '%char%';
		show session variables like '%char%';
	3.查询某个指定的会话变量
		select  @@tx_isolation;
		select  @@session.tx_isolation;
	4.为某个会话赋值
		set @@session.tx_isolation = 'read-uncommitted';
		
		set session tx_isolation = 'read-uncommitted'';


```

### 自定义变量

```
说明：由用户自定义

应用在任何地方 ，函数存储过程里面 后者是 外面。

使用步骤：
	
	
	
	用户变量：针对于当前会话（连接）有效，同于会话变量
		1.声明并初始化
			set @用户变量名 = 值；或者
			set @用户变量名：=值；或者
			select @用户变量名：=值；
		2.更新用户变量的值
			1.set / select
				set @用户变量名 = 值；或者
				set @用户变量名：=值；或者
				select @用户变量名：=值；
			2.select into
				select 字段 into 变量名
				from  表;
		3.使用(查看用户变量的值)
			select @用户变量名；
	示例：
		声明: set @m = 1; set @n = 2;
		赋值：set @sum = @m + @n;
		查看：select @sum; 
		结果：@sum = 3;
	
	
	
	局部变量：
	
	作用域：仅仅在定义它的函数或者是存过过程中 的第一句话
	
	1.声明：
		declare 变量名 类型；
		declare 变量名 类型 default 值；
	2.赋值：
		1.set / select
				set 局部变量名 = 值；或者
				set 局部变量名：=值；或者
				select 局部变量名：=值；
		2.select into
				select 字段 into 变量名
				from  表;
	3.使用
		select 局部变量名；
	
	
	
	
	
	
	


```