package net.sijbers.csia.reservationApp.model;

import java.util.Date;

public class Reservation {
	private long id;
	private String clientID;
	private Date reservationDate;
	private int duration;
	private String status;
	private Date creationDate;
	private Date modifDate;
	private boolean cleanUpCrew;
	private boolean barCrew;
	private int numberOfGuests;
	private int price;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getModifDate() {
		return modifDate;
	}
	public void setModifDate(Date modifDate) {
		this.modifDate = modifDate;
	}
	public boolean isCleanUpCrew() {
		return cleanUpCrew;
	}
	public void setCleanUpCrew(boolean cleanUpCrew) {
		this.cleanUpCrew = cleanUpCrew;
	}
	public boolean isBarCrew() {
		return barCrew;
	}
	public void setBarCrew(boolean barCrew) {
		this.barCrew = barCrew;
	}
	public int getNumberOfGuests() {
		return numberOfGuests;
	}
	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
