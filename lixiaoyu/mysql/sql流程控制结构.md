# 流程控制结构

> 顺序结构：程序上下一次执行
>
> 分支结构：程序从多条路径选择一条去执行
>
> 循环结构：程序在满足一定条件的基础上，重复执行一段代码



## 分支结构

> if结构
>
> ```
> 功能实现双分支：
> 	if(表达式1，表达式2，表达式3)
> 	如果表达式1成立则返回'表达式2'否则返回表达式3
> 
> 功能实现多重分支:应用在begin end中
> 	if 条件1 then 值
> 	elseif 条件2 then 值
> 	elseif 条件3 then 值
> 	elseif 条件4 then 值
> 	else 值
> 	end if;
> ```
>
> 

> case结构
>
> ```
> 1.类似于java中的switch语句。
> 	case 表达式 when 判断的值  then 值 when 判断的值 then 值 end;
> 2.类似于java中的多重if。
> 	case when 判断条件1 then 值 when 判断条件2 then 值 end；
> 	
> 可以作为表达式，嵌套在其他语句中使用，可以放在任何地方，begin end中或者是 begin end的外面。
> 可以作为独立的语句去使用，只能放在begin end中。
> 
> 作为表达式去使用：
> 	create procedure test_demo(in  money int)
>     begin
>     	case
>     	when money > 20 then select '我爱你';
>     	when money > 40 then select '我bu爱你';
>     	when money > 60 then select '我很爱你';
>     	when money > 80 then select '爱你';
>     	else select 'ddddd';
>     	end case;
>     end $
>     
>     call test_demo(50)$
> ```
>
> 

## 循环控制

> 分类：while 、loop、repeat
>
> 循环控制：
>
> ​	iterate类似于continue，结束本次循环，继续下一次
>
> ​	leave类似于break，跳出，结束当前所在的循环

```
标签如果不用到循环控制可以不加
1.while
语法：
	标签:while 循环条件 do
		循环体
	end while 标签;
	
2.loop
语法：
	标签:loop
		循环体
	end loop 标签;
可以用来模拟简单的死循环、

3.repeat
语法：
	标签:repeat
		循环体
	until 结束循环的条件
	end repeat 标签;
	
	
案例：while
批量插入到student多条记录
create procedure pro_while(in insertcount int)
begin
	declare i int default 1;
	while i <= insertcount do
		insert into student (username,password)values(concat('join',i),'123456789');
		set i = i + 1;
	end while;
end $

call pro_while(100)$;


案例：leave  循环控制
	create procedure pro_while(in insertcount int)
    begin
        declare i int default 1;
       a:while i <= insertcount do
            insert into student (username,password)values(concat('join',i),'123456789');
         if i>20 then leave a;
         end if;
         set i = i + 1;
      end while a;
    end $
call pro_while(100)$
```

```
流程控制案例：
	已知有stringcontent表
	id自增长
	content varchar(20)
向该表中插入制定个数的，随机字符串

drop table if exists stringcontent; //如果存在就删除表
create table stringcontent( // 创建表
	id int primary key auto_increment,
	content varchar(20)
);

delimiter $
create procedure test_randstr_insert(in INSERTCOunt int)
begin
	declare i int default 1; //定义一个循环变量a，表示插入次数
	declare str varchar(26) default 'abcdefghijklmnopqrstuvwxyz';
	declare startindex int default 1; //代表起始索引
	declare len int default 1; //代表截取字符的长度
	while i <= insertcount do
		set len = floor(rand()*(20-startindex+1)+1); //产生一个随机整数，代表 截取长度
		set startindex = floor(rand()*26+1)
		insert into stringcontent(content)values(substr(str,startindex,len));
		set i = i + 1; //循环变量更新
end $;
```

