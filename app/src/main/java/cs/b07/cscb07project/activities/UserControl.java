package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import cs.b07.cscb07project.R;
import cs.b07.cscb07project.driver.Load;
import cs.b07.cscb07project.driver.Save;
import cs.b07.cscb07project.flights.ArrayFlight;
import cs.b07.cscb07project.users.Admin;
import cs.b07.cscb07project.users.Client;
import cs.b07.cscb07project.users.User;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;


public class UserControl extends AppCompatActivity {

  // Internal Files
  private static final String USER_FILENAME = "all_users.p2s";
  private static final String ADMIN_FILENAME = "all_admins.p2s";
  private static final String FLIGHT_FILENAME = "all_flights.p2s";

  // External Files
  private static final String CLIENT_TXT_FILE = "clients.txt";
  private static final String ADMIN_TXT_FILE = "admin.txt";
  private static final String PASSWORD_TXT_FILE = "password.txt";
  private static final String FLIGHT1_TXT_FILE = "flights1.txt";
  private static final String FLIGHT2_TXT_FILE = "flights2.txt";

  private String fileLocation;

  boolean isLoggedIn;
  boolean isAdmin;
  private User currentUser;
  private Map<String, Client> allClients;
  private Map<String, Admin> allAdmins;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_control);
    setTitle(R.string.user_control_page);
    isLoggedIn = false;

    // App File Storage Location
    fileLocation = this.getApplicationContext().getFilesDir().toString();

    // Load All Users and Flights
    allClients = loadClients();
    allAdmins = loadAdmin();
    loadFlights();

    Intent intent = getIntent();
    String email = intent.getStringExtra("email");
    String password = intent.getStringExtra("password");

    // TextView for login text to display
    TextView loginPrompt = (TextView) findViewById(R.id.welcome_user_text);
    isAdmin = false;
    // User exists or does not exist and show appropriate messages
    if (allClients.containsKey(email) || allAdmins.containsKey(email)) {
      if (allAdmins.containsKey(email)) {
        isAdmin = true;
        currentUser = allAdmins.get(email);
      } else {
        currentUser = allClients.get(email);
      }
      if (currentUser.getPassword().equals(password)) {
        isLoggedIn = true;

        String welcomeText = getResources().getString(R.string.welcome_user) + " "
            + currentUser.getFirstName() + " " + currentUser.getLastName() + "!";
        loginPrompt.setText(welcomeText);

        // Display Buttons
        findViewById(R.id.view_all_flights_button).setVisibility(View.VISIBLE);
        findViewById(R.id.search_itinerary_button).setVisibility(View.VISIBLE);

        if (!isAdmin) {
          findViewById(R.id.view_booked_flights_button).setVisibility(View.VISIBLE);
          findViewById(R.id.view_personal_info_button).setVisibility(View.VISIBLE);
        } else {
          // Display Admin Control here
          findViewById(R.id.admin_mode_text).setVisibility(View.VISIBLE);
          findViewById(R.id.enter_user_text).setVisibility(View.VISIBLE);
          findViewById(R.id.into_client).setVisibility(View.VISIBLE);
          findViewById(R.id.search_flight_to_edit_button).setVisibility(View.VISIBLE);
          findViewById(R.id.upload_flights_button).setVisibility(View.VISIBLE);
          findViewById(R.id.upload_clients_button).setVisibility(View.VISIBLE);
        }
      } else {
        // Wrong password
        loginPrompt.setText(R.string.login_error);
      }
    } else {
      // No such user in database
      loginPrompt.setText(R.string.login_error);
    }
  }

  /**
   * View user's booked Itineraries.
   *
   * @param view defines the layout
   */
  public void viewBookedFlights(View view) {
    Intent intent = new Intent(this, AllBookedFlights.class);
    intent.putExtra("user", currentUser);
    startActivity(intent);
  }

  /**
   * Search all the flights in the database.
   * 
   * @param view Represents the view
   */
  public void searchFlights(View view) {
    Intent intent = new Intent(this, SearchFlight.class);
    intent.putExtra("flightFileLocation", fileLocation + "/" + FLIGHT_FILENAME);
    startActivity(intent);
  }

  /**
   * User can edit personal information.
   * @param view Represents the view
   */
  public void editPersonalInfo(View view) {
    Intent intent = new Intent(this, PersonalInfo.class);
    intent.putExtra("currentUser", currentUser);
    intent.putExtra("allTheUser", (Serializable) allClients);
    intent.putExtra("fileLocation", fileLocation + "/" + USER_FILENAME);
    startActivity(intent);
  }

  /**
   * Loads the admin from the file given.
   * @return a map of admins loaded from the file
   */
  public Map<String, Admin> loadAdmin() {
    Map<String, Admin> allAdmins = Load.loadInternalAdmins(fileLocation + "/" + ADMIN_FILENAME);
    if (allAdmins.isEmpty()) {
      try {
        allAdmins = Load.loadAdmin(fileLocation + "/" + ADMIN_TXT_FILE,
            fileLocation + "/" + PASSWORD_TXT_FILE);
        Save.save(allAdmins, fileLocation + "/" + ADMIN_FILENAME);
      } catch (IOException e) {
        Log.w("UTravel", "Admin+Password not found.");
      }
    }
    return allAdmins;
  }

  /**
   * Loads the clients from our internal storage.
   * @return a map of clients loaded from the file
   */
  public Map<String, Client> loadClients() {
    // Checking if database exist
    Map<String, Client> allClients = Load.loadInternalClients(fileLocation + "/" + USER_FILENAME);
    if (allClients.isEmpty()) {
      try {
        // Load with txt file
        allClients = Load.loadClient(fileLocation + "/" + CLIENT_TXT_FILE,
            fileLocation + "/" + PASSWORD_TXT_FILE);
        Save.save(allClients, fileLocation + "/" + USER_FILENAME);
      } catch (IOException e) {
        Log.w("UTravel", "Client+Password not found.");
      }
    }
    return allClients;
  }

  /**
   * Load flights stored in our internal storage.
   */
  public void loadFlights() {
    ArrayFlight allFlights = Load.loadInternalFlights(fileLocation + "/" + FLIGHT_FILENAME);
    if (allFlights.isEmpty()) {
      try {
        ArrayFlight newFlights = new ArrayFlight();
        ArrayFlight oldFligths = new ArrayFlight();
        if ((new File(fileLocation + "/" + FLIGHT1_TXT_FILE).exists())) {
          oldFligths = Load.loadFlight(fileLocation + "/" + FLIGHT1_TXT_FILE);
        }
        if ((new File(fileLocation + "/" + FLIGHT2_TXT_FILE).exists())) {
          newFlights = Load.loadFlight(fileLocation + "/" + FLIGHT2_TXT_FILE);
        }
        oldFligths.putAll(newFlights);
        Save.save(oldFligths, fileLocation + "/" + FLIGHT_FILENAME);
      } catch (IOException e) {
        Log.w("UTravel", "Flight files not found!");
      }
    }
  }

  /**
   * Search the flights with a flight number, and starts the SearchFlightNum activity.
   * @param view represents the view
   */
  public void searchFlightsToEdit(View view) {
    Intent intent = new Intent(this, SearchFlightNum.class);
    intent.putExtra("flightFileLocation", fileLocation + "/" + FLIGHT_FILENAME);
    startActivity(intent);
  }

  /**
   * Searches the itinerary from the given information and starts the searchItinerary activity.
   * @param view represents the view
   */
  public void searchItineraries(View view) {
    Intent intent = new Intent(this, SearchItineraries.class);
    // Need user to search
    intent.putExtra("user", currentUser);
    intent.putExtra("flightFile", FLIGHT_FILENAME);
    intent.putExtra("clientFile", USER_FILENAME);
    intent.putExtra("adminFile", ADMIN_FILENAME);
    intent.putExtra("fileLocation", fileLocation);
    startActivity(intent);
  }

  /**
   * Logs a client in, and starts AdminEditUserInfo activity.
   * @param view represents a view
   */
  public void enterClient(View view) {
    Intent intent = new Intent(this, AdminEditUserInfo.class);
    intent.putExtra("allClient", (Serializable) allClients);
    startActivity(intent);
  }

  /**
   * Saves a flights file, starts the UploadFlight activity.
   * @param view represents a view.
   */
  public void uploadFlights(View view) {
    Intent intent = new Intent(this, UploadFlights.class);
    intent.putExtra("allFlightsFile", FLIGHT_FILENAME);
    intent.putExtra("fileLocation", fileLocation);
    startActivity(intent);
  }

  /**
   * Uploads a client, and starts UploadClients activity.
   * @param view represents a view.
   */
  public void uploadClients(View view) {
    Intent intent = new Intent(this, UploadClients.class);
    intent.putExtra("allClientsFile", USER_FILENAME);
    intent.putExtra("passwordPath", PASSWORD_TXT_FILE);
    intent.putExtra("fileLocation", fileLocation);
    startActivity(intent);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (isLoggedIn) {
      if (!isAdmin) {
        // Update Client information when he/she comes back
        allClients = loadClients();
        currentUser = allClients.get(currentUser.getEmail());
        TextView loginPrompt = (TextView) findViewById(R.id.welcome_user_text);
        String welcomeText = getResources().getString(R.string.welcome_user) + " "
            + currentUser.getFirstName() + " " + currentUser.getLastName() + "!";
        loginPrompt.setText(welcomeText);
      } else {
        allClients = loadClients();
        allAdmins = loadAdmin();
        currentUser = allAdmins.get(currentUser.getEmail());
      }
    }
  }
}
