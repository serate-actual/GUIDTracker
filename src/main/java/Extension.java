import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

import java.util.ArrayList;

public class Extension implements BurpExtension {
    private MontoyaApi api;
    private UnloadingHandler unloadingHandler;
    @Override
    public void initialize(MontoyaApi api) {
        this.api = api;
        api.extension().setName("GUIDTracker-TESTING");
        api.logging().logToOutput("serate was here");

        // Add GUID Tracker Tab
        GUIDTrackerTab trackerTab = new GUIDTrackerTab();
        api.userInterface().registerSuiteTab("GUID Tracker", trackerTab.getUI());

        // Context menu setup
        TrackerMenu contextMenu = new TrackerMenu(api, trackerTab);
        // Register the context menu
        api.userInterface().registerContextMenuItemsProvider(contextMenu);

        // set persistence
        UnloadingHandler unloadingHandler = new UnloadingHandler(trackerTab.getDataModel(), this.api);
        api.extension().registerUnloadingHandler(unloadingHandler);
        if(!this.api.persistence().extensionData().getStringList("guid").isEmpty()){
            int size = this.api.persistence().extensionData().getStringList("guid").size();
            int i;
            for(i = 0; i < size; i++){
                String guid = this.api.persistence().extensionData().getStringList("guid").get(i);
                String notes = this.api.persistence().extensionData().getStringList("notes").get(i);
                Object[] row = new Object[2];
                row[0] = guid;
                row[1] = notes;
                trackerTab.getDataModel().addRow(row);
            }
        }
    }
}
