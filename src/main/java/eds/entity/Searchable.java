/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity;

/**
 *
 * @author LeeKiatHaw
 */
public interface Searchable {
    
    /**
     * 
     * @return SearchName that can be indexed.
     */
    public String getSearchName();
    
    /**
     * 
     */
    public void copySearchName();
}
