package org.vaadin.chronographer.theme.event;

public class Instant {
    private String icon;
    private String lineColor;
    private String impreciseColor;
    private Integer impreciseOpacity;
    private Boolean showLineForNoText;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public String getImpreciseColor() {
        return impreciseColor;
    }

    public void setImpreciseColor(String impreciseColor) {
        this.impreciseColor = impreciseColor;
    }

    public Integer getImpreciseOpacity() {
        return impreciseOpacity;
    }

    public void setImpreciseOpacity(Integer impreciseOpacity) {
        this.impreciseOpacity = impreciseOpacity;
    }

    public Boolean getShowLineForNoText() {
        return showLineForNoText;
    }

    public void setShowLineForNoText(Boolean showLineForNoText) {
        this.showLineForNoText = showLineForNoText;
    }
}
