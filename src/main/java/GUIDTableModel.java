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
        this.dataVector.add(rowData);
        this.fireTableDataChanged();
    }

    @Override
    public void removeRow(int item){
        this.dataVector.remove(item);
        this.fireTableDataChanged();
    }

    public String[] checkGUID(String guid){
        ArrayList<String> matches = new ArrayList<String>();
        //iterate through all the items in the list to see if they match.
        for (Object itemObject : this.dataVector) {
            String[] item = (String[])itemObject;
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
           String[] item = (String[])itemObject;
           columnData.add(item[columnIndex]);
       }
       return columnData;
    }

    public void setFromColumnData(ArrayList<String> guids, ArrayList<String> notes){
        int i;
        for(i = 0; i < guids.size(); i++){
            System.out.println(guids.get(i));
            System.out.println(notes.get(i));
        }
    }

}
