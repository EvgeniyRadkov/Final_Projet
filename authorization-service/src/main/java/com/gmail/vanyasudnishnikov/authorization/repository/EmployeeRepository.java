package com.gmail.vanyasudnishnikov.authorization.repository;

import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;

import java.util.Optional;

public interface EmployeeRepository extends GenericRepository<String, Employee> {
    Optional<Employee> findByUserMail(String userMail);

}
