package main;

import accountmanagement.AccountManagement;
import accountmanagement.UserInformation;

public class Main {
    public static void main(String[] args) throws Exception {
        AccountManagement.ConnectToDatabase();
        System.out.println("Counter: " + AccountManagement.size());

        try {
//            if (AccountManagement.CheckLogin("acc01", "p01"))
//                System.out.println("Welcome!!!");
            if (AccountManagement.AddAccount("acc06", "p04"
                    , "A", "Le", "fff@gmail.com", "12321", "Ha noi"))
                System.out.println("Accept!!!");;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + AccountManagement.size());


        System.out.println("Done");
    }
}
