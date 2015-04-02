package org.guidevtools.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.guidevtools.GUIComponentWatcher;

public class Simple extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel nameLabel;
	
	private JLabel passwordLabel;
	
	private JTextField name;
	
	private JPasswordField password;
	
	Simple(){
		init();
	}

	private void init() {
		JMenuBar jMenuBar = createTopMenu();
		setJMenuBar(jMenuBar);
		add(createInputPanel());
		add(createBottomButton(), BorderLayout.SOUTH);
		
		setSize(300, 200);
		setLocation(300, 200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private JMenuBar createTopMenu() {
		JMenuBar jMenuBar = new JMenuBar();
		JMenu jMenu = new JMenu("文件");
		jMenu.add(new JMenuItem("打开"));
		jMenu.add(new JMenuItem("关闭"));
		jMenuBar.add(jMenu);
		return jMenuBar;
	}
	
	
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.setPreferredSize(new Dimension(300, 150));
		nameLabel = new JLabel("name:");
		panel.add(nameLabel);
		name = new JTextField();
		panel.add(name);
		passwordLabel = new JLabel("password:");
		panel.add(passwordLabel);
		password = new JPasswordField();
		panel.add(password);
		
		return panel;
	}

	private JPanel createBottomButton() {
		JPanel panel = new JPanel();
		
		JButton confirm = new JButton("confirm");
		confirm.setPreferredSize(new Dimension(80, 25));
		confirm.addActionListener(e -> {
			new GUIComponentWatcher(this);
		});
		
		JButton cancel = new JButton("cancel");
		cancel.setPreferredSize(new Dimension(80, 25));
		cancel.addActionListener(e -> {
			this.dispose();
		});
		
		panel.add(confirm);
		panel.add(cancel);
		
		return panel;
	}
	
	public static void main(String[] args) {
		new Simple();
	}
}
