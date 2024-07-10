import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class SmartDevices {
    public static Time time = new Time();
    public static ArrayList<String> deviceNames = new ArrayList<String>();
    public static ArrayList<SmartDevices> devices = new ArrayList<SmartDevices>();

    public abstract void setSwitchTime(LocalDateTime switchTime);

    public abstract LocalDateTime getSwitchTime();

    public abstract void changeName(String newName);

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss" );

    public abstract String giveInf();

    public abstract String isSwitchOn();

    public abstract void setSwitchOn();

    public static void nop() {
        ArrayList<String> switchTimes = new ArrayList<>();
        for (int device = 0; device < SmartDevices.devices.size(); device++) { // to add the switch times of devices
            if (SmartDevices.devices.get(device).getSwitchTime() != null) {
                switchTimes.add(SmartDevices.devices.get(device).getSwitchTime().format(Time.formatter));
            }
        }
        if (switchTimes.size() != 0) { // for choosing oldest date of switch times and set to current time
            String oldestDate = switchTimes.get(0);
            for (int times = 1; times < switchTimes.size(); times++) {
                if (switchTimes.get(times).compareTo(oldestDate) < 0) {
                    oldestDate = switchTimes.get(times);
                }
            }
            time.setTime(oldestDate);
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is nothing to switch!", true, true);
            return;
        }
        for (SmartDevices device: SmartDevices.devices) {
            if (device.getSwitchTime() != null) {
                if (device.getSwitchTime().format(Time.formatter).compareTo(time.getTime()) <= 0) {
                    if (device instanceof SmartPlug) {
                        if (device.isSwitchOn().equals("On")) {
                            if (((SmartPlug) device).isPlugIn()){
                                // get time different between starting consume energy time and finish time
                                Duration timeDiff = Duration.between(((SmartPlug) device).getPlugDate(), time.getDate());
                                double diffSecond = (double) timeDiff.toMillis() /1000; // to get in second format
                                ((SmartPlug) device).calculateTotalEnergy(diffSecond);
                                ((SmartPlug) device).setPlugDate(null); // maka null the starting consume energy time for other commands
                            }
                        } else if (device.isSwitchOn().equals("Off")) {
                            if (((SmartPlug) device).isPlugIn()){
                                ((SmartPlug) device).setPlugDate(time.getTime()); // to hold time of starting consume energy
                            }
                        }
                    } else if (device instanceof SmartCamera) {
                        if (device.isSwitchOn().equals("On")) {
                            // get time different between starting record time and stop time
                            Duration timeDiff = Duration.between(((SmartCamera) device).getRecordDate(), time.getDate());
                            double diffSecond = (double) timeDiff.toMillis() /1000; // to get in second format
                            ((SmartCamera) device).calculateStorage(diffSecond);
                            ((SmartCamera) device).setRecordDate(null); // make null the starting time of record
                        } else if (device.isSwitchOn().equals("Off")) {
                            ((SmartCamera) device).setRecordDate(time.getTime()); // to hold the time of starting record
                        }
                    }
                    device.setSwitchOn(); // to switch independent of device type
                    device.setSwitchTime(null);
                }
            }
        }
    }

    public static void removeMethod(ArrayList<String> inputText) {
        if (SmartDevices.deviceNames.contains(inputText.get(1))) {
            FileOutput.writeToFile(Main.outputFileName, "SUCCESS: Information about removed smart device is as follows:", true, true);
            int index = SmartDevices.deviceNames.indexOf(inputText.get(1));
            if (SmartDevices.devices.get(index) instanceof SmartPlug) {
                if (SmartDevices.devices.get(index).isSwitchOn().equals("On")) {
                    SmartDevices.devices.get(index).setSwitchOn();
                    if (((SmartPlug) SmartDevices.devices.get(index)).isPlugIn()){
                        Duration timeDiff = Duration.between(((SmartPlug) SmartDevices.devices.get(index)).getPlugDate(), time.getDate());
                        double diffSecond = (double) timeDiff.toMillis() /1000;
                        ((SmartPlug) SmartDevices.devices.get(index)).calculateTotalEnergy(diffSecond);
                    }
                }
            } else if (SmartDevices.devices.get(index) instanceof SmartCamera) {
                if (SmartDevices.devices.get(index).isSwitchOn().equals("On")) {
                    Duration timeDiff = Duration.between(((SmartCamera) SmartDevices.devices.get(index)).getRecordDate(), time.getDate());
                    double diffSecond = (double) timeDiff.toMillis() /1000;
                    ((SmartCamera) SmartDevices.devices.get(index)).calculateStorage(diffSecond);
                    SmartDevices.devices.get(index).setSwitchOn();
                }
            } else {
                if (SmartDevices.devices.get(index).isSwitchOn().equals("On")) {
                    SmartDevices.devices.get(index).setSwitchOn();
                }
            }
            FileOutput.writeToFile(Main.outputFileName, SmartDevices.devices.get(index).giveInf(), true, true);
            SmartDevices.devices.remove(index);
            SmartDevices.deviceNames.remove(index);
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is not such a device!", true, true);
        }
    }

    public static void zreportMethod() {
        FileOutput.writeToFile(Main.outputFileName, "Time is:\t" + time.getTime(), true, true);
        for (SmartDevices device : SmartDevices.devices) { // for print devices which has switchTime firstly
            if (device.getSwitchTime() != null) {
                FileOutput.writeToFile(Main.outputFileName, device.giveInf(), true, true);
            }
        }
        for (SmartDevices device : SmartDevices.devices) {
            if (device.getSwitchTime() == null) {
                FileOutput.writeToFile(Main.outputFileName, device.giveInf(), true, true);
            }
        }
    }

    public static void changeName(ArrayList<String> inputText) {
        if (inputText.size() == 3) {
            if (inputText.get(1).equals(inputText.get(2))) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: Both of the names are the same, nothing changed!", true, true);
            } else if (!deviceNames.contains(inputText.get(1))) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is not such a device!", true, true);
            } else if (!deviceNames.contains(inputText.get(2))) {
                int index = deviceNames.indexOf(inputText.get(1));
                devices.get(index).changeName(inputText.get(2));
                deviceNames.set(index, inputText.get(2));
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already a smart device with same name!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
        }
    }
}
