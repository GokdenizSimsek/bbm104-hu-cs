import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Books {
    public static int bookIdNumber = 1;     // to give different id numbers for every book
    private int bookId;
    private String typeOfBook;
    private boolean isBorrowed = false;
    private boolean isReading = false;
    private int borrowedBy;     // for use in history library command
    private int readingBy;      // for use in history library command
    private String borrowingDate;       // for use in history library command
    private String returningDate;       // deadline time of borrowed books
    private boolean isExtended = false;     // to know if it's been extended before
    private static int borrowedBooksNumber;     // for use in history library command
    private static int booksInLibraryNumber;    // for use in history library command
    private static int printedBookNumber;       // for use in history library command
    private static int handWrittenBookNumber;   // for use in history library command
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd" );     // to do some operations in correct time format

    public Books(String type, int bookId){
        this.typeOfBook = type;
        this.bookId = bookId;
    }
    public String getTypeOfBook() {
        return typeOfBook;
    }

    public static void addBook(String typeOfBook) {
        if(typeOfBook.equals("H")||typeOfBook.equals("P")){     // to check if it's given correctly
            Books book = new Books(typeOfBook, bookIdNumber);
            Main.bookIds.add(bookIdNumber);     // to check if there is a book with this id (in after commands)
            Main.books.add(book);
            if (book.typeOfBook.equals("P")) {
                FileOutput.writeToFile(Main.outputFileName, String.format("Created new book: Printed [id: %d]", bookIdNumber), true, true);
                printedBookNumber++;    // for use in history library command
            } else {
                FileOutput.writeToFile(Main.outputFileName, String.format("Created new book: Handwritten [id: %d]", bookIdNumber), true, true);
                handWrittenBookNumber++;    // for use in history library command
            }
            bookIdNumber++;     // to give different id numbers for every book
        }else {
            FileOutput.writeToFile(Main.outputFileName, "Invalid book type!", true, true);
        }
    }
    public static void borrowBook(int bookId, int memberId, String date) {
        if (Main.bookIds.contains(bookId) && Main.memberIds.contains(memberId)) {       // to check if there is a book and member with this id
            // to check if the book is reading or borrowed by another member and if the book is handwritten book or not
            if (!Main.books.get(bookId - 1).isBorrowed && !Main.books.get(bookId - 1).isReading & !Main.books.get(bookId - 1).typeOfBook.equals("H")) {
                if (Main.members.get(memberId - 1).getTypeOfMember().equals("A")) {     // if the member is academic
                    // to check borrow limits for academic members
                    if (Main.members.get(memberId - 1).getBorrowedBooksNumber() < 4) {
                        Main.members.get(memberId - 1).addBorrowedBook();   // for use in history library command
                        Main.books.get(bookId - 1).isBorrowed = true;
                        Main.books.get(bookId - 1).returningDate = ((LocalDate.parse(date,formatter)).plusWeeks(2)).format(formatter);
                        Main.books.get(bookId - 1).borrowedBy = memberId;
                        Main.books.get(bookId - 1).borrowingDate = date;    // for use in history library command
                        FileOutput.writeToFile(Main.outputFileName, String.format("The book [%d] was borrowed by member [%d] at %s", bookId, memberId, date), true, true);
                        borrowedBooksNumber++;  // for use in history library command
                    } else {
                        FileOutput.writeToFile(Main.outputFileName, "You have exceeded the borrowing limit!", true, true);
                    }
                } else {
                    // to check borrow limits for students
                    if (Main.members.get(memberId - 1).getBorrowedBooksNumber() < 2) {
                        Main.members.get(memberId - 1).addBorrowedBook();
                        Main.books.get(bookId - 1).isBorrowed = true;
                        Main.books.get(bookId - 1).returningDate = ((LocalDate.parse(date,formatter)).plusWeeks(1)).format(formatter);
                        Main.books.get(bookId - 1).borrowedBy = memberId;
                        Main.books.get(bookId - 1).borrowingDate = date;
                        FileOutput.writeToFile(Main.outputFileName, String.format("The book [%d] was borrowed by member [%d] at %s", bookId, memberId, date), true, true);
                        borrowedBooksNumber++;
                    } else {
                        FileOutput.writeToFile(Main.outputFileName, "You have exceeded the borrowing limit!", true, true);
                    }
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "You cannot borrow this book!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "Invalid id number!", true, true);
        }
    }
    public static void extendBook(int bookId, int memberId, String date) {
        if (Main.bookIds.contains(bookId) && Main.memberIds.contains(memberId)) {   // to check if there is a book and member with this id
            if (Main.books.get(bookId - 1).isBorrowed) {
                if (!Main.books.get(bookId - 1).isExtended) {   // to check if the book is extended by same member before
                    if (Main.books.get(bookId - 1).returningDate.compareTo(date) >= 0){     // to check if deadline is passed
                        if (Main.members.get(memberId -1).getTypeOfMember().equals("A")) {  // if the member is academic (2 weeks extend)
                            Main.books.get(bookId - 1).returningDate = ((LocalDate.parse(Main.books.get(bookId - 1).returningDate,formatter)).plusWeeks(2)).format(formatter);
                        } else {    // if the member is student (1 week extend)
                            Main.books.get(bookId - 1).returningDate = ((LocalDate.parse(Main.books.get(bookId - 1).returningDate,formatter)).plusWeeks(1)).format(formatter);
                        }
                        Main.books.get(bookId - 1).isExtended = true;   // to know if it's been extended before
                        FileOutput.writeToFile(Main.outputFileName, String.format("The deadline of book [%d] was extended by member [%d] at %s\nNew deadline of book [%d] is %s",
                                bookId, memberId, date, bookId, Main.books.get(bookId - 1).returningDate), true, true);
                    } else {
                        FileOutput.writeToFile(Main.outputFileName, "You cannot extend the deadline!", true, true);
                    }
                } else {
                    FileOutput.writeToFile(Main.outputFileName, "You cannot extend the deadline!", true, true);
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "The book is not borrowed!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "Invalid id number!", true, true);
        }
    }
    public static void returnBook(int bookId, int memberId, String date){
        if (Main.bookIds.contains(bookId) && Main.memberIds.contains(memberId)) {
            if (Main.books.get(bookId - 1).isBorrowed) {    // to check is it borrowed by someone or just reading in library
                if ((Main.books.get(bookId - 1).returningDate).compareTo(date) >= 0) {      // to check if deadline is passed or not
                    FileOutput.writeToFile(Main.outputFileName, String.format("The book [%d] was returned by member [%d] at %s Fee: 0", bookId, memberId, date), true, true);
                } else {
                    // if deadline date is passed, it calculates how many days have passed
                    long Fee = ChronoUnit.DAYS.between(LocalDate.parse(Main.books.get(bookId - 1).returningDate,formatter), LocalDate.parse(date,formatter));
                    FileOutput.writeToFile(Main.outputFileName, String.format("The book [%d] was returned by member [%d] at %s Fee: %d", bookId, memberId, date, Fee), true, true);
                }
                // to set is free
                Main.books.get(bookId - 1).returningDate = null;
                Main.books.get(bookId - 1).isBorrowed = false;
                Main.books.get(bookId - 1).isExtended = false;
                Main.books.get(bookId - 1).borrowedBy = 0;
                Main.books.get(bookId - 1).borrowingDate = null;
                Main.members.get(memberId - 1).subtractBorrowedBook();
                borrowedBooksNumber--;  // for use in history library command
            } else if (Main.books.get(bookId - 1).isReading) {
                Main.books.get(bookId - 1).returningDate = null;
                Main.books.get(bookId - 1).isReading = false;
                Main.books.get(bookId - 1).readingBy = 0;
                booksInLibraryNumber--;
                FileOutput.writeToFile(Main.outputFileName, String.format("The book [%d] was returned by member [%d] at %s Fee: 0", bookId, memberId, date), true, true);
            } else {
                FileOutput.writeToFile(Main.outputFileName, "The book is not borrowed or not reading!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "Invalid id number!", true, true);
        }
    }
    public static void readInLibrary(int bookId, int memberId, String date) {
        if (Main.bookIds.contains(bookId) && Main.memberIds.contains(memberId)) {
            if (!Main.books.get(bookId - 1).isBorrowed && !Main.books.get(bookId - 1).isReading) {      // to check if the book is free
                if (Main.members.get(memberId - 1).getTypeOfMember().equals("A")) {     // if the member is academic
                    Main.books.get(bookId - 1).isReading = true;
                    Main.books.get(bookId - 1).returningDate = date;
                    Main.books.get(bookId - 1).readingBy = memberId;
                    FileOutput.writeToFile(Main.outputFileName, String.format("The book [%d] was read in library by member [%d] at %s", bookId, memberId, date), true, true);
                    booksInLibraryNumber++;     // for use in history library command
                } else {
                    if (Main.books.get(bookId - 1).getTypeOfBook().equals("P")) {   // students cannot read handwritten books
                        Main.books.get(bookId - 1).isReading= true;
                        Main.books.get(bookId - 1).returningDate = date;
                        Main.books.get(bookId - 1).readingBy = memberId;
                        FileOutput.writeToFile(Main.outputFileName, String.format("The book [%d] was read in library by member [%d] at %s", bookId, memberId, date), true, true);
                        booksInLibraryNumber++; // for use in history library command
                    } else {
                        FileOutput.writeToFile(Main.outputFileName, "Students cannot read handwritten books!", true, true);
                    }
                }
            } else {
                FileOutput.writeToFile(Main.outputFileName, "You cannot read this book!", true, true);
            }
        } else {
            FileOutput.writeToFile(Main.outputFileName, "Invalid id number!", true, true);
        }
    }
    public static void historyOfBooks() {
        FileOutput.writeToFile(Main.outputFileName, String.format("\nNumber of printed books: %d", printedBookNumber), true, true);
        // to firstly give information about printed books
        for (Books book: Main.books) {
            if (book.typeOfBook.equals("P")) {
                FileOutput.writeToFile(Main.outputFileName, String.format("Printed [id: %d]", book.bookId), true, true);
            }
        }
        FileOutput.writeToFile(Main.outputFileName, String.format("\nNumber of handwritten books: %d", handWrittenBookNumber), true, true);
        for (Books book: Main.books) {
            if (book.typeOfBook.equals("H")) {
                FileOutput.writeToFile(Main.outputFileName, String.format("Handwritten [id: %d]", book.bookId), true, true);
            }
        }
        FileOutput.writeToFile(Main.outputFileName, String.format("\nNumber of borrowed books: %d", borrowedBooksNumber), true, true);
        for (Books book: Main.books) {
            if (book.isBorrowed) {
                FileOutput.writeToFile(Main.outputFileName, String.format("The book [%d] was borrowed by member [%d] at %s", book.bookId, book.borrowedBy, book.borrowingDate), true, true);
            }
        }
        FileOutput.writeToFile(Main.outputFileName, String.format("\nNumber of books read in library: %d", booksInLibraryNumber), true, true);
        for (Books book: Main.books) {
            if (book.isReading) {
                FileOutput.writeToFile(Main.outputFileName, String.format("The book [%d] was read in library by member [%d] at %s", book.bookId, book.readingBy, book.returningDate), true, true);
            }
        }
    }
}
