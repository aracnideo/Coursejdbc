package application;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import db.DB;
import db.DbException;
import model.entities.Department;
import repository.DepartmentRepository;
import service.DepartmentService;
import util.InputUtils;

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
			option = InputUtils.readInt(sc, "Choose an option: ");

			switch (option) {
			case 1:
				System.out.println("Option selected: INSERT DEPARTMENT");
				insert();
				break;
			case 2:
				System.out.println("Option selected: FINDBYID DEPARTMENT");
				findById();
				break;
			case 3:
				System.out.println("Option selected: FINDALL DEPARTMENT");
				findAll();
				break;
			case 4:
				System.out.println("Option selected: UPDATE DEPARTMENT");
				update();
				break;
			case 5:
				System.out.println("Option selected: DELETE DEPARTMENT");
				delete();
				break;
			case 6:
				System.out.println("Returning to Main Menu...");
				System.out.println();
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
		String newName = InputUtils.readNonEmptyString(sc, "Enter the name of the new department: ");

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
		int id = InputUtils.readInt(sc, "Enter deparment Id: ");
		String newName = InputUtils.readNonEmptyString(sc, "Enter new name for the department: ");

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
		// FindAll Department
		Connection conn = null;
		try {
			conn = DB.getConnection();
			DepartmentRepository repository = new DepartmentRepository(conn);
			DepartmentService service = new DepartmentService(repository);

			List<Department> departments = service.findAll();

			System.out.println("~~ Departments ~~");
			for (Department department : departments) {
				System.out.println(department.toString());
			}
			System.out.println();
			waitForInput();
		} catch (DbException e) {
			System.out.println("Database error: " + e.getMessage());
		} finally {
			DB.closeConnection();
		}
	}

	public void findById() {
		// FindById Department
		Connection conn = null;
		int id = InputUtils.readInt(sc, "Enter deparment Id: ");
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

	public void delete() {
		// Delete Department
		Connection conn = null;
		int id = InputUtils.readInt(sc, "Enter deparment Id: ");

		try {
			conn = DB.getConnection();
			DepartmentRepository departmentRepository = new DepartmentRepository(conn);
			DepartmentService departmentService = new DepartmentService(departmentRepository);
//			departmentService.delete(id);

		} catch (DbException e) {
			System.out.println("Database error: " + e.getMessage());
		} finally {
			DB.closeConnection();
		}
	}

}
