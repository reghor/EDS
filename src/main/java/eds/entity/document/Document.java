/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.document;

import eds.entity.audit.AuditedObject;
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
public abstract class Document<A extends DocumentAuthor> extends AuditedObject {
    
    protected long DOCUMENT_ID;
    
    protected List<A> AUTHORS = new ArrayList<A>();

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
    
    
    
    
}
