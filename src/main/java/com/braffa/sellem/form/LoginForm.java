package com.braffa.sellem.form;

import java.util.Date;

import javax.validation.constraints.Size;

public class LoginForm {

	@Size(min = 5, max = 20, message = "User Name must between 5 to 20 Characters.")
	private String userName;

	@Size(min = 4, max = 10, message = "Password must between 6 to 10 Characters.")
	private String password;
	
	private String authorityLevel;
	
	private Date crDate;
	
	private Date updDate;
	
	private String currentPage;


	public String getAuthorityLevel() {
		return authorityLevel;
	}

	public void setAuthorityLevel(String authorityLevel) {
		this.authorityLevel = authorityLevel;
	}

	public void setUserName(String userName) {

		this.userName = userName;

	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public Date getCrDate() {
		return crDate;
	}

	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
}