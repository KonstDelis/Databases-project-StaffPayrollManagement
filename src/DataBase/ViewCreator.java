package DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewCreator {


    private Connection connection;

    public void useConnection(Connection connection ) {
        this.connection = connection;
    }

    public void createViews()throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();

        deleteAllViews();
        createSalaryTotalsView(stmt);
        createContractorAdminSalaryView(stmt);
        createPermanentAdminSalaryView(stmt);
        createContractorTeachingSalaryView(stmt);
        createPermanentTeachingSalaryView(stmt);
    }

    public void deleteAllViews()throws SQLException {
        Statement stmt;
        String sql;
        stmt = connection.createStatement();

        sql = " DROP VIEW IF EXISTS SalaryTotals";
        stmt.executeUpdate(sql);

        sql = " DROP VIEW IF EXISTS ContractorAdminSalaryTotals";
        stmt.executeUpdate(sql);

        sql = " DROP VIEW IF EXISTS PermanentAdminSalaryTotals";
        stmt.executeUpdate(sql);

        sql = " DROP VIEW IF EXISTS ContractorTeachingSalaryTotals";
        stmt.executeUpdate(sql);

        sql = " DROP VIEW IF EXISTS PermanentTeachingSalaryTotals";
        stmt.executeUpdate(sql);

    }


    private void createSalaryTotalsView(Statement stmt) throws SQLException {
        String sql;
        sql = "CREATE VIEW SalaryTotals as  " +
                "Select  " +
                "st.sid , st.first_name , st.last_name, st.marriage_status , st.iban, st.bank_name," +
                "st.hire_year , st.hire_month, st.phone, st.department , st.staff_type, st.address," +
                "sal.date_month , sal.date_year , sal.salary " +
                "FROM STAFF as st " +
                "INNER JOIN " +
                "SALARY_DEPOSIT_DATA as sal " +
                "ON st.sid = sal.sid";
        stmt.executeUpdate(sql);
    }


    private void createContractorAdminSalaryView(Statement stmt) throws SQLException {
        String sql;
        sql = "CREATE VIEW ContractorAdminSalaryTotals as  " +
                "Select  " +
                    "st.sid , st.first_name , st.last_name, st.marriage_status , st.iban, st.bank_name," +
                    "st.hire_year , st.hire_month, st.phone, st.department , st.staff_type, st.address," +

                    "sal.date_month , sal.date_year , sal.salary as Total_Salary," +

                    "con.contract_salary," +

                    "casal.family_support " +

                "FROM STAFF as st " +

                    "INNER JOIN SALARY_DEPOSIT_DATA as sal " +
                    "ON st.sid = sal.sid " +

                    "INNER JOIN CONTRACTOR_ADMIN_SD as casal " +
                    "ON sal.depositid = casal.depositid " +

                    "INNER JOIN CONTRACT as con " +
                    "On st.sid = con.sid " +

                "where st.department='Administrator' and st.staff_type = 'Contractor' "
        ;
        stmt.executeUpdate(sql);
    }

    private void createPermanentAdminSalaryView(Statement stmt) throws SQLException {
        String sql;
        sql = "CREATE VIEW PermanentAdminSalaryTotals as  " +
                "Select  " +
                "st.sid , st.first_name , st.last_name, st.marriage_status , st.iban, st.bank_name," +
                "st.hire_year , st.hire_month, st.phone, st.department , st.staff_type, st.address," +
                "sal.date_month , sal.date_year , sal.salary as Total_Salary," +

                "pasal.basic_set_income , pasal.family_support , pasal.years_worked_bonus " +

                "FROM STAFF as st " +

                "INNER JOIN SALARY_DEPOSIT_DATA as sal " +
                "ON st.sid = sal.sid " +

                "INNER JOIN PERMANENT_ADMIN_SD as pasal " +
                "ON sal.depositid = pasal.depositid " +

                "where st.department='Administrator' and st.staff_type = 'Permanent' "
        ;
        stmt.executeUpdate(sql);
    }

    private void createContractorTeachingSalaryView(Statement stmt) throws SQLException {
        String sql;
        sql = "CREATE VIEW ContractorTeachingSalaryTotals as  " +
                "Select  " +
                "st.sid , st.first_name , st.last_name, st.marriage_status , st.iban, st.bank_name," +
                "st.hire_year , st.hire_month, st.phone, st.department , st.staff_type, st.address," +

                "sal.date_month , sal.date_year , sal.salary as Total_Salary," +

                "con.contract_salary," +

                "ctsal.family_support , ctsal.library_grant " +

                "FROM STAFF as st " +

                "INNER JOIN SALARY_DEPOSIT_DATA as sal " +
                "ON st.sid = sal.sid " +

                "INNER JOIN CONTRACTOR_TEACHING_SD as ctsal " +
                "ON sal.depositid = ctsal.depositid " +

                "INNER JOIN CONTRACT as con " +
                "On st.sid = con.sid " +

                "where st.department='Teaching' and st.staff_type = 'Contractor' "
        ;
        stmt.executeUpdate(sql);
    }

    private void createPermanentTeachingSalaryView(Statement stmt) throws SQLException {
        String sql;
        sql = "CREATE VIEW PermanentTeachingSalaryTotals as  " +
                "Select  " +
                "st.sid , st.first_name , st.last_name, st.marriage_status , st.iban, st.bank_name," +
                "st.hire_year , st.hire_month, st.phone, st.department , st.staff_type, st.address," +

                "sal.date_month , sal.date_year , sal.salary as Total_Salary," +

                "ptsal.basic_set_income, ptsal.family_support , ptsal.research_grant , ptsal.years_worked_bonus " +

                "FROM STAFF as st " +

                "INNER JOIN SALARY_DEPOSIT_DATA as sal " +
                "ON st.sid = sal.sid " +

                "INNER JOIN PERMANENT_TEACHING_SD as ptsal " +
                "ON sal.depositid = ptsal.depositid " +


                "where st.department='Teaching' and st.staff_type = 'Permanent' "
        ;
        stmt.executeUpdate(sql);
    }


}
