/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.document;

import eds.entity.audit.AuditedObject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;

/**
 *
 * @author LeeKiatHaw
 * @param <A>
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="DOC_TYPE")
@TableGenerator(name="DOCUMENT_ID_GEN",initialValue=1,allocationSize=100,table="SEQUENCE")
public abstract class Document<A extends DocumentAuthor> implements AuditedObject {
    
    protected long DOCUMENT_ID;
    
    protected List<A> AUTHORS = new ArrayList<A>();
    
    /**
     * Previously these fields were from AuditedObject, but moved here as JPA
     * does not persist fields that are not belonging to a mapped entity - 
     * the superclass must have a generated ID and exist by its own.
     */
    protected java.sql.Date DATE_CHANGED;
    protected String CHANGED_BY;
    protected java.sql.Date DATE_CREATED;
    protected String CREATED_BY;

    @Id @GeneratedValue(generator="DOCUMENT_ID_GEN",strategy=GenerationType.TABLE) 
    public long getDOCUMENT_ID() {
        return DOCUMENT_ID;
    }

    public void setDOCUMENT_ID(long DOCUMENT_ID) {
        this.DOCUMENT_ID = DOCUMENT_ID;
    }
    
    @ManyToMany(fetch=FetchType.EAGER,
            targetEntity=DocumentAuthor.class)                
    public List<A> getAUTHORS() {
        return AUTHORS;
    }

    public void setAUTHORS(List<A> AUTHORS) {
        this.AUTHORS = AUTHORS;
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
    
}
