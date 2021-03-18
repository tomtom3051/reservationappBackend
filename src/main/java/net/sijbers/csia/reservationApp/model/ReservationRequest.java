package net.sijbers.csia.reservationApp.model;

import java.util.Date;

public class ReservationRequest {
	private Date reservationDate;
	private int duration;
	private boolean cleanUpCrew;
	private boolean barCrew;
	private int numberOfGuests;
	
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
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
}
