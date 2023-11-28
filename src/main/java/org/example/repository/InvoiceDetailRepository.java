package org.example.repository;

import org.example.model.Invoice;
import org.example.model.InvoiceDetail;
import org.example.utils.BD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailRepository implements Crud<InvoiceDetail>{
    public InvoiceDetailRepository() {
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
            stmt.execute("CREATE TABLE IF NOT EXISTS invoiceDetail ("+
                    "invoice_id integer NOT NULL,"+
                    "product_id integer NOT NULL,"+
                    "FOREIGN KEY (invoice_id) REFERENCES invoice (id), "+
                    "FOREIGN KEY (product_id) REFERENCES product (id) "+
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;
    }

    private InvoiceDetail createData(ResultSet rs) throws SQLException{
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setInvoice_id(rs.getInt("invoice_id"));
        invoiceDetail.setProduct_id(rs.getInt("product_id"));

        return invoiceDetail;
    }

    @Override
    public List<InvoiceDetail> getList() {
        List<InvoiceDetail> invoiceDetailsList = new ArrayList<>();

        try(Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from invoiceDetail")) {
            while(rs.next()){
                invoiceDetailsList.add(this.createData(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;

        return invoiceDetailsList;
    }

    @Override
    public InvoiceDetail getId(Integer id) {
        return null;
    }

    @Override
    public int add(InvoiceDetail m) {
        int id = 0;
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("insert into invoiceDetail (invoice_id, product_id) values (?,?)")){
            stmt.setInt(1, m.getInvoice_id());
            stmt.setInt(2, m.getProduct_id());

            stmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return id;
    }

    @Override
    public void update(InvoiceDetail m) {
    }

    @Override
    public void delete(Integer id) {
    }
}
