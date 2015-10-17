/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.filmandseriesmanager.sessionbean;

import com.manager.filmandseriesmanager.constant.Constant;
import com.manager.filmandseriesmanager.exception.JeeApplicationException;
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
public class FilmUserFacadeTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private EntityManager mockedEntityManager;
    @Mock
    private FilmUserFacade filmUserFacade;

    public FilmUserFacadeTest() {
    }

    @Before
    public void setUp() throws NamingException {
        filmUserFacade = new FilmUserFacade();
        filmUserFacade.setEm(mockedEntityManager);
    }

    @After
    public void tearDown() {
        filmUserFacade = null;
    }

    /**
     * Test of create method, of class FilmUserFacade.
     *
     * @throws
     * com.manager.filmandseriesmanager.exception.JeeApplicationException
     */
    @Test
    public void whenUsernameIsNullThanReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.USERNAME_IS_EMPTY_OR_NULL);
        String username = null;
        filmUserFacade.validateUsername(username);
    }

    @Test
    public void whenUsernameIsEmptyThanReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.USERNAME_IS_EMPTY_OR_NULL);
        String username = "";
        filmUserFacade.validateUsername(username);
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
        filmUserFacade.validateUsername(username);
    }

    @Test
    public void whenUsernameIsValid() throws JeeApplicationException {
        filmUserFacade.validateUsername("admin");
    }

    @Test
    public void whenPasswordIsNullThanReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.PASSWORD_IS_EMPTY_OR_NULL);
        String password = null;
        filmUserFacade.validatePassword(password);
    }

    @Test
    public void whenPasswordIsEmptyThanReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.PASSWORD_IS_EMPTY_OR_NULL);
        String password = "";
        filmUserFacade.validatePassword(password);
    }
    
    @Test
    public void whenPasswordLessThan6CharactersThenReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.PASSWORD_HAS_LESS_THAN_SIX_CHARACTERS);
        filmUserFacade.isAlreadyExistUsername("12345");
    }
    
    @Test
    public void whenPasswordIsValid() throws JeeApplicationException {
        filmUserFacade.validatePassword("123456");
    }
    
    @Test
    public void whenUsernameIsAlreadyExistThanReturnJeeApplicationException() throws JeeApplicationException {
        exception.expect(JeeApplicationException.class);
        exception.expectMessage(Constant.USERNAME_IS_ALREADY_EXIST);
        filmUserFacade.isAlreadyExistUsername("admin");
    }
    

}
