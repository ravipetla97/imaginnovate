package com.example.imaginnovate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/employees")

public class EmployeeController {


    @Autowired
    EmpRepo empRepo ;
    @PostMapping
    public ResponseEntity<String> storeEmployeeDetails( @RequestBody Employee employee,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid employee data");
        }

        empRepo.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body("Employee details stored successfully");
    }
    @GetMapping("/tax-deductions/{empId}")
    public EmployeeTax getTaxDeductionsForCurrentYear(@PathVariable("empId") int empId) {
        EmployeeTax emptax = new EmployeeTax();


        Optional<Employee> employee = empRepo.findById(empId);

        // Calculate tax deductions for each employee

            double taxDeduction = calculateTaxDeduction(employee.get().getSalary());



            emptax.setEmployeeID(employee.get().getEmployeeId());
            emptax.setFirstName(employee.get().getFirstName());
            emptax.setLastName(employee.get().getLastName());
            emptax.setYearlySalary(employee.get().getSalary() * 12);
            emptax.setTaxAmount(taxDeduction);
            emptax.setCessAmount(taxDeduction * 0.04);


            return emptax;
        }
    public double calculateTaxDeduction(double salary) {
        double yearlySalary = salary * 12;
        double taxDeduction = 0;

        if (yearlySalary > 1000000) {
            taxDeduction = (yearlySalary - 1000000) * 0.2 + 500000 * 0.1 + 250000 * 0.05;
        } else if (yearlySalary > 500000) {
            taxDeduction = (yearlySalary - 500000) * 0.1 + 250000 * 0.05;
        } else if (yearlySalary > 250000) {
            taxDeduction = (yearlySalary - 250000) * 0.05;
        }

        return taxDeduction;
    }
}

