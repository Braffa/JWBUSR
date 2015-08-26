package com.braffa.sellem.form;

import java.util.List;



public class RegistrationForm {
	
    private List<RegisterForm> registeredDetails;
    
	private String currentPage;

	public List<RegisterForm> getRegisteredDetails() {
		return registeredDetails;
	}

	public void setRegisteredDetails(List<RegisterForm> registeredDetails) {
		this.registeredDetails = registeredDetails;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
    

}
