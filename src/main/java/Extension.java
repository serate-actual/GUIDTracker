import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

import java.util.ArrayList;

public class Extension implements BurpExtension {
    private MontoyaApi api;
    private UnloadingHandler unloadingHandler;
    private RequestResponseHandler requestResponseHandler;
    @Override
    public void initialize(MontoyaApi api) {
        this.api = api;
        api.extension().setName("GUIDTracker-TESTING");
        api.logging().logToOutput("serate was here");

        // Add GUID Tracker Tab
        GUIDTrackerTab trackerTab = new GUIDTrackerTab();
        api.userInterface().registerSuiteTab("GUID Tracker", trackerTab.getUI());

        // Add GUID Adder Tab
        GUIDAdder adderTab = new GUIDAdder(trackerTab);

        // Context menu setup
        TrackerMenu contextMenu = new TrackerMenu(api, trackerTab, adderTab);
        // Register the context menu
        api.userInterface().registerContextMenuItemsProvider(contextMenu);

        // set persistence
        UnloadingHandler unloadingHandler = new UnloadingHandler(trackerTab.getDataModel(), this.api);
        api.extension().registerUnloadingHandler(unloadingHandler);
        if(this.api.persistence().extensionData().stringListKeys().contains("guid") && this.api.persistence().extensionData().stringListKeys().contains("notes") && this.api.persistence().extensionData().stringListKeys().contains("color")){
            int size = this.api.persistence().extensionData().getStringList("guid").size();
            int i;
            for(i = 0; i < size; i++){
                String guid = this.api.persistence().extensionData().getStringList("guid").get(i);
                String notes = this.api.persistence().extensionData().getStringList("notes").get(i);
                String color = this.api.persistence().extensionData().getStringList("color").get(i);
                Object[] row = new Object[3];
                row[0] = guid;
                row[1] = notes;
                row[2] = HighlightColor.from(color);
                trackerTab.getDataModel().addRow(row);
            }
        }

        api.proxy().registerRequestHandler(new RequestResponseHandler(trackerTab.getDataModel()));
        api.proxy().registerResponseHandler(new RequestResponseHandler(trackerTab.getDataModel()));
    }
}
