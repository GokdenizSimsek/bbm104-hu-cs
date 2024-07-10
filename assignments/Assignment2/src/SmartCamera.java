import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SmartCamera extends SmartDevices {
    private String name;
    private LocalDateTime recordDate;// to hold the time of start recording
    private LocalDateTime switchTime;
    private final int mbPerMin;
    private String switchOn = "Off";
    private double storage = 0;
    private final DecimalFormat storageFormat = new DecimalFormat("0.00");// to print storage using with correct format

    public SmartCamera(String name, int mbPerMin){
        this.name = name;
        this.mbPerMin = mbPerMin;
    }
    public SmartCamera(String name, int mbPerMin, String switchOn){
        this.name = name;
        this.mbPerMin = mbPerMin;
        this.switchOn = switchOn;
    }

    public void setSwitchOn() {
        if (isSwitchOn().equals("Off")) {
            switchOn = "On";
        } else {
            switchOn = "Off";
        }
    }

    public String isSwitchOn() { return switchOn; }

    public void changeName(String newName){ name = newName; }

    public void setSwitchTime(LocalDateTime switchTime) { this.switchTime = switchTime; }

    public LocalDateTime getSwitchTime() {
        return switchTime;
    }

    public void setRecordDate(String date) {
        if (date == null) {
            this.recordDate = null;
        } else {
            this.recordDate = LocalDateTime.parse(date, formatter);
        }
    }

    public int getMbPerMin() {
        return mbPerMin;
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public void calculateStorage(Double diffSecond) {
        this.storage += (getMbPerMin() * (diffSecond/60));
    }

    public static void addSmartCamera(ArrayList<String> inputText) {
        if (inputText.size() == 4) {
            if (SmartDevices.deviceNames.contains(inputText.get(2))) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already a smart device with same name!", true, true);
                return;
            }
            if (Integer.parseInt(inputText.get(3)) > 0) {
                // to create new object using with given information
                SmartCamera camera = new SmartCamera(inputText.get(2), Integer.parseInt(inputText.get(3)));
                // adding object to devicesList and deviceNamesList
                SmartDevices.devices.add(camera);
                SmartDevices.deviceNames.add(inputText.get(2));
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: Megabyte value must be a positive number!", true, true);
            }
        } else if (inputText.size() == 5) {
            if (SmartDevices.deviceNames.contains(inputText.get(2))) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already a smart device with same name!", true, true);
                return;
            }
            if (inputText.get(4).equals("On") || inputText.get(4).equals("Off") &&
                    Integer.parseInt(inputText.get(3)) > 0) {
                SmartCamera camera = new SmartCamera(inputText.get(2), Integer.parseInt(inputText.get(3)), inputText.get(4));
                if (inputText.get(4).equals("On")){
                    camera.setRecordDate(time.getTime());// to hold record date when it starts recording
                }
                SmartDevices.devices.add(camera);
                SmartDevices.deviceNames.add(inputText.get(2));
            } else if (Integer.parseInt(inputText.get(3)) > 0) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: Megabyte value must be a positive number!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
        }
    }

    public String giveInf() {
        return (switchTime != null ?  "Smart Camera " + name + " is " + isSwitchOn().toLowerCase() + " and used " +
                storageFormat.format(storage) + " MB of storage so far (excluding current status), and its time to switch its status is " +
                getSwitchTime().format(formatter) + "." : "Smart Camera " + name + " is " + isSwitchOn().toLowerCase() + " and used " +
                storageFormat.format(storage) + " MB of storage so far (excluding current status), and its time to switch its status is null.");
    }

    public static void switchCameraMethod(int index, ArrayList<String> inputText) {// index for deviceList
        if (inputText.get(2).equals("On")) {
            if (SmartDevices.devices.get(index).isSwitchOn().equals("Off")) {
                SmartDevices.devices.get(index).setSwitchOn();// to switch on to off
                ((SmartCamera) SmartDevices.devices.get(index)).setRecordDate(time.getTime());// to hold time of start recording
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: This device is already switched on!", true, true);
            }
        } else if (inputText.get(2).equals("Off")){
            if (SmartDevices.devices.get(index).isSwitchOn().equals("On")) {
                // for calculate time different between starting record and finish time of recording
                Duration timeDiff = Duration.between(((SmartCamera) SmartDevices.devices.get(index)).getRecordDate(), time.getDate());
                double diffSecond = (double) timeDiff.toMillis() /1000;// to calculate in seconds format
                ((SmartCamera) SmartDevices.devices.get(index)).calculateStorage(diffSecond);
                ((SmartCamera) SmartDevices.devices.get(index)).setRecordDate(null);// to make empty the starting record time for other commands
                SmartDevices.devices.get(index).setSwitchOn();// to switch off to on
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: This device is already switched on!", true, true);
            }
        }
    }
}