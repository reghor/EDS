/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.audit;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author LeeKiatHaw
 */
@SessionScoped
public class DefaultActiveUser implements ActiveUser, Serializable {

    @Override
    public String getUsername() {
        return "";
    }
    
}
