package components;

import model.Flashcard;
import view.MainWindow;

import java.util.List;

public class LearnModeDialog extends AbstractModeDialog {
    private MainWindow parent;

    public LearnModeDialog(MainWindow parent) {
        this.parent = parent;
        setTitle("Lernmodus");
        addCardBrowsListener();
    }

    public void updateAndShow() {
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void addCardBrowsListener() {
        next.addActionListener(event -> {
            ++listPosition;
            if(listPosition < cards.size()) {
                setCardContent(listPosition);
            }
            else {
                listPosition = 0;
                setCardContent(listPosition);
            }
        });
        previous.addActionListener(event -> {
            --listPosition;
            if(listPosition >= 0) {
                setCardContent(listPosition);
            }
            else {
                listPosition = cards.size()-1;
                setCardContent(listPosition);
            }
        });
    }
}
