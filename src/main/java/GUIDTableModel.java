import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class GUIDTableModel extends DefaultTableModel {
    private final Vector dataVector;
    private final Vector columnIdentifiers;

    public GUIDTableModel(){
        this.columnIdentifiers = new Vector();
        this.columnIdentifiers.add("GUID");
        this.columnIdentifiers.add("Notes");
        this.columnIdentifiers.add("Color");
        this.dataVector = new Vector();
    }

    @Override
    public void setValueAt(Object aValue, int row, int column){
        Object[] item = (Object[]) this.dataVector.get(row);
        item[column] = aValue;
        this.dataVector.set(row, item);
        this.fireTableDataChanged();
    }

    @Override
    public String getValueAt(int RowIndex, int columnIndex){
        Object[] row = (Object[]) this.dataVector.get(RowIndex);
        return row[columnIndex].toString();
    }

    @Override
    public int getColumnCount() {
        return columnIdentifiers.size();
    }

    @Override
    public int getRowCount(){
        if (this.dataVector == null) {
            return 0;
        } else {
            return this.dataVector.size();
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return (String) this.columnIdentifiers.get(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        /*return switch (columnIndex) {
            case 0 -> String.class;
            case 1 -> String.class;
            case 2 -> String.class;
            default -> String.class;
        };*/
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    @Override
    public void addRow(Object[] rowData){
        // In case you pass a different length array in ( omitting optional arguments ) handle it gracefully
        Object[] finalRow = new Object[this.columnIdentifiers.size()];
        System.arraycopy(rowData,0,finalRow,0,this.columnIdentifiers.size());
        this.dataVector.add(finalRow);
        this.fireTableDataChanged();
    }

    @Override
    public void removeRow(int item){
        this.dataVector.remove(item);
        this.fireTableDataChanged();
    }

    public HighlightColor searchAndGetGUIDcolor(String request){
        for (Object itemObject : this.dataVector) {
            Object[] item = (Object[])itemObject;
            if (item != null) {
                if (request.contains(item[0].toString()) && item[2] != HighlightColor.NONE){
                    // MATCH has occurred
                    return HighlightColor.from(item[2].toString());
                }
            }
        }
        return HighlightColor.NONE;
    }

    public String[] containsGUID(String selection){
        String[] message = new String[2];
        message[0] = "";
        for (Object itemObject : this.dataVector) {
            Object[] item = (Object[])itemObject;
            if (item != null) {
                if (selection.contains(item[0].toString())){
                    // MATCH has occurred
                    message[1] = item[0].toString();
                     int selectionIndex = selection.indexOf(item[0].toString());
                    // Check if the selection is 20 characters greater than the item
                    if (selection.length() - item[0].toString().length() < 20){
                        // Just return the whole selection
                        message[0] = selection;
                    } else {
                        // Check if left side is sticking out
                        if (selectionIndex > 10){
                            message[0] += " . . . ";
                            message[0] += selection.substring(selectionIndex-10,selectionIndex);
                        } else {
                            message[0] += selection.substring(0,selectionIndex);
                        }
                        // Do the same as above, but for the right side
                        if (selectionIndex + item[0].toString().length() - selection.length() < -10){
                            message[0] += selection.substring(selectionIndex,selectionIndex+10) + " . . . ";
                        } else {
                            message[0] += selection.substring(selectionIndex,selection.length());
                        }
                    }
                }
            }
        }
        return message;
    }

    public String[] checkGUID(String guid){
        ArrayList<Object> matches = new ArrayList<Object>();
        //iterate through all the items in the list to see if they match.
        for (Object itemObject : this.dataVector) {
            Object[] item = (Object[])itemObject;
            if (item != null) {
                if (guid.equals(item[0])){
                    // MATCH has occurred
                    matches.add((String) item[1]);
                }
            }
        }
        String[] returnmatches = new String[matches.size()];
        returnmatches = matches.toArray(returnmatches);
        return returnmatches;
    }

    public String[] getColumnIdentifiers(){
        return Arrays.copyOf(this.columnIdentifiers.toArray(),this.columnIdentifiers.size(),String[].class);
    }

    public ArrayList<String> getColumnData(int columnIndex){
       ArrayList<String> columnData = new ArrayList<>();
       for (Object itemObject : this.dataVector){
           Object[] item = (Object[]) itemObject;
           columnData.add(item[columnIndex].toString());
       }
       return columnData;
    }

}
