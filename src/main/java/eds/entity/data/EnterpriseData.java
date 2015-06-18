/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eds.entity.data;

import eds.entity.audit.AuditedObject;
import java.sql.Date;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author KH
 * @param <T>
 */
@Entity
@Table(name="ENTERPRISEDATA")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name="DATA_TYPE")
@EntityListeners(EnterpriseDataListener.class)
public abstract class EnterpriseData<T extends EnterpriseObject> extends AuditedObject{
    
    protected T OWNER;
    protected java.sql.Date START_DATE;
    protected java.sql.Date END_DATE;
    protected int SNO;

    /**
     * 
     * @return 
     */
    //@Id @ManyToOne(fetch=FetchType.LAZY) //For performance's sake
    @Id @ManyToOne(fetch=FetchType.EAGER,//Actually, it won't affect performance much as each ED only has 1 EO.
            cascade = CascadeType.ALL, //To resolve the issue of null column when merging
            optional=false,
            targetEntity=EnterpriseObject.class) 
    public T getOWNER() {
        return OWNER;
    }

    public void setOWNER(T OWNER) {
        this.OWNER = OWNER;
    }

    /**
     * Start date is a primary as all EnterpriseObjects can have multiple EnterpriseData
     * records
     * @return 
     */
    @Id
    public Date getSTART_DATE() {
        return START_DATE;
    }

    public void setSTART_DATE(Date START_DATE) {
        this.START_DATE = START_DATE;
    }

    /**
     * End date is a primary as all EnterpriseObjects can have multiple EnterpriseData
     * records
     * @return 
     */
    @Id 
    public Date getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(Date END_DATE) {
        this.END_DATE = END_DATE;
    }

    @Id 
    public int getSNO() {
        return SNO;
    }

    public void setSNO(int SNO) {
        this.SNO = SNO;
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
    
    
}
