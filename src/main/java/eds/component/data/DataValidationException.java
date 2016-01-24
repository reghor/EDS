/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component.data;

import javax.ejb.ApplicationException;

/**
 *
 * @author LeeKiatHaw
 */
@ApplicationException(rollback = true)
public class DataValidationException extends Exception {

    public DataValidationException(String message) {
        super(message);
    }
    
}
