import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Airport> airportList;

    public World(String fileName) {
        airportList = new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String s = buf.readLine();
            while (s != null) {
                s = s.replaceAll("\"", ""); // Remove quotes
                String fields[] = s.split(",");
                // Debugging point
                if (fields[1].equals("large_airport")) {
                    String name = fields[2];
                    if (isNumeric(fields[11]) && isNumeric(fields[12])) {
                        double latitude = Double.parseDouble(fields[12]);
                        double longitude = Double.parseDouble(fields[11]);
                        String codeIATA = fields[9];
                        airportList.add(new Airport(name, latitude, longitude, codeIATA));
                    }
                }
                s = buf.readLine();
            }
            buf.close();
        } catch (IOException e) {
            System.out.println("Maybe the file isn't there?");
            e.printStackTrace();
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Airport findByCode(String codeIATA) {
        for (Airport airport : airportList) {
            if (codeIATA.equals(airport.getCodeIATA())) {
                return airport;
            }
        }
        return null;
    }


    public Airport findNearestAirport(double longitude, double latitude) {
        Airport nearest = null;
        double minDistance = Double.MAX_VALUE;
        for (Airport airport : airportList) {
            double distance = distance(longitude, latitude, airport.getLongitude(), airport.getLatitude());

            if (distance < minDistance) {
                minDistance = distance;
                nearest = airport;
            }
        }
        return nearest;
    }
    public double distance(double lon1, double lat1, double lon2, double lat2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double aVal = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(aVal), Math.sqrt(1 - aVal));
        return R * c; // convert to kilometers
    }

    public List<Airport> getList() {
        return airportList;
    }
}