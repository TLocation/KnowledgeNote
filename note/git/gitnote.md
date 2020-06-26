#### 命令大全

##### 基础配置

###### 1.配置用户名和密码

```
git config --global user.name "名字"

git config --global user.email "邮箱"
//查看配置
git config --list
```

###### 2.生成SSH密钥

```
//查看 ssh 密钥 在git bash窗口
cd ~/.ssh
//如果有文件夹  再输入 ls 如果有id_rsa  id_rsa.pub  两个文件 则已经生成ssh密钥
//生成ssh密钥
ssh-keygen -t rsa -C "邮箱"
//一直回车即可

```




##### 1.提交命令

###### 1.检查修改
```
 检查修改的文件

 git status
```
###### 2.查看修改的详细信息
```
 输入目录则查看单个文件修改  只输入git diff  查看全部修改

 git diff  <File 
```
###### 3.添加到暂本库
```
添加某个文件到暂存库 多个文件用 空格 隔开 

 git add  <Fie

 添加全部文件

 git add .
```
###### 4.提交到本地仓库
```
 生成新的提交记录

 git  commit -m "提交内容"

 追加提交 会把修改的文件合并到最近的一次提交

 git commit –amend 
```
###### 5 提交到远程仓库/拉取远程仓库代码

```
  分支名为想要提交到服务器的分支名

 git push origin 分支名

 提交之前应该先拉取

 git pull origin 分支名
```
###### 5.放弃修改

```
  放弃对某个文件的修改

 git checkout <file

 放弃所有文件 修改

 git checkout  .

 

 如果是新添加的文件   git checkout 不管用

 使用下面的命令

 查看  未进版本库的文件  及新建的文件

 git clean -fd -n                                        

 删除 文件 如果不加file 则会删除 所有未入版本库的文件 加了file 则会删除对应的文件

 git clean  -f <file           

 

 已经add 的情况下  已经进入暂存区

 从暂存区恢复到工作区 不加file 把暂存区的所有文件恢复到工作区

 git reset HEAD  <file
```


##### 2.暂时保存代码

```
在工作区写了一部分代码  需要 回退版本 或者切换分支 但是不想提交的情况下

 保存工作区的状态

 git stash  

 查看已经保存的状态列表

 git stash list  

 恢复文件  如果只是 git stash pop 则会恢复最近保存的文件

 git stash pop stash@{num} 

 清空记录

 git stash clear 
```
##### 3.分支操作

###### 1.查看分支

```
 查看本地分支

 git branch 

 查看远程分支

 git branch -r

 查看远程分支和本地分支

 git branch -a
```

###### 2.切换分支

```
 切换分支

 git checkout 分支名

 创建并切换

 git checkout -b 要创建的分支名

###### 3.更新远程仓库

 更新远程库

 git fetch 
```
##### 4.回退版本 查看日志

###### 1.查看日志

```
 查看 日志 按q退出

 git log 

 查看最近的操作commmitid

 git reflog
 
 查看某个用户的提交
 git log --author="user"
```
###### 2.回退版本

```
 commitid 只需要前几位即可  回到某个commitid的版本

 git reset --hard cimmitId 
```








