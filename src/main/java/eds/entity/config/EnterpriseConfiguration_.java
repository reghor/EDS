/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.config;

import eds.entity.audit.AuditedObject;
import eds.entity.audit.AuditedObject_;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author LeeKiatHaw
 */
@StaticMetamodel(EnterpriseConfiguration.class)
public class EnterpriseConfiguration_ extends AuditedObject_ {
    public static volatile SingularAttribute<EnterpriseConfiguration,Long> CONFIG_ID;
    public static volatile SingularAttribute<EnterpriseConfiguration,java.sql.Date>  CONFIG_EFF_DATE;
    public static volatile SingularAttribute<EnterpriseConfiguration,java.sql.Date> CONFIG_END_DATE;
    public static volatile SingularAttribute<EnterpriseConfiguration,Integer> SNO;
}
