package data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Mirko Eberlein
 *
 */
public class DBConnector {
	private static final String DBPATH = "jdbc:sqlite::resource:fwplan.db";
	protected Connection connect() throws ClassNotFoundException{
        Connection conn = null;
        try {
			Class.forName("org.sqlite.JDBC");
        	conn = DriverManager.getConnection(DBPATH);
        } catch (SQLException e) {
			System.out.println("DB error");
        }
        return conn;
    }
}
