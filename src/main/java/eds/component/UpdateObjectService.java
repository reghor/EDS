/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component;

import eds.component.data.DBConnectionException;
import eds.component.data.EntityNotFoundException;
import eds.entity.data.EnterpriseObject;
import eds.entity.data.EnterpriseRelationship;
import eds.entity.data.EnterpriseRelationship_;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import org.hibernate.exception.GenericJDBCException;

/**
 *
 * @author LeeKiatHaw
 */
@Stateless
public class UpdateObjectService extends Service {
    
    @EJB
    private GenericObjectService GenericObjectService;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteObjectAndRelationships(long objectId) throws EntityNotFoundException{
        try {
            //Get the object
            EnterpriseObject object = em.find(EnterpriseObject.class, objectId);
            if (object == null) {
                throw new EntityNotFoundException(object.getClass(), objectId);
            }
            //Get all relationships first
            /*List<EnterpriseRelationship> allRel = new ArrayList<>();
            allRel.addAll(GenericObjectService.getRelationshipsForSourceObject(objectId));
            allRel.addAll(GenericObjectService.getRelationshipsForTargetObject(objectId));
            */
            //Delete all relationships first
            //How to do a mass delete here?
            //em.remove(em.contains(allRel) ? allRel : em.merge(allRel));
            deleteRelationshipBySource(objectId,EnterpriseRelationship.class);
            deleteRelationshipByTarget(objectId,EnterpriseRelationship.class);
            
            //and remove the object itself
            em.remove(em.contains(object) ? object : em.merge(object));
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public <R extends EnterpriseRelationship> int deleteRelationshipBySource(long sourceId,Class<R> r){
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaDelete<R> delCriteria = builder.createCriteriaDelete(r);
            Root<R> root = delCriteria.from(r);
            
            delCriteria.where(builder.equal(root.get(EnterpriseRelationship_.SOURCE), sourceId));
            
            int result = em.createQuery(delCriteria).executeUpdate();
            
            return result;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public <R extends EnterpriseRelationship> int deleteRelationshipByTarget(long targetId,Class<R> r){
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaDelete<R> delCriteria = builder.createCriteriaDelete(r);
            Root<R> root = delCriteria.from(r);
            
            delCriteria.where(builder.equal(root.get(EnterpriseRelationship_.TARGET), targetId));
            
            int result = em.createQuery(delCriteria).executeUpdate();
            
            return result;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        }
    }
}
