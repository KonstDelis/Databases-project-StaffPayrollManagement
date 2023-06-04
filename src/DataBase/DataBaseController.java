package DataBase;

import java.sql.*;

public class DataBaseController {
    private Connection con;
    public Statement stmt, stmt2, stmt3;
    public int family_support_percentage,years_worked_percentage,basic_salary_admin,basic_salary_teaching,research_grant,library_grant;
    public static String DepartmentAdmin = "Administrator", DepartmentTeaching = "Teaching",
            TypePermanent = "Permanent", TypeContractor = "Contractor";

    public static int boolean_to_int(boolean b) {
        return (b) ? 1 : 0;
    }

    /**
     * Private method used by newContractorStaff() and newPermanentStaff() to create new staff
     */
    private void newStaff(String SID, String FNAME, String LNAME, boolean MARRIAGE, String IBAN,
                                         String BankName, int HireYear, int HireMonth, String Phone,
                                         String Department, String Type, String Address) throws SQLException
    {
        if(Department.equalsIgnoreCase(DepartmentAdmin) && Department.equalsIgnoreCase(DepartmentTeaching)){
            System.err.println("Department entered is wrong, make sure departemnt is either 'Administrator' or 'Teaching'");
            return;
        }
        if(Type.equalsIgnoreCase(TypeContractor) && Type.equalsIgnoreCase(TypePermanent)){
            System.err.println("Type entered is wrong, make sure Type is either 'Contractor' or 'Permanent'");
            return;
        }
        if(HireMonth<1 || HireMonth>12){
            System.err.println("Month entered is incorrect, make sure it is a number between 1 and 12");
            return;
        }
        String insertStaffStr =
                "INSERT INTO STAFF "+
                        "VALUES ('"+ SID.strip() +"', " +
                        "'"+ FNAME.strip() +"', " +
                        "'"+LNAME.strip()+"', " +
                        ""+boolean_to_int(MARRIAGE)+", " +
                        "'"+IBAN.strip()+"', " +
                        "'" +BankName.strip()+"', " +
                        ""+HireYear+", " +
                        ""+HireMonth+", " +
                        "'"+Phone.strip()+"', " +
                        "'"+Department.strip()+"', " +
                        "'"+Type.strip()+"'"+", " +
                        "'"+Address.strip()+"')";

        stmt.executeUpdate(insertStaffStr);
    }

    /**
     * Private method used by newContractorStaff() to create contract
     */
    private void newContract(String Sid, String ContractID, int Contract_salary, int Contract_start_month,
                             int Contract_start_year, int Contract_end_month, int Contract_end_year) throws SQLException {
        String createContract = "INSERT INTO CONTRACT " +
                "VALUES ('"+ContractID+"', '"+Sid+"',"+Contract_salary+", "+Contract_start_year+", " +
                ""+Contract_start_month+", "+Contract_end_year+", "+Contract_end_month+" ) ";
        stmt.executeUpdate(createContract);
    }

    /**
     * Creates new child
     */
    public void newChild(String SID, String ChildID, int Age) throws SQLException {
        String addChild = "INSERT INTO CHILD " +
                "VALUES('"+ChildID+"', '"+SID+"', "+Age+" )";
        stmt.executeUpdate(addChild);
    }

    /**
     * Returns the number of children of the staff that are under 18
     */
    public int getUnderageChildrenNumber(String SID) throws SQLException {
        int childrenNumber=0;
        String getChildren = "SELECT * FROM CHILD " +
                "WHERE sid='"+SID+"';";
        ResultSet result = stmt.executeQuery(getChildren);

        while(result.next()){
            if(result.getInt("Age")<=18){
                childrenNumber++;
            }
        }
        result.close();
        return childrenNumber;
    }

    /**
     * Creates new permanent staff
     */
    public void newPermanentStaff(String SID, String FNAME, String LNAME, boolean MARRIAGE, String IBAN,
                          String BankName, int HireYear, int HireMonth, String Phone,
                          String Department, String Address) throws SQLException
    {
        newStaff(SID, FNAME, LNAME, MARRIAGE, IBAN, BankName, HireYear, HireMonth, Phone, Department, TypePermanent, Address);
    }

