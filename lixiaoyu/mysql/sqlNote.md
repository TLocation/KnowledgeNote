### 按照年代分类  sql分为92和99  推荐99
#### SQL92仅仅支持内连接 就是多表关联用逗号连接
#### SQL99 支持  内连接 + 外连接(左外和右外不支持全外) + 交叉连接

#### sql执行顺序
select ---> from ---> where ---> group by ---> having  ---> order by  ---> limit



#### 获取当前日期

```
获得当前日期
SELECT NOW(),CURDATE(),CURTIME()

获取前一天
SELECT  DATE_SUB(CURDATE(),INTERVAL 1 DAY);

获取后一天
SELECT  DATE_SUB(CURDATE(),INTERVAL -1 DAY);

根据录入日期获取当月最后一天
select LAST_DAY(20200316)

根据录入日期获取当月第一天一天
select first_day(20200316)

-- 根据录入日期得到周一
select subdate('20200611',weekday('20200611')); 

-- 根据录入日期得到周天
select subdate('20200611',weekday('20200611')-6);


年份差
SELECT TIMESTAMPDIFF(YEAR,'2017-05-01', DATE_FORMAT(now(), '%Y-%m-%d'))

月份差
SELECT TIMESTAMPDIFF(MONTH,'2017-05-01', DATE_FORMAT(now(), '%Y-%m-%d'))

根据年月获取到月份差
select PERIOD_DIFF('202004',DATE_FORMAT(now(), '%Y%m'))

天数差
（1）SELECT datediff(DATE_FORMAT(now(), '%Y-%m-%d'),DATE_FORMAT('2018-09-10','%Y-%m-%d'))
（2）SELECT TIMESTAMPDIFF(DAY,'2017-05-01', DATE_FORMAT(now(), '%Y-%m-%d'))

```

#### sql 在 ...  和 ...  之间 (between ... and ...)
#### 查询 入职时间在 2019-08-09 和 2020-03-04 之间的人员数据  >=2019-08-09 和 <=2020-03-04

```
select *   from  laagent where employdate between '2019-08-09' and  '2020-03-04'

```


#### update 用and 连接错误用法

```
表: a     b      c
    1     1      2

例如：1>、update 表 set a = 2 and b = 2 where c = 2; 结果：a = 0 , b = 1;
      2>、update 表 set a = 2 and b = 1 where c = 2; 结果：a = 1 , b = 1;
	  
```

#### 常用函数的使用

```   
	select version()     -- 当前数据库版本
    select DATABASE()    -- 当前库
    select user()        -- 当前用户

	//大小写转换
	>select upper('asdf')   结果：ASDF

	>select lower('ASDF')   结果：asdf


	//拼接  concat(a,b,c,d)
	>select concat('wo 是','_','li',':','xiaoyu')    结果：wo 是_li:xiaoyu
	
	//mysql索引是从 1 开始的
    
	//返回第一次出现的索引
	instr(12312312,3)    结果：3
	
	//去掉前后空格
    >select trim(' li  li ')   结果：li  li
	>select trim('a' from 'aaaa张aaa无忌aaa')    结果:张aaa无忌(去掉前后a)
	
	//填充 左填充:lpad(字段名,填充的长度,填充的字符)   右填充:rpad(字段名,填充的长度,填充的字符)
	>select lpad('张无忌的师傅',10,'张三丰是')    结果:张三丰是张无忌的师傅
    >select rpad('张三丰',10,'是张无忌的师傅')    结果:张三丰是张无忌的师傅
	
	//替换  replace()
	>select replace('abcdefg','bcd','BCD')   结果:aBCDefg
	
	//ceil():向上取整。返回>=该参数的最小整数
	
	//floor():向下取整。返回<=该参数的最大整数
	
	//mod():取余  同java中的%。 mod(a,b)  a-a/b*b
	
	//count(字段)： 统计该字段非空值的个数
	//count(*):   统计结果集的行数
	
	//now():当前日期时间  
	//curdate():当前日期不包括时间
	//curtime():当前时间不包括日期
	//year年  month月    monthname(now()):返回英文当前月份
	
	
	//1.mysql日期和字符相互转换方法 
	date_format(date,’%Y-%m-%d’) ————–>oracle中的to_char(); 
	str_to_date(date,’%Y-%m-%d’) ————–>oracle中的to_date();

	%Y：代表4位的年份 
	%y：代表2为的年份

	%m：代表月, 格式为(01……12) 
	%c：代表月, 格式为(1……12)

	%d：代表月份中的天数,格式为(00……31) 
	%e：代表月份中的天数, 格式为(0……31)

	%H：代表小时,格式为(00……23) 
	%k：代表 小时,格式为(0……23) 
	%h： 代表小时,格式为(01……12) 
	%I： 代表小时,格式为(01……12) 
	%l：代表小时,格式为(1……12)

	%i： 代表分钟, 格式为(00……59) 【只有这一个代表分钟，大写的I 不代表分钟代表小时】

	%r：代表 时间,格式为12 小时(hh:mm:ss [AP]M) 
	%T：代表 时间,格式为24 小时(hh:mm:ss)

	%S：代表 秒,格式为(00……59) 
	%s：代表 秒,格式为(00……59)

	//2.例子： 
	select str_to_date(‘09/01/2009’,’%m/%d/%Y’)

	select str_to_date(‘20140422154706’,’%Y%m%d%H%i%s’)

	select str_to_date(‘2014-04-22 15:47:06’,’%Y-%m-%d %H:%i:%s’)

```
## 加密函数

