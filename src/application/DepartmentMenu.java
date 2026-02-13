package application;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import db.DB;
import db.DbException;
import model.entities.Department;
import repository.DepartmentRepository;
import service.DepartmentService;

public class DepartmentMenu {

	Scanner sc;

	public DepartmentMenu(Scanner sc) {
		super();
		this.sc = sc;
	}

	public void start() {
		int option = 0;
		while (option != 6) {
			System.out.println("--- Department Menu ---");
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
				insert();
				break;
			case 2:
				findById();
				break;
			case 3:
				findAll();
				break;
			case 4:
				update();
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

	private void insert() {
		// Insert Department
		System.out.println("Enter the name of the new Department: ");
		sc.nextLine();
		String newName = sc.nextLine();

		Connection conn = null;
		try {
			conn = DB.getConnection();
			DepartmentRepository departmentRepository = new DepartmentRepository(conn);
			DepartmentService departmentService = new DepartmentService(departmentRepository);

			Department department = new Department(null, newName);
			departmentService.insert(department);
			System.out.println("Inserted! New Id: " + department.getId());
			waitForInput();
		} catch (DbException e) {
			System.out.println("Database error: " + e.getMessage());
		} finally {
			DB.closeConnection();
		}
	}

	private void update() {
		// Update Department
		System.out.println("Enter Deparment Id: ");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter new name: ");
		String newName = sc.nextLine();

		Connection conn = null;
		try {
			conn = DB.getConnection();
			DepartmentRepository departmentRepository = new DepartmentRepository(conn);
			DepartmentService departmentService = new DepartmentService(departmentRepository);

			Department department = new Department(id, newName);
			departmentService.update(department);
			System.out.println("Updated completed");
			waitForInput();
		} catch (DbException e) {
			System.out.println("Database error: " + e.getMessage());
		} finally {
			DB.closeConnection();
		}
	}

	private void findAll() {
		// ReadAll Department
		Connection conn = null;
		try {
			conn = DB.getConnection();
			DepartmentRepository departmentRepository = new DepartmentRepository(conn);
			DepartmentService departmentService = new DepartmentService(departmentRepository);

			List<Department> departments = departmentService.findAll();

			System.out.println("~~ Departments ~~");
			for (Department department : departments) {
				System.out.println(department.toString());
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
		Connection conn = null;
		System.out.println("Enter Deparment Id: ");
		int id = sc.nextInt();
		sc.nextLine();
		try {
			conn = DB.getConnection();
			DepartmentRepository departmentRepository = new DepartmentRepository(conn);
			DepartmentService departmentService = new DepartmentService(departmentRepository);

			Department department = departmentService.findById(id);

			if (department != null) {
				System.out.println("~~ Department Found ~~");
				System.out.println(department.toString());
			} else {
				System.out.println("Department not found.");
			}
			waitForInput();
		} catch (DbException e) {
			System.out.println("Database error: " + e.getMessage());
		} finally {
			DB.closeConnection();
		}
	}

}
