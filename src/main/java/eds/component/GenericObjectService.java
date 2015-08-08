/*
 * This class handles all retrieval and update methods on the EnterpriseObject and
 * the EnterpriseRelationship objects. It is a stateless EJB with no state of its 
 * own. 
 */
package eds.component;

import eds.component.data.DBConnectionException;
import eds.entity.data.EnterpriseData;
import eds.entity.data.EnterpriseData_;
import eds.entity.data.EnterpriseObject;
import eds.entity.data.EnterpriseObject_;
import eds.entity.data.EnterpriseRelationship;
import eds.entity.data.EnterpriseRelationship_;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.hibernate.exception.GenericJDBCException;
import org.joda.time.DateTime;

/**
 * 
 * @author LeeKiatHaw
 */
@Stateless
public class GenericObjectService extends Service {

    /**
     * Inject an instance of EntityManager based on config in persistence.xml.
     * The persistence.xml must be in the project and includes this EDS package
     * as a JAR file.
     */
    //@PersistenceContext(name = "HIBERNATE")
    //private EntityManager em;
    
    /**
     * Retrieves an EnterpriseObject with objectid.
     * 
     * @param objectid
     * @return A unique EnterpriseObject
     * @throws DBConnectionException if no DB connection can be established
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EnterpriseObject getEnterpriseObjectById(long objectid) throws DBConnectionException{
        try{
            //Use plain EntityManager methods instead of CriteriaQuery
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
    
    /**
     * Retrieves all EnterpriseObject with the objectName. Disregards the Object Type.
     * 
     * This method could encounter an OutOfMemoryException if there are too many 
     * instances of the same object. Considerations:
     * - How much memory space can be allocated to a List object?
     * - Instead of restricting the maximum number of results, can we provide a form
     * of "Data-dripping" interface that can allow the user to "scroll" through
     * a large amount of objects?
     * 
     * @param objectName
     * @return a list of EnterpriseObject
     * @throws DBConnectionException if no DB connection can be established
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EnterpriseObject> getEnterpriseObjectByName(String objectName)
            throws DBConnectionException {
        try {

            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<EnterpriseObject> criteria = builder.createQuery(EnterpriseObject.class);
            Root<EnterpriseObject> sourceEntity = criteria.from(EnterpriseObject.class); //FROM 

            criteria.select(sourceEntity);//SELECT *
            
            criteria.where(builder.equal(sourceEntity.get(EnterpriseObject_.OBJECT_NAME),objectName)); //WHERE OBJECT_NAME
            
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
    
    /**
     * Retrieves a list of EnterpriseRelationship with the provided object ID as the 
     * source object. 
     * 
     * @param sourceobjectid
     * @return a list of EnterpriseRelationship
     * @throws DBConnectionException 
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EnterpriseRelationship> getRelationshipsForSourceObject(long sourceobjectid)
            throws DBConnectionException {
        try {
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
    
    /**
     * Retrieves a list of EnterpriseRelationship with the provided object ID as the 
     * source object.
     * 
     * @param <T>
     * @param sourceobjectid
     * @param c
     * @return a list of T which are subclasses of EnterpriseRelationship
     * @throws DBConnectionException 
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <R extends EnterpriseRelationship> List<R> getRelationshipsForSourceObject(long sourceobjectid, Class<R> c)
            throws DBConnectionException {
        try {
            //We wanted to use em.find(), but that would require us to create a 
            //composite primary key class, which might complicate things a little.
            //So let's stick to typed queries.

            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<R> criteria = builder.createQuery(c);
            Root<R> sourceEntity = criteria.from(c); //FROM UserType

            criteria.select(sourceEntity); // SELECT *

            criteria.where(
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.SOURCE), sourceobjectid)
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
    
    /**
     * Retrieves a list of EnterpriseRelationship with the provided object ID as the 
     * target object.
     * 
     * @param targetobjectid
     * @return
     * @throws DBConnectionException 
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EnterpriseRelationship> getRelationshipsForTargetObject(long targetobjectid)
            throws DBConnectionException {
        try {
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
    
    /**
     * Retrieves a list of EnterpriseRelationship with the provided object ID as the 
     * target object.
     * 
     * @param <T>
     * @param targetobjectid
     * @param c
     * @return
     * @throws DBConnectionException 
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <R extends EnterpriseRelationship> List<R> getRelationshipsForTargetObject(long targetobjectid, Class<R> c)
            throws DBConnectionException {
        try {
            //We wanted to use em.find(), but that would require us to create a 
            //composite primary key class, which might complicate things a little.
            //So let's stick to typed queries.

            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<R> criteria = builder.createQuery(c);
            Root<R> sourceEntity = criteria.from(c); //FROM

            criteria.select(sourceEntity); // SELECT *

            criteria.where(
                    builder.equal(sourceEntity.get(EnterpriseRelationship_.TARGET), targetobjectid)
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
    
    /**
     * Retrieves a list of EnterpriseRelationship with the provided source and target object ID.
     * 
     * @param sourceobjectid
     * @param targetobjectid
     * @return
     * @throws DBConnectionException 
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EnterpriseRelationship> getRelationshipsForObject(long sourceobjectid, long targetobjectid)
            throws DBConnectionException {
        try {
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
    
    /**
     * Retrieves a list of EnterpriseObjects by OBJECT_ID.
     * 
     * @param <T>
     * @param objectid
     * @param c
     * @return
     * @throws DBConnectionException 
     */
    public <T extends EnterpriseObject> T getEnterpriseObjectById(long objectid, Class<T> c) 
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
    
