/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author LeeKiatHaw
 */
public class SearchableObjectListener {
    @PrePersist
    @PreUpdate
    public void PrePersistUpdate(Searchable object){
        copySearchName(object);
    }

    private void copySearchName(Searchable object) {
        object.copySearchName();
    }
}
