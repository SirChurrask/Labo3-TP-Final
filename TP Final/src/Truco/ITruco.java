package Truco;

public interface ITruco extends IJuegoCartas {
    public void truco();
    public void retruco();
    public void valecuatro();
    public void noQuiero(Jugador ganapuntos, int puntos);
    public String envido(int puntos);
    public void irseAlMazo();
}
