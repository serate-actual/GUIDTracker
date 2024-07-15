public class GUID {
    private String guid;
    private String notes;
    // Add something for highlight colors

    public GUID(String guid, String notes){
        this.guid = guid;
        this.notes = notes;
    }

    public String getGUID(){
        return this.guid;
    }

    public String getNotes(){
        return this.notes;
    }

     public void setNotes(String notes){
        this.notes = notes;
    }
}
