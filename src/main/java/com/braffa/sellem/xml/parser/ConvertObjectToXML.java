/*
	http://www.mkyong.com/java/jaxb-hello-world-example/
 */

package com.braffa.sellem.xml.parser;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.braffa.sellem.model.xml.webserviceobjects.authentication.Login;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUser;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUsers;
import com.braffa.sellem.model.xml.webserviceobjects.product.Product;
import com.braffa.sellem.model.xml.webserviceobjects.product.Products;


public class ConvertObjectToXML {

	private String fileName = "test.xml";

	private File XMLFile;

	private Login login;

	private Product product;

	private Products products;

	private RegisteredUser registeredUser;

	private RegisteredUsers registeredUsers;

	public ConvertObjectToXML() {

	}

	public ConvertObjectToXML(Login login) {
		this.login = login;
	}

	public ConvertObjectToXML(Login login, String filename) {
		this.login = login;
		this.fileName = filename;
	}

	public ConvertObjectToXML(Product product) {
		this.product = product;
	}

	public ConvertObjectToXML(Product product, String filename) {
		this.product = product;
		this.fileName = filename;
	}

	public ConvertObjectToXML(Products products) {
		this.products = products;
	}

	public ConvertObjectToXML(Products products, String filename) {
		this.products = products;
		this.fileName = filename;
	}

	public ConvertObjectToXML(RegisteredUser registeredUser) {
		this.registeredUser = registeredUser;
	}

	public ConvertObjectToXML(RegisteredUser registeredUser,
			String filename) {
		this.registeredUser = registeredUser;
		this.fileName = filename;
	}

	public ConvertObjectToXML(RegisteredUsers registeredUsers,
			String filename) {
		this.registeredUsers = registeredUsers;
		this.fileName = filename;
	}

	public ConvertObjectToXML(RegisteredUsers registeredUsers) {
		this.registeredUsers = registeredUsers;
	}

	public void convertLoginToXMLFile() {
		File file = new File(fileName);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Login.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(login, file);
			jaxbMarshaller.marshal(login, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void convertProductToXMLFile() {
		File file = new File(fileName);
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(Product.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(product, file);
			jaxbMarshaller.marshal(product, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void convertProductsToXMLFile() {
		File file = new File(fileName);
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(Products.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(products, file);
			jaxbMarshaller.marshal(products, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void convertRegisteredUserToXMLFile() {
		File file = new File(fileName);
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RegisteredUser.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(registeredUser, file);
			jaxbMarshaller.marshal(registeredUser, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void convertRegisteredUsersToXMLFile() {
		File file = new File(fileName);
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RegisteredUsers.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(registeredUsers, file);
			jaxbMarshaller.marshal(registeredUsers, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public String convertRegisteredUsersToString() {
		File file = new File(fileName);
		StringWriter st = new StringWriter();
		String listRequestStr = "";
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RegisteredUsers.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(registeredUsers, st);
			listRequestStr = st.toString();
			jaxbMarshaller.marshal(registeredUsers, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return listRequestStr;
	}
}