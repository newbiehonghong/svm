
-- DROP TABLE sample_emp;
-- DROP TABLE sample_emp_attachment;
-- DROP TABLE sample_dept;

CREATE TABLE sample_emp
(
    emp_id numeric(18,0) NOT NULL,
    name varchar(64),
    birthday date,
    province varchar(8),
    city varchar(8),
    dept_id varchar(8),
    memo varchar(400),
    start_time varchar(8),
    salary numeric(8,2),
    last_modified date,
    gender character(1),
    is_marriage character(1),
    emp_file character(1),
    work_year numeric(10,0),
    resume text,
    file_name varchar(64),
    attachment_id numeric(18,0),
    CONSTRAINT pk_sample_emp PRIMARY KEY (emp_id)
);

CREATE TABLE sample_emp_attachment
(
    attachment_id numeric(18,0) NOT NULL,
    file_name varchar(64),
    file_type varchar(20),
    content bytea,
    CONSTRAINT pk_sample_emp_attachment PRIMARY KEY (attachment_id)
)

CREATE TABLE sample_dept
(
    dept_id varchar(8) NOT NULL,
    dept_name varchar(64),
    parent_id varchar(8) NOT NULL
)