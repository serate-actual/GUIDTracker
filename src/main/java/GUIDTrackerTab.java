import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIDTrackerTab {
    private JPanel ui;
    private JButton addGUIDButton;
    private JTable guidTable;
    private JTextField guidField;
    private JTextField notesField;
    private JButton deleteGUIDButton;
    private JComboBox colorSelector;
    private GUIDTableModel datamodel;

    public GUIDTrackerTab(){
    }

    public GUIDTableModel getDataModel(){
        return this.datamodel;
    }
    public JPanel getUI(){
        return this.ui;
    }

    public JTable getGuidTable(){
        return this.guidTable;
    }

    public JButton getAddGUIDButton(){
        return this.addGUIDButton;
    }

    public JTextField getGuidField(){
        return this.guidField;
    }

    public JTextField getNotesField(){
        return this.notesField;
    }

    public JButton getDeleteGUIDButton(){
        return this.deleteGUIDButton;
    }
    
    public void addGuidValue(String guid, String notes, HighlightColor color){
        // addRow takes a list - sometimes I don't want to have to pass a whole array
        //this.datamodel.addRow(new String[]{String.valueOf(guidField.getText()), String.valueOf(notesField.getText())});
        this.datamodel.addRow(new String[]{guid, notes, color.toString()});
    }

    private static class HighlightDropdownRenderer implements ListCellRenderer<HighlightColor> {
        private final ListCellRenderer renderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList<? extends HighlightColor> list, HighlightColor value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) renderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(list.isSelectionEmpty()){
                return label;
            } else if (value == HighlightColor.NONE) {
                return label;
            } else {
                label.setBackground(value.getColor());
            }
            return label;
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        //this.colorSelector = new JComboBox(HighlightColor.values());
        this.colorSelector = new JComboBox();
        this.colorSelector.setModel(new DefaultComboBoxModel<>(HighlightColor.values()));
        this.colorSelector.setRenderer(new HighlightDropdownRenderer());
        this.colorSelector.repaint();

        //Handle creation of the datamodel and JTable for the guids/notes
        this.datamodel = new GUIDTableModel();
        this.guidTable = new JTable(datamodel){

            @Override
            protected JTableHeader createDefaultTableHeader() {
                return super.createDefaultTableHeader();
            }

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (this.getSelectedRow() != -1) {
                    // A row is selected, enable deletion
                    deleteGUIDButton.setEnabled(true);
                    if (this.getSelectedRowCount() == 1) {
                        // one row is
                        addGUIDButton.setText("Edit GUID");
                        guidField.setText(String.valueOf(this.getValueAt(this.getSelectedRow(),0)));
                        notesField.setText(String.valueOf(this.getValueAt(this.getSelectedRow(),1)));
                        colorSelector.setSelectedItem(HighlightColor.from((String) this.getValueAt(this.getSelectedRow(),2)));
                        colorSelector.repaint();
                        addGUIDButton.setEnabled(true);
                    } else {
                        guidField.setText("");
                        notesField.setText("");
                        colorSelector.setSelectedItem(HighlightColor.from("NONE"));
                        addGUIDButton.setEnabled(false);
                        this.revalidate();
                        this.repaint();
                    }
                } else {
                    // no rows are selected, disable delete guid button, re-enable add guid button, reset the text
                    guidField.setText("");
                    notesField.setText("");
                    colorSelector.setSelectedItem(HighlightColor.from("NONE"));
                    addGUIDButton.setEnabled(true);
                    deleteGUIDButton.setEnabled(false);
                    addGUIDButton.setText("Add GUID");
                }
                deleteGUIDButton.setEnabled(true);
                super.valueChanged(e);
                this.revalidate();
                this.repaint();
            }

            @Override
            protected void processFocusEvent(FocusEvent e){
                if(e.getID() == FocusEvent.FOCUS_LOST){
                    if(e.getOppositeComponent() != notesField && e.getOppositeComponent() != guidField && e.getOppositeComponent() != deleteGUIDButton && e.getOppositeComponent() != colorSelector){
                        this.clearSelection();
                    }
                }
            }

            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend){
                super.changeSelection(rowIndex, columnIndex, true, false);
            }
        };

        this.addGUIDButton = new JButton();
        this.addGUIDButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // Check if a row is selected first
                if (guidTable.getSelectedRowCount() == 1){
                    // A singular row is selected, edit a guid
                    //datamodel.editRow(guidTable.getSelectedRow()-1, String.valueOf(guidField.getText()), String.valueOf(notesField.getText()));
                    datamodel.setValueAt(String.valueOf(guidField.getText()), guidTable.getSelectedRow(), 0);
                    datamodel.setValueAt(String.valueOf(notesField.getText()), guidTable.getSelectedRow(), 1);
                    datamodel.setValueAt((HighlightColor) colorSelector.getSelectedItem(), guidTable.getSelectedRow(), 2);
                } else {
                    // No rows are selected, add a guid
                    // Add a row to the table
                    // check that it is enabled before adding the row
                    if(addGUIDButton.isEnabled() && !guidField.getText().isBlank()){
                        addGuidValue(String.valueOf(guidField.getText()), String.valueOf(notesField.getText()), (HighlightColor) colorSelector.getSelectedItem());
                    }
                }
                guidField.setText("");
                notesField.setText("");
                // Reset the GUID field
                addGUIDButton.setText("Add GUID");
                // Reset the dropdown menu
                colorSelector.setSelectedItem(HighlightColor.from("NONE"));
                // Deselect All rows
                guidTable.clearSelection();
                guidTable.revalidate();
                guidTable.repaint();
            }
        });
        this.deleteGUIDButton = new JButton();
        deleteGUIDButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // Check if a row is selected first
                if (guidTable.getSelectedRowCount() >= 1){
                    int i;
                    for(i = guidTable.getSelectedRows().length-1; i >= 0; i--){
                        datamodel.removeRow(guidTable.getSelectedRows()[i]);
                    }
                }
                // Resets the UI
                guidField.setText("");
                notesField.setText("");
                guidTable.clearSelection();
                guidTable.revalidate();
                guidTable.repaint();
            }
        });
    }
}
