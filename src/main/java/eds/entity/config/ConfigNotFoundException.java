/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.config;

/**
 *
 * @author LeeKiatHaw
 */
public class ConfigNotFoundException extends Exception {
    
    public <C extends EnterpriseConfiguration> ConfigNotFoundException(Class<C> c, String valueOrName){
        super("Config "+c.getName()
            + ((valueOrName != null && !valueOrName.isEmpty()) ? " of value or name "+valueOrName : "")
            + " of value or name "+valueOrName);
        
    }
}
