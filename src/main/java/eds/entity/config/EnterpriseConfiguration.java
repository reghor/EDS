/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.config;

import eds.entity.audit.AuditedObject;
import eds.entity.audit.AuditedObjectListener;
import eds.entity.data.EnterpriseObject;
import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * All configuration share the same ID sequence. What is different is that all 
 * config types are stored in their own table.
 * 
 * @author LeeKiatHaw
 */
@Entity
@Table(name="ENTERPRISE_CONFIG")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@TableGenerator(name="ENTERPRISE_CONFIG_SEQ",initialValue=1,allocationSize=10,table="SEQUENCE")
@EntityListeners({
    AuditedObjectListener.class
})
public abstract class EnterpriseConfiguration implements AuditedObject{
    
    protected long CONFIG_ID;
    protected java.sql.Date CONFIG_EFF_DATE;
    protected java.sql.Date CONFIG_END_DATE;
    
    protected List<EnterpriseObject> OWNERS;

    /**
     * Previously these fields were from AuditedObject, but moved here as JPA
     * does not persist fields that are not belonging to a mapped entity - 
     * the superclass must have a generated ID and exist by its own.
     */
    protected java.sql.Date DATE_CHANGED;
    protected String CHANGED_BY;
    protected java.sql.Date DATE_CREATED;
    protected String CREATED_BY;
    
    protected String VALUE;

    @Id @GeneratedValue(generator="ENTERPRISE_CONFIG_SEQ",strategy=GenerationType.TABLE) 
    public long getCONFIG_ID() {
        return CONFIG_ID;
    }

    public void setCONFIG_ID(long CONFIG_ID) {
        this.CONFIG_ID = CONFIG_ID;
    }

    public Date getCONFIG_EFF_DATE() {
        return CONFIG_EFF_DATE;
    }

    public void setCONFIG_EFF_DATE(Date CONFIG_EFF_DATE) {
        this.CONFIG_EFF_DATE = CONFIG_EFF_DATE;
    }

    public Date getCONFIG_END_DATE() {
        return CONFIG_END_DATE;
    }

    public void setCONFIG_END_DATE(Date CONFIG_END_DATE) {
        this.CONFIG_END_DATE = CONFIG_END_DATE;
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

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }
    
    @OneToMany(targetEntity=EnterpriseObject.class,mappedBy="OBJECTID")
    public List<EnterpriseObject> getOWNERS() {
        return OWNERS;
    }

    public void setOWNERS(List<EnterpriseObject> OWNERS) {
        this.OWNERS = OWNERS;
    }
}
