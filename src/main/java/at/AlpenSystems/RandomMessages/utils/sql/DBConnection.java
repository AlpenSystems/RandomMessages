/**
 * Class DBConnection
 * @author David-H-Dev
 *
 * last changes:
 * @date 15.08.2020
 */

package at.AlpenSystems.RandomMessages.utils.sql;

import at.AlpenSystems.RandomMessages.utils.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public final String DB_Name = Config.get("DB_NAME");
    public final String IP = Config.get("IP_ADDRESS");
    public final String PORT = Config.get("PORT");
    public final String DRIVER = Config.get("DRIVER");
    public final String CONN_ARGS = Config.get("CONN_ARGS");
    public final String USERNAME = Config.get("USERNAME");
    public final String PASSWORD = Config.get("PASSWORD");

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connnection;
        connnection = DriverManager.getConnection(DRIVER + "://"+ IP + ":" + PORT + "/" + DB_Name + CONN_ARGS, USERNAME, PASSWORD);
        return connnection;
    }
}