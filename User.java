package healthe;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Date;
import javax.naming.NamingException;


/**
 *
 * @author team alpha
 */
public class User implements Serializable {
    private String name;
	 
    public String getName() {
        return name;
    }
	 
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Creates a new instance of User
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    public User() throws SQLException, NamingException{
        
    }
    
}
