package model.dao.implementation;

import db.DBConnection;
import db.DBException;
import model.dao.DepartmentDao;
import model.entity.Department;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    @Override
    public void insert(Department d) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                "INSERT INTO department(Name) VALUES(?)"
        )) {
            ps.setString(1, d.getName());
            int rows = ps.executeUpdate();
            if(rows == 0) {
                throw new DBException("Unexpected error! Insert Department");
            }
        } catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void update(Department d) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                "UPDATE department SET Name = ? WHERE Id = ?"
        )){
            ps.setString(1, d.getName());
            ps.setInt(2, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                "DELETE department FROM department WHERE Id = ?"
        )){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public Department findById (Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = DBConnection.getConnection().prepareStatement(
				"SELECT * FROM department WHERE Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Department obj = new Department();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				return obj;
			}	
		} catch (SQLException e) {
			e.getMessage();
		}
        return null;
    }

    @Override
    public List<Department> findAll() {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                "SELECT * FROM department"
        )){
            try (ResultSet rs = ps.executeQuery()){
                List<Department> list = new ArrayList<>();

                while (rs.next()) {
                    Department obj = new Department();
                    obj.setId(rs.getInt("Id"));
                    obj.setName(rs.getString("Name"));
                    if(!list.contains(obj)) 
                        list.add(obj); 
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }
}
