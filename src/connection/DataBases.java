package connection;

import beans.Employee;
import beans.Enterprise;
import beans.Speciality;
import beans.Vacancy;

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

    public static List<Enterprise> getEnterprises() throws SQLException {
        List<Enterprise> enterprises = new ArrayList<>();
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ENTERPRISE");
        while (resultSet.next()) {
            enterprises.add(new Enterprise(
                    resultSet.getInt("ID"),
                    resultSet.getString("NAME"),
                    resultSet.getString("PHONE")
            ));
        }
        statement.close();
        resultSet.close();
        return enterprises;
    }

    public static void updateEnterprise(Enterprise enterprise) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("UPDATE ENTERPRISE SET NAME = ?, PHONE = ? WHERE ID = ?");
        statement.setString(1, enterprise.getName());
        statement.setString(2, enterprise.getPhone());
        statement.setInt(3, enterprise.getId());
        statement.executeUpdate();
        statement.close();
    }

    public static void addEnterprise(Enterprise enterprise) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO ENTERPRISE VALUES (ENTERPRISE_ID_SEQ.NEXTVAL, ?, ?)");
        statement.setString(1, enterprise.getName());
        statement.setString(2, enterprise.getPhone());
        statement.execute();
        statement.close();
    }

    public static void removeEnterprise(int id) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM ENTERPRISE WHERE ID = ?");
        statement.setInt(1, id);
        statement.execute();
        statement.close();
    }

    public static Enterprise getEnterprise(int id) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM ENTERPRISE WHERE ID = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        Enterprise enterprise = new Enterprise(
                resultSet.getInt("ID"),
                resultSet.getString("NAME"),
                resultSet.getString("PHONE"));

        statement.close();
        resultSet.close();
        return enterprise;
    }

    public static List<Speciality> getSpecialities() throws SQLException {
        List<Speciality> specialities = new ArrayList<>();
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM SPECIALITY");
        while (resultSet.next()) {
            specialities.add(new Speciality(
                    resultSet.getInt("ID"),
                    resultSet.getString("NAME")
            ));
        }
        statement.close();
        resultSet.close();
        return specialities;
    }

    public static Speciality getSpeciality(int id) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM SPECIALITY WHERE ID = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        Speciality speciality = new Speciality(
                resultSet.getInt("ID"),
                resultSet.getString("NAME")
        );

        statement.close();
        resultSet.close();
        return speciality;
    }

    public static void updateSpeciality(Speciality speciality) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("UPDATE SPECIALITY SET NAME = ? WHERE ID = ?");
        statement.setString(1, speciality.getName());
        statement.setInt(2, speciality.getId());
        statement.executeUpdate();
        statement.close();
    }

    public static void addSpeciality(Speciality speciality) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO SPECIALITY VALUES (SPECIALITY_ID_SEQ.NEXTVAL, ?)");
        statement.setString(1, speciality.getName());
        statement.execute();
        statement.close();
    }

    public static void removeSpeciality(int id) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM SPECIALITY WHERE ID = ?");
        statement.setInt(1, id);
        statement.execute();
        statement.close();
    }

    public static List<Vacancy> getVacancies() throws SQLException {
        List<Vacancy> vacancies = new ArrayList<>();
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM VACANCY");
        while (resultSet.next()) {
            vacancies.add(new Vacancy(
                    resultSet.getInt("ID"),
                    resultSet.getInt("ENTERPRISE_ID"),
                    resultSet.getInt("SPECIALITY_ID"),
                    resultSet.getInt("EXPERIENCE"),
                    resultSet.getInt("SALARY")
            ));
        }
        statement.close();
        resultSet.close();
        return vacancies;
    }

    public static void updateVacancy(Vacancy vacancy) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("UPDATE VACANCY SET ENTERPRISE_ID = ?, SPECIALITY_ID = ?, EXPERIENCE = ?, SALARY = ? WHERE ID = ?");
        statement.setInt(1, vacancy.getEnterpriseID());
        statement.setInt(2, vacancy.getSpecialityID());
        statement.setInt(3, vacancy.getExperience());
        statement.setInt(4, vacancy.getSalary());
        statement.setInt(5, vacancy.getId());
        statement.executeUpdate();
        statement.close();
    }

    public static void addVacancy(Vacancy vacancy) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO VACANCY VALUES (VACANCY_ID_SEQ.NEXTVAL, ?, ?, ?, ?)");
        statement.setInt(1, vacancy.getEnterpriseID());
        statement.setInt(2, vacancy.getSpecialityID());
        statement.setInt(3, vacancy.getExperience());
        statement.setInt(4, vacancy.getSalary());
        statement.execute();
        statement.close();
    }

    public static void removeVacancy(int id) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM VACANCY WHERE ID = ?");
        statement.setInt(1, id);
        statement.execute();
        statement.close();
    }
}
