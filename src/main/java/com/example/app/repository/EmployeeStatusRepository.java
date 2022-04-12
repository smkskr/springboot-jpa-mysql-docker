package com.example.app.repository;

import com.example.app.entity.EmployeeStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatusEntity, Long> {

    @Query(nativeQuery = true, value="select * from wm_employee_status where emp_id = :empId")
    EmployeeStatusEntity findEmployeeStatusById(@Param(value = "empId")Long empId);
}
