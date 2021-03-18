package net.sijbers.csia.reservationApp.model;

import java.util.ArrayList;

public class Reservations {
	ArrayList<Reservation> reservations = new ArrayList();

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
	}

}
