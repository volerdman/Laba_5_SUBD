package Entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Delivery {

	private int deliveryid;
	private int providerid;
	private int productid;
	private String date;
	private int amount;
	
	public Delivery(int deliveryid, int productid, int providerid, String date, int amount ){
		this.deliveryid = deliveryid;
		this.productid = productid;
		this.providerid = providerid;
		this.date = date;
		this.amount = amount;
	}
	
	public Delivery(int productid, int providerid, String date, int amount){
		this.productid = productid;
		this.providerid = providerid;
		this.date = date;
		this.amount = amount;
	}
	
	public Delivery(){
		
	}
	
	public int getId(){
		return deliveryid;
	}
	
	public void setId(int deliveryid){
		this.deliveryid = deliveryid;
	}
	
	public int getProductid(){
		return productid;
	}
	
	public void setProductid(int productid){
		this.productid = productid;
	}
	
	public int getProviderid(){
		return providerid;
	}
	
	public void setProviderid(int providerid){
		this.providerid = providerid;
	}

	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}

	
	
	public Vector<Object> setData(Connection connection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(deliveryid);
		data.add(date);
		data.add(amount);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select name from product where productid = " + productid + ";");
		while (rs.next()) {
			data.add(rs.getString("name"));
		}
		rs = statement.executeQuery("select surname, name, patronymic from provider where providerid = " + providerid + ";");
		while (rs.next()) {
			data.add(rs.getString("surname"));	
			data.add(rs.getString("name"));	
			data.add(rs.getString("patronymic"));	
		}
		return data;

	}

	public void addElement(int productid, int providerid, String date, int amount, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("insert into delivery values(nextval('seq_delivery'),'" + productid + "', '" + providerid
				+ "', '" + date + "', '" + amount  + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("delete from delivery where deliveryid = " + id + ";");
	}

	public void refreshElement(int id, int productid, int providerid, String date,
			int amount, Connection connection) throws SQLException {		
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("update delivery set productid = " + productid + ",providerid = " + providerid
				+ ", date = '" + date + "', amount = '" + amount  + "' where deliveryid = " + id + ";");
	}

	public DefaultTableModel TableModel(Connection connection) throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		for (int i = 0; i <= getTable(connection).size() - 1; i++) {
			data.add(getTable(connection).get(i).setData(connection));
		}
		tableModel.setDataVector(data, columnNames);
		return tableModel;
	}

	public Vector<String> getTitles() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("id");
		columnNames.add("Продукт");
		columnNames.add("Поставщик");
		columnNames.add("Дата");
		columnNames.add("Количество");
		return columnNames;
	}

	public ArrayList<Delivery> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from delivery");
		ArrayList<Delivery> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Delivery((int) rs.getObject(1), (int) rs.getObject(2), (int) rs.getObject(3),
					 rs.getObject(4).toString(), (int) rs.getObject(5)));
		}
		return res;
	}
}
