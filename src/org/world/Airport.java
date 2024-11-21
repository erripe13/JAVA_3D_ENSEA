package org.world;

public class Airport {
    private final String name;
    private final double latitude;
    private final double longitude;
    private final String codeIATA;

    public Airport(String name, double latitude, double longitude, String codeIATA) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.codeIATA = codeIATA;
    }

    @Override
    public String toString() {
        return "org.world.Airport" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", code='" + codeIATA + '\'' +
                '}';
    }
    public String getCodeIATA() {
        return codeIATA;
    }

    public double getLatitude() {return latitude;}

    public double getLongitude() {
        return longitude;
    }

    public double calculDistance(Airport a) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(a.getLatitude() - this.latitude);
        double lonDistance = Math.toRadians(a.getLongitude() - this.longitude);
        double aVal = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(a.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(aVal), Math.sqrt(1 - aVal));
        return R * c; // convert to kilometers
    }

}
