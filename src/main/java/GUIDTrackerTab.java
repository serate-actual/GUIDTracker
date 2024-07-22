import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
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
        this.datamodel = new GUIDTableModel();
        this.guidTable = new JTable(datamodel){
            public void jTable(){
                this.getModel().addTableModelListener( new TableModelListener() {
                    public void tableChanged(TableModelEvent e) {
                        System.out.println("SOMETHING IS HAPPENING");
                        System.out.println(e.getType());
                    }
                }
                );
            }

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
                        addGUIDButton.setEnabled(true);
                    } else {
                        guidField.setText("");
                        notesField.setText("");
                        addGUIDButton.setEnabled(false);
                        this.revalidate();
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
                this.revalidate();
                this.repaint();
            }

            @Override
            protected void processFocusEvent(FocusEvent e){
                if(e.getID() == FocusEvent.FOCUS_LOST){
                    if(e.getOppositeComponent() != notesField && e.getOppositeComponent() != guidField && e.getOppositeComponent() != deleteGUIDButton){
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
