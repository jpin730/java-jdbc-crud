package org.example.view;

import org.example.models.Employee;
import org.example.repository.EmployeeRepository;
import org.example.repository.Repository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class SwingApp extends JFrame {
    private final Repository<Employee> employeeRepository;
    private final JTable employeeTable;

    public SwingApp() {
        setTitle("Employee Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 300);
        setLocationRelativeTo(null);

        employeeTable = new JTable();
        employeeTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        employeeRepository = new EmployeeRepository();

        refreshEmployeeTable();

        addButton.addActionListener(e -> {
            try {
                addEmployee();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        updateButton.addActionListener(e -> {
            try {
                updateEmployee();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                deleteEmployee();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void refreshEmployeeTable() {
        try {
            List<Employee> employees = employeeRepository.findAll();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Id");
            model.addColumn("First Name");
            model.addColumn("Last Name");
            model.addColumn("Email");
            model.addColumn("Salary");

            for (Employee employee : employees) {
                model.addRow(new Object[]{employee.getId(), employee.getFirst_name(), employee.getLast_name(), employee.getEmail(), employee.getSalary()});
            }

            employeeTable.setModel(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addEmployee() throws SQLException {
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField salaryField = new JTextField();

        Object[] fields = {
                "First Name:", firstNameField,
                "Last Name:", lastNameField,
                "Email:", emailField,
                "Salary:", salaryField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add Employee", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Employee employee = new Employee();
            employee.setFirst_name(firstNameField.getText());
            employee.setLast_name(lastNameField.getText());
            employee.setEmail(emailField.getText());

            try {
                employee.setSalary(Float.parseFloat(salaryField.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid salary", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                employeeRepository.save(employee);
            } catch (SQLIntegrityConstraintViolationException e) {
                String message = e.getMessage();
                JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            refreshEmployeeTable();

            JOptionPane.showMessageDialog(this, "Employee added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateEmployee() throws SQLException {
        int row = employeeTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee to update");
            return;
        }

        Integer id = (Integer) employeeTable.getValueAt(row, 0);
        Employee employee = employeeRepository.getById(id);

        JTextField firstNameField = new JTextField(employee.getFirst_name());
        JTextField lastNameField = new JTextField(employee.getLast_name());
        JTextField emailField = new JTextField(employee.getEmail());
        JTextField salaryField = new JTextField(employee.getSalary().toString());

        Object[] fields = {
                "First Name:", firstNameField,
                "Last Name:", lastNameField,
                "Email:", emailField,
                "Salary:", salaryField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Update Employee", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            employee.setFirst_name(firstNameField.getText());
            employee.setLast_name(lastNameField.getText());
            employee.setEmail(emailField.getText());

            try {
                employee.setSalary(Float.parseFloat(salaryField.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid salary", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                employeeRepository.save(employee);
            } catch (SQLIntegrityConstraintViolationException e) {
                String message = e.getMessage();
                JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            refreshEmployeeTable();

            JOptionPane.showMessageDialog(this, "Employee updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteEmployee() throws SQLException {
        int row = employeeTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee to delete");
            return;
        }

        Integer id = (Integer) employeeTable.getValueAt(row, 0);
        employeeRepository.delete(id);
        refreshEmployeeTable();

        JOptionPane.showMessageDialog(this, "Employee deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