```
select password(字符串);

select md5(字符串);

```




#### case when 使用：

```
1:case 判断字段 
  when 常量1 then 值 
  when 常量2 then 值
  when 常量3 then 值
  when 常量4 then 值
  end;
```

```
2:case    when 判断条件1
  then 值 when 判断条件2
  then 值 when 判断条件3
  then 值 when 判断条件4
  else 值
  end;
```

#### if...else....

```
1、if 判断条件1 then
      值;
   elseif 判断条件2 then
      值;
   elseif 判断条件3 then
      值;
   else
      值;
   end if;
   
2、if 判断条件 then
      值；
   else
      值；
   end if;
```

#### union 和 union all 

```

union:两个表都有的数据(就是union all取出冗余的数据)

union all：两个表的数据全部展示

如果需要展示不同的数据比如count只有一条数据:就可以使用union 或者 union all
(表的别名一定要不同 否则数据有问题)

应用场景：要查询的结果来自于多个表，并且并且多个表没有直接的连接关系，但是查询的数据是一致的。

特点：
	1、要求多条查询语句的查询列数是一致的。
	2、要求多条查询语句查询的列数是一致的。
	3、union默认是去重，union all 可以包含重复项
	
案例：查询每个国家的人数的总个数
   select concat(*) as '个数',name as '国家' from  country where name = '中国'
   union
   select concat(*),name from  country where name = '美国'
   union
   select concat(*),name from  country where name = '韩国'
   
   

```
#### group by 使用

```
group by :select  分组函数(avg,max,count,sum,min)
          from  表
		    [where 筛选条件]
		  group by 分组列表
		    [order by 子句] 排序  默认:asc

```

#### exists 和 not exists

```
-- 表stud1中 存在 stud1中name 和 stud2 中name相等数据的
select *  from stud1 a where exists (select 1 from stud2 b  where a.name = b.name)

-- 表stud1中  存在stud1中name 和 stud2 中name不相等数据的
select *  from stud1 a where not exists (select 1 from stud2 b  where a.name = b.name)

-- 表stud2中  不存在 表1和表2 相等的name数据
select *  from stud2 a where not exists (select 1 from stud1 b  where a.name = b.name)

```

#### cast(属性值 as 数据类型)  用来转换类型

```
可以转换的类型是有限制的。这个类型可以是以下值其中的一个：

    二进制，同带binary前缀的效果 : BINARY    
    字符型，可带参数 : CHAR()     
    日期 : DATE     
    时间: TIME     
    日期时间型 : DATETIME     
    浮点数 : DECIMAL      
    整数 : SIGNED     
    无符号整数 : UNSIGNED 

```

#### 向上取整:ceiling()    向下取整:floor()      返回最接近的函数:ROUND(23.3222,3)

```
select ceiling(23.4) -----  24


select floor(23.4)   ------ 23


1、select round(23.3222,3)  ------ 23.322 保留小数点后3位
2、select round(23.3222)    -----  23     四舍五入


```



## 多表关联

### 等值连接   内连接   SQL99 inner join

```
SQL92：

例如：select 
		a.id,a.name,a.age,a.sex,b.class 
	from 
		Student a,Classes b 
	where 
		a.id = b.id; 
	[有筛选条件:where 条件;]

```

```
SQL99：

例如：select 
		a.id,a.name,a.age,a.sex,b.class 
	from 
		Student a 
	inner join 
		Classes b 
	on
		a.id = b.id; 
	[有筛选条件:where 条件;]


```


### 非等值连接

```
SQL92:

例如：select 
		a.id,a.name,a.age,a.sex,b.class 
	from 
		Student a,Classes b 
	where 
		between a.id and b.id； 

```

```
SQL99:

例如：select 
		a.id,a.name,a.age,a.sex,b.class 
	from 
		Student a
	inner join
		Classes b 
	on 
		between a.id and b.id； 

```

