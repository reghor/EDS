/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component.data;

import eds.entity.data.EnterpriseData;
import javax.ejb.ApplicationException;

/**
 * By 2015 Apr, I had started to realize that I created a separate Exception type
 * for every single Service method that updates and this is useless and unnecessary.
 * One of the key purpose of exceptions is to group similar exception types and
 * handle them in a similar way (https://docs.oracle.com/javase/tutorial/essential/exceptions/advantages.html).
 * A good example is DBConnectionException. 
 * 
 * This will start a series of exception types that basically tell the same stories
 * and will hopefully be handled in a similar way.
 * 
 * @author LeeKiatHaw
 */
@ApplicationException(rollback = true)
public class MissingOwnerException extends Exception{
    
    public MissingOwnerException(EnterpriseData d){
        super("No owner set for data "+d+".");
    }
    
}
