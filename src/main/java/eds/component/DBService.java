/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component;

import java.sql.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.joda.time.DateTime;

/**
 *
 * @author LeeKiatHaw
 */
public abstract class DBService {
    
    /**
     * Inject an instance of EntityManager based on config in persistence.xml.
     * The persistence.xml must be in the project and includes this EDS package
     * as a JAR file.
     */
    @PersistenceContext(name = "HIBERNATE")
    protected EntityManager em;
    
    protected java.sql.Date TAP_START;
    protected java.sql.Date TAP_END;
    
    public DBService(Date TAP_START, Date TAP_END) {
        this.TAP_START = TAP_START;
        this.TAP_END = TAP_END;
    }
    
    public DBService(){
        //by default, all dates are today
        DateTime today = new DateTime();
        java.sql.Date todaySQL = new java.sql.Date(today.getMillis());
        
        this.TAP_START = (java.sql.Date)todaySQL.clone();
        this.TAP_END = (java.sql.Date)todaySQL.clone();
    }

    public EntityManager getEm() {
        return em;
    }
}
