/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.document;

import eds.entity.audit.AuditedObject;
import eds.entity.audit.AuditedObject_;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author LeeKiatHaw
 */
@StaticMetamodel(Document.class)
public class Document_ extends AuditedObject_ {
    public static volatile SingularAttribute<AuditedObject,Long> DOCUMENT_ID;
    public static volatile ListAttribute<AuditedObject,? extends DocumentAuthor> AUTHORS;
}
