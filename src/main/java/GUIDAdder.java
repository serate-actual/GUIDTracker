import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIDAdder {
    private JPanel mainPanel;
    private JTextArea notes;
    private JButton addGUIDButton;
    private GUIDTableModel datamodel;
    private String guid;
    private GUIDTrackerTab trackerTab;

    public GUIDAdder(GUIDTrackerTab trackerTab){
        this.datamodel = trackerTab.getDataModel();
        this.trackerTab = trackerTab;
        this.guid = "";
    }

    public void setGUID(String guid){
        this.guid = guid;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.mainPanel = new JPanel();
        this.notes = new JTextArea();
        this.notes.setText("");
        this.addGUIDButton = new JButton();
        this.addGUIDButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(notes.getText().isBlank()){
                    // No text
                    datamodel.addRow(new String[]{guid, "Go to the GUID Tracker tab and set the value", "None".toString()});
                    trackerTab.getGuidTable().revalidate();
                    trackerTab.getGuidTable().repaint();
                    System.out.println("ADDING A GUID");
                } else {
                    // Is text
                    datamodel.addRow(new String[]{guid, notes.getText(), "None".toString()});
                    datamodel.fireTableDataChanged();
                    notes.setText("");
                    trackerTab.getGuidTable().revalidate();
                    trackerTab.getGuidTable().repaint();
                    System.out.println("ADDING A GUID");
                }
            }
        });
        this.mainPanel.repaint();
    }



    public JPanel getPanel(){
        return this.mainPanel;
    }
}
