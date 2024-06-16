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
import java.util.Comparator;
import java.util.List;

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

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonContent.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        // Procesar cada jugador en la lista
        try {
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
                    } else {
                        newJugador.put("Partidas Ganadas", 0);
                        newJugador.put("Partidas Perdidas", 1);
                    }
                    jsonArray.put(newJugador);
                }
            }
        } catch (JSONException e){
            throw new RuntimeException(e);
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

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonContent.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Inicializamos la variable que vamos a devolver
        String estadisticas = "";

        // Procesar cada jugador en la lista
        try {
            for (Jugador jugador : jugadores) {
                boolean encontrado = false;
                if (jsonArray.length() > 1) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String jsonNombre = jsonObject.getString("Nombre");

                        if (jugador.getNombre().equalsIgnoreCase(jsonNombre)) {
                            encontrado = true;
                            estadisticas += "Nombre: " + jsonNombre + "\n";
                            estadisticas += "Partidas Jugadas: " + jsonObject.getInt("Partidas Jugadas") + "\n";
                            estadisticas += "Partidas Ganadas: " + jsonObject.getInt("Partidas Ganadas") + "\n";
                            estadisticas += "Partidas Perdidas: " + jsonObject.getInt("Partidas Perdidas") + "\n\n";
                            break;
                        }
                    }

                }
                if (!encontrado) {
                    estadisticas += "Nombre: " + jugador.getNombre() + "\n";
                    estadisticas += "Partidas Jugadas: 0\n";
                    estadisticas += "Partidas Ganadas: 0\n";
                    estadisticas += "Partidas Perdidas: 0\n\n";
                }
            }
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
        return estadisticas;
    }

    public String devuelveRanking(String archivo) {
        StringBuilder jsonContent = new StringBuilder();

        // Leer el archivo JSON en una cadena de texto
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo + ".json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage(), e);
        }

        // Convertir la cadena de texto en un JSONArray
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonContent.toString());
        } catch (JSONException e) {
            throw new RuntimeException("Error al convertir el contenido a JSONArray: " + e.getMessage(), e);
        }

        // Convertir JSONArray a una lista de JSONObject
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObjects.add(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                throw new RuntimeException("Error al obtener JSONObject del JSONArray: " + e.getMessage(), e);
            }
        }

        // Ordenar la lista de JSONObjects por "Partidas Ganadas" en orden descendente
        jsonObjects.sort(new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject a, JSONObject b) {
                try {
                    int partidasGanadasA = a.getInt("Partidas Ganadas");
                    int partidasGanadasB = b.getInt("Partidas Ganadas");
                    return Integer.compare(partidasGanadasB, partidasGanadasA); // Orden descendente
                } catch (JSONException e) {
                    throw new RuntimeException("Error al obtener 'Partidas Ganadas': " + e.getMessage(), e);
                }
            }
        });

        // Inicializamos la variable que vamos a devolver
        StringBuilder estadisticas = new StringBuilder();

        // Procesar cada jugador en la lista ordenada
        for (JSONObject jsonObject : jsonObjects) {
            try {
                String jsonNombre = jsonObject.getString("Nombre");
                estadisticas.append("Nombre: ").append(jsonNombre).append("\n");
                estadisticas.append("Partidas Jugadas: ").append(jsonObject.getInt("Partidas Jugadas")).append("\n");
                estadisticas.append("Partidas Ganadas: ").append(jsonObject.getInt("Partidas Ganadas")).append("\n");
                estadisticas.append("Partidas Perdidas: ").append(jsonObject.getInt("Partidas Perdidas")).append("\n\n");
            } catch (JSONException e) {
                throw new RuntimeException("Error al obtener datos del JSONObject: " + e.getMessage(), e);
            }
        }

        return estadisticas.toString();
    }
}



