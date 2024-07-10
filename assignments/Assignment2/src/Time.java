import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Time {
    private static LocalDateTime date;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss" ); // specifying the format

    public void setInitialTime(String time){
        date = LocalDateTime.parse(time, formatter);
    }
    public void setTime(String time){
        date = LocalDateTime.parse(time, formatter);
    }
    public static void skipMinutes(int minute) {
        date = date.plusMinutes(minute);
    }
    public String getTime(){
        return date.format(formatter);
    }
    public LocalDateTime  getDate(){
        return date;
    }

    public static void setSwitchTime(ArrayList<String> inputText) {
        DateTimeFormatter formatter = Time.formatter; // to using with desired format
        if (SmartDevices.deviceNames.contains(inputText.get(1))) { // to check if there is device with given name
            int index = SmartDevices.deviceNames.indexOf(inputText.get(1));
            if (inputText.get(2).compareTo(SmartDevices.time.getTime()) > 0) { // to check if it is a past tense
                SmartDevices.devices.get(index).setSwitchTime(LocalDateTime.parse(inputText.get(2), formatter));
            } else if (inputText.get(2).compareTo(SmartDevices.time.getTime()) < 0) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: Switch time cannot be in the past!", true, true);
            } else {    // if the given time is current time, then switch device
                if (SmartDevices.devices.get(index) instanceof SmartPlug) { // to check device is smartPlug
                    if (SmartDevices.devices.get(index).isSwitchOn().equals("On")) {
                        if (((SmartPlug) SmartDevices.devices.get(index)).isPlugIn()){
                            // for calculate time different between starting consume energy and stop
                            Duration timeDiff = Duration.between(((SmartPlug) SmartDevices.devices.get(index)).getPlugDate(), SmartDevices.time.getDate());
                            double diffSecond = (double) timeDiff.toMillis() /1000;// to get in second format
                            ((SmartPlug) SmartDevices.devices.get(index)).calculateTotalEnergy(diffSecond);
                            ((SmartPlug) SmartDevices.devices.get(index)).setPlugDate(null); // to set plug time for other commands
                        }
                    } else if (SmartDevices.devices.get(index).isSwitchOn().equals("Off")) {
                        if (((SmartPlug) SmartDevices.devices.get(index)).isPlugIn()){
                            ((SmartPlug) SmartDevices.devices.get(index)).setPlugDate(SmartDevices.time.getTime()); // for hold the starting time of consume energy
                        }
                    }
                } else if (SmartDevices.devices.get(index) instanceof SmartCamera) {  // to check device is smartCamera
                    if (SmartDevices.devices.get(index).isSwitchOn().equals("On")) {
                        Duration timeDiff = Duration.between(((SmartCamera) SmartDevices.devices.get(index)).getRecordDate(), SmartDevices.time.getDate());
                        // for calculate time different between starting record and stop
                        double diffSecond = (double) timeDiff.toMillis() /1000;// to get in second format
                        ((SmartCamera) SmartDevices.devices.get(index)).calculateStorage(diffSecond);
                        ((SmartCamera) SmartDevices.devices.get(index)).setRecordDate(null); // to set starting record time for other commands
                    } else if (SmartDevices.devices.get(index).isSwitchOn().equals("Off")) {
                        ((SmartCamera) SmartDevices.devices.get(index)).setRecordDate(SmartDevices.time.getTime());// to hold time of starting record
                    }
                }
                SmartDevices.devices.get(index).setSwitchOn(); // to switch independent of device type
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is not such a device!", true, true);
        }
    }

    public static void skipMinutesMethod(ArrayList<String> inputText) {
        if (Integer.parseInt(inputText.get(1)) > 0) {
            skipMinutes(Integer.parseInt(inputText.get(1)));
            for (SmartDevices device: SmartDevices.devices) {   // checking switch times after skip minutes
                if (device.getSwitchTime() != null) {   // if it has switch time
                    if (device.getSwitchTime().format(formatter).compareTo(SmartDevices.time.getTime()) <= 0) {
                        if (device instanceof SmartPlug) {
                            if (device.isSwitchOn().equals("On")) {
                                if (((SmartPlug) device).isPlugIn()){
                                    Duration timeDiff = Duration.between(((SmartPlug) device).getPlugDate(), SmartDevices.time.getDate());
                                    double diffSecond = (double) timeDiff.toMillis() /1000;
                                    ((SmartPlug) device).calculateTotalEnergy(diffSecond);
                                    ((SmartPlug) device).setPlugDate(null);
                                }
                            } else if (device.isSwitchOn().equals("Off")) {
                                if (((SmartPlug) device).isPlugIn()){
                                    ((SmartPlug) device).setPlugDate(SmartDevices.time.getTime());
                                }
                            }
                        } else if (device instanceof SmartCamera) {
                            if (device.isSwitchOn().equals("On")) {
                                Duration timeDiff = Duration.between(((SmartCamera) device).getRecordDate(), SmartDevices.time.getDate());
                                double diffSecond = (double) timeDiff.toMillis() /1000;
                                ((SmartCamera) device).calculateStorage(diffSecond);
                                ((SmartCamera) device).setRecordDate(null);
                            } else if (device.isSwitchOn().equals("Off")) {
                                ((SmartCamera) device).setRecordDate(SmartDevices.time.getTime());
                            }
                        }
                        device.setSwitchOn(); // to switch independent of device type
                    }
                }
            }
        } else if (Integer.parseInt(inputText.get(1)) == 0) {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is nothing to skip!", true, true);
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: Time cannot be reversed!", true, true);
        }
    }

    public static void setTimeMethod(ArrayList<String> inputText) {
        if (inputText.get(1).compareTo(SmartDevices.time.getTime()) > 0) {
            try {
                SmartDevices.time.setTime(inputText.get(1));
                for (SmartDevices device: SmartDevices.devices) { // for checking switch times after
                    if (device.getSwitchTime() != null) {
                        if (device.getSwitchTime().format(formatter).compareTo(SmartDevices.time.getTime()) <= 0) {
                            if (device instanceof SmartPlug) {
                                if (device.isSwitchOn().equals("On")) {
                                    device.setSwitchOn();
                                    if (((SmartPlug) device).isPlugIn()){
                                        Duration timeDiff = Duration.between(((SmartPlug) device).getPlugDate(), SmartDevices.time.getDate());
                                        double diffSecond = (double) timeDiff.toMillis() /1000;
                                        ((SmartPlug) device).calculateTotalEnergy(diffSecond);
                                        ((SmartPlug) device).setPlugDate(null); // to make empty the starting consume energy time for other commands
                                    }
                                } else if (device.isSwitchOn().equals("Off")) {
                                    device.setSwitchOn();
                                    if (((SmartPlug) device).isPlugIn()){
                                        ((SmartPlug) device).setPlugDate(SmartDevices.time.getTime());
                                    }
                                }
                                device.setSwitchTime(null);
                            } else if (device instanceof SmartCamera) {
                                if (device.isSwitchOn().equals("On")) {
                                    device.setSwitchOn();
                                    Duration timeDiff = Duration.between(((SmartCamera) device).getRecordDate(), SmartDevices.time.getDate());
                                    double diffSecond = (double) timeDiff.toMillis() /1000;
                                    ((SmartCamera) device).calculateStorage(diffSecond);
                                } else if (device.isSwitchOn().equals("Off")) {
                                    device.setSwitchOn();
                                    ((SmartCamera) device).setRecordDate(SmartDevices.time.getTime());
                                }
                            } else {
                                device.setSwitchOn(); // to switch independent of device type
                            }
                            device.setSwitchTime(null); // to set the switchTime of devices after switch them
                        }
                    }
                }
            } catch (DateTimeException e) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: Time format is not correct!", true, true);
            }
        } else  if (inputText.get(1).compareTo(SmartDevices.time.getTime()) == 0){
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is nothing to change!", true, true);
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: Time cannot be reversed!", true, true);
        }
    }
}