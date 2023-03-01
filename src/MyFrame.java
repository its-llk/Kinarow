import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MyFrame extends JFrame implements ActionListener {
	JButton button;
	JFrame frame= new JFrame(); 
	MyFrame(){
		button = new JButton("insert values");
		button.setBounds(200, 100, 100, 50);
		button.addActionListener(this);
		button.setFocusable(false);
		button.setBackground(Color.lightGray);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setBackground(Color.WHITE);
		frame.add(button);
		
		
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==button){
			frame.setEnabled(false);
			insertV newinsertV = new insertV();
			frame.setVisible(false);
		}
		
	}

}
