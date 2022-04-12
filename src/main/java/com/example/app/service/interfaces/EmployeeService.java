package com.example.app.service.interfaces;

import com.example.app.entity.EmployeeEntity;
import com.example.app.entity.EmployeeStatusEntity;
import com.example.app.entity.EmployeeSubStatusEntity;
import com.example.app.models.EmployeeResponse;
import com.example.app.models.EmployeeStatus;

import java.util.List;

public interface EmployeeService {

    public EmployeeEntity addEmployee(EmployeeEntity employeeEntity);

    public String updateEmployeeStatus(EmployeeStatus employeeStatus);

    void approvedStateCheck(EmployeeStatusEntity employeeStatusEntity);

    String validateStateChange(String status, String event, EmployeeStatusEntity employeeStatusEntity, EmployeeSubStatusEntity employeeSubStatusEntity);

    void changeSubStatus(String status, EmployeeStatusEntity employeeStatusEntity, EmployeeSubStatusEntity employeeSubStatusEntity, String parentStatus);

    void changeStatusToInCheck(String status, EmployeeStatusEntity employeeStatusEntity);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployee(long empId);

//    @Transactional
//    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);

//    @Transactional
//    StateMachine<SubStatus, Events> validate(EmployeeStatus employeeStatus, Events event);

//    List<EmployeeResponse> getAllEmployees();
}
