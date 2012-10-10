package com.oivan.cashman.test;

import static org.junit.Assert.*;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oivan.cashman.model.CostItem;

public class HibernateTest {
	
	private SessionFactory sessionFactory;
	private Session session;
	private ServiceRegistry serviceRegistry;
	private Transaction tx;
	private CostItem costItem;
	
	@Before
	public void setUp() {
		Configuration configuration = new Configuration();
		configuration.configure().setProperty("hibernate.show_sql", "false");
		serviceRegistry = new ServiceRegistryBuilder().applySettings(
		configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
	}
	
	@Test
	public void testDataBase() {
		costItem = (CostItem)session.get(CostItem.class, 1);
		System.out.println("Name of item: " + costItem.getItemName());
		assertEquals(costItem.getItemName(), "food");
	}
	
	@Test
	public void testQueryToDataBase() {
		List<?> itemList = session.createQuery("from CostItem").list();
		for (int i=0; i<itemList.size(); i++) {
			costItem = (CostItem)itemList.get(i);
			System.out.println("Row " + (i + 1) + "> " + costItem.getItemName()
					+ " (" + costItem.getCostItemID() + ")");
		}
		assertNotNull(itemList);
	}
	
	@After
	public void tearDown() {
		session.close();
		sessionFactory.close();
	}
}