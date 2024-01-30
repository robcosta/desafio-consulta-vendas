package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Seller;

public class SellerMinDTO {

	private Long id;
	private String name;
	
	public SellerMinDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public SellerMinDTO(Seller entity) {
		id = entity.getId();
		name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
