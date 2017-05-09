package org.jaffa.presentation.portlet.widgets.model;

/**
 * A very light-weight IWidgetModel subclass that simply holds a string value.
 * 
 * Note: An EditBoxModel could have been used, but that class provices
 *       too much extra 'bagage' for simply storing a string.
 */
public class StringModel implements IWidgetModel {
    private String s;
    public StringModel(String s) {
        this.s = s;
    }
    public StringModel() {
        s = "";
    }
    public String toString() {
        return s;
    }
}
