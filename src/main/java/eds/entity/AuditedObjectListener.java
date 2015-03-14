/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eds.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.joda.time.DateTime;

/**
 *
 * @author KH
 */
public class AuditedObjectListener {
    
    //Inject an user object here for CHANGED_BY and CREATED_BY
    
    @PrePersist
    @PreUpdate
    public void PrePersistUpdate(AuditedObject object){
        recordDateChanged(object);
        recordCreated(object);
    }
    
    
    public void recordDateChanged(AuditedObject object){
        DateTime today = new DateTime();
        java.sql.Date todaySQL = new java.sql.Date(today.getMillis());
        
        object.setDATE_CHANGED(todaySQL);
    }
    
    public void recordCreated(AuditedObject object){
        if(object.getDATE_CREATED() != null) return;
        
        DateTime today = new DateTime();
        java.sql.Date todaySQL = new java.sql.Date(today.getMillis());
        
        object.setDATE_CREATED(todaySQL);
    }
}