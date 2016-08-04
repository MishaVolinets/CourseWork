package jframetest;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class Controller {
    

    public ArrayList stringToWords(String str){    //Функція для перетворення слів
        ArrayList<String> words = new ArrayList(); //в массив слів
        String word = "";
        char[] ch = str.toCharArray();
        for(int i =0;i<ch.length;i++){
            if(ch[i] == ' '){
                words.add(word);
                word = "";
            }else{
                word += ch[i];
            }
            if(i == (ch.length-1)){
                words.add(word);
                word = "";
            }
        }
        
        return words;
    }
    
    public ArrayList<Bus> sortByTime(ArrayList<Bus> array){
        ArrayList<Bus> sortedArray = new ArrayList(array);
        
        for(int i =0;i<sortedArray.size();i++){
            for(int j = 0;j<sortedArray.size()-1;j++){
                if(sortedArray.get(j).hour>sortedArray.get(j+1).hour){
                    Bus tmp = sortedArray.get(j);
                    sortedArray.set(j, sortedArray.get(j+1));
                    sortedArray.set(j+1, tmp);              
                }
            }
        }
        for(int i =0;i<sortedArray.size();i++){
            for(int j = 0;j<sortedArray.size()-1;j++){
                if(sortedArray.get(j).hour == sortedArray.get(j+1).hour
                   &&sortedArray.get(j+1).minute < sortedArray.get(j).minute){
                    Bus tmp = sortedArray.get(j);
                    sortedArray.set(j, sortedArray.get(j+1));
                    sortedArray.set(j+1, tmp);
                }
            }
        }
        return sortedArray;
    }
    
    public ArrayList<Bus> sortByNowTime(ArrayList<Bus> array){
        ArrayList<Bus> sortedArray = new ArrayList(array);
        
        sortedArray = sortByTime(sortedArray);
   
        for(int i =0;i<sortedArray.size();i++){
            if(new Date().getHours() >= sortedArray.get(i).hour){
                if(new Date().getHours() == sortedArray.get(i).hour && new Date().getMinutes()
                        < sortedArray.get(i).minute){
                   
                }else{
                    sortedArray.remove(i);
                    i--;
                }
            }
               
        }

        
        
        return sortedArray;
    }
    
    public void printBuses(int WIDTH,int HEIGTH,int from,int to, JPanel p,ArrayList<Bus> buses){  
            int margin = 0;
                for(int i = from;i < to;i++){
                JLabel label = new JLabel( (i+1)+".Тернопіль-"+buses.get(i).way 
                        + " - " + checkHour(buses.get(i).hour)
                        +":"+checkHour(buses.get(i).minute));
           
                label.setLocation(WIDTH,(margin*20) + HEIGTH);
                label.setSize(300,20);
                p.add(label);
                margin++;
                }
    }
    public void printTime(int hour,int minute,int width,int height,JPanel p){
        JLabel label = new JLabel(checkHour(hour));
        label.setLocation(width,height);
        label.setSize(20,20);
        p.add(label);
        label = new JLabel(":");
        label.setLocation(width+15,height);
        label.setSize(5,20);
        p.add(label);
        label = new JLabel(checkHour(minute));
        label.setLocation(width+20,height);
        label.setSize(20,20);
        p.add(label);
    }
    

    public ArrayList<Bus> schudleFunction(String direction){  
        ArrayList<Bus> busArray = new ArrayList();
        for(int i = 0;i<Bus.buses.size();i++){   
                if(Bus.buses.get(i).way.equals(direction)){
                    busArray.add(Bus.buses.get(i));
            }
        }
        for(int i = 0;i<Bus.buses.size();i++){
            for(int j = 0;j<Bus.buses.get(i).towns.size();j++){
                if(Bus.buses.get(i).towns.get(j).equals(direction)){
                    busArray.add(Bus.buses.get(i));
                }
            }
        }
        busArray = sortByTime(busArray);
        return busArray;
    }
    
    public void removeBus(int index){
        Bus.buses.remove(index);
    }
    
    public String checkHour(int hour){
        String newHour= "";
        if(hour<10){
            newHour = "0"+Integer.toString(hour);
        }else newHour = Integer.toString(hour);
        return newHour;
    }
        public String checkMinute(int minute){
        String newMinute= "";
        if(minute<10){
            newMinute = "0"+Integer.toString(minute);
        }else newMinute = Integer.toString(minute);
        return newMinute;
    }
        public Bus findBus(String way,int hour, int minute){
            Bus bus = null;
            for(int i =0;i<Bus.buses.size();i++){
                if(Bus.buses.get(i).way.equals(way)){
                    if(Bus.buses.get(i).hour==hour){
                        if(Bus.buses.get(i).minute == minute){
                            return Bus.buses.get(i);
                        }
                    }
                }
            }
           return null;
        }
        
}

