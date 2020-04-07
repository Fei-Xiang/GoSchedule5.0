package com.example.goschedule20;

public class Employee {

    private String FullName;
    private String Email;
    private float PhoneNo;
    private String Position;
    private String MondayAvailability;
    private String TuesdayAvailability;
    private String WednesdayAvailability;
    private String ThursdayAvailability;
    private String FridayAvailability;
    private String SaturdayAvailability;
    private String SundayAvailability;

    public Employee(){
    }
    public String getFullName(){

        return FullName;
    }
    public void setFullName(String name){

        FullName = name;
    }
    public float getPhoneNo(){

        return PhoneNo;
    }

    public void setPhoneNo(float phoneNo){

        PhoneNo = phoneNo;
    }
    public String getEmail(){

        return Email;
    }
    public void setEmail(String email){

        Email = email;
    }
    public String getPosition(){

        return Position;
    }
    public void setPosition(String position){

        Position = position;
    }
    public String getMondayAvailability(){

        return MondayAvailability;
    }
    public void setMondayAvailability(String availability){

        MondayAvailability = availability;
    }
    public String getTuesdayAvailability(){

        return TuesdayAvailability;
    }
    public void setTuesdayAvailability(String availability){

        TuesdayAvailability = availability;
    }
    public String getWednesdayAvailability(){

        return WednesdayAvailability;
    }
    public void setWednesdayAvailability(String availability){

        WednesdayAvailability = availability;
    }
    public String getThursdayAvailability(){

        return ThursdayAvailability;
    }
    public void setThursdayAvailability(String availability){

        ThursdayAvailability = availability;
    }
    public String getFridayAvailability(){

        return FridayAvailability;
    }
    public void setFridayAvailability(String availability){

        FridayAvailability = availability;
    }
    public String getSaturdayAvailability(){

        return SaturdayAvailability;
    }
    public void setSaturdayAvailability(String availability){

        SaturdayAvailability = availability;
    }
    public String getSundayAvailability(){

        return SundayAvailability;
    }
    public void setSundayAvailability(String availability){

        SundayAvailability = availability;
    }
}
