package org.vaadin.chronographer.gwt.client.model.theme.ether;

public class EthersTheme {
    private String[] backgroundColors;
    private String highlightColor;
    private Integer highlightOpacity;
    private Interval interval;

    public String[] getBackgroundColors() {
        return backgroundColors;
    }

    public void setBackgroundColors(String[] backgroundColors) {
        this.backgroundColors = backgroundColors;
    }

    public String getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(String highlightColor) {
        this.highlightColor = highlightColor;
    }

    public Integer getHighlightOpacity() {
        return highlightOpacity;
    }

    public void setHighlightOpacity(Integer highlightOpacity) {
        this.highlightOpacity = highlightOpacity;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }
}
