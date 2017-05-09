package solutions.it.zanjo.travease.Model;

import java.io.Serializable;

/**
 * Created by abc on 5/9/2017.
 */

public class ChatList implements Serializable {

    String id,name,time,room_no;

    public ChatList(String id, String name, String time, String room_no) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.room_no = room_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }
}
