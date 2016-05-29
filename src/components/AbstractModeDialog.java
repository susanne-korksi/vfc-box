package components;

import model.Flashcard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class AbstractModeDialog extends JDialog {
    protected List<Flashcard> cards;
    protected JPanel center, info, info1, info2, front, back, bottom;
    protected JLabel frontLabel, backLabel, id, category, subCategory, calls, correctAnswers;
    protected JTextArea frontText, backText;
    protected JButton next, previous;
    private String cardFront, cardBack;
    protected int listPosition;

    public AbstractModeDialog() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        listPosition = 0;
        setSize(700, 300);
        setModal(true);
        setLayout(new BorderLayout());
        initComponents();
        addComponents();
    }

    public void setCards(List<Flashcard> cards) {
        this.cards = cards;
    }

    public void initFirstCard() {
        setCardContent(listPosition);
    }

    private void initComponents() {
        center = new JPanel(new GridLayout(2, 1));
        front = new JPanel(new GridLayout(3,1));
        back = new JPanel(new GridLayout(2,1));
        bottom = new JPanel(new FlowLayout());
        info = new JPanel(new GridLayout(2,1));
        info1 = new JPanel(new GridLayout(1,2));
        info2 = new JPanel(new GridLayout(1,3));
        next = new JButton(">>");
        previous = new JButton("<<");
        id = new JLabel("", JLabel.CENTER);
        category = new JLabel("", JLabel.CENTER);
        subCategory = new JLabel("", JLabel.CENTER);
        calls = new JLabel("", JLabel.CENTER);
        correctAnswers = new JLabel("", JLabel.CENTER);
        frontLabel = new JLabel("Kartenfront", JLabel.CENTER);
        backLabel = new JLabel("Kartenrückseite", JLabel.CENTER);
        frontText = new JTextArea();
        frontText.setLineWrap(true);
        frontText.setEditable(false);
        backText = new JTextArea();
        backText.setLineWrap(true);
        backText.setEditable(false);
    }

    public void setCardContent(int index) {
        category.setText("Kategorie: " + cards.get(index).getCategory());
        subCategory.setText("Unterkategorie: " + cards.get(index).getSubCategory());
        id.setText(String.valueOf("ID: " + cards.get(index).getCardId()));
        calls.setText(String.valueOf("Aufrufe: " + cards.get(index).getCalls()));
        correctAnswers.setText("gewusst: " + String.valueOf(cards.get(index).getCorrectAnswered()));
        cardFront = cards.get(index).getFront();
        cardBack = cards.get(index).getBack();
        frontText.setText(cardFront);
        backText.setText(cardBack);
    }

    private void addComponents() {
        info1.add(category);
        info1.add(subCategory);
        info2.add(id);
        info2.add(calls);
        info2.add(correctAnswers);
        info.add(info1);
        info.add(info2);
        front.add(info);
        front.add(frontLabel);
        front.add(frontText);
        back.add(backLabel, CENTER_ALIGNMENT);
        back.add(backText);
        center.add(front);
        center.add(back);
        add(center, BorderLayout.CENTER);

        add(bottom, BorderLayout.SOUTH);

        add(next, BorderLayout.EAST);
        add(previous, BorderLayout.WEST);
    }
}
