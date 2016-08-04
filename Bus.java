package jframetest;

import java.io.Serializable;
import java.util.ArrayList;

public class Bus implements Serializable{
    private static IOFile file = new IOFile(); // Для читання з файлу
    public String way;
    public int places, platform;
    public ArrayList<String> towns;
    public int hour, minute;
    public int tickets;
    public static ArrayList<Bus> buses = (ArrayList<Bus>) file.deserData("buses");
    
    
    
    
    public Bus(String way, int places, int platform,ArrayList<String> towns,  int hour, int minute){
        this.way = way;
        this.places = places;
        this.tickets = places;
        this.platform = platform;
        this.towns = towns;
        this.hour = hour;
        this.minute = minute;
        
    }
    public static void checkBuses(){
        try{
             buses = (ArrayList<Bus>) file.deserData("buses");
        }catch (Exception ex){
            System.out.println("Hello");
        }
    }
    public Bus(){
        
    }
    public void addBus(String way, int places, int platform,ArrayList<String> towns, int hour, int minute){
       buses.add(new Bus(way,places,platform,towns,hour,minute));
    }
    
    public Bus getBus(){
        return this;
    }
    public static void removeBus(int index){
        Bus.buses.remove(index);
    }
    public static void removeAllBuses(){
        Bus.buses.clear();
    }
}
