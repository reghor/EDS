/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component.data;

import eds.entity.data.EnterpriseObject;
import javax.ejb.EJBException;

/**
 *
 * @author LeeKiatHaw
 */
public class EnterpriseObjectNotFoundException extends EJBException {

    
    public EnterpriseObjectNotFoundException(Class<? extends EnterpriseObject> object) {
        super("Object "+object.getName()+" is not found.");
    }

    public EnterpriseObjectNotFoundException(Class<? extends EnterpriseObject> object, long id) {
        super("Object "+object.getName()+" with ID "+id+" is not found.");
    }
    
    
}
