# mysql定时任务

***mysql中事件调度器，event 也叫定时任务***

```
event根据计划执行特定的操作，语句可以使begin...end。event可以是一次性的，也可以是重复性的。并且重复事件的日程分配特定的 开始日期和时间，结束日期和时间。(默认情况下，定期event在创建后立即开始，并且无期限的继续，直到它被警用或者删除)
```



### 查看数据库事件是否开启

```
SELECT @@event_scheduler; 
 
SHOW VARIABLES LIKE 'event_scheduler';
```



### 开启数据库事件

```

set global event_scheduler=1; 
 
SET GLOBAL event_scheduler = ON;

在my.cnf中的[mysqld]部分添加 event_scheduler=ON 然后重启mysql。
```



### 关闭数据库事件

```
SET GLOBAL event_scheduler = OFF;
```



### 创建事件的语法

```
CREATE
    [DEFINER = { user | CURRENT_USER }] -- DEFINER: 定义事件执行的时候检查权限的用户。
    EVENT
    [IF NOT EXISTS]
    event_name   -- 事件名
    ON SCHEDULE schedule     -- ON SCHEDULE schedule: 定义执行的时间和时间间隔。
    [ON COMPLETION [NOT] PRESERVE] -- ON COMPLETION [NOT] PRESERVE: 定义事件是一次执行还是永久执行，默认为一次执行，即NOT PRESERVE。
    [ENABLE | DISABLE | DISABLE ON SLAVE]  -- ENABLE | DISABLE | DISABLE ON SLAVE: 定义事件创建以后是开启还是关闭，以及在从上关闭。如果是从服务器自动同步主上的创建事件的语句的话，会自动加上DISABLE ON SLAVE。
    [COMMENT 'comment']  -- COMMENT 'comment': 定义事件的注释。
    DO event_body;
    
    schedule:
        AT timestamp [+ INTERVAL interval] ... | EVERY interval  开始[STARTS timestamp [+ INTERVAL interval] ...] 结束[ENDS timestamp [+ INTERVAL interval] ...]
        【AT timestamp】一般用于只执行一次，一般使用时可以使用当前时间加上延后的一段时间，例如：AT CURRENT_TIMESTAMP + INTERVAL 1 HOUR。也可以定义一个时间常量，例如：AT '2006-02-10 23:59:00';
        【EVERY interval】一般用于周期性执行，可以设定开始时间和结束时间。
        
    interval:
        quantity {YEAR (年) | QUARTER(季度) | MONTH(月) | DAY(日) | HOUR(时) | MINUTE(分) | WEEK(周) | SECOND (秒)| YEAR_MONTH (年月)| DAY_HOUR() | AY_MINUTE() |DAY_SECOND ()| HOUR_MINUTE() | HOUR_SECOND() | MINUTE_SECOND()}
        
        

```



### 开启事件

```
alter event event_name(事件名称) ON COMPLETION PRESERVE ENABLE;
```



### 关闭事件

```
alter event event_name(事件名称) ON COMPLETION PRESERVE DISABLE;
```



### 删除事件

```
DROP EVENT [IF EXISTS] event_name(事件名称)
```



### 事件案例

```
案例1：
    CREATE TABLE `test` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `t1` datetime DEFAULT NULL,
      `id2` int(11) NOT NULL DEFAULT '0',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8

    <!-- 创建一个每隔3秒往test表中插入一条数据的事件 -->
    CREATE EVENT IF NOT EXISTS e_test_1   -- 如果不存在就创建事件
    ON SCHEDULE EVERY 3 SECOND		-- 永久执行  3秒执行一次
    ON COMPLETION PRESERVE         -- on completion [not] preserve 不加not是指永久执行，默认加not 只执行一次
    DO INSERT INTO test(id,t1) VALUES(NULL,NOW());


案例2：
    <!-- 创建一个1分钟后清空test表数据的事件  -->
	CREATE EVENT IF NOT EXISTS e_test_2   -- 如果不存在就创建事件
	ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 1 MINUTE   -- 定义执行时间 at current_timestamp 一般用作只执行一次 (当前时间 + 1分钟 )
	DO TRUNCATE TABLE test;   -- truncate 删除数据


案例3：
	<!-- 调用存储过程 -->
	1、创建过程
	CREATE PROCEDURE pro_test()  -- 创建存储过程
    BEGIN
        INSERT INTO test(id,t1,id2) VALUES(NULL,NOW(),'1000000'); -- 过程体内容 添加表内容
    END
	2、调用过程
	CREATE EVENT IF NOT EXISTS e_test_3 -- 如果不存在就创建事件
	ON SCHEDULE EVERY 3 SECOND   -- 3秒执行一次 every 周期性执行
	ON COMPLETION PRESERVE   -- ON COMPLETION [NOT] PRESERVE 加not是只执行一次，这句话不写默认只执行一次
	DO CALL pro_test();  -- 执行存储过程
    
    
案例4：    
    <!--  5天后开启每天定时清空test表： -->
        CREATE EVENT e_test   -- 创建事件
        ON SCHEDULE EVERY 1 DAY  -- 每天执行(every 代表周期性)
        STARTS CURRENT_TIMESTAMP + INTERVAL 5 DAY  -- (STARTS 开始)(CURRENT_TIMESTAMP: 当前时间)(INTERVAL 5 DAY: 5天)
        DO TRUNCATE TABLE test.aaa;   -- (TRUNCATE ： 删除数据)

案例5：
    <!-- 每天定时清空test表，5天后停止执行： -->
        CREATE EVENT e_test			-- 创建事件
        ON SCHEDULE EVERY 1 DAY		-- 每天执行(every 代表周期性)
        ENDS CURRENT_TIMESTAMP + INTERVAL 5 DAY	-- (ENDS 结束)(CURRENT_TIMESTAMP: 当前时间)(INTERVAL 5 DAY: 5天)
        DO TRUNCATE TABLE test.aaa; -- (TRUNCATE ： 删除数据)
```



### 关于事件权限问题（Access denied for user 'root'@'%' to database ‘xxxx’）

```
使用Naicat Premium远程连接的mysql上面创建了一个新数据库和新的用户后，给该用户添加这个新数据库权限时出现：
access denied for user 'root'@'%' to database xxxx的提示。
错误的原因是root用户在远程连接的MYSQL上面，没有这个新数据库的授权。在本地使用mysql应该不存在这个问题。
解决方法，执行授权：
    UPDATE mysql.user SET Event_priv = 'Y' WHERE HOST='%' AND USER='root';
    FLUSH PRIVILEGES;
    grant all PRIVILEGES on xxxx.* to root@'%' identified by 'password' with grant option;
    grant all on xxxx.* to 'root'@'%' identified by 'password' with grant option; 
    xxxx为创建的数据库，password为root的密码。请按实际要求进行更改。
```

https://www.cnblogs.com/jalja/category/687302.html



https://dev.mysql.com/doc/refman/5.7/en/events-configuration.html