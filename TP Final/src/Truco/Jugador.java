package Truco;

import java.util.ArrayList;

public class Jugador {
    //atri
    private String nombre; //si se hace mas enfoque en los jugadores, esto se puede cambiar
    private int puntos;
    private ArrayList<CartaTruco> manoActual;
    private int tantoEnvidoAct;
    private int rondaAct; //para llevar cuenta de cuantas rondas se gano esta mano
    private ArrayList<CartaTruco> cartasJugadas;

    //constru
    public Jugador (String nombre){
        this.nombre = nombre;
        puntos = 0;
        tantoEnvidoAct = 0;
        rondaAct = 0;
        manoActual = new ArrayList<CartaTruco>();
        cartasJugadas = new ArrayList<CartaTruco>();
    }

    //get n set
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getTantoEnvidoAct() {
        return tantoEnvidoAct;
    }

    public int getRondaAct() {
        return rondaAct;
    }

    //metodos
    public CartaTruco jugarCartaJug(int carta){  //int 1, 2, 3 (si mano completa)
        CartaTruco aux = null;
        if (carta <= manoActual.size() && carta > 0){
            aux = manoActual.get(carta-1); //guarda la carta a retornar desde el arreglo al aux
            cartasJugadas.addLast(aux);
            manoActual.remove(carta-1); //elimina la carta en mano
        }
        return aux;
    }

    public String mostrarMano(){  //se puede hacer un formato mejor
        return manoActual.toString();
    }

    public String mostrarCartasJugadas(){return cartasJugadas.toString();}

    public void calcularEnvido(){
        int tanto = 0;
        int aux = 0;
        for (int i = 0; i < manoActual.size(); i++){ //chequeo de carta mayor, independiente del palo
            if (manoActual.get(i).valorCarta() > tanto){
                tanto = manoActual.get(i).valorCarta();
            }
        }
        //chequeo palo, buscando similitudes en la primera y segunda carta, segunda y tercera y primera y tercera
        if (manoActual.get(0).getPalo() == manoActual.get(1).getPalo()){
            aux = 20 + manoActual.get(0).valorCarta() + manoActual.get(1).valorCarta();
            if (aux > tanto){
                tanto = aux;
            }
        }
        if (manoActual.get(1).getPalo() == manoActual.get(2).getPalo()){
            aux = 20 + manoActual.get(1).valorCarta() + manoActual.get(2).valorCarta();
            if (aux > tanto){
                tanto = aux;
            }
        }
        if (manoActual.get(0).getPalo() == manoActual.get(2).getPalo()){
            aux = 20 + manoActual.get(0).valorCarta() + manoActual.get(2).valorCarta();
            if (aux > tanto){
                tanto = aux;
            }
        }
        //se juega sin flor
        tantoEnvidoAct = tanto;
    }

    public void nuevaMano(){ //limpia los datos del jugador
        this.tantoEnvidoAct = 0;
        this.rondaAct = 0;
        manoActual.clear();
        cartasJugadas.clear();
    }

    public void darCarta(CartaTruco carta){
        manoActual.add(carta);
    }

    public void darPuntos(int puntos){
        this.puntos += puntos;
    }

    public CartaTruco ultimaCartaJugada(){
        return cartasJugadas.getLast();
    }

    public CartaTruco primeraCartaJugada(){
        return cartasJugadas.getFirst();
    }

    public void rondaGanada(){
        rondaAct = rondaAct + 1;
    }
    public boolean manoGanada(){ //corta la mano actual si se gana antes de jugar todas las cartas
        boolean aux = false;
        if (rondaAct == 2){
            aux = true;
        }
        return aux;
    }

    public boolean partidaGanada(){ //corta la partida y da la victoria
        boolean aux = false;
        if (puntos > 29){
            aux = true;
        }
        return aux;
    }
}
