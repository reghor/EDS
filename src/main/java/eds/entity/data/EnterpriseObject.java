/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eds.entity.data;

import eds.entity.audit.AuditedObject;
import eds.entity.audit.AuditedObjectListener;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author KH
 */
@Entity
@Table(name="ENTERPRISEOBJECT")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="OBJECT_TYPE")
@TableGenerator(name="ENTERPRISE_OBJECT_SEQ",initialValue=1,allocationSize=10,table="SEQUENCE")
@EntityListeners({
    AuditedObjectListener.class,
    EnterpriseObjectListener.class})
public abstract class EnterpriseObject 
        implements AuditedObject, Serializable, Comparable<EnterpriseObject> {
    
    protected long OBJECTID;
    protected String OBJECT_NAME;
    /*
     * Start and End dates should not be primary keys
     * - Only 1 instance of an entity should exist anytime
     * - If both start and end dates are PK, this means >1 record can be created
     * for 1 object id.
     * - Each object instance can only have 1 record
     */
    /*@Id*/ protected java.sql.Date START_DATE;
    /*@Id*/ protected java.sql.Date END_DATE;
    
    protected String SEARCH_TERM;
    
    /**
     * Previously these fields were from AuditedObject, but moved here as JPA
     * does not persist fields that are not belonging to a mapped entity - 
     * the superclass must have a generated ID and exist by its own.
     */
    protected java.sql.Date DATE_CHANGED;
    protected String CHANGED_BY;
    protected java.sql.Date DATE_CREATED;
    protected String CREATED_BY;

    

    @Id @GeneratedValue(generator="ENTERPRISE_OBJECT_SEQ",strategy=GenerationType.TABLE) 
    public long getOBJECTID() {
        return OBJECTID;
    }

    public void setOBJECTID(long OBJECTID) {
        this.OBJECTID = OBJECTID;
    }

    /**
     * You can't set object name, it is part of the EnterpriseObjectListener module
     * which copies the child object's Alias into OBJECT_NAME.
     * 
     * @return 
     */
    public String getOBJECT_NAME() {
        return OBJECT_NAME;
    }

    public void setOBJECT_NAME(String OBJECT_NAME) {
        this.OBJECT_NAME = OBJECT_NAME;
    }
    
    public Date getSTART_DATE() {
        return START_DATE;
    }

    public void setSTART_DATE(Date START_DATE) {
        this.START_DATE = START_DATE;
    }

    public Date getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(Date END_DATE) {
        this.END_DATE = END_DATE;
    }

    public String getSEARCH_TERM() {
        return SEARCH_TERM;
    }

    public void setSEARCH_TERM(String SEARCH_TERM) {
        this.SEARCH_TERM = SEARCH_TERM;
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
    
    public abstract void randInit();
    
    public abstract Object generateKey();
    
    /**
     * Essential method for the EnterpriseObjectListener to copy the correct attribute
     * as the OBJECT_NAME of the EnterpriseObject.
     * 
     * @return 
     */
    public abstract String alias();

    /**
     * 
     * @param o
     * @return 
     */
    @Override
    public int compareTo(EnterpriseObject o) {
        if(this.OBJECTID == o.OBJECTID) return 0;
        return (this.OBJECTID > o.OBJECTID ? 1 : -1);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (int) (this.OBJECTID ^ (this.OBJECTID >>> 32));
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
        final EnterpriseObject other = (EnterpriseObject) obj;
        if (this.OBJECTID != other.OBJECTID) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "EnterpriseObject{" + "OBJECTID=" + OBJECTID + ", OBJECT_NAME=" + OBJECT_NAME + ", START_DATE=" + START_DATE + ", END_DATE=" + END_DATE + '}';
    }

    
    
}
