package io.github.sachintha_madubashana.libraryos.model;

import org.kordamp.ikonli.javafx.FontIcon;

import java.io.Serializable;

public class Route implements Serializable {
    private String viewName;
    private String url;
    private String title;
    private String subtitle;
    private FontIcon icon;

    public Route(String viewName, String url, String title, String subtitle, FontIcon icon) {
        this.viewName = viewName;
        this.url = url;
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public FontIcon getIcon() {
        return icon;
    }

    public void setIcon(FontIcon icon) {
        this.icon = icon;
    }
}
