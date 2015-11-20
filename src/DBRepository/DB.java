/* 
  Julio Sevilla
  CLASS 
  ASSIGNMENT NAME
  DATE
  Developed on NetBeans IDE 8.0 (Build 201403101706)
  Java: 1.8.0_05
*/
package DBRepository;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


public class DB {
    private String dbName;
    private Connection db;
    
    public DB (String dbName) throws SQLException, ClassNotFoundException {
        this.dbName = dbName;
        Class.forName("org.sqlite.JDBC");
        this.db = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
    }
    
    public void createTable(String tableName, JSONArray fields) throws SQLException {
        try {
            Statement statement = db.createStatement();
            String table = "CREATE TABLE IF NOT EXISTS " + tableName + " ";
            for(int counter = 0; counter < fields.length(); counter++) {
                if (counter == 0)
                    table += "(";
                JSONObject field = fields.getJSONObject(counter);
                if (field.getString("type").equals("int") || field.getString("type").equals("boolean"))
                    table += field.getString("name") + " INTEGER";
                else if (field.getString("type").equals("decimal"))
                    table += field.getString("name") + " REAL";
                else 
                    table += field.getString("name") + " TEXT";
                if (counter >= 0 && counter < fields.length() - 1)
                    table += ",\n";
                if (counter == fields.length() - 1)
                    table += ")";
            }
            statement.executeUpdate(table);
        } catch (SQLException e) {
        }
    }
    
    public ResultSet queryDatabase(String sql) throws SQLException {
        ResultSet tempHolder = null;
        Statement statement = db.createStatement();
        tempHolder = statement.executeQuery(sql);
        return tempHolder;
    }
    
    public void dropTable(String tableName) throws SQLException {
        try {
            Statement statement = db.createStatement();
            String sql = "DROP TABLE IF EXISTS" + tableName;
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            
        }
    }
}
