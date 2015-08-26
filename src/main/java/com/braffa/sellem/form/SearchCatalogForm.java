package com.braffa.sellem.form;

public class SearchCatalogForm {

    private String author;
    private String manufacturer;
    private String productid;
	private String title;
	
	private String currentPage;
    
    public SearchCatalogForm() {
    }
    
    public SearchCatalogForm(String author, String manufacturer, String productid, String title) {
        this.author = author;
        this.manufacturer = manufacturer;
        this.productid = productid;
        this.title = title;
    }

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
    
}