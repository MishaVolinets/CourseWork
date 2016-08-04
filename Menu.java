package jframetest;

import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Date;

public class Menu extends JFrame{
    private Bus bus = new Bus();
    
    private Controller c = new Controller();
    
    String[] arrayHours ={"00","01","02","03","04","05","06","07","08","09",
            "10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
    String[] arrayMinutes ={"00","01","02","03","04","05","06","07","08","09",
            "10","11","12","13","14","15","16","17","18","19","20","21","22","23",
            "24","25","26","27","28","29","30","31","32","33","34","35","36","37",
            "38","39","40","41","42","43","44","45","46","47","48","49","50","51",
            "52","53","54","55","56","57","58","59"};
    String[] arrayPlatform = {"1","2","3","4","5","6","7","8","9","10","11","12"};
    
    
    private int nowHours= new Date().getHours();
    private int nowMinutes =  new Date().getMinutes();
    int from = 0;
    int to = 10;
    
    private JPanel Panel = new JPanel();
    private JButton simpleMenu, adminMenu, back,save, schudle, info, buyTicket;
    private JButton addBusPanel, removeBusPanel, addBus, busList;
    private JComboBox platform,hours, minutes;
    private JTextField way, places,towns;
    
    private ActionListener aListener = new listener();
    
    private Font font = new Font("Verdana", Font.BOLD, 18);
    
    public Menu(String title){
        super(title);
        setContentPane(Panel);
        setSize(480,300);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        majorFrame();
    }
    
  //////////////////////////ГОЛОВНЕ ВІКНО///////////////////////////////////////   
    private void majorFrame (){
        clearPanel(Panel);
        Panel.setLayout(null);
        
        
        addLabel("Курсова робота",140,0,170,36,font);
        
        addLabel("На тему: Розклад руху автобусів",125,44,188,14);
        
        addLabel("Виконав студент группи СІ-11",135,68,188,14);
        
        addLabel("Волинець Михайло",165,92,188,14);
        
        addLabel("Оберіть тип меню: ",175,136,188,14);
        
        simpleMenu = new JButton("Меню користувача");
        simpleMenu.setLocation(30, 170);
        simpleMenu.setSize(150,40);
        simpleMenu.addActionListener(aListener);
        
        adminMenu = new JButton("Меню керування");
        adminMenu.setLocation(280, 170);
        adminMenu.setSize(150,40);
        adminMenu.addActionListener(aListener);
        
        Panel.add(simpleMenu);
        Panel.add(adminMenu);
    }
    private class listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == simpleMenu)menu();
            if(e.getSource() == adminMenu)adminMenu();
            if(e.getSource() == schudle)schudle();
            if(e.getSource() == addBusPanel)addBusPanel();
            if(e.getSource() == save){
                JFileChooser dialog = new JFileChooser();
                dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                dialog.showOpenDialog(Panel);
                File workingDirectory = new File(System.getProperty("user.dir"));
                dialog.setCurrentDirectory(workingDirectory);
                dialog.setAcceptAllFileFilterUsed(false);
                dialog.setDialogTitle("Зберегти файл");
                try{
                IOFile.serDataByWay(dialog.getSelectedFile().toString(),"buses", Bus.buses);
                }catch(Exception ex){
                    System.out.println("FileChooser is closed");
                }
            }
            if(e.getSource() == addBus){
                try{
                String w =  way.getText();
                int place = Integer.valueOf(places.getText());
                int platfo = Integer.valueOf(platform.getSelectedItem().toString());
                ArrayList<String> t = c.stringToWords(towns.getText());
                int hour = Integer.valueOf(hours.getSelectedItem().toString());
                int minute = Integer.valueOf(minutes.getSelectedItem().toString());
                bus.addBus(w, place, platfo,t, hour, minute);
                JOptionPane.showMessageDialog(null, "Автобус добавлений");
                adminMenu();
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Неправильно введені дані");
                }
            }
            if(e.getSource() == busList&&Bus.buses.isEmpty()) JOptionPane.showMessageDialog(null, "Автобусів немає");
            else if(e.getSource()==busList)busList();
            
