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
    [DEFINER = { user | CURRENT_USER }]
    EVENT
    [IF NOT EXISTS]
    event_name
    ON SCHEDULE schedule
    [ON COMPLETION [NOT] PRESERVE]
    [ENABLE | DISABLE | DISABLE ON SLAVE]
    [COMMENT 'comment']
    DO event_body;
    schedule:
        AT timestamp [+ INTERVAL interval] ... | EVERY interval  [STARTS timestamp [+ INTERVAL interval] ...] [ENDS timestamp [+ INTERVAL interval] ...]
    interval:
        quantity {YEAR | QUARTER | MONTH | DAY | HOUR | MINUTE | WEEK | SECOND | YEAR_MONTH | DAY_HOUR | AY_MINUTE |DAY_SECOND | HOUR_MINUTE | HOUR_SECOND | MINUTE_SECOND}
        
参数详细说明：
DEFINER: 定义事件执行的时候检查权限的用户。
ON SCHEDULE schedule: 定义执行的时间和时间间隔。
ON COMPLETION [NOT] PRESERVE: 定义事件是一次执行还是永久执行，默认为一次执行，即NOT PRESERVE。
ENABLE | DISABLE | DISABLE ON SLAVE: 定义事件创建以后是开启还是关闭，以及在从上关闭。如果是从服务器自动同步主上的创建事件的语句的话，会自动加上DISABLE ON SLAVE。
COMMENT 'comment': 定义事件的注释。
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
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t1` datetime DEFAULT NULL,
  `id2` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8
 
<!-- 创建一个每隔3秒往test表中插入一条数据的事件 -->
CREATE EVENT IF NOT EXISTS e_test_1 ON SCHEDULE EVERY 3 SECOND
ON COMPLETION PRESERVE
DO INSERT INTO test(id,t1) VALUES(NULL,NOW());
<!-- 创建一个10分钟后清空test表数据的事件  -->
CREATE EVENT IF NOT EXISTS e_test_2
ON SCHEDULE
AT CURRENT_TIMESTAMP + INTERVAL 1 MINUTE
DO TRUNCATE TABLE test;
<!-- 调用存储过程 -->
1、创建过程
CREATE PROCEDURE pro_test()
    BEGIN
        INSERT INTO test(id,t1,id2) VALUES(NULL,NOW(),'1000000');
    END
2、调用过程
CREATE EVENT IF NOT EXISTS e_test_3 ON SCHEDULE EVERY 3 SECOND
ON COMPLETION PRESERVE
DO CALL pro_test();
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