public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            FileOutput.writeToFile("output.txt", "There should be only 1 parameter", true, true);
            return;
        }
        String filesPath = args[0];
        try{
            String[] inputFile = FileInput.readFile(filesPath, true, true);
            if (inputFile.length == 0) {
                FileOutput.writeToFile("output.txt", "The input file should not be empty", true, true);
                return;
            }
            System.out.println(inputFile.length);
            System.out.println(inputFile[0]);
            System.out.println(inputFile[0].length());
            if ((inputFile.length > 1) || (!inputFile[0].matches("[a-zA-Z ]+")) || (inputFile[0].length() != 32)) {
                FileOutput.writeToFile("output.txt", "The input file should not contains unexpected characters", true, true);
                return;
            }
            FileOutput.writeToFile("output.txt",inputFile[0],true,true);
        } catch (NullPointerException e) {
            FileOutput.writeToFile("output.txt","There should be an input file in the specified path",true,true);
        }
    }
}