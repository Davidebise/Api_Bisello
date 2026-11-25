import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API {
    private final String urlBase="https://pokeapi.co/api/v2/";
    private final HttpClient client;

    private static API instance;
    private API(){
        client = HttpClient.newHttpClient();
    }
    public static API getInstance(){
        if(instance == null){
            instance = new API();
        }
        return instance;
    }
    public Pokemon getPokemonByName(String name) throws IOException, InterruptedException {  //si usa request perchè devo fare una richiesta di dati
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlBase + "/pokemon/" + name)) //design pattern builder
                .build(); //crea la richiesta ma non la manda

        HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
        if(response.statusCode()!=200){
            throw new IOException("Errore: "+response.statusCode());
        }

        Gson gson = new Gson();

        Pokemon pokemon = gson.fromJson(response.body(),Pokemon.class);  //gli attributi devono avere nome uguale rispetto a quelli del json
        return pokemon;
    }
}
