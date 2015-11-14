/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.lock;

/**
 * 
 * @author LeeKiatHaw
 */
public interface Lockable {
    
    public boolean isLocked();
    
    public void setLock(boolean lock);
    
    
}
