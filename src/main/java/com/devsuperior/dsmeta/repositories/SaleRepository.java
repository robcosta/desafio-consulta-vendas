package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SallerSumDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
			+ "FROM Sale obj "
			//+ "JOIN FETCH obj.seller "
			+ "WHERE (obj.date BETWEEN :minDate AND :maxDate) "
			+ "AND (UPPER(obj.seller.name) LIKE(CONCAT('%', UPPER(:name), '%')))")
			//countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller")
	Page<SaleMinDTO> getReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

	
	@Query(value = "SELECT new com.devsuperior.dsmeta.dto.SallerSumDTO(obj.seller.name, SUM(obj.amount))"
			+ " FROM Sale obj "
			+ "WHERE obj.date BETWEEN :minDate AND :maxDate "
			+ "GROUP BY obj.seller.name")
	Page<SallerSumDTO> getSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable);
	


}
