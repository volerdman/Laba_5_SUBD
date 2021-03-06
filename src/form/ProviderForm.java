package form;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Entities.Customer;
import Entities.Provider;
import java.awt.BorderLayout;

public class ProviderForm extends JFrame{
	
	public int idSelected;
	private Connection connection = null;

	private JPanel panel;
	private JTextField textFieldSurname;
	private JTextField textFieldName;
	private JTextField textFieldPatronymic;
	private JTextField textFieldNameCompany;


	/**
	 * Launch the application.
	 */
	public ProviderForm(Connection connection) {
		initialize();
		this.idSelected = -1;
		this.connection = connection;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ProviderForm(int id, Connection connection)throws SQLException  {
		getContentPane().setLayout(null);
		
		
		
		initialize();
		this.idSelected = id;
		this.connection = connection;
		Provider pr = new Provider();
		ArrayList<Provider> provider = new ArrayList<>(pr.getTable(connection));
		pr = null;
		for (int i = 0; i < provider.size(); i++) {
			if (id == provider.get(i).getId()) {
				pr = provider.get(i);
				break;
			}
		}
		textFieldSurname.setText(pr.getSurName());
		textFieldName.setText(pr.getName());
		textFieldPatronymic.setText(pr.getPatronymic());
		textFieldNameCompany.setText(pr.getNameCompany());
		
		

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		textFieldSurname = new JTextField();
		textFieldSurname.setText((String) null);
		textFieldSurname.setColumns(10);
		textFieldSurname.setBounds(137, 35, 116, 22);
		getContentPane().add(textFieldSurname);
		
		textFieldName = new JTextField();
		textFieldName.setText((String) null);
		textFieldName.setColumns(10);
		textFieldName.setBounds(137, 74, 116, 22);
		getContentPane().add(textFieldName);
		
		textFieldPatronymic = new JTextField();
		textFieldPatronymic.setText((String) null);
		textFieldPatronymic.setColumns(10);
		textFieldPatronymic.setBounds(137, 115, 116, 22);
		getContentPane().add(textFieldPatronymic);
		
		
		
		JLabel label = new JLabel("Фамилия");
		label.setBounds(10, 36, 56, 16);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Имя");
		label_1.setBounds(10, 78, 56, 16);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Отчество");
		label_2.setBounds(10, 115, 56, 16);
		getContentPane().add(label_2);
		
		textFieldNameCompany = new JTextField();
		textFieldNameCompany.setText((String) null);
		textFieldNameCompany.setColumns(10);
		textFieldNameCompany.setBounds(137, 147, 116, 22);
		getContentPane().add(textFieldNameCompany);
		
		JLabel label_3 = new JLabel("Название компании");
		label_3.setBounds(10, 150, 106, 16);
		getContentPane().add(label_3);
		getContentPane().setLayout(null);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		
		JButton buttonSave = new JButton("Сохранить");
		buttonSave.setBounds(10, 197, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Provider provider = null;
				if (textFieldSurname.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
				if (textFieldName.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
				try {
					provider = new Provider(textFieldSurname.getText(), textFieldName.getText(),textFieldPatronymic.getText(), textFieldNameCompany.getText());
					if (idSelected < 0) {
						provider.addElement(textFieldSurname.getText(), textFieldName.getText(),textFieldPatronymic.getText(), textFieldNameCompany.getText(), connection);
					} else {
						provider.refreshElement(idSelected,textFieldSurname.getText(), textFieldName.getText(),textFieldPatronymic.getText(), textFieldNameCompany.getText(), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		getContentPane().add(buttonSave);
		
		JButton buttonCancel = new JButton("Отмена");
		buttonCancel.setBounds(137, 197, 97, 25);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		getContentPane().add(buttonCancel);
	}
}
