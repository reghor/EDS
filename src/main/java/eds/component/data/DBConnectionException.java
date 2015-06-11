/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eds.component.data;

import javax.ejb.EJBException;

/**
 * Applicable only to Hibernate Session, because creation of a Session object
 * requires opening a DB connection while creation of an EM doesn't. 
 * @author KH
 */
public class DBConnectionException extends EJBException{

    public DBConnectionException(String message) {
        super(message);
    }

    /**
     * Put default error message
     */
    public DBConnectionException() {
        this("Could not connect to database!");
    }
    
    
}
