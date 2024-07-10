public class TripSchedule {
    public Trip[] trips;
    public TripSchedule() {
        this.trips = new Trip[100]; // initialize the array with a size of 100
    }
    public void addTrip(Trip trip) {
        for (int i = 0; i < trips.length; i++) {
            if (trips[i] == null) {
                trips[i] = trip;
                trip.calculateArrival(trip.getDepartureTime()); // calculate the arrival time of the trip
                return;
            }
        }
    }
    public Trip[] getTrips() {
        return trips;
    }
}