/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.filmandseriesmanager.exception;

/**
 *
 * @author valentin
 */
public class JeeApplicationException extends Exception {

    public JeeApplicationException() {
    }

    public JeeApplicationException(String message) {
        super(message);
    }

    public JeeApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JeeApplicationException(Throwable cause) {
        super(cause);
    }
    
}
