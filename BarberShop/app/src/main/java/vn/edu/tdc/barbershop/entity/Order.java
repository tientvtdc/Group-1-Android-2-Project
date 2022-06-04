package vn.edu.tdc.barbershop.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    private String id;
    private User customer;
    private Service service;
    private long timeOrder;
    private long timeFinish;
    private int finish;

    public Order() {

    }

    public Order(String id, User customer, Service service, long timeOrder, long timeFinish, int finish) {
        this.id = id;
        this.customer = customer;
        this.service = service;
        this.timeOrder = timeOrder;
        this.timeFinish = timeFinish;
        this.finish = finish;
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

    public long getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(long timeOrder) {
        this.timeOrder = timeOrder;
    }

    public long getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(long timeFinish) {
        this.timeFinish = timeFinish;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("finish", finish);

        return result;
    }

    public Calendar getCalendarOrder() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeOrder);
        return calendar;
    }

    public Calendar getCalendarFinish() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeFinish);
        return calendar;
    }
}
