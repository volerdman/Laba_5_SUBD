package Entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Product {

	private int id;
	private String name;
	private boolean presence;
	private int amount;
	private ArrayList<Request> requests;

	public Product(int id, String name, boolean presence, int amount ){
		this.id = id;
		this.name = name;
		this.presence = presence;
		this.amount = amount;
		this.requests = new ArrayList<Request>();
	}
	
	public Product(String name, boolean presence, int amount ){
		this.name = name;
		this.presence = presence;
		this.amount = amount;
		this.requests = new ArrayList<Request>();
	}
	
	public Product(){
		
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public boolean getPresence(){
		return presence;
	}
	
	public void setPresence(boolean presence){
		this.presence = presence;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public Vector<Object> setData(Connection conection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(name);
		data.add(presence);
		data.add(amount);
		return data;
	}

	public void addElement(String name, boolean presence, int amount, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("insert into product values(nextval('seq_product'),'" + name+ "', " + presence + ", '" + amount + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("delete from product where productid = " + id + ";");
	}

	public void refreshElement(int id,String name, boolean presence, int amount, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("update product set name = '" + name + "', presence = " + presence + ", amount = '" + amount + "' where productid = " + id + ";");
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
		columnNames.add("Наименование");
		columnNames.add("Наличие");
		columnNames.add("Количество");
		return columnNames;
	}

	public ArrayList<Product> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from product;");
		ArrayList<Product> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Product((int) rs.getObject(1), rs.getObject(2).toString(), (boolean) rs.getObject(3), (int)rs.getObject(4)));
		}
		return res;
	}
}
