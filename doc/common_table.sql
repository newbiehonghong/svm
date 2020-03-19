CREATE TABLE dashboard_config (
	id numeric(18,0) NOT NULL,
	type numeric(8,0) NOT NULL,
	width numeric(4,0) NOT NULL,
	height numeric(4,0) NOT NULL,
	index numeric(5,0) NOT NULL,
	count numeric(3,0) NULL,
	mode varchar(12) NULL,
	services varchar(1024) NULL,
	apis varchar(1024) NULL,
	CONSTRAINT pk_dashboard_config PRIMARY KEY (id)
);