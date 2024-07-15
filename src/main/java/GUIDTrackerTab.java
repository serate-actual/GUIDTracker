import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class GUIDTrackerTab {
    private JPanel ui;
    private JButton addGUIDButton;
    private JTable guidTable;
    private JTextField guidField;
    private JTextField notesField;
    private JButton deleteGUIDButton;
    private GUIDTable datamodel;

    public GUIDTrackerTab(){
    }

    public GUIDTable getDataModel(){
        return this.datamodel;
    }
    public JPanel getUI(){
        return this.ui;
    }


    private void createUIComponents( ) {
        // TODO: place custom component creation code here
        /*this.guidTable = new JTable(this.data, new String[]{"GUID","Notes"}){
            @Override
            public void tableChanged(TableModelEvent e) {
                super.tableChanged(e);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }


        };*/
        this.datamodel = new GUIDTable();
        this.guidTable = new JTable(datamodel){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (guidTable.getSelectedRow() != -1) {
                    // A row is selected, enable deletion
                    deleteGUIDButton.setEnabled(true);
                    if (guidTable.getSelectedRowCount() == 1) {
                        // one row is
                        addGUIDButton.setText("Edit GUID");
                        guidField.setText(String.valueOf(guidTable.getValueAt(guidTable.getSelectedRow(),0)));
                        notesField.setText(String.valueOf(guidTable.getValueAt(guidTable.getSelectedRow(),1)));
                        addGUIDButton.setEnabled(true);
                    } else {
                        guidField.setText("");
                        notesField.setText("");
                        addGUIDButton.setEnabled(false);
                        this.repaint();
                    }
                } else {
                    // no rows are selected, disable delete guid button, reenable add guid button, reset the text
                    guidField.setText("");
                    notesField.setText("");
                    addGUIDButton.setEnabled(true);
                    deleteGUIDButton.setEnabled(false);
                    addGUIDButton.setText("Add GUID");
                }
                deleteGUIDButton.setEnabled(true);
                super.valueChanged(e);
            }

            @Override
            protected void processFocusEvent(FocusEvent e){
                if(e.getID() == FocusEvent.FOCUS_LOST){
                    if(e.getOppositeComponent() != notesField && e.getOppositeComponent() != guidField && e.getOppositeComponent() != deleteGUIDButton){
                        guidTable.clearSelection();
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
                } else {
                    // No rows are selected, add a guid
                    // Add a row to the table
                    // check that it is enabled before adding the row
                    if(addGUIDButton.isEnabled() && guidField.getText() != ""){
                        datamodel.addRow(new String[]{String.valueOf(guidField.getText()), String.valueOf(notesField.getText())});
                    }
                }
                guidField.setText("");
                notesField.setText("");
                // Reset the GUID field
                addGUIDButton.setText("Add GUID");
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
