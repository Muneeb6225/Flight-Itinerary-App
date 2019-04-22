package cs.b07.cscb07project.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cs.b07.cscb07project.R;
import cs.b07.cscb07project.driver.Load;
import cs.b07.cscb07project.driver.Save;
import cs.b07.cscb07project.flights.ArrayFlight;
import cs.b07.cscb07project.flights.ArrayItinerary;
import cs.b07.cscb07project.flights.Flight;
import cs.b07.cscb07project.flights.Itinerary;
import cs.b07.cscb07project.users.Admin;
import cs.b07.cscb07project.users.Client;
import cs.b07.cscb07project.users.User;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;


public class ViewItineraries extends AppCompatActivity {

  private User currentUser;
  private String clientFile;
  private String adminFile;
  private String flightFile;
  private String fileLocation;
  private ArrayFlight allFlights;
  private ArrayItinerary allItinerary;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_itineraries);
    setTitle(R.string.itineraries_result);

    Intent intent = getIntent();
    currentUser = (User) intent.getSerializableExtra("currentUser");
    clientFile = intent.getStringExtra("clientFile");
    adminFile = intent.getStringExtra("adminFile");
    flightFile = intent.getStringExtra("flightFile");
    fileLocation = intent.getStringExtra("fileLocation");

    allFlights = Load.loadInternalFlights(fileLocation + "/" + flightFile);

    allItinerary = (ArrayItinerary) intent.getSerializableExtra("searchResult");

    if (allItinerary.getSize() > 0) {

      // Show buttons and text
      findViewById(R.id.itinerary_tip).setVisibility(View.VISIBLE);
      findViewById(R.id.sortButtons).setVisibility(View.VISIBLE);

      displayItinerary(null);
    } else {
      // Display no results
      TextView noResult = new TextView(this);
      noResult.setText(this.getString(R.string.no_result));
      LinearLayout parentLayout = ((LinearLayout) findViewById(R.id.search_flights_layout));
      parentLayout.addView(noResult);
    }
  }


  /**
   * Sort the result by the total cost of each Itinerary and display it.
   * 
   * @param view defines the layout.
   */
  public void displayByCost(View view) {
    LinearLayout parentLayout = ((LinearLayout) findViewById(R.id.search_flights_layout));
    parentLayout.removeAllViews();
    allItinerary.sortByCost();
    displayItinerary(null);
  }

  /**
   * Sort the result by the total time of each Itinerary and display it.
   * 
   * @param view defines the layout.
   */
  public void displayByTime(View view) {
    LinearLayout parentLayout = ((LinearLayout) findViewById(R.id.search_flights_layout));
    parentLayout.removeAllViews();
    allItinerary.sortByTime();
    displayItinerary(null);
  }


  private void displayItinerary(View view) {

    // Obtain the strings in xml file
    String arrivalText = this.getString(R.string.flight_arrival_date);
    String departureText = this.getString(R.string.flight_departure_date);
    String totalTimeText = this.getString(R.string.flight_total_travel_time);
    String totalTimeText2 = this.getString(R.string.flight_total_travel_time_2);
    String seatsRemaining = this.getString(R.string.flight_seats_remaining);
    String moneySymbol = this.getString(R.string.money_symbol);

    // Layout ID for the main one in the XML file
    LinearLayout parentLayout = ((LinearLayout) findViewById(R.id.search_flights_layout));

    int itineraryNumber = 1;

    // Layouts

    LinearLayout.LayoutParams linearWrapLayoutParam = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams mainLinearWrapLayoutParam = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    mainLinearWrapLayoutParam.setMargins(0, 50, 0, 50);

    LinearLayout.LayoutParams underLinePayoutParam = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    underLinePayoutParam.height = 10;


    for (int i = 0; i < allItinerary.getSize(); i++) {
      final Itinerary currentItinerary = allItinerary.getItinerary(i);

      // Making a new LinearLayout of this Itinerary
      LinearLayout displayLayout = new LinearLayout(this);
      displayLayout.setLayoutParams(mainLinearWrapLayoutParam);

      displayLayout.setOrientation(LinearLayout.VERTICAL);
      displayLayout.setPadding(20, 20, 20, 20);

      LinearLayout numItinerary = new LinearLayout(this);
      numItinerary.setLayoutParams(linearWrapLayoutParam);
      numItinerary.setOrientation(LinearLayout.HORIZONTAL);

      String itineraryNum = Integer.toString(itineraryNumber);
      TextView itineraryNumView = new TextView(this);
      itineraryNumView.setText(itineraryNum);
      itineraryNumView.setTextSize((int) getResources().getDimension(R.dimen.itinerary_number));
      itineraryNumView.setPadding(0, 0, 20, 0);
      int thisItineraryColor = randomColour();
      itineraryNumView.setTextColor(thisItineraryColor);

      String itineraryTotalCost = String.valueOf(moneySymbol + String.format("%.2f",
              new BigDecimal(currentItinerary.getTotalCost())));
      TextView totalCostView = new TextView(this);
      totalCostView.setText(itineraryTotalCost);
      totalCostView.setTextSize((int) getResources().getDimension(R.dimen.itinerary_cost));

      numItinerary.addView(itineraryNumView);
      numItinerary.addView(totalCostView);

      LinearLayout underLineLayout = new LinearLayout(this);
      underLineLayout.setLayoutParams(underLinePayoutParam);
      underLineLayout.setBackgroundColor(thisItineraryColor);

      displayLayout.addView(numItinerary);
      displayLayout.addView(underLineLayout);

      // Looping Flights in this Itinerary
      for (int j = 0; j < currentItinerary.getTotalFlights(); j++) {
        Flight currentFlight = currentItinerary.getFlight(j);

        TextView flightNumView = new TextView(this);

        // Flight Number and Airline
        LinearLayout numAirlineLayout = new LinearLayout(this);
        numAirlineLayout.setLayoutParams(linearWrapLayoutParam);
        numAirlineLayout.setOrientation(LinearLayout.HORIZONTAL);

        flightNumView.setText(currentFlight.getFlightNum());
        flightNumView
            .setTextSize((int) getResources().getDimension(R.dimen.itinerary_flight_number));
        flightNumView.setPadding(0, 0, 20, 0);
        int thisFlightColor = randomColour();
        flightNumView.setTextColor(thisFlightColor);

        TextView flightAirlineView = new TextView(this);
        flightAirlineView.setText(currentFlight.getAirline());
        flightAirlineView
            .setTextSize((int) getResources().getDimension(R.dimen.itinerary_flight_airline));
        flightAirlineView.setLayoutParams(linearWrapLayoutParam);

        numAirlineLayout.addView(flightNumView);
        numAirlineLayout.addView(flightAirlineView);

        displayLayout.addView(numAirlineLayout);

        // Origin and Destination
        RelativeLayout oriDestLayout = new RelativeLayout(this);
        oriDestLayout.setLayoutParams(new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        TextView flightTravelView = new TextView(this);
        String flightOriginDestination =
            currentFlight.getOrigin() + " - " + currentFlight.getDestination();
        flightTravelView.setText(flightOriginDestination);
        flightTravelView.setLayoutParams(new LinearLayout.LayoutParams(linearWrapLayoutParam));
        flightTravelView
            .setTextSize((int) getResources().getDimension(R.dimen.itinerary_flight_travel));
        flightTravelView.setSingleLine(true);

        RelativeLayout.LayoutParams oriDestLayoutParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        oriDestLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        oriDestLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        flightTravelView.setLayoutParams(oriDestLayoutParams);

        oriDestLayout.addView(flightTravelView);

        displayLayout.addView(oriDestLayout);

        TextView flightDepartureView = new TextView(this);
        String flightDeparture = departureText + " " + currentFlight.getDepartureTime();
        flightDepartureView.setText(flightDeparture);
        TextView flightArrivalView = new TextView(this);
        String flightArrival = arrivalText + " " + currentFlight.getArrivalTime();
        flightArrivalView.setText(flightArrival);
        TextView flightSeatView = new TextView(this);
        String flightNumSeats = String.valueOf(currentFlight.getNumSeats()) + " " + seatsRemaining;
        flightSeatView.setText(flightNumSeats);


        displayLayout.addView(flightDepartureView);
        displayLayout.addView(flightArrivalView);
        displayLayout.addView(flightSeatView);

      }

      // Total Time
      String itineraryTotalTime = String.valueOf(
          totalTimeText + " " + currentItinerary.getTotalTravelTime() + " " + totalTimeText2);
      TextView totalTimeView = new TextView(this);
      totalTimeView.setText(itineraryTotalTime);
      totalTimeView.setGravity(Gravity.END);
      totalTimeView.setTextSize((int) getResources().getDimension(R.dimen.itinerary_time));
      totalTimeView.setTextColor(getResources().getColor(R.color.colorPrimary));
      displayLayout.addView(totalTimeView);

      LinearLayout underLineLayout2 = new LinearLayout(this);
      underLineLayout2.setLayoutParams(underLinePayoutParam);
      underLineLayout2.setBackgroundColor(thisItineraryColor);

      displayLayout.addView(underLineLayout2);

      if (currentUser.getClass() != Admin.class) {
        displayLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            new AlertDialog.Builder(ViewItineraries.this).setCancelable(true)
                .setTitle(getResources().getString(R.string.book_title))
                .setMessage(getResources().getString(R.string.book_message))
                // If User wants to book this Itinerary
                .setPositiveButton(getResources().getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                        // Show alert for successful book and failed bookings.
                        if (checkFlightAvailability(currentItinerary)) {
                          ((Client) currentUser).bookFlight(currentItinerary);
                          adjustFlights(currentItinerary);
                          saveUser();
                          showSuccessAlert();
                        } else {
                          showFailureAlert();
                        }
                      }
                    })
                // If user doesn't want to book, then do nothing.
                .setNegativeButton(getResources().getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {}
                    }).create().show();
          }
        });
      }
      parentLayout.addView(displayLayout);
      itineraryNumber++;
    }
  }

  /**
   * Save current user to database along with the itinerary they booked.
   */
  private void saveUser() {
    if (currentUser.getClass() != Admin.class) {
      Map<String, Client> allClients = Load.loadInternalClients(fileLocation + "/" + clientFile);
      allClients.put(currentUser.getEmail(), (Client) currentUser);
      Save.save(allClients, fileLocation + "/" + clientFile);
    }
  }

  /**
   * Save all flights with the corresponding seat decreases.
   * 
   * @param saveFlight flights to be saved
   */
  private void saveFlights(ArrayFlight saveFlight) {
    Save.save(saveFlight, fileLocation + "/" + flightFile);
  }

  /**
   * Prompt a book success alert for the Itinerary.
   */
  private void showSuccessAlert() {
    new AlertDialog.Builder(this).setCancelable(true)
        .setTitle(getResources().getString(R.string.book_success_title))
        .setMessage(getResources().getString(R.string.book_success_message))
        .setPositiveButton(getResources().getString(R.string.confirm),
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                finish();
              }
            })
        .create().show();
  }

  /**
   * Prompt a book failure alert for the Itinerary.
   */
  private void showFailureAlert() {
    new AlertDialog.Builder(this).setCancelable(true)
        .setTitle(getResources().getString(R.string.book_failed_title))
        .setMessage(getResources().getString(R.string.book_failed_message))
        .setPositiveButton(getResources().getString(R.string.confirm),
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {}
            })
        .create().show();
  }

  /**
   * Return true if each flight in this Itinerary has more than 1 seat.
   * 
   * @param bookedItinerary this Itinerary to be checked
   * @return the availability of this Itinerary
   */
  private boolean checkFlightAvailability(Itinerary bookedItinerary) {
    boolean available = true;
    for (int i = 0; i < bookedItinerary.getTotalFlights(); i++) {
      if (bookedItinerary.getFlight(i).getNumSeats() == 0) {
        available = false;
      }
    }
    return available;
  }

  /**
   * Lower each Flight's number of seats by 1 in this Itinerary.
   * 
   * @param bookedItinerary this Itinerary's Flights to have seats lowered.
   */
  private void adjustFlights(Itinerary bookedItinerary) {
    for (int i = 0; i < bookedItinerary.getTotalFlights(); i++) {
      // Get the flight number of the current flight, then get the Flight from all the flights
      // so we can modify it.
      allFlights.getFlight(bookedItinerary.getFlight(i).getFlightNum()).lowerNumSeats();
    }
    saveFlights(allFlights);
  }

  /**
   * Generate a random colour.
   * 
   * @return a new random colour
   */
  private int randomColour() {
    Random rndColor = new Random();
    return Color.rgb(rndColor.nextInt(250), rndColor.nextInt(250), rndColor.nextInt(250));
  }
}
