import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection connection;
    private static Database instance;

    private Database() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:database.db");
    }

    public static Database getInstance() throws SQLException {
        if(instance == null){
            instance=new Database();
        }
        return instance;
    }

    private boolean TestConnection() throws SQLException {
        try{
            return connection.isValid(5);
        }catch(SQLException e){
            return false;
        }
    }
}
