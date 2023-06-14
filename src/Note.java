import java.awt.Color;
import java.io.Serializable;

public class Note implements Serializable {
    String title = "��� ��������";
    String text = "������� ��� ������";
    Color noteColor = new Color(229, 227, 255);
    
    public Note() {
        this.title = "";
        this.text = "";
    }
    
    public Note(String pTitle, String pText) {
        this.title = pTitle;
        this.text = pText;
    }
        
    public Note(String pTitle, String pText, Color pColor) {
        this.title = pTitle;
        this.text = pText;
        noteColor = pColor;
    }

    public String getTitle() {
        if (title.isEmpty())
            return "��� ��������";
        else
            return title;
    }

    public String getText() {
        if (text.isEmpty())
            return "������� ��� ������";
        else
            return text;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }  
    
    public Color getColor() {
        return this.noteColor;
    }
}
