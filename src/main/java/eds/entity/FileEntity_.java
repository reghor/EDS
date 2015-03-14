/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eds.entity;

import java.sql.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author KH
 */
@StaticMetamodel(FileEntity.class)
public class FileEntity_{
    public static volatile SingularAttribute<FileEntity,Long> OBJECTID;
    public static volatile SingularAttribute<FileEntity,String> OBJECT_NAME;
    public static volatile SingularAttribute<FileEntity,java.sql.Date> START_DATE;
    public static volatile SingularAttribute<FileEntity,java.sql.Date> END_DATE;
    public static volatile SingularAttribute<FileEntity,String> SEARCH_TERM;
    
    
}
