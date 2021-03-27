package net.sijbers.csia.reservationApp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.sijbers.csia.reservationApp.model.db.ReservationEntity;

public interface iReservationRepository extends JpaRepository<ReservationEntity, Long>{
	public List<ReservationEntity> findByClientID(String clientID);
	
	public List<ReservationEntity> findByStatus(String status);
	
	public List<ReservationEntity> findByClientIDAndStatus(String clientID, String status);
	
	public List<ReservationEntity> findByIdAndClientID(long id, String clientID);
	
	
	
	
	
	@Query(	value="select * from reservations "
			+ "where reservation_date>=:startDate "
			+ "and reservation_date<=:endDate ",
			nativeQuery=true)
	public  List<ReservationEntity> getByDate(
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate);
	
	@Query(	value="select * from reservations "
			+ "where reservation_date>=:startDate "
			+ "and reservation_date<=:endDate "
			+ "and clientID = :clientID",
			nativeQuery=true)
	public  List<ReservationEntity> getByDateAndClientID(
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate, 
			@Param("clientID")  String clientID);
	
	@Query(	value="select * from reservations "
			+ "where reservation_date>=:startDate "
			+ "and reservation_date<=:endDate "
			+ "and status = :status",
			nativeQuery=true)
	public  List<ReservationEntity> getByDateAndStatus(
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate, 
			@Param("status")  String status);
	
	@Query(	value="select * from reservations "
			+ "where reservation_date>=:startDate "
			+ "and reservation_date<=:endDate "
			+ "and status = :status "
			+ "and clientID = :clientID",
			nativeQuery=true)
	public  List<ReservationEntity> getByDateAndClientIDAndStatus(
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate, 
			@Param("clientID")  String clientID,
			@Param("status")  String status);
	
	
	@Query(	value="select * from reservations where reservation_date>:startDate",
			nativeQuery=true)
	public List<String> getReservationsAfter(@Param("startDate") Date startDate);
	
	@Query(	value="select distinct clientid from reservations",
			nativeQuery=true)
	public List<String> getAllClients();
	
	
	@Query(	value="select distinct status from reservations",
			nativeQuery=true)
	public List<String>  getAllStatus();
	
	
}
