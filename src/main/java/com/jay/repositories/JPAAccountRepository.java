package com.jay.repositories;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.jay.entities.Account;


@Repository
public class JPAAccountRepository implements AccountRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	private long nextId = 4;
	
	@Override
	public List<Account> getAccounts() {
		return entityManager.createQuery("select a from Account a", Account.class)
				.getResultList();
	}
	@Override
	public Account getAccount(Long id) {
		return entityManager.find(Account.class, id);
	}
	@Override
	public int getNumberOfAccounts() {
		String sql = "select count(a.id) from Account a";
		Long result = (Long) entityManager.createQuery(sql).getSingleResult();
		return result.intValue();
	}
	@Override
	public Long createAccount(BigDecimal initialBalance) {
		Long id = nextId++;
		
		entityManager.persist(new Account(id, initialBalance));
		return id;
	}
	@Override
	public int deleteAccount(Long id) {
		entityManager.remove(getAccount(id));
		return 1;
	}
	@Override
	public void updateAccount(Account account) {
		entityManager.merge(account);
	}
}
