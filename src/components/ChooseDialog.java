package components;

import model.Flashcard;
import model.FlashcardFactory;
import view.CardOverview;
import view.DeckOverview;
import view.DefaultOverview;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChooseDialog extends AbstractSelectionDialog {
    private FlashcardFactory flashcardFactory;
    private int mode;
    private CardOverview cardOverview;
    private DeckOverview deckOverview;
    private JPanel panel;
    private JButton confirm;
    private MainWindow parent;
    private TestModeDialog testModeDialog;
    private LearnModeDialog learnModeDialog;

    public ChooseDialog(FlashcardFactory factory, MainWindow parent, CardOverview cardOverview, DeckOverview deckOverview,
                        LearnModeDialog learnModeDialog, TestModeDialog testModeDialog) {
        this.flashcardFactory = factory;
        this.parent = parent;
        this.cardOverview = cardOverview;
        this.deckOverview = deckOverview;
        this.learnModeDialog = learnModeDialog;
        this.testModeDialog = testModeDialog;
        selectionText.setText("Wonach soll ausgewählt werden?");
        cards.setText("Karten");
        decks.setText("Sammlungen");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(cards);
        buttonGroup.add(decks);
        initPanel();
    }

    public void updateAndShow(int mode) {
        this.mode = mode;
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void initPanel() {
        this.confirm = new JButton("Auswahl bestätigen");
        this.panel = new JPanel(new BorderLayout());
        confirm.addActionListener(event -> confirm());
        panel.add(confirm, BorderLayout.SOUTH);
    }

    @Override
    public void addListener() {
        ok.addActionListener(event -> ok());
    }

    private void ok() {
        setSize(600,300);
        setLocationRelativeTo(parent);
        radioButtons.setVisible(false);
        ok.setEnabled(false);
        if(cards.isSelected()) {
            panel.add(cardOverview, BorderLayout.CENTER);
            add(panel, BorderLayout.CENTER);
            cardOverview.updateAndShow();

        }
        if(decks.isSelected()) {
            panel.add(deckOverview, BorderLayout.CENTER);
            add(panel, BorderLayout.CENTER);
            deckOverview.updateAndShow();

        }
    }

    private void confirm() {
        dispose();
        reset();
        List<Flashcard> list = new ArrayList<>();
        if(cards.isSelected()) {
            int[] selection = cardOverview.getSelection();
            for (int i : selection) {
                list.add(flashcardFactory.getCards().get(i));
            }
        }
        else {
            int[] selection = deckOverview.getSelection();
            for (int i : selection) {
                list.addAll(flashcardFactory.getDecks().get(i).getCards());
            }
        }

        if(mode == 0) {
            learnModeDialog.setCards(list);
            learnModeDialog.initFirstCard();
            learnModeDialog.updateAndShow();
        }
        else {
            Collections.sort(list, (o1, o2) -> Integer.compare(o1.getCorrectAnswered(), o2.getCorrectAnswered()));
            testModeDialog.setCards(list);
            testModeDialog.initFirstCard();
            testModeDialog.updateAndShow();
        }
    }
}
