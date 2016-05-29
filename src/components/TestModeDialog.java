package components;

import model.Flashcard;
import view.MainWindow;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TestModeDialog extends AbstractModeDialog {
    private MainWindow parent;
    protected JButton show;
    protected JCheckBox known;

    public TestModeDialog(MainWindow parent) {
        this.parent = parent;
        setTitle("Prüfmodus");
        initAdditionalComponents();
        addAdditionalComponents();
        back.setVisible(false);
        previous.setEnabled(false);
        addCardBrowsListener();
    }

    public void updateAndShow() {
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void initAdditionalComponents() {
        show = new JButton("Rückseite anzeigen");
        known = new JCheckBox("hätte ich gewusst");
    }

    private void addAdditionalComponents() {
        bottom.add(show);
        bottom.add(known);
    }

    private void addCardBrowsListener() {
        next.addActionListener(event -> {
            ++listPosition;
            if(listPosition < cards.size()) {
                setCardContent(listPosition);
            }
            else {
                JOptionPane.showMessageDialog(getParent(), "Alle Karten abgefragt", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            Flashcard card = cards.get(listPosition-1);
            card.setCalls(card.getCalls() + 1);
            if(known.isSelected()) {
                card.setCorrectAnswered(card.getCorrectAnswered() + 1);
                known.setSelected(false);
            }
        });

        show.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                back.setVisible(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                back.setVisible(false);
            }
        });
    }
}
