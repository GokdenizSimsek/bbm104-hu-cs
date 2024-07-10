import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    // Fields for using in other classes
    public static String fileName;
    public static String outputFileName;
    public static ArrayList<Integer> bookIds = new ArrayList<Integer>();
    public static ArrayList<Integer> memberIds = new ArrayList<Integer>();
    public static ArrayList<Books> books = new ArrayList<Books>();
    public static ArrayList<Members> members = new ArrayList<Members>();

    public static void main(String[] args) {
        fileName = args[0];     // Get the input file name from command line argument and read the input file
        outputFileName = args[1];       // Get the output file name from command line argument
        FileOutput.writeToFile(outputFileName, "", false, false);
        ArrayList<ArrayList<String>> inputText = new ArrayList<>();     // to get the lines one by one (every index is one line of input file)
        String[] inputFile = FileInput.readFile(fileName, true, true);      // Strings of all input file

        // Read the input file and store each line as a list of strings in a list of lists
        for (String line : inputFile) {
            String[] items = line.split("\t");// to separate from tab space
            ArrayList<String> lineList = new ArrayList<>(Arrays.asList(items));
            inputText.add(lineList);
        }
        // Evaluate each line of the input file based on the first item (command)
        for (int line = 0; line < inputText.size(); line++) { // to evaluate lines
            switch (inputText.get(line).get(0)) { // to control of commands in lines
                case "addBook":
                    Books.addBook(inputText.get(line).get(1));
                    break;
                case "addMember":
                    Members.addMembers(inputText.get(line).get(1));
                    break;
                case "borrowBook":
                    Books.borrowBook(Integer.parseInt(inputText.get(line).get(1)),
                            Integer.parseInt(inputText.get(line).get(2)), inputText.get(line).get(3));
                    break;
                case "returnBook":
                    Books.returnBook(Integer.parseInt(inputText.get(line).get(1)),
                            Integer.parseInt(inputText.get(line).get(2)), inputText.get(line).get(3));
                    break;
                case "extendBook":
                    Books.extendBook(Integer.parseInt(inputText.get(line).get(1)),
                            Integer.parseInt(inputText.get(line).get(2)), inputText.get(line).get(3));
                    break;
                case "readInLibrary":
                    Books.readInLibrary(Integer.parseInt(inputText.get(line).get(1)),
                            Integer.parseInt(inputText.get(line).get(2)), inputText.get(line).get(3));
                    break;
                case "getTheHistory":
                    FileOutput.writeToFile(Main.outputFileName, "History of library:\n", true, true);
                    Members.historyOfMembers();
                    Books.historyOfBooks();
                    break;
            }
        }
    }
}