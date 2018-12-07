insert into sys_user(user_id, name, nickname, password, create_time)
 values (1, 'admin', '超级管理员', 'a', current_date)
insert into sys_user(user_id, name, nickname, password, create_time)
 values (2, 'test', '测试人员', 'a', current_date)

insert into sys_role(role_id, name)
 values ('admin', '超级管理员');
insert into sys_role(role_id, name)
 values ('test', '测试人员');
 
insert into sys_user_role_map(user_id, role_id)
 values (1, 'admin');
insert into sys_user_role_map(user_id, role_id)
 values (2, 'test');

insert into sys_permission(permission_id, name, type)
 values ('security_user_query', '用户查询', 'service');
insert into sys_permission(permission_id, name, type)
 values ('security_user_add', '用户新增及修改', 'service');
insert into sys_permission(permission_id, name, type)
 values ('security_user_delete', '用户删除', 'service');
insert into sys_permission(permission_id, name, type)
 values ('security_p_admin', '管理员用户权限', 'page');
insert into sys_permission(permission_id, name, type)
 values ('security_p_test', '测试用户权限', 'page');
 
insert into sys_role_permission_map(role_id, permission_id)
 values ('admin', 'security_user_query');
insert into sys_role_permission_map(role_id, permission_id)
 values ('admin', 'security_user_add');
insert into sys_role_permission_map(role_id, permission_id)
 values ('admin', 'security_user_delete');
insert into sys_role_permission_map(role_id, permission_id)
 values ('admin', 'security_p_admin');
 insert into sys_role_permission_map(role_id, permission_id)
 values ('test', 'security_user_query');
insert into sys_role_permission_map(role_id, permission_id)
 values ('test', 'security_user_add');
insert into sys_role_permission_map(role_id, permission_id)
 values ('test', 'security_p_test');