package launcher;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import engineTester.Loop;
import renderEngine.DisplayManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.Toolkit;
import javax.swing.SwingConstants;


public class EngineLauncher {
	
	private static int WIDTH  = 800;
	private static int HEIGHT = 600;
	private static boolean FULLSCREEN = false;

	private JFrame frame;
	
	public void a() throws IOException {
		new DisplayManager("SBN Engine", WIDTH, HEIGHT, FULLSCREEN);
		new Loop();
	}
	
	public static void runLauncher() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					EngineLauncher window = new EngineLauncher();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EngineLauncher() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Burak\\Documents\\Eclipse Workspace\\SBN Engine\\Resources\\ikon.png"));
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setForeground(Color.ORANGE);
		frame.setBackground(Color.ORANGE);
		frame.setTitle("Launcher");
		frame.setBounds(100, 100, 628, 372);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		JSeparator separator = new JSeparator();
		separator.setAlignmentY(5.0f);
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.DARK_GRAY);
		separator.setBounds(11, 178, 606, 14);
		frame.getContentPane().add(separator);
		
		JButton btnDeneme = new JButton("Start");
		btnDeneme.setAlignmentY(5.0f);
		btnDeneme.setBackground(Color.BLACK);
		btnDeneme.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDeneme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				frame.dispose();
				try {
					a();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		btnDeneme.setForeground(Color.BLACK);
		btnDeneme.setBounds(265, 215, 97, 25);
		frame.getContentPane().add(btnDeneme);
		
		JRadioButton rdbtnTamEkran = new JRadioButton("Fullscreen");
		rdbtnTamEkran.setAlignmentY(5.0f);
		rdbtnTamEkran.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				FULLSCREEN = !FULLSCREEN;
				
				if(FULLSCREEN) {
					WIDTH  = 1920;
					HEIGHT = 1080;
				}
				else {
					WIDTH  = 1290;
					HEIGHT = 920;
				}
			}
		});
		rdbtnTamEkran.setForeground(Color.WHITE);
		rdbtnTamEkran.setBackground(Color.DARK_GRAY);
		rdbtnTamEkran.setBounds(450, 244, 97, 25);
		frame.getContentPane().add(rdbtnTamEkran);
		
		JButton btnKapat = new JButton("Close");
		btnKapat.setAlignmentY(5.0f);
		btnKapat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnKapat.setBackground(Color.BLACK);
		btnKapat.setBounds(265, 276, 97, 25);
		frame.getContentPane().add(btnKapat);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setAlignmentY(5.0f);
		separator_1.setForeground(Color.BLACK);
		separator_1.setBackground(Color.DARK_GRAY);
		separator_1.setBounds(11, 345, 606, 14);
		frame.getContentPane().add(separator_1);
		
		JLabel lblSbnEngne = new JLabel("SBN Engine");
		lblSbnEngne.setBackground(Color.BLACK);
		lblSbnEngne.setFont(new Font("Calibri Light", Font.BOLD, 42));
		lblSbnEngne.setForeground(new Color(204, 204, 204));
		lblSbnEngne.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSbnEngne.setHorizontalAlignment(SwingConstants.CENTER);
		lblSbnEngne.setBounds(11, 37, 606, 106);
		frame.getContentPane().add(lblSbnEngne);
		
	}
}