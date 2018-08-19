package data.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Mirko Eberlein
 *
 */
public class DBConnector {
	private static final String DBPATH = "jdbc:sqlite:";
	private static final String FBNAME = System.getProperty("user.dir") + File.separator + "userdata" + File.separator + "db" + File.separator + "fwplan.db";
	protected Connection connect() throws ClassNotFoundException{
        Connection conn = null;
        try {
        	File temp = new File(FBNAME);
        	String connection = DBPATH + temp.getAbsolutePath();
			Class.forName("org.sqlite.JDBC");
        	conn = DriverManager.getConnection(connection);
        } catch (SQLException e) {
			System.out.println("DB error");
			e.printStackTrace();
        }
        return conn;
    }
}
