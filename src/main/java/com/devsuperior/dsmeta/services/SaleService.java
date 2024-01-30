package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SallerSumDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
	
	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {
		Page<Sale> result = repository.getReport(verifyMinDate(minDate), verifyMaxDate(maxDate), name, pageable);
		return result.map(x -> new SaleMinDTO(x));
	}
	
	public Page<SallerSumDTO> getSummary(String minDate, String maxDate, Pageable pageable) {
		Page<SallerSumDTO> result = repository.getSummary(verifyMinDate(minDate), verifyMaxDate(maxDate), pageable);
		return result;//.map(x -> new SallerSumDTO(x));
	}
	
	private LocalDate verifyMinDate(String minDate) {
		return minDate.isEmpty() ? today.minusYears(1L) : LocalDate.parse(minDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	private LocalDate verifyMaxDate(String maxDate) {
		return maxDate.isEmpty() ? today : LocalDate.parse(maxDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

}
