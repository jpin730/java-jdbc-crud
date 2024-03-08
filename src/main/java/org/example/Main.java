package org.example;


import org.example.models.Employee;
import org.example.repository.EmployeeRepository;
import org.example.repository.Repository;
import org.example.utils.DatabaseConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Repository<Employee> repository = new EmployeeRepository();
            repository.findAll().forEach(System.out::println);
            // System.out.println(repository.getById(1));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred on main");
        }
    }
}