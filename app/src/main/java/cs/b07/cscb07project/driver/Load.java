package cs.b07.cscb07project.driver;


import cs.b07.cscb07project.flights.ArrayFlight;
import cs.b07.cscb07project.flights.Flight;
import cs.b07.cscb07project.users.Admin;
import cs.b07.cscb07project.users.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;


public class Load {

  /**
   * Return a Map of Admins with their email mapped to Admin.
   * @param adminPath path of admin file.
   * @param pwdPath path of password file.
   * @return a map of Admins with their emails mapped to Admin.
   * @throws IOException when the file is not found.
   */

  public static Map<String, Admin> loadAdmin(String adminPath, String pwdPath) throws IOException {
    Map<String, Admin> allAdmins = new HashMap<String, Admin>();
    Map<String, String> allPasswords = new HashMap<String, String>();

    int lineNumber = 0;
    String line;

    if (new File(pwdPath).exists()) {
      File passwordtxt = new File(pwdPath);
      FileReader pwdInfo = new FileReader(passwordtxt);
      BufferedReader pwdRead = new BufferedReader(pwdInfo);

      while ((line = pwdRead.readLine()) != null) {
        lineNumber++;
        String[] pwdArray = line.split(", ");
        allPasswords.put(pwdArray[0], pwdArray[1]);
      }
      pwdRead.close();
      lineNumber = 0;
    }

    File admintxt = new File(adminPath);
    FileReader adminInfo = new FileReader(admintxt);
    BufferedReader adminRead = new BufferedReader(adminInfo);

    while ((line = adminRead.readLine()) != null) {
      lineNumber++;
      String[] adminArray = line.split(", ");
      Admin admin = new Admin(adminArray[0], adminArray[1], adminArray[2]);
      if (allPasswords.containsKey(adminArray[2])) {
        admin.setPassword(allPasswords.get(adminArray[2]));
      }
      allAdmins.put(adminArray[2], admin);
    }

    adminRead.close();

    return allAdmins;
  }

  /**
   * Return a Map of Clients with their email mapped to Client from given file.
   *
   * @param clientPath path of this txt file for clients.
   * @param pwdPath path of this txt file for passwords.
   * @return all this stored Clients.
   * @throws IOException File does not exist.
   *
   *         We use client's email, which is unique for each user to map its info.
   */
  public static Map<String, Client> loadClient(String clientPath, String pwdPath)
          throws IOException {
    Map<String, Client> allClients = new HashMap<String, Client>();
    Map<String, String> allPasswords = new HashMap<String, String>();

    int lineNumber = 0;
    String line;

    if (new File(pwdPath).exists()) {
      File passwordtxt = new File(pwdPath);
      FileReader pwdInfo = new FileReader(passwordtxt);
      BufferedReader pwdRead = new BufferedReader(pwdInfo);

      while ((line = pwdRead.readLine()) != null) {
        lineNumber++;
        String[] pwdArray = line.split(", ");
        allPasswords.put(pwdArray[0], pwdArray[1]);
      }
      pwdRead.close();
      lineNumber = 0;
    }

    File clienttxt = new File(clientPath);
    FileReader clientInfo = new FileReader(clienttxt);
    BufferedReader clientRead = new BufferedReader(clientInfo);

    while ((line = clientRead.readLine()) != null) {
      lineNumber += 1;
      // Split the line to make it an array
      String[] array = line.split(",");
      // Make a new user with the properties of the line
      Client client = new Client(array[0], array[1], array[2], array[3], array[4], array[5],
              lineNumber);
      if (allPasswords.containsKey(array[2])) {
        client.setPassword(allPasswords.get(array[2]));
      }
      // Add the user to the list of users
      allClients.put(array[2], client);
    }
    clientRead.close();
    return allClients;
  }

  /**
   * Returns an ArrayFlight of all the Flight given file.
   *
   * @param path path of this txt file.
   * @return all this stored Clients.
   * @throws IOException File does not exist.
   *
   *
   */
  public static ArrayFlight loadFlight(String path) throws IOException {
    ArrayFlight flights = new ArrayFlight();
    File flighttxt = new File(path);
    FileReader info = new FileReader(flighttxt);
    BufferedReader read = new BufferedReader(info);

    // Not sure if arrayList or normal string output
    String line;
    while ((line = read.readLine()) != null) {
      // Split the line at the comma
      String[] array = line.split(",");
      // Make a new flight and send it into the flights object
      Flight flight = new Flight(array[0], array[1], array[2], array[3], array[4], array[5],
              array[6], array[7]);
      // Add the flight into the arrayFlight
      flights.add(flight);
    }
    read.close();
    return flights;
  }

  /**
   * Returns an ArrayFlight of all Flights from an object file.
   * Returns an empty ArrayFlight if specified file does not exist.
   *
   * @param fileName this name of an object file.
   * @return internalflights: all Flights present in database.
   */
  @SuppressWarnings({"resource"})
  public static ArrayFlight loadInternalFlights(String fileName) {

    String filePath = fileName;

    ArrayFlight flights = new ArrayFlight();
    try {
      // Loading all files as objects. If file doesn't exist, return an empty object.
      FileInputStream fis = new FileInputStream(filePath);
      ObjectInputStream ois = new ObjectInputStream(fis);

      flights = (ArrayFlight) ois.readObject();
    } catch (FileNotFoundException e) {
      return flights;
    } catch (IOException e) {
      return flights;
    } catch (ClassNotFoundException e) {
      return flights;
    }
    return flights;
  }

  /**
   * Returns a Map of all User mapped to their email. Returns an empty Map if
   * specified file does not exist.
   *
   * @param fileName this name of an object file.
   * @return all User present in database.
   */
  @SuppressWarnings({"unchecked", "resource"})
  public static Map<String, Client> loadInternalClients(String fileName) {

    String filePath = fileName;
    Map<String, Client> clients = new HashMap<String, Client>();
    try {
      FileInputStream fis = new FileInputStream(filePath);
      ObjectInputStream ois = new ObjectInputStream(fis);
      clients = (Map<String, Client>) ois.readObject();
    } catch (FileNotFoundException e) {
      return clients;
    } catch (IOException e) {
      return clients;
    } catch (ClassNotFoundException e) {
      return clients;
    }
    return clients;
  }

  /**
   *
   * @param fileName this name of an object file.
   * @return  all Admin present in database.
   */
  public static Map<String, Admin> loadInternalAdmins(String fileName) {
    String filePath = fileName;
    Map<String, Admin> admins = new HashMap<String, Admin>();
    try {
      FileInputStream fis = new FileInputStream(filePath);
      ObjectInputStream ois = new ObjectInputStream(fis);
      admins = (Map<String, Admin>) ois.readObject();
    } catch (FileNotFoundException e) {
      return admins;
    } catch (IOException e) {
      return admins;
    } catch (ClassNotFoundException e) {
      return admins;
    }
    return admins;
  }

}
