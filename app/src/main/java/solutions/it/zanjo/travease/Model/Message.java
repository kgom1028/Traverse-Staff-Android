package solutions.it.zanjo.travease.Model;

/**
 * Created by Microsoft on 23-Sep-16.
 */
public class Message {

    String mid;
    String message;
    String mtype;
    String time1;

    public Message() {
    }

    public Message(String mid, String message, String mtype, String time1) {
        this.mid = mid;
        this.message = message;
        this.mtype = mtype;
        this.time1 = time1;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }
}
