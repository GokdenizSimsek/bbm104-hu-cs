import java.text.ParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException {
        // Create an empty output file
        FileOutput.writeToFile("output.txt", "", false, false);

        // Get the input file name from command line argument and read the input file
        String fileName = args[0];
        String[] inputText = FileInput.readFile(fileName, true, true);

        // Create a TripSchedule and add trips from the input file to it
        TripSchedule tripSchedule = new TripSchedule();
        for (String line : inputText) {
            List<String> infos = Arrays.asList(line.split("\t"));
            Trip trip = new Trip(infos.get(0), infos.get(1), infos.get(2));
            tripSchedule.addTrip(trip);
        }
        TripController tripController = new TripController(tripSchedule);

        // Sort and print trips by departure time
        FileOutput.writeToFile("output.txt", "Departure order:", true, true);
        tripController.sortTripsByDepartureTime();

        // Sort and print trips by arrival time
        FileOutput.writeToFile("output.txt", "\nArrival order:", true, true);
        tripController.sortTripsByArrivalTime();
    }
}