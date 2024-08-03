/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 */
@Data
@Entity
@Table(name = "TB_ORDER_ITEM")
public class OrderItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private double quantity;

	@ManyToOne
	private CoinEntity coinEntity;

	private double buyPrice;

	private double sellPrice;

	@JsonIgnore
	@OneToOne
	private OrderEntity orderEntity;

}
