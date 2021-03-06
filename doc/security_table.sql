--DROP TABLE sys_user;
--DROP TABLE sys_role;
--DROP TABLE sys_user_role_map;
--DROP TABLE sys_permission;
--DROP TABLE sys_role_permission_map;

CREATE TABLE sys_user
(
    user_id numeric(18,0) NOT NULL,
    name varchar(64),
    nickname varchar(64),
    password varchar(32),
    create_time date,
    CONSTRAINT pk_sys_user PRIMARY KEY (user_id)
);

CREATE TABLE sys_role
(
    role_id varchar(32) NOT NULL,
    name varchar(64),
    CONSTRAINT pk_sys_role PRIMARY KEY (role_id)
);

CREATE TABLE sys_user_role_map
(
    user_id numeric(18,0) NOT NULL,
    role_id varchar(32) NOT NULL,
    CONSTRAINT sys_user_role_map_pk PRIMARY KEY (user_id, role_id)
);

CREATE TABLE sys_permission
(
    permission_id varchar(64) NOT NULL,
    name varchar(64),
	permission_type varchar(16),
    CONSTRAINT sys_permission_pk PRIMARY KEY (permission_id)
);

CREATE TABLE sys_role_permission_map
(
    role_id varchar(32) NOT NULL,
	permission_id varchar(64) NOT NULL,
    CONSTRAINT sys_role_permission_map_pk PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE sys_session (
	session_id           numeric(18,0)        NOT NULL,
	user_name            varchar(64)          NULL,
	nickname             varchar(64)          NULL,
	client_ip            varchar(64)          NULL,
	server_name          varchar(64)          NULL,
	login_type           varchar(1)           NULL,
	login_time           timestamp            NULL,
	last_access_time     timestamp            NULL,
	CONSTRAINT pk_sys_session PRIMARY KEY (session_id)
);

CREATE TABLE sys_log_login (
	log_id               numeric(18,0)        NOT NULL,
	session_id           numeric(18,0)        NULL,
	user_name            varchar(64)          NULL,
	nickname             varchar(64)          NULL,
	client_ip            varchar(64)          NULL,
	server_name          varchar(64)          NULL,
	login_type           varchar(1)           NULL,
	login_time           timestamp            NULL,
	last_access_time     timestamp            NULL,
	logout_time          timestamp            NULL,
	fail_count           numeric(5)           NULL,
	fail_type            varchar(10)          NULL,
	CONSTRAINT pk_sys_log_login PRIMARY KEY (log_id)
);