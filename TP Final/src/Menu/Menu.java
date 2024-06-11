package Menu;

import org.json.JSONArray;
import org.json.JSONObject;

public class Menu {
JSONArray jsonArray;
    public String mostrarMenuInicial()
    {
        return  "҉ ------------------------------------------------------------- ҉\n" +
                "|                                                               |\n" +
                "|                         TRUCAAAAAZOOOO                        |\n" +
                "|                                                               |\n" +
                "|                           1- JUGAR                            |\n" +
                "|                        2- INSTRUCCIONES                       |\n" +
                "|                                                               |\n" +
                "҉ ------------------------------------------------------------- ҉\n";
    }

    public String mostrarMenuInstrucciones()
    {
        return  "҉ ------------------------------------------------------------- ҉\n" +
                "|                                                               |\n" +
                "|                       MENU INSTRUCCIONES                      |\n" +
                "|                                                               |\n" +
                "|                          1- JUGADAS                           |\n" +
                "|                    2- VALORES DE LAS CARTAS                   |\n" +
                "|                          3- VOLVER                            |\n" +
                "|                                                               |\n" +
                "҉ ------------------------------------------------------------- ҉\n";
    }
}