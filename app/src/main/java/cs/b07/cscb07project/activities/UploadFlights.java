package cs.b07.cscb07project.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import cs.b07.cscb07project.R;
import cs.b07.cscb07project.driver.Load;
import cs.b07.cscb07project.driver.Save;
import cs.b07.cscb07project.flights.ArrayFlight;

import java.io.File;
import java.io.IOException;


public class UploadFlights extends AppCompatActivity {

  private String flightsFile;
  private String fileLocation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_upload_flights);
    setTitle(R.string.upload_flights);

    Intent intent = getIntent();
    flightsFile = intent.getStringExtra("allFlightsFile");
    fileLocation = intent.getStringExtra("fileLocation");
  }

  /**
   * Saves the flights given a file of flights.
   * @param view defines the layout
   */
  public void uploadFlights(View view) {
    String fileName = ((EditText) findViewById(R.id.flight_file_location)).getText().toString();

    if (new File(fileLocation + "/" + fileName).exists()) {
      try {
        ArrayFlight newFlights = Load.loadFlight(fileLocation + "/" + fileName);
        ArrayFlight oldFligths = Load.loadInternalFlights(fileLocation + "/" + flightsFile);
        oldFligths.putAll(newFlights);
        Save.save(oldFligths, fileLocation + "/" + flightsFile);

        new AlertDialog.Builder(this).setCancelable(false)
            .setTitle(getResources().getString(R.string.upload_flight_success_title))
            .setMessage(getResources().getString(R.string.upload_flight_success_message))
            .setPositiveButton(getResources().getString(R.string.confirm),
                new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    finish();
                  }
                })
            .create().show();
      } catch (IOException e) {
        // An error occured during Flight creation, make sure the file is correct
        new AlertDialog.Builder(this).setCancelable(false)
            .setTitle(getResources().getString(R.string.upload_flight_file_read_error_title))
            .setMessage(getResources().getString(R.string.upload_flight_file_read_error_message))
            .setPositiveButton(getResources().getString(R.string.confirm),
                new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    ((EditText) findViewById(R.id.flight_file_location)).setText("");
                  }
                })
            .create().show();
      }
      // File not found, display error message.
    } else {
      new AlertDialog.Builder(this).setCancelable(false)
          .setTitle(getResources().getString(R.string.upload_flight_file_not_found_title))
          .setMessage(getResources().getString(R.string.upload_flight_file_not_found_message))
          .setPositiveButton(getResources().getString(R.string.confirm),
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  ((EditText) findViewById(R.id.flight_file_location)).setText("");
                }
              })
          .create().show();
    }
  }
}
