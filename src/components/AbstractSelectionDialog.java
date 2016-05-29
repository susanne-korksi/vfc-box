package components;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractSelectionDialog extends JDialog {
    protected JPanel radioButtons, buttons;
    protected JLabel selectionText;
    protected JRadioButton cards, decks;
    protected JButton ok, abort;

    public AbstractSelectionDialog() {
        setTitle("Auswahl treffen");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300,150);
        setModal(true);
        setLayout(new BorderLayout());
        initComponents();
        addComponents();
        addListener();
    }

    public void reset() {
        setSize(300,150);
        radioButtons.setVisible(true);
        ok.setEnabled(true);
    }

    private void initComponents() {
        selectionText = new JLabel("",JLabel.CENTER);
        radioButtons = new JPanel(new GridLayout(1,2));
        buttons = new JPanel(new GridLayout(1,2));
        cards = new JRadioButton();
        decks = new JRadioButton();
        ok = new JButton("Ok");
        abort = new JButton("Abbrechen");
    }

    private void addComponents() {
        add(selectionText, BorderLayout.NORTH);
        radioButtons.add(cards);
        radioButtons.add(decks);
        add(radioButtons, BorderLayout.CENTER);
        buttons.add(ok);
        buttons.add(abort);
        add(buttons, BorderLayout.SOUTH);
        abort.addActionListener(event -> {
            dispose();
            reset();
        });
    }

    public abstract void addListener();
}