    /**
     * Creates new contractor staff and his contract
     */
    public void newContractorStaff(String SID, String FNAME, String LNAME, boolean MARRIAGE, String IBAN,
                                   String BankName, int HireYear, int HireMonth, String Phone,
                                   String Department, String Address, String ContractID, int Contract_salary, int Contract_start_month,
                                    int Contract_start_year, int Contract_end_month, int Contract_end_year) throws SQLException
    {
        if(Contract_salary<0) {
            System.err.println("Salary cannot be negative");
            return;
        }
        if(Contract_end_month<1 || Contract_end_month>12 || Contract_start_month<1 || Contract_start_month>12){
            System.err.println("Contract month entered is incorrect, make sure it is a number between 1 and 12");
            return;
        }
        if(Contract_start_year>Contract_end_year || (Contract_start_year==Contract_end_year && Contract_start_month>Contract_end_month)) {
            System.err.println("Contract dates are incorrect, contract end date should be after contract start date");
            return;
        }

        newStaff(SID, FNAME, LNAME, MARRIAGE, IBAN, BankName, HireYear, HireMonth, Phone, Department, TypeContractor, Address);
        newContract(SID, ContractID, Contract_salary, Contract_start_month, Contract_start_year, Contract_end_month, Contract_end_year);
    }

    /**
     * Returns a resultSet that contains all info of the staff
     */
    public ResultSet getStaffInfo(String SID) throws SQLException {
        String getStaff = "SELECT * FROM STAFF " +
                "WHERE sid='"+SID+"';";
        ResultSet result = stmt.executeQuery(getStaff);
        return result;
    }

    /**
     * Changes the constants used to calculate salaries (decreasing them is not allowed)
     */
    public void changeSalaryConstants(int new_family_support_percentage, int new_years_worked_percentage, int new_basic_salary_admin,
                                      int new_basic_salary_teaching, int new_research_grant, int new_library_grant) throws SQLException {
        updateConstants();
        if(new_family_support_percentage<family_support_percentage || new_basic_salary_admin<basic_salary_admin ||
        new_basic_salary_teaching<basic_salary_teaching || new_years_worked_percentage<years_worked_percentage ||
        new_research_grant<research_grant || new_library_grant<library_grant){
            System.err.println("Decreasing grants and salary bonuses is not allowed");
            return;
        }
        String changeConstants = "UPDATE CONSTANTS SET " +
                " family_support_percentage = "+new_family_support_percentage+" , "+
                " years_worked_percentage = "+new_years_worked_percentage+", "+
                " basic_set_income_admin = "+new_basic_salary_admin+", "+
                " basic_set_income_teaching = "+new_basic_salary_teaching+" , "+
                " library_grant = "+new_library_grant+" , "+
                " research_grant = "+new_research_grant+" "+
                "WHERE conid='1'";
        stmt.executeUpdate(changeConstants);
        updateConstants();
    }

    /**
     * Changes all fields of the Staff
     */
    public void changeStaffInfo(String SID, String FNAME, String LNAME, boolean MARRIAGE, String IBAN,
                                String BankName, int HireYear, int HireMonth, String Phone,
                                String Department, String Type, String Address) throws SQLException
    {
        String changeInfo = "UPDATE STAFF SET " +
                "first_name = '"+FNAME.strip()+"', " +
                "last_name = '"+LNAME.strip()+"', " +
                "marriage_status = "+boolean_to_int(MARRIAGE)+", " +
                "iban = '"+IBAN.strip()+"', " +
                "bank_name = '"+BankName.strip()+"', " +
                "hire_year = "+HireYear+", " +
                "hire_month = "+HireMonth+", " +
                "phone = '"+Phone.strip()+"', " +
                "department = '"+Department.strip()+"', " +
                "staff_type = '"+Type.strip()+"', " +
                "address = '"+Address.strip()+"' " +
                "WHERE sid='"+SID.strip()+"'";

        stmt.executeUpdate(changeInfo);
    }

