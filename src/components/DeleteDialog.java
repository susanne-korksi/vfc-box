package components;

import model.FlashcardFactory;
import view.MainWindow;

import javax.swing.*;

public class DeleteDialog extends AbstractSelectionDialog {
    private FlashcardFactory flashcardFactory;
    private MainWindow parent;

    public DeleteDialog(FlashcardFactory flashcardFactory, MainWindow parent) {
        this.flashcardFactory = flashcardFactory;
        this.parent = parent;
        selectionText.setText("Was soll gelöscht werden?");
        cards.setText("Karten");
        decks.setText("Sammlungen");
    }

    public void updateAndShow() {
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    @Override
    public void addListener() {
        ok.addActionListener(event -> {
            if(cards.isSelected()) {
                flashcardFactory.getCards().clear();
                FlashcardFactory.ID = 1;
            }
            if(decks.isSelected()) {
                flashcardFactory.getDecks().clear();
            }
            JOptionPane.showMessageDialog(parent, "Ausgwählte Elemente gelöscht!", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
    }
}
