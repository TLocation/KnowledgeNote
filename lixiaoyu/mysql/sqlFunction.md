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



## 存储过程和函数

```
存储过程和函数就类似于java中的方法。
好处：
	1.提高代码的重用
	2.简化操作
```



### 存储过程

> 含义:一组预先编译好的sql语句的集合。
>
> ```
> 1.提高代码的重用性。
> 2.简化操作
> 3.减少了编译次数并且减少了和数据库服务器的连接次数，提高了效率。
> ```
>
> 

#### 创建语法

```
create procedure 存储过程名(参数列表)
begin

	存储过程体(一组合法有效的sql语句)

end;

注意:
	1.参数列表包含3部分
	参数模式   参数名   参数类型
举例： in      sname   varchar(20)
参数模式:
	in：该参数模式可以作为输入(也就是说该参数需要调用方传入值)
	out：该参数模式可以作为输出(也就是说该参数只可以作为返回值)
	inout：该参数模式可以作为输入也可以作为输出(也就是说该参数既可以作为传入值也可以作为返回值)
	
	2.如果存储过程体 里面只有一句话 那么，begin end;可以省略
	  存储过程体重每一条sql语句的结尾要求必须加分号。
	  存储过程结果可以使用delimiter 重新设置
	  语法：
	  	delimiter 结束标记
	  案例:
	  	delimiter $
```

#### 调用语法

```
call 存储过程名(实参列表);

空参列表的存储过程：
示例：
	创建：
        create procedure guocheng_1()
        begin
            insert into student(id,name,sex,age)
            values('1','李晓钰','男','22');
        end $
	调用：
		call guocheng_1()$
```

> 参数模式 in：参数模式可以作为输入
>
> ```
> 参数模式不写默认是in
> 示例1：
> 	创建：
>         create procedure guocheng_1(in agentcode varchar(20))
>         begin
>             select name from  laagent where agentcode = agentcode;
>         end $
> 	调用：
>     	call guocheng_1('1231212121')$
> 示例2：
> 	创建：
> 		create procedure guocheng_1(in agentcode varchar(20),in ssex varchar(10))
>         begin
>         	declare ssname varchar(10) default '0';
>             select 
>             	name into ssname 
>             from  
>             	laagent 
>             where agentcode = agentcode and sex = ssex;
>             
>             select if(ssname<>0,ssname,'没有查到数据');
>             -- 调用存储过程以后 就会打印查询出的值
>             
>         end $
> 	调用：
>     	call guocheng_1('1231212121','0')$
> ```
>
> 

> 参数模式out:该参数只可以作为返回值
>
> ```
> 示例1：
> 	创建：
>         create procedure guocheng_1(in agentcode varchar(20),out ssex varchar(10))
>         begin
>             select 
>             	name into ssex 
>             from  
>             	laagent 
>             where agentcode = agentcode;          
>         end $
> 	调用：
> 		第一种：
>             call guocheng_1('1231212121',@bname)$
>             select @bname$
>         第二种：
>         	set @bname$  -- 定义一个变量
>         	call guocheng_1('1231212121',@bname)$
>         	select @bname$
> ```
>
> 

> 参数模式inout：该参数模式可以作为输入也可以作为输出
>
> ```
> 示例1：
> 	创建：
>         create procedure guocheng_1(inout a int,inout b int)
>         begin
>             set a = a*2;
>             set b = b*2;
>         end $
> 	调用：
> 	  在此过程中首先得创建两个变量
> 		set @m = 10;
> 		set @n = 20;
> 	  然后传入存储过程
> 		call guocheng_1(@m,@n)$
> 	  其次进行查询返回的内容
> 		select @m,@n;
> ```
>
> 



#### 删除存储过程

```
语法：存储过程一次只能删除一个
	drop procedure 存储过程名
```



#### 查看存储过程的信息

```
查看表的信息的时候我们会用到
	desc 表名;
查看存储过程desc是不管用的这里要用到
	show create procedure 存储过程名;
```

#### 修改存储过程

```
修改存储过程sql语句是不支持修改的。可以使用数据库常用的工具进行修改例如:navicat。
```



### 函数

```
1.提高代码的重用性。
2.简化操作
3.减少了编译次数并且减少了和数据库服务器的连接次数，提高了效率。
```

> 函数和存储过程的区别
>
> ```
> 存储过程：可以没有返回值，也可以有多个返回值。 
> 使用场景：适合做批量插入,更新。
> 	
> 函数：有且只有一个返回值。
> 使用场景:适合做处理数据后返回一个结果。
> ```
>
> 

#### 创建语法

> 注意：
>
> ​	1.参数列表包含两个部分： 参数名  参数类型
>
> ​	2.函数体：一定会有return。没有会报错。建议放在最后面。
>
> ​	3.函数体中仅一句话，可以省略 begin end
>
> ​    4.使用delimiter语句设置结束标记。

```
create function 函数名(参数列表) return 返回值类型
begin
	函数体
end;
```

#### 调用语法

```
select 函数名(参数列表);
```

#### 案例

```
案例一：
	无参有返回值
		create function demo() return int
		begin
			declare num int default 0;
			
			select 
				count(*) into num
			from
				laagent;
			return num;
		end $   ($ 是结束语)
		
		select demo()$
案例二：
	有参有返回值
		create function demo2(name varchar(20)) return double
		begin
			set @money = 0; -- 定义用户变量
			select 
				money into @money
			from
				laorderinfo
			where  username = name;
			return @money;
		end $
		
		select demo2('lixiaoyu') $
```



#### 查看函数

```
show create function 函数名;
```

#### 删除函数

```
drop function 函数名;
```

