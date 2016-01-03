/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component;

import eds.component.data.DBConnectionException;
import eds.entity.data.EnterpriseObject;
import eds.entity.data.EnterpriseRelationship;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import org.hibernate.exception.GenericJDBCException;

/**
 *
 * @author LeeKiatHaw
 */
@Stateless
public class StaticObjectService extends DBService {

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Class> getAllEntities() throws DBConnectionException {
        try {
            Metamodel metamodel = em.getMetamodel();
            List<Class> allEntities = new ArrayList<Class>();
            for (final ManagedType<?> managedType : metamodel.getManagedTypes()) {
                allEntities.add(managedType.getJavaType()); // this returns the java class of the @Entity object
            }
            return allEntities;
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw pex;
        } catch (Exception ex) {
            throw ex;
        }

    }

    public List<Class<? extends EnterpriseObject>> getAllEnterpriseObjects() {
        List<Class<? extends EnterpriseObject>> allEO = new ArrayList<>();
        List<Class> allEntities = getAllEntities();
        for (Class c : allEntities) {
            if (c.isAssignableFrom(EnterpriseObject.class)) {
                allEO.add(c);
            }
        }
        return allEO;
    }

    public List<Class<? extends EnterpriseRelationship>> getAllEnterpriseRelationships() {
        List<Class<? extends EnterpriseRelationship>> allRel = new ArrayList<>();
        List<Class> allEntities = getAllEntities();
        for (Class c : allEntities) {
            if (c.isAssignableFrom(EnterpriseRelationship.class)) {
                allRel.add(c);
            }
        }
        return allRel;
    }

    public <E extends EnterpriseObject> List<Class<? extends EnterpriseRelationship>>
            getAllEnterpriseRelationshipsForSourceObject(Class<E> e) {
        List<Class<? extends EnterpriseRelationship>> allRel = getAllEnterpriseRelationships();
        List<Class<? extends EnterpriseRelationship>> connectedRel = new ArrayList<>();
        try {
            for (Class<? extends EnterpriseRelationship> r : allRel) {
                if (r.getField("SOURCE").getClass().equals(e)) {
                    connectedRel.add(r);
                }
            }
            return connectedRel;
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException("This shouldn't happen in StaticObjectService:"+ex.getMessage());
        } catch (SecurityException ex) {
            throw new RuntimeException("This shouldn't happen in StaticObjectService:"+ex.getMessage());
        }

    }
}
