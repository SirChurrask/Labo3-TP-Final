package Truco;

import java.io.Serializable;
import java.util.Objects;

public class Carta implements Serializable {
    //atri
    private int numero;
    private String palo;

    //constr
    public Carta (int numb, String palo){
        numero = numb;
        this.palo = palo;
    }

    //gett n sett
    public int getNumero() {
        return numero;
    }

    public String getPalo() {
        return palo;
    }

    //met
    public int valorCarta(){
        return numero;
    }
    @Override
    public String toString() {
        return "El " + numero + " de " + palo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return numero == carta.numero && Objects.equals(palo, carta.palo);
    }

    @Override
    public int hashCode() {
        return 14;
    }
}
