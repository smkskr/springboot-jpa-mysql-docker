package com.example.app.repository;

import com.example.app.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {


//    List<EmployeeEntity> findAllEmployees();
}
