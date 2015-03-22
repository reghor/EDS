/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component;

import eds.component.data.DBConnectionException;
import eds.entity.EnterpriseObject;
import eds.entity.EnterpriseRelationship;
import eds.entity.EnterpriseRelationship_;
import eds.entity.client.Client;
import eds.entity.user.UserType;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.exception.GenericJDBCException;

/**
 *
 * @author LeeKiatHaw
 */
@Stateless
public class GenericEnterpriseObjectService {
    
    @PersistenceContext(name = "HIBERNATE")
    private EntityManager em;
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EnterpriseObject getEnterpriseObjectById(long objectid) throws DBConnectionException{
        try{
            return em.find(EnterpriseObject.class, objectid);
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw pex;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EnterpriseRelationship> getRelByObjectId(long sourceid, long targetid)
            throws DBConnectionException{
        try{
            //We wanted to use em.find(), but that would require us to create a 
            //composite primary key class, which might complicate things a little.
            //So let's stick to typed queries.
            
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<EnterpriseRelationship> criteria = builder.createQuery(EnterpriseRelationship.class);
            Root<EnterpriseRelationship> sourceEntity = criteria.from(EnterpriseRelationship.class); //FROM UserType

            criteria.select(sourceEntity); // SELECT *
            
            criteria.where(builder.and(
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.SOURCE), sourceid),
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.TARGET), targetid)
                ));
            
            List<EnterpriseRelationship> results = em.createQuery(criteria)
                    .getResultList();
            
            return results;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw pex;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
