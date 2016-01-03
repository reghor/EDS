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
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
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
 * @param <T>
 */
@Entity
@Table(name="ENTERPRISEDATA")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name="DATA_TYPE")
@EntityListeners({
    AuditedObjectListener.class,
    EnterpriseDataListener.class
})
public abstract class EnterpriseData<T extends EnterpriseObject> implements AuditedObject{
    
    protected T OWNER;
    protected java.sql.Date START_DATE;
    protected java.sql.Date END_DATE;
    protected int SNO;

    /**
     * Previously these fields were from AuditedObject, but moved here as JPA
     * does not persist fields that are not belonging to a mapped entity - 
     * the superclass must have a generated ID and exist by its own.
     */
    protected java.sql.Date DATE_CHANGED;
    protected String CHANGED_BY;
    protected java.sql.Date DATE_CREATED;
    protected String CREATED_BY;
    /**
     * 
     * @return 
     */
    //@Id @ManyToOne(fetch=FetchType.LAZY) //For performance's sake
    @Id @ManyToOne(fetch=FetchType.EAGER,//Actually, it won't affect performance much as each ED only has 1 EO.
            cascade = CascadeType.ALL, //To resolve the issue of null column when merging
            optional=false,
            targetEntity=EnterpriseObject.class)
    @JoinColumn(name="OWNER",
            referencedColumnName="OBJECTID",
            foreignKey=@ForeignKey(name="OWNER",value=NO_CONSTRAINT))
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.OWNER);
        hash = 79 * hash + Objects.hashCode(this.START_DATE);
        hash = 79 * hash + Objects.hashCode(this.END_DATE);
        hash = 79 * hash + this.SNO;
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
        final EnterpriseData<?> other = (EnterpriseData<?>) obj;
        if (!Objects.equals(this.OWNER, other.OWNER)) {
            return false;
        }
        if (!Objects.equals(this.START_DATE, other.START_DATE)) {
            return false;
        }
        if (!Objects.equals(this.END_DATE, other.END_DATE)) {
            return false;
        }
        if (this.SNO != other.SNO) {
            return false;
        }
        return true;
    }
    
    
}
