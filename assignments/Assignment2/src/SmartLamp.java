import java.time.LocalDateTime;
import java.util.ArrayList;

public class SmartLamp extends SmartDevices {
    protected String name;
    protected int kelvinDegree = 4000;
    protected int brightness = 100;
    protected String switchOn = "Off";
    protected LocalDateTime switchTime;

    public SmartLamp(String name){
        this.name = name;
    }

    public SmartLamp(String name, String onOrOff){
        this.name = name;
        this.switchOn = onOrOff;
    }

    public SmartLamp(String name, String onOrOff, int kelvinDegree, int brightness){
        this.name = name;
        this.switchOn = onOrOff;
        this.kelvinDegree = kelvinDegree;
        this.brightness = brightness;
    }

    public void setSwitchOn() {
        if (isSwitchOn().equals("Off")) {
            switchOn = "On";
        } else {
            switchOn = "Off";
        }
    }

    public String isSwitchOn() { return switchOn; }
@Override
    public void changeName(String newName){ name = newName; }

    public void setSwitchTime(LocalDateTime switchTime) { this.switchTime = switchTime; }

    public LocalDateTime getSwitchTime() { return switchTime; }

    public void setKelvin(int kelvinDegree) {
        this.kelvinDegree = kelvinDegree;
    }

    public int getKelvin() { return kelvinDegree; }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getBrightness(){ return brightness; }

    public void setWhite(int kelvinDegree, int brightness) { this.brightness = brightness; this.kelvinDegree = kelvinDegree; }

