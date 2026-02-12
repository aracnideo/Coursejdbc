package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;

		// Select
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from department");

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
					"INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, "Carlos Magnetico");
			ps.setString(2, "magnetico@outlook.com");
			ps.setDate(3, new java.sql.Date(sdf.parse("16/06/1994").getTime()));
			ps.setDouble(4, 2600.0);
			ps.setInt(5, 4);

			System.out.println("--- INSERT INTO SELLER ---");
			int rowsAffected = ps.executeUpdate();
			System.out.println("Done. Rows affected: " + rowsAffected);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

	}

}
