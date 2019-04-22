package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cs.b07.cscb07project.R;
import cs.b07.cscb07project.flights.ArrayFlight;
import cs.b07.cscb07project.flights.Flight;

import java.math.BigDecimal;
import java.util.Random;


public class FlightResult extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flight_result);
    setTitle(R.string.flight_search_result);

    String arrivalText = this.getString(R.string.flight_arrival_date);
    String departureText = this.getString(R.string.flight_departure_date);
    String totalTimeText = this.getString(R.string.flight_total_travel_time);
    String totalSeatsLeft = this.getString(R.string.flight_seats_remaining);
    String moneySymbolText = this.getString(R.string.money_symbol);

    Intent intent = getIntent();
    ArrayFlight allFlights = (ArrayFlight) intent.getSerializableExtra("resultFlight");
    // Layout ID for the main one in the XML file
    LinearLayout parentLayout = ((LinearLayout) findViewById(R.id.search_flights_layout));

    if (allFlights.getSize() > 0) {
      // Layouts
      LinearLayout.LayoutParams linearWrapLayoutParam = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

      LinearLayout.LayoutParams mainLinearWrapLayoutParam = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
      mainLinearWrapLayoutParam.setMargins(0, 30, 0, 30);

      LinearLayout.LayoutParams underLinePayoutParam = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
      underLinePayoutParam.height = 10;

      for (int i = 0; i < allFlights.getSize(); i++) {
        // Making a new LinearLayout of this flight
        LinearLayout displayLayout = new LinearLayout(this);
        displayLayout.setLayoutParams(mainLinearWrapLayoutParam);

        displayLayout.setOrientation(LinearLayout.VERTICAL);
        displayLayout.setPadding(20, 20, 20, 20);

        TextView flightNumView = new TextView(this);
        Flight currentFlight = allFlights.getFlight(i);

        // Flight Number and Airline
        LinearLayout numAirlineLayout = new LinearLayout(this);
        numAirlineLayout.setLayoutParams(linearWrapLayoutParam);
        numAirlineLayout.setOrientation(LinearLayout.HORIZONTAL);

        flightNumView.setText(currentFlight.getFlightNum());
        flightNumView.setTextSize((int) getResources().getDimension(R.dimen.flight_num_size));
        flightNumView.setPadding(0, 0, 20, 0);
        Random rndColor = new Random();
        int thisFlightColor =
            Color.rgb(rndColor.nextInt(250), rndColor.nextInt(250), rndColor.nextInt(250));
        flightNumView.setTextColor(thisFlightColor);

        TextView flightAirlineView = new TextView(this);
        flightAirlineView.setText(currentFlight.getAirline());
        flightAirlineView.setLayoutParams(linearWrapLayoutParam);

        numAirlineLayout.addView(flightNumView);
        numAirlineLayout.addView(flightAirlineView);

        LinearLayout underLineLayout = new LinearLayout(this);
        underLineLayout.setLayoutParams(underLinePayoutParam);
        underLineLayout.setBackgroundColor(thisFlightColor);

        displayLayout.addView(numAirlineLayout);
        displayLayout.addView(underLineLayout);

        // Origin and Destination
        RelativeLayout oriDestLayout = new RelativeLayout(this);
        oriDestLayout.setLayoutParams(new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        TextView flightTravelView = new TextView(this);
        String flightOriginDestination =
            currentFlight.getOrigin() + " - " + currentFlight.getDestination();
        flightTravelView.setText(flightOriginDestination);
        flightTravelView.setLayoutParams(new LinearLayout.LayoutParams(linearWrapLayoutParam));
        flightTravelView.setTextSize(20);
        flightTravelView.setSingleLine(true);

        RelativeLayout.LayoutParams oriDestLayoutParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        oriDestLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        oriDestLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        flightTravelView.setLayoutParams(oriDestLayoutParams);

        oriDestLayout.addView(flightTravelView);
        displayLayout.addView(oriDestLayout);

        // Departure and Arrival & Total Time
        LinearLayout depArrLayout = new LinearLayout(this);
        depArrLayout.setLayoutParams(linearWrapLayoutParam);
        depArrLayout.setOrientation(LinearLayout.VERTICAL);

        TextView flightDepartureView = new TextView(this);
        String flightDepartureTime = departureText + " " + currentFlight.getDepartureTime();
        flightDepartureView.setText(flightDepartureTime);
        TextView flightArrivalView = new TextView(this);
        String flightArrivalTime = arrivalText + " " + currentFlight.getArrivalTime();
        flightArrivalView.setText(flightArrivalTime);
        TextView flightTravelTimeView = new TextView(this);
        String flightTotalTime = totalTimeText + " " + currentFlight.getTotalTravelTime();
        flightTravelTimeView.setText(flightTotalTime);
        TextView flightSeatView = new TextView(this);
        String flightTotalSeats = currentFlight.getNumSeats() + " " + totalSeatsLeft;
        flightSeatView.setText(flightTotalSeats);

        depArrLayout.addView(flightDepartureView);
        depArrLayout.addView(flightArrivalView);
        depArrLayout.addView(flightTravelTimeView);
        depArrLayout.addView(flightSeatView);

        displayLayout.addView(depArrLayout);

        TextView flightCostView = new TextView(this);
        String costToString = String.format("%.2f",
                new BigDecimal(Double.toString(currentFlight.getCost())));
        String flightCost = moneySymbolText + costToString;
        flightCostView.setText(flightCost);
        flightCostView.setTextSize(20);
        flightCostView.setTextColor(getResources().getColor(R.color.moneyColor));

        displayLayout.addView(flightCostView);
        parentLayout.addView(displayLayout);
      }
    } else {
      TextView noResult = new TextView(this);
      noResult.setText(this.getString(R.string.no_result));
      parentLayout.addView(noResult);
    }
  }
}
