package cs.b07.cscb07project.flights;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents an array of Flights.
 */
public class ArrayFlight implements Serializable {

  private static final long serialVersionUID = 2195201312464844764L;
  private ArrayList<Flight> flights;

  /**
   * Makes an empty ArrayFlight.
   */
  public ArrayFlight() {
    this.flights = new ArrayList<Flight>();
  }

  /**
   * Adds a flight to this array.
   *
   * @param newFlight a Flight object to add (overwrite if exist) in this ArrayFlight.
   */
  public void add(Flight newFlight) {
    // Replace if exists
    boolean found = false;
    int index = 0;
    for (int i = 0; i < flights.size(); i++) {
      if (newFlight.getFlightNum().equals(flights.get(i).getFlightNum())) {
        found = true;
        index = i;
      }
    }
    if (found) {
      flights.remove(index);
    }
    flights.add(newFlight);
  }

  /**
   * Makes an array of flights connecting this origin and destination on this date.
   *
   * @param date represents the date at which we want the flights
   * @param origin represents the origin at which we want the flights
   * @param destination represents the destination at which we want the flight
   * @return result includes all flights connecting this origin and destination on this date.
   */
  public ArrayFlight originDestination(String date, String origin, String destination) {
    // Make an arrayList for the flights
    ArrayFlight result = new ArrayFlight();
    // Loop through all the flights and check each flight's destination
    for (int i = 0; i < flights.size(); i++) {
      // Check each flight's origin
      if (flights.get(i).getOrigin().equals(origin)
              && flights.get(i).getDestination().equals(destination)
              && flights.get(i).getDepartureTime().substring(0, 10).equals(date)) {
        result.add(flights.get(i));
      }
    }
    return result;
  }

  /**
   * To add all flights into this array.
   *
   * @param other we use this add function to input this other flights, making sure nothing is
   *        overlapping.
   */
  public void putAll(ArrayFlight other) {
    for (int i = 0; i < other.getSize(); i++) {
      add(other.getFlight(i));
    }
  }

  /**
   * Returns total number of all Flights in this array.
   *
   * @return total number of flights.
   */
  public int getSize() {
    return flights.size();
  }

  /**
   * Returns this flight at index in this array.
   *
   * @param index represents the index at which we want the flight.
   * @return this flight at index i.
   */
  public Flight getFlight(int index) {
    return flights.get(index);
  }

  /**
   * Returns a Flight with the given flight number.
   *
   * @param flightNum represents the flight number of the desired flight.
   * @return this flight with flightNum.
   */
  public Flight getFlight(String flightNum) {
    for (int i = 0; i < flights.size(); i++) {
      if (flights.get(i).getFlightNum().equals(flightNum)) {
        return flights.get(i);
      }
    }
    return null;
  }

  /**
   * Returns a string of flights with no price.
   *
   * @return a multi-lines String consists of results of every flight in flights calling getNoPrice
   *         in this Flight class.
   */
  public String getNoPrice() {
    String everything = "";
    for (int i = 0; i < flights.size(); i++) {
      everything += flights.get(i).getNoPrice();
      if (i < flights.size()) {
        everything += "\n";
      }
    }
    return everything;
  }

  /**
   * Return true if this ArrayFlight is empty, false otherwise.
   * @return true if this ArrayFlight is empty, false otherwise.
   */
  public boolean isEmpty() {
    return flights.isEmpty();
  }

  @Override
  public String toString() {
    String everything = "";
    for (int i = 0; i < flights.size(); i++) {
      everything += flights.get(i);
      if (i < flights.size()) {
        everything += "\n";
      }
    }
    return everything;
  }
}
