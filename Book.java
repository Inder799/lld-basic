import java.math.BigDecimal;

public class Book {
    private final String title;
    private final String author;
    private final String isbn;
    private BigDecimal price;
    private int stockQuantity;

    Book(String title, String author, String isbn, BigDecimal price, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.stockQuantity = quantity;
    }

    public void purchaseBook(int quantity) {
        checkQuantity(quantity);
        if(this.stockQuantity < quantity) {
            throw new IllegalArgumentException("Available stock is less than required Quantity");
        }
        this.stockQuantity -= quantity;
    }

    public void restock(int quantity) {
        checkQuantity(quantity);
        this.stockQuantity += quantity; 
    }

    private void checkQuantity(int quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity can't be less than or equal to 0");
        }
    }
}
