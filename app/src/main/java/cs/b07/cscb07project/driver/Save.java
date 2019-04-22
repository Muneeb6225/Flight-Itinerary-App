package cs.b07.cscb07project.driver;

/**
 * Saves information from a given object to a txt file.
 */

import cs.b07.cscb07project.users.Client;
import cs.b07.cscb07project.users.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;


public class Save {

  /**
   * Saves information from a given object to an object file.
   *
   * @param saveThis is object to save into this file.
   * @param filePath location of a file to store to.
   */
  public static void save(Object saveThis, String filePath) {
    try {
      FileOutputStream fos = new FileOutputStream(filePath);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(saveThis);
      oos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Saves this Client into database.
   * @param allUser all the clients in database
   * @param client this Client to be added
   * @param filePath the path where this Client is saved
   */
  public static void saveClient(Map<String, User> allUser, Client client, String filePath) {
    Client currentUser = client;
    Map<String, User> allUsers = allUser;
    allUser.put(currentUser.getEmail(), currentUser);
    save(allUser, filePath);
  }

}

