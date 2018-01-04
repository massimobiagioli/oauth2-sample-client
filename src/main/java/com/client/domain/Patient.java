package com.client.domain;

import com.client.domain.enums.Gender;
import com.client.domain.enums.Status;

public class Patient {
	private Long patientId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Status status;
    private String medicalOrder;
    private String dosage;
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
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
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getMedicalOrder() {
		return medicalOrder;
	}
	public void setMedicalOrder(String medicalOrder) {
		this.medicalOrder = medicalOrder;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
    
    
}
