package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import org.apache.commons.lang3.StringUtils;

import java.lang.*;

import model.User;
import model.service.UserModelService;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog {
	
	private MainWindow mainwindow;
	private JTextField tfUsername;
	private JPanel footer, content;
	private JButton bConfirm;
	private JLabel lUsername;
	
	private boolean confirmed;
	
	private User user;
	

	public LoginDialog(MainWindow mainwindow) {
		this.mainwindow = mainwindow;
		init();
		initComponents();
		addComponents();
		addListeners();
	}
	
	private void init() {
		setModal(true);
		setSize(400, 150);
		setTitle("Login");
		setLayout(new BorderLayout());
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(mainwindow);
	}
	
	private void initComponents() {
		
		content = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbcs = new GridBagConstraints();
		gbcs.fill = GridBagConstraints.HORIZONTAL;
		
		lUsername = new JLabel("Username: ");
		gbcs.gridx = 0;
		gbcs.gridy = 0;
		gbcs.gridwidth = 1;
		content.add(lUsername, gbcs);
		
		tfUsername = new JTextField(20);
		gbcs.gridx = 1;
		gbcs.gridy = 0;
		gbcs.gridwidth = 2;
		content.add(tfUsername, gbcs);
		
		footer = new JPanel();
		bConfirm = new JButton("Login");
	}
	
	private void addComponents() {
		footer.add(bConfirm);
		add(content);
		add(footer, BorderLayout.SOUTH);
	}
	
	private void addListeners() {
		bConfirm.addActionListener(e -> {

			String username = tfUsername.getText();
			if (!StringUtils.isEmpty(username)) {
				if (isUserValid(tfUsername.getText())) {
					mainwindow.setUser(user);
					confirmed = true;
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Sorry, User " + tfUsername.getText() + " not found.", "Achtung!", 
							JOptionPane.INFORMATION_MESSAGE);
					confirmed = false;
				}
			} else {
				JOptionPane.showMessageDialog(this, "Bitte geben Sie einen Namen ein", "Achtung!", 
						JOptionPane.INFORMATION_MESSAGE);
				confirmed = false;
			}
		});
	}

	private void closeMainWindow() {
		try {
			mainwindow.saveClose(mainwindow);
		} catch (IllegalArgumentException e) {
			e.getMessage();
		}
	}
	
	private boolean isUserValid(String username) {
		UserModelService userService = new UserModelService(mainwindow.getDbPath());
		user = userService.getUserByUserName(username);
		return user != null;	
	}
	
	public boolean isConfirmed() {
		return confirmed;
	}
}
