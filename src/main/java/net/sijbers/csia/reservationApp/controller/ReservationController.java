package net.sijbers.csia.reservationApp.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sijbers.csia.reservationApp.model.Reservation;
import net.sijbers.csia.reservationApp.model.ReservationRequest;
import net.sijbers.csia.reservationApp.model.Reservations;
import net.sijbers.csia.reservationApp.model.SearchRequest;
import net.sijbers.csia.reservationApp.model.Price;
import net.sijbers.csia.reservationApp.service.ReservationService;


@RestController
@Api(tags="Reservation App Rest Calls")
public class ReservationController {
	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	
	// developper: Tom Sijbers
	// controller for managing reservations
	
	@Autowired
	ReservationService reservationService;
	
	
	@RolesAllowed("admin")
	@ApiOperation("change status requests")
	@RequestMapping(value="/api/admin/newStatus/{requestID}/{status}" , method = RequestMethod.GET)
	public Reservation changeStatus(
			@ApiParam(
					name = "requestID",
					type = "number",
					value = "id of the reservation Request",
					example = "1",
					required = true
					) 
			@PathVariable(name="requestID") long requestID,
			@ApiParam(
					name = "status",
					type = "string",
					value = "new status of the reservation Request",
					example = "cancel",
					required = true
			) 
			@PathVariable(name="status") String status)
	{
		logger.info("requestID: {}, status: {}",requestID,status);
		return reservationService.changeStatus(requestID,status);
	}

	
	@RolesAllowed("user")
	@ApiOperation("post reservation request")
	@RequestMapping(value="/api/requestReservation" , method = RequestMethod.POST)
	public Reservation requestReservation(KeycloakAuthenticationToken authenticationToken, @RequestBody ReservationRequest reservationRequest) {
		logger.info("requestReservation");
		return reservationService.requestReservation(getUserName(authenticationToken), reservationRequest);
	}
	
	@ApiOperation("get All prices")
	@RequestMapping(value="/api/getAllPrices" , method = RequestMethod.GET)
	public List<Price> getAllPrices() {
		logger.info("getAllPrices");
		return reservationService.getAllPrices();
	}
	
	@RolesAllowed("admin")
	@ApiOperation("save requests")
	@RequestMapping(value="/api/admin/savePrices" , method = RequestMethod.POST)
	public List<Price>  savePrices( @RequestBody List<Price>  prices) {
		logger.info("savePrices");
		return reservationService.savePrices(prices);
	}
	
	
	@RolesAllowed("admin")
	@ApiOperation("get All requests")
	@RequestMapping(value="/api/admin/getAllReservations" , method = RequestMethod.GET)
	public Reservations getAllReservations() {
		logger.info("getAllReservations");
		return reservationService.getAllReservations();
	}
	
	//@RolesAllowed("admin")
	@ApiOperation("get All status")
	@RequestMapping(value="/api/admin/getAllStatus" , method = RequestMethod.GET)
	public List<String> getAllStatus() {
		logger.info("getAllStatus");
		return reservationService.getAllStatus();
	}
	
	@RolesAllowed("admin")
	@ApiOperation("get All clients")
	@RequestMapping(value="/api/admin/getAllClients" , method = RequestMethod.GET)
	public List<String> getAllClients() {
		logger.info("getAllClients");
		return reservationService.getAllClients();
	}
	
	@RolesAllowed("admin")
	@ApiOperation("search reservation request")
	@RequestMapping(value="/api/searchReservation" , method = RequestMethod.POST)
	public Reservations searchReservation(KeycloakAuthenticationToken authenticationToken, @RequestBody SearchRequest searchRequest) {
		logger.info("searchReservation");
		return reservationService.searchReservation(searchRequest);
	}
	
	
	@RolesAllowed("user")
	@ApiOperation("get my requests")
	@RequestMapping(value="/api/getMyReservations" , method = RequestMethod.GET)
	public Reservations getMyReservations(KeycloakAuthenticationToken authenticationToken) {
		logger.info("getReservations4User");
		return reservationService.getReservations4User(getUserName(authenticationToken));
	}

	private String getUserName(KeycloakAuthenticationToken authenticationToken) {
		SimpleKeycloakAccount account = (SimpleKeycloakAccount) authenticationToken.getDetails();	
		AccessToken token = account.getKeycloakSecurityContext().getToken();
		return token.getPreferredUsername();
	}

}
