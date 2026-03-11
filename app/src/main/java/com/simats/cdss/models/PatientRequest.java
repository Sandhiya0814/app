package com.simats.cdss.models;

public class PatientRequest {
    private String full_name;
    private String dob;
    private String sex;
    private String ward;
    private String bed_number;

    public PatientRequest(String full_name, String dob, String sex, String ward, String bed_number) {
        this.full_name = full_name;
        this.dob = dob;
        this.sex = sex;
        this.ward = ward;
        this.bed_number = bed_number;
    }
}
