package view;

import java.awt.GridLayout;

import javax.swing.*;

@SuppressWarnings("serial")
public class ChooseLoginDialog extends JDialog {
	
	private JButton login, createUser;
	private MainWindow mainwindow;
	
	public ChooseLoginDialog(MainWindow mainwindow) {
		this.mainwindow = mainwindow;
		init();
		initComponents();
		addComponents();

	}
	
	private void init() {
		setModal(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 150);
		setLayout(new GridLayout(1,2));
		setResizable(false);
		setLocationRelativeTo(mainwindow);
	}
	
	private void initComponents() {
		login = new JButton("Login");
		createUser = new JButton("User erstellen");
	}
	
	private void addComponents() {
		add(login);
		add(createUser);
	}
	

}
