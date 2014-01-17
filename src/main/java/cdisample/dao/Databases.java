package cdisample.dao;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import cdisample.qualifier.Documents;
import cdisample.qualifier.Users;

public class Databases {
	@Produces @PersistenceContext
	@Users EntityManager userDatabaseEntityManager;
	@Produces	@PersistenceUnit
	@Users EntityManagerFactory userDatabaseEntityManagerFactory;
	@Produces	@PersistenceContext
	@Documents EntityManager docDatabaseEntityManager;	
}