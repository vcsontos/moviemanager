/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.sessionbean;

import com.manager.moviemanager.constant.Constant;
import com.manager.moviemanager.exception.JeeApplicationException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

/**
 *
 * @author valentin
 */
public class MovieUserFacadeTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private EntityManager mockedEntityManager;
    @Mock
    private MovieUserFacade movieUserFacade;

    public MovieUserFacadeTest() {
    }

    @Before
    public void setUp() throws NamingException {
        movieUserFacade = new MovieUserFacade();
        movieUserFacade.setEm(mockedEntityManager);
    }

    @After
    public void tearDown() {
        movieUserFacade = null;
    }

    /**
     * Test of create method, of class MovieUserFacade.
     *
     * @throws
     * com.manager.moviemanager.exception.JeeApplicationException
     */
    @Test
    public void whenUsernameIsNullThanReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.USERNAME_IS_EMPTY_OR_NULL);
        String username = null;
        movieUserFacade.validateUsername(username);
    }

    @Test
    public void whenUsernameIsEmptyThanReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.USERNAME_IS_EMPTY_OR_NULL);
        String username = "";
        movieUserFacade.validateUsername(username);
    }

    /**
     *
     * @throws JeeApplicationException
     */
    @Test
    public void whenUsernameLessThan3CharactersThenReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.USERNAME_HAS_LESS_THAN_THREE_CHARACTERS);
        String username = "*/";
        movieUserFacade.validateUsername(username);
    }

    @Test
    public void whenUsernameIsValid() throws JeeApplicationException {
        movieUserFacade.validateUsername("admin");
    }

    @Test
    public void whenPasswordIsNullThanReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.PASSWORD_IS_EMPTY_OR_NULL);
        String password = null;
        movieUserFacade.validatePassword(password);
    }

    @Test
    public void whenPasswordIsEmptyThanReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.PASSWORD_IS_EMPTY_OR_NULL);
        String password = "";
        movieUserFacade.validatePassword(password);
    }
    
    @Test
    public void whenPasswordLessThan6CharactersThenReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.PASSWORD_HAS_LESS_THAN_SIX_CHARACTERS);
        movieUserFacade.validatePassword("12345");
    }
    
    @Test
    public void whenPasswordIsValid() throws JeeApplicationException {
        movieUserFacade.validatePassword("123456");
    }
    
//    @Test
//    public void whenUsernameIsAlreadyExistThanReturnJeeApplicationException() throws JeeApplicationException {
//        exception.expect(JeeApplicationException.class);
//        exception.expectMessage(Constant.USERNAME_IS_ALREADY_EXIST);
//        movieUserFacade.isAlreadyExistUsername("admin");
//    }
    

}
