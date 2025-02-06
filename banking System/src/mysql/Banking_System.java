package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Banking_System {

	public static void main(String[] args) {
	Scanner sc=new Scanner(System.in);
	bankaccountt bank= new bankaccountt("shreya", "012");
	bank.ShowMenu();
	}
}




class bankaccountt{
	int balance;
	int previousTranscation;
	String customer_name;
	String customer_id;
	
	bankaccountt(String cname, String cid)
	{
		this.customer_name=cname;
		this.customer_id=cid;
	}
	
	void deposit(int amt) {
		if(amt!=0) {
			balance=balance+amt;
			previousTranscation=amt;
			
		}
	}
	
	void withdraw(int amt)
	{
		if(amt!=0) {
			balance=balance-amt;
			previousTranscation= -amt;
			}
	}
	void getpreviousTranscation()
	{
		if(previousTranscation>0) {
			System.out.println("deposit :"+ previousTranscation);
		}
		else if(previousTranscation<0) {
			System.out.println("withdraw :"+ Math.abs(previousTranscation));
		}
		else {
			System.out.println("no transcation is occured");
		}
	}
	
	void ShowMenu() 
	{
		char option='\0';//null value
		Scanner sc=new Scanner(System.in);
		System.out.println("welcome "+customer_name);
		System.out.println("Your ID is :"+customer_id);
		System.out.println();
		System.out.println("A. CHECK BALANCE");
		System.out.println("B. DEPOSIT");
		System.out.println("C. WITHDRAW");
		System.out.println("D. PREVIOUS TRANSCATION");
		System.out.println("E. EXIT");
		
		do {
			System.out.println("----------------------------");
			System.out.println("enter the required option ");
			System.out.println("----------------------------");
			option=sc.next().charAt(0);
			//Character.toUpperCase(option);
			
			switch(option)
			{
			case 'A':
			    System.out.println("----------------------------");
				System.out.println("Balance is :"+balance);
				
				System.out.println("----------------------------");
				System.out.println();
				break;
			case 'B':
				System.out.println("----------------------------");
				System.out.println("enter the amount to deposit");
				System.out.println("----------------------------");
				int amt=sc.nextInt();
				deposit(amt);
				System.out.println();
				break;
			case 'C':
				System.out.println("----------------------------");
				System.out.println("enter the amount to be withdrawn");
				System.out.println("----------------------------");
				int amt1=sc.nextInt();
				withdraw(amt1);
				System.out.println();
				break;
			case 'D':
				System.out.println("----------------------------");
			    getpreviousTranscation();
			    System.out.println("----------------------------");
			    System.out.println();
			    break;
			case 'E':
				System.out.println("----------------------------");
				break;
			default:
				System.out.println("Invalid option!!! Please try again");
				break;
			}
			
		}while(option !='E');
	}
}

