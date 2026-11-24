package library;

import java.time.LocalDate;

public class Loan {
    private final Member member;
    private final Copy copy;
    private final LocalDate startDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(Member member, Copy copy, LocalDate startDate) {
        if (member == null || copy == null || startDate == null) {
            throw new IllegalArgumentException("Member, copy, and start date cannot be null");
        }
        this.member = member;
        this.copy = copy;
        this.startDate = startDate;
        this.dueDate = startDate.plusDays(14);
        this.returnDate = null;
    }

    public Member getMember() {
        return member;
    }

    public Copy getCopy() {
        return copy;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isOpen() {
        return returnDate == null;
    }

    public boolean isLate(LocalDate today) {
        if (today == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (returnDate != null) {
            return returnDate.isAfter(dueDate);
        }
        return today.isAfter(dueDate);
    }

    public void close(LocalDate returnDate) {
        if (returnDate == null) {
            throw new IllegalArgumentException("Return date cannot be null");
        }
        this.returnDate = returnDate;
    }
}
