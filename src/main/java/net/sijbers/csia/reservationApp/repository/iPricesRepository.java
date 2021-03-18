package net.sijbers.csia.reservationApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.sijbers.csia.reservationApp.model.db.PricesEntity;

public interface iPricesRepository extends JpaRepository<PricesEntity, Long>{
	
	public List<PricesEntity>  findByPriceType(String priceType);

}
