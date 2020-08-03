## 视图

```
含义：虚拟的表

使用：和普通的表一样使用

应用场景：
	1.多个地方用到了同样的查询结果。
	2.查询结果使用的sql语句复杂。

mysql5.1版本出现的新特性，是通过表动态生成的数据。


例如:创建视图

	create view shitu
	as 
	select 
		name,age,sex 
	from 
		student s 
	inner join 
		class c 
	on s.id = c.id ;
	
	
	select *  from shitu where name like '张%';


```

### 语法

>视图的创建

```
	create view 视图名
	as
	查询语句;


```

>视图的修改

```
第一种：如果该视图存在那么就修改，如果不存在就创建
	create or replace view 视图名
	as
	查询语句。
	
	
第二种：直接修改视图
	alter view 视图名
	as
	查询语句;

```

>删除视图

```
	drop view 视图名,视图名......;

```

>查看视图(结构)

```
	desc 视图名;
	
	show create view 视图名;


```

### 视图的更新

```
视图是支持 update insert delete 的。

但是具备以下条件的视图不允许更新：
		1.包含关键字distince,group by,having,union 或者是union all
		2.常量视图
		3.select 中包含子查询
		4.from一个不能更新的视图
		5.where 子句的子查询引用了from字句中的表



````



### 视图和表的区别

```

		创建语法		是否占用物理空间		使用
视图   create view		只是保存了sql逻辑	    增删改查
												(一般不能增删改)



表     create table 	保存了数据              增删改查




````


### 视图的好处

```

	1.重用sql
	2.简化复杂的sql操作，不必知道他的查询细节
	3.保护数据，提高安全性。


```






