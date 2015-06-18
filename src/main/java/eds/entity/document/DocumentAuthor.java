/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.document;

import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author LeeKiatHaw
 */
@Entity
@Table(name="DocumentAuthor")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public interface DocumentAuthor {
    
    /**
     * Returns the unique identifier of the author
     * 
     * @return 
     */
    @Id
    public long getAuthorId();
    
    public void setAuthorId(long id);
    
    /**
     * With an input of ideas, create a specific document of the author's choice.
     * 
     * @param ideas
     * @return 
     */
    public Document createDocument(Map<String,Object> ideas);
    
    /**
     * Gets the identifier of the author. This may not be an unique identifier, 
     * it will all depends on how the document is signed. eg. Emails are signed 
     * with sender's (Author) email address.
     * 
     * @return 
     */
    public String getSignature();
    
    public void setSignature(String signature);
    
    /**
     * Gets the human-recognizable name of the author. This is like an alias or 
     * display name for the author eg. Email addresses would have a display name 
     * "Doe, John <john.doe@segmail.com>"
     * 
     * @return 
     */
    public String getName();
    
    public void setName(String name);
    
}
