/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component;

import eds.component.data.DBConnectionException;
import eds.entity.EnterpriseObject;
import eds.entity.EnterpriseObject_;
import eds.entity.EnterpriseRelationship;
import eds.entity.EnterpriseRelationship_;
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
    public List<EnterpriseObject> getEnterpriseObjectByName(String objectName)
        throws DBConnectionException{
        try{
            
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<EnterpriseObject> criteria = builder.createQuery(EnterpriseObject.class);
            Root<EnterpriseObject> sourceEntity = criteria.from(EnterpriseObject.class); //FROM 

            criteria.select(sourceEntity); // SELECT *
            
            criteria.where(builder.equal(sourceEntity.get(EnterpriseObject_.OBJECT_NAME),objectName));
            
            List<EnterpriseObject> results = em.createQuery(criteria)
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
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EnterpriseRelationship> getRelationshipsForSourceObject(long sourceobjectid)
        throws DBConnectionException{
        try{
            //We wanted to use em.find(), but that would require us to create a 
            //composite primary key class, which might complicate things a little.
            //So let's stick to typed queries.
            
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<EnterpriseRelationship> criteria = builder.createQuery(EnterpriseRelationship.class);
            Root<EnterpriseRelationship> sourceEntity = criteria.from(EnterpriseRelationship.class); //FROM UserType

            criteria.select(sourceEntity); // SELECT *
            
            criteria.where(
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.SOURCE), sourceobjectid)
                );
            
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
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T extends EnterpriseRelationship> List<T> getRelationshipsForSourceObject(long sourceobjectid, Class<T> c)
        throws DBConnectionException{
        try{
            //We wanted to use em.find(), but that would require us to create a 
            //composite primary key class, which might complicate things a little.
            //So let's stick to typed queries.
            
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(c);
            Root<T> sourceEntity = criteria.from(c); //FROM UserType

            criteria.select(sourceEntity); // SELECT *
            
            criteria.where(
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.SOURCE), sourceobjectid)
                );
            
            List<T> results = em.createQuery(criteria)
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
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EnterpriseRelationship> getRelationshipsForTargetObject(long targetobjectid)
        throws DBConnectionException{
        try{
            //We wanted to use em.find(), but that would require us to create a 
            //composite primary key class, which might complicate things a little.
            //So let's stick to typed queries.
            
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<EnterpriseRelationship> criteria = builder.createQuery(EnterpriseRelationship.class);
            Root<EnterpriseRelationship> sourceEntity = criteria.from(EnterpriseRelationship.class); //FROM UserType

            criteria.select(sourceEntity); // SELECT *
            
            criteria.where(
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.TARGET), targetobjectid)
                );
            
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
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T extends EnterpriseRelationship> List<T> getRelationshipsForTargetObject(long targetobjectid, Class<T> c)
        throws DBConnectionException{
        try{
            //We wanted to use em.find(), but that would require us to create a 
            //composite primary key class, which might complicate things a little.
            //So let's stick to typed queries.
            
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(c);
            Root<T> sourceEntity = criteria.from(c); //FROM UserType

            criteria.select(sourceEntity); // SELECT *
            
            criteria.where(
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.TARGET), targetobjectid)
                );
            
            List<T> results = em.createQuery(criteria)
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
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EnterpriseRelationship> getRelationshipsForObjects(long sourceobjectid, long targetobjectid)
            throws DBConnectionException{
        try{
            //We wanted to use em.find(), but that would require us to create a 
            //composite primary key class, which might complicate things a little.
            //So let's stick to typed queries.
            
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<EnterpriseRelationship> criteria = builder.createQuery(EnterpriseRelationship.class);
            Root<EnterpriseRelationship> sourceEntity = criteria.from(EnterpriseRelationship.class); //FROM UserType

            criteria.select(sourceEntity); // SELECT *
            
            criteria.where(
                builder.and(
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.SOURCE), sourceobjectid),
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.TARGET), targetobjectid)
                )
            );
            
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
    
    public <T extends EnterpriseObject> T getEnterpriseObjectById(Long objectid, Class<T> c) 
            throws DBConnectionException{
        try{
            return em.find(c, objectid);
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw pex;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public <T extends EnterpriseObject> List<T> getEnterpriseObjectsByName(String objectName,Class<T> c)
        throws DBConnectionException{
        try{
            
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(c);
            Root<T> sourceEntity = criteria.from(c); //FROM 

            criteria.select(sourceEntity); // SELECT *
            
            criteria.where(builder.equal(sourceEntity.get(EnterpriseObject_.OBJECT_NAME),objectName));
            
            List<T> results = em.createQuery(criteria)
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
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <R extends EnterpriseRelationship> List<R> getRelationshipsForObjects(long sourceobjectid, long targetobjectid, Class<R> r)
            throws DBConnectionException{
        try{
            //We wanted to use em.find(), but that would require us to create a 
            //composite primary key class, which might complicate things a little.
            //So let's stick to typed queries.
            
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<R> criteria = builder.createQuery(r);
            Root<R> sourceEntity = criteria.from(r); //FROM UserType

            criteria.select(sourceEntity); // SELECT *
            
            criteria.where(
                builder.and(
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.SOURCE), sourceobjectid),
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.TARGET), targetobjectid)
                )
            );
            
            List<R> results = em.createQuery(criteria)
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
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T extends EnterpriseObject> List<T> getAllEnterpriseObjects(Class<T> c) throws DBConnectionException{
        try{
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(c);
            Root<T> sourceEntity = criteria.from(c); //FROM UserType

            criteria.select(sourceEntity); // SELECT *
            
            List<T> results = em.createQuery(criteria)
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
