package cs.b07.cscb07project.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.driver.Load;
import cs.b07.cscb07project.flights.ArrayFlight;
import cs.b07.cscb07project.flights.Flight;

public class SearchFlight extends Activity {

  private String flightFileName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_flight);
    setTitle(R.string.flight_search);
  }

  /**
   * Searches the flight once the button is pressed.
   * @param view defines the layout
   */
  public void searchThatFlight(View view) {
    Intent intent = getIntent();
    flightFileName = intent.getStringExtra("flightFileLocation");

    String originText = ((EditText) findViewById(R.id.searchOrigin)).getText().toString();
    ArrayFlight resultFlight;

    String destinationText =
        ((EditText) findViewById(R.id.searchDestination)).getText().toString();
    String dateText = ((EditText) findViewById(R.id.searchDate)).getText().toString();
    ArrayFlight allFlights = Load.loadInternalFlights(flightFileName);
    resultFlight = allFlights.originDestination(dateText, originText, destinationText);

    Intent searchThat = new Intent(this, FlightResult.class);
    searchThat.putExtra("resultFlight", resultFlight);
    startActivity(searchThat);
  }
}
