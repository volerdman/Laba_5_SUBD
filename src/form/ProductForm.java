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
import Entities.Product;
import javafx.scene.control.CheckBox;

import javax.swing.JCheckBox;

public class ProductForm extends JFrame{
	
	public int idSelected;
	private Connection connection = null;

	private JPanel panel;
	private JTextField textFieldName;
	private JTextField textFieldAmount;
	private JLabel label1;
	private JCheckBox checkBox;

	/**
	 * Launch the application.
	 */
	public ProductForm(Connection connection) {
		initialize();
		this.idSelected = -1;
		this.connection = connection;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ProductForm(int id, Connection connection)throws SQLException  {
		initialize();
		this.idSelected = id;
		this.connection = connection;
		Product p = new Product();
		ArrayList<Product> product = new ArrayList<>(p.getTable(connection));
		p = null;
		for (int i = 0; i < product.size(); i++) {
			if (id == product.get(i).getId()) {
				p = product.get(i);
				break;
			}
		}
		textFieldName.setText(p.getName());
		textFieldAmount.setText("" + p.getAmount());
		
		
		
		

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panel = new JPanel();
		setContentPane(panel);
		getContentPane().setLayout(null);
		
		checkBox = new JCheckBox("в наличии");
		checkBox.setSelected(true);
		checkBox.setBounds(143, 84, 97, 23);
		panel.add(checkBox);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(143, 46, 116, 22);
		panel.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldAmount = new JTextField();
		textFieldAmount.setColumns(10);
		textFieldAmount.setBounds(143, 114, 116, 22);
		panel.add(textFieldAmount);
		
		JButton buttonSave = new JButton("Сохранить");
		buttonSave.setBounds(12, 171, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Product product = null;
				if (textFieldName.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
				if (textFieldAmount.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
				try {
					product = new Product(textFieldName.getText(),checkBox.isSelected(), Integer.parseInt(textFieldAmount.getText()));
					if (idSelected < 0) {
						product.addElement(textFieldName.getText(), checkBox.isSelected(),Integer.parseInt(textFieldAmount.getText()), connection);
					} else {
						product.refreshElement(idSelected,textFieldName.getText(), checkBox.isSelected(),Integer.parseInt(textFieldAmount.getText()), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		panel.add(buttonSave);
		
		JButton buttonCancel = new JButton("Отмена");
		buttonCancel.setBounds(143, 171, 97, 25);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		panel.add(buttonCancel);
		
		JLabel label1;
		label1 = new JLabel("Наименование");
		label1.setBounds(12, 49, 84, 16);
		panel.add(label1);
		
		JLabel label_1 = new JLabel("Наличие товара");
		label_1.setBounds(12, 84, 84, 16);
		panel.add(label_1);
		
		JLabel label = new JLabel("Количество");
		label.setBounds(12, 117, 84, 16);
		panel.add(label);
	}
}