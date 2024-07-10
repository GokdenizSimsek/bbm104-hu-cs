public class DepartureController {
    public void DepartureSchedule(Trip[] trips) {
        // Loop through all trips in the array
        for (int i = 0; i < trips.length - 1; i++) {
            // Skip null elements
            if (trips[i] == null) {
                continue; // null elemanları atla
            }
            // Loop through all remaining trips in the array
            for (int j = i + 1; j < trips.length; j++) {
                // Skip null elements
                if (trips[j] == null) {
                    continue; // null elemanları atla
                }
                // If the arrival time of trip i is greater than that of trip j, swap them
                if (trips[j].getDepartureTime().compareTo(trips[i].getDepartureTime()) < 0) {
                    // swap process
                    Trip temp = trips[i];
                    trips[i] = trips[j];
                    trips[j] = temp;
                }
                // If the arrival times are the same, mark both trips as delayed
                else if (trips[i].getDepartureTime().compareTo(trips[j].getDepartureTime()) == 0) {
                    trips[i].setDepartureState("DELAYED");
                    trips[j].setDepartureState("DELAYED");
                }
            }
        }
    }
}