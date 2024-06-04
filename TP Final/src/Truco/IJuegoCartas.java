package Truco;

public interface IJuegoCartas {
    public Carta jugarCarta(int carta);
    public void empezarMano();
    public Jugador ganadorPartida();
    public String mostrarPuntaje();
}
