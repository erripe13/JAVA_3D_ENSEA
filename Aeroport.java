public class Aeroport {
    // Attributs
    private final String IATA;
    private final String name;
    private final String country;
    private final double latitude;
    private final double longitude;

    // Constructeur
    public Aeroport(String IATA, String name, String country, double latitude, double longitude) {
        this.IATA = IATA;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public String getIATA() {
        return IATA;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Aeroport{" +
                "IATA='" + IATA + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public double calculerDistance(Aeroport autre) {
        final int RAYON_TERRE = 6371; // Rayon de la Terre en km
        double distanceLatitude = Math.toRadians(autre.getLatitude() - this.latitude);
        double distanceLongitude = Math.toRadians(autre.getLongitude() - this.longitude);
        double a = Math.sin(distanceLatitude / 2) * Math.sin(distanceLatitude / 2) +
                Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(autre.getLatitude())) *
                        Math.sin(distanceLongitude / 2) * Math.sin(distanceLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RAYON_TERRE * c;
    }

    public static void main(String[] args) {
        Aeroport cdg = new Aeroport("CDG", "Charles de Gaulle", "France", 49.0097, 2.5479);
        System.out.println(cdg);
    }
}
