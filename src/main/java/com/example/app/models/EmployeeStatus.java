package com.example.app.models;


import com.example.app.entity.AuditEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Builder
public class EmployeeStatus {

    private long empId;
    private String status;
    private String event;

}
