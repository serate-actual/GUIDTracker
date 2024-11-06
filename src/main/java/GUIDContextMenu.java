import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.Color;
import java.awt.*;

public class GUIDContextMenu {
    private JPanel panel1;
    private JTextArea GUIDPane;
    private JTextArea NotesPane;

    public JPanel getPanel(){
        return this.panel1;
    }

    public void setGUIDPaneText(String text, Object[] highlights) throws BadLocationException {
        Highlighter highlighter = this.GUIDPane.getHighlighter();
        this.GUIDPane.setText(text);
        for (Object highlightItem : highlights) {
            Object[] item = (Object[]) highlightItem;
            if (item != null) {
                Color highlight = HighlightColor.from(item[4].toString()).getColor();
                Painter tempPainter = new Painter(highlight);
                highlighter.addHighlight((Integer) item[0], (Integer) item[1], tempPainter);
            }
        }
    }

    public void setNotesPaneText(String text){
        this.NotesPane.setText(text);
    }

    public String getNotesPaneText(){
        return this.NotesPane.getText();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.NotesPane = new JTextArea();
        this.GUIDPane = new JTextArea();
        this.GUIDPane.setLineWrap(true);
        this.NotesPane.setLineWrap(true);
        this.panel1 = new JPanel();
        this.panel1.repaint();
    }
}

class Painter extends DefaultHighlighter.DefaultHighlightPainter {
    public Painter(Color c) {
        super(c);
    }
}