/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateIssue;

import java.util.Properties;
import org.hibernate.MappingException;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.MultipleHiLoPerTableGenerator;
import org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory;
import org.hibernate.type.Type;

/**
 *
 * @author LeeKiatHaw
 */
public class IdGeneratorOverride extends DefaultIdentifierGeneratorFactory {

    @Override
    public IdentifierGenerator createIdentifierGenerator(String strategy, Type type, Properties config) {
        try {
			Class clazz = getIdentifierGeneratorClass( strategy );
                        //My testing for fix #7
                        MultipleHiLoPerTableGenerator gen = (MultipleHiLoPerTableGenerator) clazz.newInstance();
			IdentifierGenerator identifierGenerator = ( IdentifierGenerator ) clazz.newInstance();
			if ( identifierGenerator instanceof Configurable ) {
				( ( Configurable ) identifierGenerator ).configure( type, config, super.getDialect() );
			}
			return identifierGenerator;
		}
		catch ( Exception e ) {
			final String entityName = config.getProperty( IdentifierGenerator.ENTITY_NAME );
			throw new MappingException( String.format( "Could not instantiate id generator [entity-name=%s]", entityName ), e );
		} //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
