package application;

import java.util.Scanner;

public class SellerMenu {

	Scanner sc;

	public SellerMenu(Scanner sc) {
		super();
		this.sc = sc;
	}

	public void start() {
		int option = 0;
		while (option != 6) {
			System.out.println("--- Seller Menu ---");
			System.out.println("1 - Insert");
			System.out.println("2 - FindById");
			System.out.println("3 - FindAll");
			System.out.println("4 - Update");
			System.out.println("5 - Delete");
			System.out.println("6 - Return to Main Menu");
			System.out.println("Choose an option:");
			option = sc.nextInt();

			switch (option) {
			case 1:
				System.out.println("Insert em andamento");
				break;
			case 2:
				System.out.println("FindById em andamento");
				break;
			case 3:
				System.out.println("FindAll em andamento");
				break;
			case 4:
				System.out.println("Update em andamento");
				break;
			case 5:
				System.out.println("Deleção em andamento");
				break;
			case 6:
				System.out.println("Returning to Main Menu...");
				break;
			default:
				System.out.println("Invalid option");
			}
		}
	}
	
	private void waitForInput() {
		System.out.println("Press Enter to continue...");
	    sc.nextLine();
	}

}
