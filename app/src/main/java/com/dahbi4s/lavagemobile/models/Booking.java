package com.dahbi4s.lavagemobile.models;

public class Booking {

    public enum State {
        RUNNING,
        WAITING,
        DONE
    };

    private String BookId;
    private String UserId;
    private String Phone;
    private String Name;
    private String Day;
    private String Time;
    private String Price;
    private Package.PACK LavageType;
    private String CarType;
    private Coordinate Address;
    private State Status;


    public State getStatus() {
        return Status;
    }

    public void setStatus(State status) {
        Status = status;
    }

    public Coordinate getAddress() {
        return Address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAddress(Coordinate address) {
        Address = address;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public Package.PACK getLavageType() {
        return LavageType;
    }

    public void setLavageType(Package.PACK lavageType) {
        LavageType = lavageType;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }


}
