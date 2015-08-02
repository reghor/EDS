/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component.data;

import eds.entity.data.EnterpriseObject;

/**
 * Usually thrown in creation of relationships where the SOURCE or TARGET 
 * entity is not found
 * @author LeeKiatHaw
 */
public class EntityNotFoundException extends Exception{

    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public EntityNotFoundException(Class<? extends EnterpriseObject> c,long id){
        super(c.getSimpleName()+" "+id+" is not found.");
    }
}
