package com.example;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Main {
    static String url = "jdbc:postgresql://localhost:5432/University";
    static String user = "postgres";
    static String password = "FairyTail7";
    public static void main(String[] args) {
        setup();
        control();


    }
    public static Connection connect(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to Database");
            }else{
                System.out.println("Failed to connect to Datebase");
            }
            return connection;
        }
        catch(Exception e){return null;}
        
    }
    
    public static void setup(){
        Connection connection = connect();
        try{      
            Statement statement = connection.createStatement();
            //Create Schema
            statement.executeUpdate("DROP SCHEMA IF  EXISTS \"Assignment3\" CASCADE");
            statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS \"Assignment3\"");
            //Create table
            statement.executeUpdate("create table if not exists \"Assignment3\".students (student_id serial PRIMARY KEY, first_name TEXT NOT NULL,last_name TEXT NOT NULL, email TEXT NOT NULL UNIQUE,enrollment_date DATE);");
            //Load Table
            statement.executeUpdate("INSERT INTO \"Assignment3\".students (first_name, last_name, email, enrollment_date) VALUES\r\n" + //
                                "('John', 'Doe', 'john.doe@example.com', '2023-09-01'),\r\n" + //
                                "('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),\r\n" + //
                                "('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02')");
            System.out.println("Successfully setup to get Students");
        }
        catch(Exception e){System.out.println("Failed to get Setup");}
    }

    public static void control(){
        //Allows for input
        Scanner input = new Scanner(System.in);

        //User Controls
        while(true){
            System.out.println("\n1: Display Students");
            System.out.println("2: Add Student");
            System.out.println("3: Update Student Email");
            System.out.println("4: Delete Student");
            System.out.println("5: Quit");
            String Responce = input.nextLine();

            if(Responce.equals("1")){
                getAllStudents();
            }else if(Responce.equals("2")){
                System.out.println("Enter first name, last name, email, and enrollment date(YYYY-MM-DD) of the new student");
                Responce = input.nextLine();
                String[] splitFour= Responce.split(" ");
                addStudent(splitFour[0],splitFour[1],splitFour[2],splitFour[3]);
            }else if(Responce.equals("3")){
                System.out.println("Enter student_id, than the new email of the student(seperate with space)");
                Responce = input.nextLine();
                String[] splitTwo= Responce.split(" ");
                updateStudentEmail(splitTwo[0],splitTwo[1]);
            }else if(Responce.equals("4")){
                System.out.println("Enter student_id of the student you want to delete");
                Responce = input.nextLine();
                deleteStudent(Responce);

            }else if(Responce.equals("5")){
                System.out.println("Goodbye");
                break;
            }else{
                System.out.println("Need to enter a number between 1-5");
            }

        }
        input.close();

    }

    public static void getAllStudents(){
        Connection connection = connect();
            try{
            Statement statement = connection.createStatement();
            //Get from DB
            statement.executeQuery("Select * from \"Assignment3\".students");
            ResultSet resultSet = statement.getResultSet();
            //Print top 1000 results
            while(resultSet.next()){
                System.out.print(resultSet.getString("student_id")+"\t");
                System.out.print(resultSet.getString("first_name")+"\t");
                System.out.print(resultSet.getString("last_name")+"\t");
                System.out.print(resultSet.getString("email")+"\t");
                System.out.println(resultSet.getString("enrollment_date"));
            }
            Thread.sleep(2000);
        }
        catch(Exception e){
            System.out.println("Failed to get Students");
        }
    }
    
    public static void addStudent(String first_name, String last_name, String email, String enrollment_date){
        Connection connection = connect();
        try{
        Statement statement = connection.createStatement();
        //Get from DB
        statement.executeUpdate("INSERT INTO \"Assignment3\".students (first_name, last_name, email, enrollment_date) VALUES\r\n" + //
                        "('"+first_name+"', '"+last_name+"', '"+email+"', '"+enrollment_date+"');");
    }
    catch(Exception e){
        System.out.println("Failed to get add Student");
    }
    }
    
    public static void updateStudentEmail(String student_id, String new_email){
        Connection connection = connect();
        try{
        Statement statement = connection.createStatement();
        //Get from DB
        statement.executeUpdate("update \"Assignment3\".students set email='"+new_email+"' where student_id="+student_id);
    }
    catch(Exception e){
        System.out.println("Failed to get update Student");
    }
        
    }
    
    public static void deleteStudent(String student_id){
        Connection connection = connect();
        try{
        Statement statement = connection.createStatement();
        //Get from DB
        statement.executeUpdate("delete from \"Assignment3\".students where student_id="+student_id);
    }
    catch(Exception e){
        System.out.println("Failed to get delete Student");
    }
    }
}