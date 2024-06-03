package Truco;

import java.util.ArrayList;
import java.util.Random;

public class Mazo <E> {
    //atri
    private ArrayList<E> mazo;

    //constru
    public Mazo() {
        mazo = new ArrayList<>();
    }

    //gett n sett

    //met
    public E robar() { //retorna la primera carta y la saca del mazo
        E aux = null;
        if (!mazo.isEmpty()) {
            aux = mazo.getFirst();
            mazo.removeFirst();
        }
        return aux;
    }

    public void agregar(E cosa) {
        mazo.add(cosa);
    }

    public void eliminar(E cosa) {
        if (cosa != null) {
            mazo.remove(cosa);
        }
    }

    public void mezclar() {
        Random aleatorio = new Random();
        for (int i = 0; i < mazo.size(); i++) {
            int numeroRndm = aleatorio.nextInt(mazo.size());
            E aux = mazo.get(numeroRndm);
            mazo.set(numeroRndm, mazo.get(i));
            mazo.set(i, aux);
        }
    }

    public E adivinar() { //solamente retorna la primera carta, no la elimina
        E aux = null;
        if (!mazo.isEmpty()) {
            aux = mazo.getFirst();
        }
        return aux;
    }

    public void alFondo() { //manda al final la primera carta
        if (!mazo.isEmpty()) {
            E aux = mazo.getFirst();
            mazo.removeFirst();
            mazo.add(aux);
        }
    }
    //originalmente, la clase se pensó para ser un mazo de mtg, por eso los metodos
    //para evitar problemas en el pasaje de datos y la posible repeticion o falta de cartas en el mazo, se usa adivinar/al fondo en vez de robar

    @Override
    public String toString() {
        return "Test.Mazo{" +
                "mazo=" + mazo +
                '}';
    }

    public void limpiarMazo(){
        mazo.clear();
    }

    public int cantidadTotal(){
        return mazo.size();
    }

    public boolean estaVacio(){
        return mazo.isEmpty();
    }
}
