
CREATE TABLE t_user(
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20),
  password VARCHAR(50),
  perms VARCHAR(50)
);

insert into t_user(username, password,perms) values('admin','123','user:add),('zhangsan','123','user:update'),('lisi','123','user:update');