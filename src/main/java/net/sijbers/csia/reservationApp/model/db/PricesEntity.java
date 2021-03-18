package net.sijbers.csia.reservationApp.model.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="prices")
public class PricesEntity implements Serializable{
	private static final long serialVersionUID = 2148377515497956068L;
	
	@Id
	@GeneratedValue(generator = "request-generator")
	@GenericGenerator(
			name= "request-generator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name= "sequence_name", value = "sequencePrice"),
					@Parameter(name = "initial_value", value="1"),
					@Parameter(name= "increment_size", value="1")
			}
			)
	private long id;
	private String priceType;
	private long price;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
}
	

