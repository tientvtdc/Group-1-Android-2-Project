package vn.edu.tdc.barbershop.entity;

import java.io.Serializable;
import java.util.Calendar;

public class Schedule implements Serializable {
    private String id;
    private User customer;
    private Service service;
    private Calendar timeOrder;
    private Calendar timeFinish;
    private int isFinish;

    public Schedule(String id, User customer, Service service, Calendar timeOrder, Calendar timeFinish, int isFinish) {
        this.id = id;
        this.customer = customer;
        this.service = service;
        this.timeOrder = timeOrder;
        this.timeFinish = timeFinish;
        this.isFinish = isFinish;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Calendar getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(Calendar timeOrder) {
        this.timeOrder = timeOrder;
    }

    public Calendar getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(Calendar timeFinish) {
        this.timeFinish = timeFinish;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }
}
