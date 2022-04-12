package com.example.app.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
@Builder
public class EmployeeResponse {

    private long empId;
    private String firstName;
    private String lastName;
    private int age;
    private int contractInformationInMonths;
    private String status;
    private List<String> subStatus;
    private Long createdAt;
    private Long updatedAt;


}
