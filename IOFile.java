package jframetest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;


public class IOFile {
    public static void serDataByWay(String way,String file_name, Object obj) {
        try{
        FileOutputStream fileOut = new FileOutputStream(way+"\\"+file_name + ".obj");  
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(obj);
        fileOut.close();
        out.close();
        JOptionPane.showMessageDialog(null, "Успішно збережено");
        }catch(FileNotFoundException e){
             JOptionPane.showMessageDialog(null, "Файл не знайдений");
            System.exit(1);
        }catch (IOException ex) {
                System.out.println("Помилка вводу/виводу");
                System.exit(2);
        }
    }
        public static void serData(String file_name, Object obj) {
        try{
        FileOutputStream fileOut = new FileOutputStream(file_name + ".obj");  
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(obj);
        fileOut.close();
        out.close();
        }catch(FileNotFoundException e){
             System.out.println("File not found");
            System.exit(1);
        }catch (IOException ex) {
                System.out.println("Помилка вводу/виводу");
                System.exit(2);
        }
    }

    public  Object deserData(String file_name) {
        Object redObject = null;
        try{
        FileInputStream fileIn = new FileInputStream(file_name + ".obj");  
        ObjectInputStream in = new ObjectInputStream(fileIn);
        redObject = in.readObject();    
        fileIn.close();
        in.close();
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "Файл не знайдений,"
                    + " вставте файл із даними в одну директорію із програмою");
            System.exit(1);
        }catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Помилка Вводу/Виводу");
                System.exit(2);
        }catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Клас не знайдений");
            }
        return redObject;
    }

}
