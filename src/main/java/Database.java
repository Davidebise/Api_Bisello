import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            stmt.setString(3,pokemon.types().getFirst().type().name());
            stmt.setString(4,pokemon.types().getLast().type().name());
            stmt.execute();
        }catch(SQLException e){
            throw new SQLException("Errore nell'inserimento");
        }
    }

    public Pokemon getPokemon(int id) throws SQLException {
        if(!TestConnection()){
            throw new SQLException();
        }
        String query =  "SELECT * FROM pokemon WHERE IdPokemon = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet set=stmt.executeQuery();
            if(set.next()){
                int idP=set.getInt("IdPokemon");
                String name=set.getString("Nome");
                Type type1=new Type(set.getString("Tipo1"));
                Type type2=new Type(set.getString("Tipo2"));

                Types types1=new Types(type1);
                Types types2=new Types(type2);
                List<Types> typesList=new ArrayList<Types>();
                typesList.add(types1);
                typesList.add(types2);

                return new Pokemon(idP,name,typesList);
            }else{
                return null;
            }
        }catch(SQLException e){
            throw new SQLException("Errore nella richiesta");
        }
    }
    private boolean TestConnection() throws SQLException {
        try{
            return connection.isValid(5);
        }catch(SQLException e){
            return false;
        }
    }
}
