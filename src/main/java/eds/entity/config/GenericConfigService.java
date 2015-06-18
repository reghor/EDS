/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.entity.config;

import eds.component.data.DBConnectionException;
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
import org.joda.time.DateTime;

/**
 *
 * @author LeeKiatHaw
 */
@Stateless
public class GenericConfigService {
    
    @PersistenceContext(name = "HIBERNATE")
    private EntityManager em;
    
    
    /**
     * Creates an entirely new config entry. If the config passed in already has 
     * an Id, then an EntityExistsException would be thrown.
     * 
     * @param ec
     * @return 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EnterpriseConfiguration createConfig(EnterpriseConfiguration ec){
        try {
            em.persist(ec);
            
            return ec;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw pex;
        } 
        
    }
    
    /**
     * Updates if exists, creates if it does not exist
     * 
     * @param ec
     * @return 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EnterpriseConfiguration updateConfig(EnterpriseConfiguration ec){
        try {
            return em.merge(ec);
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw pex;
        } 
    }
    
    /**
     * Retrieves a config by ID.
     * 
     * @param configId
     * @return 
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EnterpriseConfiguration getConfig(long configId){
        try {
            return em.find(EnterpriseConfiguration.class, configId);
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw pex;
        } 
    }
    
    /**
     * Returns any observable records within the start and end period. If no date is
     * supplied for start or end (or both), then the earliest possible date and 
     * latest possible dates are used respectively (01.01.1800 and 31.12.9999)
     * 
     * @param <C>
     * @param c
     * @param start
     * @param end
     * @return 
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <C extends EnterpriseConfiguration> List<C> getConfigByPeriod(
            Class<C> c, java.sql.Date start, java.sql.Date end){
        try {
            if (start == null) {
                start = new java.sql.Date((new DateTime(1800, 1, 1, 0, 0, 0, 0)).getMillis());
            }
            if (end == null) {
                end = new java.sql.Date((new DateTime(9999, 12, 31, 0, 0, 0, 0)).getMillis());
            }
            
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<C> criteria = builder.createQuery(c);
            Root<C> sourceEntity = criteria.from(c);
            
            criteria.select(sourceEntity);
            
            criteria.where(
                builder.and(
                    builder.lessThanOrEqualTo(sourceEntity.<java.sql.Date>get(EnterpriseConfiguration_.CONFIG_EFF_DATE), end),
                    builder.greaterThanOrEqualTo(sourceEntity.<java.sql.Date>get(EnterpriseConfiguration_.CONFIG_END_DATE), start)
                ));
            
            List<C> results = em.createQuery(criteria)
                    .getResultList();
            
            return results;
            
        } catch (PersistenceException pex) {
            if (pex.getCause() instanceof GenericJDBCException) {
                throw new DBConnectionException(pex.getCause().getMessage());
            }
            throw pex;
        } 
    } 
}
