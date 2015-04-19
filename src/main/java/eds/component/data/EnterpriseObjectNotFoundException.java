/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component.data;

import javax.ejb.EJBException;

/**
 *
 * @author LeeKiatHaw
 */
public class EnterpriseObjectNotFoundException extends EJBException {

    public EnterpriseObjectNotFoundException(long id) {
        super("Object with ID "+id+" is not found.");
    }
    
    
}
