/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eds.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author KH
 */
@StaticMetamodel(AuditedObject.class)
public class AuditedObject_ {
    public static volatile SingularAttribute<AuditedObject,java.sql.Date> DATE_CHANGED;
    public static volatile SingularAttribute<AuditedObject, String> CHANGED_BY;
    public static volatile SingularAttribute<AuditedObject, java.sql.Date> DATE_CREATED;
    public static volatile SingularAttribute<AuditedObject, String> CREATED_BY;
}