    /**
     * Retrieves a list of EnterpriseObjects by OBJECT_NAME.
     * 
     * @param <T>
     * @param objectName
     * @param c
     * @return
     * @throws DBConnectionException 
     */
    public <T extends EnterpriseObject> List<T> getEnterpriseObjectsByName(String objectName,Class<T> c)
        throws DBConnectionException{
        try{
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(c);
            Root<T> sourceEntity = criteria.from(c); //FROM 

            criteria.select(sourceEntity); // SELECT *

            criteria.where(builder.equal(sourceEntity.get(EnterpriseObject_.OBJECT_NAME), objectName));

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
    
    /**
     * Retrieves a list of EnterpriseRelationship with the provided source and target object ID.
     * 
     * @param <R>
     * @param sourceobjectid
     * @param targetobjectid
     * @param r
     * @return
     * @throws DBConnectionException 
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <R extends EnterpriseRelationship> List<R> getRelationshipsForObject(long sourceobjectid, long targetobjectid, Class<R> r)
            throws DBConnectionException {
        try {
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
    
    /**
     * Retrieves all EnterpriseObjects of the provided class.
     * 
     * @param <T>
     * @param c
     * @return
     * @throws DBConnectionException 
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T extends EnterpriseObject> List<T> getAllEnterpriseObjects(Class<T> c) throws DBConnectionException {
        try {
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
    
    /**
     * Retrieves all EnterpriseData belonging to the provided object ID.
     * 
     * @param <T>
     * @param objectid
     * @param c
     * @return
     * @throws DBConnectionException 
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T extends EnterpriseData> List<T> getEnterpriseData(long objectid, Class<T> c)
            throws DBConnectionException {
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(c);
            Root<T> sourceEntity = criteria.from(c);

            criteria.select(sourceEntity);
            criteria.where(
                    builder.equal(sourceEntity.get(EnterpriseData_.OWNER), objectid)
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
    
    /**
     * Retrieves all EnterpriseData belonging to the provided object ID and within 
     * the provided validity. If a null is passed in for either one of the dates,
     * 01.01.1800 will be used for start date and 31.12.9999 will be used for the 
     * end date.
     * 
     * @param <T>
     * @param objectid
     * @param start
     * @param end
     * @param c
     * @return
     * @throws DBConnectionException 
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T extends EnterpriseData> List<T> getEnterpriseDataForObject(long objectid, java.sql.Date start, java.sql.Date end, Class<T> c)
            throws DBConnectionException {
        try {
            /**
             * If any of the dates are not provided, use the low or high date
             * respectively - If start is null, use 01.01.1800 as start - If end
             * is null, use 31.12.9999
             */

            if (start == null) {
                start = new java.sql.Date((new DateTime(1800, 1, 1, 0, 0, 0, 0)).getMillis());
            }
            if (end == null) {
                end = new java.sql.Date((new DateTime(9999, 12, 31, 0, 0, 0, 0)).getMillis());
            }

            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(c);
            Root<T> sourceEntity = criteria.from(c);

            criteria.select(sourceEntity);
            criteria.where(
                    builder.and(
                            builder.equal(sourceEntity.get(EnterpriseData_.OWNER), objectid),
                            builder.lessThanOrEqualTo(sourceEntity.<java.sql.Date>get(EnterpriseData_.START_DATE), end),
                            builder.greaterThanOrEqualTo(sourceEntity.<java.sql.Date>get(EnterpriseData_.END_DATE), start)
                    )
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
    public <R extends EnterpriseRelationship> long countRelationshipsForSource(long sourceobjectid, Class<R> r) {
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();

            CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
            Root<R> sourceEntity = criteria.from(r);

            criteria.select(builder.count(criteria.from(r)));
            criteria.where(builder.equal(sourceEntity.get(EnterpriseRelationship_.SOURCE), sourceobjectid));

            Long result = em.createQuery(criteria)
                    .getSingleResult();

            return result;
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <R extends EnterpriseRelationship> long countRelationshipsForTarget(long targetobjectid, Class<R> r) {
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();

            CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
            Root<R> sourceEntity = criteria.from(r);

            criteria.select(builder.count(criteria.from(r)));
            criteria.where(builder.equal(sourceEntity.get(EnterpriseRelationship_.TARGET), targetobjectid));

            Long result = em.createQuery(criteria)
                    .getSingleResult();

            return result;
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T extends EnterpriseObject, R extends EnterpriseRelationship>
            List<T> getAllTargetObjectsFromSource(long sourceid, Class<R> r, Class<T> t) {
        try {
            List<R> rList = this.getRelationshipsForSourceObject(sourceid, r);

            List<T> tList = new ArrayList();
            for (R rInstance : rList) {
                tList.add((T) rInstance.getTARGET());
            }

            return tList;

        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <S extends EnterpriseObject, R extends EnterpriseRelationship>
            List<S> getAllSourceObjectsFromTarget(long targetid, Class<R> r, Class<S> s) {
        try {
            List<R> rList = this.getRelationshipsForTargetObject(targetid, r);

            List<S> sList = new ArrayList();
            for (R rInstance : rList) {
                sList.add((S) rInstance.getSOURCE());
            }

            return sList;

        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }
            
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EnterpriseRelationship> getRelationshipsForSourceObjects(List<Long> sourceids){
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<EnterpriseRelationship> query = builder.createQuery(EnterpriseRelationship.class);
            Root<EnterpriseRelationship> sourceEntity = query.from(EnterpriseRelationship.class);
            
            query.where(sourceEntity.get(EnterpriseRelationship_.SOURCE).in(sourceids));
            
            List<EnterpriseRelationship> results = em.createQuery(query).getResultList();
            
            return results;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <R extends EnterpriseRelationship> List<R> getRelationshipsForSourceObjects(List<Long> sourceids, Class<R> r){
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<R> query = builder.createQuery(r);
            Root<R> sourceEntity = query.from(r);
            
            query.where(sourceEntity.get(EnterpriseRelationship_.SOURCE).in(sourceids));
            
            List<R> results = em.createQuery(query).getResultList();
            
            return results;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EnterpriseRelationship> getRelationshipsForTargetObjects(List<Long> targetids){
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<EnterpriseRelationship> query = builder.createQuery(EnterpriseRelationship.class);
            Root<EnterpriseRelationship> sourceEntity = query.from(EnterpriseRelationship.class);
            
            query.where(sourceEntity.get(EnterpriseRelationship_.TARGET).in(targetids));
            
            List<EnterpriseRelationship> results = em.createQuery(query).getResultList();
            
            return results;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <R extends EnterpriseRelationship> List<R> getRelationshipsForTargetObjects(List<Long> targetids, Class<R> r){
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<R> query = builder.createQuery(r);
            Root<R> sourceEntity = query.from(r);
            
            query.where(sourceEntity.get(EnterpriseRelationship_.TARGET).in(targetids));
            
            List<R> results = em.createQuery(query).getResultList();
            
            return results;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <R extends EnterpriseRelationship, T extends EnterpriseObject> List<T> getAllTargetObjectsFromSources(List<Long> sourceids, Class<R> r, Class<T> t){
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(t);
            Root<R> source = query.from(r);
            
            Join<R,T> joinToTarget = source.join("TARGET");
            
            query.where(source.get(EnterpriseRelationship_.SOURCE).in(sourceids));
            
            List<T> results = em.createQuery(query).getResultList();
            
            return results;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <R extends EnterpriseRelationship, S extends EnterpriseObject> List<S> getAllSourceObjectsFromTargets(List<Long> targetids, Class<R> r, Class<S> s){
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<S> query = builder.createQuery(s);
            Root<R> source = query.from(r);
            
            Join<R,S> joinToTarget = source.join("SOURCE");
            
            query.where(source.get(EnterpriseRelationship_.TARGET).in(targetids));
            
            List<S> results = em.createQuery(query).getResultList();
            
            return results;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw new EJBException(pex);
        }
    }
}