
package cs.b07.cscb07project.flights;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A City that keeps track of a City's all outgoing Flights.
 */
public class City {

  private Map<String, ArrayList<Flight>> connects;

  /**
   * Creates a new City(All). Since this City is storing more Cities,
   * we'll the container to be City(All), and all the City it's storing to be City(Single).
   */
  public City() {
    connects = new HashMap<String, ArrayList<Flight>>();
  }

  /**
   * Add a City(Single) to this City(All).
   *
   * @param city this City to be added.
   */
  public void addCity(String city) {
    connects.put(city, new ArrayList<Flight>());
  }

  /**
   * Add a Flight to this given City(Single).
   *
   * @param city given City with this Flight to be added.
   * @param flight this flight to be added.
   */
  public void addFlightToCity(String city, Flight flight) {
    connects.get(city).add(flight);
  }

  /**
   * Get total Flights in the given City(Single).
   *
   * @param city the number of this City's outgoing Flights.
   * @return number of this outgoing Flights in this City.
   */
  public int totalFlight(String city) {
    // If city exists, we'll return it, otherwise, it's a 0
    if (connects.containsKey(city)) {
      return connects.get(city).size();
    } else {
      return 0;
    }
  }

  /**
   * Get outgoing Flights in this given City.
   *
   * @param city Flights from this given City to be retrieved.
   * @return all outgoing Flights from this City.
   */
  public ArrayList<Flight> getCity(String city) {
    return connects.get(city);
  }


  /**
   * Checks to see if this City(Single) exists in City(All).
   *
   * @param city this City to be checked.
   * @return if this City exists.
   */
  public boolean contains(String city) {
    return connects.get(city) != null;
  }

  @Override
  public String toString() {
    String output = "";
    for (Entry<String, ArrayList<Flight>> entry : connects.entrySet()) {
      output += entry.getKey() + ": " + entry.getValue() + "\n";
    }
    return output;
  }
}
