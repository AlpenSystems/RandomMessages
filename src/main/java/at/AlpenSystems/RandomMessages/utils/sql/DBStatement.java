/**
 * Class DBStatement
 * @author David-H-Dev
 *
 * last changes:
 * @date 19.08.2020
 */

package at.AlpenSystems.RandomMessages.utils.sql;

import de.vandermeer.asciitable.AsciiTable;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBStatement {
    private final DBConnection dbConnection = new DBConnection();

    private Connection connection = null;
    private Statement statement = null;

    public void checkDBConnection() {
        try {
            this.connection = this.dbConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(this.connection != null) {
            System.out.println("Connection to database established!");
        } else {
            System.out.println("Failed to connect to database!");
        }
    }

    public void createTable(long serverid) throws SQLException {
        String TABLE_SERVER_NAME = "s" + serverid;
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_SERVER_NAME + "( " + "ID" + " INTEGER PRIMARY KEY AUTO_INCREMENT, " + "message" + " TEXT NOT NULL," + "date" + " DATETIME NOT NULL  )";
        try {
            this.connection = this.dbConnection.getConnection();
            this.statement = this.connection.createStatement();
            if (this.statement != null)
                this.statement.execute(createTable);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert this.statement != null;
            this.statement.close();
            this.connection.close();
        }
    }

    public void createConfigTable(long serverid) throws SQLException {
        String TABLE_SERVER_NAME = "c" + serverid;
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_SERVER_NAME + "( " + "ID" + " INTEGER PRIMARY KEY, " + "channel" + " BIGINT  )";
        String insertData = "INSERT INTO " + TABLE_SERVER_NAME + " VALUES (1, NULL);";
        try {
            this.connection = this.dbConnection.getConnection();
            this.statement = this.connection.createStatement();
            if (this.statement != null) {
                this.statement.execute(createTable);
                if (!entryExists(serverid))
                    this.statement.execute(insertData);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert this.statement != null;
            this.statement.close();
            this.connection.close();
        }
    }

    public void insertMessage(long serverid, String msg) throws SQLException {
        String TABLE_SERVER_NAME = "s" + serverid;
        System.out.println();
        String insertData = "INSERT INTO " + TABLE_SERVER_NAME + " VALUES (NULL, '" + msg + "', CURRENT_TIMESTAMP )";
        try {
            this.connection = this.dbConnection.getConnection();
            this.statement = this.connection.createStatement();
            if (this.statement != null)
                this.statement.execute(insertData);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert this.statement != null;
            this.statement.close();
            this.connection.close();
        }
    }

    public void deleteMessage(long serverid, int msgID) throws SQLException {
        String TABLE_SERVER_NAME = "s" + serverid;
        System.out.println();
        String deleteData = "DELETE FROM `" + TABLE_SERVER_NAME + "` WHERE `ID`=" + msgID + ";";
        try {
            this.connection = this.dbConnection.getConnection();
            this.statement = this.connection.createStatement();
            if (this.statement != null)
                this.statement.execute(deleteData);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert this.statement != null;
            this.statement.close();
            this.connection.close();
        }
    }

    public int getID(long serverid) throws SQLException, ClassNotFoundException {
        this.connection = this.dbConnection.getConnection();
        Statement stmt = this.connection.createStatement();
        int randomElement = 0;
        ResultSet rs = stmt.executeQuery("SELECT ID FROM `s" + serverid + "`;");
        List<Integer> myList = new ArrayList<>();
        while (rs.next()) {
            int i = 1;
            myList.add(rs.getInt(i));
            Random rand = new Random();
            randomElement = myList.get(rand.nextInt(myList.size()));
        }
        return randomElement;
    }

    public String getMessage(long serverid) throws SQLException {
        String msg = "";
        try {
            this.connection = this.dbConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                String TABLE_SERVER_NAME = "s" + serverid;
                String sql = "SELECT message  from `" + TABLE_SERVER_NAME + "` WHERE ID=" + getID(serverid) + ";";
                ps = this.connection.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next())
                    msg = rs.getString(1);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert rs != null;
                    rs.close();
                    ps.close();
                    this.connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            this.connection.close();
        }
        return msg;
    }

    public Boolean entryExists(long serverid) throws SQLException {
        boolean entryexists = false;
        try {
            this.connection = this.dbConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                String TABLE_SERVER_NAME = "c" + serverid;
                String sql = "SELECT ID  FROM `" + TABLE_SERVER_NAME + "` WHERE ID=1;";
                ps = this.connection.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next())
                    entryexists = true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert rs != null;
                    rs.close();
                    ps.close();
                    this.connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            this.connection.close();
        }
        return entryexists;
    }

    public void setActionChannel(long serverid, long setchannelid) throws SQLException {
        String TABLE_SERVER_NAME = "c" + serverid;
        System.out.println();
        String insertData = "UPDATE `" + TABLE_SERVER_NAME + "` SET channel=" + setchannelid + " WHERE ID=1;";
        try {
            this.connection = this.dbConnection.getConnection();
            this.statement = this.connection.createStatement();
            if (this.statement != null)
                this.statement.execute(insertData);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert this.statement != null;
            this.statement.close();
            this.connection.close();
        }
    }

    public long getActionChannel(long serverid) throws SQLException, ClassNotFoundException {
        long actionchannelid = 0L;
        this.connection = this.dbConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String TABLE_SERVER_NAME = "c" + serverid;
            String sql = "SELECT channel  from `" + TABLE_SERVER_NAME + "` WHERE ID=1;";
            ps = this.connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next())
                actionchannelid = rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert rs != null;
                rs.close();
                ps.close();
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return actionchannelid;
    }

    public void getTable(long serverid, MessageChannel msgChannel) {
        List<String> messages = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        try (Connection conn = this.dbConnection.getConnection()) {
            String TABLE_SERVER_NAME = "s" + serverid;
            String sql = "SELECT ID, message FROM `" + TABLE_SERVER_NAME + "`;";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                AsciiTable at = new AsciiTable();
                at.addRule();
                at.addRow("ID", "Messages");
                while (rs.next()) {
                    int i = 0;
                    messages.add(i, rs.getString("message"));
                    numbers.add(i, rs.getInt("ID"));
                    at.addRule();
                    at.addRow(numbers.get(i),  messages.get(i));
                }
                at.addRule();
                String rend = at.render();
                msgChannel.sendMessage("```" + rend + "```").queue();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}