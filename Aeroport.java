public class Aeroport {
    // Attributs
    private String IATA;
    private String name;
    private String country;
    private double latitude;
    private double longitude;

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

    // Surcharge de la méthode toString pour une représentation claire
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

    // Méthode principale pour tester la classe
    public static void main(String[] args) {
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", "France", 49.0097, 2.5479);
        System.out.println(aeroport.toString());
    }
}
