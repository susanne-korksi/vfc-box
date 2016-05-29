package components;

import model.Flashcard;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;

public class ViewCardDialog extends JDialog {
    private JLabel id, idText;
    private JLabel category, catText;
    private JLabel subCategory, subCatText;
    private JLabel front, frontText;
    private JLabel back, backText;
    private JLabel calls, callsText;
    private JLabel correctAnswers, ansText;
    private MainWindow parent;

    public ViewCardDialog(MainWindow parent) {
        this.parent = parent;
        setTitle("Kartendetails");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7,2));
        setSize(530, 150);
        setModal(true);
        initComponents();
        addComponents();
    }

    public void updateAndShow(Flashcard card) {
        idText.setText(String.valueOf(card.getCardId()));
        callsText.setText(String.valueOf(card.getCalls()));
        ansText.setText(String.valueOf(card.getCorrectAnswered()));
        catText.setText(card.getCategory());
        subCatText.setText(card.getSubCategory());
        frontText.setText(card.getFront());
        backText.setText(card.getBack());
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void initComponents() {
        id = new JLabel("ID:");
        idText = new JLabel();
        calls = new JLabel("Aufrufe:");
        callsText = new JLabel();
        correctAnswers = new JLabel("korrekt beantwortet:");
        ansText = new JLabel();
        category = new JLabel("Kategorie:");
        catText = new JLabel();
        subCategory = new JLabel("Unterkategorie:");
        subCatText = new JLabel();
        front = new JLabel("Kartenvorderseite:");
        frontText = new JLabel();
        back = new JLabel("Kartenrückseite:");
        backText = new JLabel();
    }

    private void addComponents() {
        add(id);
        add(idText);
        add(calls);
        add(callsText);
        add(correctAnswers);
        add(ansText);
        add(category);
        add(catText);
        add(subCategory);
        add(subCatText);
        add(front);
        add(frontText);
        add(back);
        add(backText);
    }
}
