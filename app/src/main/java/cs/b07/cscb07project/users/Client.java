package cs.b07.cscb07project.users;

import android.util.Log;
import cs.b07.cscb07project.flights.ArrayItinerary;
import cs.b07.cscb07project.flights.Itinerary;

import java.io.Serializable;

/**
 * A client User.
 */
public class Client extends User implements Serializable {

  private static final long serialVersionUID = -1176091151140880770L;
  private String address;
  private String ccNumber;
  private String ccDate;
  protected int fileLineIndex;
  private ArrayItinerary bookedFlights;

  /**
   * Create a new Client user with the given name and email.
   * @param lastName last name of this Client
   * @param firstName first name of this Client
   * @param email email of this Client
   * @param address address of this Client
   * @param ccNumber credit card number of this Client
   * @param ccDate cred card expiry date of this Client
   * @param fileLineIndex file line of this Client
   */
  public Client(String lastName, String firstName, String email, String address, String ccNumber,
              String ccDate, int fileLineIndex) {
    super(lastName, firstName, email);
    this.address = address;
    this.ccNumber = ccNumber;
    this.ccDate = ccDate;
    this.fileLineIndex = fileLineIndex;
    this.bookedFlights = new ArrayItinerary();
  }

  @Override
  public String toString() {
    String clientInfo = super.getLastName() + "," + super.getFirstName() + ","
            + super.getEmail() + "," + address + "," + ccNumber + "," + ccDate;
    return clientInfo;
  }

  /**
   * Returns this User's address.
   *
   * @return address of this User.
   */
  public String getAddress() {
    return address;
  }

  /**
   * Returns this User's Credit Card number.
   *
   * @return ccNumber of this User.
   */
  public String getccNumber() {
    return ccNumber;
  }

  /**
   * Returns this User's Credit Card expiry Date.
   *
   * @return ccDate of this User.
   */
  public String getccDate() {
    return ccDate;
  }

  /**
   * Returns this User's position in the file.
   *
   * @return this user's position in the file.
   */
  public int getFileLineIndex() {
    return fileLineIndex;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setCcNumber(String ccNumber) {
    this.ccNumber = ccNumber;
  }

  public void setCcDate(String ccDate) {
    this.ccDate = ccDate;
  }

  /**
   * Returns booked flights of this user.
   *
   * @return booked flights of this user.
   */
  public ArrayItinerary getBookedFlights() {
    return bookedFlights;
  }

  /**
   * Book a specified Itinerary.
   *
   * @param booked a Itinerary to be booked.
   */
  public void bookFlight(Itinerary booked) {
    bookedFlights.addItinerary(booked);
  }
}
