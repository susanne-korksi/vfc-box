package components;

import exception.CardException;
import model.Flashcard;
import model.FlashcardFactory;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;

public class CardCreator extends JDialog {
    private FlashcardFactory factory;
    private JLabel cat;
    private JLabel subCat;
    private JLabel fr;
    private JLabel ba;
    private JTextField category;
    private JTextField subCategory;
    private JTextField front;
    private JTextField back;
    private JButton confirm, abort;
    private MainWindow parent;

    public CardCreator(FlashcardFactory factory, MainWindow parent) {
        this.factory = factory;
        this.parent = parent;
        setTitle("Eingabefelder");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5,2));
        setModal(true);
        setSize(530, 150);
        initComponents();
        addComponents();
        addListener();
    }

    public void updateAndShow() {
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public String getFront() {
        return front.getText();
    }

    public String getBack() {
        return back.getText();
    }

    public String getCategory() {
        return category.getText();
    }

    public String getSubCategory() {
        return subCategory.getText();
    }

    private void initComponents() {
        cat = new JLabel("Kategorie:");
        subCat = new JLabel("Unterkategorie:");
        fr = new JLabel("Kartenvorderseite:");
        ba = new JLabel("Kartenrückseite:");
        front = new JTextField();
        back = new JTextField();
        category = new JTextField();
        subCategory = new JTextField();
        confirm = new JButton("Ok");
        abort = new JButton("Abbruch");
        front.setBounds(30, 50, 150, 25);
        back.setBounds(30, 50, 150, 25);
        category.setBounds(30, 50, 150, 25);
        subCategory.setBounds(30, 50, 150, 25);
    }

    private void addComponents() {
        add(cat);
        add(category);
        add(subCat);
        add(subCategory);
        add(fr);
        add(front);
        add(ba);
        add(back);
        add(confirm);
        add(abort);
    }

    private void addListener() {
        confirm.addActionListener(event -> create());
        abort.addActionListener(event -> dispose());
    }

    private void create() {
        try {
            factory.addFlashcard(new Flashcard(getCategory(), getSubCategory(), getFront(), getBack()));
            factory.save();
            dispose();
        } catch (CardException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}
