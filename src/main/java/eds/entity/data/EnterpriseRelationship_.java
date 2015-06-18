/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eds.entity.data;

import eds.entity.audit.AuditedObject;
import eds.entity.audit.AuditedObject_;
import java.sql.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author KH
 * @param <S>
 * @param <T>
 */
@StaticMetamodel(EnterpriseRelationship.class)
public class EnterpriseRelationship_<S extends EnterpriseObject_,T extends EnterpriseObject_> extends AuditedObject_{
    
    
    public static volatile SingularAttribute<EnterpriseRelationship,? extends EnterpriseObject> SOURCE;
    public static volatile SingularAttribute<EnterpriseRelationship,? extends EnterpriseObject> TARGET;
    public static volatile SingularAttribute<EnterpriseRelationship,String> REL_TYPE;
}
