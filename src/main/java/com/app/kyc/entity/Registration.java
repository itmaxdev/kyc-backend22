package com.app.kyc.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "registration")
@Setter
@Getter
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String msisdn;
    private String regDate;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String dob;
    private String placeOfBirth;
    private String address;
    private String msisdn1;
    private String msisdn2;
    private String cardType;
    private String cardId;




}

