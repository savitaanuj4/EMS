
package anuj;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class DAO
{
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DAO.con = DriverManager.getConnection("jdbc:mysql://localhost/ems", "root", "");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean addEmployee(final int id, final String name, final String designation, final String doj, final String city, final long mobile, final String email, final String shift) {
        try {
            (DAO.ps = DAO.con.prepareStatement("INSERT INTO employees values(?,?,?,?,?,?,?,?)")).setInt(1, id);
            DAO.ps.setString(2, name);
            DAO.ps.setString(3, designation);
            DAO.ps.setString(4, doj);
            DAO.ps.setString(5, city);
            DAO.ps.setLong(6, mobile);
            DAO.ps.setString(7, email);
            DAO.ps.setString(8, shift);
            return DAO.ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static ResultSet searchEmployee(final int id) {
        try {
            (DAO.ps = DAO.con.prepareStatement("SELECT * FROM employees WHERE emp_id = ?")).setInt(1, id);
            DAO.rs = DAO.ps.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return DAO.rs;
    }
    
    public static boolean removeEmployee(final int id) {
        try {
            (DAO.ps = DAO.con.prepareStatement("DELETE FROM employees WHERE emp_id = ?")).setInt(1, id);
            return DAO.ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean updateEmployee(final String name, final String doj, final String designation, final String city, final long mobile, final String email, final String shift, final int id) {
        try {
            (DAO.ps = DAO.con.prepareStatement("UPDATE employees set name =?, doj =?, designation =?, city =?, mobile =?, email = ?, shift = ? where emp_id = ?")).setString(1, name);
            DAO.ps.setString(2, doj);
            DAO.ps.setString(3, designation);
            DAO.ps.setString(4, city);
            DAO.ps.setLong(5, mobile);
            DAO.ps.setString(6, email);
            DAO.ps.setString(7, shift);
            DAO.ps.setInt(8, id);
            return DAO.ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static ResultSet employeesRecord() {
        try {
            DAO.ps = DAO.con.prepareStatement("SELECT * FROM employees");
            DAO.rs = DAO.ps.executeQuery();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return DAO.rs;
    }
    
    public static boolean basicSalary(final int id, final String name, final String designation, final double salary, final String salaryType) {
        try {
            (DAO.ps = DAO.con.prepareStatement("INSERT INTO salary (emp_id, name, designation, salary, salary_type) values (?,?,?,?,?)")).setInt(1, id);
            DAO.ps.setString(2, name);
            DAO.ps.setString(3, designation);
            DAO.ps.setDouble(4, salary);
            DAO.ps.setString(5, salaryType);
            return DAO.ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean incentives(final double ta, final double da, final double hra, final double medical, final int id) {
        try {
            (DAO.ps = DAO.con.prepareStatement("UPDATE salary set ta =?, da= ?, hra =?, medical = ? WHERE emp_id =?")).setDouble(1, ta);
            DAO.ps.setDouble(2, da);
            DAO.ps.setDouble(3, hra);
            DAO.ps.setDouble(4, medical);
            DAO.ps.setInt(5, id);
            return DAO.ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static ResultSet searchFromSalary(final int id) {
        try {
            (DAO.ps = DAO.con.prepareStatement("SELECT * FROM salary WHERE emp_id =?")).setInt(1, id);
            DAO.rs = DAO.ps.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return DAO.rs;
    }
    
    public static boolean bonus(final double bonusAmt, final String bounesty, final int id) {
        try {
            (DAO.ps = DAO.con.prepareStatement("UPDATE salary set bonus = ?, bounesty =? where emp_id =?")).setDouble(1, bonusAmt);
            DAO.ps.setString(2, bounesty);
            DAO.ps.setInt(3, id);
            return DAO.ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean deductions(final double advance, final double loan, final double leaveCut, final int id) {
        try {
            (DAO.ps = DAO.con.prepareStatement("UPDATE salary set advance_paid = ?, loan =?, leave_cut =? where emp_id =?")).setDouble(1, advance);
            DAO.ps.setDouble(2, loan);
            DAO.ps.setDouble(3, leaveCut);
            DAO.ps.setInt(4, id);
            return DAO.ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean leavesEntry(final int id, final String name, final String designation, final double salary, final int leaves, final String leaveType) {
        try {
            (DAO.ps = DAO.con.prepareStatement("INSERT INTO leaves values(?,?,?,?,?,?);")).setInt(1, id);
            DAO.ps.setString(2, name);
            DAO.ps.setString(3, designation);
            DAO.ps.setDouble(4, salary);
            DAO.ps.setInt(5, leaves);
            DAO.ps.setString(6, leaveType);
            return DAO.ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static ResultSet salaryRecord() {
        try {
            DAO.ps = DAO.con.prepareStatement("SELECT * FROM salary");
            DAO.rs = DAO.ps.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return DAO.rs;
    }
    
    public static ResultSet leavesRecord() {
        try {
            DAO.ps = DAO.con.prepareStatement("SELECT * FROM leaves");
            DAO.rs = DAO.ps.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return DAO.rs;
    }
}
