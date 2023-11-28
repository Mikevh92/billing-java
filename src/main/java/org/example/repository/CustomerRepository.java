package org.example.repository;

import org.example.model.Customer;
import org.example.utils.BD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements Crud<Customer> {
    public CustomerRepository() {
        createTable();
    }

    private Connection getConnection(){
        return BD.getInstance();
    }

    private int getLastId(){
        int id = 0;
        try(Statement stmt = getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) as id FROM customer;");
            if(rs.next()){
                id = Integer.parseInt(rs.getString("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        };

        return id;
    }

    private void createTable() {
        try(Statement stmt = getConnection().createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS customer ("+
                    "id integer PRIMARY KEY,"+
                    "name text NOT NULL,"+
                    "lastname text,"+
                    "rfc text,"+
                    "razon text,"+
                    "cp int,"+
                    "regimen text,"+
                    "active int"+
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        };
    }

    private Customer createData(ResultSet rs) throws SQLException{
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setLastname(rs.getString("lastname"));
        customer.setRfc(rs.getString("rfc"));
        customer.setRazon(rs.getString("razon"));
        customer.setCp(rs.getInt("cp"));
        customer.setRegimen(rs.getString("regimen"));
        customer.setActive(rs.getInt("active"));

        return customer;
    }
    @Override
    public List<Customer> getList() {
        List<Customer> custumerList = new ArrayList<>();

        try(Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from customer")) {
            while(rs.next()){
                custumerList.add(this.createData(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        };

        return custumerList;
    }

    @Override
    public Customer getId(Integer id) {
        Customer customer = new Customer();

        try(PreparedStatement stmt = getConnection()
                .prepareStatement("select * from customer where id=?")){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                customer = this.createData(rs);
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return customer;
    }

    @Override
    public int add(Customer m) {
        int id = 0;
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into customer (name, lastname, rfc, razon, cp, regimen, active) values (?,?,?,?,?,?,?)");
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getLastname());
            stmt.setString(3, m.getRfc());
            stmt.setString(4, m.getRazon());
            stmt.setInt(5, m.getCp());
            stmt.setString(6, m.getRegimen());
            stmt.setInt(7, m.getActive());

            stmt.executeUpdate();

            PreparedStatement stmt2 = conn.prepareStatement("SELECT MAX(id) as id FROM customer;");
            ResultSet rs = stmt2.executeQuery();
            if(rs.next()){
                id = Integer.parseInt(rs.getString("id"));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return id;
    }

    @Override
    public void update(Customer m) {
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("update customer set name=?, lastname=?, rfc=?, razon=?, cp=?, regimen=?, active=? where id=?")){
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getLastname());
            stmt.setString(3, m.getRfc());
            stmt.setString(4, m.getRazon());
            stmt.setInt(5, m.getCp());
            stmt.setString(6, m.getRegimen());
            stmt.setInt(7, m.getActive());
            stmt.setInt(8, m.getId());

            stmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("delete from customer where id=?")){
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
