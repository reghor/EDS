/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.data;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author LeeKiatHaw
 */
public class EnterpriseRelationshipListener {
    
    @PrePersist
    @PreUpdate
    public void PrePersistUpdate(EnterpriseRelationship rel){
        setSourceObjectType(rel);
        setTargetObjectType(rel);
    }
    
    public void setSourceObjectType(EnterpriseRelationship rel){
        EnterpriseObject source = rel.getSOURCE();
        rel.setSOURCE_TYPE(source.getClass().getSimpleName());
    }
    
    public void setTargetObjectType(EnterpriseRelationship rel){
        EnterpriseObject target = rel.getTARGET();
        rel.setTARGET_TYPE(target.getClass().getSimpleName());
    }
}
