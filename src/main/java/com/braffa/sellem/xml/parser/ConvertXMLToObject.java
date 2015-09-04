package com.braffa.sellem.xml.parser;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.braffa.sellem.model.xml.authentication.XmlLoginMsg;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUserMsg;
import com.braffa.sellem.model.xml.product.XmlProductMsg;
import com.braffa.sellem.model.xml.product.XmlUserToProductMsg;
import com.braffa.sellem.model.xml.product.XmlUsersProductMsg;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Login;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Register;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUser;
import com.braffa.sellem.model.xml.webserviceobjects.product.Catalog;
import com.braffa.sellem.model.xml.webserviceobjects.product.ProductToUsers;

public class ConvertXMLToObject {

	private String xmlInput;

	public ConvertXMLToObject(String xmlInput) {
		this.xmlInput = xmlInput;
	}
	
	public XmlLoginMsg xmlloginMsgToObjects() {
		XmlLoginMsg login = null;
		try {
			StringReader reader = new StringReader(xmlInput);
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlLoginMsg.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			login = (XmlLoginMsg) jaxbUnmarshaller.unmarshal(reader);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return login;
	}
	
	public XmlUsersProductMsg xmlUsersProductMsgToObjects () {
		try {
			StringReader reader = new StringReader(xmlInput);
			JAXBContext jaxbContext = JAXBContext
					.newInstance(XmlUsersProductMsg.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			XmlUsersProductMsg xmlUsersProductMsg = (XmlUsersProductMsg) jaxbUnmarshaller.unmarshal(reader);
			return xmlUsersProductMsg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public XmlRegisteredUserMsg xmlRegisteredUserMsgToObjects() {
		XmlRegisteredUserMsg registeredUserMsg = null;
		try {
			StringReader reader = new StringReader(xmlInput);
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlRegisteredUserMsg.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			registeredUserMsg = (XmlRegisteredUserMsg) jaxbUnmarshaller.unmarshal(reader);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return registeredUserMsg;
	}
	
	public XmlProductMsg xmlProductMsgToObjects() {
		XmlProductMsg productMsg = null;
		try {
			StringReader reader = new StringReader(xmlInput);
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlProductMsg.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			productMsg = (XmlProductMsg) jaxbUnmarshaller.unmarshal(reader);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return productMsg;
	}	
	
	public XmlUserToProductMsg xmlUserToProductMsgToObjects() {
		XmlUserToProductMsg userToProductMsg = null;
		try {
			StringReader reader = new StringReader(xmlInput);
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlUserToProductMsg.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			userToProductMsg = (XmlUserToProductMsg) jaxbUnmarshaller.unmarshal(reader);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return userToProductMsg;
	}	

	public Login loginXMLFileToObjects() {
		Login login = null;
		try {
			StringReader reader = new StringReader(xmlInput);
			JAXBContext jaxbContext = JAXBContext.newInstance(Login.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			login = (Login) jaxbUnmarshaller.unmarshal(reader);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return login;
	}

	public Catalog catalogXMLFileToObjects() {
		Catalog catalog = null;
		try {
			StringReader reader = new StringReader(xmlInput);
			JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			catalog = (Catalog) jaxbUnmarshaller.unmarshal(reader);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return catalog;
	}

	public RegisteredUser registeredUserXMLFileToObjects() {
		RegisteredUser registeredUser = null;
		try {
			StringReader reader = new StringReader(xmlInput);
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RegisteredUser.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			registeredUser = (RegisteredUser) jaxbUnmarshaller
					.unmarshal(reader);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return registeredUser;
	}

	public Register registeredUsersXMLFileToObjects() {

		Register register = null;
		try {
			StringReader reader = new StringReader(xmlInput);
			JAXBContext jaxbContext = JAXBContext.newInstance(Register.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			register = (Register) jaxbUnmarshaller.unmarshal(reader);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return register;
	}

	public ProductToUsers productToUsersXMLFileToObjects() {
		ProductToUsers productToUsers = null;
		try {
			StringReader reader = new StringReader(xmlInput);
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ProductToUsers.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			productToUsers = (ProductToUsers) jaxbUnmarshaller
					.unmarshal(reader);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return productToUsers;
	}
}
