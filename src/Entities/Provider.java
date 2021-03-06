package Entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Provider {


	private int id;
	private String surname;
	private String name;
	private String patronymic;
	private String name_company;
	private ArrayList<Delivery> deliverys;

	public Provider(int id, String surname, String name, String patronymic, String name_company ){
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.name_company = name_company;
		this.deliverys = new ArrayList<Delivery>();
	}
	
	public Provider(String surname, String name, String patronymic, String name_company ){
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.name_company = name_company;
		this.deliverys = new ArrayList<Delivery>();
	}
	
	public Provider(){
		
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getSurName(){
		return surname;
	}
	
	public void setSurName(String surname){
		this.surname = surname;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getPatronymic(){
		return name;
	}
	
	public void setPatronymic(String patronymic){
		this.patronymic = patronymic;
	}
	
	public String getNameCompany(){
		return name_company;
	}
	
	public void setNameCompany(String name_company){
		this.name_company = name_company;
	}
	
	public Vector<Object> setData(Connection conection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(surname);
		data.add(name);
		data.add(patronymic);
		data.add(name_company);
		return data;
	}

	public void addElement(String surname, String name, String patronymic, String name_company, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("insert into provider values(nextval('seq_provider'),'" + surname+ "', '" + name + "', '" + patronymic+ "', '" + name_company  + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("delete from provider where providerid = " + id + ";");
	}

	public void refreshElement(int id,String surname, String name, String patronymic, String name_company, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("update product set surname = '" + surname + "', name = " + name + ", patronymic = '" + patronymic + "', name_company = "+name_company + "' where providerid = " + id + ";");
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
		columnNames.add("Фамилия");
		columnNames.add("Имя");
		columnNames.add("Отчество");
		columnNames.add("Название компании");
		return columnNames;
	}

	public ArrayList<Provider> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from provider;");
		ArrayList<Provider> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Provider((int) rs.getObject(1), rs.getObject(2).toString(), rs.getObject(3).toString(), rs.getObject(4).toString(), rs.getObject(5).toString()));
		}
		return res;
	}
}
