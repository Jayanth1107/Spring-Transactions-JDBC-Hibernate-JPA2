package com.jay;

import java.math.BigDecimal;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jay.config.AppConfig;
import com.jay.services.AccountService;

public class SpringApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
	                new AnnotationConfigApplicationContext(
	                        AppConfig.class, AppConfig.class);
	        
	        AccountService as = context.getBean("accountService", AccountService.class);
	        
	        System.out.println("1: "+ as.getBalance(1L)+"\n 2: "+as.getBalance(2L)+"\n3: "+as.getBalance(3L));
	        
	        as.deposit(2L, new BigDecimal("203.15"));
	        System.out.println("1: "+ as.getBalance(1L)+"\n 2: "+as.getBalance(2L)+"\n3: "+as.getBalance(3L));
	        as.withdraw(3L, new BigDecimal("34.02"));
	        System.out.println("1: "+ as.getBalance(1L)+"\n 2: "+as.getBalance(2L)+"\n3: "+as.getBalance(3L));
	        as.transfer(1L, 3L, new BigDecimal("5623.95"));
	        System.out.println("1: "+ as.getBalance(1L)+"\n 2: "+as.getBalance(2L)+"\n3: "+as.getBalance(3L));
	        
	        
	        
	        System.out.println(context.getBeanDefinitionCount());
	        for (String name : context.getBeanDefinitionNames()) {
	            System.out.println(name);
	        }
	        
	        context.close();
	}

}
