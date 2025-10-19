package com.divarizky.employeemanagement.service;

import com.divarizky.employeemanagement.model.Employee;
import com.divarizky.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // mengambil data seluruh pegawai
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // mengambil data pegawai sesuai Id
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // tambah data pegawai
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Email sudah terpakai: " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    // ubah data pegawai
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        if (!employee.getEmail().equals(employeeDetails.getEmail()) &&
                employeeRepository.existsByEmail(employeeDetails.getEmail())) {
            throw new RuntimeException("Email already exists: " + employeeDetails.getEmail());
        }

        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPosition(employeeDetails.getPosition());
        employee.setSalary(employeeDetails.getSalary());

        return employeeRepository.save(employee);
    }

    // hapus data pegawai
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id pegawai tidak ditemukan: " + id));
        employeeRepository.delete(employee);
    }
}
