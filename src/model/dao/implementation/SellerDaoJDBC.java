package model.dao.implementation;

import db.DBConnection;
import db.DBException;
import model.dao.SellerDao;
import model.entity.Department;
import model.entity.Seller;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC  implements SellerDao {

    @Override
    public void insert(Seller s) {

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                "INSERT INTO seller(Name, Email, BirthDate, BaseSalary,DepartmentId) "
                +"VALUES(?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS
        )) {
            ps.setString(1,s.getName());
            ps.setString(2,s.getEmail());
            ps.setDate(3,new java.sql.Date(s.getBirthDate().getTime()));
            ps.setDouble(4, s.getBaseSalary());
            ps.setInt(5, s.getDepartment().getId());

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    int id = rs.getInt(1);
                    s.setId(id);
                }
                rs.close();
            } else {
                throw new DBException("Unexpected error! Insert User");
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void update(Seller s) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?  "
                +"WHERE Id = ?"
        )) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
            ps.setDouble(4, s.getBaseSalary());
            ps.setInt(5, s.getDepartment().getId());
            ps.setInt(6, s.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                "DELETE FROM seller WHERE Id = ?"
        )) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if(rows == 0) {
                throw new DBException("Non-existent Id. Try again!");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public Seller findById(Integer id) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                "SELECT seller.*, department.Name as DepName FROM seller" +
                        " INNER JOIN department ON seller.DepartmentId = department.Id" +
                        " WHERE  seller.Id = ?;"
        )) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Department dep = instantiateDepartment(rs); 
                    Seller seller = instantiateSeller(rs, dep);
                    return  seller;
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Seller> findAll() {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                "SELECT seller.*, department.Name as DepName FROM seller " +
                     "INNER JOIN department ON seller.departmentId = department.id "
                     +"ORDER BY Name"
        )) {
            try (ResultSet rs = ps.executeQuery()){

                List<Seller> list  = new ArrayList<>();
                Map<Integer, Department> map = new HashMap<>();

                while (rs.next()){
                    Department dep = map.get(rs.getInt("DepartmentId"));
                    
                    if(dep == null) {
                        dep = instantiateDepartment(rs);
                        map.put(rs.getInt("DepartmentId"), dep);

                    }
                    Seller obj = instantiateSeller(rs, dep);
                    list.add(obj);
                }
                return list;
            }   
        } catch (SQLException e) {
            throw new DBException("Error " + e.getMessage());
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        try(PreparedStatement ps = DBConnection.getConnection().prepareStatement(
            "SELECT seller.*, department.Name as DepName FROM seller "
            +"INNER JOIN department ON seller.departmentId = department.id "
            +"WHERE departmentId = ? "
            +"ORDER BY Name")) {
            ps.setInt(1, department.getId());

            try(ResultSet rs = ps.executeQuery()) {

                List<Seller> list = new ArrayList<>();
                Map<Integer, Department> map = new HashMap<>();

                while (rs.next()) {
                    Department dep = map.get(rs.getInt("DepartmentId"));
                    
                    if(dep == null) {
                        dep = instantiateDepartment(rs);
                        map.put(rs.getInt("DepartmentId"), dep);


                    }
                    Seller obj = instantiateSeller(rs, dep);
                    list.add(obj);
                }
                return list;
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    } 

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department obj = new Department();
        obj.setId(rs.getInt("DepartmentId"));
        obj.setName(rs.getString("DepName"));
        return obj;
    }

}
