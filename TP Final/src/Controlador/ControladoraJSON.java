package Controlador;

import Truco.CartaTruco;
import Truco.Jugador;
import Truco.Mazo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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


    public JSONArray cargaJsonArray(ArrayList<Jugador> jugadores, String archivo) {

        StringBuilder jsonContent = new StringBuilder();

        // Leer el archivo JSON en una cadena de texto
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo + ".json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Convertir la cadena de texto en un JSONArray

        JSONArray jsonArray = new JSONArray(jsonContent.toString());


        // Procesar cada jugador en la lista
        for (Jugador jugador : jugadores) {
            boolean encontrado = false;
        if (jsonArray.length() > 1) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String jsonNombre = jsonObject.getString("Nombre");

                    if (jugador.getNombre().equalsIgnoreCase(jsonNombre)) {
                        encontrado = true;
                        jsonObject.put("Partidas Jugadas", jsonObject.getInt("Partidas Jugadas") + 1);
                        if (jugador.partidaGanada()) {
                            jsonObject.put("Partidas Ganadas", jsonObject.getInt("Partidas Ganadas") + 1);
                        } else {
                            jsonObject.put("Partidas Perdidas", jsonObject.getInt("Partidas Perdidas") + 1);
                        }
                        break;
                    }
                }

        }
            if (!encontrado) {
                JSONObject newJugador = new JSONObject();
                newJugador.put("Nombre", jugador.getNombre().toLowerCase());
                newJugador.put("Partidas Jugadas", 1);
                if (jugador.partidaGanada()) {
                    newJugador.put("Partidas Ganadas", 1);
                    newJugador.put("Partidas Perdidas", 0);
                }else {
                    newJugador.put("Partidas Ganadas", 0);
                    newJugador.put("Partidas Perdidas", 1);
                }
                jsonArray.put(newJugador);
            }
        }

        return jsonArray;
    }

    public String estadisticasIniciales(ArrayList<Jugador> jugadores, String archivo){

        StringBuilder jsonContent = new StringBuilder();

        // Leer el archivo JSON en una cadena de texto
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo + ".json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Convertir la cadena de texto en un JSONArray

        JSONArray jsonArray = new JSONArray(jsonContent.toString());

        // Inicializamos la variable que vamos a devolver
        String estadisticas = "";

        // Procesar cada jugador en la lista
        for (Jugador jugador : jugadores) {
            boolean encontrado = false;
            if (jsonArray.length() > 1) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String jsonNombre = jsonObject.getString("Nombre");

                    if (jugador.getNombre().equalsIgnoreCase(jsonNombre)) {
                        encontrado = true;
                        estadisticas+="Nombre: "+jsonNombre+"\n";
                        estadisticas+="Partidas Jugadas: "+jsonObject.getInt("Partidas Jugadas")+"\n";
                        estadisticas+="Partidas Ganadas: "+jsonObject.getInt("Partidas Ganadas")+"\n";
                        estadisticas+="Partidas Perdidas: "+jsonObject.getInt("Partidas Perdidas")+"\n\n";
                        break;
                    }
                }

            }
            if (!encontrado) {
                estadisticas+="Nombre: "+jugador.getNombre()+"\n";
                estadisticas+="Partidas Jugadas: 0\n";
                estadisticas+="Partidas Ganadas: 0\n";
                estadisticas+="Partidas Perdidas: 0\n\n";
            }
        }

        return estadisticas;
    }

}



