package Truco;

public interface ITruco extends IJuegoCartas {
    public void truco();
    public void retruco();
    public void valecuatro();
    public void noQuiero(Jugador ganapuntos, int puntos);
    public void irseAlMazo(Jugador ganapuntos);
    public Jugador envido1();
    public Jugador envido2();
    public Jugador real1();
    public Jugador real2();
    public Jugador faltaEnvido();

}