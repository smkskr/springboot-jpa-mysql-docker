package com.example.app.controller;

import com.example.app.entity.EmployeeEntity;
import com.example.app.models.EmployeeResponse;
import com.example.app.models.EmployeeStatus;
import com.example.app.service.interfaces.EmployeeService;
import com.example.app.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<Object> addEmployee(@RequestBody EmployeeEntity employeeEntity){
        try{
            EmployeeEntity result = employeeService.addEmployee(employeeEntity);
            return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, result);
        }catch(Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/employeeStatus")
    public String updateEmployeeStatus(@RequestBody EmployeeStatus employeeStatus){
            return employeeService.updateEmployeeStatus(employeeStatus);
    }

    @GetMapping("/employees")
    public ResponseEntity<Object> getAllEmployees(){

        try{
            List<EmployeeResponse> result = employeeService.getAllEmployees();
            return ResponseHandler.generateResponse("Successfully fetched data!", HttpStatus.OK, result);
        }catch(Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<Object> getEmployee(@PathVariable long empId){

        try{
            EmployeeResponse result = employeeService.getEmployee(empId);
            return ResponseHandler.generateResponse("Successfully fetched data!", HttpStatus.OK, result);
        }catch(Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


}
