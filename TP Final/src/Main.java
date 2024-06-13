import Truco.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import Menu.*;
import Controlador.*;

public class Main {
    static Scanner scan;
    public static void main(String[] args) {
        scan = new Scanner(System.in);

        ControladoraArchivos controladoraArchivos = new ControladoraArchivos();
        ControladoraJSON controladoraJSON = new ControladoraJSON();

        Mazo<CartaTruco> mazo = new Mazo<>();
        controladoraJSON.cargaArrayConJson(mazo);



        int seleccion = 0;
        int i = 0;

        Menu menues = new Menu();

        do
        {
            System.out.printf(menues.mostrarMenuInicial());
            System.out.printf("Ingrese una opcion: ");
            while (!scan.hasNextInt()) scan.next();
            seleccion = scan.nextInt();

            switch (seleccion)
            {
                case 1:
                    for(i=0; i<2; i++)
                    {
                        System.out.println("");
                    }
                    System.out.println("Ingrese el nombre del 1er jugador: ");
                    scan.nextLine();
                    String nombre = scan.nextLine();
                    Jugador j1 = new Jugador(nombre);

                    scan.nextLine();

                    System.out.println("Ingrese el nombre del 2do jugador: ");
                    String nombre2 = scan.nextLine();
                    Jugador j2 = new Jugador(nombre2);

                    MenuPartida menuPartida = new MenuPartida(j1, j2, mazo);
                    menuPartida.partida();
                    ArrayList<Jugador> jugadores = new ArrayList<>();
                    jugadores.add(j1);
                    jugadores.add(j2);

                    JSONArray jsonArray;
                    jsonArray =  controladoraJSON.cargaJson(jugadores);
                    controladoraArchivos.generaJSONRanking(jsonArray, "ranking");
                    System.out.println("Ganador: "+menuPartida.jugadorGanador().getNombre());
                    break;
                case 2:
                    for(i=0; i<10; i++)
                    {
                        System.out.println("");
                    }

                    int seleccionInstrucciones = 0;

                    do
                    {
                        MenuInstrucciones instrucciones = new MenuInstrucciones();

                        int opcion = 0;

                        System.out.println(menues.mostrarMenuInstrucciones());
                        System.out.printf("Ingrese una opcion: ");
                        seleccionInstrucciones = scan.nextInt();

                        switch (seleccionInstrucciones)
                        {
                            case 1:
                                for(i=0; i<10; i++)
                                {
                                    System.out.println("");
                                }

                                controladoraArchivos.leerArchivoReglas();

                                System.out.printf("\n\nPresione 1 para volver al menu anterior o presiones 2 cerrar el programa: ");
                                opcion = scan.nextInt();

                                if(opcion == 1)
                                {
                                    seleccionInstrucciones = 0;
                                }

                                break;
                            case 2:
                                for(i=0; i<10; i++)
                                {
                                    System.out.println("");
                                }


                                System.out.println(instrucciones.mostrarValores());

                                System.out.printf("Presione 1 para volver al menu anterior o presiones 2 para cerrear el programa: ");
                                opcion = scan.nextInt();

                                if(opcion == 1)
                                {
                                    seleccionInstrucciones = 0;
                                }
                                break;
                            case 3:
                                for(i=0; i<10; i++)
                                {
                                    System.out.println("");
                                }

                                seleccion = 0;

                                break;
                            default:
                                for(i=0; i<10; i++)
                                {
                                    System.out.println("");
                                }

                                System.out.println("Opcion ingresada invalida, vuelvalo a intentar");
                                break;
                        }
                    }while(seleccionInstrucciones < 1 || seleccionInstrucciones > 3);

                    break;
                default:
                    for(i=0; i<10; i++)
                    {
                        System.out.println("");
                    }

                    System.out.println("Opcion ingresada invalida, vuelvalo a intentar");
                    break;
            }
        }while(seleccion < 1 || seleccion > 2);

    }
}
