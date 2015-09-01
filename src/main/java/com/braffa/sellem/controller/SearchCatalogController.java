package com.braffa.sellem.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.braffa.sellem.form.CatalogForm;
import com.braffa.sellem.form.SearchCatalogForm;
import com.braffa.sellem.model.xml.webserviceobjects.product.Catalog;
import com.braffa.sellem.model.xml.webserviceobjects.product.Product;

import com.braffa.sellem.xml.parser.ConvertXMLToObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Controller
@SessionAttributes
public class SearchCatalogController {

	private static final Logger logger = Logger
			.getLogger(SearchCatalogController.class);

	@RequestMapping("/searchCatalogStart")
	public String searchCatalogStart(Map<String, SearchCatalogForm> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("searchCatalog");
		}
		SearchCatalogForm searchCatalogForm = new SearchCatalogForm();
		searchCatalogForm.setCurrentPage("searchCatalogue");
		model.put("searchCatalogForm", searchCatalogForm);
		return "searchCatalogue";
	}

	@RequestMapping(value = "/searchCatalog", method = RequestMethod.POST)
	public String search(@Valid SearchCatalogForm searchForm,
			BindingResult result, Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		if (result.hasErrors()) {
			return "searchCatalogue";
		}
		if (searchForm.getAuthor().length() == 0
				&& searchForm.getManufacturer().length() == 0
				&& searchForm.getProductid().length() == 0
				&& searchForm.getTitle().length() == 0) {
			result.addError(new FieldError("SearchCatalogForm", "author",
					"At least one field must have a value"));
			return "searchCatalogue";
		}
		String searchField = "";
		String searchValue = "";
		
		if (searchForm.getAuthor().length() > 0) {
			searchField = "author";
			searchValue = searchForm.getAuthor();
		}
		if (searchForm.getManufacturer().length() > 0) {
			searchField = "manufacturer";
			searchValue = searchForm.getManufacturer();
		}
		if (searchForm.getProductid().length() > 0) {
			searchField = "productid";
			searchValue = searchForm.getProductid();
		}
		if (searchForm.getTitle().length() > 0) {
			searchField = "title";
			searchValue = searchForm.getTitle();
		}
		
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		String xml = service.path("rest").path("catalog").path(searchValue).path(searchField)
				.accept(MediaType.APPLICATION_XML).get(String.class);
		
		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xml);
		Catalog catalog = convertXMLToObject.catalogXMLFileToObjects();
		
		List<Product> lOfProducts = catalog.getProducts().getlOfProducts();
		CatalogForm catalogForm = new CatalogForm();
		//catalogForm.setmOfProducts(lOfProducts);
		catalogForm.setShowLinks(false);
		catalogForm.setHeader("Search Results");
		catalogForm.setCurrentPage("searchResults");
		model.put("catalogForm", catalogForm);
		return "catalog";
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/sellemws").build();
	}
}
