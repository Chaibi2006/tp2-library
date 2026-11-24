package library;

public class Copy {
    private final String inventoryNumber;
    private final Book book;
    private boolean available;

    public Copy(String inventoryNumber, Book book) {
        if (inventoryNumber == null || inventoryNumber.isBlank()) {
            throw new IllegalArgumentException("Inventory number cannot be null or blank");
        }
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        this.inventoryNumber = inventoryNumber;
        this.book = book;
        this.available = true;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public Book getBook() {
        return book;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
