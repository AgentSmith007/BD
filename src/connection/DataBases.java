package connection;

import beans.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataBases {
    private static Connection connection = null;

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Locale.setDefault(Locale.ENGLISH);
                Class.forName("oracle.jdbc.OracleDriver");
                connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Fedor", "124578");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static List<Employee> getEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPLOYEE");
        while (resultSet.next()) {
            employees.add(new Employee(
                    resultSet.getInt("ID"),
                    resultSet.getString("FIO"),
                    resultSet.getString("PHONE"),
                    resultSet.getString("EMAIL"),
                    resultSet.getInt("AGE")
            ));
        }
        statement.close();
        resultSet.close();
        return employees;
    }

    public static void updateEmployee(Employee employee) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("UPDATE EMPLOYEE SET FIO = ?, PHONE = ?, EMAIL = ?, AGE = ? WHERE ID = ?");
        statement.setString(1, employee.getFio());
        statement.setString(3, employee.getEmail());
        statement.setString(2, employee.getPhonenumber());
        statement.setInt(4, employee.getAge());
        statement.setInt(5, employee.getId());
        statement.executeUpdate();
        statement.close();
    }

    public static void addEmployee(Employee employee) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO EMPLOYEE VALUES (EMPLOYEE_ID_SEQ.NEXTVAL, ?, ?, ?, ?)");
        statement.setString(1, employee.getFio());
        statement.setString(3, employee.getEmail());
        statement.setString(2, employee.getPhonenumber());
        statement.setInt(4, employee.getAge());
        statement.execute();
        statement.close();
    }

    public static void removeEmployee(int id) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM EMPLOYEE WHERE ID = ?");
        statement.setInt(1, id);
        statement.execute();
        statement.close();
    }
}
