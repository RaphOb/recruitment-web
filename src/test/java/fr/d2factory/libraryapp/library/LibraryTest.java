package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.MemberFactory;
import fr.d2factory.libraryapp.member.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public class LibraryTest {
    private LibraryService library;
    private BookRepository bookRepository;
    private Book book;
    private Member student;
    private Member neighbour;
    private LocalDate borrowedDate;
    private ISBN isbn;


    @Before
    public void setup() throws IOException {
        //TODO instantiate the library and the repository
        this.isbn = new ISBN(123);
        library = new LibraryService();
        this.book = new Book("Kane et Abdel", "Jeffrey Archer", isbn);
        bookRepository = BookRepository.getInstance();
        List<Book> books = BookRepository.books;
        books.add(book);
        bookRepository.addBooks(books);
        student = MemberFactory.getMember("STUDENT");
        student.setWallet(100);
        student.setLate(false);
        ((Student) student).setSeniority(2);

        neighbour = MemberFactory.getMember("NEIGHBOURS");
        neighbour.setWallet(100);
        neighbour.setLate(false);


    }

    /**
     * if thr book is in the available list, member can borrow
     */
    @Test
    public void member_can_borrow_a_book_if_book_is_available() {

        Assert.assertNotNull(bookRepository.findBook(968787565445L));
    }

    /**
     * If book is not available, cant borrowed it
     */
    @Test
    public void borrowed_book_is_no_longer_available() {
        Assert.assertNotNull(bookRepository.findBook(book.getIsbn().getIsbnCode()));
    }


    @Test
    public void residents_are_taxed_10cents_for_each_day_they_keep_a_book() throws NoSuchFieldException, IOException {

        borrowedDate = LocalDate.of(2019, 9, 7);
        library.borrowBook(968787565445L, neighbour, borrowedDate);

        library.returnBook(bookRepository.findBookEveryWhere(968787565445L), neighbour);
        Assert.assertEquals(99.0f, neighbour.getWallet(), 0.01);
    }

    @Test
    public void students_pay_10_cents_the_first_30days() throws IOException, NoSuchFieldException {
        borrowedDate = LocalDate.of(2019, 8, 18);
        library.borrowBook(123, student, borrowedDate);
        library.returnBook(bookRepository.findBookEveryWhere(123), student);
        Assert.assertEquals(97, student.getWallet(), 0);

    }

    @Test
    public void students_in_1st_year_are_not_taxed_for_the_first_15days() throws IOException, NoSuchFieldException {
        Member student1 = MemberFactory.getMember("STUDENT");
        student1.setWallet(100);
        student1.setLate(false);
        ((Student) student1).setSeniority(1);
        borrowedDate = LocalDate.of(2019, 9, 2);
        library.borrowBook(465789453149L, student1, borrowedDate);
        library.returnBook(bookRepository.findBookEveryWhere(465789453149L), student1);
        Assert.assertEquals(100f, student1.getWallet(), 0);

    }

    @Test
    public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days() throws IOException, NoSuchFieldException {
        borrowedDate = LocalDate.of(2019, 8, 16);
        library.borrowBook(968787565445L, neighbour, borrowedDate);
        library.returnBook(bookRepository.findBookEveryWhere(968787565445L), neighbour);
        Assert.assertEquals(96.7f, neighbour.getWallet(), 1);
    }

    @Test
    public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() throws IOException, NoSuchFieldException {

        Member residents = MemberFactory.getMember("NEIGHBOURS");
        residents.setWallet(100);
        residents.setLate(false);
        borrowedDate = LocalDate.of(2019, 7, 18);
        library.borrowBook(968787565445L, residents, borrowedDate);

        library.returnBook(bookRepository.findBookEveryWhere(968787565445L), residents);
        Assert.assertEquals(93.6f, residents.getWallet(), 0.1);

    }

    @Test
    public void members_cannot_borrow_book_if_they_have_late_books() throws IOException {
        student.setLate(true);
        library.borrowBook(968787565445L, neighbour, borrowedDate);
    }
}
