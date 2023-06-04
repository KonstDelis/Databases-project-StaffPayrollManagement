package DataBase;

import POJOclasses.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class DBActions {

    private static HashMap<String, String> clToName;

    static {
        clToName = new HashMap<String, String>();
        clToName.put("depositid", "Deposit ID");
        clToName.put("total_sum", "Summarized Total");
        clToName.put("max_salary", "Max Salary");
        clToName.put("min_salary", "Min Salary");
        clToName.put("avg_salary", "Average Salary");
        clToName.put("sid", "SID");
        clToName.put("first_name", "First Name");
        clToName.put("last_name", "Last Name");
        clToName.put("staff_first_name", "First Name");
        clToName.put("staff_last_name", "Last Name");
        clToName.put("marriage_status", "Is Marriage");
        clToName.put("iban", "IBAN");
        clToName.put("bank_name", "Bank Name");
        clToName.put("hire_year", "Hire Date");
        clToName.put("date_year", "Date");
        clToName.put("phone", "Phone Number");
        clToName.put("department", "Department");
        clToName.put("staff_type", "Staff Type");
        clToName.put("address", "Address");
        clToName.put("salary", "Salary");
        clToName.put("total", "Total");
        clToName.put("Total_Salary", "Total Salary");
        clToName.put("contract_salary", "Contract Salary");
        clToName.put("library_grant", "Library Grant");
        clToName.put("basic_set_income", "Basic Set Salary");
        clToName.put("family_support", "Family Support");
        clToName.put("research_grant", "Research Grant");
        clToName.put("years_worked_bonus", "Years Experience Bonus");
    }


    private Connection connection;

    private DataBaseController controller;


    public DBActions() {

    }

    public void useConnection(Connection connection) throws SQLException {
        this.connection = connection;
        controller = new DataBaseController(this.connection);
    }

    public void useConnectionAndController(Connection connection , DataBaseController controller)  {
        this.connection = connection;
        this.controller = controller;
    }


    public void payStaff(int month, int year) throws SQLException {
        controller.payAllStaff(month, year);
    }

    public void createRandomStaff(int count) throws SQLException {
        InsertStaff(count, controller);
    }

    public void createRandomStaff(int count, int startId) throws SQLException {
        InsertStaff(count, startId, controller);
    }


    public void clearDatabaseAndViews() throws SQLException {
        DatabaseCreator tableCreator = new DatabaseCreator();
        ViewCreator viewCreator = new ViewCreator();
        viewCreator.useConnection(this.connection);
        viewCreator.deleteAllViews();
        tableCreator.useConnection(this.connection);
        tableCreator.cleanAll();
    }

    public void createTablesAndViews() throws SQLException {
        DatabaseCreator tableCreator = new DatabaseCreator();
        tableCreator.useConnection(this.connection);
        tableCreator.createDatabaseTables();
        ViewCreator viewCreator = new ViewCreator();
        viewCreator.useConnection(DBConnection.getInstance().getConnection());
        viewCreator.createViews();
    }


    public void InsertStaff(int count, DataBaseController controller) throws SQLException {
        InsertStaff(count, 1, controller);
    }

    public void InsertStaff(int count, int startId, DataBaseController controller) throws SQLException {
        new Random();
        int total = startId + count;
        for (int i = startId; i < total; ++i) {
            ArrayList<Child> childs = null;
            Contract contract = null;
            Staff staff = getBasicRandomStaff(i);
            if (staff.getStaffType().charAt(0) == 'C') {
                contract = getRandomContract(staff);
                controller.newContractorStaff(staff.getSid(), staff.getFirstName(), staff.getLastName(),
                        staff.getMarriageStatus(), staff.getIban(), staff.getBankName(), staff.getHireYear(),
                        staff.getHireMonth(), staff.getPhone(), staff.getDepartment(), staff.getAddress(),
                        contract.getContractid(), contract.getContractSalary(), contract.getContractStartMonth(),
                        contract.getContractStartYear(), contract.getContractEndMonth(), contract.getContractEndYear());
            } else {
                controller.newPermanentStaff(staff.getSid(), staff.getFirstName(), staff.getLastName(),
                        staff.getMarriageStatus(), staff.getIban(), staff.getBankName(), staff.getHireYear(),
                        staff.getHireMonth(), staff.getPhone(), staff.getDepartment(), staff.getAddress());
            }

            if (staff.getMarriageStatus()) {
                childs = getRandomChilds(staff);
            }

            if (childs != null) {
                for (int j = 0; j < childs.size(); ++j) {
                    Child ch = (Child) childs.get(j);
                    // System.out.println("CHILD " + ch.getSid() + ":" + ch.getChildid() +":" + ch.getAge());
                    controller.newChild(ch.getSid(), ch.getChildid(), ch.getAge());
                }
            }
        }

    }


    public String executeQuery(String sql) throws SQLException {

        Statement stmt = connection.createStatement();
        StringBuilder result = new StringBuilder();
        ResultSet resultSet = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        //result.append("<html>----------------------------------------------------------------<br>");
        // result.append("<hr style=\"border-left: 2px solid blue\">");
        while (resultSet.next()) {
            result.append("<table style=\"border-collapse: separate; border-color: black; border-style: solid; border-width: 1px;\">");
            for (int i = 1; i <= columnsNumber; i++) {
                // result.append("<tr>");
                String cln = rsmd.getColumnName(i);
                if (cln.equals("hire_year") || cln.equals("date_year")) {
                    result.append("<tr>");
                    result.append(getDate(resultSet, cln));
                } else if (cln.equals("hire_month") || cln.equals("date_month")) {
                    continue;
                } else {
                    result.append("<tr>");
                    String name = clToName.get(cln);
                    if (name != null) {
                        //result.append("<b>" + name + "</b>");
                        // result.append(name);
                        result.append("<td>" + name + "</td>");
                    } else {
                        result.append("--" + cln);
                    }
                    // result.append(":");
                    // result.append("<b>" + resultSet.getString(i) + "</b>");
                    result.append("<td>" + resultSet.getString(i) + "</td>");
                }
                //result.append("<br>");
                result.append("</tr>");
            }
            //result.append("----------------------------------------------------------------<br>");
            //result.append("<hr style=\"border-left: 2px solid blue\">");
            result.append("</table>");
        }
        //result.append("</html>");
        resultSet.close();
        stmt.close();
        return result.toString();
    }

    private String getDate(ResultSet rs, String cln) throws SQLException {
        String result = "";
        if (cln.equals("hire_year")) {
            //result = (clToName.get("hire_year") + ":" + rs.getString("hire_month") +
            //        "/" + rs.getString("hire_year"));
            result = ("<td>" + clToName.get("hire_year") + "</td><td>" + rs.getString("hire_month") + "/" + rs.getString("hire_year") + "</td>");


        } else if (cln.equals("date_year")) {
            //result = (clToName.get("date_year") + ":" + rs.getString("date_month") +
            //        "/" + rs.getString("date_year"));

            result = ("<td>" + clToName.get("date_year") + "</td><td>" + rs.getString("date_month") + "/" + rs.getString("date_year") + "</td>");


        }

        return result;
    }

    public String getAllStaffStr() throws SQLException {
        return executeQuery("Select * from STAFF");
    }


    public ArrayList<Staff> getAllStaff() throws SQLException {
        return getStaff("Select * from STAFF");
    }


    public ArrayList<Staff> getStaff(String sql) throws SQLException {
        // Note that the sql must be staff sql
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        ArrayList<Staff> result = new ArrayList<>();
        while (resultSet.next()) {
            Staff rec = new Staff();
            rec.setSid(resultSet.getString("sid"));
            rec.setFirstName(resultSet.getString("first_name"));
            rec.setLastName(resultSet.getString("last_name"));
            rec.setMarriageStatus(resultSet.getBoolean("marriage_status"));
            rec.setIban(resultSet.getString("iban"));
            rec.setBankName(resultSet.getString("bank_name"));
            rec.setHireYear(resultSet.getShort("hire_year"));
            rec.setHireMonth(resultSet.getShort("hire_month"));
            rec.setPhone(resultSet.getString("phone"));
            rec.setDepartment(resultSet.getString("department"));
            rec.setStaffType(resultSet.getString("staff_type"));
            rec.setAddress(resultSet.getString("address"));
            result.add(rec);
        }
        resultSet.close();
        stmt.close();
        return result;

    }


    public String getAllSalaryDepositDataStr() throws SQLException {
        return executeQuery("Select * from SALARY_DEPOSIT_DATA");
    }


    public ArrayList<SalaryDepositData> getAllSalaryDepositData() throws SQLException {
        return getSalaryDepositData("Select * from SALARY_DEPOSIT_DATA");
    }


    public ArrayList<SalaryDepositData> getSalaryDepositData(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        ArrayList<SalaryDepositData> result = new ArrayList<>();
        while (resultSet.next()) {
            SalaryDepositData rec = new SalaryDepositData();
            rec.setDepositid(resultSet.getString("depositid"));
            rec.setStaffFirstName(resultSet.getString("staff_first_name"));
            rec.setStaffLastName(resultSet.getString("staff_last_name"));
            rec.setDateMonth(resultSet.getInt("date_month"));
            rec.setDateYear(resultSet.getInt("date_year"));
            rec.setSalary(resultSet.getInt("salary"));
            rec.setSid(resultSet.getString("sid"));
            result.add(rec);
        }
        resultSet.close();
        stmt.close();
        return result;
    }


    public String getAllSalaryTotalsStr() throws SQLException {
        return executeQuery("Select * from SalaryTotals");
    }

    public String getAllSalaryTotalsStr(int month,  int year) throws SQLException {
        return executeQuery("Select * from SalaryTotals where date_month=" + month + " and date_year=" + year);
    }


    public ArrayList<SalaryTotals> getAllSalaryTotals() throws SQLException {
        return getSalaryTotals("Select * from SalaryTotals");
    }

    public String getMaxMinAvgSalaryTotalsStr() throws SQLException {
        String sql = "Select Max(salary) as max_salary , " + "MIN(salary) as min_salary , " +
                "AVG(salary) as avg_salary from  SalaryTotals";
        return executeQuery(sql);
    }

    public String getMaxMinAvgSalaryTotalsStr(int month , int year) throws SQLException {
        String sql = "Select Max(salary) as max_salary , " + "MIN(salary) as min_salary , " +
                "AVG(salary) as avg_salary from  SalaryTotals" +
                " where date_month=" + month + " and date_year=" + year;
        return executeQuery(sql);
    }

    public String getSalaryTotalValueStr() throws SQLException {
        String sql = "Select SUM(salary) as total_sum from SalaryTotals ";
        return executeQuery(sql);
    }

    public String getSalaryTotalValueStr(int month, int year) throws SQLException {
        String sql = "Select SUM(salary) as total from SalaryTotals where date_month=" + month + " and date_year=" + year;
        return executeQuery(sql);
    }



    public MaxMinAvg getMaxMinAvgSalaryTotals(int month, int year) throws SQLException {
        String sql = "Select Max(salary) as max_salary , " + "MIN(salary) as min_salary , AVG(salary) as avg_salary from  SalaryTotals" + " where date_month=" + month + " and date_year=" + year;

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        MaxMinAvg result = new MaxMinAvg();
        while (resultSet.next()) {
            result.max = resultSet.getInt("max_salary");
            result.min = resultSet.getInt("min_salary");
            result.avg = resultSet.getFloat("avg_salary");
        }
        return result;
    }


    public ArrayList<SalaryTotals> getSalaryTotals(String sql) throws SQLException {
        // Note that the sql must be staff sql
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        ArrayList<SalaryTotals> result = new ArrayList<>();
        while (resultSet.next()) {
            SalaryTotals rec = new SalaryTotals();
            rec.setSid(resultSet.getString("sid"));
            rec.setFirstName(resultSet.getString("first_name"));
            rec.setLastName(resultSet.getString("last_name"));
            rec.setMarriageStatus(resultSet.getBoolean("marriage_status"));
            rec.setIban(resultSet.getString("iban"));
            rec.setBankName(resultSet.getString("bank_name"));
            rec.setHireYear(resultSet.getShort("hire_year"));
            rec.setHireMonth(resultSet.getShort("hire_month"));
            rec.setPhone(resultSet.getString("phone"));
            rec.setDepartment(resultSet.getString("department"));
            rec.setStaffType(resultSet.getString("staff_type"));
            rec.setAddress(resultSet.getString("address"));
            rec.setDateMonth(resultSet.getInt("date_month"));
            rec.setDateYear(resultSet.getInt("date_year"));
            rec.setSalary(resultSet.getInt("salary"));
            result.add(rec);
        }
        resultSet.close();
        stmt.close();
        return result;

    }

    public ArrayList<PermanentTeachingSalaryTotals> getAllPermanentTeachingSalaryTotals() throws SQLException {
        return getPermanentTeachingSalaryTotals("Select * from PermanentTeachingSalaryTotals");
    }

    public String getAllPermanentTeachingSalaryTotalsStr() throws SQLException {
        return executeQuery("Select * from PermanentTeachingSalaryTotals");
    }

    public String getPermanentTeachingSalaryTotalsStr(int month, int year) throws SQLException {
        return executeQuery("Select * from PermanentTeachingSalaryTotals where date_month=" + month + " and date_year=" + year);
    }


    public ArrayList<PermanentTeachingSalaryTotals> getPermanentTeachingSalaryTotals(int month, int year) throws SQLException {
        return getPermanentTeachingSalaryTotals("Select * from PermanentTeachingSalaryTotals where date_month=" + month + " and date_year=" + year);
    }


    public MaxMinAvg getMaxMinAvgPermanentTeachingSalaryTotals() throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  PermanentTeachingSalaryTotals";

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        MaxMinAvg result = new MaxMinAvg();
        while (resultSet.next()) {
            result.max = resultSet.getInt("max_salary");
            result.min = resultSet.getInt("min_salary");
            result.avg = resultSet.getFloat("avg_salary");
        }
        return result;
    }

    public String getMaxMinAvgPermanentTeachingSalaryTotalsStr() throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  PermanentTeachingSalaryTotals";
        return executeQuery(sql);
    }

    public String getMaxMinAvgPermanentTeachingSalaryTotalsStr(int month, int year) throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  PermanentTeachingSalaryTotals" + " where date_month=" + month + " and date_year=" + year;
        return executeQuery(sql);
    }

    public MaxMinAvg getMaxMinAvgPermanentTeachingSalaryTotals(int month, int year) throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  PermanentTeachingSalaryTotals" + " where date_month=" + month + " and date_year=" + year;

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        MaxMinAvg result = new MaxMinAvg();
        while (resultSet.next()) {
            result.max = resultSet.getInt("max_salary");
            result.min = resultSet.getInt("min_salary");
            result.avg = resultSet.getFloat("avg_salary");
        }
        return result;
    }


    public String getPermanentTeachingSalaryTotalValueStr() throws SQLException {
        String sql = "Select SUM(Total_Salary) as total_sum from PermanentTeachingSalaryTotals ";
        return executeQuery(sql);
    }

    public String getPermanentTeachingSalaryTotalValueStr(int month, int year) throws SQLException {
        String sql = "Select SUM(Total_Salary) as total from PermanentTeachingSalaryTotals where date_month=" + month + " and date_year=" + year;
        return executeQuery(sql);
    }

    public int getPermanentTeachingSalaryTotalValue() throws SQLException {
        String sql = "Select SUM(Total_Salary) as total from PermanentTeachingSalaryTotals ";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt("total");
        }
        return result;
    }


    public int getPermanentTeachingSalaryTotalValue(int month, int year) throws SQLException {
        String sql = "Select SUM(Total_Salary) as total from PermanentTeachingSalaryTotals where date_month=" + month + " and date_year=" + year;
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt("total");
        }
        return result;
    }

    public ArrayList<PermanentTeachingSalaryTotals> getPermanentTeachingSalaryTotals(String sql) throws SQLException {
        // Note that the sql must be staff sql
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        ArrayList<PermanentTeachingSalaryTotals> result = new ArrayList<>();
        while (resultSet.next()) {
            PermanentTeachingSalaryTotals rec = new PermanentTeachingSalaryTotals();
            rec.setSid(resultSet.getString("sid"));
            rec.setFirstName(resultSet.getString("first_name"));
            rec.setLastName(resultSet.getString("last_name"));
            rec.setMarriageStatus(resultSet.getBoolean("marriage_status"));
            rec.setIban(resultSet.getString("iban"));
            rec.setBankName(resultSet.getString("bank_name"));
            rec.setHireYear(resultSet.getShort("hire_year"));
            rec.setHireMonth(resultSet.getShort("hire_month"));
            rec.setPhone(resultSet.getString("phone"));
            rec.setDepartment(resultSet.getString("department"));
            rec.setStaffType(resultSet.getString("staff_type"));
            rec.setAddress(resultSet.getString("address"));
            rec.setDateMonth(resultSet.getInt("date_month"));
            rec.setDateYear(resultSet.getInt("date_year"));
            rec.setSalary(resultSet.getInt("Total_salary"));
            rec.setBasicSetIncome(resultSet.getInt("basic_set_income"));
            rec.setFamilySupport(resultSet.getInt("family_support"));
            rec.setResearchGrant(resultSet.getInt("research_grant"));
            rec.setYearsWorkedBonus(resultSet.getInt("years_worked_bonus"));
            result.add(rec);
        }
        resultSet.close();
        stmt.close();
        return result;

    }


    public ArrayList<PermanentAdminSalaryTotals> getAllPermanentAdminSalaryTotals() throws SQLException {
        return getPermanentAdminSalaryTotals("Select * from PermanentAdminSalaryTotals");
    }

    public ArrayList<PermanentAdminSalaryTotals> getPermanentAdminSalaryTotals(int month, int year) throws SQLException {
        return getPermanentAdminSalaryTotals("Select * from PermanentAdminSalaryTotals where date_month=" + month + " and date_year=" + year);
    }

    public String getAllPermanentAdminSalaryTotalsStr() throws SQLException {
        return executeQuery("Select * from PermanentAdminSalaryTotals");
    }

    public String getPermanentAdminSalaryTotalsStr(int month, int year) throws SQLException {
        return executeQuery("Select * from PermanentAdminSalaryTotals where date_month=" + month + " and date_year=" + year);
    }

    public String getMaxMinAvgPermanentAdminSalaryTotalsStr() throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  PermanentAdminSalaryTotals";
        return executeQuery(sql);
    }

    public String getMaxMinAvgPermanentAdminSalaryTotalsStr(int month, int year) throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  PermanentAdminSalaryTotals" + " where date_month=" + month + " and date_year=" + year;
        return executeQuery(sql);
    }

    public MaxMinAvg getMaxMinAvgPermanentAdminSalaryTotals() throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  PermanentAdminSalaryTotals";

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        MaxMinAvg result = new MaxMinAvg();
        while (resultSet.next()) {
            result.max = resultSet.getInt("max_salary");
            result.min = resultSet.getInt("min_salary");
            result.avg = resultSet.getFloat("avg_salary");
        }
        return result;
    }

    public MaxMinAvg getMaxMinAvgPermanentAdminSalaryTotals(int month, int year) throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  PermanentAdminSalaryTotals" + " where date_month=" + month + " and date_year=" + year;

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        MaxMinAvg result = new MaxMinAvg();
        while (resultSet.next()) {
            result.max = resultSet.getInt("max_salary");
            result.min = resultSet.getInt("min_salary");
            result.avg = resultSet.getFloat("avg_salary");
        }
        return result;
    }

    public String getPermanentAdminSalaryTotalsValueStr() throws SQLException {
        String sql = "Select SUM(Total_Salary) as total_sum from PermanentAdminSalaryTotals ";
        return executeQuery(sql);
    }

    public String getPermanentAdminSalaryTotalsValueStr(int month, int year) throws SQLException {
        String sql = "Select SUM(Total_Salary) as total from PermanentAdminSalaryTotals where date_month=" + month + " and date_year=" + year;
        return executeQuery(sql);
    }

    public int getPermanentAdminSalaryTotalsValue() throws SQLException {
        String sql = "Select SUM(Total_Salary) as total from PermanentAdminSalaryTotals ";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt("total");
        }
        return result;
    }


    public int getPermanentAdminSalaryTotalsValue(int month, int year) throws SQLException {
        String sql = "Select SUM(Total_Salary) as total from PermanentAdminSalaryTotals where date_month=" + month + " and date_year=" + year;
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt("total");
        }
        return result;
    }

    public ArrayList<PermanentAdminSalaryTotals> getPermanentAdminSalaryTotals(String sql) throws SQLException {
        // Note that the sql must be staff sql
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        ArrayList<PermanentAdminSalaryTotals> result = new ArrayList<>();
        while (resultSet.next()) {
            PermanentAdminSalaryTotals rec = new PermanentAdminSalaryTotals();
            rec.setSid(resultSet.getString("sid"));
            rec.setFirstName(resultSet.getString("first_name"));
            rec.setLastName(resultSet.getString("last_name"));
            rec.setMarriageStatus(resultSet.getBoolean("marriage_status"));
            rec.setIban(resultSet.getString("iban"));
            rec.setBankName(resultSet.getString("bank_name"));
            rec.setHireYear(resultSet.getShort("hire_year"));
            rec.setHireMonth(resultSet.getShort("hire_month"));
            rec.setPhone(resultSet.getString("phone"));
            rec.setDepartment(resultSet.getString("department"));
            rec.setStaffType(resultSet.getString("staff_type"));
            rec.setAddress(resultSet.getString("address"));
            rec.setDateMonth(resultSet.getInt("date_month"));
            rec.setDateYear(resultSet.getInt("date_year"));
            rec.setSalary(resultSet.getInt("total_salary"));
            rec.setBasicSetIncome(resultSet.getInt("basic_set_income"));
            rec.setFamilySupport(resultSet.getInt("family_support"));
            rec.setYearsWorkedBonus(resultSet.getInt("years_worked_bonus"));
            result.add(rec);
        }
        resultSet.close();
        stmt.close();
        return result;

    }


    public ArrayList<ContractorTeachingSalaryTotals> getAllContractorTeachingSalaryTotals() throws SQLException {
        return getContractorTeachingSalaryTotals("Select * from ContractorTeachingSalaryTotals");
    }

    public ArrayList<ContractorTeachingSalaryTotals> getContractorTeachingSalaryTotals(int month, int year) throws SQLException {
        return getContractorTeachingSalaryTotals("Select * from ContractorTeachingSalaryTotals  where date_month=" + month + " and date_year=" + year);
    }


    public String getAllContractorTeachingSalaryTotalsStr() throws SQLException {
        return executeQuery("Select * from ContractorTeachingSalaryTotals");
    }

    public String getContractorTeachingSalaryTotalsStr(int month, int year) throws SQLException {
        return executeQuery("Select * from ContractorTeachingSalaryTotals  where date_month=" + month + " and date_year=" + year);
    }


    public String getMaxMinAvgContractorTeachingSalaryTotalsStr() throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  ContractorTeachingSalaryTotals";
        return executeQuery(sql);
    }

    public String getMaxMinAvgContractorTeachingSalaryTotalsStr(int month, int year) throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  ContractorTeachingSalaryTotals" + " where date_month=" + month + " and date_year=" + year;
        return executeQuery(sql);
    }

    public MaxMinAvg getMaxMinAvgContractorTeachingSalaryTotals() throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  ContractorTeachingSalaryTotals";

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        MaxMinAvg result = new MaxMinAvg();
        while (resultSet.next()) {
            result.max = resultSet.getInt("max_salary");
            result.min = resultSet.getInt("min_salary");
            result.avg = resultSet.getFloat("avg_salary");
        }
        return result;
    }

    public MaxMinAvg getMaxMinAvgContractorTeachingSalaryTotals(int month, int year) throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  ContractorTeachingSalaryTotals" + " where date_month=" + month + " and date_year=" + year;

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        MaxMinAvg result = new MaxMinAvg();
        while (resultSet.next()) {
            result.max = resultSet.getInt("max_salary");
            result.min = resultSet.getInt("min_salary");
            result.avg = resultSet.getFloat("avg_salary");
        }
        return result;
    }

    public String getContractorTeachingSalaryTotalsValueStr() throws SQLException {
        String sql = "Select SUM(Total_Salary) as total_sum from ContractorTeachingSalaryTotals ";
        return executeQuery(sql);
    }

    public String getContractorTeachingSalaryTotalsValueStr(int month, int year) throws SQLException {
        String sql = "Select SUM(Total_Salary) as total_sum from ContractorTeachingSalaryTotals where date_month=" + month + " and date_year=" + year;
        return executeQuery(sql);
    }

    public int getContractorTeachingSalaryTotalsValue() throws SQLException {
        String sql = "Select SUM(Total_Salary) as total_sum from ContractorTeachingSalaryTotals ";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt("total_sum");
        }
        return result;
    }


    public int getContractorTeachingSalaryTotalsValue(int month, int year) throws SQLException {
        String sql = "Select SUM(Total_Salary) as total_sum from ContractorTeachingSalaryTotals where date_month=" + month + " and date_year=" + year;
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt("total_sum");
        }
        return result;
    }

    public ArrayList<ContractorTeachingSalaryTotals> getContractorTeachingSalaryTotals(String sql) throws SQLException {
        // Note that the sql must be staff sql
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        ArrayList<ContractorTeachingSalaryTotals> result = new ArrayList<>();
        while (resultSet.next()) {
            ContractorTeachingSalaryTotals rec = new ContractorTeachingSalaryTotals();
            rec.setSid(resultSet.getString("sid"));
            rec.setFirstName(resultSet.getString("first_name"));
            rec.setLastName(resultSet.getString("last_name"));
            rec.setMarriageStatus(resultSet.getBoolean("marriage_status"));
            rec.setIban(resultSet.getString("iban"));
            rec.setBankName(resultSet.getString("bank_name"));
            rec.setHireYear(resultSet.getShort("hire_year"));
            rec.setHireMonth(resultSet.getShort("hire_month"));
            rec.setPhone(resultSet.getString("phone"));
            rec.setDepartment(resultSet.getString("department"));
            rec.setStaffType(resultSet.getString("staff_type"));
            rec.setAddress(resultSet.getString("address"));
            rec.setDateMonth(resultSet.getInt("date_month"));
            rec.setDateYear(resultSet.getInt("date_year"));
            rec.setSalary(resultSet.getInt("total_salary"));
            rec.setContractSalary(resultSet.getInt("contract_salary"));
            rec.setFamilySupport(resultSet.getInt("family_support"));
            rec.setLibraryGrant(resultSet.getInt("library_grant"));
            result.add(rec);
        }
        resultSet.close();
        stmt.close();
        return result;

    }


    public ArrayList<ContractorAdminSalaryTotals> getAllContractorAdminSalaryTotals() throws SQLException {
        return getContractorAdminSalaryTotals("Select * from ContractorAdminSalaryTotals");
    }

    public ArrayList<ContractorAdminSalaryTotals> getContractorAdminSalaryTotals(int month, int year) throws SQLException {
        return getContractorAdminSalaryTotals("Select * from ContractorAdminSalaryTotals  where date_month=" + month + " and date_year=" + year);
    }

    public String getAllContractorAdminSalaryTotalsStr() throws SQLException {
        return executeQuery("Select * from ContractorAdminSalaryTotals");
    }

    public String getContractorAdminSalaryTotalsStr(int month, int year) throws SQLException {
        return executeQuery("Select * from ContractorAdminSalaryTotals  where date_month=" + month + " and date_year=" + year);
    }

    public String getMaxMinAvgContractorAdminSalaryTotalsStr() throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  ContractorAdminSalaryTotals";
        return executeQuery(sql);
    }

    public String getMaxMinAvgContractorAdminSalaryTotalsStr(int month, int year) throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  ContractorAdminSalaryTotals" + " where date_month=" + month + " and date_year=" + year;
        return executeQuery(sql);
    }

    public MaxMinAvg getMaxMinAvgContractorAdminSalaryTotals() throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  ContractorAdminSalaryTotals";

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        MaxMinAvg result = new MaxMinAvg();
        while (resultSet.next()) {
            result.max = resultSet.getInt("max_salary");
            result.min = resultSet.getInt("min_salary");
            result.avg = resultSet.getFloat("avg_salary");
        }
        return result;
    }

    public MaxMinAvg getMaxMinAvgContractorAdminSalaryTotals(int month, int year) throws SQLException {
        String sql = "Select Max(Total_Salary) as max_salary , " + "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary from  ContractorAdminSalaryTotals" + " where date_month=" + month + " and date_year=" + year;

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        MaxMinAvg result = new MaxMinAvg();
        while (resultSet.next()) {
            result.max = resultSet.getInt("max_salary");
            result.min = resultSet.getInt("min_salary");
            result.avg = resultSet.getFloat("avg_salary");
        }
        return result;
    }

    public String getContractorAdminSalaryTotalsValueStr() throws SQLException {
        String sql = "Select SUM(Total_Salary) as total from ContractorAdminSalaryTotals ";
        return executeQuery(sql);
    }

    public String getContractorAdminSalaryTotalsValueStr(int month, int year) throws SQLException {
        String sql = "Select SUM(Total_Salary) as total from ContractorAdminSalaryTotals where date_month=" + month + " and date_year=" + year;
        return executeQuery(sql);
    }

    public int getContractorAdminSalaryTotalsValue() throws SQLException {
        String sql = "Select SUM(Total_Salary) as total from ContractorAdminSalaryTotals ";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt("total");
        }
        return result;
    }


    public int getContractorAdminSalaryTotalsValue(int month, int year) throws SQLException {
        String sql = "Select SUM(Total_Salary) as total from ContractorAdminSalaryTotals where date_month=" + month + " and date_year=" + year;
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt("total");
        }
        return result;
    }


    public ArrayList<ContractorAdminSalaryTotals> getContractorAdminSalaryTotals(String sql) throws SQLException {
        // Note that the sql must be staff sql
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        ArrayList<ContractorAdminSalaryTotals> result = new ArrayList<>();
        while (resultSet.next()) {
            ContractorAdminSalaryTotals rec = new ContractorAdminSalaryTotals();
            rec.setSid(resultSet.getString("sid"));
            rec.setFirstName(resultSet.getString("first_name"));
            rec.setLastName(resultSet.getString("last_name"));
            rec.setMarriageStatus(resultSet.getBoolean("marriage_status"));
            rec.setIban(resultSet.getString("iban"));
            rec.setBankName(resultSet.getString("bank_name"));
            rec.setHireYear(resultSet.getShort("hire_year"));
            rec.setHireMonth(resultSet.getShort("hire_month"));
            rec.setPhone(resultSet.getString("phone"));
            rec.setDepartment(resultSet.getString("department"));
            rec.setStaffType(resultSet.getString("staff_type"));
            rec.setAddress(resultSet.getString("address"));
            rec.setDateMonth(resultSet.getInt("date_month"));
            rec.setDateYear(resultSet.getInt("date_year"));
            rec.setSalary(resultSet.getInt("total_salary"));
            rec.setContractSalary(resultSet.getInt("contract_salary"));
            rec.setFamilySupport(resultSet.getInt("family_support"));
            result.add(rec);
        }
        resultSet.close();
        stmt.close();
        return result;

    }

    public String getSalaryAverageChangesStr(int month, int year, int compareToMonth, int compareToYear) throws SQLException {

        MaxMinAvg mma = getMaxMinAvgSalaryTotals(month, year);
        MaxMinAvg mmaCompare = getMaxMinAvgSalaryTotals(compareToMonth, compareToYear);

        if (mma.max == 0 || mma.min == 0 || mma.avg == 0) {
            return "<html>----------------------------------------------------------------<br>" + "Average calculation can't be performed to empty data" + "<br>----------------------------------------------------------------<br></html>";
        }

        //Average maximum wage increase.
        float amaxwi = (mmaCompare.max - mma.max) * 100 / mma.max;

        //Average minimum wage increase.
        float aminwi = (mmaCompare.min - mma.min) * 100 / mma.min;

        //Average salary increase.
        float asali = (mmaCompare.avg - mma.avg) * 100 / mma.avg;

        return "<html>----------------------------------------------------------------<br>" + "Average maximum wage increase:" + String.format("%.02f", amaxwi) + "<br>" + "Average minimum wage increase:" + String.format("%.02f", aminwi) + "<br>" + "Average salary increase:" + String.format("%.02f", asali) + "<br>" + "<br>----------------------------------------------------------------<br></html>";

    }

    public String getAllSalaryDataForEmployStr(String sid) throws SQLException {

        ArrayList<Staff> stAr = getStaff("Select * from STAFF where sid='" + sid + "'");

        if (stAr.size() == 0) {
            return "<html>----------------------------------------------------------------<br>" + "There is no staff with specified SID. SID=" + sid + "<br>----------------------------------------------------------------<br></html>";
        }
        Staff st = stAr.get(0);
        String viewName = "";
        String dep = st.getDepartment().toLowerCase();
        String stp = st.getStaffType().toLowerCase();

        if (dep.equals("teaching") && stp.equals("permanent")) {
            viewName = "PermanentTeachingSalaryTotals";
        } else if (dep.equals("administrator") && stp.equals("permanent")) {
            viewName = "PermanentAdminSalaryTotals";
        } else if (dep.equals("teaching") && stp.equals("contractor")) {
            viewName = "ContractorTeachingSalaryTotals";
        } else if (dep.equals("administrator") && stp.equals("contractor")) {
            viewName = "ContractorAdminSalaryTotals";
        }

        String sql = "Select * from " + viewName + " where sid='" + sid + "' order by date_year , date_month";
        return executeQuery(sql);
    }

    public String getAllSalaryDataForEmployStr(String sid, int month, int year) throws SQLException {

        ArrayList<Staff> stAr = getStaff("Select * from STAFF where sid='" + sid + "'");

        if (stAr.size() == 0) {
            return "<html>----------------------------------------------------------------<br>" + "There is no staff with specified SID. SID=" + sid + "<br>----------------------------------------------------------------<br></html>";
        }
        Staff st = stAr.get(0);
        String viewName = "";
        String dep = st.getDepartment().toLowerCase();
        String stp = st.getStaffType().toLowerCase();

        if (dep.equals("teaching") && stp.equals("permanent")) {
            viewName = "PermanentTeachingSalaryTotals";
        } else if (dep.equals("administrator") && stp.equals("permanent")) {
            viewName = "PermanentAdminSalaryTotals";
        } else if (dep.equals("teaching") && stp.equals("contractor")) {
            viewName = "ContractorTeachingSalaryTotals";
        } else if (dep.equals("administrator") && stp.equals("contractor")) {
            viewName = "ContractorAdminSalaryTotals";
        }

        String sql = "Select * from " + viewName + " where sid='" + sid + "' and date_year=" + year + " and date_month=" + month;

        return executeQuery(sql);

    }


    ////////////////////////////////////////////////////////////////////////////
    // Generator operations
    ////////////////////////////////////////////////////////////////////////////

    private ArrayList<Child> getRandomChilds(Staff staff) {
        ArrayList<Child> result = new ArrayList();
        Random rand = new Random();
        int value = rand.nextInt(4) + 1;

        for (int i = 0; i < value; ++i) {
            Child child = new Child();
            child.setChildid(UUID.randomUUID().toString());
            child.setSid(staff.getSid());
            child.setAge((short) rand.nextInt(1, 17));
            result.add(child);
        }

        return result;
    }

    private Contract getRandomContract(Staff staff) {
        Random rand = new Random();
        Contract contract = new Contract();
        contract.setSid(staff.getSid());
        contract.setContractid(UUID.randomUUID().toString());
        contract.setContractStartYear(staff.getHireYear());
        contract.setContractStartMonth(staff.getHireMonth());
        int year = rand.nextInt(2023, 2025);
        contract.setContractEndYear(year);
        contract.setContractEndMonth(rand.nextInt(1, 13));
        contract.setContractSalary(rand.nextInt(750, 1300));
        return contract;
    }

    private void setRandomTypeHireDate(Staff staff) {
        String[] names = new String[]{"Permanent", "Contractor"};
        Random rand = new Random();
        int pos = rand.nextInt(names.length);
        staff.setStaffType(names[pos]);
        int hireYear;
        if (pos == 0) {
            hireYear = rand.nextInt(2011, 2023);
        } else {
            hireYear = rand.nextInt(2021, 2023);
        }

        staff.setHireYear(hireYear);
        staff.setHireMonth(rand.nextInt(1, 13));
    }

    private Staff getBasicRandomStaff(int sid) {
        Staff result = new Staff();
        Random rand = new Random();
        result.setSid("ID-" + sid);
        int value = rand.nextInt(2);
        if (value == 0) {
            result.setFirstName(getRandomFemaleFirstName());
            result.setLastName(getRandomFemaleLastName());
        } else {
            result.setFirstName(getRandomMaleFirstName());
            result.setLastName(getRandomMaleLastName());
        }

        value = rand.nextInt(2);
        if (value == 0) {
            result.setMarriageStatus(false);
        } else {
            result.setMarriageStatus(true);
        }

        result.setIban("GR" + generateRandomNumber(25));
        result.setBankName(getRandomBank());
        result.setPhone("694" + generateRandomNumber(7));
        result.setDepartment(generateRandomDepartment());
        result.setAddress(generateRandomAddress());
        setRandomTypeHireDate(result);
        return result;
    }

    private String generateRandomAddress() {
        String[] Address = new String[]{"Aristotelous", "Akropoleos", "Garivaldi", "Flemig", "Konstantinoupoleos", "Praksitelous", "Agamemnonos", "Leof. Papanastasiou", "Thanasi Skordalou", "Megalou Aleksandrou", "Leof. Knosou", "Nik. Plastira", "Lasithiou", "Chanion", "Evans", "Viannou", "Kapetanisas Marigos", "Romanou", "Giannikou", "Kornarou", "Chatzimixalh Giannari", "Smirnis", "Dikaiosinis", "Daidalou", "Vizantiou", "Idomeneos"};
        Random rand = new Random();
        int pos = rand.nextInt(Address.length);
        String address = Address[pos];
        pos = rand.nextInt(50) + 1;
        address = address + " " + pos;
        return address;
    }

    private String generateRandomDepartment() {
        String[] names = new String[]{"Administrator", "Teaching"};
        Random rand = new Random();
        int pos = rand.nextInt(names.length);
        String result = names[pos];
        return result;
    }

    private String getRandomBank() {
        String[] names = new String[]{"PIREOS BANK", "NATIONAL BANK OF GREECE", "ALPHA BANK", "EUROBANK", "ATTICA BANK", "HSBC", "CITIBANK EUROPE PLC"};
        Random rand = new Random();
        int pos = rand.nextInt(names.length);
        String result = names[pos];
        return result;
    }

    private String getRandomFemaleFirstName() {
        String[] names = new String[]{"Georgia", "Konstantina", "Ioanna", "Dimitra", "Christina", "Apostolia", "Panagiota", "Maria", "Aleksandra", "Athanasia", "Katerina", "Zoi", "Chrisanthi", "Ilektra", "Aggeliki", "Aliki", "Anna", "Artemis", "Athina", "Dionisia", "Eleni", "Margarita", "Olga", "Sia", "Tatiana", "Euaggelia", "Loykia", "Faidra"};
        Random rand = new Random();
        int pos = rand.nextInt(names.length);
        String result = names[pos];
        return result;
    }

    private String getRandomFemaleLastName() {
        String[] names = new String[]{"Athanasiadou", "Vamvaka", "Gazi", "Dede", "Dela", "Evert", "Eugenidou", "Papadopoulou", "Papanikolaou", "Aleksndrou", "Papazoglou", "Kelesi", "Nikolopoulou", "Vekri", "Plyxronopoulou", "Maniaka", "Bitsani", "Euthimiou", "Anastasiou", "Georgakopoulou", "Parda", "Rokou", "Papandreoy", "Koygia", "Rouva"};
        Random rand = new Random();
        int pos = rand.nextInt(names.length);
        String result = names[pos];
        return result;
    }

    private String getRandomMaleFirstName() {
        String[] names = new String[]{"Georgios", "Konstantinos", "Giannis", "Dimitris", "Odysseas", "Andreas", "Manolis", "Christos", "Apostolos", "Panagiotis", "Lazaros", "Loykas", "Marios", "Aleksndros", "Athanasios", "Theodoros", "Theofilos", "Antonis", "Apostolos", "Ilias", "Orestis", "Lampros", "Dimosthenis", "Fedon", "Foivos", "Miltiadis", "Dionisis", "Euaggelos"};
        Random rand = new Random();
        int pos = rand.nextInt(names.length);
        String result = names[pos];
        return result;
    }

    private String getRandomMaleLastName() {
        String[] names = new String[]{"Athanasiadis", "Vamvakas", "Gazis", "Dedes", "Delas", "Evert", "Eugenidis", "Papadopoulos", "Papanikolaou", "Aleksandrou", "Papazoglou", "Kelesis", "Nikolopoulos", "Vekris", "Polixronopoulos", "Maniakas", "Mpitsanis", "Efthimiou", "Anastasiou", "Georgakopoulos", "Pardas", "Rokos", "Papandreoy", "Koygias", "Rouvas"};
        Random rand = new Random();
        int pos = rand.nextInt(names.length);
        String result = names[pos];
        return result;
    }

    private String generateRandomNumber(int length) {
        String numbers = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; ++i) {
            int index = random.nextInt(numbers.length());
            char randomChar = numbers.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }


}
