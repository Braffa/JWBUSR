package com.braffa.sellem.form;

public class ProductForm {

	private static final long serialVersionUID = 1L;
	
	private String productid;
	private String productidtype;
	private String action;
	private String errorMessage;
	
	private String currentPage;

	public ProductForm () {
		
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductidtype() {
		return productidtype;
	}

	public void setProductidtype(String productidtype) {
		this.productidtype = productidtype;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

}
