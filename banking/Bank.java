package banking;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bank {

    Scanner scn;
    Random rand;
    //ArrayList<Account> accounts = new ArrayList<Account>();
    InsertApp app;
    String fileName;

    Bank(String fileName) {
        scn = new Scanner(System.in);
        rand = new Random();
        app = new InsertApp();
        this.fileName = fileName;
    }

    public void run() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("o. Exit");

        int input = scn.nextInt();

        switch(input) {
            case 1: createAccount();
                this.run();
                break;
            case 2: loginAccount();
                this.run();
                break;
            case 0: System.out.println("Bye!");
                break;
            default:
        }
    }

    public void createAccount() {

        String pin = String.format("%04d",  rand.nextInt(10000));
        StringBuilder actNum = new StringBuilder("400000");
        for(int i = 0; i < 9; i++) {
            actNum.append(rand.nextInt(10));
        }

        actNum.append(checkSum(actNum));
        app.insert(actNum.toString(), pin, fileName);
        //accounts.add(new Account(actNum, pin));

        System.out.println("Your card has been created");
        System.out.println("Your card number: ");
        System.out.println(actNum.toString());
        System.out.println("Your pin number: ");
        System.out.println(pin);
    }

    public void loginAccount() {
        boolean correct = false;
        String[] arr = new String[2];
        System.out.println("Enter your card number: ");
        String actNum = scn.next();
        System.out.println("Enter your PIN: ");
        String pin = scn.next();
        Account act = null;

        arr = app.selectAct(actNum, fileName);
        if (arr[1].equals(pin)) {
            System.out.println("You have successfully logged in!");
            act = new Account(arr[0], arr[1], fileName);
            act.run();
        } else {
            System.out.println("Wrong card number or PIN");
        }

        /*for (Account account : accounts) {
            if (account.getActNumber().equals(actNum)) {
                if (account.getPIN().equals(pin)) {
                    correct = true;
                    act = account;
                }
            }
        }

        if(correct) {
            System.out.println("You have successfully logged in!");
            act.run();
        } else {
            System.out.println("Wrong card number or PIN");
        } */
    }

    public int checkSum(StringBuilder str) {
        int sum = 0;
        int num = 0;
        int checksum = 0;
        for(int i = 0; i < str.length(); i++) {
            num = Integer.parseInt(str.substring(i, i +1));
            if(i % 2 == 0) {
                num *= 2;
                num = (num > 9) ? num - 9 : num;
                sum += num;
            } else {
                sum += num;
            }

        }

        while(sum % 10 != 0) {
            checksum++;
            sum ++;
        }
        return checksum;
    }
}

