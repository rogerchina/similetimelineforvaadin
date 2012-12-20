package org.vaadin.chronographer.theme.event;

public class Duration {
    private String color;
    private Integer opacity;
    private String impreciseColor;
    private Integer impreciseOpacity;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getOpacity() {
        return opacity;
    }

    public void setOpacity(Integer opacity) {
        this.opacity = opacity;
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
}
