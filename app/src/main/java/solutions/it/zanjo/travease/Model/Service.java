package solutions.it.zanjo.travease.Model;

import java.io.Serializable;

/**
 * Created by abc on 5/31/2017.
 */

public class Service implements Serializable {

    String item_name;
    int displayorder;

    public Service(String item_name, int displayorder) {
        this.item_name = item_name;
        this.displayorder = displayorder;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(int displayorder) {
        this.displayorder = displayorder;
    }
}
