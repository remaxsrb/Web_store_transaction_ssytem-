/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author remax
 */

public class Client {

    private static final byte EXIT = 0;
    private static final byte CREATE_CITY = 1;
    private static final byte CREATE_USER = 2;
    private static final byte WIRE_MONEY_TO_USER = 3;
    private static final byte CHANGE_USER_ADDRESS = 4;
    private static final byte CREATE_CATEGORY = 5;
    private static final byte CREATE_ARTICLE = 6;
    private static final byte MODIFY_ARTICLE_PRICE = 7;
    private static final byte ADD_ARTICLE_DISCOUNT = 8;
    private static final byte ADD_TO_CART = 9;
    private static final byte REMOVE_FROM_CART = 10;
    private static final byte PAYMENT = 11;
    private static final byte ALL_CITIES = 12;
    private static final byte ALL_USERS = 13;
    private static final byte ALL_CATEGORIES = 14;
    private static final byte ALL_ARTICLES_USER_IS_SELLING = 15;
    private static final byte VIEW_CART = 16;
    private static final byte USER_ORDERS = 17;
    private static final byte ALL_ORDERS = 18;
    private static final byte ALL_TRANSACTIONS = 19;
    
    private static Boolean isCreated = false;
    
    private void  run () 
    {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        byte userInput;
         
        System.out.println("--------------------[ OPCIJE ]---------------------");
        System.out.println("\t1.  Kreiranje grada");
        System.out.println("\t2.  Kreiranje korisnika");
        System.out.println("\t3.  Dodavanje novca korisniku");
        System.out.println("\t4.  Promena adrese i grada za korisnika");
        System.out.println("\t5.  Kreiranje kategorije");
        System.out.println("\t6.  Kreiranje artikla");
        System.out.println("\t7.  Menjanje cene artikla");
        System.out.println("\t8.  Postavljanje popusta za artikal");
        System.out.println("\t9.  Dodavanje artikla u odredjenoj kolicini u korpu");
        System.out.println("\t10. Brisanje artikla u odredjenoj kolicni iz korpe");
        System.out.println("\t11. Plaćanje, koje obuhvata kreiranje transakcije, kreiranje narudžbine sa njenim stavkama, i brisanje sadržaja iz korpe");
        System.out.println("\t12. Dohvatanje svih gradova");
        System.out.println("\t13. Dohvatanje svih korisnika");
        System.out.println("\t14. Dohvatanje svih kategorija");
        System.out.println("\t15. Dohvatanje svih artikala koje prodaje korisnik koji je poslao zahtev");
        System.out.println("\t16. Dohvatanje sadržaja korpe korisnika koji je poslao zahtev");
        System.out.println("\t17. Dohvatanje svih narudžbina korisnika koji je poslao zahtev");
        System.out.println("\t18. Dohvatanje svih narudžbina");
        System.out.println("\t19. Dohvatanje svih transakcija");
        System.out.println("\t0.  Kraj rada");
        System.out.println("---------------------------------------------------");
        
        String cityName, cityCountry, username, userFirstName, userLastName,
                userStreet, categoryName, articleName;
        
        int idKorisnik, idArtikal; 
        
        while(true) 
        {
            System.out.println("Unesite broj opcije: ");
            try {
                userInput = Byte.parseByte(input.readLine());
            } catch (IOException ex) { userInput = 0; }
            
            try {
                    switch (userInput) {
                case CREATE_CITY:
                    System.out.println("Naziv grada: ");
                    cityName = input.readLine();
                    System.out.println("Naziv drzave: ");
                    cityCountry = input.readLine();
                    createCity(cityName, cityCountry);
                    break;
                case CREATE_USER:
                    
                    break;
                case WIRE_MONEY_TO_USER:
                    
                    break;
                case CHANGE_USER_ADDRESS:
                    
                    break;
                case CREATE_CATEGORY:
                    
                    break;
                case CREATE_ARTICLE:
                    
                    break;
                case MODIFY_ARTICLE_PRICE:
                    
                    break;
                case ADD_ARTICLE_DISCOUNT:
                    
                    break;
                case ADD_TO_CART:
                    
                    break;
                case REMOVE_FROM_CART:
                    
                    break;
                case PAYMENT:
                    
                    break;
                case ALL_CITIES:
                    
                    break;
                case ALL_USERS:
                    
                    break;
                case ALL_CATEGORIES:
                    
                    break;
                case ALL_ARTICLES_USER_IS_SELLING:
                    
                    break;
                
                case VIEW_CART:
                    
                    break;
               
                case USER_ORDERS:
                    
                    break;
               
                case ALL_ORDERS:
                    
                    break;
             
                case ALL_TRANSACTIONS:
                    
                    break;
                case EXIT:
                   
                    break;
            }
            } catch (IOException ex) {
            }
        }
    }
    
    public static Client create() 
    {
        if(!isCreated) 
        {
            isCreated = true;
            return new Client();
        }
        return null;
    }
    
    public static void main(String[] args) {
        Client c = create();
        c.run();
    }
    
    private void createCity(String cityName, String countryName) 
    {
        String URLAddress = "http://localhost:8080/server/api/cities";
        String inputString = null;
        int responseCode = 0;
        
        try {
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
         
                myHttpConnection.setRequestMethod("POST");
                myHttpConnection.setDoOutput(true);
                
                StringBuffer queryParameters = new StringBuffer();
               
                queryParameters.append("cityName=");
                queryParameters.append(cityName);
                queryParameters.append("&");
                queryParameters.append("cityCountry=");
                queryParameters.append(countryName);
                
                OutputStream output = myHttpConnection.getOutputStream();
                output.write(queryParameters.toString().getBytes());
                output.flush();
                
                responseCode = myHttpConnection.getResponseCode();
                
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "POST", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                 BufferedReader input = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                while ((inputString = input.readLine()) != null) 
                    System.out.println(inputString);
                input.close();   
                System.out.println("----------------------------------------------------------");
                
            } catch (IOException e) {e.printStackTrace();}
            
        } catch (MalformedURLException e) {e.printStackTrace();}
            
        
    }
}
