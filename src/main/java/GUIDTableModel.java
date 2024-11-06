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

    public Object[] containsGUID(String selection){
        ArrayList<Object> locations = new ArrayList<Object>();
        for (Object itemObject : this.dataVector) {
            Object[] item = (Object[])itemObject;
            if (item != null) {
                if (selection.contains(item[0].toString())){
                    // MATCH has occurred
                    int startIndex = 0;
                    int selectionIndex = selection.indexOf(item[0].toString(),startIndex);
                    System.out.println(selectionIndex);
                    while (selectionIndex != -1 ){
                        selectionIndex = selection.indexOf(item[0].toString(),startIndex);
                        if (selectionIndex != -1) {
                            Object[] selectBounds = new Object[5];
                            selectBounds[0] = selectionIndex;
                            selectBounds[1] = item[0].toString().length() + (int) selectBounds[0];
                            startIndex = (int) selectBounds[1];
                            // guid
                            selectBounds[2] = item[0];
                            // notes
                            selectBounds[3] = item[1];
                            // color
                            selectBounds[4] = item[2];
                            locations.add(selectBounds);
                            //selectBounds.removeAll();
                        }
                    }
                }
            }
        }
        return locations.toArray();
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
