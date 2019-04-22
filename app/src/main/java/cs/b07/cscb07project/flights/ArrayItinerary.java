package cs.b07.cscb07project.flights;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Array of Itineraries. Can be sorted by both time or cost. Is made of an ArrayList of Itinerary.
 */
public class ArrayItinerary implements Serializable {

  private static final long serialVersionUID = -2486095419315111705L;
  private ArrayList<Itinerary> itinerary;

  /** Initialize a new ArrayItinerary. */
  public ArrayItinerary() {
    this.itinerary = new ArrayList<Itinerary>();
  }

  /**
   * Add a new Itinerary to this ArrayItinerary.
   *
   * @param newItinerary to be added.
   */
  public void addItinerary(Itinerary newItinerary) {
    this.itinerary.add(newItinerary);
  }

  /**
   * Get this Itinerary.
   *
   * @return Itinerary at a specified index.
   */
  public ArrayList<Itinerary> getAllItinerary() {
    return itinerary;
  }

  /**
   * Get Itinerary at a specified index.
   *
   * @param index of this Itinerary in this ArrayItinerary.
   * @return Itinerary at a specified index.
   */
  public Itinerary getItinerary(int index) {
    return itinerary.get(index);
  }

  public int getSize() {
    return itinerary.size();
  }

  /**
   * Sorts this ArrayItinerary by cost of the contained Itineraries.
   */
  public void sortByCost() {
    sort(false);
  }

  /**
   * Sorts this ArrayItinerary by time spent of the contained Itineraries.
   */
  public void sortByTime() {
    sort(true);
  }

  /**
   * Sort this current itinerary list by either total cost or total time.
   *
   * @param type this sort factor, true to sort by time and false for cost.
   */
  private void sort(boolean type) {
    boolean costSort = type;
    int sorted = 0;
    ArrayList<Itinerary> array = getAllItinerary();

    while (sorted < array.size()) {
      // Sorted is index in the front, representing the smallest value
      for (int i = (sorted + 1); i < array.size(); i++) {
        // Swap the index of the element if a smaller value than the
        // current smallest value (the sorted) is found.
        if (costSort) {
          if (array.get(sorted).getTotalTravelTime() >= array.get(i).getTotalTravelTime()) {
            Itinerary objA = array.get(sorted);
            Itinerary objB = array.get(i);
            array.set(i, objA);
            array.set(sorted, objB);
          }
        } else {
          if (array.get(sorted).getTotalCost() >= array.get(i).getTotalCost()) {
            Itinerary objA = array.get(sorted);
            Itinerary objB = array.get(i);
            array.set(i, objA);
            array.set(sorted, objB);
          }
        }
      }
      sorted += 1;
    }
    this.itinerary = array;
  }

  @Override
  public String toString() {
    String output = "";
    for (int i = 0; i < itinerary.size(); i++) {
      output += getItinerary(i);
      if (i < itinerary.size()) {
        output += "\n";
      }
    }
    return output;
  }

}
