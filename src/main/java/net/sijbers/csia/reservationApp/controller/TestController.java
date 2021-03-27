package net.sijbers.csia.reservationApp.controller;

import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import net.sijbers.csia.reservationApp.service.EmailService;
import net.sijbers.csia.reservationApp.service.ReservationService;
import net.sijbers.csia.reservationApp.tools.SharedTools;

@RestController
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	ReservationService reservationService;
	
	@Autowired
	EmailService emailService;
	
	@ApiOperation("test if app is working")
	@RequestMapping(value="/api/up" , method = RequestMethod.GET)
	public String iAmUp() {
		logger.info("i am up called");
		return SharedTools.getSimpleJson(reservationService.getEnvironment()).toString();
	}
	
	
	@ApiOperation("test if app is working")
	@RequestMapping(value="/api/thread" , method = RequestMethod.GET)
	public String testThread() {
		logger.info("testThread");
		return SharedTools.getSimpleJson(reservationService.testThread()).toString();
	}
	
	@RequestMapping(value = { "/mail/tls" }, method = RequestMethod.GET)
	public String SendEmailTLS() {
		logger.info("tls mail");
		emailService.SendEmailTLS();
		return "sent";
	}	

	@RequestMapping(value = { "/mail/ssl" }, method = RequestMethod.GET)
	public String SendEmailSSL() {
		logger.info("ssl mail");
		emailService.SendEmailSSL();
		return "sent";
	}

	@RequestMapping(value = { "/mail/ssl2f" }, method = RequestMethod.GET)
	public String SendEmailSSL2F() {
		logger.info("ssl mail 2F");
		emailService.SendEmailSSL2F();
		return "sent";
	}
	
	@RolesAllowed("user")
	@ApiOperation("test user auth")
	@RequestMapping(value="/api/auth/user" , method = RequestMethod.GET)
	public String userRequest(KeycloakAuthenticationToken authenticationToken) {
		SimpleKeycloakAccount account = (SimpleKeycloakAccount) authenticationToken.getDetails();	
		AccessToken token = account.getKeycloakSecurityContext().getToken();
		String clientUserName = token.getPreferredUsername();

		String clientFullName = token.getGivenName() + " " + token.getFamilyName();
		logger.info("user request from {} - {}", clientUserName,clientFullName);
//		AccessToken.Access access1 = token.getResourceAccess(account.get);
		Set<String> roles1 = account.getRoles();
		
        for (String role: roles1) {
        	logger.info("Role1 {}",role);
        }	
		
		AccessToken.Access access2 = token.getRealmAccess();
		Set<String> roles2 = access2.getRoles();
        for (String role: roles2) {
        	logger.info("Role2 {}",role);
        }
		return SharedTools.getSimpleJson("i am a user").toString();
	}
	
	
	//@RolesAllowed("user")
	@ApiOperation("test anon auth")
	@RequestMapping(value="/api/auth/anon" , method = RequestMethod.GET)
	public String anonRequest() {
		logger.info("anon request");
		return SharedTools.getSimpleJson("i am a anon").toString();
	}
	
	@RolesAllowed("admin")
	@ApiOperation("test admin auth")
	@RequestMapping(value="/api/auth/admin" , method = RequestMethod.GET)
	public String adminRequest() {
		logger.info("admin request");
		return SharedTools.getSimpleJson("i am a admin").toString();
	}
	

}