            if(e.getSource()==info)information();
            if(e.getSource() == buyTicket)buyTicket();
            
        }
    
    }
    /////////////////////////////ГОЛОВНЕ ВІКНО ЗАКІНЧЕНЕ////////////////////////
    
    /////////////////////////////МЕНЮ КОРИСТУВАЧА///////////////////////////////
    private void menu(){
        clearPanel(Panel);
        
        back = new JButton("Назад");
        back.setLocation(10, 10);
        back.setSize(100,20);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==back)majorFrame();
            }
        });
        Panel.add(back);  
        
        addLabel(c.checkHour(new Date().getHours())+":"+c.checkMinute(new Date().getMinutes()),40,40,60,20,font);
        
        ArrayList<Bus> sorted = c.sortByNowTime(Bus.buses);
        int s = (sorted.size()<=10)?sorted.size():10;
        for(int i =0;i< s;i++){
          addLabel((i+1) +". Тернопіль-"+sorted.get(i).way + " " + c.checkHour(sorted.get(i).hour)+":"
                  +c.checkMinute(sorted.get(i).minute),
                  40,(i*20) +60,200,20);
          if(i == 10)break;
       }
       
        schudle = new JButton("Рокзлад");
        schudle.setLocation(280,30);
        schudle.setSize(180,40);
        schudle.addActionListener(aListener);
        Panel.add(schudle);
        
        info = new JButton("Інформація про автобус");
        info.setLocation(280,116);
        info.setSize(180,40);
        info.addActionListener(aListener);
        Panel.add(info);
        
        buyTicket= new JButton("Купити квиток");
        buyTicket.setLocation(280, 202);
        buyTicket.setSize(180,40);
