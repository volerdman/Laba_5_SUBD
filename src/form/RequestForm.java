package form;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Entities.Customer;
import Entities.Product;
import Entities.Request;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class RequestForm extends JFrame{

	private Connection connection = null;
	public int idSelected;
	private JPanel contentPane;

	private JPanel panel;
	private JTextField textFieldDate;
	private JTextField textFieldAmount;
	private JComboBox comboBoxCustomer;
	private JComboBox comboBoxProduct;
	private ArrayList<Product> product;
	private ArrayList<Customer> customer;
	private JButton buttonSave;
	private JButton buttonCancel;
	private JLabel label_8;
	private JLabel label_9;
	private JLabel label_10;
	private JLabel label_11;



	public RequestForm(Connection connection) throws SQLException  {
		initialize();
		this.connection = connection;
		this.idSelected = -1;
		
		Customer cu = new Customer();
		customer = new ArrayList<>(cu.getTable(connection));
		comboBoxCustomer.removeAllItems();
		for (int i = 0; i < customer.size(); i++) {
			comboBoxCustomer.addItem("" + customer.get(i).getSurName() + " " + customer.get(i).getName());
		}
		Product p = new Product();
		product = new ArrayList<>(p.getTable(connection));
		comboBoxProduct.removeAllItems();
		for (int i = 0; i < product.size(); i++) {
			comboBoxProduct.addItem("" + product.get(i).getName());
		}
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public RequestForm(int id, Connection connection) throws SQLException  {
		setTitle("Заказ");
		initialize();
		
		int cid = 0;
		int pid = 0;
		this.connection = connection;
		this.idSelected = id;
		Request r = new Request();
		ArrayList<Request >request = new ArrayList<>(r.getTable(connection));
		r = null;
		for (int i = 0; i < request.size(); i++) {
			if (id == request.get(i).getId()) {
				r = request.get(i);
				break;
			}
		}
		
		Customer cu = new Customer();
		customer = new ArrayList<>(cu.getTable(connection));
		comboBoxCustomer.removeAllItems();
		for (int i = 0; i < customer.size(); i++) {
			comboBoxCustomer.addItem("" + customer.get(i).getSurName() + " " + customer.get(i).getName()+" " + customer.get(i).getPatronymic());
			if (r.getCustomerid() == customer.get(i).getId()) {
				cid = i;
			}
		}
		
		Product p = new Product();
		product = new ArrayList<>(p.getTable(connection));
		for (int i = 0; i < product.size(); i++) {
			comboBoxProduct.addItem("" + product.get(i).getName());
			if (r.getProductid() == product.get(i).getId()) {
				pid = i;
			}
		}
		
		textFieldDate.setText(r.getDate());
		//textFieldSum.setText(o.getSum());
		comboBoxCustomer.setSelectedItem(customer.get(cid).getSurName());
		comboBoxProduct.setSelectedItem(product.get(pid).getName());
		
		

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 378, 277);
		panel = new JPanel();
		setContentPane(panel);
		getContentPane().setLayout(null);
		
		label_8 = new JLabel("Дата");
		label_8.setBounds(10, 46, 56, 16);
		panel.add(label_8);
		
		label_9 = new JLabel("Количество товара");
		label_9.setBounds(10, 88, 116, 16);
		panel.add(label_9);
		
		label_10 = new JLabel("Заказчик");
		label_10.setBounds(10, 123, 56, 16);
		panel.add(label_10);
		
		label_11 = new JLabel("Товар");
		label_11.setBounds(10, 159, 56, 16);
		panel.add(label_11);
		
		textFieldDate = new JTextField();
		textFieldDate.setBounds(121, 46, 116, 22);
		getContentPane().add(textFieldDate);
		textFieldDate.setColumns(10);
		
		textFieldAmount = new JTextField();
		textFieldAmount.setBounds(121, 85, 116, 22);
		getContentPane().add(textFieldAmount);
		textFieldAmount.setColumns(10);
		
		comboBoxCustomer = new JComboBox();
		comboBoxCustomer.setBounds(121, 123, 116, 22);
		getContentPane().add(comboBoxCustomer);
		
		comboBoxProduct = new JComboBox();
		comboBoxProduct.setBounds(121, 159, 116, 22);
		getContentPane().add(comboBoxProduct);
		
		buttonSave = new JButton("Сохранить");
		buttonSave.setBounds(10, 199, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Request request = null;
				if (textFieldDate.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
				try {
					request = new Request((comboBoxProduct.getSelectedIndex()),(comboBoxCustomer.getSelectedIndex()),
							textFieldDate.getText(),
							Integer.parseInt(textFieldAmount.getText())
							 );
					if (idSelected < 0) {
						request.addElement(product.get(comboBoxProduct.getSelectedIndex()).getId(),customer.get(comboBoxCustomer.getSelectedIndex()).getId(),
								textFieldDate.getText(),
								Integer.parseInt(textFieldAmount.getText()), connection);
					} else {
						request.refreshElement(idSelected,product.get(comboBoxProduct.getSelectedIndex()).getId(),customer.get(comboBoxCustomer.getSelectedIndex()).getId(),
								textFieldDate.getText(),
								Integer.parseInt(textFieldAmount.getText()), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					Logger.getLogger(RequestForm.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});

		getContentPane().add(buttonSave);
		
		buttonCancel = new JButton("Отмена");
		buttonCancel.setBounds(174, 199, 97, 25);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		getContentPane().add(buttonCancel);
	}

}