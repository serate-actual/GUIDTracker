import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

public class Extension implements BurpExtension {
    private MontoyaApi api;

    @Override
    public void initialize(MontoyaApi api) {
        this.api = api;
        api.extension().setName("GUIDTracker");
        api.logging().logToOutput("serate was here");

        // Add GUID Tracker Tab
        GUIDTrackerTab trackerTab = new GUIDTrackerTab();
        api.userInterface().registerSuiteTab("GUID Tracker", trackerTab.getUI());

        // Context menu setup
        TrackerMenu contextMenu = new TrackerMenu(api, trackerTab.getDataModel());
        // Register the context menu
        api.userInterface().registerContextMenuItemsProvider(contextMenu);
    }
}
