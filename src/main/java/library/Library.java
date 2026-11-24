package library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Library {
    private static final int MAX_OPEN_LOANS = 5;

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Copy> copies = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private final List<Loan> loans = new ArrayList<>();

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        books.put(book.getIsbn(), book);
    }

    public void addCopy(String isbn, String inventoryNumber) {
        Book book = books.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Unknown ISBN: " + isbn);
        }
        copies.put(inventoryNumber, new Copy(inventoryNumber, book));
    }

    public void registerMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        members.put(member.getNumber(), member);
    }

    public Copy findCopy(String inventoryNumber) {
        Copy copy = copies.get(inventoryNumber);
        if (copy == null) {
            throw new IllegalArgumentException("Unknown inventory number: " + inventoryNumber);
        }
        return copy;
    }

    public Loan lend(String memberNumber, String inventoryNumber, LocalDate today) {
        Member member = members.get(memberNumber);
        if (member == null) {
            throw new IllegalArgumentException("Unknown member number: " + memberNumber);
        }
        Copy copy = findCopy(inventoryNumber);

        if (!copy.isAvailable()) {
            throw new IllegalStateException("Copy is not available: " + inventoryNumber);
        }

        long openLoans = loans.stream()
                .filter(l -> l.isOpen() && l.getMember().getNumber().equals(memberNumber))
                .count();
        if (openLoans >= MAX_OPEN_LOANS) {
            throw new IllegalStateException("Member has reached maximum open loans (" + MAX_OPEN_LOANS + ")");
        }

        copy.setAvailable(false);
        Loan loan = new Loan(member, copy, today);
        loans.add(loan);
        return loan;
    }

    public void returnCopy(String inventoryNumber, LocalDate today) {
        Copy copy = findCopy(inventoryNumber);
        Loan openLoan = loans.stream()
                .filter(l -> l.isOpen() && l.getCopy().getInventoryNumber().equals(inventoryNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No open loan for copy: " + inventoryNumber));

        openLoan.close(today);
        copy.setAvailable(true);
    }

    public List<Book> searchByTitle(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        String lower = keyword.toLowerCase(Locale.ROOT);
        return books.values().stream()
                .filter(b -> b.getTitle().toLowerCase(Locale.ROOT).contains(lower))
                .collect(Collectors.toList());
    }
}
