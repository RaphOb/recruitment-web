package fr.d2factory.libraryapp.book;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
    private static Map<ISBN, Book> availableBooks = new HashMap<>();
    public static Map<Book, LocalDate> borrowedBooks = new HashMap<>();
    public static List<Book> books = new ArrayList<>();

    private static BookRepository bookRepositoryInstance;


    private BookRepository() throws FileNotFoundException {
        availableBooks = new HashMap<>();
        borrowedBooks = new HashMap<>();

        //Deserialize books.json
        Gson googleJson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/books.json"));
        Type type = new TypeToken<List<Book>>() {
        }.getType();
        books = googleJson.fromJson(bufferedReader, type);
    }

    //Singleton
    public static BookRepository getInstance() throws FileNotFoundException {
        if (bookRepositoryInstance == null)
            bookRepositoryInstance = new BookRepository();
        return bookRepositoryInstance;
    }


    public void addBooks(List<Book> books) {
        for (Book b : books) {
            availableBooks.put(b.getIsbn(), b);
        }
    }

    /**
     * Find book in available and Borrowed list
     *
     * @param isbnCode
     * @return
     */
    public Book findBookEveryWhere(long isbnCode) {
        for (ISBN isbn : availableBooks.keySet()) {
            if (isbn.getIsbnCode() == isbnCode) {
                return availableBooks.get(isbn);
            }
        }

        for (Book book : borrowedBooks.keySet()) {
            if (book.getIsbn().getIsbnCode() == isbnCode
                    && book.getIsbn() != null) {
                return book;
            }
        }
        return null;
    }

    /**
     * Find book available
     *
     * @param isbnCode
     * @return
     */
    public Book findBook(long isbnCode) {

        for (ISBN isbn : availableBooks.keySet()) {
            if (isbn.getIsbnCode() == isbnCode) {
                return availableBooks.get(isbn);
            }
        }
        return null;
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt) {

        borrowedBooks.put(book, borrowedAt);
    }

    public void removeFromBorrow(Book book) {
        borrowedBooks.keySet().removeIf(key -> key == book);
    }

    public void saveAvailableBook(Book book) {
        availableBooks.put(book.getIsbn(), book);
    }

    public void removeFromAvailableBook(Book book) {
        availableBooks.keySet().removeIf(key -> key == book.getIsbn());
    }

    /**
     * return date from book borrowed
     *
     * @param book
     * @return
     */
    public LocalDate findBorrowedBookDate(Book book) {
        return borrowedBooks.get(book);
    }


}
