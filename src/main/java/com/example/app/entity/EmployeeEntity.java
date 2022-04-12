package com.example.app.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "wm_employee")
@Getter
@Setter
@SequenceGenerator(name = "IdGenerator", initialValue = 100000)
public class EmployeeEntity extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "contract_information", nullable = false)
    private int contractInformation;//in months

    @Column(name = "age", nullable = false)
    private int age;



}
