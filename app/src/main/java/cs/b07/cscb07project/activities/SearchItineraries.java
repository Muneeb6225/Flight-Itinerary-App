package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.driver.Load;
import cs.b07.cscb07project.flights.ArrayFlight;
import cs.b07.cscb07project.flights.ArrayItinerary;
import cs.b07.cscb07project.users.User;

public class SearchItineraries extends AppCompatActivity {

  private User currentUser;
  private ArrayFlight allFlights;
  private String clientFile;
  private String adminFile;
  private String flightFile;
  private String fileLocation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_itineraries);
    setTitle(R.string.search_itineraries);

    Intent intent = getIntent();
    flightFile = intent.getStringExtra("flightFile");
    clientFile = intent.getStringExtra("clientFile");
    adminFile = intent.getStringExtra("adminFile");
    fileLocation = intent.getStringExtra("fileLocation");
    allFlights = Load.loadInternalFlights(fileLocation + "/" + flightFile);
    currentUser = (User) intent.getSerializableExtra("user");
  }

  /**
   * Searches for the itinerary given origin,destination and date by the user.
   * @param view defines the layout
   */
  public void searchItinerary(View view) {
    Intent intent = new Intent(this, ViewItineraries.class);

    String origin = ((EditText) findViewById(R.id.origin_text)).getText().toString();
    String destination = ((EditText) findViewById(R.id.destination_text)).getText().toString();
    String date = ((EditText) findViewById(R.id.date_text)).getText().toString();
    ArrayItinerary result = currentUser.search(date, origin, destination, allFlights);

    intent.putExtra("searchResult", result);
    intent.putExtra("clientFile", clientFile);
    intent.putExtra("adminFile", adminFile);
    intent.putExtra("fileLocation", fileLocation);
    intent.putExtra("currentUser", currentUser);
    intent.putExtra("flightFile", flightFile);

    startActivity(intent);
  }
}
