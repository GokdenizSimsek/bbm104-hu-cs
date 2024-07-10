import java.util.ArrayList;

public class SmartLampWithColor extends SmartLamp {
    private int colorCode = 0x000000;

    public SmartLampWithColor(String name) {
        super(name);
    }

    public SmartLampWithColor(String name, String onOrOff){
        super(name, onOrOff);
    }

    public SmartLampWithColor(String name, String onOrOff, String kelvinOrColorCode, int brightness){
        super(name, onOrOff);
        super.brightness = brightness;
        if (kelvinOrColorCode.contains("x")) { // to checking for is it color code
            colorCode = Integer.parseInt(kelvinOrColorCode.substring(2),16);
            kelvinDegree = 0;
        } else {
            super.kelvinDegree = Integer.parseInt(kelvinOrColorCode);
        }
    }

    public int getColorCode() { return colorCode; }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public void setColor(int colorCode, int brightness) {
        super.brightness = brightness; this.colorCode = colorCode;
    }

    public static void addSmartLampWithColor(ArrayList<String> inputText) {
        if (inputText.size() == 3) { // for checking that the correct number of variables is entered
            if (SmartDevices.deviceNames.contains(inputText.get(2))) { // to check if there is device with given name
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already a smart device with same name!", true, true);
                return;
            }
            SmartLampWithColor colorLamp = new SmartLampWithColor((inputText.get(2)));
            SmartDevices.devices.add(colorLamp);
            SmartDevices.deviceNames.add(inputText.get(2));
        } else if (inputText.size() == 4) {
            if (SmartDevices.deviceNames.contains(inputText.get(2))) {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: There is already a smart device with same name!", true, true);
                return;
            }
            if (inputText.get(3).equals("On") || inputText.get(3).equals("Off")) {
                SmartLampWithColor colorLamp = new SmartLampWithColor((inputText.get(2)), inputText.get(3));
                SmartDevices.devices.add(colorLamp);
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
                try {
                    if (inputText.get(4).contains("x")) { // to check is it color code or not
                        // to check color code is in given range
                        if (Integer.parseInt(inputText.get(4).substring(2),16) >= 0x000000 && Integer.parseInt(inputText.get(4).substring(2),16) <= 0xFFFFFF) {
                            if (Integer.parseInt(inputText.get(5)) <= 100 && Integer.parseInt(inputText.get(5)) >= 0) {
                                SmartLampWithColor colorLamp = new SmartLampWithColor((inputText.get(2)), inputText.get(3), inputText.get(4), Integer.parseInt(inputText.get(5)));
                                SmartDevices.devices.add(colorLamp);
                                SmartDevices.deviceNames.add(inputText.get(2));
                            } else {
                                FileOutput.writeToFile(Main.outputFileName, "ERROR: Brightness must be in range of 0%-100%!", true, true);
                            }
                        } else {
                            FileOutput.writeToFile(Main.outputFileName, "ERROR: Color code value must be in range of 0x0-0xFFFFFF!", true, true);
                        }
                    } else {
                        if (Integer.parseInt(inputText.get(4)) >= 2000 && Integer.parseInt(inputText.get(4)) <= 6500) {
                            if (Integer.parseInt(inputText.get(5)) <= 100 && Integer.parseInt(inputText.get(5)) >= 0) {
                                SmartLampWithColor colorLamp = new SmartLampWithColor((inputText.get(2)), inputText.get(3), inputText.get(4), Integer.parseInt(inputText.get(5)));
                                SmartDevices.devices.add(colorLamp);
                                SmartDevices.deviceNames.add(inputText.get(2));
                            } else {
                                FileOutput.writeToFile(Main.outputFileName, "ERROR: Brightness must be in range of 0%-100%!", true, true);
                            }
                        } else {
                            FileOutput.writeToFile(Main.outputFileName, "ERROR: Kelvin value must be in range of 2000K-6500K!", true, true);
                        }
                    }
                } catch (NumberFormatException e) {
                    FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
                }
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
        }
    }

    public static void setColorCodeMethod(ArrayList<String> inputText) {
        if (SmartDevices.deviceNames.contains(inputText.get(1))) {
            int index = SmartDevices.deviceNames.indexOf(inputText.get(1));
            if (SmartDevices.devices.get(index) instanceof SmartLampWithColor) {
                try {
                    if (Integer.parseInt(inputText.get(2).substring(2),16) >= 0x000000 &&
                            Integer.parseInt(inputText.get(2).substring(2),16) <= 0xFFFFFF) {
                        ((SmartLampWithColor) SmartDevices.devices.get(index)).setColorCode(Integer.parseInt(inputText.get(2)));
                        ((SmartLampWithColor) SmartDevices.devices.get(index)).setKelvin(0);
                    } else {
                        FileOutput.writeToFile(Main.outputFileName, "ERROR: Color code value must be in range of 0x0-0xFFFFFF!", true, true);
                    }
                } catch (NumberFormatException e) {
                    FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: This device is not a smart color lamp!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is not such a device!", true, true);
        }
    }
    public static void switchColorLampMethod(int index, ArrayList<String> inputText) {
        if (inputText.get(3).equals("On") || inputText.get(3).equals("Off")) {
            if (!SmartDevices.devices.get(index).isSwitchOn().equals(inputText.get(2))) {
                SmartDevices.devices.get(index).setSwitchOn();
            } else {
                FileOutput.writeToFile(Main.outputFileName, String.format("ERROR: This device is already switched %s!", inputText.get(2).toLowerCase()), true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
        }
    }

    public static void setColorMethod(ArrayList<String> inputText) {
        if (SmartDevices.deviceNames.contains(inputText.get(1))) {
            int index = SmartDevices.deviceNames.indexOf(inputText.get(1));
            if (SmartDevices.devices.get(index) instanceof SmartLampWithColor) {
                try {
                    if ((Integer.parseInt(inputText.get(2).substring(2),16) >= 0x000000 &&
                            Integer.parseInt(inputText.get(2).substring(2),16) <= 0xFFFFFF) &&
                            (Integer.parseInt(inputText.get(3)) <= 100 && Integer.parseInt(inputText.get(3)) >= 0)) {
                        ((SmartLampWithColor) SmartDevices.devices.get(index)).setColor(Integer.parseInt(inputText.get(2).substring(2),16),Integer.parseInt(inputText.get(3)));
                        ((SmartLampWithColor) SmartDevices.devices.get(index)).setKelvin(0);
                    } else if ((Integer.parseInt(inputText.get(2).substring(2),16) >= 0x000000 &&
                            Integer.parseInt(inputText.get(2).substring(2),16) <= 0xFFFFFF)){
                        FileOutput.writeToFile(Main.outputFileName, "ERROR: Brightness must be in range of 0%-100%!", true, true);
                    } else {
                        FileOutput.writeToFile(Main.outputFileName, "ERROR: Color code value must be in range of 0x0-0xFFFFFF!", true, true);
                    }
                } catch (NumberFormatException e) {
                    FileOutput.writeToFile(Main.outputFileName, "ERROR: Erroneous command!", true, true);
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "ERROR: This device is not a smart color lamp!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "ERROR: There is not such a device!", true, true);
        }
    }

    public String giveInf() {
        if (getSwitchTime() == null) {
            if (kelvinDegree == 0) { // if kelvin degree equals to 0, then it does not use white mode
                return "Smart Color Lamp " + name + " is " + switchOn.toLowerCase() + " and its color value is " + String.format("0x%06X", getColorCode()) + " with " + getBrightness() + "% brightness, and its time to switch its status is null.";
            } else { return "Smart Color Lamp " + name + " is " + switchOn.toLowerCase() + " and its color value is " + getKelvin() + "K with " + getBrightness() + "% brightness, and its time to switch its status is null.";}
        } else {
            if (kelvinDegree == 0) {
                return "Smart Color Lamp " + name + " is " + switchOn.toLowerCase() + " and its color value is " + String.format("0x%06X", getColorCode()) + " with " + getBrightness() + "% brightness, and its time to switch its status is " + getSwitchTime().format(formatter) + ".";
            } else {
                return "Smart Color Lamp " + name + " is " + switchOn.toLowerCase() + " and its color value is " + getKelvin() + "K with " + getBrightness() + "% brightness, and its time to switch its status is " + getSwitchTime().format(formatter) + ".";
            }
        }
    }
}
