package model.dao.impl;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conncetion) {
        this.conn = conncetion;
    }

    @Override
    public void insert(Department dp) {
        String sql = "INSERT INTO department (Name) VALUES (?)";

        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dp.getDepartament());
            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    dp.setId(id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbException("Unexpected error! No rows affected!");
            }

        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeConnection();
        }
    }

    @Override
    public void update(Department dp) {
        String sql = "UPDATE department SET Name = ? WHERE Id = ?";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, dp.getDepartament());
            ps.setInt(2, dp.getId());

            ps.executeUpdate();

        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeConnection();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM department WHERE Id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeConnection();
        }
    }

    @Override
    public Department findById(Integer id) {
        String sql = "SELECT * FROM depertment WHERE Id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Department department = null;

        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                Integer idDepartment = rs.getInt(1);
                String departmentName = rs.getString(2);

                department = new Department (idDepartment, departmentName);

            }
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
            DB.closeConnection();
        }
        return department;
    }

    @Override
    public List<Department> findAll() {
        String sql = "SELECT * FROM department ORDER BY Name";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Department> departments = new ArrayList<>();
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Integer id = rs.getInt(1);
                String departmentName = rs.getString(2);
                departments.add(new Department(id, departmentName));
            }
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
            DB.closeConnection();
        }
        return departments;
    }
}