### 自连接

```
SQL92:

例如: 假如说同一张表里面 有id   pid是id的上级，想查询id 对应的它的上级的编号和名字
	select 
		a.id,a.name,b.id,b.name
	from 
		Student a,Student b
    where a.id = b.pid;		

```

```
SQL99:

例如: 假如说同一张表里面 有id   pid是id的上级，想查询id 对应的它的上级的编号和名字
	select 
		a.id,a.name,b.id,b.name
	from 
		Student a
	inner join
		Student b
    on
		a.id = b.pid;		

```

### 外连接   一般的应用场景就是查询：一个表中有，另一个表中没有的数据

>外连接查询 =  内连接结果 + 主表中有而从表中没有的数据(null)；

```

left join 左连接 左边是主表


right join 右连接 右边是主表


pull 全外连接 = 内连接结果 + 表1中有表2中没有 + 表2中有表1中没有   （mysql不支持）
```

### 交叉连接 cross join

```


```




## 子查询  出现在其他语句中的select语句   叫做子查询或者是内查询
### 子查询出现的位置

>select 后面

```

支持标量子查询


案例：查询每个班级的学生个数
select a.*,(select count(*) from student where id = a.pid) where class a ;


```

>from 后面

```
支持表子查询

案例:查询每个员工的工资和所在的班级
select 
	a.money,c.name
from 
	(select money a,id b from laagent ) la
inner join 
     class c
on
    c.pid = la.b



```


>where 或者是having后面


```
支持标量子查询
支持列子查询
支持行子查询


1、子查询放在小括号内
2、子查询一般放在条件右侧
3、标量子查询 一般搭配单行操作符使用(>  <  >=  <=  <>)
	①：标量子查询
		案例：查询 谁的工资比tom高；
		select *  from  laagent where money  > (select money from laagnt where name = 'tom')
		案例：查询工资最少的人  工号，姓名，工资
		select id ,name ,money where money = (select min(money) from laagent);
	②：列子查询
		any 和some 一样： where a > any(11,22,33) 只要大于其中任意一个就行
		all:    where  a  > all（11,22,33）  必须大于all里面的所有
		
		案例：查询 工资是 2000 和 6000的员工的信息
		select * from laagent where money in (2000,6000);
						或者是
		select * from laagent where money = any(2000,6000); -- 等于其中的任意一个
		
		案例：查询工资在 2000 或者 6000 的 员工
		select * from laagent where money any (2000,6000);
	③：行子查询(结果集一行多列 或者是 多行多列)
		案例：查询员工编号最小 并且工资最高的员工
		select *  from  laagent where id  = (select min(id) from laagent) and money =(select max(money) from  laagent);
		或者
		select *  from  laagent where (id,money)  = (select min(id),max(money) from laagent);
		
		
```

>exists后面 存在返回1   不存在返回0

```
支持表子查询

案例：查询不在班级101 的学生信息
select *  from  laagent a where  nont exists (select 1 from class where pid = a.id and cla = 101);

```



>案结果集行列不同

```

标量子查询(结果集只有一行一列)
列子查询(结果集只有一列多行)
行子查询(结果集有一行多列)
表子查询(结果集一般为多行多列)

```

## 分页 limit

```
1、limit 语句放在查询语句的最后

2、select *  from  表 limit 0,5;  -- 0 从下标为0的开始 也就是第一条数据。5 是一页显示5条
公式： 页数page , 每页的条数size   select * from 表 limit (page-1)*size,size;   
	例如:每页10条，找第8页
	limit （8-1）*10,10；


```




## DML 语言   数据操作语言(增删改)
### 增
```
1.insert into 表 (id,name,age,sex)values('1','李夏雨','22','女');

2.insert into 表 set id = '1',name = '李夏雨';

3.insert into 表(id,name,age,sex)values('1','李夏雨','22','女')，('2','李夏雨2','22','女')，('3','李夏雨3','22','女')；

4.insert into 表(id,name,age,sex) select id,name,age,sex from laagent;

```
### 改


>单表

```

1.update 表 set name = '',age = '' where id = '';

```

>多表

```

2.udpate 表1 a,表2 b set 列 = '' where 连接条件 and 筛选条件

3.udpate 表1 a inner|left|right join 表2 b on 连接条件 set  列= ''  where  筛选条件


```

### 删   如果有自增列  用delete删除的话再添加 从断点处自增，但是 用truncate 刪除 自增列从1开始

>单表

```

1.delete from 表 where id = '';

2.truncate table 表; 不能加where条件  执行 就整个表全部删除了

```

>多表   delete那个表就删那个表

