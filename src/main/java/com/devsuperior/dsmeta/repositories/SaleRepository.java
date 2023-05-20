package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleMinProjection;
import com.devsuperior.dsmeta.projections.SellerMinProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(nativeQuery = true, value = "SELECT tb_seller.name, SUM(amount) AS amount " +
			"FROM tb_sales " +
			"INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id " +
			"WHERE date BETWEEN :minDate AND :maxDate " +
			"GROUP BY tb_seller.name")
	List<SaleMinProjection> searchSummary(LocalDate minDate, LocalDate maxDate);

	@Query(nativeQuery = true, value = "SELECT tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name " +
			"FROM tb_sales " +
			"INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id " +
			"WHERE date BETWEEN :minDate AND :maxDate AND UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
	Page<SellerMinProjection> searchReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);
	
}
