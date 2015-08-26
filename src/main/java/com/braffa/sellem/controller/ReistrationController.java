package com.braffa.sellem.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.braffa.sellem.form.RegisterForm;
import com.braffa.sellem.form.RegistrationForm;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Login;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Register;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUser;
import com.braffa.sellem.validaters.EmailValidator;

import com.braffa.sellem.xml.parser.ConvertXMLToObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Controller
@SessionAttributes("loggedin")
public class ReistrationController {

	private static final Logger logger = Logger
			.getLogger(ReistrationController.class);

	@RequestMapping("/registerStart")
	public String registerStart(Map<String, RegisterForm> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("register");
		}
		RegisterForm registerForm = new RegisterForm();
		registerForm.setCurrentPage("register");
		model.put("registerForm", registerForm);
		return "register";
	}

	@RequestMapping(value = "/attemptoregister", method = RequestMethod.POST)
	public String registered(@Valid RegisterForm registeredDetails,
			BindingResult result, Map<String, RegisterForm> model, Model myModel) {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		if (result.hasErrors()) {
			return "register";
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		String xml = service.path("rest").path("registeredusers")
				.path(registeredDetails.getUserId())
				.accept(MediaType.APPLICATION_XML).get(String.class);

		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xml);
		RegisteredUser registeredUser = convertXMLToObject
				.registeredUserXMLFileToObjects();

		// does user id already exist
		if (registeredUser.getLogin() != null
				&& registeredUser.getLogin().getUserId()
						.equalsIgnoreCase(registeredDetails.getUserId())) {
			result.addError(new FieldError("registeredDetails", "userId",
					"Invalid userName" + " ( " + registeredDetails.getUserId()
							+ " already exists" + " )"));
			return "register";
		}
		// is email valid
		if (!validateEmailAddress(registeredDetails.getEmail())) {
			result.addError(new FieldError("registeredDetails", "email",
					"Invalid email" + " ( " + registeredDetails.getEmail()
							+ " please correct it" + " )"));
			return "register";
		}

		Login login = new Login("-99", registeredDetails.getPassword(),
				registeredDetails.getUserId());

		registeredUser = new RegisteredUser(registeredDetails.getEmail(),
				registeredDetails.getFirstname(),
				registeredDetails.getLastname(),
				registeredDetails.getTelephone(), login);

		ClientResponse response = service.path("rest").path("registeredusers")
				.path(registeredUser.getLogin().getUserId())
				.accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, registeredUser);

		if (logger.isDebugEnabled()) {
			logger.debug(response.getStatus());
		}
		return "redirect:login.html";
	}

	@RequestMapping(value = "/showRegisteredUsers")
	public Object showRegisteredUsers(Map<String, Object> model, Model myModel) {
		if (logger.isDebugEnabled()) {
			logger.debug("showRegisteredUsers");
		}
		Login login = (Login)model.get("loggedin");
		if (login == null || login.getUserId() == null) {
			return new ModelAndView("redirect:homepage.html");
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		String xml = service.path("rest").path("registeredusers")
				.accept(MediaType.APPLICATION_XML).get(String.class);

		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xml);
		Register register = convertXMLToObject
				.registeredUsersXMLFileToObjects();

		List<RegisteredUser> lOfRegisteredUser = register.getRegisteredUsers()
				.getlOfRegisteredUser();
		List<RegisterForm> lOfRegisteredDetails = new ArrayList<RegisterForm>();
		for (RegisteredUser registeredUser : lOfRegisteredUser) {
			RegisterForm registerForm = new RegisterForm(
					registeredUser.getEmail(), registeredUser.getFirstname(),
					registeredUser.getLastname(), registeredUser.getLogin()
							.getPassword(), registeredUser.getTelephone(),
					registeredUser.getLogin().getUserId());
			lOfRegisteredDetails.add(registerForm);
		}

		RegistrationForm registrationForm = new RegistrationForm();
		registrationForm.setRegisteredDetails(lOfRegisteredDetails);
		registrationForm.setCurrentPage("registeredUsers");
		model.put("registrationForm", registrationForm);
		return "registeredUsers";
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/sellemws").build();
	}

	private boolean validateEmailAddress(String email) {
		EmailValidator emailValidator = new EmailValidator();
		boolean valid = emailValidator.validate(email);
		return valid;
	}
}