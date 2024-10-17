public class Flight {
    private Airport depart;
    private Airport arrivee;

    public Flight(Airport depart, Airport arrivee) {
        this.depart = depart;
        this.arrivee = arrivee;
    }

    @Override
    public String toString() {
        return "Vol{" +
                "Départ=" + depart +
                ", Fin=" + arrivee +
                '}';
    }
}