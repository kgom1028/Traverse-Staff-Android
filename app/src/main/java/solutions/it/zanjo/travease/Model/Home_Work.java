package solutions.it.zanjo.travease.Model;

import java.io.Serializable;

/**
 * Created by abc on 5/9/2017.
 */

public class Home_Work implements Serializable {

    String id,view_work,request_status,time,date,room_no;

    public Home_Work(String id, String view_work, String request_status, String time, String date, String room_no) {
        this.id = id;
        this.view_work = view_work;
        this.request_status = request_status;
        this.time = time;
        this.date = date;
        this.room_no = room_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getView_work() {
        return view_work;
    }

    public void setView_work(String view_work) {
        this.view_work = view_work;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }
}
