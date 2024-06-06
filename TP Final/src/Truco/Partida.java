package Truco;

public class Partida implements ITruco{
    //atri
    private Jugador jugador1;
    private Jugador jugador2;
    private Mazo<CartaTruco> mazo;
    private Jugador jugadorMano; //jugador que tira la primera carta
    private int rondaactual; //cantidad de cartas jugadas esta mano
    private Jugador jugadorAct; //el jugador que tiene que jugar carta ahora
    private Jugador jugadorRiv; //contrario al jugador actual
    private int trucoAcumulado;
    private boolean parda; //empate primera ronda

    //constru
    public Partida (Jugador juan, Jugador jorge, Mazo cartas){
        jugador1 = juan;
        jugador2 = jorge;
        mazo = cartas;
        jugadorMano = jugador1;
        rondaactual = 0;
        jugadorAct = jugador1;
        jugadorRiv = jugador2;
        trucoAcumulado = 1;
        parda = false;
    }

    //get n set
    public Jugador getJugadorAct() {
        return jugadorAct;
    }

    public int getRondaactual() {
        return rondaactual;
    }

    public Jugador getJugadorMano() {
        return jugadorMano;
    }

    public int getTrucoAcumulado() {
        return trucoAcumulado;
    }

    //metodos
    public void empezarMano(){
        //inicializa los datos temporales de cada mano
        trucoAcumulado = 1;
        rondaactual = 0;
        parda = false;
        mazo.mezclar();
        jugador1.nuevaMano();
        mazo.alFondo();
        //razon por la que no uso el metodo robar: robar elimina la carta del mazo y en el truco jamás se va a usar el mazo completo, de esta forma se evitan errores
        for(int i = 0; i < 3; i++){
            jugador1.darCarta(mazo.adivinar());
            mazo.alFondo();
        }
        jugador1.calcularEnvido();
        jugador2.nuevaMano();
        for(int i = 0; i < 3; i++){
            jugador2.darCarta(mazo.adivinar());
            mazo.alFondo();
        }
        jugador2.calcularEnvido();
    }

    public Carta jugarCarta(int carta){ //metodo principal
        CartaTruco carAux = jugadorAct.jugarCartaJug(carta);
        if (carAux != null){
            rondaactual++; //aumenta el numero de cartas jugadas esta mano, para utilizarse de timer y llevar el orden de turnos
            int aux;
            switch (rondaactual){
                case 2: //ambos jugadores jugaron su primera carta
                    aux = jugador1.ultimaCartaJugada().compareTo(jugador2.ultimaCartaJugada()); //chequea cual carta es mayor de las jugadas
                    if (aux == -1){ //si es menor
                        jugadorGanaRond(jugador2, jugador1);
                    }
                    if (aux == 1){ //si es mayor
                        jugadorGanaRond(jugador1, jugador2);
                    }
                    if (aux == 0){ //si son iguales
                        jugador1.rondaGanada();
                        jugador2.rondaGanada();
                        parda = true;
                        cambioJugActual();
                    }
                    break;
                case 4: //ambos jugadores jugaron su segunda carta
                    aux = jugador1.ultimaCartaJugada().compareTo(jugador2.ultimaCartaJugada());
                    if (aux == -1){
                        jugadorGanaRond(jugador2, jugador1);
                    }
                    if (aux == 1){
                        jugadorGanaRond(jugador1, jugador2);
                    }
                    if (aux == 0){
                        if (!parda){ //si no hubo empate en la primera ronda, da puntos a ambos, causando que el que haya ganado la primera, gane la mano
                            jugador1.rondaGanada();
                            jugador2.rondaGanada();
                        }
                        else {
                            cambioJugActual();
                        }
                    }
                    break;
                case 6: //ambos jugaron su ultima carta
                    aux = jugador1.ultimaCartaJugada().compareTo(jugador2.ultimaCartaJugada());
                    if (aux == -1){
                        jugadorGanaRond(jugador2, jugador1);
                    }
                    if (aux == 1){
                        jugadorGanaRond(jugador1, jugador2);
                    }
                    if (aux == 0){
                        if (!parda){ //caso similar al empate arriba mencionado, comprueba la mayor de las primeras cartas jugadas
                            int aux2 = jugador1.primeraCartaJugada().compareTo(jugador2.primeraCartaJugada());
                            if (aux2 == -1){
                                jugador2.rondaGanada();
                            }
                            if (aux2 == 1){
                                jugador1.rondaGanada();
                            }
                        }
                        else { //da la victoria al jugador mano
                            jugadorMano.rondaGanada();
                        }
                    }
                    break;
                default:
                    cambioJugActual();
            }
        }
        return carAux;
    }

    private void jugadorGanaRond(Jugador gana, Jugador nogana){
            gana.rondaGanada();
            jugadorAct = gana;
            jugadorRiv = nogana;
    }

