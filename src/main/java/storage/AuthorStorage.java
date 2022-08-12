package storage;

import exception.AuthorNotFoundException;
import model.Author;

public class AuthorStorage {

    private Author[] array = new Author[10];
    private int size = 0;

    public void add(Author author) {
        if (size == array.length) {
            increaseArray();
        }
        array[size++] = author;
    }

    private void increaseArray() {
        Author[] temp = new Author[array.length + 10];
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
            System.out.println("Author deleted");
        } else {
            System.out.println("Index out of bounds");
        }
    }

    public Author getAuthorByIndex(int index) throws AuthorNotFoundException {
        if (index >= 0 && index < size) {
            //TODO: check if author exists
            return array[index];
        } else {
            throw new AuthorNotFoundException("Author with " + index + " index does not exists.");
        }
    }

}


