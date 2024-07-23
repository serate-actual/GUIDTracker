import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.HighlightColor;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;
import burp.api.montoya.ui.contextmenu.MessageEditorHttpRequestResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class TrackerMenu implements ContextMenuItemsProvider {
    private final MontoyaApi api;
    private final GUIDTrackerTab guidTab;
    public TrackerMenu(MontoyaApi api, GUIDTrackerTab guidTab){
        this.api = api;
        this.guidTab = guidTab;
    }
    private String getSelection(ContextMenuEvent event){
        if (event.messageEditorRequestResponse().isPresent()) {
            // Attempts to get the selection
            MessageEditorHttpRequestResponse selection = event.messageEditorRequestResponse().get();
            // Find the start and end of the selection
            int start = selection.selectionOffsets().get().startIndexInclusive();
            int end = selection.selectionOffsets().get().endIndexExclusive();
            String selectedText = "";
            // Figure out if the selection is in the REQUEST or RESPONSE
            if (String.valueOf(selection.selectionContext()) == "REQUEST") {
                selectedText = String.valueOf(selection.requestResponse().request()).substring(start,end);
            }
            else if (String.valueOf(selection.selectionContext()) == "RESPONSE"){
                selectedText = String.valueOf(selection.requestResponse().response()).substring(start,end);
            }
            return selectedText;
        }
        return null;
    }

    public List<Component> provideMenuItems(ContextMenuEvent event){
        List<Component> menuItemList = new ArrayList<>();

        // Handle the new menu item
        JMenuItem Register = new JMenuItem("Register GUID");
        Register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                guidTab.getDataModel().addRow(new Object[]{getSelection(event),"Go to the GUID Tracker tab and set the value", HighlightColor.NONE});
                guidTab.getGuidTable().revalidate();
                guidTab.getGuidTable().repaint();
            }
        });
        menuItemList.add(Register);
        // runs IF something is selected
        // Identify all related GUIDs
        menuItemList.add(new JSeparator(SwingConstants.HORIZONTAL));
        Object[] matches = this.guidTab.getDataModel().checkGUID(getSelection(event));
        if(matches.length > 0) {
            Iterator<Object> searchResults = Arrays.stream(matches).iterator();
            while (searchResults.hasNext()) {
                menuItemList.add(new JMenuItem(String.valueOf(searchResults.next())));
            }
        } else {
            Object[] contexts = this.guidTab.getDataModel().containsGUID(getSelection(event));
            if (!contexts[0].toString().isBlank()){
                menuItemList.add(new JMenuItem("Selection contains a registered GUID: "));
                menuItemList.add(new JMenuItem(contexts[0].toString()));
                menuItemList.add(new JMenuItem("Notes: "));
                menuItemList.add(new JMenuItem(String.valueOf(contexts[1].toString())));
            } else {
                menuItemList.add(new JMenuItem("No matches detected."));
            }
        }
        return menuItemList;
    }
}
