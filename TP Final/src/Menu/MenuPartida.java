package Menu;

import Truco.Jugador;
import Truco.Mazo;
import Truco.Partida;

import java.util.Scanner;

public class MenuPartida {
    //atri
    private Partida trucazo;
    private Jugador ganador;
    private Jugador ganoMano;
    private boolean cantarEnvido; //si está permitido cantar envido
    private Jugador cantaTruco; //puntero al jugador que cantó truco, para evitar que cante otra vez
    //


    public MenuPartida(Jugador jug1, Jugador jug2, Mazo mazo) {
        this.trucazo = new Partida(jug1,jug2, mazo);
        ganador = null;
        ganoMano = null;
        cantarEnvido = true;
        cantaTruco = null;
    }

    //

    public void partida(){
        Scanner scan = new Scanner(System.in);
        while (ganador == null) {
            trucazo.empezarMano(); //se hace el inicio de la mano, limpiando datos que podrian quedar de manos anteriores
            cantarEnvido = true;
            cantaTruco = null;
            while (ganoMano == null) {
                //jugador 1
                //System.out.println("jugador 1 \n");
                System.out.println(trucazo.getJugadorAct().getNombre());
                System.out.println(trucazo.mostrarManoJugAct());
                //inicializarTurno();
                opcionesJugador();
                if (ganoMano == null) { //si la mano no termino en las acciones del primer jugador
                    //System.out.println("jugador 2 \n");
                    System.out.println(trucazo.getJugadorAct().getNombre());
                    System.out.println(trucazo.mostrarManoJugAct());
                    //inicializarTurno();
                    opcionesJugador();
                }
                System.out.println(trucazo.mostrarCartasJugadas());
                System.out.println(trucazo.mostrarPuntaje());
                //System.out.println("final ronda");
            }
            //System.out.println("final mano");  //tanto el final ronda de arriba y el final mano es para revisar que los turnos avancen correctamente
            ganoMano = null;
            ganador = trucazo.ganadorPartida();
        }
        System.out.println("Ganador: " + ganador.getNombre());
    }

    private void inicializarTurno(){ //borrar desp
        //inicializar turno
        cantarEnvido = true;
        cantaTruco = null;
    }

