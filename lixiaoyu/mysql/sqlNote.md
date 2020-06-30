#####按照年代分类  sql分为92和99  推荐99
#####SQL92仅仅支持内连接 就是多表关联用逗号连接
#####SQL99 支持  内连接 + 外连接(左外和右外不支持全外) + 交叉连接

####sql执行顺序
select ---> from ---> where ---> group by ---> having  ---> order by  ---> limit



####获取当前日期
SELECT NOW(),CURDATE(),CURTIME()

获取前一天
SELECT  DATE_SUB(CURDATE(),INTERVAL 1 DAY);

获取后一天
SELECT  DATE_SUB(CURDATE(),INTERVAL -1 DAY);

年份差
SELECT TIMESTAMPDIFF(YEAR,'2017-05-01', DATE_FORMAT(now(), '%Y-%m-%d'))

月份差
SELECT TIMESTAMPDIFF(MONTH,'2017-05-01', DATE_FORMAT(now(), '%Y-%m-%d'))

天数差
（1）SELECT datediff(DATE_FORMAT(now(), '%Y-%m-%d'),DATE_FORMAT('2018-09-10','%Y-%m-%d'))
（2）SELECT TIMESTAMPDIFF(DAY,'2017-05-01', DATE_FORMAT(now(), '%Y-%m-%d'))

####sql 在 ...  和 ...  之间 (between ... and ...)
####查询 入职时间在 2019-08-09 和 2020-03-04 之间的人员数据  >=2019-08-09 和 <=2020-03-04
select *   from  laagent where employdate between '2019-08-09' and  '2020-03-04'



####update 用and 连接错误用法
表: a     b      c
    1     1      2

例如：1>、update 表 set a = 2 and b = 2 where c = 2; 结果：a = 0 , b = 1;
      2>、update 表 set a = 2 and b = 1 where c = 2; 结果：a = 1 , b = 1;


####常用函数的使用
    
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


####case when 使用：
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

####if...else....
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

####union 和 union all 
```

union:两个表都有的数据(就是union all取出冗余的数据)

union all：两个表的数据全部展示

如果需要展示不同的数据比如count只有一条数据:就可以使用union 或者 union all
(表的别名一定要不同 否则数据有问题)

```
####group by 使用
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

####向上取整:ceiling()    向下取整:floor()      返回最接近的函数:ROUND(23.3222,3)
```
select ceiling(23.4) -----  24


select floor(23.4)   ------ 23


1、select round(23.3222,3)  ------ 23.322 保留小数点后3位
2、select round(23.3222)    -----  23     四舍五入


```


####根据录入日期得到日期所在的周一和周天
```
-- 得到周一
select subdate('20200611',weekday('20200611')); 

-- 得到周天
select subdate('20200611',weekday('20200611')-6);


```


#########多表关联







