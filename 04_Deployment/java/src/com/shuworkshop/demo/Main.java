package com.shuworkshop.demo;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("What operation you want to do?");
		System.out.println("1. Create User");
		System.out.println("2. Delete User");
		System.out.println("3. Update User Information");
		System.out.println("4. Login");
		System.out.println("5. List User");
		
		String options = scanner.nextLine();
		
		IOperation operation = null;
		if (options.equals("1")) {
			operation = new CreateUserOperation();
			operation.askQuestion();
		} else if (options.equals("2")) {
			operation = new DeleteUserOperation();
			operation.askQuestion();
		} else if (options.equals("3")) {
			operation = new UpdateUserOperation();
			operation.askQuestion();
		} else if (options.equals("4")) {
			operation = new LoginOperation();
			operation.askQuestion();
		} else if (options.equals("5")) {
			operation = new ListUserOperation();
			operation.askQuestion();
		} else {
			System.out.println("There is no such option");
		}
		
		
		if (operation != null) {
			operation.doOperation();
		}
		scanner.close();
	}
	
	
	
	
}
