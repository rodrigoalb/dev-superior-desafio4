package com.devsuperior.dsmeta.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDto;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(
	    @RequestParam(name = "minDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDate,
	    @RequestParam(name = "maxDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxDate,
	    @RequestParam(name = "name", defaultValue = "") String sellerName, Pageable pageable) {
	  
	    if (minDate == null && maxDate == null) {
	        LocalDate currentDate = LocalDate.now();
	        minDate = currentDate.minusMonths(12);
	        maxDate = currentDate;
	    }else if (minDate == null) {
	    	minDate = maxDate.minusYears(1L);
	    }else if (maxDate == null) {
	    		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
	    		maxDate = today;
	    }

	    Page<SaleMinDTO> list = service.findByDateBetweenAndSellerName(minDate, maxDate, sellerName, pageable);
	    return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/summary")
	public ResponseEntity<List<SellerMinDto>> getSummary(
			@RequestParam(name = "minDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDate,
		    @RequestParam(name = "maxDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxDate) {
		
		 if (minDate == null && maxDate == null) {
		        LocalDate currentDate = LocalDate.now();
		        minDate = currentDate.minusMonths(12);
		        maxDate = currentDate;
		    }else if (minDate == null) {
		    	minDate = maxDate.minusYears(1L);
		    }else if (maxDate == null) {
		    		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		    		maxDate = today;
		    }
		
		List<SellerMinDto> list = service.findByProjection2(minDate, maxDate);
		return ResponseEntity.ok(list);
	}
	

}
