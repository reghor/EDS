/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component.data;

import eds.entity.EnterpriseObject;
import javax.ejb.ObjectNotFoundException;

/**
 *
 * @author LeeKiatHaw
 */
public class EnterpriseObjectNotFoundException extends ObjectNotFoundException {

    public EnterpriseObjectNotFoundException(long id) {
        super("Object with ID "+id+" is not found.");
    }
    
    
}
