import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Airport> listeAirports;

    public World(String fileName) {
        listeAirports = new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String ligne = buf.readLine();
            while (ligne != null) {
                ligne = ligne.replaceAll("\"", "");
                String fields[] = ligne.split(",");
                if (fields[1].equals("large_airport")) {
                    String nom = fields[2];
                    String codeIATA = fields[9];
                    String[] coordonnees = fields[12].split(" ");
                    double latitude = Double.parseDouble(coordonnees[1]);
                    double longitude = Double.parseDouble(coordonnees[2]);
                    listeAirports.add(new Airport(nom, latitude, longitude, codeIATA));
                }
                ligne = buf.readLine();
            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture du fichier");
            e.printStackTrace();
        }
    }

    public Airport findByCode(String code) {
        for (Airport Airport : listeAirports) {
            if (Airport.getCodeIATA().equals(code)) {
                return Airport;
            }
        }
        return null;
    }

    public Airport findNearestAirport(double longitude, double latitude) {
        Airport AirportProche = null;
        double distanceMin = Double.MAX_VALUE;
        for (Airport Airport : listeAirports) {
            double distance = Math.pow((latitude - Airport.getLatitude()), 2) +
                    Math.pow(((longitude - Airport.getLongitude()) * Math.cos(Math.toRadians((latitude + Airport.getLatitude()) / 2))), 2);
            if (distance < distanceMin) {
                distanceMin = distance;
                AirportProche = Airport;
            }
        }
        return AirportProche;
    }

    public List<Airport> getListeAirports() {
        return listeAirports;
    }
}