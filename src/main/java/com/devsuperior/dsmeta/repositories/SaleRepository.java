package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDto;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
	        + "FROM Sale obj JOIN obj.seller s "
	        + "WHERE obj.date BETWEEN :minDate AND :maxDate AND UPPER(s.name) LIKE UPPER(CONCAT('%', :sellerName, '%'))")
	Page<SaleMinDTO> findByDateBetweenAndSellerName(LocalDate minDate, LocalDate maxDate, String sellerName, Pageable pageable);
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SellerMinDto(s.seller.name AS sellerName, SUM(s.amount) AS totalAmount) " +
		       "FROM Sale s " +
		       "WHERE s.date BETWEEN :minDate AND :maxDate " +
		       "GROUP BY s.seller.id, s.seller.name")
		List<SellerMinDto> findByProjection2(
				@Param("minDate") LocalDate minDate,
		        @Param("maxDate") LocalDate maxDate);

	
//	@Query(nativeQuery = true, value = "SELECT s.id, s.date, s.amount, sel.name AS sellerName\r\n"
//			+ "FROM tb_sales s\r\n"
//			+ "INNER JOIN tb_seller sel ON s.seller_id = sel.id\r\n"
//			+ "WHERE s.date BETWEEN DATE '2022-05-01' AND DATE '2022-05-31' AND UPPER(sel.name) LIKE '%ODINSON%'\r\n"
//			+ "")
//	List<SaleMinProjetion2> findByDateBetweenAndSellerName(LocalDate minDate, LocalDate maxDate, String sellerName);
//	
//
//	@Query(nativeQuery = true, value = "SELECT s.seller_id, sel.name AS sellerName, SUM(s.amount) AS totalAmount\r\n"
//			+ "FROM tb_sales s\r\n"
//			+ "INNER JOIN tb_seller sel ON s.seller_id = sel.id\r\n"
//			+ "WHERE s.date BETWEEN :minDate AND :maxDate\r\n"
//			+ "GROUP BY s.seller_id, sel.name\r\n"
//			+ "")
//	List<SaleMinProjection> findByProjection( 
//			@Param("minDate") LocalDate minDate,
//	        @Param("maxDate") LocalDate maxDate);
}

