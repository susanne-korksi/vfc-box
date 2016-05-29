package components;

import exception.CardException;
import model.Flashcard;
import model.FlashcardFactory;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;

public class EditCardDialog extends JDialog {
    private FlashcardFactory factory;
    private Flashcard card;
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

    public EditCardDialog(MainWindow parent) {
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

    public void updateAndShow(FlashcardFactory factory, Flashcard card) {
        this.factory = factory;
        this.card = card;
        front.setText(card.getFront());
        back.setText(card.getBack());
        category.setText(card.getCategory());
        subCategory.setText(card.getSubCategory());
        setLocationRelativeTo(parent);
        setVisible(true);
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
            card.setBack(back.getText());
            card.setFront(front.getText());
            card.setCategory(category.getText());
            card.setSubCategory(subCategory.getText());
            card.setCalls(0);
            card.setCorrectAnswered(0);
            factory.save();
            dispose();
        } catch (CardException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}
