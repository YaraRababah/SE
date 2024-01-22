
package javaapplication15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BookStoreFrame extends JFrame implements InventoryObserver {
    private DefaultListModel<Book> bookListModel;
    private JList<Book> bookList;
    private DefaultListModel<Book> updateListModel;
    public BookStoreFrame() {
        setTitle("Bookstore Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadBookData();
        BookInventory.getInstance().addObserver(this); }
 
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(1, 1, 1, 1);
        JButton addButton = new JButton("Add New Book");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddBookDialog();
            }});
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(addButton, constraints);
        JButton searchButton = new JButton("Search Book");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchBookDialog();
            }});
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(searchButton, constraints);
        bookListModel = new DefaultListModel<>();
        bookList = new JList<>(bookListModel);
        JScrollPane scrollPane = new JScrollPane(bookList);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        panel.add(scrollPane, constraints);
        JButton updateInventoryButton = new JButton("Update Inventory");
        updateInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateInventoryPanel();
            }});
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        panel.add(updateInventoryButton, constraints);
        add(panel);}
    private void showUpdateInventoryPanel() {
        JFrame updatePanelFrame = new JFrame("Update Inventory");
        updatePanelFrame.setSize(400, 300);
        updatePanelFrame.setLocationRelativeTo(this);
        updatePanelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel updatePanel = new JPanel(new BorderLayout());
        updateListModel = new DefaultListModel<>();
        final DefaultListModel<Book> updateListModel = new DefaultListModel<>();
        final JList<Book> updateList = new JList<>(updateListModel);
        JScrollPane updateScrollPane = new JScrollPane(updateList);
        updatePanel.add(updateScrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton modifyButton = new JButton("Modify");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = updateList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Book selectedBook = updateListModel.getElementAt(selectedIndex);
                    showModifyBookDialog(selectedBook);
                    List<String> bookLines = new ArrayList<>();
                    for (int i = 0; i < updateListModel.size(); i++) {
                        Book book = updateListModel.getElementAt(i);
                        bookLines.add(book.getType() + ", " +book.getTitle() + ", " +book.getAuthor() + ", " +book.getPrice());}
                    FileHandler.getInstance().writeToFile("books.txt", bookLines);
                    BookInventory.getInstance().updateObservers();
                }}});
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = updateList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Book selectedBook = updateListModel.getElementAt(selectedIndex);
                    updateListModel.removeElement(selectedBook);
                    List<String> bookLines = new ArrayList<>();
                    for (int i = 0; i < updateListModel.size(); i++) {
                        Book book = updateListModel.getElementAt(i);
                        bookLines.add(book.getType() + ", " +book.getTitle() + ", " +book.getAuthor() + ", "+book.getPrice());}
                    FileHandler.getInstance().writeToFile("books.txt", bookLines);
                    BookInventory.getInstance().updateObservers();
                }}});
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        updatePanel.add(buttonPanel, BorderLayout.SOUTH);
        for (int i = 0; i < bookListModel.size(); i++) {
            updateListModel.addElement(bookListModel.getElementAt(i));}
        updatePanelFrame.add(updatePanel);
        updatePanelFrame.setVisible(true); }
    private void showModifyBookDialog(Book book) {
        JTextField titleField = new JTextField(book.getTitle());
        JTextField authorField = new JTextField(book.getAuthor());
        JTextField priceField = new JTextField(String.valueOf(book.getPrice()));
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Mystery", "Crime", "Drama", "Scientific"});
        typeComboBox.setSelectedItem(book.getType());
        Object[] message = {
                "Book Type:", typeComboBox,
                "Title:", titleField,
                "Author:", authorField,
                "Price:", priceField };
        int option = JOptionPane.showConfirmDialog(this, message, "Modify Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String selectedType = typeComboBox.getSelectedItem().toString();
                Book modifiedBook = BookFactory.createBook(
                    selectedType,
                    titleField.getText().trim(),
                    authorField.getText().trim(),
                    Double.parseDouble(priceField.getText().trim()));
                int index = bookListModel.indexOf(book);
                if (index != -1) {
                    bookListModel.setElementAt(modifiedBook, index);
                    updateListModel.setElementAt(modifiedBook, updateListModel.indexOf(book));
                    List<String> bookLines = new ArrayList<>();
                    for (int i = 0; i < bookListModel.size(); i++) {
                        Book updatedBook = bookListModel.getElementAt(i);
                        bookLines.add(updatedBook.getType() + ", " +updatedBook.getTitle() + ", " +updatedBook.getAuthor() + ", " +updatedBook.getPrice());}
                    FileHandler.getInstance().writeToFile("books.txt", bookLines);
                    BookInventory.getInstance().updateObservers();
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Book not found in the list.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid price format. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }}}
    private void loadBookData() {
        List<String> bookLines = FileHandler.getInstance().readFromFile("books.txt");
        for (String line : bookLines) {
            String[] bookData = line.split(",");
            String type = bookData[0].trim();
            String title = bookData[1].trim();
            String author = bookData[2].trim();
            double price = Double.parseDouble(bookData[3].trim());
            Book book = BookFactory.createBook(type, title, author, price);
            bookListModel.addElement(book);
        }}
    private void showAddBookDialog() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField priceField = new JTextField();
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Mystery", "Crime", "Drama", "Scientific"});
        Object[] message = {
                "Book Type:", typeComboBox,
                "Title:", titleField,
                "Author:", authorField,
                "Price:", priceField };
        int option = JOptionPane.showConfirmDialog(this, message, "Add New Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String type = typeComboBox.getSelectedItem().toString();
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                Book newBook = BookFactory.createBook(type, title, author, price);
                bookListModel.addElement(newBook);
                List<String> bookLines = FileHandler.getInstance().readFromFile("books.txt");
                bookLines.add(type + ", " + title + ", " + author + ", " + price);
                FileHandler.getInstance().writeToFile("books.txt", bookLines);
                BookInventory.getInstance().updateObservers();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid price format. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }}}
    private void showSearchBookDialog() {
        String searchTerm = JOptionPane.showInputDialog(this, "Enter book title to search:", "Search Book", JOptionPane.PLAIN_MESSAGE);
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            DefaultListModel<Book> searchResults = new DefaultListModel<>();
            for (int i = 0; i < bookListModel.size(); i++) {
                Book book = bookListModel.getElementAt(i);
                if (book.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                    searchResults.addElement(book);
                }}
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No matching books found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JList<Book> searchResultsList = new JList<>(searchResults);
                JOptionPane.showMessageDialog(this, new JScrollPane(searchResultsList), "Search Results", JOptionPane.PLAIN_MESSAGE);
            }}}
    @Override
    public void update() {
        bookListModel.clear();
        loadBookData();
        repaint();
    }
}


