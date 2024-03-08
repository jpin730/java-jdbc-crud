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

            System.out.println("All employees:");
            repository.findAll().forEach(System.out::println);
            System.out.println();

            Employee employee = new Employee();
            employee.setFirst_name("James");
            employee.setPa_surname("James");
            employee.setMa_surname("Smith");
            employee.setEmail("some@email.com");
            employee.setSalary(1000.0f);
            repository.save(employee);

            Integer id = employee.getId();

            System.out.println("Employee created:");
            System.out.println(repository.getById(id));
            System.out.println();

            employee.setFirst_name("John");
            repository.save(employee);

            System.out.println("Employee updated:");
            System.out.println(repository.getById(id));
            System.out.println();

            System.out.println("All employees:");
            repository.findAll().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred on main");
        }
    }
}