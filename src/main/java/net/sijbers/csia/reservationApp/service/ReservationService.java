package net.sijbers.csia.reservationApp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import net.sijbers.csia.reservationApp.model.Reservation;
import net.sijbers.csia.reservationApp.model.ReservationRequest;
import net.sijbers.csia.reservationApp.model.Reservations;
import net.sijbers.csia.reservationApp.model.SearchRequest;
import net.sijbers.csia.reservationApp.model.Price;
import net.sijbers.csia.reservationApp.model.db.PricesEntity;
import net.sijbers.csia.reservationApp.model.db.ReservationEntity;
import net.sijbers.csia.reservationApp.repository.iPricesRepository;
import net.sijbers.csia.reservationApp.repository.iReservationRepository;
import net.sijbers.csia.reservationApp.tools.SharedTools;

@Service("ReservationService")
public class ReservationService {
	private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);
	
	@Autowired
	iReservationRepository reservationRepository;
	
	@Autowired
	iPricesRepository pricesRepository;
	
	@Autowired
	EmailService emailService;
	
	
	@Value("${admin.email}")
	String adminEmail;
			
	
	public String IamUp() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		long now = cal.getTimeInMillis();
		cal.add(Calendar.DAY_OF_YEAR, 1);

		long then = cal.getTimeInMillis();
		long secondsFromEpoch = (then - now) / 1000;
		logger.info("I am up, called in service");
		logger.info("epoch: {}", secondsFromEpoch);
		logger.info("date: {}", new Date());
		logger.info("adminEmail: {}", adminEmail);
		
		return "alive";
	}
	
	
	public List<Price> getAllPrices() {
		List<Price> prices = new ArrayList<Price>();
		List<PricesEntity> priceRecords = pricesRepository.findAll();
		for (PricesEntity priceRecord: priceRecords) {
			Price price = new Price();
			price.setPrice(priceRecord.getPrice());
			price.setPriceType(priceRecord.getPriceType());
			prices.add(price);	
		}
		return prices;
	}
	
	public List<Price> savePrices(List<Price> prices) {
		for (Price price:prices) {
			PricesEntity priceRecord = new PricesEntity();
			List<PricesEntity> existingRecords = pricesRepository.findByPriceType(price.getPriceType());
			if (existingRecords.size()==1) {
				priceRecord = existingRecords.get(0);
			}
			priceRecord.setPrice(price.getPrice());
			priceRecord.setPriceType(price.getPriceType());
			pricesRepository.saveAndFlush(priceRecord);
		}
		return  getAllPrices() ;
	}
	
	@Async
	private void sendRequestConfirmations(String userName, ReservationRequest reservationRequest)  {
		String adminBody = "Dear admin \n\n";
		adminBody += "There is a new reservation request at ";
		adminBody += reservationRequest.getReservationDate();
		adminBody += "\n";
		adminBody += "Reservation was requested by ";
		adminBody +=  userName;
		adminBody += "\n\nHave a nice day\n\nThe Reservation App";
		
		String requesterBody = "Dear ";
		requesterBody += userName;
		requesterBody += "\n";
		requesterBody += "Your reservation request for ";
		requesterBody += reservationRequest.getReservationDate();
		requesterBody += " has been received.\n\n You will hear from us shortly\n\nThe Reservation App";	
		
		emailService.SendEmailSSL2F(adminEmail, "New Reservation Request", adminBody);
		emailService.SendEmailSSL2F(userName, "New Reservation Request", requesterBody);		
	}
	
	private int getPrice(String priceType) {
		List<PricesEntity> existingRecords = pricesRepository.findByPriceType(priceType);
		if (existingRecords.size()==1) {
			return (int) existingRecords.get(0).getPrice();
		}
		return 0;
	}
	
	public Reservation requestReservation(String userName, ReservationRequest reservationRequest) {
		logger.info("1");
		//todo: check if there is already a APPROVED reservation on that date
		ReservationEntity reservationRecord = new ReservationEntity();
		reservationRecord.setClientID(userName);
		reservationRecord.setReservationDate(reservationRequest.getReservationDate());
		reservationRecord.setDuration(reservationRequest.getDuration());
		reservationRecord.setStatus("requested");
		reservationRecord.setBarCrew(reservationRequest.isBarCrew());
		reservationRecord.setCleanUpCrew(reservationRequest.isCleanUpCrew());
		reservationRecord.setNumberOfGuests(reservationRequest.getNumberOfGuests());
		int totalPrice = getPrice("hourPrice") * reservationRequest.getDuration();
		if (reservationRequest.isBarCrew()) {
			totalPrice += getPrice("barCrewPrice") * reservationRequest.getDuration();
		}
		if (reservationRequest.isCleanUpCrew()) {
			totalPrice += getPrice("cleanUpPrice");
		}
		reservationRecord.setPrice(totalPrice);
		logger.info("2");
		sendRequestConfirmations(userName,reservationRequest);
		logger.info("3");
		reservationRepository.save(reservationRecord);
		logger.info("4");
						
		return reservationEntityDAO(reservationRecord);
	}

	public Reservations getAllReservations() {
		Reservations reservations = new Reservations();
		
		List<ReservationEntity> reservationRecords = reservationRepository.findAll();
		
		for(ReservationEntity reservationRecord:reservationRecords) {
			reservations.getReservations().add(reservationEntityDAO(reservationRecord));
		}
		return reservations;
	}
	
	public List<String> getAllClients(){
		List<String> allClients = reservationRepository.getAllClients();
		return allClients;
	}
	
	public  List<String>  getAllStatus() {
		List<String> allClients = reservationRepository.getAllStatus();
		return allClients;
	}
	
	public Reservations getReservations4User(String username) {
		Reservations reservations = new Reservations();
		
		List<ReservationEntity> reservationRecords = reservationRepository.findByClientID(username);
		
		for(ReservationEntity reservationRecord:reservationRecords) {
			reservations.getReservations().add(reservationEntityDAO(reservationRecord));
		}
		return reservations;
	}
	
	public Reservations searchReservation(SearchRequest searchRequest) {
		logger.info(SharedTools.deserialiseObject(searchRequest));
		Reservations reservations = new Reservations();
		if ((!searchRequest.getClientID().isEmpty())&&(!searchRequest.getStatus().isEmpty())) {
			List<ReservationEntity> reservationRecords = reservationRepository.getByDateAndClientIDAndStatus(
						searchRequest.getStartDate(), 
						searchRequest.getEndDate(), 
						searchRequest.getClientID(), 
						searchRequest.getStatus());
			logger.info("results: {}",reservationRecords.size());
			for(ReservationEntity reservationRecord:reservationRecords) {
				reservations.getReservations().add(reservationEntityDAO(reservationRecord));
			}
			return reservations;
		}
		if (!searchRequest.getClientID().isEmpty()) {
			List<ReservationEntity> reservationRecords = reservationRepository.getByDateAndClientID(
						searchRequest.getStartDate(), 
						searchRequest.getEndDate(), 
						searchRequest.getClientID());
			logger.info("results: {}",reservationRecords.size());
			for(ReservationEntity reservationRecord:reservationRecords) {
				reservations.getReservations().add(reservationEntityDAO(reservationRecord));
			}
			return reservations;
		}
		if (!searchRequest.getStatus().isEmpty()) {
			List<ReservationEntity> reservationRecords = reservationRepository.getByDateAndStatus(
						searchRequest.getStartDate(), 
						searchRequest.getEndDate(), 	
						searchRequest.getStatus());
			logger.info("results: {}",reservationRecords.size());
			for(ReservationEntity reservationRecord:reservationRecords) {
				reservations.getReservations().add(reservationEntityDAO(reservationRecord));
			}
			return reservations;
		}
		List<ReservationEntity> reservationRecords = reservationRepository.getByDate(searchRequest.getStartDate(),
				searchRequest.getEndDate());
		logger.info("results: {}", reservationRecords.size());
		for (ReservationEntity reservationRecord : reservationRecords) {
			reservations.getReservations().add(reservationEntityDAO(reservationRecord));
		}
		return reservations;
	}
	
	public Reservation changeStatus(long requestID,String status) {
		//todo: who is allowed to change to which status
		//todo: respect order of statemachine	
		Optional<ReservationEntity> reservationOptional = reservationRepository.findById(requestID);
		ReservationEntity reservationRecord = new ReservationEntity();
		if (reservationOptional.isPresent()) {
			reservationRecord = reservationOptional.get();
			if (reservationRecord.getStatus().equalsIgnoreCase("requested")) {
				reservationRecord.setStatus(status);
				reservationRepository.save(reservationRecord);
				String requesterBody = "Dear ";
				requesterBody += reservationRecord.getClientID();
				requesterBody += "\n";
				requesterBody += "The status of your reservation request for ";
				requesterBody += reservationRecord.getReservationDate();
				requesterBody += " has been changed to ";
				requesterBody += status;
				requesterBody += "\n\nHave a nice day\n\nThe Reservation App";
	
				emailService.SendEmailSSL2F(reservationRecord.getClientID(), "New Reservation Request", requesterBody);

			}
		}
		return reservationEntityDAO(reservationRecord);
	}
	
	private Reservation reservationEntityDAO(ReservationEntity reservationRecord) {
		Reservation reservation = new Reservation();
		reservation.setClientID(reservationRecord.getClientID());
		reservation.setDuration(reservationRecord.getDuration());
		reservation.setId(reservationRecord.getId());
		reservation.setReservationDate(reservationRecord.getReservationDate());
		reservation.setStatus(reservationRecord.getStatus());
		reservation.setCreationDate(reservationRecord.getCreationDate());
		reservation.setModifDate(reservationRecord.getModifDate());
		reservation.setBarCrew(reservationRecord.isBarCrew());
		reservation.setCleanUpCrew(reservationRecord.isCleanUpCrew());
		reservation.setNumberOfGuests(reservationRecord.getNumberOfGuests());
		reservation.setPrice(reservationRecord.getPrice());
		return reservation;
	}
}
