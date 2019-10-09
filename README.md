
##需求
学习做一个问答社区

##资料
Github授权登陆：
https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/

OAuth documentation：
https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/

OkHttp：
https://github.com/square/okhttp

H2数据库：
好处：通过依赖把H2导入起来，可以随带着项目去发布，一起去提交。当别人下载这个项目的时候，就可以直接运行了，不用安装、配置数据库了。
	
##脚本
建表语句
```sql
CREATE TABLE USER
(
    ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID varchar(100),
    NAME varchar(50),
    TOKEN varchar(36),
    GMT_CREATE bigint,
    GMT_MODIFIED bigint
)
```

	

