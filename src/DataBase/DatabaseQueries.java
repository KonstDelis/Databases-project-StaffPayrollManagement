package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DatabaseQueries {

    private static DatabaseQueries instance;


    public static DatabaseQueries getInstance() {
        if (instance == null) {
            instance = new DatabaseQueries();
        }
        return instance;
    }

    private Connection connection;

    Map<String, PreparedStatement> statements;


    public void useConnection(Connection connection) {
        this.connection = connection;
        try {
            initialisePreparedStatements();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void initialisePreparedStatements() throws SQLException {
        statements = new HashMap<String, PreparedStatement>();
        PreparedStatement stmt;
        String sql;

        sql = "INSERT INTO `STAFF` " +
                "(sid, first_name, last_name, marriage_status, iban, bank_name, " +
                "hire_year, hire_month, phone , department,staff_type,address ) " +
                "VALUES " +
                "(NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
        stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statements.put("InsertStaff", stmt);



    }

    /*
    private void initialisePreparedStatements() throws SQLException {
        statements = new HashMap<String, PreparedStatement>();
        PreparedStatement stmt;
        String sql;


        sql = "INSERT INTO `Staff` " +
                "(`id`, `category_id`, `department_id`, `bank_id`, `marital_status`, `full_name`, " +
                "`address`, `phone`, `iBan` ) " +
                "VALUES " +
                "(NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
        stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statements.put("InsertStaff", stmt);


        sql = "INSERT INTO `Bank` (`id`, `name`) VALUES (NULL, ?)";
        stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        statements.put("InsertBank", stmt);

        sql = "INSERT INTO `Department` (`id`, `name`) VALUES (NULL, ?)";
        stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        statements.put("InsertDepartment", stmt);

        sql = "INSERT INTO `Staff_Categories` (`id`, `name` ,base_salary,per_year," +
                "family_bonus,research_bonus,library_bonus,child_bonus) VALUES " +
                "(NULL, ?,?,?,?,?,?,?)";
        stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        statements.put("InsertStaffCategory", stmt);

        sql = "INSERT INTO `Staff_Childs` (`id`, `staff_id`, `birth_date`) " +
                "VALUES " +
                "(NULL, ?,?)";
        stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        statements.put("InsertStaffChilds", stmt);

        sql = "INSERT INTO `Staff_Contract` (`staff_id`, `start_date`, `end_date`  ) " +
                "VALUES " +
                "(?,?,?)";
        stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        statements.put("InsertStaffContract", stmt);

        sql = "INSERT INTO `Staff_SContract` (`staff_id`, `salary`  ) " +
                "VALUES " +
                "(?,?)";
        stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        statements.put("InsertStaffSContract", stmt);


    }


    public void InsertStaffSContract(StaffSContract contract) throws SQLException {

        PreparedStatement stmt;
        stmt = statements.get("InsertStaffSContract");

        stmt.setLong(1, contract.getStaffId());
        stmt.setFloat(2, contract.getSalary());
        stmt.executeUpdate();
    }


    public void InsertStaffContract(StaffContract contract) throws SQLException {
        PreparedStatement stmt;
        stmt = statements.get("InsertStaffContract");

        stmt.setLong(1, contract.getStaffId());
        stmt.setDate(2, contract.getStartDate());
        stmt.setDate(3, contract.getEndDate());
        stmt.executeUpdate();
    }


    public void InsertStaffChilds(StaffChilds child) throws SQLException {
        PreparedStatement stmt;
        stmt = statements.get("InsertStaffChilds");

        stmt.setLong(1, child.getStaffId());
        stmt.setDate(2, child.getBirthDate());

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            child.setId(rs.getInt(1));
        }
        rs.close();
    }


    public void InsertStaffCategory(StaffCategories category) throws SQLException {
        PreparedStatement stmt;
        stmt = statements.get("InsertStaffCategory");

        stmt.setString(1, category.getName());
        stmt.setFloat(2,category.getBaseSalary());
        stmt.setFloat(3,category.getPerYear());
        stmt.setFloat(4,category.getFamilyBonus());
        stmt.setFloat(5,category.getResearchBonus());
        stmt.setFloat(6,category.getLibraryBonus());
        stmt.setFloat(7,category.getChildBonus());

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            category.setId(rs.getInt(1));
        }
        rs.close();
    }


    public void InsertDepartment(Department depart) throws SQLException {
        PreparedStatement stmt;
        stmt = statements.get("InsertDepartment");

        stmt.setString(1, depart.getName());

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            depart.setId(rs.getInt(1));
        }
        rs.close();
    }


    public void InsertBank(Bank bank) throws SQLException {
        PreparedStatement stmt;
        stmt = statements.get("InsertBank");

        stmt.setString(1, bank.getName());

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            bank.setId(rs.getInt(1));
        }
        rs.close();
    }

    public void InsertStaff(Staff staff) throws SQLException {
        PreparedStatement stmt;

        stmt = statements.get("InsertStaff");
        stmt.setShort(1, staff.getCategoryId());
        stmt.setShort(2, staff.getDepartmentId());
        stmt.setShort(3, staff.getBankId());
        stmt.setBoolean(4, staff.getMaritalStatus());
        stmt.setString(5, staff.getFullName());
        stmt.setString(6, staff.getAddress());
        stmt.setString(7, staff.getPhone());
        stmt.setString(8, staff.getIBan());

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            staff.setId(rs.getInt(1));
        }
        rs.close();
    }
*/

}
