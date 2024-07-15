import javax.swing.event.TableModelListener;
import java.util.ArrayList;
import java.util.Vector;

public class GUIDTable2 extends DefaultTableModel {
    public GUIDTable2(){
        this.columnIdentifiers = new Vector();
        this.columnIdentifiers.add("GUID");
        this.columnIdentifiers.add("Notes");
        this.dataVector = new Vector<>();
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
        return "Abc".getClass();
    }

    @Override
    public Object[] getValueAt(int rowIndex, int columnIndex){
        Vector row = this.dataVector.get(rowIndex);
        return (Object[]) row.get(columnIndex);
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
    public String[] checkGUID(String guid){
        ArrayList<String> matches = new ArrayList<String>();
        //iterate through all the items in the list to see if they match.
        for (Vector item : this.getDataVector()) {
            System.out.println(item);
            if (item != null) {
                if (item.get(0).equals(guid)) {
                    // MATCH has occurred
                    matches.add((String) item.get(1));
                }
            }
        }
        String[] returnmatches = new String[matches.size()];
        returnmatches = matches.toArray(returnmatches);
        return returnmatches;
    }

    @Override
    public void addRow(Object[] rowData){
        // Manually append a value to the row
        //Object[] fullRow = new Object[rowData.length+1];
        //System.arraycopy(rowData,0,fullRow,0,rowData.length+1);
        //fullRow[fullRow.length-1]="GREEN";
        //super.addRow(fullRow);
        //this.dataVector.addElement(rowData);
        //this.dataVector.add(new String[]{"a", "a"});
        super.addRow(rowData);
    }
}
