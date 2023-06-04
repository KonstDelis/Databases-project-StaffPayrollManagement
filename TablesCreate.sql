CREATE TABLE STAFF
	(sid VARCHAR(100) not NULL,
	first_name VARCHAR(100),
	last_name VARCHAR(100),
	marriage_status BOOLEAN,
	iban VARCHAR(34),
	bank_name VARCHAR(100),
	hire_year SMALLINT,
	hire_month TINYINT,
	phone VARCHAR(15),
	department VARCHAR(100),
	staff_type VARCHAR(100),
	address VARCHAR(100),
	PRIMARY KEY(sid) );

CREATE TABLE CHILD
	(childid VARCHAR(100) not NULL,
	sid VARCHAR(100) not NULL,
	age SMALLINT,
	PRIMARY KEY(childid),
	FOREIGN KEY(sid) REFERENCES STAFF(sid) ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE CONTRACT
	(contractid VARCHAR(100) not NULL,
	sid VARCHAR(100) not NULL,
	contract_salary INT,
	contract_start_year SMALLINT,
	contract_start_month TINYINT,
	contract_end_year SMALLINT,
	contract_end_month TINYINT,
	PRIMARY KEY(contractid),
	FOREIGN KEY(sid) REFERENCES STAFF(sid) ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE SALARY_DEPOSIT_DATA
	(depositid VARCHAR(100) not NULL,
	staff_first_name VARCHAR(100) not NULL,
	staff_last_name VARCHAR(100) not NULL,
	date_month TINYINT ,
	date_year SMALLINT ,
	salary INT,
	sid VARCHAR(100) not NULL,
	PRIMARY KEY(depositid),
	FOREIGN KEY(sid) REFERENCES STAFF(sid) ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE CONTRACTOR_TEACHING_SD
	(depositid VARCHAR(100) not NULL,
	contractid VARCHAR(100) not NULL,
	family_support INT,
	library_grant INT,
	PRIMARY KEY(depositid),
	FOREIGN KEY(contractid) REFERENCES CONTRACT(contractid) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(depositid) REFERENCES SALARY_DEPOSIT_DATA(depositid) ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE CONTRACTOR_ADMIN_SD
	(depositid VARCHAR(100) not NULL,
	contractid VARCHAR(100) not NULL,
	family_support INT,
	PRIMARY KEY(depositid),
	FOREIGN KEY(depositid) REFERENCES SALARY_DEPOSIT_DATA(depositid) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(contractid) REFERENCES CONTRACT(contractid) ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE PERMANENT_TEACHING_SD
	(depositid VARCHAR(100) not NULL,
	research_grant INTEGER ,
	family_support INTEGER ,
	basic_set_income INTEGER ,
	years_worked_bonus INTEGER ,
	PRIMARY KEY(depositid),
	FOREIGN KEY(depositid) REFERENCES SALARY_DEPOSIT_DATA(depositid) ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE PERMANENT_ADMIN_SD
	(depositid VARCHAR(100) not NULL,
	family_support INTEGER ,
	basic_set_income INTEGER ,
	years_worked_bonus INTEGER ,
	PRIMARY KEY(depositid),
	FOREIGN KEY(depositid) REFERENCES SALARY_DEPOSIT_DATA(depositid) ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE CONSTANTS
	(conid VARCHAR(100) not NULL,
	family_support_percentage INTEGER ,
	years_worked_percentage INTEGER ,
	basic_set_income_admin INTEGER ,
	basic_set_income_teaching INTEGER ,
	library_grant INTEGER ,
	research_grant INTEGER ,
	PRIMARY KEY(conid));