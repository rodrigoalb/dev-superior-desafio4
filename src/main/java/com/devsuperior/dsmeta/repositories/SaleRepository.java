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
}

