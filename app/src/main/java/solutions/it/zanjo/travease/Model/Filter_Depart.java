package solutions.it.zanjo.travease.Model;

import java.io.Serializable;

/**
 * Created by abc on 5/30/2017.
 */

public class Filter_Depart implements Serializable {

    int id;
    String name;

    public Filter_Depart(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
