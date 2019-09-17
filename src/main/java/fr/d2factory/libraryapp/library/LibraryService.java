package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.member.Member;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LibraryService implements Library {

    private BookRepository bookRepository;


    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException, IOException {
        bookRepository = BookRepository.getInstance();
        if (bookRepository.findBook(isbnCode) == null) {
            System.out.println("No FOUND");
            return null;
        } else if (member.isLate()) throw new HasLateBooksException();

        else {
            Book book = bookRepository.findBook(isbnCode);
            bookRepository.saveBookBorrow(book, borrowedAt);
            bookRepository.removeFromAvailableBook(book);
            return book;
        }
    }

    @Override
    public void returnBook(Book book, Member member) throws NoSuchFieldException, IOException {
        bookRepository = BookRepository.getInstance();
        if (book != null) {
            LocalDate startRent = bookRepository.findBorrowedBookDate(book);
            int numberOfdays = (int) ChronoUnit.DAYS.between(startRent, LocalDate.now());

            member.setWallet(member.getWallet() - member.payBook(numberOfdays));
            bookRepository.saveAvailableBook(book);
            bookRepository.removeFromBorrow(book);
        } else throw new NoSuchFieldException();
    }
}

