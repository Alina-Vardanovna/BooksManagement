package storage;

import model.Book;

public class BookStorage {

    private Book[] array = new Book[10];
    private int size = 0;

    public void add(Book book) {
        if (size == array.length) {
            increaseArray();
        }
        array[size++] = book;
    }

    private void increaseArray() {
        Book[] temp = new Book[array.length + 10];
        System.arraycopy(array, 0, temp, 0, size);
        array = temp;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.println(i + ". " + array[i] + " ");
        }
    }

    public int getSize() {
        return size;
    }

    public void delete(int index) {
        if (index >= 0 && index < size) {
            for (int i = index; i < size; i++) {
                array[index] = array[index + 1];
            }
            size--;
            System.out.println("Book deleted");
        } else {
            System.out.println("Index out of bounds");
        }
    }

    public Book getBookByIndex(int index) {
        if (index >= 0 && index < size) {
            return array[index];
        }
        return null;
    }

    public void printBookByAuthorName(String authorName) {
        for (int i = 0; i < size; i++) {
            if (array[i].getAuthor().getName().equals(authorName)) {
                System.out.println(array[i]);
            }
        }
    }

    public void printBookByGenre(String genre) {
        for (int i = 0; i < size; i++) {
            if (array[i].getGenre().equals(genre)) {
                System.out.println(array[i]);
            }
        }
    }

    public void printBookByPriceRange(double price1, double price2) {
        for (int i = 0; i < size; i++) {
            if (price1 < price2 && array[i].getPrice() > price1 && array[i].getPrice() < price2) {
                System.out.println(array[i]);
            } else {
                System.out.println("We don't have any book in this price range.");
            }
        }
    }

}