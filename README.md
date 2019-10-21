
##需求
学习做一个问答社区

##资料
[Github授权登陆](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

[OAuth documentation](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)

[OkHttp](https://github.com/square/okhttp)

[spring devtools](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)

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

	

