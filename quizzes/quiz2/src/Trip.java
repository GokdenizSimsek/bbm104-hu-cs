import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class Trip {
    public String tripName;
    public Date departureTime;
    public Date arrivalTime;
    public int duration;
    public String arrivalState;
    public String departureState;

    public Trip(String tripName, String departureTime, String tripDuration) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // specifying the format
        Date date = null;
        try {
            date = sdf.parse(departureTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int intDuration = Integer.parseInt(tripDuration);

        this.tripName = tripName;
        this.departureTime = date;
        this.duration = intDuration;
        this.arrivalState = "IDLE";
        this.departureState = "IDLE";
    }
    // Getter Methods
    public String getTripName() {
        return tripName;
    }
    public Date getDepartureTime() {
        return departureTime;
    }
    public Date getArrivalTime() {
        return arrivalTime;
    }
    public String getArrivalState() {
        return arrivalState;
    }
    public String getDepartureState() {
        return departureState;
    }
    // Setter Methods
    public void setArrivalState(String newState) {
        this.arrivalState = newState;
    }
    public void setDepartureState(String newState) {
        this.departureState = newState;
    }
    public void calculateArrival(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date); // setting the starting date
        cal.add(Calendar.MINUTE, duration);
        arrivalTime = cal.getTime();
    }
}