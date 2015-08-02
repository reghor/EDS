/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component.data;

import eds.entity.data.EnterpriseObject;

/**
 *
 * @author LeeKiatHaw
 */
public class EntityExistsException extends Exception {

    public EntityExistsException(String message) {
        super(message);
    }
    
    public EntityExistsException(EnterpriseObject e){
        super("Entity "+e.getClass().getSimpleName()+" with ID "+e.getOBJECTID()+" and name "+e.getOBJECT_NAME()+" already exists.");
    }
    
}
