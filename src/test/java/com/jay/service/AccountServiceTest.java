package com.jay.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jay.config.AppConfig;
import com.jay.services.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class AccountServiceTest {
	
	@Autowired
	private AccountService accountService;
	
	@Test
	public void testDeposit() throws Exception {
		
		BigDecimal start = accountService.getBalance(1L);
		BigDecimal amount = new BigDecimal("50.0");
		accountService.deposit(1L, amount);
		BigDecimal finish = start.add(amount);
		
		assertThat(accountService.getBalance(1L), is(closeTo(finish, new BigDecimal("0.01"))));
	}

	@Test
	public void testWithdraw() throws Exception {
		
		BigDecimal start = accountService.getBalance(1L);
		BigDecimal amount = new BigDecimal("50.0");
		accountService.withdraw(1L, amount);
		BigDecimal finish = start.subtract(amount);
		assertThat(accountService.getBalance(1L), is(closeTo(finish, new BigDecimal("0.01"))));
	}
	
	@Test
	public void testTransfer() throws Exception {
		BigDecimal account1Start = accountService.getBalance(1L);
		BigDecimal account2Start = accountService.getBalance(2L);
		BigDecimal amount = new BigDecimal("50.0");
		
		accountService.transfer(1L, 2L, amount);
		
		BigDecimal account1Finish = account1Start.subtract(amount);
		BigDecimal account2Finish = account2Start.add(amount);
		
		assertThat(account1Finish, is(closeTo(accountService.getBalance(1L), new BigDecimal("0.01"))));
		assertThat(account2Finish, is(closeTo(accountService.getBalance(2L), new BigDecimal("0.01"))));
	}
}
