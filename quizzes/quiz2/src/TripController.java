import java.text.SimpleDateFormat;

public class TripController {
    protected TripSchedule trip_schedule;

    public TripController(TripSchedule tripSchedule) {
        this.trip_schedule = tripSchedule;
    }

    // Method to sort the trips by departure time
    public void sortTripsByDepartureTime() {
        // Create a new instance of DepartureController
        DepartureController departureController = new DepartureController();
        // Call the DepartureSchedule method to sort the trips by departure time
        departureController.DepartureSchedule(trip_schedule.getTrips());

        // Create a SimpleDateFormat object to format the output
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        // Iterate over the trips in the trip schedule
        for (Trip trip : trip_schedule.getTrips()) {
            if (trip == null) {
                continue;
            }
            // Write the trip information to the output file
            FileOutput.writeToFile("output.txt", trip.getTripName() + " depart at " + sdf.format(trip.getDepartureTime()) +
                    "   Trip State:" + trip.getDepartureState(), true, true);
        }
    }

    // Method to sort the trips by arrival time
    public void sortTripsByArrivalTime() {
        // Create a new instance of ArrivalController
        ArrivalController arrivalController = new ArrivalController();
        // Call the ArrivalSchedule method to sort the trips by arrival time
        arrivalController.ArrivalSchedule(trip_schedule.getTrips());

        // Create a SimpleDateFormat object to format the output
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        // Iterate over the trips in the trip schedule
        for (Trip trip : trip_schedule.getTrips()) {
            if (trip == null) {
                continue;
            }
            // Write the trip information to the output file
            FileOutput.writeToFile("output.txt", trip.getTripName() + " arrive at " + sdf.format(trip.getArrivalTime()) +
                    "   Trip State:" + trip.getArrivalState(), true, true);
        }
    }
}