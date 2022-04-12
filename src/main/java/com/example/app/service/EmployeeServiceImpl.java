package com.example.app.service;

import com.example.app.entity.EmployeeEntity;
import com.example.app.entity.EmployeeStatusEntity;
import com.example.app.entity.EmployeeSubStatusEntity;
import com.example.app.enums.Events;
import com.example.app.enums.SubStatus;
import com.example.app.models.EmployeeResponse;
import com.example.app.models.EmployeeStatus;
import com.example.app.repository.*;
import com.example.app.service.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeStatusRepository employeeStatusRepository;

    @Autowired
    private EmployeeSubStatusRepository employeeSubStatusRepository;


    @Override
    public EmployeeEntity addEmployee(EmployeeEntity employeeEntity) {
        EmployeeEntity emp = employeeRepository.save(employeeEntity);
        EmployeeStatusEntity employeeStatusEntity = EmployeeStatusEntity.builder()
                .empId(emp.getId())
                .status(SubStatus.ADDED.toString())
                .issubStatus(0)
                .build();
        employeeStatusRepository.save(employeeStatusEntity);
        return emp;
    }

    @Transactional
    @Override
    public String updateEmployeeStatus(EmployeeStatus employeeStatus) {

        //get previous/existing status of the employee
        EmployeeStatusEntity employeeStatusEntity = employeeStatusRepository.findEmployeeStatusById(employeeStatus.getEmpId());

        //employeeStatusRepository.findEmployeeStatusById();
        EmployeeSubStatusEntity employeeSubStatusEntity = new EmployeeSubStatusEntity();

        //if the employee has a substatus then fetch employee substatus by emp id and event
        if(employeeStatusEntity.getIssubStatus() == 1){
            employeeSubStatusEntity = employeeSubStatusRepository.findByEmpIdandEvent(employeeStatus.getEmpId(), employeeStatus.getEvent());
        }


        if(employeeSubStatusEntity != null && employeeSubStatusEntity.getSubStatus() != null)
            employeeStatus.setStatus(employeeSubStatusEntity.getSubStatus());
        else employeeStatus.setStatus(employeeStatusEntity.getStatus());

        return validateStateChange(employeeStatus.getStatus(),employeeStatus.getEvent(), employeeStatusEntity, employeeSubStatusEntity);

    }

    @Override
    public void approvedStateCheck(EmployeeStatusEntity employeeStatusEntity) {
        List<EmployeeSubStatusEntity> employeeSubStatusEntityList = employeeSubStatusRepository.findAllByEmpId(employeeStatusEntity.getEmpId());
        Set<String> set = new HashSet<>();
        for(EmployeeSubStatusEntity employeeSubStatusEntity : employeeSubStatusEntityList){
            set.add(employeeSubStatusEntity.getSubStatus());
        }
        if(set.contains(SubStatus.SECURITY_CHECK_FINISHED.toString()) && set.contains(SubStatus.WORK_PERMIT_CHECK_FINISHED.name())){
            employeeStatusEntity.setStatus(SubStatus.APPROVED.name());
            employeeStatusEntity.setIssubStatus(0);
        }
    }

    @Override
    public String validateStateChange(String status, String event, EmployeeStatusEntity employeeStatusEntity, EmployeeSubStatusEntity employeeSubStatusEntity) {


        String success = "";
        try{
            switch(Events.valueOf(event)){
                case INCHECK: if(status.equals(SubStatus.ADDED.toString())){changeStatusToInCheck(SubStatus.INCHECK.name(),employeeStatusEntity);success = "State change successful";}break;
                case SECURITY_CHECK: if(status.equals(SubStatus.SECURITY_CHECK_STARTED.toString())){changeSubStatus(SubStatus.SECURITY_CHECK_FINISHED.name(), employeeStatusEntity, employeeSubStatusEntity, SubStatus.INCHECK.name());success = "State change successful";}break;
                case WORK_PERMIT_CHECK: if(status.equals(SubStatus.WORK_PERMIT_CHECK_STARTED.toString())){changeSubStatus(SubStatus.WORK_PERMIT_CHECK_FINISHED.name(), employeeStatusEntity, employeeSubStatusEntity, SubStatus.INCHECK.name());success = "State change successful";}break;
                case ACTIVE: if(status.equals(SubStatus.APPROVED.toString())){employeeStatusEntity.setStatus(SubStatus.ACTIVE.name());success = "State change successful";}break;
//                default: return "INVALID EVENT";
            }
        }catch (Exception e){
            return "INVALID EVENT";
        }

        return success == "" ? "INVALID EVENT" : success;
    }

    @Override
    public void changeSubStatus(String status, EmployeeStatusEntity employeeStatusEntity, EmployeeSubStatusEntity employeeSubStatusEntity, String parentStatus) {
        employeeStatusEntity.setStatus(parentStatus);
        employeeSubStatusEntity.setSubStatus(status);
        approvedStateCheck(employeeStatusEntity);
    }

    @Override
    public void changeStatusToInCheck(String status, EmployeeStatusEntity employeeStatusEntity) {

        employeeStatusEntity.setStatus(status);
        employeeStatusEntity.setIssubStatus(1);
        EmployeeSubStatusEntity employeeSubStatusEntity1 = EmployeeSubStatusEntity.builder()
                .event(Events.SECURITY_CHECK.toString())
                .subStatus(SubStatus.SECURITY_CHECK_STARTED.toString())
                .empId(employeeStatusEntity.getEmpId())
                .build();
        EmployeeSubStatusEntity employeeSubStatusEntity2 = EmployeeSubStatusEntity.builder()
                .event(Events.WORK_PERMIT_CHECK.toString())
                .subStatus(SubStatus.WORK_PERMIT_CHECK_STARTED.toString())
                .empId(employeeStatusEntity.getEmpId())
                .build();
        employeeSubStatusRepository.save(employeeSubStatusEntity1);
        employeeSubStatusRepository.save(employeeSubStatusEntity2);

    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        //List<EmployeeSubStatusEntity employeeSubStatusEntityList =
        for(EmployeeEntity employeeEntity : employeeEntityList){

            List<String> subStatus = new ArrayList<>();
            EmployeeStatusEntity employeeStatusEntity = employeeStatusRepository.findEmployeeStatusById(employeeEntity.getId());

            if(employeeStatusEntity.getIssubStatus() == 1){
                List<EmployeeSubStatusEntity> employeeSubStatusEntityList = employeeSubStatusRepository.findAllByEmpId(employeeEntity.getId());
                for(EmployeeSubStatusEntity employeeSubStatusEntity : employeeSubStatusEntityList){
                    subStatus.add(employeeSubStatusEntity.getSubStatus());
                }
            }

            EmployeeResponse employeeResponse = EmployeeResponse.builder()
                    .empId(employeeEntity.getId())
                    .firstName(employeeEntity.getFirstName())
                    .lastName(employeeEntity.getLastName())
                    .age(employeeEntity.getAge())
                    .contractInformationInMonths(employeeEntity.getContractInformation())
                    .status(employeeStatusEntity.getStatus())
                    .subStatus(subStatus)
                    .createdAt(employeeEntity.getCreatedAt())
                    .updatedAt(employeeEntity.getUpdatedAt())
                    .build();
            employeeResponses.add(employeeResponse);
        }
        return employeeResponses;
    }

    @Override
    public EmployeeResponse getEmployee(long empId) {
        EmployeeEntity employeeEntity = employeeRepository.getById(empId);
        List<String> subStatus = new ArrayList<>();
        EmployeeStatusEntity employeeStatusEntity = employeeStatusRepository.findEmployeeStatusById(employeeEntity.getId());

        if(employeeStatusEntity.getIssubStatus() == 1){
            List<EmployeeSubStatusEntity> employeeSubStatusEntityList = employeeSubStatusRepository.findAllByEmpId(employeeEntity.getId());
            for(EmployeeSubStatusEntity employeeSubStatusEntity : employeeSubStatusEntityList){
                subStatus.add(employeeSubStatusEntity.getSubStatus());
            }
        }
        EmployeeResponse employeeResponse = EmployeeResponse.builder()
                .empId(employeeEntity.getId())
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .age(employeeEntity.getAge())
                .contractInformationInMonths(employeeEntity.getContractInformation())
                .status(employeeStatusEntity.getStatus())
                .subStatus(subStatus)
                .createdAt(employeeEntity.getCreatedAt())
                .updatedAt(employeeEntity.getUpdatedAt())
                .build();
        return employeeResponse;
    }
}
