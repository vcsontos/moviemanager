/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.sessionbean;

import com.manager.moviemanager.entity.MovieUser;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author valentin
 */
public class MovieUserFacadeIT {
    
    public MovieUserFacadeIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

//    /**
//     * Test of create method, of class MovieUserFacade.
//     */
//    @Test
//    public void testCreate() throws Exception {
//        System.out.println("create");
//        MovieUser entity = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        MovieUserFacadeLocal instance = (MovieUserFacadeLocal)container.getContext().lookup("java:global/classes/MovieUserFacade");
//        instance.create(entity);
//        container.close();
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of edit method, of class MovieUserFacade.
//     */
//    @Test
//    public void testEdit() throws Exception {
//        System.out.println("edit");
//        MovieUser entity = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        MovieUserFacadeLocal instance = (MovieUserFacadeLocal)container.getContext().lookup("java:global/classes/MovieUserFacade");
//        instance.edit(entity);
//        container.close();
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of remove method, of class MovieUserFacade.
//     */
//    @Test
//    public void testRemove() throws Exception {
//        System.out.println("remove");
//        MovieUser entity = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        MovieUserFacadeLocal instance = (MovieUserFacadeLocal)container.getContext().lookup("java:global/classes/MovieUserFacade");
//        instance.remove(entity);
//        container.close();
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of find method, of class MovieUserFacade.
//     */
//    @Test
//    public void testFind() throws Exception {
//        System.out.println("find");
//        Object id = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        MovieUserFacadeLocal instance = (MovieUserFacadeLocal)container.getContext().lookup("java:global/classes/MovieUserFacade");
//        MovieUser expResult = null;
//        MovieUser result = instance.find(id);
//        assertEquals(expResult, result);
//        container.close();
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAll method, of class MovieUserFacade.
//     */
//    @Test
//    public void testFindAll() throws Exception {
//        System.out.println("findAll");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        MovieUserFacadeLocal instance = (MovieUserFacadeLocal)container.getContext().lookup("java:global/classes/MovieUserFacade");
//        List<MovieUser> expResult = null;
//        List<MovieUser> result = instance.findAll();
//        assertEquals(expResult, result);
//        container.close();
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findRange method, of class MovieUserFacade.
//     */
//    @Test
//    public void testFindRange() throws Exception {
//        System.out.println("findRange");
//        int[] range = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        MovieUserFacadeLocal instance = (MovieUserFacadeLocal)container.getContext().lookup("java:global/classes/MovieUserFacade");
//        List<MovieUser> expResult = null;
//        List<MovieUser> result = instance.findRange(range);
//        assertEquals(expResult, result);
//        container.close();
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of count method, of class MovieUserFacade.
//     */
//    @Test
//    public void testCount() throws Exception {
//        System.out.println("count");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        MovieUserFacadeLocal instance = (MovieUserFacadeLocal)container.getContext().lookup("java:global/classes/MovieUserFacade");
//        int expResult = 0;
//        int result = instance.count();
//        assertEquals(expResult, result);
//        container.close();
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of validateUsername method, of class MovieUserFacade.
//     */
//    @Test
//    public void testValidateUsername() throws Exception {
//        System.out.println("validateUsername");
//        String username = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        MovieUserFacadeLocal instance = (MovieUserFacadeLocal)container.getContext().lookup("java:global/classes/MovieUserFacade");
//        instance.validateUsername(username);
//        container.close();
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEm method, of class MovieUserFacade.
//     */
//    @Test
//    public void testGetEm() throws Exception {
//        System.out.println("getEm");
//        MovieUserFacade instance = new MovieUserFacade();
//        EntityManager expResult = null;
//        EntityManager result = instance.getEm();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setEm method, of class MovieUserFacade.
//     */
//    @Test
//    public void testSetEm() throws Exception {
//        System.out.println("setEm");
//        EntityManager em = null;
//        MovieUserFacade instance = new MovieUserFacade();
//        instance.setEm(em);
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUsername method, of class MovieUserFacade.
//     */
//    @Test
//    public void testGetUsername() throws Exception {
//        System.out.println("getUsername");
//        MovieUserFacade instance = new MovieUserFacade();
//        String expResult = "";
//        String result = instance.getUsername();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setUsername method, of class MovieUserFacade.
//     */
//    @Test
//    public void testSetUsername() throws Exception {
//        System.out.println("setUsername");
//        String username = "";
//        MovieUserFacade instance = new MovieUserFacade();
//        instance.setUsername(username);
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPassword method, of class MovieUserFacade.
//     */
//    @Test
//    public void testGetPassword() throws Exception {
//        System.out.println("getPassword");
//        MovieUserFacade instance = new MovieUserFacade();
//        String expResult = "";
//        String result = instance.getPassword();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setPassword method, of class MovieUserFacade.
//     */
//    @Test
//    public void testSetPassword() throws Exception {
//        System.out.println("setPassword");
//        String password = "";
//        MovieUserFacade instance = new MovieUserFacade();
//        instance.setPassword(password);
//        fail("The test case is a prototype.");
//    }
    
}
