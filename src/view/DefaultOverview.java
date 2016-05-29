package view;

import model.FlashcardFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public abstract class DefaultOverview extends JInternalFrame {
    protected DefaultTableModel model;
    protected JTable table;
    private JScrollPane scrollPane;
    protected FlashcardFactory factory;

    public DefaultOverview(FlashcardFactory factory) {
        this.factory = factory;
        setTitle("Übersicht");
        setClosable(true);
        initTable();
        addMouseClickListener();
        setVisible(false);
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public abstract void update();

    public abstract void addMouseClickListener();

    public int[] getSelection() {
        int[] selection = table.getSelectedRows();
        for(int i = 0; i < selection.length; i++) {
            selection[i] = table.convertRowIndexToModel(selection[i]);
        }
        Arrays.sort(selection);
        return selection;
    }

    private void initTable() {
        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
        scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
}
