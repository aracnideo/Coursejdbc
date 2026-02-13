package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.entities.Department;

public class DepartmentRepository {

	private Connection conn;

	public DepartmentRepository(Connection conn) {
		this.conn = conn;
	}

	public void insert(Department department) {
		PreparedStatement st = null;
		String sql = "INSERT INTO department (Name) VALUES (?)";
		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, department.getName());
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				var rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
				}

				DB.closeResultSet(rs);
			}
			if (rowsAffected == 0) {
				throw new SQLException("No rows affected");
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			DB.closeStatement(st);

		}
	}

	public void update(Department department) {
		PreparedStatement st = null;
		String sql = "UPDATE department SET name = ? WHERE ID = ?";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, department.getName());
			st.setInt(2, department.getId());

			int rowsAffected = st.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException("No rows affected. Id may not exist.");
			}
		} catch (SQLException e) {
			throw new DbException("Error updating department: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	public List<Department> findAll() {
		Statement st = null;
		ResultSet rs = null;
		List<Department> list = new ArrayList<>();
		String sql = "SELECT * FROM department";

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Department department = new Department(rs.getInt("Id"), rs.getString("Name"));
				list.add(department);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException("Error fetching all departments: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	public Department findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM department WHERE Id = ?";

		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			try {
				rs = st.executeQuery();
				if (rs.next()) {
					return new Department(rs.getInt("Id"), rs.getString("Name"));
				}
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}

		} catch (SQLException e) {
			throw new DbException("Error fetching department by id: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}
}
