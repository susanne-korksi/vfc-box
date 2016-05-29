package components;

import exception.CardException;
import model.DeckOld;
import model.Flashcard;
import model.FlashcardFactory;
import view.CardOverview;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeckCreator extends JDialog {
    private CardOverview cardOverview;
    private FlashcardFactory factory;
    private JPanel top, bottom;
    private JButton confirm, abort;
    private JLabel name;
    private JTextField nameText;
    private MainWindow parent;

    public DeckCreator(CardOverview cardOverview, FlashcardFactory factory, MainWindow parent) {
        this.cardOverview = cardOverview;
        this.parent = parent;
        this.factory = factory;
        setModal(true);
        setTitle("Erzeugen einer Sammlung");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(530,300);
        initComponents();
        addComponents();
        addListener();
    }

    public void updateAndShow() {
        setLocationRelativeTo(parent);
        add(cardOverview);
        cardOverview.updateAndShow();
        setVisible(true);
    }

    private void initComponents() {
        top = new JPanel(new GridLayout(1,2));
        name = new JLabel("Name der Sammlung: ");
        nameText = new JTextField();
        nameText.setBounds(30, 50, 150, 25);
        top.add(name);
        top.add(nameText);

        bottom = new JPanel(new FlowLayout());
        confirm = new JButton("Bestätigen");
        abort = new JButton("Abbruch");
        bottom.add(confirm);
        bottom.add(abort);
    }

    private void addComponents() {
        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
    }

    private void addListener() {
        abort.addActionListener(event -> dispose());
        confirm.addActionListener(event -> {
            int[] selection = cardOverview.getSelection();
            List<Flashcard> list = new ArrayList<>();
            for(int index : selection) {
                list.add(factory.getCards().get(index));
            }
            DeckOld deck = null;
            try {
                deck = new DeckOld(nameText.getText(), list);
                factory.getDecks().add(deck);
                factory.save();
                dispose();
                JOptionPane.showMessageDialog(this, "Neue Sammlung erzeugt!", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
            } catch (CardException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
