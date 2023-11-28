package org.example.repository;

import org.example.model.Product;
import org.example.utils.BD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Crud<Product> {
    public ProductRepository() {
        try {
            createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Connection getConnection(){
        return BD.getInstance();
    }
    private void createTable() throws SQLException {
        try(Statement stmt = getConnection().createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS product ("+
                    "id integer PRIMARY KEY,"+
                    "name text NOT NULL,"+
                    "cost real,"+
                    "price real,"+
                    "iva int,"+
                    "active int"+
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;
    }

    private Product createData(ResultSet rs) throws SQLException{
        Product product = new Product();
        product.setName(rs.getString("name"));
        product.setPrice(rs.getFloat("price"));
        product.setCost(rs.getFloat("cost"));
        product.setIva(rs.getInt("iva"));
        product.setActive(rs.getInt("active"));

        return product;
    }
    @Override
    public List<Product> getList() {
        List<Product> productList = new ArrayList<>();

        try(Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from product")) {
            while(rs.next()){
                productList.add(this.createData(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;

        return productList;
    }

    @Override
    public Product getId(Integer id) {
        Product product = new Product();

        try(PreparedStatement stmt = getConnection()
                .prepareStatement("select * from product where id=?")){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                product = this.createData(rs);
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return product;
    }

    @Override
    public int add(Product m) {
        int id = 0;
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("insert into product (name, cost, price, iva, active) values (?,?,?,?,?)")){
            stmt.setString(1, m.getName());
            stmt.setFloat(2, m.getCost());
            stmt.setFloat(3, m.getPrice());
            stmt.setFloat(4, m.getIva());
            stmt.setInt(7, m.getActive());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return id;
    }

    @Override
    public void update(Product m) {
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("update product set name=?, cost=?, price=?, iva=?, active=? where id=?")){
            stmt.setString(1, m.getName());
            stmt.setFloat(2, m.getCost());
            stmt.setFloat(3, m.getPrice());
            stmt.setFloat(4, m.getIva());
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
                .prepareStatement("delete from product where id=?")){
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
