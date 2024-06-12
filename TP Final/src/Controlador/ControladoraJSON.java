package Controlador;

import Truco.CartaTruco;
import Truco.Jugador;
import Truco.Mazo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ControladoraJSON {

    public void cargaArrayConJson(Mazo mazo){

        // Leer el archivo JSON
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("TP Final/src/mazo.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (FileNotFoundException ex){
            System.out.println("Archivo no encontrado: "+ex.getMessage());
        }
        catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }

        // Procesar el contenido JSON
        try {
            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            JSONArray cartasArray = jsonObject.getJSONArray("cartas");

            for (int i = 0; i < cartasArray.length(); i++) {
                JSONObject cartaObject = cartasArray.getJSONObject(i);
                int numero = cartaObject.getInt("numero");
                String palo = cartaObject.getString("palo");
                int valorTruco = cartaObject.getInt("valorTruco");

                CartaTruco cartaTruco = new CartaTruco(numero, palo, valorTruco);
                //cartaTrucoArrayList.add(cartaTruco);
                mazo.agregar(cartaTruco);
            }

        } catch (JSONException e) {
            System.out.println("JSON mal procesado o fuente incorrecta: " + e.getMessage());
        }
    }

    public JSONArray cargaJson(ArrayList<Jugador> jugadores){
        JSONArray jsonArray = new JSONArray();

        try {
            for (Jugador jugador : jugadores) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nombre", jugador.getNombre());
                jsonObject.put("puntos", jugador.getPuntos());
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println("Error al generar el JSONArray: " + e.getMessage());
            e.printStackTrace();
        }

        return jsonArray;

    }



}
