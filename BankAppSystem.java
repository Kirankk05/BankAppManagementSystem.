package com.Bank.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

interface BankOperations{
	void createAccount(String name,double initialBalance);
	void deposit(String accountNumber,double amount);
	void withDraw(String accountNumber,double amount);
	void transfer(String fromAcc,String toAcc, double amount );
	void checBalance(String accountNumber);
}

class InsufficientBalanceException extends Exception
{
	@Override
	public String getMessage() {
		return "Insufficient Balance!!";
	}
}
class BankAccount{
	private String accountNumber;
	private String accountHolder;
	double balance;
	public BankAccount(String accountNumber, String accountHolder, double initialBalance) {	
		this.accountNumber = accountNumber;
		this.accountHolder = accountHolder;
		this.balance = initialBalance;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public String getAccountHolder() {
		return accountHolder;
	}
	
	public double getBalance() {
		return balance;
	}
	public void deposit(double amount) {
		balance = balance+amount;
		
	}
	public void withDraw(double amount)throws InsufficientBalanceException{
		if (balance>=amount) {
			balance = balance-amount;
		}
		else {
			InsufficientBalanceException e = new InsufficientBalanceException();
			throw e;
		}
	}
}
class Bank implements BankOperations{
	 private Map<String ,BankAccount> accounts = new HashMap<String ,BankAccount>();
	 
	 private Random rdm = new Random();
	 
	 private String generateAccountNo() {
		 return "AC"+ (1000+rdm.nextInt(90000));
	 }
	 @Override
	public void createAccount(String name, double initialBalance) {
		 String accNo = generateAccountNo();
		 BankAccount acc = new BankAccount(accNo,name,initialBalance);
		 accounts.put(accNo, acc);
		 System.out.println("Account is created: "+accNo);
		 
		
	}
	@Override
	public void deposit(String accountNumber, double amount) {
		BankAccount acc = accounts.get(accountNumber);
		if(acc!=null) {
			acc.deposit(amount);
			System.out.println("Deposit Successful. Your new Balance is:"+acc.getBalance());
		}
		else {
			System.out.println("Account not found!");
		}
			
	}
	@Override
	public void withDraw(String accountNumber, double amount) {
		
		BankAccount acc = accounts.get(accountNumber);
		if(acc!=null) {
			try {
				
				acc.withDraw(amount);
				System.out.println("Withdrawal Successful. Remaining balance "+acc.getBalance());
			}
			catch(InsufficientBalanceException e) {
				System.out.println(e.getMessage());
				
			}
		}
		else {
                   System.out.println("Account not Found!!");			
		}
		
	}
	@Override
	public void transfer(String fromAcc, String toAcc, double amount) {
		
		BankAccount from = accounts.get(fromAcc);
		BankAccount to = accounts.get(toAcc);
		
		if(from!= null && to!=null) {
			try {
				from.withDraw(amount);
				to.deposit(amount);
				System.out.println("Transfer successful..");
			}
			catch(InsufficientBalanceException e) {
				System.out.println(e.getMessage());
				System.out.println("Transfer failed!!");
			}
		}
		else {
			System.out.println("Invalid Account Details");
		}
		
	}
	
	@Override
	public void checBalance(String accountNumber) {
		
		BankAccount acc = accounts.get(accountNumber);
		if(acc!=null) {
			System.out.println("Balance: "+acc.getBalance());
		}
		else {
			System.out.println("Invalid account number");
		}
		
	}


}
public class BankAppSystem {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Bank bank = new Bank();
		boolean exit = false;
		
		while(!exit) {
			System.out.println("--------Bank Account Management--------");
			System.out.println("1. Create Account");
			System.out.println("2. Deposit");
			System.out.println("3. Withdraw");
			System.out.println("4. Transfer");
			System.out.println("5. Check Balance");
			System.out.println("6. Exit");
			System.out.println("Choose an option");
			int choice =  sc.nextInt();
			sc.nextLine();
			
			String accNo,toAcc;
			double amount;
			
			switch(choice) {
			
			case 1: 
				System.out.print("Enter a name: ");
				String name = sc.nextLine();
				System.out.print("Enter a initial Balance: ");
				amount = sc.nextDouble();
				bank.createAccount(name, amount);
				System.out.println("Account created Successfully");
				break;
			case 2:
				System.out.print("Enter Account Number: ");
				accNo = sc.nextLine();
				System.out.print("Enter amount to be deposited: ");
				amount = sc.nextDouble();
				bank.deposit(accNo, amount);
				break;
			case 3:
				System.out.print("Enter Account Number: ");
				accNo = sc.nextLine();
				System.out.print("Enter a Amount to withdraw: ");
				amount = sc.nextDouble();
				bank.withDraw(accNo, amount);
				break;
			case 4:
				System.out.print("Enter a sender Account Number: ");
				accNo = sc.nextLine();
				System.out.print("Enter a receiver Account Number: ");
				toAcc = sc.nextLine();
				System.out.print("Enter amount: ");
				amount = sc.nextDouble();
				bank.transfer(accNo, toAcc, amount);
				break;
			case 5:
				System.out.print("Enter Account Number: ");
				accNo = sc.nextLine();
				bank.checBalance(accNo);
				break;
			case 6:
				exit = true;
				System.out.println("Thank you for Using the bank system: ");
				break;
			default:
				System.out.println("Invalid choice...");
			}
		}
		sc.close();
	}

}
