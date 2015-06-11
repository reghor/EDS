/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.data;

import eds.entity.audit.AuditedObject;
import eds.entity.audit.AuditedObjectListener;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * FileEntity is not an EnterpriseObject. It is a fundamental system object that
 * can exist without any enterprise structure.
 * Document is an EnterpriseObject.
 * 
 * @author LeeKiatHaw
 */
@Entity
@Table(name="FILE_ENTITY")
//@Inheritance(strategy=InheritanceType.JOINED) //May need this in the future
@TableGenerator(name="FILE_SEQ",initialValue=1,allocationSize=10,table="SEQUENCE")
@EntityListeners(AuditedObjectListener.class)
public class FileEntity extends AuditedObject implements Serializable {
 
    private long FILE_ID;
    private String NAME;
    private String CHECKSUM;
    private byte[] CONTENT;

    @Id @GeneratedValue(generator="FILE_SEQ",strategy=GenerationType.TABLE) 
    public long getFILE_ID() {
        return FILE_ID;
    }

    public void setFILE_ID(long FILE_ID) {
        this.FILE_ID = FILE_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCHECKSUM() {
        return CHECKSUM;
    }

    public void setCHECKSUM(String CHECKSUM) {
        this.CHECKSUM = CHECKSUM;
    }

    public byte[] getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(byte[] CONTENT) {
        this.CONTENT = CONTENT;
    }
    
    
}
