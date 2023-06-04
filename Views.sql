
CREATE VIEW SalaryTotals AS
SELECT
	st.sid , st.first_name , st.last_name, st.marriage_status , st.iban, st.bank_name,
	st.hire_year , st.hire_month, st.phone, st.department , st.staff_type, st.address,
	sal.date_month , sal.date_year , sal.salary 
FROM STAFF AS st
INNER JOIN SALARY_DEPOSIT_DATA AS sal 
ON st.sid = sal.sid;


CREATE VIEW ContractorAdminSalaryTotals AS
SELECT
	st.sid , st.first_name , st.last_name, st.marriage_status , st.iban, st.bank_name,
	st.hire_year , st.hire_month, st.phone, st.department , st.staff_type, st.address,
	sal.date_month , sal.date_year , sal.salary AS Total_Salary,con.contract_salary,casal.family_support
FROM STAFF AS st 
INNER JOIN SALARY_DEPOSIT_DATA AS sal 
ON st.sid = sal.sid
INNER JOIN CONTRACTOR_ADMIN_SD AS casal 
ON sal.depositid = casal.depositid
INNER JOIN CONTRACT AS con 
ON st.sid = con.sid
WHERE st.department='Administrator' AND st.staff_type = 'Contractor';



CREATE VIEW PermanentAdminSalaryTotals AS
SELECT 
	st.sid , st.first_name , st.last_name, st.marriage_status , st.iban, st.bank_name,
	st.hire_year , st.hire_month, st.phone, st.department , st.staff_type, st.address,
	sal.date_month , sal.date_year , sal.salary AS Total_Salary,
	pasal.basic_set_income , pasal.family_support , pasal.years_worked_bonus
FROM STAFF AS st
INNER JOIN SALARY_DEPOSIT_DATA AS sal ON st.sid = sal.sid
INNER JOIN PERMANENT_ADMIN_SD AS pasal ON sal.depositid = pasal.depositid
WHERE st.department='Administrator' AND st.staff_type = 'Permanent';


CREATE VIEW ContractorTeachingSalaryTotals AS
SELECT 
	st.sid , st.first_name , st.last_name, st.marriage_status , st.iban, st.bank_name,
	st.hire_year , st.hire_month, st.phone, st.department , st.staff_type, st.address,
	sal.date_month , sal.date_year , sal.salary AS Total_Salary, con.contract_salary,
	ctsal.family_support , ctsal.library_grant
FROM STAFF AS st
INNER JOIN SALARY_DEPOSIT_DATA AS sal ON st.sid = sal.sid
INNER JOIN CONTRACTOR_TEACHING_SD AS ctsal ON sal.depositid = ctsal.depositid
INNER JOIN CONTRACT AS con ON st.sid = con.sid
WHERE st.department='Teaching' AND st.staff_type = 'Contractor';


CREATE VIEW PermanentTeachingSalaryTotals AS
SELECT 
	st.sid , st.first_name , st.last_name, st.marriage_status , st.iban, st.bank_name,
	st.hire_year , st.hire_month, st.phone, st.department , st.staff_type, st.address,
	sal.date_month , sal.date_year , sal.salary AS Total_Salary,
	ptsal.basic_set_income, ptsal.family_support , ptsal.research_grant , ptsal.years_worked_bonus
FROM STAFF AS st 
INNER JOIN SALARY_DEPOSIT_DATA AS sal
ON st.sid = sal.sid
INNER JOIN PERMANENT_TEACHING_SD AS ptsal
ON sal.depositid = ptsal.depositid
WHERE st.department='Teaching' AND st.staff_type = 'Permanent';
