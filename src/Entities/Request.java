package Entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Request {
	private int requestid;
	private int customerid;
	private int productid;
	private String date;
	private int amount;
	
	public Request(int requestid, int productid, int customerid, String date, int amount ){
		this.requestid = requestid;
		this.productid = productid;
		this.customerid = customerid;
		this.date = date;
		this.amount = amount;
	}
	
	public Request(int productid, int customerid, String date, int amount){
		this.productid = productid;
		this.customerid = customerid;
		this.date = date;
		this.amount = amount;
	}
	
	public Request(){
		
	}
	
	public int getId(){
		return requestid;
	}
	
	public void setId(int requestid){
		this.requestid = requestid;
	}
	
	public int getProductid(){
		return productid;
	}
	
	public void setProductid(int productid){
		this.productid = productid;
	}
	
	public int getCustomerid(){
		return customerid;
	}
	
	public void setCustomerid(int customerid){
		this.customerid = customerid;
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
		data.add(requestid);
		data.add(date);
		data.add(amount);
		Statement statement = connection.createStatement();
		
		ResultSet rs = statement.executeQuery("select name from product where productid = " + productid + ";");
		while (rs.next()) {
			data.add(rs.getString("name"));
		}
		rs = statement.executeQuery("select surname, name, patronymic from customer where customerid = " + customerid + ";");
		while (rs.next()) {
			data.add(rs.getString("surname"));	
			data.add(rs.getString("name"));	
			data.add(rs.getString("patronymic"));	
		}
		return data;

	}

	public void addElement(int productid, int customerid, String date, int amount, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("insert into request values(nextval('seq_request'),'" + productid + "', '" + customerid
				+ "', '" + date + "', '" + amount  + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("delete from request where requestid = " + id + ";");
	}

	public void refreshElement(int id, int productid, int customerid, String date,
			int amount, Connection connection) throws SQLException {		
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("update request set productid = '" + productid + "',customerid = '" + customerid
				+ "', date = " + date + ", amount = " + amount  + " where requestid = " + id + ";");
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
		columnNames.add("Заказчик");
		columnNames.add("Дата");
		columnNames.add("Количество");
		return columnNames;
	}

	public ArrayList<Request> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from request");
		ArrayList<Request> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Request((int) rs.getObject(1), (int) rs.getObject(2), (int) rs.getObject(3),
					 rs.getObject(4).toString(), (int) rs.getObject(5)));
		}
		return res;
	}
}
