package application;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import db.DB;
import db.DbException;
import model.entities.Department;
import model.entities.Seller;
import repository.DepartmentRepository;
import repository.SellerRepository;
import service.DepartmentService;
import service.SellerService;

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
				System.out.println("Option selected: INSERT SELLER");
				insert();
				break;
			case 2:
				System.out.println("Option selected: FINDBYID SELLER");
				findById();
				break;
			case 3:
				System.out.println("Option selected: FINDALL SELLER");
				findAll();
				break;
			case 4:
				System.out.println("Option selected: UPDATE SELLER");
				break;
			case 5:
				System.out.println("Option selected: DELETE SELLER");
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

	private void insert() {
		// Insert Seller
		Connection conn = null;
		try {
			conn = DB.getConnection();
			System.out.println("Enter the name of the new seller: ");
			sc.nextLine();
			String name = sc.nextLine();
			System.out.println("Enter the email of the new seller: ");
			String email = sc.nextLine();

			LocalDate birthDate = null;
			boolean validBirthDate = false;
			while (!validBirthDate) {
				System.out.println("Enter the birthdate of the new seller: (Format: yyyy-MM-dd)");
				String birthDayString = sc.nextLine();
				try {
					birthDate = LocalDate.parse(birthDayString);
					validBirthDate = true;
				} catch (Exception e) {
					System.out.println("Invalid date format. Use yyyy-MM-dd.");
				}
			}

			System.out.println("Enter the base salary of the new seller: ");
			double baseSalary = sc.nextDouble();
			sc.nextLine();
			Department department = null;

			DepartmentRepository departmentRepository = new DepartmentRepository(conn);
			DepartmentService departmentService = new DepartmentService(departmentRepository);

			while (department == null) {
				System.out.println("Enter the department ID of the new seller: ");
				int departmentId = sc.nextInt();
				sc.nextLine();
				department = departmentService.findById(departmentId);
				if (department == null) {
					System.out.println("Department ID not found. Please try again.");
				}
			}

			Seller seller = new Seller(name, email, birthDate, baseSalary, department);

			SellerRepository repository = new SellerRepository(conn);
			SellerService service = new SellerService(repository);

			service.insert(seller);
			System.out.println("Inserted! New Id: " + seller.getId());
			waitForInput();
		} catch (DbException e) {
			System.out.println("Database error: " + e.getMessage());
		} finally {
			DB.closeConnection();
		}
	}

	private void findAll() {
		// FindAll Seller
		Connection conn = null;
		try {
			conn = DB.getConnection();
			SellerRepository repository = new SellerRepository(conn);
			SellerService service = new SellerService(repository);

			List<Seller> sellers = service.findAll();

			System.out.println("~~ Sellers ~~");
			for (Seller seller : sellers) {
				System.out.println(seller.toString());
			}
			sc.nextLine();
			waitForInput();
		} catch (DbException e) {
			System.out.println("Database error: " + e.getMessage());
		} finally {
			DB.closeConnection();
		}
	}

	public void findById() {
		// FindById Seller
		Connection conn = null;
		System.out.println("Enter Seller Id: ");
		int id = sc.nextInt();
		sc.nextLine();
		try {
			conn = DB.getConnection();
			SellerRepository repository = new SellerRepository(conn);
			SellerService service = new SellerService(repository);

			Seller seller = service.findById(id);

			if (seller != null) {
				System.out.println("~~ Seller Found ~~");
				System.out.println(seller.toString());
			} else {
				System.out.println("Seller not found.");
			}
			waitForInput();
		} catch (DbException e) {
			System.out.println("Database error: " + e.getMessage());
		} finally {
			DB.closeConnection();
		}
	}

	public void delete() {

	}
}