    private void opcionesJugador(){
        Scanner scan = new Scanner(System.in);
        //datos aux para los switchs
        int i = 0;
        int cantar = 0;
        int resp1 = 0;
        int resp2 = 0;
        int resp3 = 0;
        int resp4 = 0;
        int resp5 = 0;
        int resp6 = 0;
        int envido = 0;
        int opcionturno = 0;
        Jugador aux = null;
        //opciones jugador
        while (i == 0){ //se pueden elegir varias opciones, como cantar envido, truco e irse al mazo, asi que se tiene que utilizar un loop para hacer esto posible
            System.out.println("qué quiere hacer? 1: jugar carta, 2: cantar, 3: irse al mazo");
            while (!scan.hasNextInt()) scan.next(); //para evitar errores
            opcionturno = scan.nextInt();
            switch (opcionturno) {
                case 1:
                    System.out.println(trucazo.getJugadorAct().getNombre() + " cual carta?");
                    while (!scan.hasNextInt()) scan.next();
                    i = scan.nextInt();
                    trucazo.jugarCarta(i); //jugar la carta de la mano
                    ganoMano = trucazo.chequeoMano(); //chequeo comparando las cartas jugadas de ambos jugadores para revisar si se termina la mano
                    i = 1; //rompe el while que loopea las opciones
                    break;
                case 2:
                    // cantar
                    System.out.println(trucazo.getJugadorAct().getNombre() + ", quiere cantar algo? 0: nada,  1: truco, 2: envido");
                    while (!scan.hasNextInt()) scan.next();
                    cantar = scan.nextInt();
                    switch (cantar) {
                        case 1:
                            if (trucazo.getJugadorAct() != cantaTruco) {
                                cantaTruco = trucazo.getJugadorAct(); //chequea si este jugador cantó truco en esta mano, si no, permite cantarlo y guarda puntero del jugador
                                if (trucazo.getTrucoAcumulado() == 1) { //revisa el truco acumulado, dato que muestra cual fue el truco mayor cantado (1 base, 2 truco, 3 retruco, 4 vale cuatro)
                                    System.out.println("truco");
                                    //jugador rival responde
                                    System.out.println(trucazo.getJugadorRiv().getNombre() + ", que responde? 0: no quiero,  1: quiero, 2: más");
                                    while (!scan.hasNextInt()) scan.next();
                                    resp1 = scan.nextInt();
                                    switch (resp1) {
                                        case 1:
                                            trucazo.truco(); //se confirma el truco
                                            break;
                                        case 2:
                                            System.out.println("retruco");
                                            //jugador actual responde
                                            System.out.println(trucazo.getJugadorAct().getNombre() + ", que responde? 0: no quiero,  1: quiero, 2: más");
                                            while (!scan.hasNextInt()) scan.next();
                                            resp2 = scan.nextInt();
                                            switch (resp2) {
                                                case 1:
                                                    trucazo.retruco(); //se confirma el retruco
                                                    break;
                                                case 2:
                                                    System.out.println("vale cuatro");
                                                    //jugador rival responde
                                                    System.out.println(trucazo.getJugadorRiv().getNombre() + ", que responde? 0: no quiero,  1: quiero");
                                                    while (!scan.hasNextInt()) scan.next();
                                                    resp3 = scan.nextInt();
                                                    switch (resp3) {
                                                        case 1:
                                                            trucazo.valecuatro(); //se confirma el vale cuatro
                                                            break;
                                                        default:
                                                            trucazo.noQuiero(trucazo.getJugadorAct(),3);
                                                            //corta el loop de la mano
                                                            i = 2;
                                                            ganoMano = trucazo.getJugadorAct();
                                                            break;
                                                    }
                                                    break;
                                                default:
                                                    trucazo.noQuiero(trucazo.getJugadorRiv(),2);
                                                    //corta el loop de la mano
                                                    i = 2;
                                                    ganoMano = trucazo.getJugadorRiv();
                                                    break;
                                            }
                                            break;
                                        default:
                                            trucazo.noQuiero(trucazo.getJugadorAct(),1);
                                            //corta el loop de la mano
                                            i = 2;
                                            ganoMano = trucazo.getJugadorAct();
                                            break;
                                    }
                                } else if (trucazo.getTrucoAcumulado() == 2) { //el truco ya esta cantado
                                    System.out.println("retruco");
                                    //jugador rival responde
                                    System.out.println(trucazo.getJugadorRiv().getNombre() + ", que responde? 0: no quiero,  1: quiero, 2: más");
                                    while (!scan.hasNextInt()) scan.next();
                                    resp1 = scan.nextInt();
                                    switch (resp1) {
                                        case 1:
                                            trucazo.retruco(); //se confirma el retruco
                                            break;
                                        case 2:
                                            System.out.println("vale cuatro");
                                            //jugador actual responde
                                            System.out.println(trucazo.getJugadorAct().getNombre() + ", que responde? 0: no quiero,  1: quiero");
                                            while (!scan.hasNextInt()) scan.next();
                                            resp2 = scan.nextInt();
                                            switch (resp2) {
                                                case 1:
                                                    trucazo.valecuatro(); //se confirma el vale cuatro
                                                    break;
                                                default:
                                                    trucazo.irseAlMazo(trucazo.getJugadorRiv());
                                                    //corta el loop de la mano
                                                    i = 2;
                                                    ganoMano = trucazo.getJugadorRiv();
                                                    break;
                                            }
                                            break;
                                        default:
                                            trucazo.irseAlMazo(trucazo.getJugadorAct());
                                            //corta el loop de la mano
                                            i = 2;
                                            ganoMano = trucazo.getJugadorAct();
                                            break;
                                    }
                                    break;
                                } else if (trucazo.getTrucoAcumulado() == 3) { //el retruco ya esta cantado
                                    System.out.println("vale cuatro");
                                    //jugador rival responde
                                    System.out.println(trucazo.getJugadorRiv() + ", que responde? 0: no quiero,  1: quiero");
                                    while (!scan.hasNextInt()) scan.next();
                                    resp1 = scan.nextInt();
                                    switch (resp1) {
                                        case 1:
                                            trucazo.valecuatro(); //se confirma el vale cuatro
                                            break;
                                        default:
                                            trucazo.irseAlMazo(trucazo.getJugadorAct());
                                            //corta el loop de la mano
                                            i = 2;
                                            ganoMano = trucazo.getJugadorAct();
                                            break;
                                    }
                                    break;
                                }
                            } else {
                                System.out.println("no se puede cantar truco");
                            }
                            if(ganoMano != null){ //si cantar termina en un no quiero de truco, termina la mano
                                trucazo.finalMano();
                            }
                            break;
                        case 2:
                            //envido
                            if (trucazo.getRondaactual() < 2){ //solamente se puede cantar envido en la primera ronda de la mano
                                if (cantarEnvido) { //chequeo si ya se cantó el envido
                                    System.out.println("cuánto quiere cantar de envido? 0: mejor no, 1: envido, 2: real, 3: falta");
                                    while (!scan.hasNextInt()) scan.next();
                                    envido = scan.nextInt();
                                    switch (envido) {
                                        case 1:
                                            System.out.println("envido");
                                            //jugador rival responde
                                            System.out.println(trucazo.getJugadorRiv().getNombre() + ", que responde? 0: no quiero,  1: quiero, 2: más");
                                            while (!scan.hasNextInt()) scan.next();
                                            resp1 = scan.nextInt();
                                            switch (resp1) {
                                                case 1:
                                                    aux = trucazo.envido1(); //se calcula quien gana
                                                    System.out.println(aux.getNombre() + " gano el tanto con " + aux.getTantoEnvidoAct());
                                                    break;
                                                case 2:
                                                    System.out.println("cuánto más quiere cantar? 1: envido, 2: real, 3: falta, 0: mejor no");
                                                    while (!scan.hasNextInt()) scan.next();
                                                    resp2 = scan.nextInt();
                                                    switch (resp2) {
                                                        case 1:
                                                            System.out.println("envido");
                                                            // jugador actual
                                                            System.out.println(trucazo.getJugadorAct().getNombre() + ", que responde? 0: no quiero,  1: quiero, 2: más");
                                                            while (!scan.hasNextInt()) scan.next();
                                                            resp3 = scan.nextInt();
                                                            switch (resp3) {
                                                                case 1:
                                                                    aux = trucazo.envido2(); //se calcula quien gana
                                                                    System.out.println(aux.getNombre() + " gano el tanto con " + aux.getTantoEnvidoAct());
                                                                    break;
                                                                case 2:
                                                                    System.out.println("cuánto más quiere cantar? 1: real, 2: falta, 0: mejor no");
                                                                    while (!scan.hasNextInt()) scan.next();
                                                                    resp4 = scan.nextInt();
                                                                    switch (resp4) {
                                                                        case 1:
                                                                            System.out.println("real envido");
                                                                            //jugador rival
                                                                            System.out.println(trucazo.getJugadorRiv().getNombre() + ", que responde? 0: no quiero,  1: quiero, 2: más");
                                                                            while (!scan.hasNextInt()) scan.next();
                                                                            resp5 = scan.nextInt();
                                                                            switch (resp5) {
                                                                                case 1:
                                                                                    aux = trucazo.real2(); //se calcula quien gana
                                                                                    System.out.println(aux.getNombre() + " gano el tanto con " + aux.getTantoEnvidoAct());
                                                                                    break;
                                                                                case 2:
                                                                                    System.out.println("falta envido");
                                                                                    //jugador actual responde
                                                                                    System.out.println(trucazo.getJugadorAct().getNombre() + ", que responde? 0: no quiero,  1: quiero");
                                                                                    while (!scan.hasNextInt()) scan.next();
                                                                                    resp6 = scan.nextInt();
                                                                                    switch (resp6) {
                                                                                        case 1:
                                                                                            aux = trucazo.faltaEnvido(); //se calcula quien gana
                                                                                            System.out.println(aux.getNombre() + " gano el tanto con " + aux.getTantoEnvidoAct());
                                                                                            break;
                                                                                        default:
                                                                                            trucazo.noQuiero(trucazo.getJugadorRiv(), 4);
                                                                                            break;
                                                                                    }
                                                                                    break;
                                                                                default:
                                                                                    trucazo.noQuiero(trucazo.getJugadorAct(), 3);
                                                                                    break;
                                                                            }
                                                                            break;
                                                                        case 2:
                                                                            System.out.println("falta envido");
                                                                            //jugador rival
                                                                            System.out.println(trucazo.getJugadorRiv().getNombre() + ", que responde? 0: no quiero,  1: quiero");
                                                                            while (!scan.hasNextInt()) scan.next();
                                                                            resp5 = scan.nextInt();
                                                                            switch (resp5) {
                                                                                case 1:
                                                                                    aux = trucazo.faltaEnvido(); //se calcula quien gana
                                                                                    System.out.println(aux.getNombre() + " gano el tanto con " + aux.getTantoEnvidoAct());
                                                                                    break;
                                                                                default:
                                                                                    trucazo.noQuiero(trucazo.getJugadorAct(), 3);
                                                                                    break;
                                                                            }
                                                                            break;
                                                                        default:
                                                                            trucazo.noQuiero(trucazo.getJugadorAct(), 3);
                                                                            break;
                                                                    }
                                                                    break;
                                                                default:
                                                                    trucazo.noQuiero(trucazo.getJugadorAct(), 2);
                                                                    break;
                                                            }
                                                            break;
                                                        case 2:
                                                            System.out.println("real envido");
                                                            // jugador actual
                                                            System.out.println(trucazo.getJugadorAct().getNombre() + ", que responde? 0: no quiero,  1: quiero, 2: más");
                                                            while (!scan.hasNextInt()) scan.next();
                                                            resp3 = scan.nextInt();
                                                            switch (resp3) {
                                                                case 1:
                                                                    aux = trucazo.real2(); //se calcula quien gana
                                                                    System.out.println(aux.getNombre() + " gano el tanto con " + aux.getTantoEnvidoAct());
                                                                    break;
                                                                case 2:
                                                                    System.out.println("falta envido");
                                                                    //jugador actual responde
                                                                    System.out.println(trucazo.getJugadorAct().getNombre() + ", que responde? 0: no quiero,  1: quiero");
                                                                    while (!scan.hasNextInt()) scan.next();
                                                                    resp2 = scan.nextInt();
                                                                    switch (resp2) {
                                                                        case 1:
                                                                            aux = trucazo.faltaEnvido(); //se calcula quien gana
                                                                            System.out.println(aux.getNombre() + " gano el tanto con " + aux.getTantoEnvidoAct());
                                                                            break;
                                                                        default:
                                                                            trucazo.noQuiero(trucazo.getJugadorRiv(), 3);
                                                                            break;
                                                                    }
                                                                    break;
                                                                default:
                                                                    trucazo.noQuiero(trucazo.getJugadorAct(), 2);
                                                                    break;
                                                            }
                                                            break;
                                                        case 3:
                                                            // jugador actual
                                                            System.out.println(trucazo.getJugadorAct().getNombre() + ", que responde? 0: no quiero,  1: quiero");
                                                            while (!scan.hasNextInt()) scan.next();
                                                            resp2 = scan.nextInt();
                                                            switch (resp2) {
                                                                case 1:
                                                                    aux = trucazo.faltaEnvido(); //se calcula quien gana
                                                                    System.out.println(aux.getNombre() + " gano el tanto con " + aux.getTantoEnvidoAct());
                                                                    break;
                                                                default:
                                                                    trucazo.noQuiero(trucazo.getJugadorRiv(), 2);
                                                                    break;
                                                            }
                                                            break;
                                                        default:
                                                            trucazo.noQuiero(trucazo.getJugadorAct(), 1);
                                                            break;
                                                    }
                                                    break;
                                                default:
                                                    trucazo.noQuiero(trucazo.getJugadorAct(), 1);
                                                    break;
                                            }
                                            cantarEnvido = false; //para evitar que se cante envido en la primera mano
                                            break;
                                        case 2:
                                            System.out.println("real envido");
                                            //jugador rival responde
                                            System.out.println(trucazo.getJugadorRiv().getNombre() + ", que responde? 0: no quiero,  1: quiero, 2: más");
                                            while (!scan.hasNextInt()) scan.next();
                                            resp1 = scan.nextInt();
                                            switch (resp1) {
                                                case 1:
                                                    aux = trucazo.real1(); //se calcula quien gana
                                                    System.out.println(aux.getNombre() + " gano el tanto con " + aux.getTantoEnvidoAct());
                                                    break;
                                                case 2:
                                                    System.out.println("falta envido");
                                                    //jugador actual responde
                                                    System.out.println(trucazo.getJugadorAct().getNombre() + ", que responde? 0: no quiero,  1: quiero");
                                                    while (!scan.hasNextInt()) scan.next();
                                                    resp2 = scan.nextInt();
                                                    switch (resp2) {
                                                        case 1:
                                                            aux = trucazo.faltaEnvido(); //se calcula quien gana
                                                            System.out.println(aux.getNombre() + " gano el tanto con " + aux.getTantoEnvidoAct());
                                                            break;
                                                        default:
                                                            trucazo.noQuiero(trucazo.getJugadorRiv(), 2);
                                                            break;
                                                    }
                                                    break;
                                                default:
                                                    trucazo.noQuiero(trucazo.getJugadorAct(), 1);
                                                    break;
                                            }
                                            cantarEnvido = false;
                                            break;
                                        case 3:
                                            System.out.println("falta envido");
                                            //jugador rival responde
                                            System.out.println(trucazo.getJugadorRiv().getNombre() + ", que responde? 0: no quiero,  1: quiero");
                                            while (!scan.hasNextInt()) scan.next();
                                            resp1 = scan.nextInt();
                                            switch (resp1) {
                                                case 1:
                                                    aux = trucazo.faltaEnvido(); //se calcula quien gana
                                                    System.out.println(aux.getNombre() + " gano el tanto con " + aux.getTantoEnvidoAct());
                                                    break;
                                                default:
                                                    trucazo.noQuiero(trucazo.getJugadorAct(), 1);
                                                    break;
                                            }
                                            cantarEnvido = false;
                                            break;
                                        default:
                                            System.out.println("okei");
                                            break;
                                    }
                                } else {
                                    System.out.println("no se puede cantar envido");
                                }
                            }else{
                                System.out.println("no se puede cantar envido");
                            }
                            if(trucazo.getJugadorAct().partidaGanada() || trucazo.getJugadorRiv().partidaGanada()){ //si el puntaje del envido gana la partida, corta el loop de la mano
                                i = 2;
                                trucazo.finalMano();
                                ganoMano = trucazo.getJugadorMano();
                            }
                            break;
                        default:
                            System.out.println("bueno");
                            break;
                    }
                    break;
                case 3:
                    //irse al mazo
                    System.out.println("al mazo");
                    trucazo.irseAlMazo(trucazo.getJugadorRiv());//da puntos
                    ganoMano = trucazo.getJugadorRiv();//corta el loop mano
                    trucazo.finalMano();//cambia punteros para empezar nueva mano
                    i = 3;
                    break;
                default:
                    System.out.println("otra vez");
                    break;
            }
        }
        //fin loop opciones jugador
    }

    public Jugador jugadorGanador(){
        return jugadorGanador();
    } //para retornar el ganador al finalizar la partida
}

