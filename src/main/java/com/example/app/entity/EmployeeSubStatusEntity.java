package com.example.app.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "wm_employee_substatus")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSubStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false, updatable = false)
    private long id;

    @Column(name="substatus", nullable = false)
    private String subStatus;

    @Column(name="emp_id", nullable = false)
    private long empId;

    @Column(name="event", nullable = false)
    private String event;



}
