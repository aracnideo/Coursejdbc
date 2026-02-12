package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;
import db.DbIntegrityException;

public class Program {

	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;

		// Opening connections and Select
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from department");

			System.out.println();
			System.out.println("--- SELECT * FROM DEPARTMENT ---");
			while (rs.next()) {
				System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
			}
			System.out.println("--- --- --- --- --- --- --- ---");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Insert
		try {

			ps = conn.prepareStatement(
					"INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, "Carlos Magnetico");
			ps.setString(2, "magnetico@outlook.com");
			ps.setDate(3, new java.sql.Date(sdf.parse("16/06/1994").getTime()));
			ps.setDouble(4, 2600.0);
			ps.setInt(5, 4);

			System.out.println();
			System.out.println("--- INSERT INTO SELLER ---");
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				rs = ps.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Added Seller with ID: " + id);

				}
			} else {
				System.out.println("No rows affected.");
			}
			System.out.println("--- --- --- --- --- --- --- ---");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Update
		try {
			System.out.println();
			System.out.println("--- UPDATE seller SET BaseSalary = +200 WHERE (DepartmentId = 2) ---");
			ps = conn.prepareStatement("UPDATE seller SET BaseSalary = BaseSalary +? WHERE (DepartmentId = ?)");
			ps.setDouble(1, 200);
			ps.setInt(2, 2);
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Update done.");
			} else {
				System.out.println("No rows affected.");
			}
			System.out.println("--- --- --- --- --- --- --- ---");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Delete
		try {
			System.out.println();
			System.out.println("--- DELETE FROM seller WHERE Name = Carlos Magnetico");
			ps = conn.prepareStatement("DELETE FROM seller WHERE Name = ?");
			ps.setString(1, "Carlos Magnetico");
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Delete done.");
			} else {
				System.out.println("No rows affected.");
			}
			System.out.println("--- --- --- --- --- --- --- ---");

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}

		//Delete - Exception test
//		try {
//			System.out.println();
//			System.out.println("--- DELETE FROM department WHERE Id = 2");
//			ps = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
//			ps.setInt(1, 2);
//			int rowsAffected = ps.executeUpdate();
//			if (rowsAffected > 0) {
//				System.out.println("Delete done.");
//			} else {
//				System.out.println("No rows affected.");
//			}
//			System.out.println("--- --- --- --- --- --- --- ---");
//
//		} catch (SQLException e) {
//			throw new DbIntegrityException(e.getMessage());
//		}
		
		// Closing connections
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

	}

}
