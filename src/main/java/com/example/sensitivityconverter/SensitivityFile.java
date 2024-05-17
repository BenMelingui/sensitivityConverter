package com.example.sensitivityconverter;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class SensitivityFile {

    public SensitivityFile(){
        try {
            File myObj = new File("yaws.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("yaws.txt");


            myWriter.write("Overwatch;0.0066\n");
            myWriter.write("Counter-Strike 2;0.022\n");
            myWriter.write("Valorant;0.07");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  static HashMap<String,Double> getYawsFromFile(){
        HashMap<String,Double> gamesYaws = new HashMap<String,Double>();
        try{
            Scanner fs = new Scanner(new File("yaws.txt"));
            while(fs.hasNextLine()){
                String[] line = fs.nextLine().split(";");
                //System.out.println(line[0]+line[1]);
                gamesYaws.put(line[0],Double.parseDouble(line[1]));
            }
            fs.close();
            //System.out.println(gamesYaws);

        }catch(FileNotFoundException e){
            //System.out.println("file not found");
        }

        return gamesYaws;

    }
}
