package cs.b07.cscb07project.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cs.b07.cscb07project.R;
import cs.b07.cscb07project.driver.Load;
import cs.b07.cscb07project.driver.Save;
import cs.b07.cscb07project.flights.ArrayFlight;
import cs.b07.cscb07project.flights.Flight;

public class FlightResultToEdit extends AppCompatActivity {

  private String fileLocation;
  private String flightNum;
  private ArrayFlight allFlights;
  private Flight foundFlight;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flight_result_to_edit);
    Intent intent = getIntent();
    fileLocation = intent.getStringExtra("fileLocation");
    flightNum = intent.getStringExtra("flightNum");
    setTitle(getResources().getString(R.string.edit_flight_num) + " " + flightNum);

    allFlights = Load.loadInternalFlights(fileLocation);
    foundFlight = allFlights.getFlight(flightNum);
    if (foundFlight != null) {
      ((EditText) findViewById(R.id.flight_airline_field)).setText(foundFlight.getAirline());
      ((EditText) findViewById(R.id.flight_origin_field)).setText(foundFlight.getOrigin());
      ((EditText) findViewById(R.id.flight_destination_field))
              .setText(foundFlight.getDestination());
      ((EditText) findViewById(R.id.flight_departure_field))
              .setText(foundFlight.getDepartureTime());
      ((EditText) findViewById(R.id.flight_arrival_field)).setText(foundFlight.getArrivalTime());
      ((EditText) findViewById(R.id.flight_price_field))
              .setText(String.valueOf(foundFlight.getCost()));
      ((EditText) findViewById(R.id.flight_seats))
              .setText(String.valueOf(foundFlight.getNumSeats()));
    } else {
      // Flight not found
      new AlertDialog.Builder(this).setCancelable(false)
            .setTitle(getResources().getString(R.string.flight_error_title))
            .setMessage(getResources().getString(R.string.flight_error_message) + flightNum + " "
                    + getResources().getString(R.string.flight_error_message_2))
            .setPositiveButton(getResources().getString(R.string.flight_confirm_button),
                  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      finish();
                    }
                  })
          .create().show();
    }
  }

  /**
   * Saves the flights that edited by admin.
   * @param view defines the layout
   */
  public void submitFlight(View view) {
    foundFlight.setAirline(((EditText) findViewById(R.id.flight_airline_field))
            .getText().toString());
    foundFlight.setOrigin(((EditText) findViewById(R.id.flight_origin_field)).getText().toString());
    foundFlight.setDestination(((EditText) findViewById(R.id.flight_destination_field))
            .getText().toString());
    foundFlight.setDepartureTime(((EditText) findViewById(R.id.flight_departure_field))
            .getText().toString());
    foundFlight.setArrivalTime(((EditText) findViewById(R.id.flight_arrival_field))
            .getText().toString());
    foundFlight.setCost(Double.parseDouble((((EditText) findViewById(R.id.flight_price_field))
            .getText().toString())));
    foundFlight.setNumSeats(Integer.parseInt((((EditText) findViewById(R.id.flight_seats))
            .getText().toString())));
    foundFlight.updateTime();
    allFlights.add(foundFlight);
    Save.save(allFlights, fileLocation);
    finish();
  }
}
