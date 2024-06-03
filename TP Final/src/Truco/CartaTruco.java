package Truco;

public class CartaTruco extends Carta implements Comparable{
//la razon por la que esta clase existe es por el valor del truco de cada carta
    //ademas de que se puede utilizar la clase "Carta" para otros juegos de cartas diferentes

    //atri
    private int valorTruco;

    //const
    public CartaTruco(int numb, String palo, int valor){
        super(numb, palo);
        valorTruco = valor;
    }

    //gett
    public int getValorTruco() {
        return valorTruco;
    }

    //metodos
    @Override //Este método está pensado para utilizarse a la hora de calcular el envido
    public int valorCarta(){
        int e = super.valorCarta();
        if (e > 9){
            e = 0;
        }
        return e;
    }

    @Override
    public int compareTo(Object o) { //comparacion del valor de la carta en el truco, no el numero o el palo
        int resp = 0;
        if (o != null){
            if (o instanceof CartaTruco){
                CartaTruco aux = (CartaTruco) o;
                if (aux.valorTruco > this.valorTruco){
                    resp = -1;
                }
                if (aux.valorTruco < this.valorTruco){
                    resp = 1;
                }
            }
        }
        return resp;
    }
}
