import java.sql.*;
import javax.swing.*;

public class SqliteConnection {

	Connection dbConnection = null;

	public static Connection sqliteConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection dbConnection = DriverManager.getConnection("jdbc:sqlite:C:\\AbsenTracker\\AbsenTracker.sqlite");
			// To test connection uncomment the next line
			// JOptionPane.showMessageDialog(null,"Connection Successful");
			return dbConnection;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}

}
