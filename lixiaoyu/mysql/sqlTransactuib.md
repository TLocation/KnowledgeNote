# 事务

## 事务就是一个或者是一组sql语句组成的一个执行单元，这个执行单元要么全部执行，要么就全部不执行。

```
innodb  存储引擎支持事务，其他都不支持


```

## 事务的特性  ACID

```
1.原子性A
	事务是一个不可分割的工作单位，要不全部执行要么全部不执行
	
2.一致性C
	事务执行完成之后 数据还是一直状态
	
3.隔离性I
	事务的执行不能被其他事务干扰， 也就是说一个事务能不操作和使用的数据对并发的其他事务
	是隔离的，并发执行的每个事务之间不能互相干扰。 (需要看隔离的级别)

4.持久性D
	事务一旦提交，就会永久改变。



```


## 事务的创建

```
隐式的事务： 事务没有明显的开启和结束的标志。


显示的事务：事务具有明显的开启和结束的标志。
	前提是必须先设置自动提交功能为禁用。


关于事务的一些术语

    开启事务：Start Transaction
    事务结束：End Transaction
    提交事务：Commit Transaction
    回滚事务：Rollback Transaction


	
	1.开启事务
	set autocommit = 0;
	2.编写事务中的sql语句(select  insert update delete)
	3.结束事务
		commit;提交事务
		rollback;回滚
		
	案例:set autocommit = 0;
		 start transaction; -- 开启事务
		 update biao set name = 'lelele' where id = 11;
		 delete from biao where id = 22;
		 commit;
		 
		 
		 回滚: name = lixiaoyu,biao id 22 存在
		 set autocommit = 0;
		 start transaction;  -- 开启事务
		 update biao set name = 'lelele' where id = 11;
		 delete from biao where id = 22;
		 rollback;
		 因为是回滚，所以数据不改变 还是原来的数据

start transaction;??

```


## 数据库隔离级别

>如果说多个事务同时执行去访问数据库，没有采取必要的隔离级别的话就可能产生一些并发问题。
```
脏读：假如有两个事物a,b ，a读取了已经被b修改过但是环没有提交的字段之后，如果b回滚了，那么a读取
      到的内容就是临时且无效的。

不可重复读：例如两个事务a，b。a读取了一个字段，然后b更新了这个字段之后，a再次读取，字段值
		就不同了 。
		
幻读：例如有2个事务a，b。a从表内读了一个字段是2行，然后b在这个表中插入了一些新的行，如果a再
      次读取如同一个表，就会出现多行数据。(针对插入insert)
	  

```

>查看隔离级别

```

select @@tx_isolation;

```


>如果去避免上面这些并发问题  设置隔离级别

```

隔离级别：					脏读           不重复读				幻读
	1.read uncmmitted:		  √				  √                   √
	
	2.reade committed:        ×               √                   √
	
	3.repeatable read:        ×               ×                   √
	
	4.serializable:           ×               ×                   ×

mysql中默认repeatable read 隔离级别。
oracle中默认read committed；



set session | global transaction isolation level .....;
1.设置当前mysql连接的隔离级别：

	set transaction isolation level .......;   (...为隔离级别)


2.设置数据库系统的全局隔离级别(重启才有效)

	set global transaction isolation level .....; (....为隔离级别)
	


```

>回滚  saveplint

```

例如：
	set autocommit = 0;
	start transaction;
	delete from  biao where id = 20;
	savepoint a;  -- 设置一个节点名
	delete from  biao  where id = 28;
	rollback to a; -- 回滚到保存点
	
	select *  from biao ; 结果就是没有20的数据 有28的数据
	
```

















