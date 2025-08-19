
package com.app.kyc.model;
import java.util.Date;


public class AnomalyDTO {
    private Long id;
    private String firstName;
    private String lastName;

    private String middleName;
    private Date birthDate;
    private String address;
    private String nationality;
    private Date registrationDate;
    private String msisdn;

    private String alternateMsidn1;
    private String alternateMsidn2;

    private String idCardType;
    private String idCardNumber;


    private String birthPlace;
    private String identificationNumber;
    private String identificationType;
    private Date identityValitidyDate;

    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getAlternateMsidn1() {
        return alternateMsidn1;
    }

    public void setAlternateMsidn1(String alternateMsidn1) {
        this.alternateMsidn1 = alternateMsidn1;
    }

    public String getAlternateMsidn2() {
        return alternateMsidn2;
    }

    public void setAlternateMsidn2(String alternateMsidn2) {
        this.alternateMsidn2 = alternateMsidn2;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public Date getIdentityValitidyDate() {
        return identityValitidyDate;
    }

    public void setIdentityValitidyDate(Date identityValitidyDate) {
        this.identityValitidyDate = identityValitidyDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

