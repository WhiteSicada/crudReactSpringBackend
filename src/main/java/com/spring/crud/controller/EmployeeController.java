package com.spring.crud.controller;

import com.spring.crud.exception.RessourceNotFoundException;
import com.spring.crud.model.Employee;
import com.spring.crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // get All employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Employee does not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmloyee(@PathVariable Long id, @RequestBody Employee new_employee){
        // get the employee by id
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Employee does not exist with id :" + id));

        // set the new vars for the old employee into the new one
        employee.setFirstName(new_employee.getFirstName());
        employee.setLastName(new_employee.getLastName());
        employee.setEmail(new_employee.getEmail());

        // save = update
        Employee updatedEmployee = employeeRepository.save(employee);

        // return OK 200
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable Long id){
        try {
            employeeRepository.deleteById(id);
        }catch (Exception exception){
            return exception.getMessage();
        }
        return null;
    }

}
