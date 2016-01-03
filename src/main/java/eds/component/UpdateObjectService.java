/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component;

import eds.component.data.DBConnectionException;
import eds.component.data.EntityNotFoundException;
import eds.entity.data.EnterpriseData;
import eds.entity.data.EnterpriseData_;
import eds.entity.data.EnterpriseObject;
import eds.entity.data.EnterpriseRelationship;
import eds.entity.data.EnterpriseRelationship_;
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
public class UpdateObjectService extends DBService {
    
    @EJB
    private GenericObjectService GenericObjectService;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteObjectDataAndRelationships(long objectId) throws EntityNotFoundException{
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
            deleteAllEnterpriseDataByType(objectId,EnterpriseData.class);
            deleteRelationshipBySource(objectId,EnterpriseRelationship.class);
            deleteRelationshipByTarget(objectId,EnterpriseRelationship.class);
            
            //Leave all EnterpriseData?
            
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
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <D extends EnterpriseData> int deleteAllEnterpriseDataByType(long ownerId,Class<D> d) {
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaDelete<D> delCriteria = builder.createCriteriaDelete(d);
            Root<D> root = delCriteria.from(d);
            
            delCriteria.where(builder.equal(root.get(EnterpriseData_.OWNER), ownerId));
            
            int result = em.createQuery(delCriteria).executeUpdate();
            
            return result;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        }
    }
    
    /* might not need
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deleteAllEnterpriseData(long ownerId) {
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaDelete<EnterpriseData> delCriteria = builder.createCriteriaDelete(EnterpriseData.class);
            Root<EnterpriseData> root = delCriteria.from(EnterpriseData.class);
            
            delCriteria.where(builder.equal(root.get(EnterpriseData_.OWNER), ownerId));
            
            int result = em.createQuery(delCriteria).executeUpdate();
            
            return result;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        }
    }*/
}
