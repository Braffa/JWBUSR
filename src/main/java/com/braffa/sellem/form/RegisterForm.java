package com.braffa.sellem.form;

//import org.hibernate.validator.constraints.NotEmpty;

public class RegisterForm {

	//@NotEmpty
    private String email;
	
	//@NotEmpty
    private String firstname;
	
	//@NotEmpty
    private String lastname;
	
	//@NotEmpty
	private String password;
	
	//@NotEmpty
    private String telephone;
	
	//@NotEmpty
	private String userId;
	
	private String currentPage;
    
    public RegisterForm() {
    }
    
    public RegisterForm(String firstname, String lastname, String email, String telephone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.telephone = telephone;
    }
    
    public RegisterForm(String email, String firstname, String lastname, String password, String telephone, String userId) {
    	this.email = email;
    	this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.telephone = telephone;
        this.userId = userId;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

}