import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class insertV extends JFrame implements ActionListener {
	int t;
	JFrame frame= new JFrame(); 
	JButton button;
	JButton sumbitBT;
	JTextField tfk;
	JTextField tfr;
	JTextField tfc;
	JPanel panelup;
	JPanel paneldown;
	JLabel label;
	JRadioButton pvp;
	JRadioButton cvp;
	JRadioButton cvc;
	
	public insertV(int rr ,int cc ,int kk){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(2,1));
		//add label for output
		label=new JLabel();
		label.setText("want to change stats?");
		label.setHorizontalTextPosition(JLabel.CENTER);
		
		//add text files to input k
		tfk=new JTextField(String.valueOf(kk));
		tfk.setPreferredSize(new Dimension(250,40));
		
		//add text files to input r
		tfr=new JTextField(String.valueOf(rr));
		tfr.setPreferredSize(new Dimension(250,40));
		
		//add text files to input c
		tfc=new JTextField(String.valueOf(cc));
		tfc.setPreferredSize(new Dimension(250,40));
		//add game button 
		button = new JButton("insert values");
		button.setBounds(200, 100, 100, 50);
		button.addActionListener(this);
		button.setFocusable(false);
		button.setBackground(Color.pink);
		button.setEnabled(false);
		
		//add button sumbit
		sumbitBT = new JButton("sumbit");
		sumbitBT.addActionListener(this);
		sumbitBT.setBackground(Color.green);
		//add radio buttons
		 pvp= new JRadioButton("player vs player");
		 cvp= new JRadioButton("computer vs player");
		 cvc= new JRadioButton("computer vs computer");
		ButtonGroup group= new ButtonGroup();
		pvp.setSelected(true);
		this.t=0;
		group.add(pvp);
		group.add(cvp);
		group.add(cvc);
		pvp.addActionListener(this);
		cvp.addActionListener(this);
		cvc.addActionListener(this);
		
		
		// add pannel up
		panelup = new JPanel();
		panelup.setBounds(0,0,frame.getWidth(), (int)(frame.getHeight()*0.25));
		panelup.setLayout(new GridLayout());
		panelup.setBackground(Color.white);
		panelup.add(label);
		panelup.add(sumbitBT);
		panelup.add(button);
		//add pannel down
		paneldown = new JPanel();
		paneldown.setBounds((int)(frame.getWidth()*0.25),(int)(frame.getHeight()*0.25),(int)(frame.getWidth()*0.50), (int)(frame.getHeight()*0.75));
		paneldown.setLayout(new GridLayout(3,2));
		paneldown.setBackground(Color.white);
		paneldown.add(tfk);
		paneldown.add(pvp);
		paneldown.add(tfc);
		paneldown.add(cvp);
		paneldown.add(tfr);
		paneldown.add(cvc);
		
		
		frame.add(panelup);
		frame.add(paneldown);
		
		frame.setSize(700, 250);    
		frame.setVisible(true);
	
		
	}
	
	insertV(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(2,1));
		//add label for output
		label=new JLabel();
		label.setText("type k then rows then columns");
		label.setHorizontalTextPosition(JLabel.CENTER);
		
		//add text files to input k
		tfk=new JTextField("enter k");
		tfk.setPreferredSize(new Dimension(250,40));
		
		//add text files to input r
		tfr=new JTextField("enter rows");
		tfr.setPreferredSize(new Dimension(250,40));
		
		//add text files to input c
		tfc=new JTextField("enter columns");
		tfc.setPreferredSize(new Dimension(250,40));
		//add game button 
		button = new JButton("insert values");
		button.setBounds(200, 100, 100, 50);
		button.addActionListener(this);
		button.setFocusable(false);
		button.setBackground(Color.pink);
		button.setEnabled(false);
		
		//add button sumbit
		sumbitBT = new JButton("sumbit");
		sumbitBT.addActionListener(this);
		sumbitBT.setBackground(Color.green);
		//add radio buttons
		 pvp= new JRadioButton("player vs player");
		 cvp= new JRadioButton("computer vs player");
		 cvc= new JRadioButton("computer vs computer");
		ButtonGroup group= new ButtonGroup();
		pvp.setSelected(true);
		this.t=0;
		group.add(pvp);
		group.add(cvp);
		group.add(cvc);
		pvp.addActionListener(this);
		cvp.addActionListener(this);
		cvc.addActionListener(this);
		
		
		// add pannel up
		panelup = new JPanel();
		panelup.setBounds(0,0,frame.getWidth(), (int)(frame.getHeight()*0.25));
		panelup.setLayout(new GridLayout());
		panelup.setBackground(Color.white);
		panelup.add(label);
		panelup.add(sumbitBT);
		panelup.add(button);
		//add pannel down
		paneldown = new JPanel();
		paneldown.setBounds((int)(frame.getWidth()*0.25),(int)(frame.getHeight()*0.25),(int)(frame.getWidth()*0.50), (int)(frame.getHeight()*0.75));
		paneldown.setLayout(new GridLayout(3,2));
		paneldown.setBackground(Color.white);
		paneldown.add(tfk);
		paneldown.add(pvp);
		paneldown.add(tfc);
		paneldown.add(cvp);
		paneldown.add(tfr);
		paneldown.add(cvc);
		
		
		frame.add(panelup);
		frame.add(paneldown);
		
		frame.setSize(700, 250);    
		frame.setVisible(true);
	
		
	}
	
	
	//function that check if the numbers are good for program
	public boolean is_ok (int k, int r,int c) {
		if((r-2)<k ) {
			label.setText("k should be 2 point under rows");
			return false;
		}
		if((c-2)<k ) {
			label.setText("k should be 2 point under columns");
			return false;
		}
		if(k<3) {
			label.setText("k should be above 3 included");
			return false;
		}
		if(k>7) {
			label.setText("k should be under 7 included");
			return false;
		}
		return true;
	}
	
	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int k=0,r=0,c=0;
		if(e.getSource()==button){
			k=Integer.parseInt(tfk.getText());
			r=Integer.parseInt(tfr.getText());
			c=Integer.parseInt(tfc.getText());
			if(cvp.isSelected())
				this.t=1;
			if(cvc.isSelected())
				this.t=2;
			frame.setEnabled(false);
			game game1 = new game(k,r,c,this.t);
			frame.setVisible(false);
			
		}
		if(e.getSource()==sumbitBT){
			boolean f=true;
			//try catch that make sure that is digit
				try {
					k=Integer.parseInt(tfk.getText());
					r=Integer.parseInt(tfr.getText());
					c=Integer.parseInt(tfc.getText());
					
				}
				catch(	Exception eror) {
					f=false;
					
					label.setText("only nunbers");
				}
			if(f==true && is_ok(k, r, c)) {
				tfk.enable(false);
				tfr.enable(false);
				tfc.enable(false);
				
			label.setText("good");
			button.setEnabled(true);
			button.setBackground(Color.GREEN);
			}
		}
	
	
	}

}
