
package javaapplication15;

public class Book{
    protected String type;
    protected String title;
    protected String author;
    protected double price;
    
public Book(String type,String title, String author,double price) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.type=type;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public double getPrice() {
        return price;
    }
    public String getType(){
        return type;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    @Override
    public String toString() {
        return "Book{" +"title='" + title + '\'' +", author='" + author + '\'' +", price=" + price +'}';
    }
}  
class MysteryBook extends Book {
    public MysteryBook(String type, String title, String author, double price) {
        super(type, title, author, price);
    }
}

class CrimeBook extends Book {
    public CrimeBook(String type, String title, String author, double price) {
        super(type, title, author, price);
    }
}

class DramaBook extends Book {
    public DramaBook(String type, String title, String author, double price) {
        super(type, title, author, price);
    }
}

class ScientificBook extends Book {
    public ScientificBook(String type, String title, String author, double price) {
        super(type, title, author, price);
    }
}
    