    public Jugador chequeoMano(){ //chequea si alguien gano la mano, retorna el ganador en ese caso, si no, null
        Jugador ganomano = null; //se deberia usar despues de cada carta jugada para ver si la mano se termina o no
        if(jugador1.manoGanada()){
            jugador1.darPuntos(trucoAcumulado);
            ganomano = jugador1;
            finalMano();
        }
        if (jugador2.manoGanada()){
            jugador2.darPuntos(trucoAcumulado);
            ganomano = jugador2;
            finalMano();
        }
        return ganomano;
    }

    private void finalMano(){ //cambia los punteros para cambiar a que jugador le toca jugar primero en cada mano
        if (jugadorMano == jugador1){
            jugadorMano = jugador2;
            jugadorAct = jugador2;
            jugadorRiv = jugador1;
        }
        else {
            jugadorMano = jugador1;
            jugadorAct = jugador1;
            jugadorRiv = jugador2;
        }
    }

    private void cambioJugActual(){ //tamb cambia los punteros, pero este cambio se utiliza en medio de las cartas jugadas
        Jugador aux = jugadorAct;
        jugadorAct = jugadorRiv;
        jugadorRiv = aux;
    }

    public Jugador irseAlMazo(){ //rendirse esta mano y dar puntos al rival
        Jugador ganadormano = jugadorRiv;
        jugadorRiv.darPuntos(trucoAcumulado);
        finalMano();
        return ganadormano;
    }

    public String mostrarManoJugAct(){
        return jugadorAct.mostrarMano() + " Envido: " + getJugadorAct().getTantoEnvidoAct();
    }
    public String mostrarCartasJugadas(){
        return jugadorRiv.getNombre() +": " + jugadorRiv.mostrarCartasJugadas() + "\n" + jugadorAct.getNombre() + ": " + jugadorAct.mostrarCartasJugadas();
    }

    public Jugador ganadorPartida(){ //forma para dar la victoria de la partida a un jugador
        Jugador ganador = null;
        if (jugador1.partidaGanada()){
            ganador = jugador1;
        }
        if (jugador2.partidaGanada()){
            ganador = jugador2;
        }
        return ganador;
    }
    public void truco(){
        trucoAcumulado = 2;
    }
    public void retruco(){
        trucoAcumulado = 3;
    }
    public void valecuatro(){
        trucoAcumulado = 4;
    }
    public void noQuiero(Jugador ganapuntos, int puntos){ //puntos = 1, 2 o 3
        ganapuntos.darPuntos(puntos);
    }

    private Jugador envidoChequeo(int puntos){ //ingresar los puntos del envido que se esta jugando: envido = 2, real = 3, envido+envido = 4, envi+real = 5
        Jugador aux = null; // se usa para calcular el ganador del mismo :)
        if (jugador1.getTantoEnvidoAct() > jugador2.getTantoEnvidoAct()){
            jugador1.darPuntos(puntos);
            aux = jugador1;
        }
        if (jugador2.getTantoEnvidoAct() > jugador1.getTantoEnvidoAct()){
            jugador2.darPuntos(puntos);
            aux = jugador2;
        }
        if (jugador1.getTantoEnvidoAct() == jugador2.getTantoEnvidoAct()){ //en empate da la victoria al jugador mano
            jugadorMano.darPuntos(puntos);
            aux = jugadorMano;
        }
        return aux;
    }
    public Jugador envido1(){ //cada caso hecho un método para mayor facilidad a la hora de hacer los menus
        Jugador aux = envidoChequeo(2);
        return aux;
    }
    public Jugador envido2(){
        Jugador aux = envidoChequeo(4);
        return aux;
    }
    public Jugador real1(){
        Jugador aux = envidoChequeo(3);
        return aux;
    }
    public Jugador real2(){
        Jugador aux = envidoChequeo(5);
        return aux;
    }
    public Jugador faltaEnvido(){ //es similar al chequeo de envido, pero al necesitar el puntaje actual del rival, se necesita hacer un chequeo del jugador que pierde
        Jugador aux = null;
        if (jugador1.getTantoEnvidoAct() > jugador2.getTantoEnvidoAct()){
            jugador1.darPuntos(30 - jugador2.getPuntos());
            aux = jugador1;
        }
        if (jugador2.getTantoEnvidoAct() > jugador1.getTantoEnvidoAct()){
            jugador2.darPuntos(30 - jugador1.getPuntos());
            aux = jugador2;
        }
        if (jugador1.getTantoEnvidoAct() == jugador2.getTantoEnvidoAct()){
            if (jugadorMano == jugador1){
                jugador1.darPuntos(30 - jugador2.getPuntos());
                aux = jugador1;
            }
            else{
                jugador2.darPuntos(30 - jugador1.getPuntos());
                aux = jugador2;
            }
        }
        return aux;
    }
    public String mostrarPuntaje(){
        return jugadorRiv.getNombre() +": " + jugadorRiv.getPuntos() + "\n" + jugadorAct.getNombre() + ": " + jugadorAct.getPuntos();
    }
}
