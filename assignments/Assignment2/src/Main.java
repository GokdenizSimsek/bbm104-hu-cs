import java.time.DateTimeException;
import java.util.*;

public class Main {
    public static String fileName;// For using in other classes
    public static String outputFileName ;// For using in other classes

    public static void main(String[] args) {
        fileName = args[0];// Get the input file name from command line argument and read the input file
        outputFileName = args[1];// Get the output file name from command line argument
        FileOutput.writeToFile(outputFileName, "", false, false);
        ArrayList<ArrayList<String>> inputText = new ArrayList<>();// to get the lines one by one (every index is one line of input file)
        String[] inputFile = FileInput.readFile(fileName, true, true);// Strings of all input file
        for (String line : inputFile) {
            String[] items = line.split("\t");// to separate from tab space
            ArrayList<String> lineList = new ArrayList<>(Arrays.asList(items));
            inputText.add(lineList);
        }
        try{
            FileOutput.writeToFile(outputFileName, "COMMAND: " + inputFile[0], true, true);
            if (inputText.get(0).get(0).equals("SetInitialTime") && inputText.get(0).size() == 2){  // Checking if the first command is setInitalTime
                SmartDevices.time.setInitialTime(inputText.get(0).get(1));// to access the time variable inside the SmartDevices
                FileOutput.writeToFile(outputFileName, String.format("SUCCESS: Time has been set to %s!", inputText.get(0).get(1)), true, true);
                for (int line = 1; line < inputText.size(); line++) { // to evaluate lines
                    FileOutput.writeToFile(outputFileName, "COMMAND: " + inputFile[line], true, true);// to write every commands
                    switch (inputText.get(line).get(0)) { // to control of commands in lines
                        case "Add":
                            if (inputText.get(line).get(1).equals("SmartPlug")) {
                                SmartPlug.addSmartPlug(inputText.get(line));
                            } else if (inputText.get(line).get(1).equals("SmartCamera")) {
                                SmartCamera.addSmartCamera(inputText.get(line));
                            } else if (inputText.get(line).get(1).equals("SmartLamp")) {
                                SmartLamp.addSmartLamp(inputText.get(line));
                            } else if (inputText.get(line).get(1).equals("SmartColorLamp")) {
                                SmartLampWithColor.addSmartLampWithColor(inputText.get(line));
                            }
                            break;
                        case "SetSwitchTime":
                            if (inputText.get(line).size() == 3) { // for checking that the correct number of variables is entered
                                try {
                                    Time.setSwitchTime(inputText.get(line));
                                } catch (DateTimeException e) { // for wrong time format
                                    FileOutput.writeToFile(outputFileName, "ERROR: Time format is not correct!", true, true);
                                }
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "SetTime":
                            if (inputText.get(line).size() == 2) {
                                Time.setTimeMethod(inputText.get(line));
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "SkipMinutes":
                            if (inputText.get(line).size() == 2) {
                                try {
                                    Time.skipMinutesMethod(inputText.get(line));
                                } catch (NumberFormatException e) { // to checking if entered number
                                    FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                                }
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "Switch":
                            if (inputText.get(line).size() == 3) {
                                if (SmartDevices.deviceNames.contains(inputText.get(line).get(1))) { // to checking if there is device
                                    // to get the device's index in deviceList with using deviceNamesList
                                    int index = SmartDevices.deviceNames.indexOf(inputText.get(line).get(1));
                                    if (SmartDevices.devices.get(index) instanceof SmartPlug) {
                                        SmartPlug.switchPlugMethod(index, inputText.get(line));
                                    } else if (SmartDevices.devices.get(index) instanceof SmartLampWithColor) {
                                        SmartLampWithColor.switchColorLampMethod(index, inputText.get(line));
                                    } else if (SmartDevices.devices.get(index) instanceof SmartLamp) {
                                        SmartLamp.switchLampMethod(index, inputText.get(line));
                                    } else if (SmartDevices.devices.get(index) instanceof SmartCamera) {
                                        SmartCamera.switchCameraMethod(index, inputText.get(line));
                                    }
                                } else {
                                    FileOutput.writeToFile(outputFileName, "ERROR: There is not such a device!", true, true);
                                }
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "ChangeName":
                            SmartDevices.changeName(inputText.get(line));
                            break;
                        case "PlugIn":
                            if (inputText.get(line).size() == 3) {
                                SmartPlug.plugInMethod(inputText.get(line));
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "PlugOut":
                            if (inputText.get(line).size() == 2) {
                                SmartPlug.plugOutMethod(inputText.get(line));
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "SetKelvin" :
                            if (inputText.get(line).size() == 3) {
                                SmartLamp.setKelvinMethod(inputText.get(line));
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "SetBrightness" :
                            if (inputText.get(line).size() == 3) {
                                SmartLamp.setBrightnessMethod(inputText.get(line));
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "SetColorCode" :
                            if (inputText.get(line).size() == 3) {
                                SmartLampWithColor.setColorCodeMethod(inputText.get(line));
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "SetWhite" :
                            if (inputText.get(line).size() == 4) {
                                SmartLamp.setWhiteMethod(inputText.get(line));
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "SetColor" :
                            if (inputText.get(line).size() == 4) {
                                SmartLampWithColor.setColorMethod(inputText.get(line));
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "Remove" :
                            if (inputText.get(line).size() == 2) {
                                SmartDevices.removeMethod(inputText.get(line));
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "ZReport" :
                            if (inputText.get(line).size() == 1) {
                                SmartDevices.zreportMethod();
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        case "Nop" :
                            if (inputText.get(line).size() == 1) {
                                SmartDevices.nop();
                            } else {
                                FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                            }
                            break;
                        default:
                            FileOutput.writeToFile(outputFileName, "ERROR: Erroneous command!", true, true);
                    }
                }
            } else { // first command check
                FileOutput.writeToFile(outputFileName, "ERROR: First command must be set initial time! Program is going to terminate!", true, true);
            }
        } catch (DateTimeException e) {// to checking of setInitialTime format
            FileOutput.writeToFile(outputFileName, "ERROR: Format of the initial date is wrong! Program is going to terminate!", true, true);
        }
    }
}