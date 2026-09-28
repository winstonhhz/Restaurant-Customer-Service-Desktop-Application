/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restaurantcustomerservice.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.List;

/**
 *
 * @author Hello
 */
public class ChatIntoJson {
    
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public ArrayList<ChatData> loadChat(String filepath){
        File file = new File(filepath);
        if (!file.exists())
            return new ArrayList<>();
        try (FileReader reader = new FileReader(file)){
            Type ArrayListType = new TypeToken<ArrayList<ChatData>>(){}.getType();
            return gson.fromJson(reader, ArrayListType);
        
        } catch (IOException e){
            System.out.println("Error: " + e);
            return new ArrayList<>();
        }
        
    }
    
    public void appendMessage(String filepath, ChatData chatdata){
        ArrayList<ChatData> data = loadChat(filepath);
        data.add(chatdata);
        
        try(FileWriter fw = new FileWriter(filepath)){
            gson.toJson(data, fw);
        
        } catch (IOException e){
            System.out.println("Error: " + e);
        }
    
    }
    
    public String getTimestamp(){
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
    
    
    
}
