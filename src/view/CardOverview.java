package view;

import components.EditCardDialog;
import components.ViewCardDialog;
import model.FlashcardFactory;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;

public class CardOverview extends DefaultOverview {
    private TableRowSorter<TableModel> sorter;
    private ViewCardDialog viewCardDialog;
    private EditCardDialog editCardDialog;
    private MainWindow parent;
    private JPopupMenu popupMenu;
    private JMenuItem details, edit, delete;


    public CardOverview(FlashcardFactory factory, MainWindow parent) {
        super(factory);
        this.parent = parent;
        sorter = new TableRowSorter<>(model);
        viewCardDialog = new ViewCardDialog(parent);
        editCardDialog = new EditCardDialog(parent);
        table.setRowSorter(sorter);
        initData();
        initPopUpMenu();
        addItemsListener();
    }

    public void update() {
        initData();
        setSorter();
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
                if(SwingUtilities.isRightMouseButton(e) &&  e.getComponent() instanceof JTable) {
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
        model.setDataVector(factory.getDatenCards(), factory.getTitelCards());
    }

    private void setSorter() {
        sorter.setComparator(0, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
            }
        });
    }

    private void initPopUpMenu() {
        popupMenu = new JPopupMenu();
        details = new JMenuItem("Details");
        edit = new JMenuItem("Bearbeiten");
        delete = new JMenuItem("Löschen");
        popupMenu.add(details);
        popupMenu.add(edit);
        popupMenu.add(delete);
    }

    private void addItemsListener() {
        delete.addActionListener(event -> {
            int[] index = getSelection();
            if(index.length != 0) {
                for(int i = index.length-1; i >= 0; i--) {
                    factory.getCards().remove(index[i]);
                }
                factory.save();
                update();
            }
            else JOptionPane.showMessageDialog(parent, "Keine Karte ausgewählt!", "Fehler", JOptionPane.ERROR_MESSAGE);
        });

        details.addActionListener(event -> {
            int[] index = getSelection();
            if(index.length == 1) viewCardDialog.updateAndShow(factory.getCards().get(index[0]));
            else if (index.length == 0) JOptionPane.showMessageDialog(parent, "Keine Karte ausgewählt!", "Fehler", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(parent, "Es darf nur eine Karte ausgewählt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
        });

        edit.addActionListener(event -> {
            int[] index = getSelection();
            if(index.length == 1) {
                editCardDialog.updateAndShow(factory, factory.getCards().get(index[0]));
                update();
            }
            else if (index.length == 0) JOptionPane.showMessageDialog(parent, "Keine Karte ausgewählt!", "Fehler", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(parent, "Es darf nur eine Karte ausgewählt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
        });
    }

}