```

1.delete 表1 from 表1 a,表2 b where  连接条件  and 筛选条件

2.delete 表1 from 表1 a inner|left|right join  表2 b on 连接条件 where 筛选条件

```


## DDL语言   数据定义语言(库和表的管理)

>创建*create*     修改*alter*   删除*drop*

### 库的管理

```
创建：create database '库名';  //默认创建在data文件夹下
	  create database if not exists '库名'; //如果有这个库名了就不创建

修改：rename database '库名' to '新库名';  //因为不安全 已经不能使用

	alter  database '库名' character set gbk;  // 改变库的字符集

删除：drop database '库名'; //删除库
    drop database if not exists '库名'; //如果存在就删除库


```

### 表的创建

```
create table 表名(
	列名 列的类型【(长度)约束】,
	列名 列的类型【(长度)约束】,
	列名 列的类型【(长度)约束】,
	列名 列的类型【(长度)约束】,
	.....
	列名 列的类型【(长度)约束】
)

例如：create table Book(  //创建book表
		id int, #编号
		bname varchar(20),#图书名
		price double,#价格
		authorid int,#作者编号
		publishdate datetime #出版日期
	  
	 )
	  create table Author(  //创建作者表
		id int, #编号
		auname varchar(20),#作者名名
		nation varchar(20) #国籍
	  )


```

### 表的修改

```
基本语法: alter table 表名 add|drop|modify|change column 列名 【列名类型，约束】

```


>修改列名

```
alter table book change column publishdate pubdate datetime;

```

>修改列的类型/约束

```
alter table book modify column pubdate timestamp;

```

>添加新列

```
alter table author add column annual double;

```


>删除列

```
alter table author drop colum annual;

```


>修改表名

```
alter table author rename to book_author;

```

### 表的删除

```
drop table book;

drop table if exists book;   -- 如果存在就删除

```

### 仅仅复制表的结构

```

create table 新表 like 复制的表

```

### 复制表结构 + 数据

```
create table 新表 select *  from  复制的表

```

## 常见的数据类型

```
数值型：
		整形：			  字节
			tinyint			1		
			smallint		2
			meditmint		3
			int,integer		4
			bigin			8
			1、如果不设置无符号，默认有符号。设置有符号 unsigned.  符号就是指 负数
			2、如果插入的数据超出了整形的范围，会报out of range 异常，并且插入的是临界值
			3、如果不设置长度会有默认的长度
		小数：
			定点型：dec(m,d)
					decimal(m,d)
			浮点型：
					float(m,d)		4
					double(m,d)		8
					m代表整数部位+小数部位
					d小数部位
					m,d都可以省略，如果是decimal,则m默认为10，d默认为0
								   如果是float和double会根据数值的精度来确定精度

字符型：
		较短的文本：
					char 				耗费空间         效率高
					varchar				比较节省空间	 效率低
		较长的文本：text  blob(较长的二进制数据)
		
		枚举：enum
			例如：crate table student(
						aa enum('a','b','c','d','e')
				  )
				  insert into student values('a')
				  insert into student values('b')
				  insert into student values('v')
				  insert into student values('d')
				  insert into student values('e')
				  结果就是第三行是空的  因为 enum，只能插入固定的值
				  

日期型：
		date 			4		日期
		datetime		8		日期 时间
		timestamp		4		当前时区的日期 时间
		time			3		时间
		year			1		年


```

## 约束 ：一种限制，用于限制表中的数据为了保证表中的数据的准确和可靠性

```
六大约束：
		not null:非空
		default：默认，用于保证该字段有默认值
		primary key：主键约束  保证字段具有唯一性，并且非空
		unique:唯一，保证字段唯一 可以为空
		check：检查约束（mysql中不支持）
		foreign key：外键约束，用于限制2个表的关系
		
		列级约束：
		例如:create table student(
				id int primary key, -- 主键
				name varchar(20) not null,非空
				seat int unique,-- 唯一约束
				age int default 18,-- 默认约束 默认18
				pid int foreign key references 表2(id) -- 外键
			)
		
					
		主键约束> 保证唯一      不允许为空       主键只允许一个
		
		
		唯一约束> 保证唯一      只允许一个为空    多个    

		外键：1.要求在从表设置外键关系
			  2.从表外键类型要和主表匹配字段类型一致
			  3.主表关联列必须是一个key（主键或者是唯一）
			  4.插入数据先插主表，再插从表
			    删除先删从表
				
				
自增长列：1.必须是一个key
		  2.一个表只能有一个标识列
		  3.标识列的类型之能是数值型
		  4.标识列 可以通过手动插入值设置起始值，还可以设置步长来配置。set increment_increment = 3;
```







