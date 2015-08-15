/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component.data;

import eds.entity.data.EnterpriseObject;
import eds.entity.data.EnterpriseRelationship;

/**
 *
 * @author LeeKiatHaw
 * @param <R>
 */
public class RelationshipExistsException extends Exception {
    /*public RelationshipExistsException(String message) {
        super(message);
    }*/
    
    public <R extends EnterpriseRelationship> RelationshipExistsException(R r){
        super("Relationship "+r.getClass().getSimpleName() 
        + ((r.getSOURCE() != null) ? "" : (" with SOURCE "+r.getSOURCE_TYPE()+" (ID:"+r.getSOURCE().getOBJECTID()+", NAME:"+r.getSOURCE().getOBJECT_NAME()+")"))
        + ((r.getTARGET() != null) ? "" : (" with TARGET "+r.getTARGET_TYPE()+" (ID:"+r.getTARGET().getOBJECTID()+", NAME:"+r.getTARGET().getOBJECT_NAME()+")"))
        +" already exists.");
    }
    
}
