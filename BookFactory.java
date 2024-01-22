
package javaapplication15;

public class BookFactory {
    public static Book createBook(String type, String title, String author, double price) {
        switch (type.toLowerCase()) {
            case "mystery":
                return new MysteryBook(type, title, author, price);
            case "crime":
                return new CrimeBook(type, title, author, price);
            case "drama":
                return new DramaBook(type, title, author, price);
            case "scientific":
                return new ScientificBook(type, title, author, price);
            default:
                throw new IllegalArgumentException("Invalid book type: " + type);
        }
    }
}

