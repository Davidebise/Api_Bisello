import java.util.List;

public record Pokemon(int id, String name, List<Types> types) {} //il record è immutabile
//pokemon al massimo 2 tipi, secondo tipo nullable