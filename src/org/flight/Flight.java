package org.flight;

public class Flight {
    private String departureIataCode;
    private String arrivalIataCode;

    public Flight(String departureIataCode, String arrivalIataCode) {
        this.departureIataCode = departureIataCode;
        this.arrivalIataCode = arrivalIataCode;
    }

    public String getDepartureIataCode() {
        return departureIataCode;
    }

    public String getArrivalIataCode() {
        return arrivalIataCode;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "departureIataCode='" + departureIataCode + '\'' +
                ", arrivalIataCode='" + arrivalIataCode + '\'' +
                '}';
    }
}