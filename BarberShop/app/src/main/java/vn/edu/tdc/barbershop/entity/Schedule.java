package vn.edu.tdc.barbershop.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Schedule implements Serializable {
    private String id;
    private User customer;
    private Service service;
    private Date timeOrder;
    private Date timeFinish;
    private int isFinish;

    public Schedule() {

    }

    public Schedule(String id, User customer, Service service, Date timeOrder, Date timeFinish, int isFinish) {
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

    public Date getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(Date timeOrder) {
        this.timeOrder = timeOrder;
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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("isFinish", isFinish);

        return result;
    }
}
