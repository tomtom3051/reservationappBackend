package net.sijbers.csia.reservationApp.model.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="reservations")
public class ReservationEntity implements Serializable{
	
	private static final long serialVersionUID = 5655101839995905001L;
	
	@Id
	@GeneratedValue(generator = "request-generator")
	@GenericGenerator(
			name= "request-generator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name= "sequence_name", value = "sequenceRequest"),
					@Parameter(name = "initial_value", value="1"),
					@Parameter(name= "increment_size", value="1")
			}
			)
	private long id;
	private String clientID;
	private Date reservationDate;
	private int duration;
	private String status;
	private boolean barCrew;
	private boolean cleanUpCrew;
	private int numberOfGuests;
	private int price;
	
	@CreationTimestamp
	private Date creationDate;
	@UpdateTimestamp
	private Date modifDate;
	
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
	public boolean isCleanUpCrew() {
		return cleanUpCrew;
	}
	public void setCleanUpCrew(boolean cleanUpCrew) {
		this.cleanUpCrew = cleanUpCrew;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
