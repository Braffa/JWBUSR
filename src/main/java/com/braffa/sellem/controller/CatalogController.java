package com.braffa.sellem.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.braffa.sellem.form.CatalogForm;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Login;
import com.braffa.sellem.model.xml.webserviceobjects.product.Catalog;
import com.braffa.sellem.model.xml.webserviceobjects.product.Product;

import com.braffa.sellem.xml.parser.ConvertXMLToObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Controller
@SessionAttributes("loggedin")
public class CatalogController {

	private static final Logger logger = Logger
			.getLogger(CatalogController.class);

	@RequestMapping("/getCatalog.html")
	public String getCatalog(Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		String xml = service.path("rest").path("catalog")
				.accept(MediaType.APPLICATION_XML).get(String.class);

		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xml);
		
		Catalog catalog = convertXMLToObject.catalogXMLFileToObjects();
		List<Product> lOfProducts = catalog.getProducts().getlOfProducts();
		CatalogForm catalogForm = new CatalogForm();
		catalogForm.setmOfProducts(lOfProducts);
		catalogForm.setShowLinks(false);
		catalogForm.setHeader("Full Catalogue");
		catalogForm.setCurrentPage("fullCatalogue");
		model.put("catalogForm", catalogForm);
		return "catalog";
	}
	
	@RequestMapping("/myCatalog.html")
	public Object myCatalog(Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Login login = (Login)model.get("loggedin");
		if (login == null || login.getUserId() == null) {
			return new ModelAndView("redirect:homepage.html");
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		String xml = service.path("rest").path("catalog").path(login.getUserId())
				.accept(MediaType.APPLICATION_XML).get(String.class);
		
		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xml);
		Catalog catalog = convertXMLToObject.catalogXMLFileToObjects();
		
		List<Product> lOfProducts = catalog.getProducts().getlOfProducts();
		CatalogForm catalogForm = new CatalogForm();
		catalogForm.setmOfProducts(lOfProducts);
		catalogForm.setShowLinks(true);
		catalogForm.setHeader("My Catalogue");
		catalogForm.setOrigin("");
		catalogForm.setCurrentPage("myCatalogue");
		model.put("catalogForm", catalogForm);
		return "catalog";
	}

	@RequestMapping(value = "/catalog", method = RequestMethod.POST)
	public String catalog() {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}

		return "redirect:login.html";
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/sellemws").build();
	}

}