    /**
     * Prints all the columns and rows of the resultSet
     */
    public static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(rsmd.getColumnName(i) + "=" + columnValue);
            }
            System.out.println();
        }
    }
    private static String depositsToString(ResultSet resultSet) throws SQLException{
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        String result = "<html>----------------------------------------------------------------<br>";
        while (resultSet.next()) {
            result += "Transaction Date: "+resultSet.getString("date_month")+"/"+resultSet.getString("date_year")+"<br>";
            result += "Name: "+resultSet.getString("staff_first_name")+" "+resultSet.getString("staff_last_name")+"<br>";
            result += "DepositID: "+resultSet.getString("depositid")+"<br>";
            result += "Salary: "+resultSet.getString("salary")+"<br>";
            result +="----------------------------------------------------------------<br>";
        }
        result+="</html>";
        return result;
    }
    /**
     * Called before calculating salaries to make sure that private global variables are up-to-date with values from database
     */
    public void updateConstants() throws SQLException {
        String queryConstants = " SELECT * " +
                " FROM CONSTANTS"+
                " WHERE conid='1'";
        ResultSet constants_rs = stmt.executeQuery(queryConstants);
        constants_rs.next();
        //Initialize global variables for the constants used to calculate the salary
        family_support_percentage = constants_rs.getInt("family_support_percentage");
        years_worked_percentage = constants_rs.getInt("years_worked_percentage");
        basic_salary_admin = constants_rs.getInt("basic_set_income_admin");
        basic_salary_teaching = constants_rs.getInt("basic_set_income_teaching");
        library_grant = constants_rs.getInt("library_grant");
        research_grant = constants_rs.getInt("research_grant");
        constants_rs.close();
    }

    /**
     * Private method used by payStaff
     * @return the years between date_hired and date_now
     */
    private static int count_years(int now_month, int now_year, int hire_month, int hire_year){
        int years=0;
        years+= (now_year - hire_year)-1;
        if(now_month>=hire_month)
            years++;
        if(years==0)
            years=1;
        return years;
    }

    /**
     * Internal method used for checking
     * Find Category of salary
     * @return 1-if PermanentAdmin, 2-if PermanentTeaching, 3-if ContractorAdmin, 4-if ContractorTeaching, negative number-if none of the previous
     */
    private int getSalaryCategory(String Type, String Department){
        if(Type.equalsIgnoreCase(TypePermanent) && Department.equalsIgnoreCase(DepartmentAdmin)) {
            return 1;
        } else if (Type.equalsIgnoreCase(TypePermanent) && Department.equalsIgnoreCase(DepartmentTeaching)) {
            return 2;
        } else if (Type.equalsIgnoreCase(TypeContractor) && Department.equalsIgnoreCase(DepartmentAdmin)) {
            return 3;
        } else if (Type.equalsIgnoreCase(TypeContractor) && Department.equalsIgnoreCase(DepartmentTeaching)) {
            return 4;
        }else{
            return -1;
        }
    }

    /**
     * Private method used by retire_fire_staff() and payAllStaff(), inserts to the database a salary_deposit_data record
     * and a salary_data record (according to his type and department)
     */
    private void payStaff(String SID, String Fname, String Lname, int deposit_month, int deposit_year, int children_number,
                          String Type, String Department, int hire_year, int hire_month, int married) throws SQLException {
        int salary_category = getSalaryCategory(Type, Department);
        if(salary_category<=0){
            System.err.println("Department or Type are invalid, no such staff category exists");
            return;
        }
        if( (deposit_year<hire_year || (deposit_year==hire_year && deposit_month<hire_month)) && (salary_category==1 || salary_category==2)){
            return;
        }
        String depositID = SID+"_deposit_"+deposit_month+"_"+deposit_year;
        String salaryData = null;
        int family_support, years_worked_bonus, years_worked, salary=0;
        int contract_start_month, contract_start_year, contract_end_month, contract_end_year, contract_salary;
        String contract_id; ResultSet rs;

        switch (salary_category){
            case 1: //PermanentAdmin
                years_worked=count_years(deposit_month, deposit_year, hire_month, hire_year);
                years_worked_bonus=(int)(basic_salary_admin*(years_worked_percentage/(float)100*years_worked));
                family_support=(int)(basic_salary_admin*(family_support_percentage/(float)100*(children_number+married)));
                salary = basic_salary_admin+years_worked_bonus+family_support;
                salaryData = "INSERT INTO PERMANENT_ADMIN_SD " +
                        " VALUES( '"+depositID+"', "+family_support+", "+basic_salary_admin+", "+years_worked_bonus+")";
                break;

            case 2: //PermanentTeaching
                years_worked=count_years(deposit_month, deposit_year, hire_month, hire_year);
                years_worked_bonus=(int)(basic_salary_teaching*(years_worked_percentage/(float)100*years_worked));
                family_support=(int)(basic_salary_teaching*(family_support_percentage/(float)100*(children_number+married)));
                salary = basic_salary_teaching+years_worked_bonus+family_support;
                salaryData = "INSERT INTO PERMANENT_TEACHING_SD " +
                        " VALUES( '"+depositID+"', "+research_grant+", "+family_support+", "+basic_salary_admin+", "+years_worked_bonus+" )";
                break;

            case 3: //ContractorAdmin
                String getContract = "SELECT * FROM CONTRACT WHERE sid='"+SID+"'";
                rs = stmt.executeQuery(getContract);
                if(rs.next()){
                    contract_start_month = rs.getInt("contract_start_month");
                    contract_start_year = rs.getInt("contract_start_year");
                    contract_end_month = rs.getInt("contract_end_month");
                    contract_end_year = rs.getInt("contract_end_year");
                    contract_salary = rs.getInt("contract_salary");
                    contract_id = rs.getString("contractid");
                }
                else{
                    rs.close();
                    System.err.println("Contractor staff has no contract");
                    return;
                }
                rs.close();
                if(deposit_year<contract_start_year || (deposit_year==contract_start_year && deposit_month<contract_start_month) ||
                    deposit_year>contract_end_year || (deposit_year==contract_end_year && deposit_month>contract_end_month)){
                    return;
                }
                family_support=(int)(contract_salary*(family_support_percentage/(float)100*(children_number+married)));
                salary = contract_salary+family_support;
                salaryData = "INSERT INTO CONTRACTOR_ADMIN_SD " +
                        " VALUES( '"+depositID+"', '"+contract_id+"', "+family_support+" )";
                break;

            case 4: //ContractorTeaching
                String getContrat = "SELECT * FROM CONTRACT WHERE sid='"+SID+"'";
                rs = stmt.executeQuery(getContrat);
                if(rs.next()){
                    contract_start_month = rs.getInt("contract_start_month");
                    contract_start_year = rs.getInt("contract_start_year");
                    contract_end_month = rs.getInt("contract_end_month");
                    contract_end_year = rs.getInt("contract_end_year");
                    contract_salary = rs.getInt("contract_salary");
                    contract_id = rs.getString("contractid");
                }
                else{
                    rs.close();
                    System.err.println("Contractor staff has no contract");
                    return;
                }
                rs.close();
                if(deposit_year<contract_start_year || (deposit_year==contract_start_year && deposit_month<contract_start_month) ||
                        deposit_year>contract_end_year || (deposit_year==contract_end_year && deposit_month>contract_end_month))
                    return;
                family_support=(int)(contract_salary*(family_support_percentage/(float)100*(children_number+married)));
                salary = contract_salary+family_support+library_grant;
                salaryData = "INSERT INTO CONTRACTOR_TEACHING_SD " +
                        " VALUES( '"+depositID+"', '"+contract_id+"', "+family_support+", "+library_grant+" )";
                break;

            default:
                System.err.println("Unexpected error in switch, method:payStaff(...), file:DataBaseController.java");
        }
        String newDeposit = "INSERT INTO SALARY_DEPOSIT_DATA " +
                "VALUES('"+depositID+"', '"+Fname+"', '"+Lname+"', "+deposit_month+", "+deposit_year+", "+salary+", '"+SID+"' )";

        stmt.executeUpdate(newDeposit);
        stmt.executeUpdate(salaryData);
    }

    /**
     * Pays all staff for a specific month, returns formatted string with all transactions that took place
     */
    public String payAllStaff(int month, int year) throws SQLException {
        updateConstants();
        String getAllStaff = "SELECT * FROM STAFF WHERE sid NOT IN " +
                "(SELECT DISTINCT sid from SALARY_DEPOSIT_DATA " +
                "WHERE date_month="+month+" AND " +
                "date_year="+year+")";
        ResultSet staff = stmt2.executeQuery(getAllStaff);
        while(staff.next()){
            String SID = staff.getString("sid");
            String fname = staff.getString("first_name");
            String lname = staff.getString("last_name");
            String type = staff.getString("staff_type");
            String department = staff.getString("department");
            int hire_month = staff.getInt("hire_month");
            int hire_year = staff.getInt("hire_year");
            int children = getUnderageChildrenNumber(SID);
            boolean marriage = staff.getBoolean("marriage_status");
            payStaff(SID, fname, lname, month, year, children, type, department, hire_year, hire_month, boolean_to_int(marriage));
        }
        staff.close();
        ResultSet rs = getDeposit(month, year);
        return depositsToString(rs);
    }

    /**
     * Returns ResultSet of salary_deposit_data for a specific month
     */
    public ResultSet getDeposit(int month, int year) throws SQLException {
        String getDepositData = "SELECT * FROM SALARY_DEPOSIT_DATA WHERE date_month="+month+" AND date_year="+year+"";
        return stmt.executeQuery(getDepositData);
    }

    /**
     * Fires/retires a staff by removing anything that has to do with him from all tables, after giving his last salary
     * @return Returns ResultSet with everything about his last salary, since everything about him has been deleted
     * from the database
     */
    public String fire_retire_staff(String SID, int last_payment_month, int last_payment_year) throws SQLException {
        String getStaff = "SELECT * FROM STAFF WHERE sid='"+SID+"'";
        ResultSet staff = stmt2.executeQuery(getStaff);
        String type, department;
        if(staff.next()){
            String fname = staff.getString("first_name");
            String lname = staff.getString("last_name");
            type = staff.getString("staff_type");
            department = staff.getString("department");
            int hire_month = staff.getInt("hire_month");
            int hire_year = staff.getInt("hire_year");
            int children = getUnderageChildrenNumber(SID);
            boolean marriage = staff.getBoolean("marriage_status");
            staff.close();
            payStaff(SID, fname, lname, last_payment_month, last_payment_year, children, type, department, hire_year, hire_month, boolean_to_int(marriage));
        }
        else{
            staff.close();
            return null;
        }
        int salary_category = getSalaryCategory(type, department);
        String sd_type ="";
        if(salary_category==1){
            sd_type = "permanent_admin_sd";
        } else if (salary_category==2) {
            sd_type = "permanent_teaching_sd";
        } else if (salary_category==3) {
            sd_type = "contractor_admin_sd";
        } else if (salary_category==4) {
            sd_type = "contractor_teaching_sd";
        }else {
            System.err.println("Incorrect staff type or department");
        }
        String getLastPayment = "SELECT * FROM SALARY_DEPOSIT_DATA INNER JOIN "+sd_type+" " +
                "ON SALARY_DEPOSIT_DATA.depositid = "+sd_type+".depositid "+
                "WHERE " +
                "SALARY_DEPOSIT_DATA.sid = '"+SID+"' AND " +
                "SALARY_DEPOSIT_DATA.date_month="+last_payment_month+" AND " +
                "SALARY_DEPOSIT_DATA.date_year="+last_payment_year+"";
        ResultSet last_payment = stmt3.executeQuery(getLastPayment);
        String lastPay = formatFireResultSet(last_payment);
        String deleteStaff = "DELETE FROM STAFF WHERE sid='"+SID+"'";
        stmt.executeUpdate(deleteStaff);

        return lastPay;
    }
    public static String formatFireResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        rs.next();
        String str = "<html>------------------------LAST_PAYMENT------------------------<br>";
        for (int i = 1; i <= columnsNumber; i++) {
            if(i==8||i==7) continue;
            String columnValue = rs.getString(i);
            String title = rsmd.getColumnName(i);
            str += title.substring(0, 1).toUpperCase() + title.substring(1)+ ": " + columnValue+"<br>";
        }
        str+="------------------------------------------------------------</html>";
        return str;
    }
    /**
     * Constructor: establishes connection to database and gets constants needed for salary from the database
     */
    public DataBaseController(){
        con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String url = new String("jdbc:mysql://localhost");
        String databaseName = new String("test");
        int port = 3306;
        String username = new String("root");
        String password = new String("");
        try {
            con = DriverManager.getConnection(
                    url + ":" + port + "/" + databaseName + "?characterEncoding=UTF-8", username, password);
            stmt = con.createStatement();
            stmt2 = con.createStatement();
            stmt3 = con.createStatement();
            System.out.println("Connected to database");

            updateConstants();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void finalize(){
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Closing connection");
    }

    public boolean SIDexists(String sid) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM STAFF WHERE sid='"+sid+"' ");
        return rs.next();
    }

    /////////////
    public DataBaseController(Connection connection) throws SQLException {
        useConnection(connection);
    }
    public void useConnection (Connection connection) throws SQLException {

        if (this.con != null) {
            return;
        }

        // We release the previous connection if any
        if (this.con != null) {
            this.con.close();
            this.con = null;
        }
        if (connection == null) {
            connection = DBConnection.getInstance().getConnection();
        }
        this.con = connection;
        basicInit();
    }

    private void basicInit() throws SQLException {
        stmt = con.createStatement();
        stmt2 = con.createStatement();
        stmt3 = con.createStatement();

        System.out.println("Connected to database");
        // updateConstants();
    }

}
