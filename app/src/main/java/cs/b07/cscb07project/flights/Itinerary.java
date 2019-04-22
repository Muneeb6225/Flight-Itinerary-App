
package cs.b07.cscb07project.flights;

import cs.b07.cscb07project.flights.Flight;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Creates an itinerary class that represents a series of Flights.
 */

public class Itinerary implements Serializable {

  private static final long serialVersionUID = -6138482465952539193L;

  private ArrayList<Flight> itinerary;

  private double totalCost;
  private String finalDestination;
  private String origin;

  /**
   * Creates an empty ArrayList for this itinerary.
   */
  public Itinerary() {
    itinerary = new ArrayList<Flight>();
    this.totalCost = 0;
  }

  /**
   * Add Flights to this Itinerary.
   *
   * @param flight a Flight to be added to this Itinerary.
   * @throws ParseException when this Flight does not have correct date format.
   */
  public void addFlight(Flight flight) {
    if (itinerary.isEmpty()) {
      origin = flight.getOrigin();
    }
    itinerary.add(flight);
    totalCost += Double.valueOf(flight.getCost());
    finalDestination = flight.getDestination();
  }

  /**
   * Travel time of this itinerary given the total flights. It uses millisecond system and subtract
   * departTime and arrivalTime to get the travelTime and return it.
   *
   * @return travelTime the travel time of this Itinerary.
   */
  public double getTotalTravelTime() {
    double travelTime = 0;
    if (getTotalFlights() == 0) {
      return 0;
    } else {
      try {
        String departTime = itinerary.get(0).getDepartureTime();
        String arrivalTime = itinerary.get(getTotalFlights() - 1).getArrivalTime();
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start = timeFormat.parse(departTime);
        Date end = timeFormat.parse(arrivalTime);
        travelTime = end.getTime() - start.getTime();
        return ((travelTime / 1000) / 60);
      } catch (ParseException e) {
        return 0;
      }
    }
  }

  /**
   * Returns total cost of this Itinerary.
   *
   * @return totalCost total cost of this Itinerary.
   */
  public double getTotalCost() {
    return totalCost;
  }

  /**
   * Returns a Flight from this Itinerary at the given index.
   *
   * @param index index in this ArrayList.
   * @return itinerary at a index index.
   */
  public Flight getFlight(int index) {
    return itinerary.get(index);
  }

  /**
   * Returns final destination of this Itinerary.
   *
   * @return final destination of this Itinerary.
   */
  public String getFinalDestination() {
    return finalDestination;
  }

  /**
   * Returns origin of this Itinerary.
   *
   * @return first origin of this Itinerary.
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * Returns amount of flights in this itinerary.
   *
   * @return size of this itinerary, i.e., how many flights.
   */
  public int getTotalFlights() {
    return itinerary.size();
  }

  /**
   * Returns an ArrayList of Flight.
   *
   * @return this Itinerary.
   */
  public ArrayList<Flight> getFlights() {
    return itinerary;
  }

  /**
   * Returns a copy of this Itinerary.
   *
   * @return clone of this itinerary.
   * @throws ParseException when this specified Flight's date is incorrect.
   */
  public Itinerary getCopy() throws ParseException {
    Itinerary copy = new Itinerary();
    for (Flight tmp : getFlights()) {
      copy.addFlight(tmp);
    }
    return copy;
  }

  /**
   * Checks if an Itinerary is empty or not.
   *
   * @return true if this Itinerary is empty, otherwise false.
   */
  public boolean isEmpty() {
    if (itinerary.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    String output = "";
    for (int i = 0; i < getTotalFlights(); i++) {
      output += getFlight(i).getNoPrice();
      if (i < getTotalFlights()) {
        output += "\n";
      }
    }
    output += String.format("%.2f\n", getTotalCost());
    output += String.format("%02d:%02d", (int) (getTotalTravelTime() / 60),
            (int) (getTotalTravelTime() % 60));
    return output;
  }

}