    public static void setWhiteMethod(ArrayList<String> inputText) {
        if (SmartDevices.deviceNames.contains(inputText.get(1))) {    // to checking if there is device with given name
            int index = SmartDevices.deviceNames.indexOf(inputText.get(1));
            if (SmartDevices.devices.get(index) instanceof SmartLamp) {
                try {
                    if ((Integer.parseInt(inputText.get(2)) <= 6500 && Integer.parseInt(inputText.get(2)) >= 2000) &&
                            (Integer.parseInt(inputText.get(3)) <= 100 && Integer.parseInt(inputText.get(3)) >= 0)) {
                        ((SmartLamp) SmartDevices.devices.get(index)).setWhite(Integer.parseInt(inputText.get(2)),Integer.parseInt(inputText.get(3)));
                    } else if ((Integer.parseInt(inputText.get(2)) <= 6500 && Integer.parseInt(inputText.get(2)) >= 2000)){
                        FileOutput.writeToFile(Main.outputFileName, "ERROR: Brightness must be in range of 0%-100%!", true, true);
                    } else {
                        FileOutput.writeToFile(Main.outputFileName, "ERROR: Kelvin value must be in range of 2000K-6500K!", true, true);
                    }
                } catch (NumberFormatException e) { // to checking if it is number
                    FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: This device is not a smart lamp!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is not such a device!", true, true);
        }
    }

    public static void setBrightnessMethod(ArrayList<String> inputText) {
        if (SmartDevices.deviceNames.contains(inputText.get(1))) {
            int index = SmartDevices.deviceNames.indexOf(inputText.get(1));
            if (SmartDevices.devices.get(index) instanceof SmartLamp) {
                try {
                    if (Integer.parseInt(inputText.get(2)) <= 100 && Integer.parseInt(inputText.get(2)) >= 0) {
                        ((SmartLamp) SmartDevices.devices.get(index)).setBrightness(Integer.parseInt(inputText.get(2)));
                    } else {
                        FileOutput.writeToFile(Main.outputFileName, "ERROR: Brightness must be in range of 0%-100%!", true, true);
                    }
                } catch (NumberFormatException e) {
                    FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: This device is not a smart lamp!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is not such a device!", true, true);
        }
    }

    public static void setKelvinMethod(ArrayList<String> inputText) {
        if (SmartDevices.deviceNames.contains(inputText.get(1))) {
            int index = SmartDevices.deviceNames.indexOf(inputText.get(1));
            if (SmartDevices.devices.get(index) instanceof SmartLamp) {
                try {
                    if (Integer.parseInt(inputText.get(2)) <= 6500 && Integer.parseInt(inputText.get(2)) >= 2000) {
                        ((SmartLamp) SmartDevices.devices.get(index)).setKelvin(Integer.parseInt(inputText.get(2)));

                    } else {
                        FileOutput.writeToFile(Main.outputFileName, "ERROR: Kelvin value must be in range of 2000K-6500K!", true, true);
                    }
                } catch (NumberFormatException e) {
                    FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: This device is not a smart lamp!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is not such a device!", true, true);
        }
    }

    public static void switchLampMethod(int index, ArrayList<String> inputText) {
        if (inputText.get(3).equals("On") || inputText.get(3).equals("Off")) {
            if (!SmartDevices.devices.get(index).isSwitchOn().equals(inputText.get(2))) {
                SmartDevices.devices.get(index).setSwitchOn();// switch it
            } else {
                FileOutput.writeToFile(Main.outputFileName, String.format("ERROR: This device is already switched %s!", inputText.get(2).toLowerCase()), true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
        }
    }

    public static void addSmartLamp(ArrayList<String> inputText) {
        if (inputText.size() == 3) { // for checking that the correct number of variables is entered
            if (SmartDevices.deviceNames.contains(inputText.get(2))) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already a smart device with same name!", true, true);
                return;
            }
            SmartLamp lamp = new SmartLamp((inputText.get(2)));
            SmartDevices.devices.add(lamp);
            SmartDevices.deviceNames.add(inputText.get(2));
        } else if (inputText.size() == 4) {
            if (SmartDevices.deviceNames.contains(inputText.get(2))) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already a smart device with same name!", true, true);
                return;
            }
            if (inputText.get(3).equals("On") || inputText.get(3).equals("Off")) { // to checking if it is On or Off
                SmartLamp lamp = new SmartLamp((inputText.get(2)), inputText.get(3));
                SmartDevices.devices.add(lamp);
                SmartDevices.deviceNames.add(inputText.get(2));
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
            }
        } else if (inputText.size() == 6) {
            if (SmartDevices.deviceNames.contains(inputText.get(2))) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already a smart device with same name!", true, true);
                return;
            }
            if (inputText.get(3).equals("On") || inputText.get(3).equals("Off")) {
                if (Integer.parseInt(inputText.get(4)) >= 2000 && Integer.parseInt(inputText.get(4)) <= 6500) {
                    if (Integer.parseInt(inputText.get(5)) <= 100 && Integer.parseInt(inputText.get(5)) >= 0) {
                        SmartLamp lamp = new SmartLamp((inputText.get(2)), inputText.get(3), Integer.parseInt(inputText.get(4)), Integer.parseInt(inputText.get(5)));
                        SmartDevices.devices.add(lamp);
                        SmartDevices.deviceNames.add(inputText.get(2));
                    } else {
                        FileOutput.writeToFile(Main.outputFileName, "ERROR: Brightness must be in range of 0%-100%!", true, true);
                    }
                } else {
                    FileOutput.writeToFile(Main.outputFileName, "ERROR: Kelvin value must be in range of 2000K-6500K!", true, true);
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
        }
    }

    public String giveInf() {
        return (getSwitchTime() != null ?  "Smart Lamp " + name + " is " + switchOn.toLowerCase() + " and its kelvin value is " +
                getKelvin() + "K with " + getBrightness() + "% brightness, and its time to switch its status is " + getSwitchTime().format(formatter) +
                "." : "Smart Lamp " + name + " is " + switchOn.toLowerCase() + " and its kelvin value is " + getKelvin() +
                "K with " + getBrightness() + "% brightness, and its time to switch its status is null.");
    }
}