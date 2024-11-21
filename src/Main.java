import com.bank.dao.AccountDAO;
import com.bank.models.Account;
import com.bank.service.BankService;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        BankService bankService=new BankService();
        Scanner sc=new Scanner(System.in);
        int choice;
        do{
            System.out.println("\nMENU");
            System.out.println("1.Create Account");
            System.out.println("2.Deposit Money");
            System.out.println("3.Withdrawal Money");
            System.out.println("4.Check Account Balance");
            System.out.println("5.Calculate Interest");
            System.out.println("6.View Account Summaries and reports");
            System.out.println("7.Exit");
            System.out.println("Enter the Choice");

            choice= sc.nextInt();
            sc.nextLine();
            switch (choice) {
                //  Create Account

                case 1:
                    System.out.println("Enter Name");
                    String name = sc.nextLine();
                    System.out.println("Enter Address");
                    String address = sc.nextLine();
                    System.out.println("Enter 4 digits PIN");
                    int pin = sc.nextInt();
                    String enterPin = String.valueOf(pin);

                    if(enterPin.length() == 4){
                        System.out.println("Initial Deposit");
                        double balance = sc.nextDouble();
                        if (balance>=1000){
                            System.out.println("Account Type Savings/Current");
                            sc.nextLine();
                            String accountType = sc.nextLine();
                            if(("Savings".equals(accountType)) || ("Current".equals(accountType))){
                                bankService.addAccount(0, name, address, pin, balance, accountType);

                            }else {
                                System.out.println("Account type must be Savings/Current");
                            }

                        }else {
                            System.out.println("Deposit amount will be more than or equal to 1000");
                        }

                    }else {
                        System.out.println("Enter valid 4 digit pin");
                    }

                    break;

                //   Deposit Money
                case 2:
                    System.out.println("Enter Account Number");
                    int depositAccountNumber = sc.nextInt();
                    System.out.println("Enter 4 digit pin");
                    int depositPin = sc.nextInt();
                    String stringPin = String.valueOf(depositPin);

                    if (stringPin.length() == 4) {
                        System.out.println("Enter Deposit amount");
                        double depositAmount = sc.nextDouble();
                        sc.nextLine();
                        bankService.depositMoney(depositAccountNumber, depositPin, depositAmount);

                    } else {
                        System.out.println("Enter valid 4 digit pin");
                    }

                    break;

                // Withdrawal Money
                case 3:
                    System.out.println("Enter Account Number");
                    int withdrawalAccountNumber = sc.nextInt();
                    System.out.println("Enter 4 digit pin");
                    int withdrawalPin = sc.nextInt();
                    String withdrawalPinstr = String.valueOf(withdrawalPin);
                    double withdrawalAmount = 0.0;
                    if (withdrawalPinstr.length() == 4) {
                        System.out.println("Enter Withdrawal amount");
                        withdrawalAmount = sc.nextInt();
                        bankService.withdrawalMoney(withdrawalAccountNumber, withdrawalPin, withdrawalAmount);

                    } else {
                        System.out.println("Enter valid 4 digit pin");
                    }

                    break;

                // Check Bank Balance
                case 4:
                    System.out.println("Enter Account Number");
                    int checkAccountNumber = sc.nextInt();
                    System.out.println("Enter 4 digit pin");
                    int checkPin = sc.nextInt();
                    String balancePin = String.valueOf(checkPin);

                    if (balancePin.length() == 4) {
                        bankService.balanceAmount(checkAccountNumber, checkPin);
                    }else {
                        System.out.println("Enter valid 4 digit pin");

                    }
                    break;

                //Calculate Interest
                case 5:
                    System.out.println("Enter Account Number");
                    int interestAccountNumber = sc.nextInt();
                    System.out.println("Enter 4 digit pin");
                    int interestPin = sc.nextInt();
                    String stringInterestPin = String.valueOf(interestPin);

                    if (stringInterestPin.length() == 4) {
                        bankService.calculateInterest(interestAccountNumber, interestPin);

                    }else {
                        System.out.println("Enter valid 4 digit pin");

                    }
                    break;


                // View account summaries
                case 6:
                    System.out.println("Choose Account Summaries");
                    System.out.println("1.Total accounts by type");
                    System.out.println("2.Total deposits and withdrawals");
                    int reportChoice=sc.nextInt();

                    if(reportChoice==1){
                        bankService.viewAccountSummary();
                    }else if(reportChoice==2){
                        bankService.viewTransactionSummary();
                    }else{
                        System.out.println("Enter valid choice");
                    }
                    break;
                // Exit Code
                case 7:
                    System.out.println("Exiting Code..");
                    System.exit(1);
                    break;

                default:
                    System.out.println("Enter a Valid number");

            }
        }while (choice!=6);
    }
}