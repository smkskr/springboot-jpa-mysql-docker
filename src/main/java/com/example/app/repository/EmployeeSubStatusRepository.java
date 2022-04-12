package com.example.app.repository;

import com.example.app.entity.EmployeeSubStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeSubStatusRepository extends JpaRepository<EmployeeSubStatusEntity,Long> {

    @Query(nativeQuery = true, value = "select * from wm_employee_substatus where emp_id=:empId")
    List<EmployeeSubStatusEntity> findAllByEmpId(@Param("empId") long empId);

    @Query(nativeQuery = true, value = "select * from wm_employee_substatus where emp_id=:empId and event=:event")
    EmployeeSubStatusEntity findByEmpIdandEvent(@Param("empId") long empId, @Param("event") String event);
}
