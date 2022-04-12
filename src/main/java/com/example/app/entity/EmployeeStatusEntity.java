package com.example.app.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "wm_employee_status")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStatusEntity extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(name = "emp_id", nullable = false)
    private long empId;

    @Column(name = "status", nullable = false)
    private String status;


    @Column(name = "is_substatus")
    private int issubStatus;

}
