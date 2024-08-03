/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 */
@Data
@Entity
@Table(name = "TB_ASSET")
public class AssetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private double quantity;

	private double buyPrice;

	@ManyToOne
	private CoinEntity coinEntity;

	@ManyToOne
	private UserEntity userEntity;
}
