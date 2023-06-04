package DataBase;

import POJOclasses.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestDBActions {

    private static DBActions act;

    public static void main(String[] args) {

        try {
            DBConnection.getInstance().initializeFromFile ("DataBase/config.properties");
            act = new DBActions();
            act.useConnection(DBConnection.getInstance().getConnection());
            testStaff();
            testSalaryDepositData();
            testSalaryTotals();
            testPermanentTeachingSalaryTotals();
            testPermanentAdminSalaryTotals();
            testContractorTeachingSalaryTotals();
            testContractorAdminSalaryTotals();
            testAdditions();
            testAdditionsStr();

            testSalaryDataForEmploy();
            testAverageChanges();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }



    private static void testStaff() throws SQLException {
        ArrayList<Staff> result = act.getAllStaff();
        // We can do with the staff what ever we like
        System.out.println("Total Staff records :" + result.size() );
    }

    private static void testSalaryDepositData() throws SQLException {
        ArrayList<SalaryDepositData> result = act.getAllSalaryDepositData();
        // We can do with the staff what ever we like
        System.out.println("Total SalaryDepositData records :" + result.size() );
    }


    private static void testSalaryTotals() throws SQLException {
        ArrayList<SalaryTotals> result = act.getAllSalaryTotals();
        // We can do with the staff what ever we like
        System.out.println("Total Salary Totals records :" + result.size() );
    }

    private static void testPermanentTeachingSalaryTotals() throws SQLException {
        ArrayList<PermanentTeachingSalaryTotals> result = act.getAllPermanentTeachingSalaryTotals();
        // We can do with the staff what ever we like
        System.out.println("Total PermanentTeachingSalaryTotals records :" + result.size() );
    }

    private static void testPermanentAdminSalaryTotals() throws SQLException {
        ArrayList<PermanentAdminSalaryTotals> result = act.getAllPermanentAdminSalaryTotals();
        // We can do with the staff what ever we like
        System.out.println("Total PermanentAdminSalaryTotals records :" + result.size() );
    }

    private static void testContractorTeachingSalaryTotals() throws SQLException {
        ArrayList<ContractorTeachingSalaryTotals> result = act.getAllContractorTeachingSalaryTotals();
        // We can do with the staff what ever we like
        System.out.println("Total ContractorTeachingSalaryTotals records :" + result.size() );
    }

    private static void testContractorAdminSalaryTotals() throws SQLException {
        ArrayList<ContractorAdminSalaryTotals> result = act.getAllContractorAdminSalaryTotals();
        // We can do with the staff what ever we like
        System.out.println("Total ContractorAdminSalaryTotals records :" + result.size() );
    }

    private static void testAdditions() throws SQLException {

        System.out.println("getContractorAdminSalaryTotals :" + act.getContractorAdminSalaryTotals(1,2023).size());
        MaxMinAvg mma = act.getMaxMinAvgContractorAdminSalaryTotals();
        System.out.println("getMaxMinAvgContractorAdminSalaryTotals Max=" + mma.max + " min=" + mma.min + " avg=" + mma.avg);
        mma = act.getMaxMinAvgContractorAdminSalaryTotals(1,2023);
        System.out.println("getMaxMinAvgContractorAdminSalaryTotals Max=" + mma.max + " min=" + mma.min + " avg=" + mma.avg);

        System.out.println("getContractorTeachingSalaryTotals :" + act.getContractorTeachingSalaryTotals(1,2023).size());
        mma = act.getMaxMinAvgContractorTeachingSalaryTotals();
        System.out.println("getMaxMinAvgContractorTeachingSalaryTotals Max=" + mma.max + " min=" + mma.min + " avg=" + mma.avg);
        mma = act.getMaxMinAvgContractorTeachingSalaryTotals(1,2023);
        System.out.println("getMaxMinAvgContractorTeachingSalaryTotals Max=" + mma.max + " min=" + mma.min + " avg=" + mma.avg);


        System.out.println("getPermanentAdminSalaryTotals :" + act.getPermanentAdminSalaryTotals(1,2023).size());
        mma = act.getMaxMinAvgPermanentAdminSalaryTotals();
        System.out.println("getMaxMinAvgPermanentAdminSalaryTotals Max=" + mma.max + " min=" + mma.min + " avg=" + mma.avg);
        mma = act.getMaxMinAvgPermanentAdminSalaryTotals(1,2023);
        System.out.println("getMaxMinAvgPermanentAdminSalaryTotals Max=" + mma.max + " min=" + mma.min + " avg=" + mma.avg);

        System.out.println("getPermanentTeachingSalaryTotals :" + act.getPermanentTeachingSalaryTotals(1,2023).size());
        mma = act.getMaxMinAvgPermanentTeachingSalaryTotals();
        System.out.println("getMaxMinAvgPermanentTeachingSalaryTotals Max=" + mma.max + " min=" + mma.min + " avg=" + mma.avg);
        mma = act.getMaxMinAvgPermanentTeachingSalaryTotals(1,2023);
        System.out.println("getMaxMinAvgPermanentTeachingSalaryTot Max=" + mma.max + " min=" + mma.min + " avg=" + mma.avg);


        int t = act.getPermanentTeachingSalaryTotalValue();
        System.out.println("getPermanentTeachingSalaryTotalValue " + t);
        t = act.getPermanentTeachingSalaryTotalValue(1,2023);
        System.out.println("getPermanentTeachingSalaryTotalValue " + t);

        t = act.getPermanentAdminSalaryTotalsValue();
        System.out.println("getPermanentAdminSalaryTotalsValue " + t);
        t = act.getPermanentAdminSalaryTotalsValue(1,2023);
        System.out.println("getPermanentAdminSalaryTotalsValue " + t);


        t = act.getContractorTeachingSalaryTotalsValue();
        System.out.println("getContractorTeachingSalaryTotalsValue " + t);
        t = act.getContractorTeachingSalaryTotalsValue(1,2023);
        System.out.println("getContractorTeachingSalaryTotalsValue " + t);


        t = act.getContractorAdminSalaryTotalsValue();
        System.out.println("getContractorAdminSalaryTotalsValue " + t);
        t = act.getContractorAdminSalaryTotalsValue(1,2023);
        System.out.println("getContractorAdminSalaryTotalsValue " + t);



    }

    private static void testAdditionsStr() throws SQLException {

        // Returns all records from STAFF in html format.
        // Equivalent to act.executeQuery("Select * from STAFF")
        System.out.println(act.getAllStaffStr());

        // Returns all records from SALARY_DEPOSIT_DATA in html format.
        // Equivalent to act.executeQuery("Select * from SALARY_DEPOSIT_DATA")
        System.out.println(act.getAllSalaryDepositDataStr());

        // Returns all records from SalaryTotals in html format.
        // Equivalent to act.executeQuery("Select * from SalaryTotals")
        System.out.println(act.getAllSalaryTotalsStr());

        // Return all Permanent - Teaching Salary records
        // View PermanentTeachingSalaryTotals . Equivalent to
        //      act.executeQuery("Select * from PermanentTeachingSalaryTotals")
        System.out.println(act.getAllPermanentTeachingSalaryTotalsStr());


        // Return Permanent - Teaching Salary records for specified year , month
        // View PermanentTeachingSalaryTotals . Equivalent to
        // act.executeQuery("Select * from PermanentTeachingSalaryTotals where
        //                  date_month=" + month +" and date_year=" + year)
        System.out.println(act.getPermanentTeachingSalaryTotalsStr(1,2023));

        // Returns Max , Min , Average Permanent - Teaching Salary from all records
        // View PermanentTeachingSalaryTotals . Equivalent to
        // act.executeQuery("Select Max(Total_Salary) as max_salary , " +
        //                "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary
        //                from  PermanentTeachingSalaryTotals")
        System.out.println(act.getMaxMinAvgPermanentTeachingSalaryTotalsStr());

        // Returns Max , Min , Average Permanent - Teaching Salary for specified month , year
        // View PermanentTeachingSalaryTotals . Equivalent to
        // act.executeQuery("Select Max(Total_Salary) as max_salary , " +
        //                "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary
        //                from  PermanentTeachingSalaryTotals where date_month=" + month + " and date_year=" + year);
        System.out.println(act.getMaxMinAvgPermanentTeachingSalaryTotalsStr(1,2023));

        // Returns the summarized total of  Permanent - Teaching Salaries
        // View PermanentTeachingSalaryTotals. Equivalent to
        // act.executeQuery("Select SUM(Total_Salary) as total_sum from PermanentTeachingSalaryTotals ")
        System.out.println(act.getPermanentTeachingSalaryTotalValueStr());

        // Returns the summarized total of  Permanent - Teaching Salaries for specified month year
        // View PermanentTeachingSalaryTotals. Equivalent to
        // act.executeQuery("Select SUM(Total_Salary) as total from PermanentTeachingSalaryTotals where date_month=" + month +
        //                " and date_year=" + year)
        System.out.println(act.getPermanentTeachingSalaryTotalValueStr(1,2023));


        // Return all Permanent - Administrator Salary records
        // View PermanentAdminSalaryTotals . Equivalent to
        //      act.executeQuery("Select * from PermanentAdminSalaryTotals")
        System.out.println(act.getAllPermanentAdminSalaryTotalsStr());

        // Return Permanent - Administrator Salary records for specified year , month
        // View PermanentAdminSalaryTotals . Equivalent to
        // act.executeQuery("Select * from PermanentAdminSalaryTotals where
        //                  date_month=" + month +" and date_year=" + year)
        System.out.println(act.getPermanentAdminSalaryTotalsStr(1,2023));

        // Returns Max , Min , Average Permanent - Administrator Salary from all records
        // View PermanentAdminSalaryTotals . Equivalent to
        // act.executeQuery("Select Max(Total_Salary) as max_salary , " +
        //                "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary
        //                from  PermanentAdminSalaryTotals")
        System.out.println(act.getMaxMinAvgPermanentAdminSalaryTotalsStr());

        // Returns Max , Min , Average Permanent - Administrator Salary for specified month , year
        // View PermanentAdminSalaryTotals . Equivalent to
        // act.executeQuery("Select Max(Total_Salary) as max_salary , " +
        //                "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary
        //                from  PermanentAdminSalaryTotals where date_month=" + month + " and date_year=" + year);
        System.out.println(act.getMaxMinAvgPermanentAdminSalaryTotalsStr(1,2023));

        // Returns the summarized total of  Permanent - Administrator Salaries
        // View PermanentAdminSalaryTotalsValue. Equivalent to
        // act.executeQuery("Select SUM(Total_Salary) as total_sum from PermanentAdminSalaryTotalsValue ")
        System.out.println(act.getPermanentAdminSalaryTotalsValueStr());

        // Returns the summarized total of  Permanent - Administrator Salaries for specified month year
        // View PermanentAdminSalaryTotalsValue. Equivalent to
        // act.executeQuery("Select SUM(Total_Salary) as total from PermanentAdminSalaryTotalsValue where date_month=" + month +
        //                " and date_year=" + year)
        System.out.println(act.getPermanentAdminSalaryTotalsValueStr(1,2023));

        // Return all Contractor - Teaching Salary records
        // View ContractorTeachingSalaryTotals . Equivalent to
        //      act.executeQuery("Select * from ContractorTeachingSalaryTotals")
        System.out.println(act.getAllContractorTeachingSalaryTotalsStr());

        // Return Contractor - Teaching Salary records for specified year , month
        // View ContractorTeachingSalaryTotals . Equivalent to
        // act.executeQuery("Select * from ContractorTeachingSalaryTotals where
        //                  date_month=" + month +" and date_year=" + year)
        System.out.println(act.getContractorTeachingSalaryTotalsStr(1,2023));

        // Returns Max , Min , Average Contractor - Teaching Salary from all records
        // View ContractorTeachingSalaryTotals . Equivalent to
        // act.executeQuery("Select Max(Total_Salary) as max_salary , " +
        //                "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary
        //                from  ContractorTeachingSalaryTotals")
        System.out.println(act.getMaxMinAvgContractorTeachingSalaryTotalsStr());

        // Returns Max , Min , Average Contractor - Teaching Salary for specified month , year
        // View ContractorTeachingSalaryTotals . Equivalent to
        // act.executeQuery("Select Max(Total_Salary) as max_salary , " +
        //                "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary
        //                from  ContractorTeachingSalaryTotals where date_month=" + month + " and date_year=" + year);
        System.out.println(act.getMaxMinAvgContractorTeachingSalaryTotalsStr(1,2023));

        // Returns the summarized total of  Contractor - Teaching Salaries
        // View ContractorTeachingSalaryTotals. Equivalent to
        // act.executeQuery("Select SUM(Total_Salary) as total_sum from ContractorTeachingSalaryTotals ")
        System.out.println(act.getContractorTeachingSalaryTotalsValueStr());

        // Returns the summarized total of  Contractor - Teaching Salaries for specified month year
        // View ContractorTeachingSalaryTotals. Equivalent to
        // act.executeQuery("Select SUM(Total_Salary) as total from ContractorTeachingSalaryTotals where date_month=" + month +
        //                " and date_year=" + year)
        System.out.println(act.getContractorTeachingSalaryTotalsValueStr(1,2023));


        // Return all Contractor - Administrator Salary records
        // View ContractorAdminSalaryTotals . Equivalent to
        //      act.executeQuery("Select * from ContractorAdminSalaryTotals")
        System.out.println(act.getAllContractorAdminSalaryTotalsStr());

        // Return Contractor - Administrator Salary records for specified year , month
        // View ContractorAdminSalaryTotals . Equivalent to
        // act.executeQuery("Select * from ContractorAdminSalaryTotals where
        //                  date_month=" + month +" and date_year=" + year)
        System.out.println(act.getContractorAdminSalaryTotalsStr(1,2023));

        // Returns Max , Min , Average Contractor - Administrator Salary from all records
        // View ContractorAdminSalaryTotals . Equivalent to
        // act.executeQuery("Select Max(Total_Salary) as max_salary , " +
        //                "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary
        //                from  ContractorAdminSalaryTotals")
        System.out.println(act.getMaxMinAvgContractorAdminSalaryTotalsStr());

        // Returns Max , Min , Average Contractor - Administrator Salary for specified month , year
        // View ContractorAdminSalaryTotals . Equivalent to
        // act.executeQuery("Select Max(Total_Salary) as max_salary , " +
        //                "MIN(Total_Salary) as min_salary , AVG(Total_Salary) as avg_salary
        //                from  ContractorAdminSalaryTotals where date_month=" + month + " and date_year=" + year);
        System.out.println(act.getMaxMinAvgContractorAdminSalaryTotalsStr(1,2023));

        // Returns the summarized total of  Contractor - Administrator Salaries
        // View ContractorAdminSalaryTotals. Equivalent to
        // act.executeQuery("Select SUM(Total_Salary) as total_sum from ContractorAdminSalaryTotals ")
        System.out.println(act.getContractorAdminSalaryTotalsValueStr());

        // Returns the summarized total of  Contractor - Administrator Salaries for specified month year
        // View ContractorAdminSalaryTotals. Equivalent to
        // act.executeQuery("Select SUM(Total_Salary) as total from ContractorAdminSalaryTotals where date_month=" + month +
        //                " and date_year=" + year)
        System.out.println(act.getContractorAdminSalaryTotalsValueStr(1,2023));

    }

    private static void testSalaryDataForEmploy() throws SQLException {

        for (int i=1 ; i<31;i++) {
            // Returns All the details salary records for the specified sid
            // First finds the department/staff_type properties from STAFF table and then
            // uses the appropriate view to read the records.
            System.out.println(act.getAllSalaryDataForEmployStr("ID-" + i));
        }
        System.out.println(act.getAllSalaryDataForEmployStr("ID-0"));


        for (int i=1 ; i<31;i++) {
            // Returns All the details salary fields for the specified sid for specified month , year
            // First finds the department/staff_type properties from STAFF table and then
            // uses the appropriate view to read the record.
            System.out.println(act.getAllSalaryDataForEmployStr("ID-" + i , 1,2023));
        }
        System.out.println(act.getAllSalaryDataForEmployStr("ID-0", 1, 2023));


    }

    private static void testAverageChanges() throws SQLException {

        // Returns Average maximum wage increase , Average minimum wage increase Average salary increase
        // for the specified payments. Gets the Min, Max , Avg for the first month/year and for the compare month/year
        // and calculates the present difference of these values
        System.out.println(act.getSalaryAverageChangesStr(11, 2022,1,2023));


    }

}

