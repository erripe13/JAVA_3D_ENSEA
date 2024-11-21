import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Aeroport> list;

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

    public Aeroport trouverAeroportParCode(String codeIATA) {
        return list.stream().filter(a -> a.getIATA().equalsIgnoreCase(codeIATA)).findFirst().orElse(null);
    }
}
