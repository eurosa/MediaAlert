package com.example.aatick.medialart;

/**
 * Created by MSI PC on 10/5/2015.
 */
public class Media {
    private int id;
    private String medicineName;
    private String date;
    private String time;
    private String dosage;
    private String quantity;
    private String stock;
    private String interval;
    private String description;
    private String doctorname;
    private String location;
    private String disease;
    private  String mobile;

    public Media(){

    }
    public Media(String doctorname, String date, String time, String location, String disease, String description){
        super();
        this.doctorname=doctorname;
        this.date=date;
        this.time=time;
        this.location=location;
        this.disease=disease;
        this.description=description;
    }


    public Media(String medicineName, String date, String time, String dosage, String quantity, String stock,String interval, String description){
        super();
        this.medicineName=medicineName;
        this.date=date;
        this.time=time;
        this.dosage=dosage;
        this.quantity=quantity;
        this.stock=stock;
        this.interval=interval;
        this.description=description;

    }
    public Media(String medicineName,String date, String time, String mobile, String description){
        super();
        this.medicineName=medicineName;
        this.date=date;
        this.time=time;
        this.mobile=mobile;
        this.description=description;
    }

    public void  setId(int id){
        this.id=id;

    }
    public void setMedicineName(String medicineName){
        this.medicineName=medicineName;
    }
    public void setDate(String date){
        this.date=date;
    }
    public  void setTime(String time){
        this.time=time;
    }
    public void setDosage(String dosage){
        this.dosage=dosage;
    }
    public void setQuantity(String quantity){
        this.quantity=quantity;
    }

    public  void setStock(String stock){
        this.stock=stock;
    }

    public void setInterval(String interval){
        this.interval=interval;
    }

    public void setMobile(String mobile){

        this.mobile=mobile;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public int getId(){

        return id;
    }
    public  String getMedicineName(){
        return medicineName;
    }
    public String getDate(){
        return  date;
    }

    public  String getTime(){
        return  time;
    }

    public String getDosage(){
        return  dosage;
    }
    public  String getStock(){
        return  stock;
    }
    public String getInterval(){
        return interval;
    }
    public String getQuantity(){
        return  quantity;
    }

    public  String getDescription(){

        return  description;
    }
    public String getDoctorname(){

        return doctorname;
    }

    public String getDisease(){

        return disease;
    }
    public String getLocation(){

        return location;
    }

    public String getMobile(){
        return mobile;
    }

}
