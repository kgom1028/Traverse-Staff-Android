package solutions.it.zanjo.travease.Model;

import java.io.Serializable;

/**
 * Created by abc on 6/1/2017.
 */

public class StaffSelection implements Serializable {

    int id;
    String first_name,last_name;

    public StaffSelection(int id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        return first_name+" "+last_name;
    }
}
