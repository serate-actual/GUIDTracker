import burp.api.montoya.MontoyaApi;
import burp.api.montoya.extension.ExtensionUnloadingHandler;
import burp.api.montoya.persistence.PersistedList;

import java.util.List;

public class UnloadingHandler implements ExtensionUnloadingHandler {
    private MontoyaApi api;
    private GUIDTableModel table;
    private PersistedList<String> columnIdentifiers;
    private PersistedList<String> dataModelGUID;
    private PersistedList<String> dataModelNotes;
    public UnloadingHandler(GUIDTableModel table, MontoyaApi api){
        this.api = api;
        this.table = table;
        //this.columnIdentifiers = new PersistedList();
    }

    @Override
    public void extensionUnloaded() {
        // FIGURE OUT PERSISTED LISTS
        this.columnIdentifiers = PersistedList.persistedStringList();
        columnIdentifiers.addAll(List.of(this.table.getColumnIdentifiers()));
        this.api.persistence().extensionData().setStringList("guidColumns",columnIdentifiers);
        this.dataModelGUID = PersistedList.persistedStringList();
        this.dataModelNotes = PersistedList.persistedStringList();
        dataModelGUID.addAll(this.table.getColumnData(0));
        dataModelNotes.addAll(this.table.getColumnData(1));
        this.api.persistence().extensionData().setStringList("guid",dataModelGUID);
        this.api.persistence().extensionData().setStringList("notes",dataModelNotes);
    }
}
