package command;

public interface UserCommands {

    int LOGOUT = 0;
    int ADD_BOOK = 1;
    int PRINT_ALL_BOOKS = 2;
    int PRINT_BOOKS_BY_AUTHOR_NAME = 3;
    int PRINT_BOOKS_BY_GENRE = 4;
    int PRINT_BOOKS_BY_PRICE_RANGE = 5;
    int PRINT_ALL_AUTHORS = 6;

    static void printUserCommands() {
        System.out.println("Please input " + UserCommands.LOGOUT + " for logout:");
        System.out.println("Please input " + UserCommands.ADD_BOOK + " to add a book:");
        System.out.println("Please input " + UserCommands.PRINT_ALL_BOOKS + " for print all books:");
        System.out.println("Please input " + UserCommands.PRINT_BOOKS_BY_AUTHOR_NAME + " for print books by author name:");
        System.out.println("Please input " + UserCommands.PRINT_BOOKS_BY_GENRE + " for print books by genre:");
        System.out.println("Please input " + UserCommands.PRINT_BOOKS_BY_PRICE_RANGE + " for print books by price range:");
        System.out.println("Please input " + UserCommands.PRINT_ALL_AUTHORS + " for print all authors:");
    }

}
