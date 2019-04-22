package cs.b07.cscb07project.flights;

import java.io.Serializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Creates a flight object that has private variables for its flight number,
 * arrival and departure time and place, cost and airline.
 */


public class Flight implements Serializable {


  private static final long serialVersionUID = -4153750692765067382L;

  private String arrivalTime;
  private String flightNum;
  private double cost;
  private String origin;
  private String destination;
  private String airline;
  private String departureTime;
  private int numSeats;
  private String travelTime;


  /**
   * Creates a Flight with the given information.
   *
   * @param flightNum Flight Number of this Flight.
   * @param departureTime this Flight's departure time.
   * @param arrivalTime this Flight's arrival time.
   * @param cost this Flight's cost.
   * @param origin this Flight's origin.
   * @param destination this Flight's destination.
   * @param airline this Flight's air line.
   */
  public Flight(String flightNum, String departureTime, String arrivalTime, String airline,
                String origin, String destination, String cost, String numSeats) {

    this.flightNum = flightNum;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.cost = Double.parseDouble(cost);
    this.numSeats = Integer.parseInt(numSeats);
    updateTime();

  }

  /**
   * Updates the Flight's total travel time.
   */
  public void updateTime() {
    try {
      SimpleDateFormat flightTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      Date start = flightTimeFormat.parse(getDepartureTime());
      Date end = flightTimeFormat.parse(getArrivalTime());
      double flightTravelTime = (((end.getTime() - start.getTime()) / 1000) / 60);
      this.travelTime = String.format("%02dH%02dM", (int) (flightTravelTime / 60),
              (int) (flightTravelTime % 60));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the number of seats in this Flight.
   * @return this Flight's number of seats.
   */
  public int getNumSeats() {
    return numSeats;
  }

  /**
   * Returns arrival time of this Flight.
   * @return this Flight's arrival time.
   */
  public String getArrivalTime() {
    return arrivalTime;
  }

  /**
   * Sets arrival time of this Flight.
   * @param arrivalTime this Flight's arrival time to set.
   */
  public void setArrivalTime(String arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  /**
   * Returns this Flight's flight number.
   * @return this Flight's flight number.
   */
  public String getFlightNum() {
    return flightNum;
  }

  /**
   * Returns cost of this Flight.
   * @return cost of this Flight.
   */
  public double getCost() {
    return cost;
  }

  /**
   * Sets cost of this Flight.
   * @param cost this Flight's cost to set.
   */
  public void setCost(double cost) {
    this.cost = cost;
  }

  /**
   * Returns origin of this Flight.
   * @return this Flight's origin.
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * Sets origin of this Flight.
   * @param origin this Flight's origin to set.
   */
  public void setOrigin(String origin) {
    this.origin = origin;
  }

  /**
   * Returns destination of this Flight.
   * @return this Flight's destination.
   */
  public String getDestination() {
    return destination;
  }


  /**
   * Lowers the number of seats in this Flight by one.
   */
  public void lowerNumSeats() {
    this.numSeats = numSeats - 1;
  }

  /**
   * Lowers the number of seats in this Flight by one.
   */
  public void setNumSeats(int seats) {
    this.numSeats = seats;
  }

  /**
   * Sets destination of this Flight.
   * @param destination this Flight's destination to set.
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * Returns airline of this Flight.
   * @return this Flight's airline.
   */
  public String getAirline() {
    return airline;
  }

  /**
   * Sets airline of this Flight.
   * @param airline this Flight's airline to set.
   */
  public void setAirline(String airline) {
    this.airline = airline;
  }

  /**
   * Returns departure time of this Flight.
   * @return this Flight's departure time.
   */
  public String getDepartureTime() {
    return departureTime;
  }

  /**
   * Sets departure time of this Flight.
   * @param departureTime this Flight's departure time to set.
   */
  public void setDepartureTime(String departureTime) {
    this.departureTime = departureTime;
  }


  /**
   * Returns the total travel time of this Flight.
   * @return this Flight's total travel time
   */
  public String getTotalTravelTime() {
    return this.travelTime;
  }

  /**
   * Returns a string representation of this Flight with no price attached.
   *
   * @return string representation of this flight with no price.
   */
  public String getNoPrice() {
    return flightNum + "," + departureTime + "," + arrivalTime + "," + airline + "," + origin + ","
            + destination;
  }

  @Override
  public String toString() {
    return flightNum + "," + departureTime + "," + arrivalTime + "," + airline + "," + origin + ","
            + destination + "," + String.format("%.2f", cost);
  }


}
