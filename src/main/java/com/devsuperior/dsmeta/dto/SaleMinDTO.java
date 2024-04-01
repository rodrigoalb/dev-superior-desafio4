package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.entities.Sale;

public class SaleMinDTO {

	private Long id;
	private Double amount;
	private LocalDate date;
	
	private String seller;
	
	public SaleMinDTO(Long id, LocalDate date, Double amount, String sellerName) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.setSeller(sellerName);
    }
	
	public SaleMinDTO(Sale entity) {
		id = entity.getId();
		amount = entity.getAmount();
		date = entity.getDate();
	}

	public Long getId() {
		return id;
	}

	public Double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
}
