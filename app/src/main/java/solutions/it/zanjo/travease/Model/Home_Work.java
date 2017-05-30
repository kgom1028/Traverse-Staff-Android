package solutions.it.zanjo.travease.Model;

import java.io.Serializable;

/**
 * Created by abc on 5/9/2017.
 */

public class Home_Work implements Serializable {

    String id,service_id,reservation_id,guest_id,view_work,request_status,time,date,room_no;

    public Home_Work(String id,String service_id,String reservation_id,String guest_id, String view_work, String request_status, String time, String date, String room_no) {
        this.id = id;
        this.service_id=service_id;
        this.reservation_id=reservation_id;
        this.guest_id=guest_id;
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

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(String guest_id) {
        this.guest_id = guest_id;
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
