/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.audit;

import javax.enterprise.context.SessionScoped;

/**
 *
 * @author LeeKiatHaw
 */
//@SessionScoped //The reason why CDI could not resolve the subclasses of this interface is because it was not annotated with a scope
public interface ActiveUser{
    
    public String getUsername();
}
