package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import cs.b07.cscb07project.R;

public class SearchFlightNum extends AppCompatActivity {

  private String fileLocation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_flight_num);
    setTitle(R.string.edit_flight);

    Intent intent = getIntent();
    fileLocation = intent.getStringExtra("flightFileLocation");
  }

  /**
   * Gets the flights given the flight number.
   * @param view defines the layout
   */
  public void getFlight(View view) {
    Intent intent = new Intent(this, FlightResultToEdit.class);

    String flightNum = ((EditText) findViewById(R.id.flight_num_text_field)).getText().toString();
    intent.putExtra("fileLocation", fileLocation);
    intent.putExtra("flightNum", flightNum);

    startActivity(intent);
  }
}
