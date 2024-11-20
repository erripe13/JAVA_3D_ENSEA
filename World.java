import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class World {
    protected ArrayList<Aeroport> list;

    public World(String fileName) {
        this.list = new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String s = buf.readLine(); // Lire la première ligne (en-tête)

            // Ajout de vérification pour voir si le fichier a été lu correctement
            if (s == null) {
                System.out.println("Erreur : le fichier semble vide.");
                return;
            }

            while ((s = buf.readLine()) != null) {
                s = s.replaceAll("\"", ""); // Enlève les guillemets
                String[] fields = s.split(",", -1); // Divise la ligne en champs

                // Vérifie que le type est "large_airport" et que les colonnes des coordonnées existent
                if (fields.length > 12 && fields[1].equals("large_airport")) {
                    String name = fields[2].trim();
                    String iata = fields[9].trim();
                    String country = fields[5].trim();

                    try {
                        // Extraction de la latitude et de la longitude à partir des colonnes 11 et 12
                        double latitude = Double.parseDouble(fields[12].trim());
                        double longitude = Double.parseDouble(fields[11].trim());

                        // Création et ajout de l'aéroport
                        Aeroport aeroport = new Aeroport(iata, name, country, latitude, longitude);
                        list.add(aeroport);
                        // Affichage pour vérifier les aéroports ajoutés
                        //System.out.println("Ajout de l'aéroport : " + aeroport);
                    } catch (NumberFormatException e) {
                        System.out.println("Erreur de format pour les coordonnées : " + name + " (Latitude : " + fields[11] + ", Longitude : " + fields[12] + ")");
                    }
                } else {
                    //System.out.println("Ligne ignorée : type = " + fields[1] + ", nombre de champs = " + fields.length);
                }
            }
            buf.close();
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du fichier : " + fileName);
            e.printStackTrace();
        }
    }

    public List<Aeroport> getList() {
        return list;
    }

    public double distance(Aeroport a, double latitude, double longitude) {
        double theta1 = Math.toRadians(a.getLatitude());
        double theta2 = Math.toRadians(latitude);
        double phi1 = Math.toRadians(a.getLongitude());
        double phi2 = Math.toRadians(longitude);
        double dist = Math.pow(theta2 - theta1, 2) + Math.pow((phi2 - phi1) * Math.cos((theta2 + theta1) / 2), 2);
        return dist;
    }

    public double distance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double theta1 = Math.toRadians(latitude1);
        double theta2 = Math.toRadians(latitude2);
        double phi1 = Math.toRadians(longitude1);
        double phi2 = Math.toRadians(longitude2);
        double dist = Math.pow(theta2 - theta1, 2) + Math.pow((phi2 - phi1) * Math.cos((theta2 + theta1) / 2), 2);
        return dist;
    }

    public Aeroport findNearestAirport(double latitude, double longitude) {
        Aeroport closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Aeroport aeroport : list) {
            double dist = distance(aeroport, latitude, longitude);
            if (dist < minDistance) {
                minDistance = dist;
                closest = aeroport;
            }
        }
        return closest;
    }

    public Aeroport findByCode(String codeIATA) {
        for (Aeroport aeroport : list) {
            if (aeroport.getIATA().equalsIgnoreCase(codeIATA)) {
                return aeroport;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        World w = new World("./data/airport-codes_no_comma.csv");
        System.out.println("Found " + w.getList().size() + " airports.");

        Aeroport paris = w.findNearestAirport(48.866, 2.316); // Latitude and Longitude switched for consistency
        Aeroport cdg = w.findByCode("CDG");

        if (paris != null) {
            double distance = w.distance(48.866, 2.316, paris.getLatitude(), paris.getLongitude());
            System.out.println(paris);
            System.out.println(distance);
        } else {
            System.out.println("Aucun aéroport trouvé à proximité de Paris.");
        }

        if (cdg != null) {
            double distanceCDG = w.distance(48.866, 2.316, cdg.getLatitude(), cdg.getLongitude());
            System.out.println(cdg);
            System.out.println(distanceCDG);
        } else {
            System.out.println("Aéroport avec le code CDG introuvable.");
        }
    }
}
