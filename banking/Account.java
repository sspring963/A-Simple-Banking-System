package banking;

import java.util.Scanner;

public class Account {
    private String actNumber;
    private final String PIN;
    private int balance = 0;
    private InsertApp app;
    private String fileName;
    Scanner scn;

    Account(String actNum, String pin, String fileName) {
        actNumber = new String(actNum.toString());
        PIN = pin;
        this.fileName = fileName;
        app = new InsertApp();
        balance = app.selectBalance(actNumber, fileName);
    }

    public void run() {
        scn = new Scanner(System.in);
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("o. Exit");

        int input = scn.nextInt();

        switch(input) {
            case 1: System.out.println(this.getBalance());
                this.run();
                break;
            case 2: addIncome();
                this.run();
                break;
            case 3: doTransfer();
                this.run();
                break;
            case 4: closeAct();
                break;
            case 5: System.out.println("You have successfully logged out!");
                break;
            case 0: System.out.println("Bye!");
                System.exit(0);
            default:
        }
    }

    public String getActNumber() { return actNumber; }

    public String getPIN() { return PIN; }

    public int getBalance() { return balance; }

    public void addIncome() {
        int addInc = 0;
        System.out.println("Enter income: ");
        if(scn.hasNext()) {
            addInc = scn.nextInt();
        }
        app.updateBalance(fileName, actNumber, addInc);
        System.out.println("Income was added!");
    }

    public void doTransfer() {
        System.out.println("Transfer");
        String rCard = " ";
        String[] arr = new String[2];
        int amt = 0;
        if(scn.hasNext()) {
            rCard = scn.nextLine();
        }

        arr = app.selectAct(rCard, fileName);

        if (arr[0].equals(rCard)) {
            System.out.println("Enter how much money you want to transfer: ");

            amt = scn.nextInt();
            if(amt > balance) {
                System.out.println("Not enough money!");
            } else {
                app.transfer(fileName, actNumber, rCard, amt);
                System.out.println("Success!");
            }
        } else {
            System.out.println("Such a card does not exist.");
        }
    }

    public void closeAct() {
        System.out.println("The Account has been closed.");
        app.delete(fileName, actNumber);
    }
}
