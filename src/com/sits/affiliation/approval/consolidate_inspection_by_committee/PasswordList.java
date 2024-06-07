package com.sits.affiliation.approval.consolidate_inspection_by_committee;
import java.util.ArrayList;
import java.util.List;
import com.sits.general.*;

public class PasswordList {
    public static void main(String[] args) {
        // List to store passwords
        List<String> passwordList = new ArrayList<>();

        // Array containing passwords
        String[] passwords = {
                "NusgE79;)*"
        };

        // Add passwords to the list using a foreach loop
        for (String password : passwords) {
            passwordList.add(password);
        }

        // Print the password list
        System.out.println("Password List:");
        for (String password : passwordList) {
        	String decodePassword=General.decPassword(password);
        	System.out.println("decodePassword:"+decodePassword);
            System.out.println(password);
        }
    }
}
