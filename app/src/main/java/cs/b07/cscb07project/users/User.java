package cs.b07.cscb07project.users;

import android.util.Log;

import cs.b07.cscb07project.flights.ArrayFlight;
import cs.b07.cscb07project.flights.ArrayItinerary;
import cs.b07.cscb07project.flights.City;
import cs.b07.cscb07project.flights.Flight;
import cs.b07.cscb07project.flights.Itinerary;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A generic User in the database. Can be either a Client or an Admin.
 */

public abstract class User implements Serializable {


  private static final long serialVersionUID = -3390342881526827807L;
  private String lastName;
  private String firstName;
  private String email;
  private String password;


  /**
   * Creates a new User with all its personal information.
   *
   * @param lastName this User's last name.
   * @param firstName this User's first name.
   * @param email this User's email.
   */
  public User(String lastName, String firstName, String email) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.password = "";
  }

  /**
   * Returns an ArrayItinerary by searching all the flights by Date, Origin, and Destination. Must
   * provide all flights to search through.
   *
   *
   * @param date day of departure.
   * @param origin origin of this Itinerary.
   * @param destination destination of this Itinerary.
   * @param allFlights all Flights in this database to search through.
   * @return an ArrayItinerary containing all possible Flights.
   * @throws ParseException when DateFormat does not match given format.
   */
  public ArrayItinerary search(String date, String origin, String destination,
                               ArrayFlight allFlights) {

    ArrayItinerary result = new ArrayItinerary();
    // Contains all the City and their outgoing Flights and Destinations
    City allCity = populateMap(allFlights);
    ArrayList<String> visited = new ArrayList<String>();
    visited.add(origin);
    // If no flights have origin city, then we return an empty ArrayItinerary
    // Once the stuff in else loop is called, we can assume the city exists
    if (allCity.getCity(origin) == null) {
      return result;
    } else {
      try {
        result = deepSearch(date + " 00:00", origin, origin, destination, allCity,
                new ArrayItinerary(), new Itinerary(), visited);
      } catch (ParseException e) {
        // Do nothing
      }
    }
    return result;
  }

  /**
   * Returns an ArrayItinerary containing all the Itinerary from the search, the search will provide
   * the specified parameters and information needed for this deepSearch to do its tasks. It will
   * recursively locate all the City and their outgoing Flights and loop until it eventually finds
   * the destination and return it. Search already made sure the given destination exists so there's
   * no need to worry about it not existing.
   *
   * @param date this date of when last plane will arrive.
   * @param origin this overall origin.
   * @param thisOrigin starting point of this recursive call.
   * @param destination final destination of where this last flight should arrive at.
   * @param allCity all Cities with their outgoing Flights and destinations.
   * @param allItinerary container to store all Itineraries.
   * @param route current Itinerary.
   * @param visited all visited flights in this search.
   * @return an ArrayItinerary containing all the Itinerary from this search.
   * @throws ParseException when date format is not correct.
   */
  private ArrayItinerary deepSearch(String date, String origin,
                                    String thisOrigin, String destination, City allCity,
                                    ArrayItinerary allItinerary, Itinerary route,
                                    ArrayList<String> visited)
          throws ParseException {
    // Make a copy of all the variables for this loop
    ArrayList<String> allVisits = visited;
    ArrayItinerary newAi = allItinerary;
    Itinerary newi = route.getCopy();
    ArrayList<Flight> outGoing = allCity.getCity(thisOrigin);
    for (Flight currentFlight : outGoing) {
      if (!visited.contains(currentFlight.getDestination())
              && !currentFlight.getDestination().equals(origin)) {
        // Add the Itinerary to the ArrayItinerary, this Itinerary is complete
        if (currentFlight.getDestination().equals(destination)) {
          // Copy because the itinerary will continue with the next flight,
          // so we don't want to change the Itinerary
          SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
          Date flightDeparture = timeFormat.parse(currentFlight.getDepartureTime());
          Date searchedTime = timeFormat.parse(date);
          double timeBetween = (((flightDeparture.getTime() - searchedTime
                  .getTime()) / 1000) / 60);
          if (allVisits.size() <= 1) {
            allVisits.add(currentFlight.getDestination());
            if (timeBetween >= 0 && timeBetween <= 1440) {
              Itinerary addThis = newi.getCopy();
              addThis.addFlight(currentFlight);
              newAi.addItinerary(addThis);
            }
          } else {
            if (timeBetween >= 30 && timeBetween <= 1440) {
              allVisits.add(currentFlight.getDestination());
              Itinerary addThis = newi.getCopy();
              addThis.addFlight(currentFlight);
              newAi.addItinerary(addThis);
            }
          }
          allVisits.remove(allVisits.size() - 1);
        } else {
          // If it's the first flight then we do not need to check for the time
          if (allVisits.size() <= 1) {
            if (currentFlight.getDepartureTime().substring(0, 10)
                    .equals(date.substring(0, 10))) {
              allVisits.add(currentFlight.getDestination());
              Itinerary addThis = newi.getCopy();
              addThis.addFlight(currentFlight);
              newAi = deepSearch(currentFlight.getArrivalTime(), origin,
                      currentFlight.getDestination(), destination, allCity, newAi,
                      addThis, allVisits);
            }
            allVisits.remove(allVisits.size() - 1);
          } else {
            // This allows us to add or subtract the dates because we want
            // flights between a 6 hours difference
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date flightDeparture = timeFormat.parse(currentFlight
                    .getDepartureTime());
            Date searchedTime = timeFormat.parse(date);
            double timeBetween = (((flightDeparture.getTime() - searchedTime
                    .getTime()) / 1000) / 60);
            // Time needs to be >30 so it's more than 30 minutes after the
            // flight has landed
            // and <360, so it's between a 6 hour period after landing
            if (allVisits.size() <= 1) {
              if (timeBetween >= 0 && timeBetween <= 360) {
                allVisits.add(currentFlight.getDestination());
                Itinerary addThis = newi.getCopy();
                addThis.addFlight(currentFlight);
                newAi = deepSearch(currentFlight.getArrivalTime(), origin,
                        currentFlight.getDestination(), destination, allCity, newAi,
                        addThis, allVisits);
              }
            } else {
              if (timeBetween >= 30 && timeBetween <= 360) {
                allVisits.add(currentFlight.getDestination());
                Itinerary addThis = newi.getCopy();
                addThis.addFlight(currentFlight);
                newAi = deepSearch(currentFlight.getArrivalTime(), origin,
                        currentFlight.getDestination(), destination, allCity, newAi,
                        addThis, allVisits);
              }
            }
            allVisits.remove(allVisits.size() - 1);
          }
        }
      }
    }
    return newAi;
  }

  /**
   * Creates new City object given all flights in the database.
   *
   * @param allFlights all Flights.
   * @return Cities mapped to their outgoing Flights and destinations.
   */
  private City populateMap(ArrayFlight allFlights) {
    City theWorld = new City();
    for (int i = 0; i < allFlights.getSize(); i++) {
      // For easy access
      String flightOrigin = allFlights.getFlight(i).getOrigin();
      String flightDestination = allFlights.getFlight(i).getDestination();
      // Obtain Origin City and their outgoing Flights
      // If City exists, then we'll add the Flight to the City,
      // if not, we add a new City
      if (!theWorld.contains(flightOrigin)) {
        theWorld.addCity(flightOrigin);
        theWorld.addFlightToCity(flightOrigin, allFlights.getFlight(i));
      } else {
        theWorld.addFlightToCity(flightOrigin, allFlights.getFlight(i));
      }
      // Obtaining Destination Cities
      if (!theWorld.contains(flightDestination)) {
        theWorld.addCity(flightDestination);
      }
    }
    return theWorld;
  }


  /**
   * Returns last name of this User.
   *
   * @return lastName of this User.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Returns this User's first name.
   *
   * @return firstName of this User.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Returns this User's email.
   *
   * @return email of this User.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set a password for this User.
   * @param password for this User to set.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns this User's password.
   * @return this User's password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set this User's first name.
   * @param firstName this User's first name to set.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Set this User's last name.
   * @param lastName this User's last name to set.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