//        buyTicket.addActionListener(aListener);
        buyTicket.addActionListener((e) -> System.out.println("Hello"));
        Panel.add(buyTicket);
    }
    ///////////////////////////МЕНЮ КЕРУВАННЯ///////////////////////////////////
    private void adminMenu(){
        clearPanel(Panel);
        

        back = new JButton("Назад");
        back.setLocation(10, 10);
        back.setSize(100,20);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==back)majorFrame();
            }
        });
        Panel.add(back); 
        
        addLabel("Меню керування",140,30,180,25,font);
        
        addBusPanel = new JButton("Добавити автобус");
        addBusPanel.setLocation(10, 65);
        addBusPanel.setSize(150,40);
        addBusPanel.addActionListener(aListener);
        Panel.add(addBusPanel);
        
        removeBusPanel = new JButton("Видалити автобус");
        removeBusPanel.setLocation(10, 145);
        removeBusPanel.setSize(150,40);
        removeBusPanel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()== removeBusPanel)removeBus();
            }
            
            
        });
        Panel.add(removeBusPanel);
        
        busList = new JButton("Список автобусів");
        busList.setLocation(10, 225);
        busList.setSize(150,40);
        busList.addActionListener(aListener);
        Panel.add(busList);
        
        save = new JButton("Зберегти");
        save.setLocation(300, 225);
        save.setSize(150,40);
        save.addActionListener(aListener);
        Panel.add(save);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //////////////////ДАЛІ НАБІР ФУНКЦІЙ ДЛЯ МЕНЮ КЕРУВАННЯ/////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    /////////////////////////ДОБАВИТИ АВТОБУС///////////////////////////////////
    private void addBusPanel(){
           
        
        clearPanel(Panel);

        back = new JButton("Назад");
        back.setLocation(10, 10);
        back.setSize(100,20);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==back)adminMenu();
            }
        });
        Panel.add(back); 

        addLabel("Добавити автобус",140,40,190,20,font);
        
        addLabel("Напрямок:",30,80,60,20);

        addLabel("Кількість місць:",30,105,100,20);
        
        addLabel("Платформа:",30,130,80,20);
        
        addLabel("Повз які села проїзджає автобус:",30,155,200,20);
        
        addLabel("Час відправлення:",30,185,110,20);
        
        way = new JTextField();
        way.setLocation(100, 80);
        way.setSize(200,20);
        Panel.add(way);
        
        
        places = new JTextField();
        places.setLocation(140, 105);
        places.setSize(200,20);
        Panel.add(places);
        
        platform = new JComboBox(arrayPlatform);
        platform.setLocation(140,130);
        platform.setSize(50, 20);
        Panel.add(platform);
        
        towns = new JTextField();
        towns.setLocation(240, 155);
        towns.setSize(200, 20);
        Panel.add(towns);
        
        hours = new JComboBox(arrayHours);
        hours.setLocation(150, 185);
        hours.setSize(50,20);
        Panel.add(hours);
        
        minutes = new JComboBox(arrayMinutes);
        minutes.setLocation(210, 185);
        minutes.setSize(50,20);
        Panel.add(minutes);
        
        addBus = new JButton("Добавити");
        addBus.setLocation(140, 215);
        addBus.setSize(190,40);
        addBus.addActionListener(aListener);
        Panel.add(addBus);
    }     
    
    /////////////////////////////СПИСОК АВТОБУСІВ///////////////////////////////
    private void busList(){
        clearPanel(Panel);
      
        back = new JButton("Назад");
        back.setLocation(10, 10);
        back.setSize(100,20);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==back)adminMenu();
            }
        });
        Panel.add(back); 
        try{
        c.printBuses(10,40,from, to, Panel,Bus.buses);
        }catch(Exception e){
            System.out.println("Buses is over");
        }
        JButton next = new JButton("Наступні =>");
        next.setLocation(300, 225);
        next.setSize(110,40);
        next.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == next){
                    if(from + 10 >= Bus.buses.size()){
                        JOptionPane.showMessageDialog(null, "Автобуси закінчились");
                    }else if(to + 10 > Bus.buses.size()){
                        to = Bus.buses.size();
                        from += 10;
                    }else{
                        to+=10;
                        from+=10;
                    }
                    Panel.removeAll();
                    busList();
                    Panel.repaint();
                }
            }
            
        });
        Panel.add(next);
        
        JButton back = new JButton("<= Минулі");
        back.setLocation(300, 165);
        back.setSize(110,40);
        back.addActionListener(new ActionListener(){
        @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == back){
                    if((from-10) < 0){
                        JOptionPane.showMessageDialog(null, "Початок");
                    }else if(to%10==0){
                        to -=10;
                        from -= 10;
                    }else {
                        int remainder = to%10;
                        to -=remainder;
                        from -=10;
                    }
                    
                    
                    Panel.removeAll();
                    busList();
                    Panel.repaint();
                }
            }
        });
        Panel.add(back);
        
    }
    /////////////////////////////ВИДАЛИТИ АВТОБУС///////////////////////////////
    public void removeBus(){
        clearPanel(Panel);
        
        back = new JButton("Назад");
        back.setLocation(10, 10);
        back.setSize(100,20);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==back)adminMenu();
            }
        });
        Panel.add(back); 

        addLabel("Видалити автобус: ",130,40,200,36);
        
        addLabel("Введіть номер автобуса: ",10,100,150,20);
        
        JTextField tf = new JTextField();
        tf.setLocation(160, 100);
        tf.setSize(40,20);
        Panel.add(tf);
        
        JButton del = new JButton("Видалити");
        del.setLocation(45, 130);
        del.setSize(100,20);
        del.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==del){
                    try{
                        Bus.removeBus(Integer.valueOf(tf.getText())-1);
                        JOptionPane.showMessageDialog(null, "Автобус видалено");
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Будь ласка, перевірте значення");
                    }
                }
            }
        });
        Panel.add(del);
        
        JButton delAll = new JButton("Видалити всі автобуси");
        delAll.setLocation(240, 100);
        delAll.setSize(170,50);
        delAll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == delAll){
                    Bus.removeAllBuses();
                    JOptionPane.showMessageDialog(null, "Всі автобуси видалені");
                }
            }
        });
        Panel.add(delAll);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //////////////ФУНКЦІЇ ДЛЯ МЕНЮ КЕРУВАННЯ ЗАКІНЧЕННІ/////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    
    ////////////////////////////////////////////////////////////////////////////
    ///////////////////ФУНКЦІЇ ДЛЯ МЕНЮ КОРИСТУВАЧА/////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    ///////////////////////////////РОЗКЛАД//////////////////////////////////////
    public void schudle(){
        clearPanel(Panel);
        Panel.setFocusable(true);
        JTextField direction = new JTextField();
        JButton show = new JButton("Показати");
        
        back = new JButton("Назад");
        back.setLocation(10, 10);
        back.setSize(100,20);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==back)menu();
            }
        });
        Panel.add(back); 
        
        addLabel("Розклад", 10,50,100,20,font);
        addLabel("Введіть напрямок куда вам потрібно доїхати: ",10,80,270,20);
        
        direction.setLocation(285, 80);
        direction.setSize(100,20);
        Panel.add(direction);
        
        show.setLocation(83, 110);
        show.setSize(200,40);
        show.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(e.getSource() == show){
                    showSchudle(direction.getText());
                }
            }
            
        });
        Panel.add(show);
        Panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("Hello");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Hello");
            }

            @Override
            public void keyReleased(KeyEvent e) {
               System.out.println("Hello");
            }
        });
    };
    
    private void showSchudle(String direction){
        clearPanel(Panel);
        
        ArrayList<Bus> buses = c.schudleFunction(direction);
        
        back = new JButton("Назад");
        back.setLocation(10, 10);
        back.setSize(100,20);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==back)menu();
            }
        });
        Panel.add(back); 
        
        
        addLabel("Розклад автобусів на напрямок: ",77,40,345,20,font);
        addLabel(direction,190,70,200,20,font);
        if(buses.size()==0){
            addLabel("Автобусів немає",160,140,180,20,font);
        }else if(buses.size()>16){
            c.printBuses(75, 100, 0, 8, Panel, buses);
            c.printBuses(245, 100, 8, 16, Panel, buses);
        }else if(buses.size()>8 && buses.size()<=16){
            c.printBuses(75, 100, 0, 8, Panel, buses);
            c.printBuses(245, 100, 8, buses.size(), Panel, buses);
        }else if(buses.size()<=8){
            c.printBuses(10, 100, 0,buses.size(), Panel, buses);
        }
    }
    
    private void information(){
        clearPanel(Panel);
        
        
        
        
        back = new JButton("Назад");
        back.setLocation(10, 10);
        back.setSize(100,20);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==back)menu();
            }
        });
        Panel.add(back);
        
        addLabel("Інформація про автобус",122,40,255,20,font);
        addLabel("Введіть напрямок: ",140,70,120,20);
        addLabel("Введіть час: ",160,100,80,20);
        way = new JTextField();
        way.setLocation(250, 70);
        way.setSize(100,20);
        Panel.add(way);
        
        hours = new JComboBox(arrayHours);
        hours.setLocation(240, 100);
        hours.setSize(50, 20);
        Panel.add(hours);
        
        minutes = new JComboBox(arrayMinutes);
        minutes.setLocation(300, 100);
        minutes.setSize(50,20);
        Panel.add(minutes);
        
        JButton show = new JButton("Показати");
        show.setLocation(200, 130);
        show.setSize(100,40);
        show.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==show){
                    if(c.findBus(way.getText(), Integer.valueOf(hours.getSelectedItem().toString())
                        , Integer.valueOf(minutes.getSelectedItem().toString())) == null){
                        JOptionPane.showMessageDialog(null, "Такого автобуса немає");
                    
                    }else{
                        showBusInformation(c.findBus(way.getText(), Integer.valueOf(hours.getSelectedItem().toString())
                        , Integer.valueOf(minutes.getSelectedItem().toString())));
                    }
                }
            }
        });
        Panel.add(show);
    }
    private void showBusInformation(Bus b){
         clearPanel(Panel);
        
        back = new JButton("Назад");
        back.setLocation(10, 10);
        back.setSize(100,20);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==back)information();
            }
        });
        Panel.add(back);
        addLabel("Автобус за напрямком: Тернопіль-",10,40,200,20);
        addLabel(b.way,210,40,150,20);
        
        addLabel("Час відправлення: ",10,70,120,20);
        c.printTime(b.hour,b.minute,125,70,Panel);
        
        addLabel("Платформа: ", 10,100,100,20);
        addLabel(Integer.toString(b.platform),90,100,20,20);
        
        addLabel("Кількість місць: ",10,130,100,20);
        addLabel(Integer.toString(b.places),110,130,20,20);
        
        addLabel("Кількість вільних місць: ",10,160,150,20);
        addLabel(Integer.toString(b.tickets),155,160,20,20);
        
    }
    
    private void buyTicket(){
                clearPanel(Panel);
        
        
        
        
        back = new JButton("Назад");
        back.setLocation(10, 10);
        back.setSize(100,20);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==back)menu();
            }
        });
        Panel.add(back);
        
        addLabel("Купити квиток",180,10,170,30,font);
        addLabel("Введіть напрямок: ",140,70,120,20);
        way = new JTextField();
        way.setLocation(250, 70);
        way.setSize(100,20);
        Panel.add(way);
        
    
        
        JButton buy = new JButton("Купити");
        buy.setLocation(200, 130);
        buy.setSize(100,40);
        buy.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==buy){
                    ArrayList<Bus> b = new ArrayList();
                    for(int i = 0;i< Bus.buses.size();i++){
                        for(int j = 0;j<Bus.buses.get(i).towns.size();j++){
                            if(way.getText().equals(Bus.buses.get(i).way)||way.getText().equals(Bus.buses.get(i).towns.get(j))){
                                b.add(Bus.buses.get(i));
                                break;
                            }
                        }
                    }
                    if(b.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Автобусів немає");
                    }else choiceBusPanel(b);
                }
            }
            
        });
        
        Panel.add(buy);
    };
    private void choiceBusPanel(ArrayList<Bus> buses){
        clearPanel(Panel);
        
        back = new JButton("Назад");
        back.setLocation(10, 10);
        back.setSize(100,20);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==back)buyTicket();
            }
        });
        Panel.add(back);
        
        ButtonGroup group = new ButtonGroup();
        ArrayList<JRadioButton> a = new ArrayList();
        JRadioButton choice;
        
        JButton buy = new JButton("Купити");
        buy.setLocation(300, 200);
        buy.setSize(150,50);
        Panel.add(buy);
        
        buses = c.sortByNowTime(buses);
        
        for(int i = 0;i<buses.size();i++){
            choice = new JRadioButton("Тернопіль - "+buses.get(i).way + " "
                    +c.checkHour(buses.get(i).hour)+" : "+c.checkMinute(buses.get(i).minute));
            choice.setSize(250,20);
            choice.setLocation(10,35+(i*20));
            a.add(choice);
            group.add(choice);
            Panel.add(choice);
        }
        buy.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Bus b;
                for(int i = 0;i<a.size();i++){
                   if(a.get(i).isSelected()){
                       String text = a.get(i).getText();
                       ArrayList<String> words = c.stringToWords(text);
                       b = c.findBus(words.get(2), Integer.valueOf(words.get(3)),
                               Integer.valueOf(words.get(5)));
                       if(b != null){
                           if(b.tickets>0){
                           b.tickets--;
                           IOFile.serData("buses", Bus.buses);
                           JOptionPane.showMessageDialog(null, "Квиток куплено!");
                           menu();
                           }else JOptionPane.showMessageDialog(null, "Вибачте, квитки закінчились!");
                       }else JOptionPane.showMessageDialog(null, "Помилка программи");
                   }
               }
            }
            
            
        });
    }
    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////ДОПОМІЖНІ ФУНКЦІЇ////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private void clearPanel(JPanel panel){
        panel.removeAll();
        panel.repaint();
    }
    private void addLabel(String text,int locationWidth,int locationHeight,int sizeWidth,int sizeHeight){
        JLabel label = new JLabel(text);
        label.setLocation(locationWidth,locationHeight);
        label.setSize(sizeWidth,sizeHeight);
        Panel.add(label);
    }
    
    private void addLabel(String text,int locationWidth,int locationHeight,int sizeWidth,int sizeHeight,Font f){
        JLabel label = new JLabel(text);
        label.setLocation(locationWidth,locationHeight);
        label.setSize(sizeWidth,sizeHeight);
        label.setFont(f);
        Panel.add(label);
    }

}