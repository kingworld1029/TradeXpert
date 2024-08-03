/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.practice.helper.HelperEnum.ORDER_STATUS;
import com.practice.helper.HelperEnum.ORDER_TYPE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name="TB_ORDER")
public class OrderEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private UserEntity userEntity;
	
	@Column(nullable = false)
	private ORDER_TYPE orderType;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	private LocalDateTime timestamp = LocalDateTime.now();
	
	@Column(nullable = false)
	private ORDER_STATUS orderStatus;
	
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	private OrderItemEntity orderItemEntity;

}
