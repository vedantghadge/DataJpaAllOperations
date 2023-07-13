package com.csi.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    private int empId;

    @Size(min = 2, message = "Name Should be atleast  4 charactor")
    private String empName;

    private String empAddress;

    @Column(unique = true)
    private long empContactNumber;

    private double empSalary;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date empDOB;

    @Email(message = "Email ID Must be Valid")
    @Column(unique = true)
    private String empEmailId;

    @Size(min = 4,message = "Name should br atleast 4 charactor")
    private String empPassword;


}
