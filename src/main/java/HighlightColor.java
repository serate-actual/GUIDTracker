import java.awt.*;

import static java.util.Arrays.stream;

public enum HighlightColor {
    NONE("None", burp.api.montoya.core.HighlightColor.NONE, null),
    RED("Red", burp.api.montoya.core.HighlightColor.RED, Color.RED),
    ORANGE("Orange", burp.api.montoya.core.HighlightColor.ORANGE, Color.ORANGE),
    YELLOW("Yellow", burp.api.montoya.core.HighlightColor.YELLOW, Color.YELLOW),
    GREEN("Green", burp.api.montoya.core.HighlightColor.GREEN, Color.GREEN),
    CYAN("Cyan", burp.api.montoya.core.HighlightColor.CYAN, Color.CYAN),
    BLUE("Blue", burp.api.montoya.core.HighlightColor.BLUE, Color.BLUE),
    PINK("Pink", burp.api.montoya.core.HighlightColor.PINK, Color.PINK),
    MAGENTA("Magenta", burp.api.montoya.core.HighlightColor.MAGENTA, Color.MAGENTA),
    GRAY("Gray", burp.api.montoya.core.HighlightColor.GRAY, Color.GRAY);

    private final String name;
    private final burp.api.montoya.core.HighlightColor highlightColor;
    private final Color color;

    HighlightColor(String name, burp.api.montoya.core.HighlightColor highlightColor, Color color) {
        this.name = name;
        this.highlightColor = highlightColor;
        this.color = color;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public Color getColor(){
        return this.color;
    }

    /**
     * Factory method to build HighlightColor from display name string
     * @param name Color's display name
     * @return highlight color instance
     */
    public static HighlightColor from(String name) {
        return stream(values())
                .filter(highlightColor -> highlightColor.name.equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
