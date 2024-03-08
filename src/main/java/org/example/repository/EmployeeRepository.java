package org.example.repository;

import org.example.models.Employee;
import org.example.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<Employee> {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            while (rs.next()) {
                Employee employee = createEmployee(rs);
                employees.add(employee);
            }
        }

        return employees;
    }

    @Override
    public Employee getById(Integer id) throws SQLException {
        Employee employee = null;
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM employees WHERE id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                employee = createEmployee(rs);
            }
        }

        return employee;
    }

    @Override
    public void save(Employee employee) throws SQLException {
        String sql;
        if (employee.getId() == null) {
            sql = "INSERT INTO employees (first_name, last_name, email, salary) VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, salary = ? WHERE id = ?";
        }
        try (Connection connection = getConnection()) {
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }

            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, employee.getFirst_name());
                ps.setString(2, employee.getLast_name());
                ps.setString(3, employee.getEmail());
                ps.setFloat(4, employee.getSalary());
                if (employee.getId() != null) {
                    ps.setInt(5, employee.getId());
                }

                ps.executeUpdate();

                if (employee.getId() == null) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            employee.setId(rs.getInt(1));
                        }
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }


    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection connection = getConnection()) {
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }

            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM employees WHERE id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    private static Employee createEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setFirst_name(rs.getString("first_name"));
        employee.setLast_name(rs.getString("last_name"));
        employee.setEmail(rs.getString("email"));
        employee.setSalary(rs.getFloat("salary"));
        return employee;
    }
}
