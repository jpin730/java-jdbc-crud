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
        try (Statement stmt = getConnection().createStatement();
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
    public void save(Employee employee) throws SQLException {
        String sql;
        if (employee.getId() == null) {
            sql = "INSERT INTO employees (first_name, pa_surname, ma_surname, email, salary) VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE employees SET first_name = ?, pa_surname = ?, ma_surname = ?, email = ?, salary = ? WHERE id = ?";
        }
        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, employee.getFirst_name());
            ps.setString(2, employee.getPa_surname());
            ps.setString(3, employee.getMa_surname());
            ps.setString(4, employee.getEmail());
            ps.setFloat(5, employee.getSalary());
            if (employee.getId() != null) {
                ps.setInt(6, employee.getId());
            }
            //specify Statement.RETURN_GENERATED_KEYS to get the id of the new employee
            ps.executeUpdate();

            if (employee.getId() == null) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        employee.setId(rs.getInt(1));
                    }
                }
            }
        }
    }


    @Override
    public void delete(Integer id) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement("DELETE FROM employees WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
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
