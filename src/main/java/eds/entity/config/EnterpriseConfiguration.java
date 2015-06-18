/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.config;

import eds.entity.audit.AuditedObject;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
public abstract class EnterpriseConfiguration extends AuditedObject{
    
    protected long CONFIG_ID;
    protected java.sql.Date CONFIG_EFF_DATE;
    protected java.sql.Date CONFIG_END_DATE;
    protected int SNO;

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

    public int getSNO() {
        return SNO;
    }

    public void setSNO(int SNO) {
        this.SNO = SNO;
    }
    
    
    
}
