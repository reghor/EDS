/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eds.entity.data;

import eds.entity.audit.AuditedObject;
import eds.entity.audit.AuditedObjectListener;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import static javax.persistence.ConstraintMode.NO_CONSTRAINT;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
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
    AuditedObjectListener.class,
    EnterpriseRelationshipListener.class
    })
public abstract class EnterpriseRelationship<S extends EnterpriseObject,T extends EnterpriseObject> implements AuditedObject {
    
    protected S SOURCE;
    protected T TARGET;
    
    //protected String REL_TYPE;
    protected int REL_SEQUENCE;
    
    protected String SOURCE_TYPE;
    protected String TARGET_TYPE;
    
    /**
     * Previously these fields were from AuditedObject, but moved here as JPA
     * does not persist fields that are not belonging to a mapped entity - 
     * the superclass must have a generated ID and exist by its own.
     */
    protected java.sql.Date DATE_CHANGED;
    protected String CHANGED_BY;
    protected java.sql.Date DATE_CREATED;
    protected String CREATED_BY;


    @Id @ManyToOne(targetEntity=EnterpriseObject.class)
    @JoinColumn(name="SOURCE",
            referencedColumnName="OBJECTID",
            foreignKey=@ForeignKey(name="SOURCE",value=NO_CONSTRAINT))
    public S getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(S SOURCE) {
        this.SOURCE = SOURCE;
    }

    @Id @ManyToOne(targetEntity=EnterpriseObject.class)
    @JoinColumn(name="TARGET",
            referencedColumnName="OBJECTID",
            foreignKey=@ForeignKey(name="TARGET",value=NO_CONSTRAINT))
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

    @Override
    public Date getDATE_CREATED() {
        return DATE_CREATED;
    }

    @Override
    public void setDATE_CREATED(Date DATE_CREATED) {
        this.DATE_CREATED = DATE_CREATED;
    }

    @Override
    public Date getDATE_CHANGED() {
        return DATE_CHANGED;
    }

    @Override
    public void setDATE_CHANGED(Date DATE_CHANGED) {
        this.DATE_CHANGED = DATE_CHANGED;
    }

    @Override
    public String getCHANGED_BY() {
        return CHANGED_BY;
    }

    @Override
    public void setCHANGED_BY(String CHANGED_BY) {
        this.CHANGED_BY = CHANGED_BY;
    }

    @Override
    public String getCREATED_BY() {
        return CREATED_BY;
    }

    @Override
    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.SOURCE);
        hash = 59 * hash + Objects.hashCode(this.TARGET);
        hash = 59 * hash + this.REL_SEQUENCE;
        hash = 59 * hash + Objects.hashCode(this.SOURCE_TYPE);
        hash = 59 * hash + Objects.hashCode(this.TARGET_TYPE);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EnterpriseRelationship<?, ?> other = (EnterpriseRelationship<?, ?>) obj;
        if (!Objects.equals(this.SOURCE, other.SOURCE)) {
            return false;
        }
        if (!Objects.equals(this.TARGET, other.TARGET)) {
            return false;
        }
        if (this.REL_SEQUENCE != other.REL_SEQUENCE) {
            return false;
        }
        return true;
    }
    
    
}
