/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.filmandseriesmanager.sessionbean;

import com.manager.filmandseriesmanager.entity.FilmUser;
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
public class FilmUserFacadeIT {
    
//    public FilmUserFacadeIT() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of create method, of class FilmUserFacade.
//     */
//    @Test
//    public void testCreate() throws Exception {
//        System.out.println("create");
//        FilmUser entity = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        FilmUserFacadeLocal instance = (FilmUserFacadeLocal)container.getContext().lookup("java:global/classes/FilmUserFacade");
//        instance.create(entity);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of edit method, of class FilmUserFacade.
//     */
//    @Test
//    public void testEdit() throws Exception {
//        System.out.println("edit");
//        FilmUser entity = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        FilmUserFacadeLocal instance = (FilmUserFacadeLocal)container.getContext().lookup("java:global/classes/FilmUserFacade");
//        instance.edit(entity);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of remove method, of class FilmUserFacade.
//     */
//    @Test
//    public void testRemove() throws Exception {
//        System.out.println("remove");
//        FilmUser entity = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        FilmUserFacadeLocal instance = (FilmUserFacadeLocal)container.getContext().lookup("java:global/classes/FilmUserFacade");
//        instance.remove(entity);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of find method, of class FilmUserFacade.
//     */
//    @Test
//    public void testFind() throws Exception {
//        System.out.println("find");
//        Object id = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        FilmUserFacadeLocal instance = (FilmUserFacadeLocal)container.getContext().lookup("java:global/classes/FilmUserFacade");
//        FilmUser expResult = null;
//        FilmUser result = instance.find(id);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAll method, of class FilmUserFacade.
//     */
//    @Test
//    public void testFindAll() throws Exception {
//        System.out.println("findAll");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        FilmUserFacadeLocal instance = (FilmUserFacadeLocal)container.getContext().lookup("java:global/classes/FilmUserFacade");
//        List<FilmUser> expResult = null;
//        List<FilmUser> result = instance.findAll();
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findRange method, of class FilmUserFacade.
//     */
//    @Test
//    public void testFindRange() throws Exception {
//        System.out.println("findRange");
//        int[] range = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        FilmUserFacadeLocal instance = (FilmUserFacadeLocal)container.getContext().lookup("java:global/classes/FilmUserFacade");
//        List<FilmUser> expResult = null;
//        List<FilmUser> result = instance.findRange(range);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of count method, of class FilmUserFacade.
//     */
//    @Test
//    public void testCount() throws Exception {
//        System.out.println("count");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        FilmUserFacadeLocal instance = (FilmUserFacadeLocal)container.getContext().lookup("java:global/classes/FilmUserFacade");
//        int expResult = 0;
//        int result = instance.count();
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of validateUsername method, of class FilmUserFacade.
//     */
//    @Test
//    public void testValidateUsername() throws Exception {
//        System.out.println("validateUsername");
//        String username = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        FilmUserFacadeLocal instance = (FilmUserFacadeLocal)container.getContext().lookup("java:global/classes/FilmUserFacade");
//        instance.validateUsername(username);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEm method, of class FilmUserFacade.
//     */
//    @Test
//    public void testGetEm() throws Exception {
//        System.out.println("getEm");
//        FilmUserFacade instance = new FilmUserFacade();
//        EntityManager expResult = null;
//        EntityManager result = instance.getEm();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setEm method, of class FilmUserFacade.
//     */
//    @Test
//    public void testSetEm() throws Exception {
//        System.out.println("setEm");
//        EntityManager em = null;
//        FilmUserFacade instance = new FilmUserFacade();
//        instance.setEm(em);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUsername method, of class FilmUserFacade.
//     */
//    @Test
//    public void testGetUsername() throws Exception {
//        System.out.println("getUsername");
//        FilmUserFacade instance = new FilmUserFacade();
//        String expResult = "";
//        String result = instance.getUsername();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setUsername method, of class FilmUserFacade.
//     */
//    @Test
//    public void testSetUsername() throws Exception {
//        System.out.println("setUsername");
//        String username = "";
//        FilmUserFacade instance = new FilmUserFacade();
//        instance.setUsername(username);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPassword method, of class FilmUserFacade.
//     */
//    @Test
//    public void testGetPassword() throws Exception {
//        System.out.println("getPassword");
//        FilmUserFacade instance = new FilmUserFacade();
//        String expResult = "";
//        String result = instance.getPassword();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setPassword method, of class FilmUserFacade.
//     */
//    @Test
//    public void testSetPassword() throws Exception {
//        System.out.println("setPassword");
//        String password = "";
//        FilmUserFacade instance = new FilmUserFacade();
//        instance.setPassword(password);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
