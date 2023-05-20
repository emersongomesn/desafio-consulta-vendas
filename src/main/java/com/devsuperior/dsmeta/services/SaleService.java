package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleMinProjection;
import com.devsuperior.dsmeta.projections.SellerMinProjection;
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
	
	public List<SaleDTO> getSummary(String minDate, String maxDate){
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		
		LocalDate min;
		LocalDate max;
		
		if(minDate.equals("")) {
			min = today.minusYears(1L);
		}else {
			min = LocalDate.parse(minDate);
		}
		if (maxDate.equals("")) {
			max = today;
		} else {
			max = LocalDate.parse(maxDate);
		}
		
		List<SaleMinProjection> result = repository.searchSummary(min, max);
		return result.stream().map(SaleDTO::new).collect(Collectors.toList());
		
	}
	
	public Page<SaleReportDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		
		LocalDate min;
		LocalDate max;
		
		if (minDate.equals("")) {
			min = today.minusYears(1L);
		}else {
			min = LocalDate.parse(minDate);
		}
		if (maxDate.equals("")) {
			max = today;
		}else {
			max = LocalDate.parse(maxDate);
		}
		
		Page<SellerMinProjection> result = (Page<SellerMinProjection>) repository.searchReport(min, max, name, pageable);
		return result.map(SaleReportDTO::new);
	}
}
