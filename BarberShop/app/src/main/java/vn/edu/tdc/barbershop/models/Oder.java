package vn.edu.tdc.barbershop.models;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class Oder extends AppCompatActivity {
    private int id;
    private User customer;
    private Service service;
    private Date timeOder;
    private Date timeFinish;
    private int isFinish;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Date getTimeOder() {
        return timeOder;
    }

    public void setTimeOder(Date timeOder) {
        this.timeOder = timeOder;
    }

    public Date getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(Date timeFinish) {
        this.timeFinish = timeFinish;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public Oder(int id, User customer, Service service, Date timeOder, Date timeFinish, int isFinish) {
        this.id = id;
        this.customer = customer;
        this.service = service;
        this.timeOder = timeOder;
        this.timeFinish = timeFinish;
        this.isFinish = isFinish;
    }

    public Oder(int contentLayoutId, int id, User customer, Service service, Date timeOder, Date timeFinish, int isFinish) {
        super(contentLayoutId);
        this.id = id;
        this.customer = customer;
        this.service = service;
        this.timeOder = timeOder;
        this.timeFinish = timeFinish;
        this.isFinish = isFinish;
    }



}
