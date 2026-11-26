import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    public void InsertPokemon(Pokemon pokemon) throws SQLException {
        if(!TestConnection()){
            throw new SQLException();
        }
        String query = "INSERT INTO pokemon (IdPokemon, Nome, Tipo1, Tipo2) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement(query);
            stmt.setInt(1,pokemon.id());
            stmt.setString(2,pokemon.name());
            stmt.setString(3,pokemon.types().getFirst().toString());
            stmt.setString(4,pokemon.types().getLast().toString());
            stmt.execute();
        }catch(SQLException e){
            throw new SQLException("Errore nell'inserimento");
        }
    }

    public Pokemon getPokemon(int id) throws SQLException {
        if(!TestConnection()){
            throw new SQLException();
        }
        Pokemon pokemon;

        return pokemon;
    }
    private boolean TestConnection() throws SQLException {
        try{
            return connection.isValid(5);
        }catch(SQLException e){
            return false;
        }
    }
}
