package com.braffa.sellem.form;

import java.util.Date;

public class RegisteredUserToProduct {

	private Date crDate;

	private String email;

	private String productId;

	private String productIndex;

	private String userId;

	public RegisteredUserToProduct() {

	}

	public RegisteredUserToProduct(Date crDate, String email, String productId,
			String productIndex, String userId) {
		super();
		this.crDate = crDate;
		this.email = email;
		this.productId = productId;
		this.productIndex = productIndex;
		this.userId = userId;
	}

	public Date getCrDate() {
		return crDate;
	}

	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductIndex() {
		return productIndex;
	}

	public void setProductIndex(String productIndex) {
		this.productIndex = productIndex;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
