/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component;

import javax.interceptor.AroundInvoke;

/**
 *
 * @author LeeKiatHaw
 */
public abstract class ServiceInterceptor {
    
    @AroundInvoke
    public void intercept(){
        
    }
}
