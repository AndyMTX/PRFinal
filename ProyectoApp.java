package main.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JToggleButton;

public class ProyectoApp {

	private JFrame frame;
	private JTextField UsuarioInicio;
	private JTextField PassInicio;

	public Connection conn=null;
	public ResultSet rs;
	private JLabel InicioAviso;
	
	/*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*/
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProyectoApp window = new ProyectoApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public ProyectoApp() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException {
		initialize();
	}


	private void initialize() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException {	
		frame = new JFrame();
		frame.setBounds(100, 100, 1073, 701);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		UsuarioInicio = new JTextField();
		UsuarioInicio.setToolTipText("");
		UsuarioInicio.setBounds(208, 163, 603, 34);
		frame.getContentPane().add(UsuarioInicio);
		UsuarioInicio.setColumns(10);
		
		PassInicio = new JTextField();
		PassInicio.setText("");
		PassInicio.setColumns(10);
		PassInicio.setBounds(208, 247, 603, 34);
		frame.getContentPane().add(PassInicio);
		
		InicioAviso = new JLabel("Comprueba las credenciales o crea una nueva cuenta");
		InicioAviso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		InicioAviso.setBounds(355, 343, 347, 34);
		frame.getContentPane().add(InicioAviso);
		InicioAviso.setVisible(false);
		
		conectar();
		
		JButton Login = new JButton("Iniciar sesión");
		Login.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					Statement s=conn.createStatement();
					
					rs=s.executeQuery("select * from usuarios "
							+ "where nombre_usuario='"+UsuarioInicio.getText()+"' and clave='"+PassInicio.getText()+"';");
										
					if(rs.next()) {
						UsuarioInicio.setVisible(false);
					}else {
						InicioAviso.setForeground(Color.RED);;
						InicioAviso.setVisible(true);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Login.setBounds(393, 406, 239, 23);
		frame.getContentPane().add(Login);
		
		JButton btnCrearCuenta = new JButton("Crear cuenta");
		btnCrearCuenta.setBounds(393, 452, 239, 23);
		frame.getContentPane().add(btnCrearCuenta);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("New toggle button");
		tglbtnNewToggleButton.setBounds(304, 72, 121, 23);
		frame.getContentPane().add(tglbtnNewToggleButton);
		
	
		System.out.println("HOLA");
	}

	
	@SuppressWarnings("deprecation")
	public void conectar() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Properties prop=new Properties();
		InputStream IS=new FileInputStream(new File("src/resources/bd.properties"));
		prop.load(IS);
		
		String user=prop.getProperty("user","");
		String pswd = prop.getProperty("password", "");
		String url = prop.getProperty("url", "");
		String drv = prop.getProperty("driver", "");
		
	
		Class.forName(drv).newInstance();
		conn = DriverManager.getConnection(url,user,pswd);
	}
}
