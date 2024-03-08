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
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement();
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
        try (PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM employees WHERE id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                employee = createEmployee(rs);
            }
        }

        return employee;
    }

    @Override
    public void save(Employee employee) {

    }

    @Override
    public void update(Employee employee) {

    }

    @Override
    public void delete(Integer id) {

    }

    private static Employee createEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setFirst_name(rs.getString("first_name"));
        employee.setPa_surname(rs.getString("pa_surname"));
        employee.setMa_surname(rs.getString("ma_surname"));
        employee.setEmail(rs.getString("email"));
        employee.setSalary(rs.getFloat("salary"));
        return employee;
    }
}
