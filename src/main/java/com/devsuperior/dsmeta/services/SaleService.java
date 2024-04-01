package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDto;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleMinDTO> findByDateBetweenAndSellerName(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable) {
        Page<SaleMinDTO> sales = repository.findByDateBetweenAndSellerName(minDate, maxDate, name, pageable);
		return sales;
	}	
	
	public List<SellerMinDto> findByProjection2(LocalDate minDate, LocalDate maxDate){
		List<SellerMinDto> result = repository.findByProjection2(minDate, maxDate);
		return result;
	}

//	public List<SaleMinDTO> findByDateBetweenAndSellerName(LocalDate minDate, LocalDate maxDate, String name) {
//        List<SaleMinProjetion2> sales = repository.findByDateBetweenAndSellerName(minDate, maxDate, name);
//        List<SaleMinDTO> result = sales.stream().map(x -> new SaleMinDTO(x)).collect(Collectors.toList());
//		return result;
//	}
//	
//	public List<SellerMinDto> findByProjection(LocalDate minDate, LocalDate maxDate){
//		List<SaleMinProjection> list = repository.findByProjection(minDate, maxDate);
//		List<SellerMinDto> result = list.stream().map(x -> new SellerMinDto(x)).collect(Collectors.toList());
//		return result;
//	}
}
