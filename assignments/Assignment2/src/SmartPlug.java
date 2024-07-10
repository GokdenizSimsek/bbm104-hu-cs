import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class SmartPlug extends SmartDevices {
    private String name;
    private String switchOn = "Off";
    private boolean plugIn = false;
    private LocalDateTime plugDate = null; // to hold the time to start consume energy
    private double ampere;
    private final double voltage = 220;
    private double totalEnergy = 0;
    private LocalDateTime switchTime;
    private final DecimalFormat energyFormat = new DecimalFormat("0.00");

    public SmartPlug(String name, String onOrOff, double ampere){
        this.name = name;
        this.ampere = ampere;
        this.switchOn = onOrOff;
    }
    public SmartPlug(String name, String onOrOff){
        this.name = name;
        this.switchOn = onOrOff;
    }
    public SmartPlug(String name){
        this.name = name;
    }

    public String getName(){ return name; }

    public void changeName(String newName){ name = newName; }

    public void setSwitchTime(LocalDateTime  switchTime) {this.switchTime = switchTime;}

    public LocalDateTime getSwitchTime() { return switchTime; }

    public void plugIn(double ampere) { plugIn = true; this.ampere = ampere; }

    public void plugOut() {
        plugIn = false;
        this.ampere = 0; // if it is plugOut that means no ampere
    }

    public boolean isPlugIn() { return plugIn; }

    public double getAmpere() { return ampere; }

    public void setSwitchOn() {
        if (isSwitchOn().equals("Off")) {
            switchOn = "On";
        } else {
            switchOn = "Off";
        }
    }

    public String isSwitchOn() { return switchOn; }

    public double getVoltage() { return voltage; }

    public double getTotalEnergy() { return totalEnergy; }

    public void setPlugDate(String plugDate) {
        if (plugDate == null) {
            this.plugDate = null;
        } else {
            this.plugDate = LocalDateTime.parse(plugDate, formatter);
        }
    }
    public LocalDateTime getPlugDate() {
        return plugDate;
    }
    public void calculateTotalEnergy(Double diffSecond) {
        this.totalEnergy += (getAmpere() * getVoltage() * (diffSecond/3600));
    }

    public static void addSmartPlug(ArrayList<String> inputText){
        if (inputText.size() == 3) {
            if (deviceNames.contains(inputText.get(2))) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already a smart device with same name!", true, true);
                return;
            }
            SmartPlug plug = new SmartPlug((inputText.get(2)));
            devices.add(plug);
            deviceNames.add(inputText.get(2));
        } else if (inputText.size() == 4) {
            if (deviceNames.contains(inputText.get(2))) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already a smart device with same name!", true, true);
                return;
            }
            if (inputText.get(3).equals("On") || inputText.get(3).equals("Off")) {
                SmartPlug plug = new SmartPlug((inputText.get(2)), inputText.get(3));
                devices.add(plug);
                deviceNames.add(inputText.get(2));
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
            }
        } else if (inputText.size() == 5) {
            if (deviceNames.contains(inputText.get(2))) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already a smart device with same name!", true, true);
                return;
            }
            if (Double.parseDouble(inputText.get(4)) > 0 &&
                    (inputText.get(3).equals("On") || inputText.get(3).equals("Off"))) {
                SmartPlug plug = new SmartPlug((inputText.get(2)), inputText.get(3),
                        Double.parseDouble(inputText.get(4)));
                if (inputText.get(3).equals("On")){
                    plug.plugIn(Double.parseDouble(inputText.get(4)));
                    plug.setPlugDate(time.getTime()); // to hold plug in date for calculating energy consumption
                }
                devices.add(plug);
                deviceNames.add(inputText.get(2));
            } else if (inputText.get(3).equals("On") || inputText.get(3).equals("Off")) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: Ampere value must be a positive number!", true, true);
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
        }
    }

    public String giveInf() {
        return (getSwitchTime() != null ?  "Smart Plug " + getName() + " is " + isSwitchOn().toLowerCase() + " and consumed " +
                energyFormat.format(getTotalEnergy()) + "W so far (excluding current device), and its time to switch its status is " +
                getSwitchTime().format(formatter) + "." : "Smart Plug " + getName() + " is " + isSwitchOn().toLowerCase() + " and consumed " +
                energyFormat.format(getTotalEnergy()) + "W so far (excluding current device), and its time to switch its status is null.");
    }

    public static void switchPlugMethod(int index, ArrayList<String> inputText) {
        if (inputText.get(2).equals("On")) {
            if (!SmartDevices.devices.get(index).isSwitchOn().equals("On")) {
                SmartDevices.devices.get(index).setSwitchOn();
                if (((SmartPlug) SmartDevices.devices.get(index)).isPlugIn()){
                    ((SmartPlug) SmartDevices.devices.get(index)).setPlugDate(time.getTime());
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: This device is already switched on!", true, true);
            }
        } else if (inputText.get(2).equals("Off")) {
            if (!SmartDevices.devices.get(index).isSwitchOn().equals("Off")) {
                SmartDevices.devices.get(index).setSwitchOn();
                if (((SmartPlug) SmartDevices.devices.get(index)).isPlugIn()){
                    Duration timeDiff = Duration.between(((SmartPlug) SmartDevices.devices.get(index)).getPlugDate(), time.getDate());
                    // for calculate time different between starting consume energy and stop
                    double diffSecond = (double) timeDiff.toMillis() /1000;// to calculate in seconds format
                    ((SmartPlug) SmartDevices.devices.get(index)).calculateTotalEnergy(diffSecond);
                    ((SmartPlug) SmartDevices.devices.get(index)).setPlugDate(null);// to make empty the starting consume energy time for other commands
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: This device is already switched off!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
        }
    }

    public static void plugInMethod(ArrayList<String> inputText) {
        if (SmartDevices.deviceNames.contains(inputText.get(1))) {
            int index = SmartDevices.deviceNames.indexOf(inputText.get(1));
            if (SmartDevices.devices.get(index) instanceof SmartPlug) {
                if (!((SmartPlug) SmartDevices.devices.get(index)).isPlugIn()) {
                    if (!(Double.parseDouble(inputText.get(2)) < 0)) {
                        ((SmartPlug) SmartDevices.devices.get(index)).plugIn(Double.parseDouble(inputText.get(2)));
                        if (SmartDevices.devices.get(index).isSwitchOn().equals("On")) {
                            ((SmartPlug) SmartDevices.devices.get(index)).setPlugDate(time.getTime());
                        }
                    } else {
                        FileOutput.writeToFile(Main.outputFileName, "ERROR: Ampere value must be a positive number!", true, true);
                    }
                } else {
                    FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already an item plugged in to that plug!", true, true);
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: This device is not a smart plug!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is not such a device!", true, true);
        }
    }

    public static void plugOutMethod(ArrayList<String> inputText) {
        if (SmartDevices.deviceNames.contains(inputText.get(1))) {
            int index = SmartDevices.deviceNames.indexOf(inputText.get(1));
            if (SmartDevices.devices.get(index) instanceof SmartPlug) {
                if (((SmartPlug) SmartDevices.devices.get(index)).isPlugIn()) {
                    ((SmartPlug) SmartDevices.devices.get(index)).plugOut();
                    if (SmartDevices.devices.get(index).isSwitchOn().equals("On")) {
                        Duration timeDiff = Duration.between(((SmartPlug) SmartDevices.devices.get(index)).getPlugDate(), time.getDate());
                        double diffMinutes = timeDiff.toMinutes();
                        ((SmartPlug) SmartDevices.devices.get(index)).calculateTotalEnergy(diffMinutes);
                        ((SmartPlug) SmartDevices.devices.get(index)).setPlugDate(null);
                    }
                } else {
                    FileOutput.writeToFile(Main.outputFileName, "ERROR: This plug has no item to plug out from that plug!", true, true);
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: This device is not a smart plug!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is not such a device!", true, true);
        }
    }
}