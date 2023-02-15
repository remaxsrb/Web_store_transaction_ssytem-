/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


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
        System.out.println("\t1.  Kreiranje grada"); //subsystem1 - DONE
        System.out.println("\t2.  Kreiranje korisnika"); //subsystem1 - DONE
        System.out.println("\t3.  Dodavanje novca korisniku"); //subsystem1 - DONE
        System.out.println("\t4.  Promena adrese korisnika"); //subsystem1 - DONE
        
        System.out.println("\t5.  Kreiranje kategorije"); //subsystem2 - DONE
        System.out.println("\t6.  Kreiranje artikla"); //subsystem2 - DONE
        System.out.println("\t7.  Menjanje cene artikla"); //subsystem2 - DONE
        System.out.println("\t8.  Postavljanje popusta za artikal"); //subsystem2 - DONE
        System.out.println("\t9.  Dodavanje artikla u odredjenoj kolicini u korpu"); //subsystem2 
        System.out.println("\t10. Brisanje artikla u odredjenoj kolicni iz korpe"); //subsystem2
        
        System.out.println("\t11. Plaćanje, koje obuhvata kreiranje transakcije, "
                + "kreiranje narudžbine sa njenim stavkama, i brisanje sadržaja iz korpe");  //subsystem3
        
        System.out.println("\t12. Dohvatanje svih gradova"); //subsystem1 - DONE
        System.out.println("\t13. Dohvatanje svih korisnika"); //subsystem1 - DONE
        
        System.out.println("\t14. Dohvatanje svih kategorija"); //subsystem2 - DONE
        System.out.println("\t15. Dohvatanje svih artikala koje prodaje korisnik koji je poslao zahtev"); //subsystem2 - DONE
        System.out.println("\t16. Dohvatanje sadržaja korpe korisnika koji je poslao zahtev"); //subsystem2
        
        System.out.println("\t17. Dohvatanje svih narudžbina korisnika koji je poslao zahtev"); //subsystem3
        System.out.println("\t18. Dohvatanje svih narudžbina");  //subsystem3
        System.out.println("\t19. Dohvatanje svih transakcija");  //subsystem3
        System.out.println("\t0.  Kraj rada");
        System.out.println("---------------------------------------------------");
        
        String cityName, cityCountry, username, userFirstName, userLastName,
                userStreet, streetNumber, userPassword,
                money, categoryName, superCategoryName ,articleName, articlePrice, articleDescription,
                articleDiscount;
        
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
                    System.out.println("Korisnicko ime: ");
                    username = input.readLine();
                    
                    System.out.println("Ime: ");
                    userFirstName = input.readLine();
                    
                    System.out.println("Prezime: ");
                    userLastName = input.readLine();
                    
                    System.out.println("Sifra: ");
                    userPassword = input.readLine();
                    
                    System.out.println("Ulica: ");
                    userStreet = input.readLine();
                    
                    System.out.println("Broj: ");
                    streetNumber = input.readLine();
                    
                    System.out.println("Grad: ");
                    cityName = input.readLine();
                    
                    System.out.println("Drzava: ");
                    cityCountry = input.readLine();
                    
                    
                    createUser(username, userFirstName, userLastName,
                            userPassword, userStreet, streetNumber ,cityName, cityCountry);
                    
                    break;
                case WIRE_MONEY_TO_USER:
                    
                    System.out.println("Korisnicko ime: ");
                    username = input.readLine();
                    
                    System.out.println("Iznos novca za dodavanje na racun: ");
                    money = input.readLine();
                    
                    wireMoneyToUser(username, money);
                    
                    break;
                case CHANGE_USER_ADDRESS:
                    
                    System.out.println("Korisnicko ime: ");
                    username = input.readLine();
                    
                    System.out.println("Ulica: ");
                    userStreet = input.readLine();
                    
                    System.out.println("Broj: ");
                    streetNumber = input.readLine();
                    
                    changeUserAddress(username, userStreet, streetNumber);
                    
                    break;
                case CREATE_CATEGORY:
                    
                    System.out.println("Ime kategorije: ");
                    categoryName = input.readLine();
                    
                    System.out.println("Ime nadkategorije (ako kategorija koja se kreira nije potkategorija neke druge pritisnuti x): ");
                    superCategoryName = input.readLine();
                    
                    createCategory(categoryName, superCategoryName);
                    
                    break;
                case CREATE_ARTICLE:
                    
                    System.out.println("Naziv artikla: ");
                    articleName = input.readLine();
                    
                    System.out.println("Cena artikla: ");
                    articlePrice = input.readLine();
                    
                    System.out.println("Opis artikla (ako artikal koji se kreira nema opis - pritisnuti x): ");
                    articleDescription = input.readLine();
                    
                    System.out.println("Kategorija artikla");
                    categoryName = input.readLine();
                    
                    System.out.println("Korisnik koji prodaje artikal");
                    username = input.readLine();
                    
                    
                    createArticle(articleName, articlePrice, articleDescription, categoryName, username);
                    
                    break;
                case MODIFY_ARTICLE_PRICE:
                    System.out.println("Naziv artikla: ");
                    articleName = input.readLine();
                    
                    System.out.println("Nova cena artikla: ");
                    articlePrice = input.readLine();
                    
                    changeArticlePrice(articleName, articlePrice);
                    
                    break;
                case ADD_ARTICLE_DISCOUNT:
                    System.out.println("Naziv artikla: ");
                    articleName = input.readLine();
                    
                    System.out.println("Popust za artikal: ");
                    articleDiscount = input.readLine();
                    
                    setArticleDiscount(articleName, articleDiscount);
                    
                    break;
                case ADD_TO_CART:
                    
                    break;
                case REMOVE_FROM_CART:
                    
                    break;
                case PAYMENT:
                    
                    break;
                case ALL_CITIES:
                    getCities();
                    break;
                case ALL_USERS:
                    getUsers();
                    break;
                case ALL_CATEGORIES:
                    getCategories();
                    break;
                case ALL_ARTICLES_USER_IS_SELLING:
                    
                    System.out.println("Korisnicko ime korisnika: ");
                    username = input.readLine();
                    getUserArticles(username);
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
    
    private String[] trimIncomingString(String string) 
    {
        string = string.replace("[", "");
        string = string.replace("]", "");
        string = string.replace("\"", "");
        
        String[] strings = string.split(",");
        
        return strings;
    }
    
    private void createCity(String cityName, String cityCountry) 
    {
        String errMsg = "Greska pri povezivanju";
        
        try {
            String URLAddress = "http://localhost:8080/Server/store/cities/createCity/" + cityName +"/" + cityCountry;
            
            String inputString = null;
            int responseCode = 0;
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                
                myHttpConnection.setRequestMethod("POST");
                myHttpConnection.setDoOutput(true);
               
                
                responseCode = myHttpConnection.getResponseCode();
                
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "POST", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                 BufferedReader input = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                while ((inputString = input.readLine()) != null) 
                    System.out.println(inputString);
                input.close();   
                System.out.println("----------------------------------------------------------");
                
            } catch (IOException e) {}
            
        } catch (MalformedURLException e) {System.out.println(errMsg);}
            
        
    }
    
     private void createUser(String userName, String firstName, String lastName, String password, String street, String streetNumber, String cityName, String cityCountry) 
    {
        String errMsg = "Greska pri povezivanju";
        
        try {
            String URLAddress = "http://localhost:8080/Server/store/users/createUser/"
                    + userName + "/" + firstName + "/"+ lastName + "/" + password 
                    + "/" + street + "/" +streetNumber + "/" + cityName + "/" + cityCountry;
            
            
            String inputString = null;
            int responseCode = 0;
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                
                myHttpConnection.setRequestMethod("POST");
                myHttpConnection.setDoOutput(true);
               
                
                responseCode = myHttpConnection.getResponseCode();
                
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "POST", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                 BufferedReader input = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                while ((inputString = input.readLine()) != null) 
                    System.out.println(inputString);
                input.close();   
                System.out.println("----------------------------------------------------------");
                
            } catch (IOException e) {}
            
        } catch (MalformedURLException e) {System.out.println(errMsg);}
            
        
    }
    
    
    private void getCities() 
    {
        String URL_errMsg = "Greska pri formiranju URL";
        
        
        String URLAddress = "http://localhost:8080/Server/store/cities/getcities";
        int responseCode = 0;
        
        try {
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                myHttpConnection.setRequestMethod("GET");
                
                responseCode = myHttpConnection.getResponseCode();
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "GET", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                BufferedReader incoming = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                
                String [] cities = trimIncomingString(incoming.readLine());
                
                incoming.close();   

                System.out.println("Gradovi u formatu: ");
                System.out.println("Naziv|Drzava" + "\n");
                
                for (String city : cities) 
                {     
                    
                    System.out.println(city); 
                }
                                        
		System.out.println("-----------------------------------------------------------");
       
            } catch (IOException e) {e.printStackTrace();}
            
        } catch (MalformedURLException e) {System.out.println(URL_errMsg);}
    }
    
    private void getUsers() 
    {
        String URL_errMsg = "Greska pri formiranju URL";
        
        
        String URLAddress = "http://localhost:8080/Server/store/users/getusers";
        String inputString = null;
        int responseCode = 0;
        
        try {
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                myHttpConnection.setRequestMethod("GET");
                
                responseCode = myHttpConnection.getResponseCode();
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "GET", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                BufferedReader incoming = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                
                String [] users = trimIncomingString(incoming.readLine());
                
                incoming.close();   

                System.out.println("Korisnici u formatu: ");
                System.out.println("KorisnickoIme|Ime|Prezime|Novac" + "\n");
                
                for (String user : users) 
                {     
                    
                    System.out.println(user); 
                }
                
		incoming.close();   
		System.out.println("-----------------------------------------------------------");
       
            } catch (IOException e) {e.printStackTrace();}
            
        } catch (MalformedURLException e) {System.out.println(URL_errMsg);}
    }
    
    private void wireMoneyToUser(String userName, String money) 
    {
        String errMsg = "Greska pri povezivanju";
        
        try {
            String URLAddress = "http://localhost:8080/Server/store/users/wireMoneyToUser/" + userName + "/" + money;
            
            String inputString = null;
            int responseCode = 0;
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                
                myHttpConnection.setRequestMethod("POST");
                myHttpConnection.setDoOutput(true);
               
                
                responseCode = myHttpConnection.getResponseCode();
                
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "POST", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                 BufferedReader input = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                while ((inputString = input.readLine()) != null) 
                    System.out.println(inputString);
                input.close();   
                System.out.println("----------------------------------------------------------");
                
            } catch (IOException e) {}
            
        } catch (MalformedURLException e) {System.out.println(errMsg);}
    }
    
    private void changeUserAddress(String userName, String street, String streetNumber) 
    {
        String errMsg = "Greska pri povezivanju";
        
        try {
            String URLAddress = "http://localhost:8080/Server/store/users/changeUserAddress/" + userName + "/" + street + "/" +streetNumber;
            
            String inputString = null;
            int responseCode = 0;
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                
                myHttpConnection.setRequestMethod("POST");
                myHttpConnection.setDoOutput(true);
               
                
                responseCode = myHttpConnection.getResponseCode();
                
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "POST", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                 BufferedReader input = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                while ((inputString = input.readLine()) != null) 
                    System.out.println(inputString);
                input.close();   
                System.out.println("----------------------------------------------------------");
                
            } catch (IOException e) {}
            
        } catch (MalformedURLException e) {System.out.println(errMsg);}
    }
    
    private void createCategory(String categoryName, String superCategoryName) 
    {
        String errMsg = "Greska pri povezivanju";
        
        try {
            String URLAddress = "http://localhost:8080/Server/store/categories/createCategory/" + categoryName + "/" + superCategoryName;
            
            String inputString = null;
            int responseCode = 0;
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                
                myHttpConnection.setRequestMethod("POST");
                myHttpConnection.setDoOutput(true);
               
                
                responseCode = myHttpConnection.getResponseCode();
                
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "POST", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                 BufferedReader input = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                while ((inputString = input.readLine()) != null) 
                    System.out.println(inputString);
                input.close();   
                System.out.println("----------------------------------------------------------");
                
            } catch (IOException e) {}
            
        } catch (MalformedURLException e) {System.out.println(errMsg);}
    }
    
    
    private void getCategories() 
    {
        String URL_errMsg = "Greska pri formiranju URL";
        
        
        String URLAddress = "http://localhost:8080/Server/store/categories/getCategories";
        String inputString = null;
        int responseCode = 0;
        
        try {
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                myHttpConnection.setRequestMethod("GET");
                
                responseCode = myHttpConnection.getResponseCode();
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "GET", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                BufferedReader incoming = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                
                String [] categories = trimIncomingString(incoming.readLine());
                
                incoming.close();   

                System.out.println("Kategorije: ");
               
                for (String category : categories) 
                {     
                    
                    System.out.println(category); 
                }
		System.out.println("-----------------------------------------------------------");
       
            } catch (IOException e) {e.printStackTrace();}
            
        } catch (MalformedURLException e) {System.out.println(URL_errMsg);}
    }
    
    private void createArticle(String articleName, String articlePrice, String articleDescription, String articleCategory, String owner) 
    {
        String errMsg = "Greska pri povezivanju";
        
        try {
            String URLAddress = "http://localhost:8080/Server/store/articles/createArticle/" 
                    + articleName + "/" + articlePrice + "/" + articleDescription + 
                    "/" + articleCategory + "/" +owner;
            
            String inputString = null;
            int responseCode = 0;
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                
                myHttpConnection.setRequestMethod("POST");
                myHttpConnection.setDoOutput(true);
               
                
                responseCode = myHttpConnection.getResponseCode();
                
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "POST", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                 BufferedReader input = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                while ((inputString = input.readLine()) != null) 
                    System.out.println(inputString);
                input.close();   
                System.out.println("----------------------------------------------------------");
                
            } catch (IOException e) {}
            
        } catch (MalformedURLException e) {System.out.println(errMsg);}
    }
    
    
    private void changeArticlePrice(String articleName, String newPrice) 
    {
        String errMsg = "Greska pri povezivanju";
        
        try {
            String URLAddress = "http://localhost:8080/Server/store/articles/changeArticlePrice/" 
                    + articleName + "/" + newPrice;
            
            String inputString = null;
            int responseCode = 0;
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                
                myHttpConnection.setRequestMethod("POST");
                myHttpConnection.setDoOutput(true);
               
                
                responseCode = myHttpConnection.getResponseCode();
                
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "POST", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                 BufferedReader input = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                while ((inputString = input.readLine()) != null) 
                    System.out.println(inputString);
                input.close();   
                System.out.println("----------------------------------------------------------");
                
            } catch (IOException e) {}
            
        } catch (MalformedURLException e) {System.out.println(errMsg);}
    }
    
    private void setArticleDiscount(String articleName, String discount) 
    {
        String errMsg = "Greska pri povezivanju";
        
        try {
            String URLAddress = "http://localhost:8080/Server/store/articles/setArticleDiscount/" 
                    + articleName + "/" + discount;
            
            String inputString = null;
            int responseCode = 0;
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                
                myHttpConnection.setRequestMethod("POST");
                myHttpConnection.setDoOutput(true);
               
                
                responseCode = myHttpConnection.getResponseCode();
                
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "POST", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                 BufferedReader input = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                while ((inputString = input.readLine()) != null) 
                    System.out.println(inputString);
                input.close();   
                System.out.println("----------------------------------------------------------");
                
            } catch (IOException e) {}
            
        } catch (MalformedURLException e) {System.out.println(errMsg);}
    }
    
    private void getUserArticles(String username) 
    {
        String URL_errMsg = "Greska pri formiranju URL";
        
        
        String URLAddress = "http://localhost:8080/Server/store/articles/getUserArticles/" + username;
        String inputString = null;
        int responseCode = 0;
        
        try {
            URL url = new URL(URLAddress);
            
            try {
                HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
                myHttpConnection.setRequestMethod("GET");
                
                responseCode = myHttpConnection.getResponseCode();
                
                System.out.format("Connecting to %s\nConnection Method: '%s'\nResponse Code is: %d\n", URLAddress, "GET", responseCode);
                
                System.out.println("----------------------[ RESPONSE ]------------------------");
                
                BufferedReader incoming = new BufferedReader(new InputStreamReader(myHttpConnection.getInputStream()));
                
                String [] articles = trimIncomingString(incoming.readLine());
                
                incoming.close();   

                System.out.println("Artikli: ");
               
                for (String article : articles) 
                {     
                    
                    System.out.println(article); 
                }
		System.out.println("-----------------------------------------------------------");
       
            } catch (IOException e) {e.printStackTrace();}
            
        } catch (MalformedURLException e) {System.out.println(URL_errMsg);}
    }
    
}
