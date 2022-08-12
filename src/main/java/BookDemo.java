import command.AdminCommands;
import command.UserCommands;
import exception.AuthorNotFoundException;
import model.Author;
import model.Book;
import model.Role;
import model.User;
import ourEnum.Gender;
import storage.AuthorStorage;
import storage.BookStorage;
import storage.UserStorage;

import java.util.Date;
import java.util.Scanner;

import static command.AdminCommands.printAdminCommands;
import static util.DateUtil.stringToDate;

public class BookDemo {

    private static final Scanner scanner = new Scanner(System.in);
    private static final BookStorage bookStorage = new BookStorage();
    private static final AuthorStorage authorStorage = new AuthorStorage();
    private static final UserStorage userStorage = new UserStorage();

    private static User currentUser = null;

    public static void main(String[] args) {

        Author author1 = new Author("Ջեք", "Լոնդոն", "x", Gender.MALE);
        authorStorage.add(author1);
        Author author2 = new Author("Մարիամ", "Պետրոսյան", "y", Gender.FEMALE);
        authorStorage.add(author2);
        Author author3 = new Author("Մարկ", "Արեն", "z", Gender.MALE);
        authorStorage.add(author3);

        User admin = new User("admin", "admin", "admin@gmail.com", "admin", Role.ADMIN);
        userStorage.add(admin);
        bookStorage.add(new Book("Սպիտակ Ժանիք", author1, 6, 300, "Արկածային", admin, new Date()));
        bookStorage.add(new Book("Մարտին Իդեն", author2, 3, 1200, "Դրամա", admin, new Date()));
        bookStorage.add(new Book("Տուն որտեղ", author3, 11, 600, "Աբստրակտ", admin, new Date()));

        boolean run = true;
        while (run) {
            AdminCommands.printLoginCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case 0:
                    run = false;
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                default:
                    System.out.println("Invalid command, please try again");
            }
        }

    }

    private static void login() {
        System.out.println("Input gmail, password");
        String gmailPasswordStr = scanner.nextLine();
        String[] gmailPassword = gmailPasswordStr.split(",");
        User user = userStorage.getUserByGmail(gmailPassword[0]);
        if (user == null) {
            System.out.println("User doest not exists!");
        } else {
            if (!user.getPassword().equals(gmailPassword[1])) {
                System.out.println("Password is wrong!");
            } else {
                currentUser = user;
                if (user.getRole() == Role.ADMIN) {
                    adminLogin();
                } else if (user.getRole() == Role.USER) {
                    userLogin();
                }
            }
        }
    }

    private static void register() {
        System.out.println("Input name, surname, gmail, password");
        String userDataStr = scanner.nextLine();
        String[] userData = userDataStr.split(",");
        if (userData.length < 4) {
            System.out.println("Please input correct user data");
        } else {
            if (userStorage.getUserByGmail(userData[2]) == null) {
                User user = new User();
                user.setName(userData[0]);
                user.setSurname(userData[1]);
                user.setGmail(userData[2]);
                user.setPassword(userData[3]);
                user.setRole(Role.USER);
                userStorage.add(user);
                System.out.println("User registered!");
            } else {
                System.out.println("User with " + userData[2] + " already exists");
            }
        }
    }

    private static void userLogin() {
        System.out.println("Welcome, " + currentUser.getName());
        boolean run = true;
        while (run) {
            UserCommands.printUserCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case UserCommands.LOGOUT:
                    currentUser = null;
                    run = false;
                    break;
                case UserCommands.ADD_BOOK:
                    addBook();
                    break;
                case UserCommands.PRINT_ALL_BOOKS:
                    bookStorage.print();
                    break;
                case UserCommands.PRINT_BOOKS_BY_AUTHOR_NAME:
                    printBooksByAuthorName();
                    break;
                case UserCommands.PRINT_BOOKS_BY_GENRE:
                    printBooksByGenre();
                    break;
                case UserCommands.PRINT_BOOKS_BY_PRICE_RANGE:
                    printBooksByPriceRange();
                    break;
                case UserCommands.PRINT_ALL_AUTHORS:
                    authorStorage.print();
                    break;
                default:
                    System.out.println("Invalid command, please try again");
            }
        }
    }

    private static void adminLogin() {
        boolean run = true;
        while (run) {
            AdminCommands.printAdminCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case AdminCommands.LOGOUT:
                    currentUser = null;
                    run = false;
                    break;
                case AdminCommands.ADD_BOOK:
                    addBook();
                    break;
                case AdminCommands.PRINT_ALL_BOOKS:
                    bookStorage.print();
                    break;
                case AdminCommands.PRINT_BOOKS_BY_AUTHOR_NAME:
                    printBooksByAuthorName();
                    break;
                case AdminCommands.PRINT_BOOKS_BY_GENRE:
                    printBooksByGenre();
                    break;
                case AdminCommands.PRINT_BOOKS_BY_PRICE_RANGE:
                    printBooksByPriceRange();
                    break;
                case AdminCommands.ADD_AUTHOR:
                    addAuthor();
                    break;
                case AdminCommands.PRINT_ALL_AUTHORS:
                    authorStorage.print();
                    break;
                default:
                    System.out.println("Invalid command, please try again");
            }
        }
    }

    private static void addAuthor() {
        System.out.println("Please input author name:");
        String name = scanner.nextLine();
        System.out.println("Please input author surname:");
        String surname = scanner.nextLine();
        System.out.println("Please input author's gmail:");
        String gmail = scanner.nextLine();
        System.out.println("Please choose author's gender:");
        Gender gender = chooseGender();

        Author author = new Author(name, surname, gmail, gender);
        authorStorage.add(author);
        System.out.println("Author created!");
    }

    private static Gender chooseGender() {
        Gender gender;
        try {
            gender = Gender.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("We don't have such a gender. Please try again!");
            gender = chooseGender();
        }
        return gender;
    }

    private static void printBooksByPriceRange() {
        System.out.println("Please input start price:");
        try {
            double price1, price2;
            price1 = Double.parseDouble(scanner.nextLine());
            System.out.println("Please input end price:");
            price2 = Double.parseDouble(scanner.nextLine());
            bookStorage.printBookByPriceRange(price1, price2);
        } catch (NumberFormatException e) {
            System.out.println("You can input only number. Please try again!");
            printBooksByPriceRange();
        }
    }

    private static void printBooksByGenre() {
        System.out.println("Please input genre:");
        String genre = scanner.nextLine();
        bookStorage.printBookByGenre(genre);
    }

    private static void printBooksByAuthorName() {
        System.out.println("Please input author name:");
        String author = scanner.nextLine();
        bookStorage.printBookByAuthorName(author);
    }

    private static void addBook() {
        if (authorStorage.getSize() == 0) {
            System.out.println("Please add Author:");
            addAuthor();
        } else {
            authorStorage.print();
            System.out.println("Please choose Author index:");
            int authorIndex = Integer.parseInt(scanner.nextLine());
            try {
                Author author = authorStorage.getAuthorByIndex(authorIndex);
                System.out.println("Please input title:");
                String title = scanner.nextLine();

                System.out.println("Please input count:");
                String countStr = scanner.nextLine();

                System.out.println("Please input price:");
                String priceStr = scanner.nextLine();

                System.out.println("Please input genre:");
                String genre = scanner.nextLine();

                int count = Integer.parseInt(countStr);
                double price = Double.parseDouble(priceStr);
                Book book = new Book(title, author, count, price, genre, currentUser, new Date());
                bookStorage.add(book);
                System.out.println("Thank you, book added.");
            } catch (AuthorNotFoundException e) {
                System.out.println(e.getMessage());
                addBook();
            }

        }

    }

}



