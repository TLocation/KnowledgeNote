# 触发器

## 触发器概念：

```
触发器是与表相关的数据库对象，在满足定义条件的时候触发。并且执行触发器中定义的语句集合。(可以协助应用在数据库端确保数据的完整性)。

场景:
	比如现在有【用户表】和【日志表】，当一个用户被创建的时候，就需要在日志表中插入创建的log日志信息，(如果在不适用触发器的情况下就需要编写程序逻辑才可以实现。)触发器的作用就是当在【用户表】中插入一条数据之后，触发器帮你在【日志表】中也插入一条日志信息。(触发器可以执行增，删，改操作)
```



## 语法

```
create trigger 
		trigger_name trigger_time trigger_event 
on tb_name 
for each row 
trigger_stmt

1、trigger_name:触发器名称
2、tirgger_time:触发时机 BEFORE 或者 AFTER
3、trigger_event:触发事件  INSERT,DELETE 或者UPDATE
       触发器类型              激活触发器的语句
   		insert              insert,load data(相当于insert),replace(插入的数据和原来的primary key或者unique相同的时候，会删除旧数据插入新数据)
   		update				update
   		delete				delete,replace
4、tb_name:表示建立触发器的表名。(在那张表建立触发器)
5、trigger_stmt:触发器的程序体。(可以是一条sql语句或者是用BEGIN...END包含的多条sql。)

可以说mysql可以创建6种触发器：
	在事件之前               在事件之后
	BEFORE INSERT          AFTER INSERT
    BEFORE DELETE		   AFTER DELETE
    BEFORE UPDATE          AFTER UPDATE

for each row :表示任何一条记录上的操作满足触发事件都会触发该触发器。
```



> 创建多条执行语句的触发器(所谓的多条执行语句就是 begin..end中可以写过个sql)
>
> ```
> DELIMITER ||或$
> create trigger 触发器名 before|after 触发事件
> on 表名 for each row
> begin
> 	执行语句列表
> end ||或$
> 
> DELIMITER; 作为技术符号
> ```
>
> 

```
案例：
 CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `add_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`(250)) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=1000001 DEFAULT CHARSET=latin1;



CREATE TABLE `logs` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `log` varchar(255) DEFAULT NULL COMMENT '日志说明',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日志表';



DELIMITER $
CREATE TRIGGER user_log AFTER INSERT ON users FOR EACH ROW
BEGIN
DECLARE s1 VARCHAR(40)character set utf8;
DECLARE s2 VARCHAR(20) character set utf8;#后面发现中文字符编码出现乱码，这里设置字符集
SET s2 = " is created";
SET s1 = CONCAT(NEW.name,s2);     #函数CONCAT可以将字符串连接
INSERT INTO logs(log) values(s1);
END $
DELIMITER ;




insert into users(name,add_time) values('周伯通',now());
```



### 使用触发器的限制和注意事项

```
限制：
    1、触发器不能调用将【数据返回客户端的存储程序】，也不能使用【采用call语句的动态sql】但是允许  存储过程或者是函数 通过out或者inout类型的参数将数据返回触发器。只是不能调用直接返回数据的过程。
    2、不能再触发器中使用以显示或隐式方式开始或结束事务的语句，如START TRANS-ACTION,COMMIT或ROLLBACK。
    
注意事项：
	MySQL的触发器是按照BEFORE触发器、行操作、AFTER触发器的顺序执行的，其中任何一步发生错误都不会继续执行剩下的操作，如果对事务表进行的操作，如果出现错误，那么将会被回滚，如果是对非事务表进行操作，那么就无法回滚了，数据可能会出错。
```

