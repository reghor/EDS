/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eds.component.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 *
 * @author KH
 */
@Stateless
public class HibernateEMServices implements Serializable {

    @PersistenceContext(name = "HIBERNATE")
    private EntityManager em;

    //private EntityExplorer entityExplorer;
    public EntityManager getEM() {
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("HIBERNATE");//, this.createFullConfig().getProperties());
        //EntityManager em = emf.createEntityManager();

        return em;
    }

    /**
     * To be deprecated.
     *
     * @return
     */
    public Session getSession() throws DBConnectionException {
        Session session;
        try {
            Configuration config = this.createFullConfig();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
            SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
            session = sessionFactory.openSession();
        } catch (JDBCConnectionException tdbccex) {
            throw new DBConnectionException("Unable to connect to DB!");
        }

        return session;
    }

    public Configuration createFullConfig() {
        Configuration cfg = createPartialConfig();
        /*cfg.setProperty("hibernate.current_session_context_class", "thread");
         cfg.setProperty("hibernate.c3p0.min_size","5");
         cfg.setProperty("hibernate.c3p0.max_size","20");
         cfg.setProperty("hibernate.c3p0.timeout","30");
         cfg.setProperty("hibernate.c3p0.max_statements","100");
         cfg.setProperty("hibernate.hbm2ddl.auto","update");
         cfg.setProperty("hibernate.archive.autodetection","class, hbm");
         cfg.setProperty("hibernate.show_sql","true");
         cfg.setProperty("hibernate.connection.autocommit","false");*/

        cfg.configure();

        return cfg;
    }

    public Configuration createPartialConfig() {
        Configuration cfg = new Configuration();
        return cfg;
    }

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

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void regenerateDBTables() throws DBConnectionException {

        try {
            Metamodel metamodel = em.getMetamodel();
            List<Class> allEntities = new ArrayList<Class>();
            for (final ManagedType<?> managedType : metamodel.getManagedTypes()) {
                allEntities.add(managedType.getJavaType()); // this returns the java class of the @Entity object
            }

            Configuration cfg = this.createFullConfig();
            for (Class c : allEntities) {
                cfg.addAnnotatedClass(c);
            }

            //Delete all tables first
            new SchemaExport(cfg).drop(true, true);
            //.setProperty("hibernate.hbm2ddl.auto", "create")) //it is currently update
            //.execute(true, true, true, false);
            new SchemaExport(cfg)
                    .execute(true, true, true, true);

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
