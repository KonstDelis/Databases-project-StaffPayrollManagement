package DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreator {

    private Connection connection;

    public void useConnection(Connection connection ) {
        this.connection = connection;
    }

    public void createDatabaseTables() throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();

        String createStaffTable =
                " CREATE TABLE IF NOT EXISTS STAFF"+
                        " (sid VARCHAR(100) not NULL, "+
                        " first_name VARCHAR(100), "+
                        " last_name VARCHAR(100), "+
                        " marriage_status BOOL, "+
                        " iban VARCHAR(34), "+
                        " bank_name VARCHAR(100), "+
                        " hire_year SMALLINT, "+
                        " hire_month TINYINT, "+
                        " phone VARCHAR(15), "+
                        " department VARCHAR(100), "+
                        " staff_type VARCHAR(100), "+
                        " address VARCHAR(100), "+
                        " PRIMARY KEY(sid) )";
        stmt.executeUpdate(createStaffTable);

        String createChildTable =
                " CREATE TABLE IF NOT EXISTS  CHILD"+
                        " (childid VARCHAR(100) not NULL, "+
                        " sid VARCHAR(100) not NULL, "+
                        " age SMALLINT, "+
                        " PRIMARY KEY(childid), "+
                        " FOREIGN KEY(sid) REFERENCES STAFF(sid) ON UPDATE CASCADE ON DELETE CASCADE)";
        stmt.executeUpdate(createChildTable);

        String createContractTable =
                " CREATE TABLE IF NOT EXISTS CONTRACT"+
                        " (contractid VARCHAR(100) not NULL, "+
                        " sid VARCHAR(100) not NULL, "+
                        " contract_salary INT, "+
                        " contract_start_year SMALLINT, "+
                        " contract_start_month TINYINT, "+
                        " contract_end_year SMALLINT, "+
                        " contract_end_month TINYINT, "+
                        " PRIMARY KEY(contractid), " +
                        " FOREIGN KEY(sid) REFERENCES STAFF(sid) ON UPDATE CASCADE ON DELETE CASCADE) ";
        stmt.executeUpdate(createContractTable);

        String createSalaryDepositData =
                " CREATE TABLE IF NOT EXISTS SALARY_DEPOSIT_DATA"+
                        "(depositid VARCHAR(100) not NULL, "+
                        " staff_first_name VARCHAR(100) not NULL, "+
                        " staff_last_name VARCHAR(100) not NULL, "+
                        " date_month TINYINT , "+
                        " date_year SMALLINT , "+
                        " salary INT, "+
                        " sid VARCHAR(100) not NULL, "+
                        " PRIMARY KEY(depositid), "+
                        " FOREIGN KEY(sid) REFERENCES STAFF(sid) ON UPDATE CASCADE ON DELETE CASCADE)";

        stmt.executeUpdate(createSalaryDepositData);

        String contractorTeachingSD =
                " CREATE TABLE IF NOT EXISTS CONTRACTOR_TEACHING_SD"+
                        " (depositid VARCHAR(100) not NULL, "+
                        " contractid VARCHAR(100) not NULL, "+
                        " family_support INT, "+
                        " library_grant INT, "+
                        " PRIMARY KEY(depositid), " +
                        " FOREIGN KEY(contractid) REFERENCES CONTRACT(contractid) ON UPDATE CASCADE ON DELETE CASCADE, "+
                        " FOREIGN KEY(depositid) REFERENCES SALARY_DEPOSIT_DATA(depositid) ON UPDATE CASCADE ON DELETE CASCADE)";
        stmt.executeUpdate(contractorTeachingSD);

        String contractorAdminSD =
                " CREATE TABLE IF NOT EXISTS CONTRACTOR_ADMIN_SD"+
                        " (depositid VARCHAR(100) not NULL, "+
                        " contractid VARCHAR(100) not NULL, "+
                        " family_support INT, "+
                        " PRIMARY KEY(depositid), " +
                        " FOREIGN KEY(depositid) REFERENCES SALARY_DEPOSIT_DATA(depositid) ON UPDATE CASCADE ON DELETE CASCADE, "+
                        " FOREIGN KEY(contractid) REFERENCES CONTRACT(contractid) ON UPDATE CASCADE ON DELETE CASCADE) ";
        stmt.executeUpdate(contractorAdminSD);

        String createPermanentTeachingStaffSalaryData =
                " CREATE TABLE IF NOT EXISTS PERMANENT_TEACHING_SD"+
                        "(depositid VARCHAR(100) not NULL, "+
                        " research_grant INTEGER , "+
                        " family_support INTEGER , "+
                        " basic_set_income INTEGER , "+
                        " years_worked_bonus INTEGER , "+
                        " PRIMARY KEY(depositid), "+
                        " FOREIGN KEY(depositid) REFERENCES SALARY_DEPOSIT_DATA(depositid) ON UPDATE CASCADE ON DELETE CASCADE)";

        stmt.executeUpdate(createPermanentTeachingStaffSalaryData);

        String createPermanentAdministrativeStaffSalaryData =
                " CREATE TABLE IF NOT EXISTS PERMANENT_ADMIN_SD"+
                        "(depositid VARCHAR(100) not NULL, "+
                        " family_support INTEGER , "+
                        " basic_set_income INTEGER , "+
                        " years_worked_bonus INTEGER , "+
                        " PRIMARY KEY(depositid), "+
                        " FOREIGN KEY(depositid) REFERENCES SALARY_DEPOSIT_DATA(depositid) ON UPDATE CASCADE ON DELETE CASCADE)";

        stmt.executeUpdate(createPermanentAdministrativeStaffSalaryData);

        String createConstants =
                " CREATE TABLE IF NOT EXISTS CONSTANTS"+
                        "(conid VARCHAR(100) not NULL, "+
                        " family_support_percentage INTEGER , "+
                        " years_worked_percentage INTEGER , "+
                        " basic_set_income_admin INTEGER , "+
                        " basic_set_income_teaching INTEGER , "+
                        " library_grant INTEGER , "+
                        " research_grant INTEGER , "+
                        " PRIMARY KEY(conid))";

        stmt.executeUpdate(createConstants);

        String initConstants = "INSERT INTO CONSTANTS " +
                "VALUES( '1', 5, 15, 2000, 3000, 500, 1000 )";
        try {
            stmt.executeUpdate(initConstants);
        } catch (SQLException e) {
            // we ignore it
        }

        stmt.close();
    }

    public void cleanAll() throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();

        String dropPATable = " DROP TABLE IF EXISTS PERMANENT_ADMIN_SD";
        stmt.executeUpdate(dropPATable);
        String dropPTTable = " DROP TABLE IF EXISTS PERMANENT_TEACHING_SD";
        stmt.executeUpdate(dropPTTable);
        String dropCATable = " DROP TABLE IF EXISTS CONTRACTOR_ADMIN_SD";
        stmt.executeUpdate(dropCATable);
        String dropCTTable = " DROP TABLE IF EXISTS CONTRACTOR_TEACHING_SD";
        stmt.executeUpdate(dropCTTable);

        String dropChildTable = " DROP TABLE IF EXISTS CHILD";
        stmt.executeUpdate(dropChildTable);
        String dropContractTable = " DROP TABLE IF EXISTS CONTRACT";
        stmt.executeUpdate(dropContractTable);
        String dropDepositTable = " DROP TABLE IF EXISTS SALARY_DEPOSIT_DATA";
        stmt.executeUpdate(dropDepositTable);
        String dropStaffTable = " DROP TABLE IF EXISTS STAFF";
        stmt.executeUpdate(dropStaffTable);
        String dropConstants = " DROP TABLE IF EXISTS CONSTANTS";
        stmt.executeUpdate(dropConstants);

        stmt.close();
    }

}
