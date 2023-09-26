package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Map;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		Reserva[] reservas = obtenerReservas();
		Huesped[] huespedes = obtenerHuespedes();

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		
		for (int i = 0; i < reservas.length; i++) {
			if (reservas[i] != null) {
				modelo.addRow(new Object[] { reservas[i].getId(), reservas[i].getFechaCheckIn(),
						reservas[i].getFechaCheckOut(), reservas[i].getValor(), reservas[i].getFormaPago() });
			}
		}

		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table,
				null);
		scroll_table.setVisible(true);

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");

		for (int i = 0; i < huespedes.length; i++) {
			if (huespedes[i] != null) {
				modeloHuesped.addRow(new Object[] { huespedes[i].getId(), huespedes[i].getNombre(),
						huespedes[i].getApellido(), huespedes[i].getFechaNacimiento(), huespedes[i].getNacionalidad(),
						huespedes[i].getTelefono(), huespedes[i].getIdReserva() });
			}
		}

		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")),
				scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String query = txtBuscar.getText();
				Reserva[] reservas = buscarReservas(query);
				modelo.setRowCount(0);
				for (int i = 0; i < reservas.length; i++) {
					if (reservas[i] != null) {
						modelo.addRow(new Object[] { reservas[i].getId(), reservas[i].getFechaCheckIn(),
								reservas[i].getFechaCheckOut(), reservas[i].getValor(), reservas[i].getFormaPago() });
					}
				}

				Huesped[] huespedes = buscarHuespedes(query);
				modeloHuesped.setRowCount(0);
				for (int i = 0; i < huespedes.length; i++) {
					if (huespedes[i] != null) {
						modeloHuesped.addRow(new Object[] { huespedes[i].getId(), huespedes[i].getNombre(),
								huespedes[i].getApellido(), huespedes[i].getFechaNacimiento(),
								huespedes[i].getNacionalidad(), huespedes[i].getTelefono(),
								huespedes[i].getIdReserva() });
					}
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "La editación de reservas no está disponible por el momento en su lugar puede eliminar la reserva y crear una nueva");
			}
		});

		

		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);

		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbReservas.getSelectedRow();
				if (row != -1) {
					int id = (int) tbReservas.getValueAt(row, 0);
					deleteReserva(id);
					modelo.removeRow(row);
				}

				row = tbHuespedes.getSelectedRow();
				if (row != -1) {
					int id = (int) tbHuespedes.getValueAt(row, 0);
					deleteHuesped(id);
					modeloHuesped.removeRow(row);
				}
			}
		});

		setResizable(false);
	}

	private Reserva[] buscarReservas(String query) {
		String connection = "jdbc:sqlite:db/hotel.db";
		String sql = "SELECT * FROM reservas WHERE id LIKE '%" + query + "%' OR fechaEntrada LIKE '%" + query
				+ "%' OR fechaSalida LIKE '%" + query + "%' OR valor LIKE '%" + query + "%' OR formaPago LIKE '%" + query
				+ "%'";
		
		Reserva[] reservas = new Reserva[100];

		try (Connection conn = DriverManager.getConnection(connection);
			 java.sql.Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String fechaCheckIn = rs.getString("fechaEntrada");
				String fechaCheckOut = rs.getString("fechaSalida");
				String valor = rs.getString("valor");
				String formaPago = rs.getString("formaPago");

				Reserva reserva = new Reserva(id, fechaCheckIn, fechaCheckOut, valor, formaPago);
				reservas[id] = reserva;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		return reservas;
	}

	private Reserva[] obtenerReservas() {
		String connection = "jdbc:sqlite:db/hotel.db";
		String sql = "SELECT * FROM reservas";
		Reserva[] reservas = new Reserva[100];
		int index = 0;

		try (Connection conn = DriverManager.getConnection(connection);
			 java.sql.Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String fechaCheckIn = rs.getString("fechaEntrada");
				String fechaCheckOut = rs.getString("fechaSalida");
				String valor = rs.getString("valor");
				String formaPago = rs.getString("formaPago");

				Reserva reserva = new Reserva(id, fechaCheckIn, fechaCheckOut, valor, formaPago);
				reservas[index] = reserva;
				index++;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		return reservas;
	}

	private Huesped[] buscarHuespedes(String query) {
		String connection = "jdbc:sqlite:db/hotel.db";
		String sql = "SELECT * FROM huespedes WHERE id LIKE '%" + query + "%' OR nombre LIKE '%" + query
				+ "%' OR apellido LIKE '%" + query + "%' OR fechaNacimiento LIKE '%" + query + "%' OR nacionalidad LIKE '%"
				+ query + "%' OR telefono LIKE '%" + query + "%' OR idReserva LIKE '%" + query + "%'";
		
		Huesped[] huespedes = new Huesped[100];

		try (Connection conn = DriverManager.getConnection(connection);
			 java.sql.Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				String fechaNacimiento = rs.getString("fechaNacimiento");
				String nacionalidad = rs.getString("nacionalidad");
				String telefono = rs.getString("telefono");
				int idReserva = rs.getInt("idReserva");

				Huesped huesped = new Huesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
				huespedes[id] = huesped;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		return huespedes;
	}

	private Huesped[] obtenerHuespedes() {
		String connection = "jdbc:sqlite:db/hotel.db";
		String sql = "SELECT * FROM huespedes";
		Huesped[] huespedes = new Huesped[100];
		int index = 0;

		try (Connection conn = DriverManager.getConnection(connection);
			 java.sql.Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				String fechaNacimiento = rs.getString("fechaNacimiento");
				String nacionalidad = rs.getString("nacionalidad");
				String telefono = rs.getString("telefono");
				int idReserva = rs.getInt("idReserva");

				Huesped huesped = new Huesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
				huespedes[index] = huesped;
				index++;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		return huespedes;
	}

	private void deleteReserva(int id) {
		String connection = "jdbc:sqlite:db/hotel.db";
		String sql = "DELETE FROM reservas WHERE id = " + id;

		try (Connection conn = DriverManager.getConnection(connection);
			 java.sql.Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(sql);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void deleteHuesped(int id) {
		String connection = "jdbc:sqlite:db/hotel.db";
		String sql = "DELETE FROM huespedes WHERE id = " + id;

		try (Connection conn = DriverManager.getConnection(connection);
			 java.sql.Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(sql);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Código que permite mover la ventana por la pantalla según la posición de "x"
	// y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}

class Reserva {
	private int id;
	private String fechaCheckIn;
	private String fechaCheckOut;
	private String valor;
	private String formaPago;

	public Reserva(int id, String fechaCheckIn, String fechaCheckOut, String valor, String formaPago) {
		this.id = id;
		this.fechaCheckIn = fechaCheckIn;
		this.fechaCheckOut = fechaCheckOut;
		this.valor = valor;
		this.formaPago = formaPago;
	}

	public int getId() {
		return id;
	}

	public String getFechaCheckIn() {
		return fechaCheckIn;
	}

	public String getFechaCheckOut() {
		return fechaCheckOut;
	}

	public String getValor() {
		return valor;
	}

	public String getFormaPago() {
		return formaPago;
	}
}

class Huesped {
	private int id;
	private String nombre;
	private String apellido;
	private String fechaNacimiento;
	private String nacionalidad;
	private String telefono;
	private int idReserva;
	
	public Huesped(int id, String nombre, String apellido, String fechaNacimiento, String nacionalidad, String telefono, int idReserva) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public int getIdReserva() {
		return idReserva;
	}
}