package view;

import model.FlashcardFactory;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DeckOverview extends DefaultOverview {
    private JPopupMenu popupMenu;
    private JMenuItem delete;

    public DeckOverview(FlashcardFactory factory) {
        super(factory);
        initData();
        initPopUpMenu();
        addItemListener();
        table.setAutoCreateRowSorter(true);
    }

    public void update() {
        initData();
    }

    public void updateAndShow() {
        update();
        setVisible(true);
    }

    @Override
    public void addMouseClickListener() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)  &&  e.getComponent() instanceof JTable) {
                    int r = table.rowAtPoint(e.getPoint());
                    if (r >= 0 && r < table.getRowCount()) {
                        table.setRowSelectionInterval(r, r);
                    } else {
                        table.clearSelection();
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void initData() {
        model.setDataVector(factory.getDatenDecks(), factory.getTitelDecks());
    }

    private void initPopUpMenu() {
        popupMenu = new JPopupMenu();
        delete = new JMenuItem("Löschen");
        popupMenu.add(delete);
    }

    private void addItemListener() {
        delete.addActionListener(event -> {
            int[] selection = getSelection();
            if(selection.length != 0) {
                for(int i = selection[selection.length-1]; i >= 0; i--) {
                    factory.getDecks().remove(i);
                }
                factory.save();
                update();
            }
        });
    }
}
