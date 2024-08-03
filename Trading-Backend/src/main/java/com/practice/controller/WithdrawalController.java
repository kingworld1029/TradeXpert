/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.service.IWithdrawalService;

/**
 * 
 */
@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {

	@Autowired
	private IWithdrawalService withdrawalService;

}
