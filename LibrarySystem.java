import java.util.Scanner;
import java.util.ArrayList;

// Parent Class
class Book {
    int id; 
    String title; 
    String author; 
    boolean available;

    public Book(int id, String title, String author) {
        this.id = id; 
        this.title = title; 
        this.author = author; 
        this.available = true;
    }

    public void displayInfo() {
        System.out.println("ID: " + id + " | " + title + " | Available: " + available);
    }
}

// Inheritance & Overriding
class EBook extends Book {
    double size;
    public EBook(int id, String title, String author, double size) { 
        super(id, title, author); 
        this.size = size; 
    }
    
    @Override
    public void displayInfo() {
        System.out.println("E-Book ID: " + id + " | " + title + " | Size: " + size + "MB | Available: " + available);
    }
}

class PrintedBook extends Book {
    int pages;
    public PrintedBook(int id, String title, String author, int pages) { 
        super(id, title, author); 
        this.pages = pages; 
    }
    
    @Override
    public void displayInfo() {
        System.out.println("[Printed] ID: " + id + " | " + title + " | Pages: " + pages + " | Available: " + available);
    }
}

// Logic Class
class Library {
    ArrayList<Book> books = new ArrayList<>();

    // Overloading
    void addBook(int id, String t, String a) { 
        books.add(new Book(id, t, a)); 
    }
    void addBook(int id, String t, String a, int p) { 
        books.add(new PrintedBook(id, t, a, p)); 
    }
    void addBook(int id, String t, String a, double s) { 
        books.add(new EBook(id, t, a, s)); 
    }

    Book find(int id) {
        for (Book b : books) if (b.id == id) return b;
        return null;
    }
}

// Main Method
public class LibrarySystem {
    public static void main(String[] args) {
        Library lib = new Library();
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice != 8) {
            System.out.print("\n1.Add 2.Search 3.Borrow 4.Return 5.All 6.Remove 7.Type 8.Exit\nChoice: ");
            choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Title: ");
                String t = sc.nextLine();

                System.out.print("Author: ");
                String a = sc.nextLine();

                if (lib.find(id) != null) { 
                    System.out.println("ID Exists!"); 
                    continue; 
                }
                
                System.out.print("Type (1:Basic 2:Print 3:E): ");
                int type = sc.nextInt();

                if (type == 2) {
                    System.out.print("Pages: ");
                    lib.addBook(id, t, a, sc.nextInt());
                }
                else if (type == 3) {
                    System.out.print("Size: ");
                    lib.addBook(id, t, a, sc.nextDouble());
                }
                else lib.addBook(id, t, a);

            } else if (choice == 2 || choice == 3 || choice == 4 || choice == 6) {
                System.out.print("Enter ID: ");
                Book b = lib.find(sc.nextInt());

                if (b == null) System.out.println("Not found");

                else if (choice == 2) b.displayInfo();

                else if (choice == 3) { 
                    if (b.available) {
                        b.available = false;
                        System.out.println("Borrowed");
                    } else System.out.println("Already borrowed");
                }

                else if (choice == 4) { 
                    if (!b.available) {
                        b.available = true;
                        System.out.println("Returned");
                    } else System.out.println("Not borrowed");
                }

                else if (choice == 6) { 
                    lib.books.remove(b); 
                    System.out.println("Removed"); 
                }

            } else if (choice == 5) {
                for (Book b : lib.books) b.displayInfo(); // Polymorphism

            } else if (choice == 7) {
                System.out.print("1:Printed 2:EBook: ");
                int t = sc.nextInt();
                for (Book b : lib.books) {
                    if (t == 1 && b instanceof PrintedBook) b.displayInfo();
                    if (t == 2 && b instanceof EBook) b.displayInfo();
                }
            }
        }
    }
}