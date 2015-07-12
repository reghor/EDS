/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eds.entity.audit;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author KH
 */
public interface AuditedObject extends Serializable {
    
    public Date getDATE_CREATED();

    public void setDATE_CREATED(Date DATE_CREATED);

    public Date getDATE_CHANGED();

    public void setDATE_CHANGED(Date DATE_CHANGED);
    
    public String getCHANGED_BY();

    public void setCHANGED_BY(String CHANGED_BY);

    public String getCREATED_BY();

    public void setCREATED_BY(String CREATED_BY);
    
    
}
