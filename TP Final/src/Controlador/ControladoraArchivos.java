package Controlador;

import Truco.CartaTruco;
import Truco.Mazo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class ControladoraArchivos {

    public void leerArchivoReglas(){
        try (BufferedReader br = new BufferedReader(new FileReader("TP Final/src/Truco/ReglasTruco.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }catch (FileNotFoundException ex){
            System.out.println("Archivo no encontrado: "+ex.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generaJSONRanking(JSONArray jsonArray, String archivo){
        try{
            FileWriter file = new FileWriter("TP Final/src/Truco/"+archivo+".json");
            file.write(jsonArray.toString());
            file.flush();
            file.close();
        }catch (FileNotFoundException ex){
            System.out.println("Archivo no encontrado: "+ex.getMessage());
        }catch (IOException ex){
            System.out.println("Error al generar el archivo: "+ex.getMessage());
        }

    }
}
