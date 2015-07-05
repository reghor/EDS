/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eds.entity.data;

import eds.entity.audit.AuditedObject;
import eds.entity.audit.AuditedObjectListener;
import java.sql.Date;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author KH
 * @param <S>
 * @param <T>
 */
@Entity
@Table(name="ENTERPRISE_RELATIONSHIP")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="REL_TYPE")
@EntityListeners({
    
    EnterpriseObjectListener.class})
public abstract class EnterpriseRelationship<S extends EnterpriseObject,T extends EnterpriseObject> extends AuditedObject {
    
    protected S SOURCE;
    protected T TARGET;
    
    //protected String REL_TYPE;
    protected int REL_SEQUENCE;
    
    protected String SOURCE_TYPE;
    protected String TARGET_TYPE;

    @Id @ManyToOne(targetEntity=EnterpriseObject.class)
    public S getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(S SOURCE) {
        this.SOURCE = SOURCE;
    }

    @Id @ManyToOne(targetEntity=EnterpriseObject.class)
    public T getTARGET() {
        return TARGET;
    }

    public void setTARGET(T TARGET) {
        this.TARGET = TARGET;
    }
    
    /*
    @Id
    public String getREL_TYPE() {
        return REL_TYPE;
    }

    public void setREL_TYPE(String REL_TYPE) {
        this.REL_TYPE = REL_TYPE;
    }
    */
    
    @Id
    public int getREL_SEQUENCE() {
        return REL_SEQUENCE;
    }

    public void setREL_SEQUENCE(int REL_SEQUENCE) {
        this.REL_SEQUENCE = REL_SEQUENCE;
    }

    public String getSOURCE_TYPE() {
        return SOURCE_TYPE;
    }

    public void setSOURCE_TYPE(String SOURCE_TYPE) {
        this.SOURCE_TYPE = SOURCE_TYPE;
    }

    public String getTARGET_TYPE() {
        return TARGET_TYPE;
    }

    public void setTARGET_TYPE(String TARGET_TYPE) {
        this.TARGET_TYPE = TARGET_TYPE;
    }
    
    

    /**
     * Testing method for initializing random values for a table row
     */
    public abstract void randInit();

    /**
     * Generates an object that represents the identifier of the row object
     *
     *
     * @return Object - Key of this table row
     */
    public abstract Object generateKey();
    
    /**
     * Helpful constructor
     * 
     * @param s
     * @param t 
     */
    public EnterpriseRelationship(S s, T t){
        this.setSOURCE(s);
        this.setTARGET(t);
    }
    
    public EnterpriseRelationship(){
        
    }
}
