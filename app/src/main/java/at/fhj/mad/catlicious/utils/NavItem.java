package at.fhj.mad.catlicious.utils;

/**
 * Created by Simone on 17.04.2017.
 *
 * holds the properties for the navigation menu (hamburger menu)
 */

public class NavItem {
    private String title;
    private int icon;

    public NavItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
