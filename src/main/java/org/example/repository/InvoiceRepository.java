package org.example.repository;

import org.example.model.Invoice;
import org.example.utils.BD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository implements Crud<Invoice>{
    public InvoiceRepository() {
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
            stmt.execute("CREATE TABLE IF NOT EXISTS invoice ("+
                    "id integer PRIMARY KEY,"+
                    "id folio NOT NULL,"+
                    "uuid text,"+
                    "description text,"+
                    "date text,"+
                    "subtotal real,"+
                    "iva real,"+
                    "total real,"+
                    "customer_id INTEGER NOT NULL,"+
                    "FOREIGN KEY (customer_id) REFERENCES customer (id) "+
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;
    }

    private Invoice createData(ResultSet rs) throws SQLException{
        Invoice invoice = new Invoice();
        invoice.setFolio(rs.getInt("folio"));
        invoice.setUuid(rs.getString("uuid"));
        invoice.setDescription(rs.getString("description"));
        invoice.setDate(rs.getString("date"));
        invoice.setSubtotal(rs.getFloat("subtotal"));
        invoice.setIva(rs.getFloat("iva"));
        invoice.setTotal(rs.getFloat("total"));
        invoice.setCustomer_id(rs.getInt("customer_id"));

        return invoice;
    }
    @Override
    public List<Invoice> getList() {
        List<Invoice> invoiceList = new ArrayList<>();

        try(Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from invoice")) {
            while(rs.next()){
                invoiceList.add(this.createData(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;

        return invoiceList;
    }

    @Override
    public Invoice getId(Integer id) {
        Invoice invoice = new Invoice();

        try(PreparedStatement stmt = getConnection()
                .prepareStatement("select * from invoice where id=?")){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                invoice = this.createData(rs);
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return invoice;
    }

    @Override
    public int add(Invoice m) {
        int id = 0;
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("insert into invoice (folio, uuid, description, date, subtotal, iva, total, customer_id) values (?,?,?,?,?,?,?,?)")){
            stmt.setInt(1, m.getFolio());
            stmt.setString(2, m.getUuid());
            stmt.setString(3, m.getDescription());
            stmt.setString(4, m.getDate());
            stmt.setFloat(5, m.getSubtotal());
            stmt.setFloat(6, m.getIva());
            stmt.setFloat(7, m.getTotal());
            stmt.setInt(8, m.getCustomer_id());

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
    public void update(Invoice m) {
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("update invoice set folio=?, uuid=?, description=?, date=?, subtotal=?, iva=?, total=? where id=?")){
            stmt.setInt(1, m.getFolio());
            stmt.setString(2, m.getUuid());
            stmt.setString(3, m.getDescription());
            stmt.setString(4, m.getDate());
            stmt.setFloat(5, m.getSubtotal());
            stmt.setFloat(6, m.getIva());
            stmt.setFloat(7, m.getTotal());
            stmt.setInt(8, m.getId());

            stmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("delete from invoice where id=?")){
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
