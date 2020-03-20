insert into sys_user(user_id, name, nickname, password, create_time)
 values (1, 'admin', '超级管理员', '0cc175b9c0f1b6a831c399e269772661', current_date);
insert into sys_user(user_id, name, nickname, password, create_time)
 values (2, 'test', '测试人员', '0cc175b9c0f1b6a831c399e269772661', current_date);

insert into sys_role(role_id, name)
 values ('admin', '超级管理员');
insert into sys_role(role_id, name)
 values ('test', '测试人员');
 
insert into sys_user_role_map(user_id, role_id)
 values (1, 'admin');
insert into sys_user_role_map(user_id, role_id)
 values (2, 'test');

insert into sys_permission(permission_id, name, permission_type)
 values ('p_security_permission', '权限管理', 'page');
insert into sys_permission(permission_id, name, permission_type)
 values ('s_security_permission', '权限管理', 'service');
 
insert into sys_role_permission_map(role_id, permission_id)
 values ('admin', 'p_security_permission');
insert into sys_role_permission_map(role_id, permission_id)
 values ('admin', 's_security_permission');
 insert into sys_role_permission_map(role_id, permission_id)
 values ('test', 'p_security_permission');
insert into sys_role_permission_map(role_id, permission_id)
 values ('test', 's_security_permission');