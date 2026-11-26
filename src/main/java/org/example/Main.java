import static java.lang.IO.println;
import static java.lang.IO.readln;

void main(){
    API api = API.getInstance();
    Database database=null;
    try {
        database = Database.getInstance();
    }catch(Exception e){
        println("Database Error:"+e.getMessage());
    }
    Pokemon p=null;
    do{
        String p_id=readln("Inserisci l'id di un pokemon:");
        try{
            p=database.getPokemon(Integer.parseInt(p_id));
        }catch(Exception e){
            println("Errore:"+e.getMessage());
        }
        if(p==null){
            try{
                p=api.getPokemonById(Integer.parseInt(p_id));
                println(p);
                database.InsertPokemon(p);
                println("Nuovo pokemon aggiunto al database");
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }else{
            println("Pokemon già presente nel database");
            println(p);
        }
    }while(true);

}