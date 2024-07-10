public class Members {
    public static int memberIdNumber = 1;
    private int memberId;
    private String typeOfMember;
    private int borrowedBooksNumber = 0;
    private boolean isReading = false;
    private static int studentNumber;
    private static int academicNumber;

    public Members(String type, int memberId){
        this.typeOfMember = type;
        this.memberId = memberId;
    }
    public int getBorrowedBooksNumber() {
        return borrowedBooksNumber;
    }
    public String getTypeOfMember() {
        return typeOfMember;
    }

    public static void addMembers(String typeOfMember){
        if(typeOfMember.equals("A")||typeOfMember.equals("S")){     // to check if it's given correctly
            Members member = new Members(typeOfMember, memberIdNumber);
            Main.memberIds.add(memberIdNumber);     // to check if there is a member with this id (in after commands)
            Main.members.add(member);
            if (member.typeOfMember.equals("S")) {
                FileOutput.writeToFile(Main.outputFileName, String.format("Created new member: Student [id: %d]", memberIdNumber), true, true);
                studentNumber++;     // for use in history library command
            } else {
                FileOutput.writeToFile(Main.outputFileName, String.format("Created new member: Academic [id: %d]", memberIdNumber), true, true);
                academicNumber++;    // for use in history library command
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "Invalid member type!", true, true);
        }
        memberIdNumber++;   // to give different id numbers for every member
    }
    public void addBorrowedBook() {
        borrowedBooksNumber++;  // for use in history library command
    }
    public void subtractBorrowedBook() {
        borrowedBooksNumber--;  // for use in history library command
    }
    public static void historyOfMembers() {
        FileOutput.writeToFile(Main.outputFileName, String.format("Number of students: %d", studentNumber), true, true);
        // to firstly give information about students
        for (Members member: Main.members) {
            if (member.typeOfMember.equals("S")) {
                FileOutput.writeToFile(Main.outputFileName, String.format("Student [id: %d]", member.memberId), true, true);
            }
        }
        FileOutput.writeToFile(Main.outputFileName, String.format("\nNumber of academics: %d", academicNumber), true, true);
        for (Members member: Main.members) {
            if (member.typeOfMember.equals("A")) {
                FileOutput.writeToFile(Main.outputFileName, String.format("Academic [id: %d]", member.memberId), true, true);
            }
        }
    }
}
