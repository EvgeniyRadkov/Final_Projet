package com.gmail.vanyasudnishnikov.employee.repository;

import com.gmail.vanyasudnishnikov.employee.repository.model.Employee;

import java.util.List;

public interface EmployeeRepository extends GenericRepository<Long, Employee> {
    List<Employee> find(String name, String ibanByn, String ibanCurrency);

    List<Employee> search(List<Long> legalIdList, String nameIndividual);
}
