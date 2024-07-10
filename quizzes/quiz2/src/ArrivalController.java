public class ArrivalController {
    public void ArrivalSchedule(Trip[] trips) {
        // Loop through all trips in the array
        for (int i = 0; i < trips.length - 1; i++) {
            // Skip null elements
            if (trips[i] == null) {
                continue;
            }
            // Loop through all remaining trips in the array
            for (int j = i + 1; j < trips.length; j++) {
                // Skip null elements
                if (trips[j] == null) {
                    continue;
                }
                // If the arrival time of trip i is greater than that of trip j, swap them
                if (trips[i].getArrivalTime().compareTo(trips[j].getArrivalTime()) > 0) {
                    // swap process
                    Trip temp = trips[i];
                    trips[i] = trips[j];
                    trips[j] = temp;
                }
                // If the arrival times are the same, mark both trips as delayed
                else if (trips[i].getArrivalTime().compareTo(trips[j].getArrivalTime()) == 0) {
                    trips[i].setArrivalState("DELAYED");
                    trips[j].setArrivalState("DELAYED");
                }
            }
        }
    }
}