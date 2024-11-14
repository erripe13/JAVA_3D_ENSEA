public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // Test airport
        Airport airport = new Airport("Charles de Gaulle", 49.0097, 2.5479, "CDG");
        System.out.println(airport);

        // Test WOrld
        World world = new World("doc/airport-codes_no_comma.csv");

        System.out.println("Found " + world.getList().size() + " airports.");
        Airport paris = world.findNearestAirport(2.316, 48.866);
        Airport cdg = world.findByCode("CDG");
        double distance = world.distance(2.316, 48.866, paris.getLongitude(), paris.getLatitude());
        System.out.println(paris);
        System.out.println(distance);
        double distanceCDG = world.distance(2.316, 48.866, cdg.getLongitude(), cdg.getLatitude());
        System.out.println(cdg);
        System.out.println(distanceCDG);

    }
}