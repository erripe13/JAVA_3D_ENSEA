import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Aeroport> list;

    public World(String fileName) {
        list = new ArrayList<>();
        try (BufferedReader buf = new BufferedReader(new FileReader(fileName))) {
            String s = buf.readLine();
            if (s == null) {
                System.out.println("Erreur : le fichier semble vide.");
                return;
            }

            while ((s = buf.readLine()) != null) {
                s = s.replaceAll("\"", "");
                String[] fields = s.split(",", -1);
                if (fields.length > 12 && fields[1].equals("large_airport")) {
                    String name = fields[2].trim();
                    String iata = fields[9].trim();
                    String country = fields[5].trim();

                    try {
                        double latitude = Double.parseDouble(fields[12].trim());
                        double longitude = Double.parseDouble(fields[11].trim());
                        Aeroport aeroport = new Aeroport(iata, name, country, latitude, longitude);
                        list.add(aeroport);
                    } catch (NumberFormatException e) {
                        System.out.println("Erreur de format pour les coordonnées : " + name);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + fileName);
            e.printStackTrace();
        }
    }

    public Aeroport findByCode(String codeIATA) {
        for (Aeroport aeroport : list) { // 'list' est la liste des Aeroports dans World
            if (codeIATA.equalsIgnoreCase(aeroport.getIATA())) { // Utilise le getter de IATA
                return aeroport;
            }
        }
        return null; // Retourne null si aucun aéroport correspondant n'est trouvé
    }

    public Aeroport trouverAeroportLePlusProche(double latitude, double longitude) {
        Aeroport plusProche = null;
        double distanceMin = Double.MAX_VALUE;

        for (Aeroport aeroport : list) {
            double distance = aeroport.calculerDistance(new Aeroport("", "", "", latitude, longitude));
            if (distance < distanceMin) {
                distanceMin = distance;
                plusProche = aeroport;
            }
        }
        return plusProche;
    }
}
