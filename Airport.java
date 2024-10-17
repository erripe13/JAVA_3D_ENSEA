public class Airport {
    private String nom;
    private double latitude;
    private double longitude;
    private String codeIATA;

    public Airport(String nom, double latitude, double longitude, String codeIATA) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.codeIATA = codeIATA;
    }

    @Override
    public String toString() {
        return "Aeroport{" +
                "nom='" + nom + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", codeIATA='" + codeIATA + '\'' +
                '}';
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCodeIATA() {
        return codeIATA;
    }

    public double calculDistance(Airport autre) {
        double theta1 = Math.toRadians(this.latitude);
        double theta2 = Math.toRadians(autre.getLatitude());
        double deltaPhi = Math.toRadians(autre.getLongitude() - this.longitude);
        return Math.pow((theta2 - theta1), 2) + Math.pow((deltaPhi * Math.cos((theta2 + theta1) / 2)), 2);
    }
}