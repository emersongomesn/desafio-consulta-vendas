package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.projections.SellerMinProjection;

public class SaleReportDTO {
	
	private Long id;
	private LocalDate date;
	private Double amount;
	private String name;
	
	public SaleReportDTO(Long id, LocalDate date, Double amount, String name) {
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.name = name;
	}
	
	public SaleReportDTO(SellerMinProjection entity) {
		id = entity.getId();
		date = entity.getDate();
		amount = entity.getAmount();
		name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public Double getAmount() {
		return amount;
	}

	public String getName() {
		return name;
	}
}
