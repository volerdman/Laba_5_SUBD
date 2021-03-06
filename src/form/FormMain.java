package form;

import java.awt.EventQueue;

import javafx.scene.control.ComboBox;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;



import com.sun.glass.events.WindowEvent;

import Entities.Customer;
import Entities.Delivery;
import Entities.Product;
import Entities.Provider;
import Entities.Request;
import form.FormNewConnection;
import javax.swing.DefaultComboBoxModel;


public class FormMain {
	JComboBox ComboBox;
	private JFrame frame;
	private JTable table;
	Connection connection = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormMain window = new FormMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FormMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		String[] str = { "Заказчик", "Заказ", "Продукт", "Доставка", "Поставщик" };
		frame = new JFrame();
		frame.setBounds(100, 100, 679, 312);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		

	
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuConnection = new JMenu("\u041F\u043E\u0434\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u0435");
		menuBar.add(menuConnection);
		
		JMenuItem menuItemNew = new JMenuItem("\u041D\u043E\u0432\u043E\u0435 \u043F\u043E\u0434\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u0435");
		menuItemNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormNewConnection form = new FormNewConnection(frame);
				connection = form.getConnection();
				if (connection != null) {
					
				}
			}
		});
		menuConnection.add(menuItemNew);
		
		
		JMenuItem menuItemShutdown = new JMenuItem("\u041E\u0442\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u0435");
		menuItemShutdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Disconnect();
				for( int i=0; i< table.getRowCount(); i++){
					
				}
			}
		});
		
		menuConnection.add(menuItemShutdown);
		
		JMenuItem menuItemExit = new JMenuItem("\u0412\u044B\u0445\u043E\u0434");
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
			}
		});
		menuConnection.add(menuItemExit);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 661, 250);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		table = new JTable();
		table.setBounds(12, 13, 523, 218);
		panel.add(table);
		
		ComboBox = new JComboBox(str);
		ComboBox.setModel(new DefaultComboBoxModel(new String[] {"Заказчик", "Продукт", "Поставщик", "Заказ", "Доставка"}));
		ComboBox.setBounds(547, 9, 102, 22);
		ComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		panel.add(ComboBox);
		
		JButton buttonAdd = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C");
		buttonAdd.setBounds(547, 61, 97, 25);
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (connection != null) {
					switch ((String) ComboBox.getSelectedItem()) {
					case "Заказчик":
						try {
							CustomerForm form1 = new CustomerForm(connection);
							form1.setVisible(true);
							Customer cu = new Customer();
							table.setModel(cu.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Заказ":
						try {
							RequestForm form2 = new RequestForm(connection);
							form2.setVisible(true);
							Request r = new Request();
							table.setModel(r.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Продукт":
						try {
							ProductForm form3 = new ProductForm(connection);
							form3.setVisible(true);
							Product p = new Product();
							table.setModel(p.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Поставщик":
						try {
							ProviderForm form4 = new ProviderForm(connection);
							form4.setVisible(true);
							Provider p = new Provider();
							table.setModel(p.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Доставка":
						try {
							DeliveryForm form5 = new DeliveryForm(connection);
							form5.setVisible(true);
							Delivery d = new Delivery();
							table.setModel(d.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					}
				}
			}
		});
		panel.add(buttonAdd);
		
		JButton buttonDel = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C");
		buttonDel.setBounds(547, 99, 97, 25);
		buttonDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0 && connection != null) {
					int idEl = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
					switch ((String) ComboBox.getSelectedItem()) {
					case "Заказчик":
						try {
							Customer cu = new Customer();
							cu.removeElement(idEl, connection);
							table.setModel(cu.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Заказ":
						try {
							Request r = new Request();
							r.removeElement(idEl, connection);
							table.setModel(r.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Поставщик":
						try {
							Provider p = new Provider();
							p.removeElement(idEl, connection);
							table.setModel(p.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Доставка":
						try {
							Delivery d = new Delivery();
							d.removeElement(idEl, connection);
							table.setModel(d.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Продукт":
						try {
							Product p = new Product();
							p.removeElement(idEl, connection);
							table.setModel(p.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					}
				} else if (connection != null) {
					JOptionPane.showMessageDialog(null, "Выберите элемент");
				}
			}
		});
		panel.add(buttonDel);
		
		JButton buttonCh = new JButton("\u0418\u0437\u043C\u0435\u043D\u0438\u0442\u044C");
		buttonCh.setBounds(547, 137, 97, 25);
		buttonCh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0 && connection != null) {
					int idEl = (table.getSelectedRow());
					switch ((String) ComboBox.getSelectedItem()) {
					case "Заказчик":
						try {
							Customer cu = new Customer();
							int i = cu.getTable(connection).get(idEl).getId();
							CustomerForm form1 = new CustomerForm(i, connection);
							form1.setVisible(true);
							Customer g = new Customer();
							table.setModel(g.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Заказ":
						try {
							Request re = new Request();
							int i = re.getTable(connection).get(idEl).getId();
							RequestForm form2 = new RequestForm(i, connection);
							form2.setVisible(true);
							Request r = new Request();
							table.setModel(r.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Постащик":
						try {
							Provider p = new Provider();
							int i = p.getTable(connection).get(idEl).getId();
							ProviderForm form3 = new ProviderForm(i, connection);
							form3.setVisible(true);
							Provider pr = new Provider();
							table.setModel(pr.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Продукт":
						try {
							Product p = new Product();
							int i = p.getTable(connection).get(idEl).getId();
							ProductForm form4 = new ProductForm(i, connection);
							form4.setVisible(true);
							Product pr = new Product();
							table.setModel(pr.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Доставка":
						try {
							Delivery d = new Delivery();
							int i = d.getTable(connection).get(idEl).getId();
							DeliveryForm form5 = new DeliveryForm(i, connection);
							form5.setVisible(true);
							Delivery de = new Delivery();
							table.setModel(de.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					}
				} else if (connection != null) {
					JOptionPane.showMessageDialog(null, "Выберите элемент");
				}
			}
		});
		
		panel.add(buttonCh);
		
	
				
		
		JButton buttonUpd = new JButton("\u041E\u0431\u043D\u043E\u0432\u0438\u0442\u044C");
		buttonUpd.setBounds(547, 175, 97, 25);
		buttonUpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		
		panel.add(buttonUpd);
		
		

		
		}
		private void Disconnect() {
			if (connection != null) {
				connection = null;
				JOptionPane.showMessageDialog(null, "Соеденение закрыто");
			}
		}

		private void refresh() {
			if (connection != null) {
				switch ((String) ComboBox.getSelectedItem()) {
				case "Заказчик":
					
					try {
						Customer cu = new Customer();
						table.setModel(cu.TableModel(connection));
					} catch (SQLException ex) {
						Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				case "Заказ":
					try {
						Request r = new Request();
						table.setModel(r.TableModel(connection));
					} catch (SQLException ex) {
						Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				case "Поставщик":
					try {
						Provider p = new Provider();
						table.setModel(p.TableModel(connection));
					} catch (SQLException ex) {
						Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				case "Продукт":
					try {
						Product p = new Product();
						table.setModel(p.TableModel(connection));
					} catch (SQLException ex) {
						Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				case "Доставка":
					try {
						Delivery d = new Delivery();
						table.setModel(d.TableModel(connection));
					} catch (SQLException ex) {
						Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
			}
		
	}
}
}
