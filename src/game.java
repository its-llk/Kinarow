
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.sound.sampled.*;



public class game extends JFrame  {
	
	JFrame frame = new JFrame();
	JLabel label = new JLabel();
	MyButton [][] arr ;
	JButton rematch ;
	int turn;
	int [] win_arr;
	int k;
	int winner_y;
	int winner_x;
	int sys;
	int [] current;
	ImageIcon noicon = new ImageIcon ("empty.png");
	Timer timer;
	Timer timer1;
	Timer timer2;
	Timer timer3;
	int music_flag;
	Clip clip;
	int time2wait;
	
	game(final int k,int r,final int c, int system){
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(1000, 1000);
		//retarting_timer
		timer=null;
		timer1=null;
		timer2=null;
		timer3=null;
		//reatarting_time2wait
		time2wait=200;
		
		//restoring_winner_data
		winner_x=-1;
		winner_y=-1;
		//restarting current
		this.current=new int[c];
		for(int ac=0;ac<this.current.length;ac++)
			this.current[ac]=r-1;
		//music_flag
		music_flag=0;
		
		this.sys=system;	//select type of system: pvp || cvp || cvc
		
		turn = (int)(Math.random() * 2); //choose turn 
		
		//add label
		label.setText("turn of player "+(turn+1));
		label.setFont(new Font(null,Font.PLAIN,25));
		label.setPreferredSize(new Dimension(100,100));
		label.setHorizontalAlignment(JLabel.CENTER);
		
		//add rematch button
		rematch =new JButton ("rematch");
		rematch.addActionListener(new AL (-1,-1));
		rematch.setEnabled(false);
		rematch.setPreferredSize(new Dimension(100,100));
		//add matrices size of r*c
		arr = new MyButton [r][c];
		for(int i =0;i<r;i++){
			for(int j =0;j<c;j++){
				
				arr[i][j]=new MyButton(noicon.getImage());
				arr[i][j].addActionListener(new AL(i,j));
				arr[i][j].setEnabled(false);
				arr[i][j].setBackground(Color.WHITE);
			
			}
		}
		//init_win_arr
		win_arr= new int[r*c];
		init_win_arr();
		
		this.k=k;
		if(sys!=2){	
			for(int j =0;j<c;j++){
				arr[r-1][j].setEnabled(true);
			}
		}	
		//if the turn is cmp and its cmp_VS_player
		if(sys==1 && turn==0){
			make_movment(arr[0].length/2,arr.length-1,1);
			arr[arr.length-1][arr[0].length/2].setBackground(Color.YELLOW);
			arr[arr.length-2][arr[0].length/2].setEnabled(true);
			
			label.setText("turn of player "+(turn+1));
			current[arr[0].length/2]--;
				}
		
		//add panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(r,c,2,2));
		panel.setBackground(Color.GRAY);
		panel.setPreferredSize(new Dimension(100,100));
		
		
		for(int i =0;i<r;i++){
			for(int j =0;j<c;j++){
			panel.add(arr[i][j]);	
			}
		}
		
		
		
		frame.add(label,BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);
		frame.add(rematch,BorderLayout.SOUTH);
		frame.setVisible(true);
		
		
		
		
		//cmp_VS_cmp
		if(sys==2){
			
			 new Thread(new Runnable() {
				   public void run() {
					   int flag=0;
					   int randomflag=0;
					   int random=0;
					   while(flag==0){
					   try {
						  Thread.sleep((int)(time2wait*k));
						  label.setText("turn of player "+(turn+1));
						  if(turn==0){//red turn
							  
							  turn=1;
							  if(randomflag>k-2) {
									 
								   	 flag =cmp_play(0);
								   	 if(flag!=0){
								   		 Thread.sleep((int)(200*k)); 
								   		 
										  print_mat();
										  return;
									  }	
							  	}
							  else {
								  randomflag++;
								  random=(int) Math.random()*c;
								  arr[current[random]][random].setBackground(Color.YELLOW);
								  make_movment(  random,current[random],0);
								  current[random]--;
							  }
							  print_mat();
						  }
						  else{//yellow turn
							  
							  turn=0;
							  if(randomflag>k-2) {
							  
							  flag =cmp_play(1);
							  if(flag!=0){
								
								  print_mat();
								  return;
							  }
							 
							  }
							  else {
								  randomflag++;
								  random=(int) Math.random()*c;
								  arr[current[random]][random].setBackground(Color.RED);
								  make_movment(  random,current[random],0);
								  current[random]--;
								  
							  	}
							  print_mat();
						  }
						  
						  label.setText("turn of player "+(turn+1));
						  Thread.sleep((int)(time2wait*k/2));
						  
					   		}
		             catch(InterruptedException ex) {}
					   }
						
		         }
		         }).start();		
			
			
			
			
			
			
			
			
		}
		
		
	}
	//draw
public boolean draw (){
	
	for(int i=0;i<arr.length;i++){
		for(int j=0;j<arr[0].length;j++){
			if(arr[i][j].getBackground()==Color.white){
					return false;
			}
		}
	}
	return true;
}
public int hookie(int a, int b,Color enemy) {
	if(a>arr.length-1 || a<0 || b>arr[0].length-1 || b<0 || a+1<arr.length  &&arr[a+1][b].getBackground()==Color.WHITE )
		return 0;
	if(arr[a][b].getBackground() == enemy)
		return 0;
	
	return 2;
	
}
	
	///cmp_algorithem_2
	public int grades1 (int a,int b){
	int grade=0;
	
	if(a-1>-1 && arr[a-1][b].getBackground()!=Color.yellow)
		grade+=2;
	if(a+1<arr.length && arr[a+1][b].getBackground()!=Color.RED)
		grade+=winrows( a+1, b, Color.red)+1;
	if(b-1>-1 && arr[a][b-1].getBackground()!=Color.yellow)
		grade+=wincolumns( a, b-1, Color.red)+1;
	if(b+1<arr[0].length && arr[a][b+1].getBackground()!=Color.yellow)
		grade+=wincolumns( a, b+1, Color.red)+1;
	if(b-1>-1 && a-1>-1 && arr[a-1][b-1].getBackground()!=Color.yellow)
		grade+=windiagonal( a-1, b-1, Color.red)+1;
	if(b+1<arr[0].length &&  a+1<arr.length && arr[a+1][b+1].getBackground()!=Color.yellow)
		grade+=windiagonal( a+1, b+1, Color.red)+1;
	if(b+1<arr[0].length && a-1>-1 && arr[a-1][b+1].getBackground()!=Color.yellow)
		grade+=winundiagonal( a-1, b+1, Color.red)+1;
	if(b-1>-1 &&  a+1<arr.length && arr[a+1][b-1].getBackground()!=Color.yellow)
		grade+=winundiagonal( a+1, b-1, Color.red)+1;
	
	
	//row win possible check
	if(hookie(a-1,b,Color.yellow)!=0 &&  winrows(a, b, Color.red)+winrows(a-1, b, Color.WHITE)>=k){
		grade+=winrows(a, b, Color.red);
		//System.out.println("row win posibble for yellow");
	}
	//cullomn win possible check
	int x=b,y=a;
	while(hookie(y,x-1,Color.yellow)!=0	&& arr[y][x-1].getBackground()==Color.RED){
		x--;
	}
	if(hookie(y,x-1,Color.yellow)!=0 && wincolumns(y, x, Color.RED)+wincolumns(y, x-1, Color.WHITE)>k ||	hookie(y,x+wincolumns(y, x, Color.RED),Color.yellow)!=0 &&	wincolumns(y, x, Color.RED)	+ wincolumns(y,x+wincolumns(y, x, Color.RED),Color.red)>k  )
			if(hookie(y,x-1,Color.yellow)!=0 && hookie(y,x+wincolumns(y, x, Color.RED),Color.yellow)!=0 && wincolumns(y, x, Color.RED)+wincolumns(y, x-1, Color.WHITE)+ wincolumns(y, x+wincolumns(y, x, Color.RED), Color.WHITE)>k &&  wincolumns(y, x, Color.RED)>=k-2){
				grade+=100;
				//System.out.println("better cullomn case could happen for yellow ");
			}
			else{
				grade+=wincolumns(y, x, Color.RED);
				//System.out.println("cullomn win posibble for yellow");
			}
	//diagonal case posible
	 x=b;
	 y=a;
	while(hookie(y-1,x-1,Color.yellow)!=0	&& arr[y-1][x-1].getBackground()==Color.RED){
		x--;
		b--;
	}
	if(hookie(y-1,x-1,Color.yellow)!=0 &&  windiagonal(y, x, Color.RED)+windiagonal(y-1, x-1, Color.WHITE)>k || hookie(y+windiagonal(y, x, Color.RED),x+windiagonal(y, x, Color.RED),Color.yellow)!=0 &&  windiagonal(y, x, Color.RED)+windiagonal(y+windiagonal(y, x, Color.RED), x+windiagonal(y, x, Color.RED), Color.WHITE)>k )
		if(hookie(y-1,x-1,Color.yellow)!=0 && hookie(y+windiagonal(y, x, Color.RED),x+windiagonal(y, x, Color.RED),Color.yellow)!=0 && windiagonal(y, x, Color.RED)+windiagonal(y-1, x-1, Color.WHITE)+ windiagonal(y+windiagonal(y, x, Color.RED), x+windiagonal(y, x, Color.RED), Color.WHITE)>k &&  windiagonal(y, x, Color.RED)>=k-2){
			grade+=100;
			//System.out.println("better diagonal case could happen for yellow ");
		}
		else{
			grade+=wincolumns(y, x, Color.RED);
			//System.out.println("diagonal win posibble for yellow");
		}
	
	//undiagonal casee possible
	 x=b;
	 y=a;
	while(hookie(y+1,x-1,Color.yellow)!=0	&& arr[y+1][x-1].getBackground()==Color.RED){
		x--;
		b++;
	}
	if(hookie(y+1,x-1,Color.yellow)!=0 &&  winundiagonal(y, x, Color.RED)+winundiagonal(y+1, x-1, Color.WHITE)>k || hookie(y-winundiagonal(y, x, Color.RED),x+winundiagonal(y, x, Color.RED),Color.yellow)!=0 &&  winundiagonal(y, x, Color.RED)+winundiagonal(y-winundiagonal(y, x, Color.RED), x+winundiagonal(y, x, Color.RED), Color.WHITE)>k )
		if(hookie(y+1,x-1,Color.yellow)!=0 && hookie(y-winundiagonal(y, x, Color.RED),x+winundiagonal(y, x, Color.RED),Color.yellow)!=0 && winundiagonal(y, x, Color.RED)+winundiagonal(y+1, x-1, Color.WHITE)+ winundiagonal(y-winundiagonal(y, x, Color.RED), x+winundiagonal(y, x, Color.RED), Color.WHITE)>k && winundiagonal(y, x, Color.RED)>=k-2 ){
			grade+=100;
			//System.out.println("better diagonal case could happen for yellow ");
		}
		else{
			grade+=wincolumns(y, x, Color.RED);
			//System.out.println("diagonal win posibble for yellow");
		}
	
	
	
	return grade;
}
	public int maVhu_up1  (int a,int b,int ary[]){
	
	ary[b*3+2]=b;
	ary[b*3+1]=a;
	arr[a][b].setBackground(Color.RED);
	if(a>0 && win(a, b, Color.RED)>=k-1){
		arr[a-1][b].setBackground(Color.RED);
		if(win(a-1, b, Color.RED)>=k ){
			arr[a][b].setBackground(Color.YELLOW);
			if(win(a-1, b, Color.RED)>=k ) {
				ary[b*3]=400;
				//System.out.println("for yellow********");
			}
			arr[a][b].setBackground(Color.RED);
		}
		arr[a-1][b].setBackground(Color.WHITE);
	}
	ary[b*3]=grades1(a,b);
	//if we close to win
	if(this.k<=win(a,b,Color.RED)){
		
		ary[b*3]+=10000;
		for(int aly=0;aly<arr.length;aly++){
			for(int avy=0;avy<arr[0].length;avy++){
				arr[aly][avy].setEnabled(false);
			}
		}
		winner_x=b;
		winner_y=a;
		label.setText("winner "+(turn+1));
		rematch.setEnabled(true);
		rematch.setForeground(Color.RED);
		return 2;
	}
	
	
	//if enemy win
	arr[a][b].setBackground(Color.yellow);
	if(this.k<=win(a,b,Color.yellow)){
		arr[a][b].setBackground(Color.WHITE);
		winner_x=b;
		winner_y=a;
		return 1;
	}
	
	//cant win a row
	int x=b,y=a;
	while(x>-1 && arr[y][x].getBackground()==Color.yellow){
		x--;
	
	}
	
		
	if(x>-1){
		
		if(arr[a][x].getBackground()==Color.WHITE && x+winrows(a,b,Color.yellow)<arr[0].length-1 && arr[a][x+winrows(a,b,Color.yellow)+1].getBackground()==Color.WHITE && winrows(a,x+1,Color.yellow)>this.k-2 )
			ary[b*3]+=100;
	}
	//cant win a diagonal
	x=b;
	y=a;	
	while(x>-1 && y>-1 && arr[y][x].getBackground()==Color.yellow){
		x--;
		y--;
	}
		
	if(x>-1 && y>-1)
		if(arr[y][x].getBackground()==Color.WHITE && x+windiagonal(a,b,Color.yellow)<arr[0].length-1 && y+windiagonal(a,b,Color.yellow)<arr.length-1 && arr[y+windiagonal(a,b,Color.yellow)+1][ x+windiagonal(a,b,Color.yellow)+1].getBackground()==Color.WHITE && windiagonal(a,b,Color.yellow)>this.k-2 )
			ary[b*3]+=100;
	//cant win a undiagonal
	x=b;
	y=a;	
	while(x<arr[0].length && y>-1 && arr[y][x].getBackground()==Color.yellow){
		x++;
		y--;
	}
		
	if(x<arr[0].length && y>-1)
		if(arr[y][x].getBackground()==Color.WHITE && arr[0].length-x-winundiagonal(a,b,Color.yellow)>0 && y+winundiagonal(a,b,Color.yellow)<arr.length-1 && arr[y+winundiagonal(a,b,Color.yellow)+1][ arr[0].length-x-winundiagonal(a,b,Color.yellow)].getBackground()==Color.WHITE && winundiagonal(a,b,Color.yellow)>this.k-2 )
			ary[b*3]+=100;
		

	
	
	
	arr[a][b].setBackground(Color.WHITE);
	
	
	//if we do it enemy will win
	if(a>0){
		
		arr[a-1][b].setBackground(Color.yellow);
		if(this.k<=win(a-1,b,Color.yellow)){
		ary[b*3]+=-600;
		
		}
		arr[a-1][b].setBackground(Color.WHITE);
	}
	
	//System.out.println("RED: derog="+ary[b*3]+" y= "+ary[b*3+1]+" x= "+ary[b*3+2]);
	return 0;
}
	
	//init_win_arr
	public void init_win_arr() {
	
		for(int stam=0;stam<win_arr.length;stam++) {
			win_arr[stam]=-1;
		}
	}


	//cmp_algorithem

	public int grades (int a,int b){
		int grade=0;
		if(a-1>-1 && arr[a-1][b].getBackground()!=Color.RED)
			grade+=2;
		if(a+1<arr.length && arr[a+1][b].getBackground()!=Color.RED)
			grade+=winrows( a+1, b, Color.yellow)+1;
		if(b-1>-1 && arr[a][b-1].getBackground()!=Color.RED)
			grade+=wincolumns( a, b-1, Color.yellow)+1;
		if(b+1<arr[0].length && arr[a][b+1].getBackground()!=Color.RED)
			grade+=wincolumns( a, b+1, Color.yellow)+1;
		if(b-1>-1 && a-1>-1 && arr[a-1][b-1].getBackground()!=Color.RED)
			grade+=windiagonal( a-1, b-1, Color.yellow)+1;
		if(b+1<arr[0].length &&  a+1<arr.length && arr[a+1][b+1].getBackground()!=Color.RED)
			grade+=windiagonal( a+1, b+1, Color.yellow)+1;
		if(b+1<arr[0].length && a-1>-1 && arr[a-1][b+1].getBackground()!=Color.RED)
			grade+=winundiagonal( a-1, b+1, Color.yellow)+1;
		if(b-1>-1 &&  a+1<arr.length && arr[a+1][b-1].getBackground()!=Color.RED)
			grade+=winundiagonal( a+1, b-1, Color.yellow)+1;
		
		
//		row win possible check
		if(hookie(a-1,b,Color.RED)!=0 &&  winrows(a, b, Color.yellow)+winrows(a-1, b, Color.WHITE)>=k){
			grade+=winrows(a, b, Color.yellow)*2;
			//System.out.println("row win posibble for red");
		}
		//cullomn win possible check
		int x=b,y=a;
		while(hookie(y,x-1,Color.RED)!=0	&& arr[y][x-1].getBackground()==Color.RED){
			x--;
		}
		if(hookie(y,x-1,Color.RED)!=0 && wincolumns(y, x, Color.yellow)+wincolumns(y, x-1, Color.WHITE)>k ||	hookie(y,x+wincolumns(y, x, Color.yellow),Color.RED)!=0 &&	wincolumns(y, x, Color.yellow)	+ wincolumns(y,x+wincolumns(y, x, Color.yellow),Color.yellow)>k  )
				if(hookie(y,x-1,Color.RED)!=0 && hookie(y,x+wincolumns(y, x, Color.yellow),Color.RED)!=0 && wincolumns(y, x, Color.yellow)+wincolumns(y, x-1, Color.WHITE)+ wincolumns(y, x+wincolumns(y, x, Color.yellow), Color.WHITE)>k &&  wincolumns(y, x, Color.yellow)>=k-2){
					grade+=100;
					//System.out.println("better cullomn case could happen for red ");
				}
				else{
					grade+=wincolumns(y, x, Color.yellow)*2;
					//System.out.println("cullomn win posibble for red");
				}
		//diagonal case posible
		 x=b;
		 y=a;
		while(hookie(y-1,x-1,Color.RED)!=0	&& arr[y-1][x-1].getBackground()==Color.yellow){
			x--;
			b--;
		}
		if(hookie(y-1,x-1,Color.RED)!=0 &&  windiagonal(y, x, Color.yellow)+windiagonal(y-1, x-1, Color.yellow)>k || hookie(y+windiagonal(y, x, Color.yellow),x+windiagonal(y, x, Color.yellow),Color.RED)!=0 &&  windiagonal(y, x, Color.yellow)+windiagonal(y+windiagonal(y, x, Color.yellow), x+windiagonal(y, x, Color.yellow), Color.WHITE)>k )
			if(hookie(y-1,x-1,Color.RED)!=0 && hookie(y+windiagonal(y, x, Color.yellow),x+windiagonal(y, x, Color.yellow),Color.RED)!=0 && windiagonal(y, x, Color.yellow)+windiagonal(y-1, x-1, Color.yellow)+ windiagonal(y+windiagonal(y, x, Color.yellow), x+windiagonal(y, x, Color.yellow), Color.WHITE)>k &&  windiagonal(y, x, Color.yellow)>=k-2){
				grade+=100;
				//System.out.println("better diagonal case could happen for red ");
			}
			else{
				grade+=wincolumns(y, x, Color.yellow)*2;
				//System.out.println("diagonal win posibble for red");
			}
		
		//undiagonal casee possible
		 x=b;
		 y=a;
		while(hookie(y+1,x-1,Color.RED)!=0	&& arr[y+1][x-1].getBackground()==Color.yellow){
			x--;
			b++;
		}
		if(hookie(y+1,x-1,Color.RED)!=0 &&  winundiagonal(y, x, Color.yellow)+winundiagonal(y+1, x-1, Color.WHITE)>k || hookie(y-winundiagonal(y, x, Color.yellow),x+winundiagonal(y, x, Color.yellow),Color.RED)!=0 &&  winundiagonal(y, x, Color.yellow)+winundiagonal(y-winundiagonal(y, x, Color.yellow), x+winundiagonal(y, x, Color.yellow), Color.WHITE)>k )
			if(hookie(y+1,x-1,Color.RED)!=0 && hookie(y-winundiagonal(y, x, Color.yellow),x+winundiagonal(y, x, Color.yellow),Color.RED)!=0 && winundiagonal(y, x, Color.yellow)+winundiagonal(y+1, x-1, Color.WHITE)+ winundiagonal(y-winundiagonal(y, x, Color.yellow), x+winundiagonal(y, x, Color.yellow), Color.WHITE)>k && winundiagonal(y, x, Color.yellow)>=k-2 ){
				grade+=100;
				//System.out.println("better diagonal case could happen for red ");
			}
			else{
				grade+=wincolumns(y, x, Color.yellow)*2;
				//System.out.println("diagonal win posibble for red");
			}
		
		
		
		return grade;
	}
	public int maVhu_up  (int a,int b,int ary[],int flag1){
		
		ary[b*3+2]=b;
		ary[b*3+1]=a;
		arr[a][b].setBackground(Color.yellow);
		if(a>0 && win(a, b, Color.yellow)>=k-1){
			arr[a-1][b].setBackground(Color.yellow);
			if(win(a-1, b, Color.yellow)>=k ){
				arr[a][b].setBackground(Color.red);
				if(win(a-1, b, Color.yellow)>=k ) {
					ary[b*3]=400;
					//System.out.println("for yellow********");
				}
				arr[a][b].setBackground(Color.yellow);
			}
			arr[a-1][b].setBackground(Color.WHITE);
		}
		if(flag1==2) {
			ary[b*3]=grades(a,b);
			arr[a][b].setBackground(Color.WHITE);
		}
		if(flag1==0 ) {
			arr[a][b].setBackground(Color.red);
			ary[b*3]=grades(a,b)-1*cmp_play(2);
			arr[a][b].setBackground(Color.WHITE);
			//System.out.println("enemy point "+cmp_play(2)+" our now points "+ary[b*3]);
		}
		if(flag1==1 ) {
			arr[a][b].setBackground(Color.yellow);
			ary[b*3]=grades(a,b)-1*cmp_play(3);
			arr[a][b].setBackground(Color.WHITE);
			//System.out.println("enemy point "+cmp_play(2)+" our now points "+ary[b*3]);
		}
		//if we close to win
		arr[a][b].setBackground(Color.yellow);
		if(this.k<=win(a,b,Color.yellow)){
			if(flag1==1 || flag1==2) {
				arr[a][b].setBackground(Color.WHITE);
				ary[b*3]+=800;
				return 0;
			}
			 winner_x=b;
			 winner_y=a;
			System.out.println("sapuse win 1");
			//ary[b*3]+=10000;
			for(int aly=0;aly<arr.length;aly++){
				for(int avy=0;avy<arr[0].length;avy++){
					arr[aly][avy].setEnabled(false);
				}
			}
			label.setText("winner "+(turn+1));
			rematch.setEnabled(true);
			rematch.setForeground(Color.RED);
			return 2;
		}
		
		
		//if enemy win
		arr[a][b].setBackground(Color.RED);
		if(this.k<=win(a,b,Color.RED)){
			arr[a][b].setBackground(Color.WHITE);
			System.out.println("sapuse block 1");
			 winner_x=b;
			 winner_y=a;
			return 1;
		}
		
		//cant win a row
		int x=b,y=a;
		while(x>-1 && arr[y][x].getBackground()==Color.RED){
			x--;
		
		}
		
			
		if(x>-1){
			
			if(arr[a][x].getBackground()==Color.WHITE && x+winrows(a,b,Color.RED)<arr[0].length-1 && arr[a][x+winrows(a,b,Color.RED)+1].getBackground()==Color.WHITE && winrows(a,x+1,Color.RED)>this.k-2 )
				ary[b*3]+=100;
		}
		//cant win a diagonal
		x=b;
		y=a;	
		while(x>-1 && y>-1 && arr[y][x].getBackground()==Color.RED){
			x--;
			y--;
		}
			
		if(x>-1 && y>-1)
			if(arr[y][x].getBackground()==Color.WHITE && x+windiagonal(a,b,Color.RED)<arr[0].length-1 && y+windiagonal(a,b,Color.RED)<arr.length-1 && arr[y+windiagonal(a,b,Color.RED)+1][ x+windiagonal(a,b,Color.RED)+1].getBackground()==Color.WHITE && windiagonal(a,b,Color.RED)>this.k-2 )
				ary[b*3]+=100;
		//cant win a undiagonal
		x=b;
		y=a;	
		while(x<arr[0].length && y>-1 && arr[y][x].getBackground()==Color.RED){
			x++;
			y--;
		}
			
		if(x<arr[0].length && y>-1)
			if(arr[y][x].getBackground()==Color.WHITE && arr[0].length-x-winundiagonal(a,b,Color.RED)>0 && y+winundiagonal(a,b,Color.RED)<arr.length-1 && arr[y+winundiagonal(a,b,Color.RED)+1][ arr[0].length-x-winundiagonal(a,b,Color.RED)].getBackground()==Color.WHITE && winundiagonal(a,b,Color.RED)>this.k-2 )
				ary[b*3]+=100;
			

			
		
		
		arr[a][b].setBackground(Color.WHITE);
		
		
		//if we do it enemy will win
		if(a>0){
			
			arr[a-1][b].setBackground(Color.RED);
			if(this.k<=win(a-1,b,Color.RED)){
			ary[b*3]+=-600;
			
			}
			arr[a-1][b].setBackground(Color.WHITE);
		}
		System.out.println("YELLOW: derog="+ary[b*3]+" y= "+ary[b*3+1]+" x= "+ary[b*3+2]);
		return 0;
	}

	//winning algorithem
	
	public int win (int y,int x,Color c){
		
		int a=0,b=0;
		a=Math.max(winrows( y, x, c),wincolumns( y, x, c));
		b=Math.max(windiagonal( y, x, c),winundiagonal( y, x, c));
		return (Math.max(a,b));
		
		
	}
	public int winrows (int y,int x,Color c){
		int cnt=0,flag=0;
		while(x>0 && arr[y][x].getBackground()==c) {
			x--;
			flag=1;
			
		}
		if(arr[y][x].getBackground()!=c && flag==1) {
			x++;
		}
	
		while(x<arr[0].length && arr[y][x].getBackground()==c) {
			
			x++;
			cnt++;
			
		}
		
		return cnt;
	}
	public int wincolumns (int y,int x,Color c){
		int cnt=0;
		while(y<arr.length && arr[y][x].getBackground()==c) {
			y++;
			cnt++;
		}
		return cnt;
	}
	public int windiagonal (int y,int x,Color c){
	int cnt=0,flag=0;
	while(x<arr[0].length-1 && y<arr.length-1 && arr[y][x].getBackground()==c) {
		x++;
		y++;
		flag=1;
		
	}
	if(arr[y][x].getBackground()!=c && flag==1) {
		x--;
		y--;
	}
	
	while(x>=0 && y>=0 && arr[y][x].getBackground()==c) {
	
		x--;
		y--;
		cnt++;
		
	}
	
	return cnt;

	}
	public int winundiagonal (int y,int x,Color c){
		int cnt=0,flag=0;
		while(x>0 && y<arr.length-1 && arr[y][x].getBackground()==c) {
			x--;
			y++;
			flag=1;
			
		}
		if(arr[y][x].getBackground()!=c&&  flag==1) {
			x++;
			y--;
		}
	

		while(x<arr[0].length && y>=0 && arr[y][x].getBackground()==c) {
			
			x++;
			y--;
			cnt++;
			
		}
		
		return cnt;
	}
	
	//print_mat
	public void print_mat() {
		for(int i=0;i<arr.length;i++) {
			for(int j=0;j<arr[0].length;j++) {
				if(arr[i][j].getBackground()==Color.RED)
					System.out.print("2");
				else if(arr[i][j].getBackground()==Color.YELLOW)
					System.out.print("1");
				else
					System.out.print("0");
			}
			System.out.println(" ");
		}
	}
	
	//cmp turn
	public int cmp_play(int flag){
				
				int [] ary = new int [current.length*3];
				
				int z1=0,z=0,f=0,f1=0,c=0;
				for( int a=0;a<current.length;a++){
					if(current[a]>-1)
						
						
						
						
						
						if(flag==0)
							z=maVhu_up(current[a],a,ary,0);
						else if(flag==1)
							z1=maVhu_up1(current[a],a,ary);
						else if(flag==2)
							z=maVhu_up(current[a],a,ary,1);
						else if(flag==3)
							z=maVhu_up(current[a],a,ary,2);
					 if(z==2  || z1==2 ){
						if(flag==2 || flag==3)
							return 800;
						
						 background_mim(winner_x, winner_y,arr[winner_y][winner_x].getBackground());
						 
						 print_mat();
						 new Thread(new Runnable() {
							   public void run() {
								   make_movment(winner_x,winner_y,0);
									try {
										Thread.sleep(150*arr.length-winner_y);
									} catch (InterruptedException e) {}
									 if_win(winner_y,winner_x);
							   }
							   }).start();
						
						 return 1;
					 }
					 if(z==1 && current[a]>-1  || z1==1 && current[a]>-1 ){
						 c++;
						 if(flag==1)
								
						 f=a;
						 f1=1;
						
					 }
					
					 }
				 if(f1==1){
					 if(flag==2 || flag==3) {
					 		if (c==1)
					 			return 80;
					 		else
					 			return - (int)(Math.pow(100, c));
					 	}
					 	System.out.println(winner_x+" "+winner_y);
					 	if(flag==0)
							arr[winner_y][winner_x].setBackground(Color.YELLOW);
					 	else if(flag==1)
					 		arr[winner_y][winner_x].setBackground(Color.RED);
					 	System.out.println(winner_x+" "+winner_y);
						make_movment(winner_x,winner_y,0);
						
						arr[winner_y][winner_x].setEnabled(true);
						if(winner_y>0)
							arr[winner_y-1][winner_x].setEnabled(true);
						background_mim(winner_x, winner_y,arr[winner_y][winner_x].getBackground());
						current[winner_x]--;
						
						
						return 0;
					 
					 
				 }
					 
				
				int max=-100000,param=-1000;
				for(int x=0;x<arr[0].length;x++){
				
					if(max<ary[x*3] && current[ary[x*3+2]]>-1){
						max=ary[x*3];
						param=x*3;
						winner_y=current[ary[x*3+2]];
					}
					
					
				}
				System.out.println("max1= "+max+" y= "+current[ary[param+2]]+" x= "+ary[param+2]);
				if(flag==2 || flag==3)
					 return max;
				
				make_movment(ary[param+2],current[ary[param+2]],0);
				if (flag==0)
					arr[current[ary[param+2]]][ary[param+2]].setBackground(Color.YELLOW);
				else{
					arr[current[ary[param+2]]][ary[param+2]].setBackground(Color.RED);
					//System.out.println("max= "+max+" y= "+current[ary[param+2]]+" x= "+ary[param+2]);
				}
				background_mim(ary[param+2], current[ary[param+2]],arr[current[ary[param+2]]][ary[param+2]].getBackground());
				current[ary[param+2]]--;
			
				
				if(draw()){
					
					label.setText("draw");
					rematch.setEnabled(true);
					rematch.setForeground(Color.blue);
					return 2;
				
				}
			
				if(ary[param+1]>0){
					arr[ary[param+1]-1][ary[param+2]].setEnabled(true);
				}
				return 0;
				
		}
	
	
	
	public void untuchable(int f,int x){
		if (f==0){
		for(int i=0;i<current.length;i++){
			if(current[i]>=0)
				arr[current[i]][i].setEnabled(false);
			
		}
		if(current[x]<arr.length-1)
			arr[current[x]+1][x].setEnabled(false);
		}
		else{
			for(int i=0;i<current.length;i++){
				if(current[i]>=0)
					arr[current[i]][i].setEnabled(true);
				
			}
			if(current[x]<arr.length-1)
				arr[current[x]+1][x].setEnabled(true);
		}
	}
	public void make_movment( final int col ,final int row,final int f){
		
		new Thread(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				if(f==1)
					untuchable(0,col);
				Image img;
				if(turn==0){
					ImageIcon icon = new ImageIcon ("liam2.png");
					img=icon.getImage();
				}
				else{
					ImageIcon icon = new ImageIcon ("haim2.png");
					img=icon.getImage();
				}
					
				for(int i=0;i<row;i++){
					arr[i][col].setImg(img);
					arr[i][col].repaint();
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {}
					
					arr[i][col].setImg(noicon.getImage());
					arr[i][col].repaint();
				}
				if(f==1)
					untuchable(1,col);
				if(turn==0){
					ImageIcon icon1 = new ImageIcon ("liam1.png");
					img=icon1.getImage();
				}
				else{
					ImageIcon icon1 = new ImageIcon ("haim1.png");
					img=icon1.getImage();
				}
				arr[row][col].setImg(img);
				arr[row][col].repaint();
			}
			
		}).start();
		
	}
	public void if_win(int a, int b) {
		Image img;
		if(turn==0){
			ImageIcon icon = new ImageIcon ("loser.gif");
			img=icon.getImage();
		}
		else{
			ImageIcon icon = new ImageIcon ("haim.gif");
			img=icon.getImage();
		}	
		
		int x=b;
		int y=a;
		int i=0;
		
		int flag=0;
		//rows_paint_gif
		if(wincolumns(a, b, arr[a][b].getBackground())>=k) {
			
			//System.out.println(hookie(a, b,arr[a][b].getBackground()));
			while(hookie(y, x,Color.WHITE)!=0 && arr[y][x].getBackground()==arr[a][b].getBackground()){
				arr[y][x].setImg(img);
				arr[y][x].repaint();
				win_arr[i++]=y;
				win_arr[i++]=x;
				flag=1;
				y++;
			}
			if (flag==1) {
				
				timer = new Timer(100,new AL1(win_arr));
				timer.start();
			}
				
		}
		x=b;
		y=a;
		i=0;
		flag=0;
		//culomns_paint_gif
		if(winrows(a, b, arr[a][b].getBackground())>=k) {
			init_win_arr();
			while(hookie(y, x-1,Color.WHITE)!=0 && arr[y][x-1].getBackground()==arr[a][b].getBackground())
				x--;
			while(hookie(y, x,Color.WHITE)!=0 && arr[y][x].getBackground()==arr[a][b].getBackground()) {
				arr[y][x].setImg(img);
				arr[y][x].repaint();
				win_arr[i++]=y;
				win_arr[i++]=x;
				x++;
				flag=1;
			}
			if (flag==1) {

				timer1 = new Timer(100,new AL1(win_arr));
				timer1.start();
			}
		}
		x=b;
		y=a;
		i=0;
		flag=0;
		//diagonal_paint_gif
		if(windiagonal(a, b, arr[a][b].getBackground())>=k) {
			init_win_arr();
			while(hookie(y-1, x-1,Color.WHITE)!=0 && arr[y-1][x-1].getBackground()==arr[a][b].getBackground()){
				x--;
				y--;
			}
			while(hookie(y, x,Color.WHITE)!=0 && arr[y][x].getBackground()==arr[a][b].getBackground()){
				arr[y][x].setImg(img);
				arr[y][x].repaint();
				win_arr[i++]=y;
				win_arr[i++]=x;
				x++;
				y++;
				flag=1;
			}
			if (flag==1) {

				timer2 = new Timer(100,new AL1(win_arr));
				timer2.start();
			}
		}
		x=b;
		y=a;
		i=0;
		flag=0;
		//undiagonal_paint_gif
		if(winundiagonal(a, b, arr[a][b].getBackground())>=k) {
			init_win_arr();
			while(hookie(y+1, x-1,Color.WHITE)!=0 && arr[y+1][x-1].getBackground()==arr[a][b].getBackground()){
			
				x--;
				y++;
				}
			while(hookie(y, x,Color.WHITE)!=0 && arr[y][x].getBackground()==arr[a][b].getBackground()){
				arr[y][x].setImg(img);
				arr[y][x].repaint();
				win_arr[i++]=y;
				win_arr[i++]=x;
				x++;
				y--;
				flag=1;
				}
			if (flag==1) {
				timer3 = new Timer(100,new AL1(win_arr));
				timer3.start();
			}
			}		
		
		
	}
	
	//background_mim	
	public void background_mim (int x,int y,Color cl ){
		if(music_flag==0){	
			int c=0,f=1;
			if(win(y,x,cl)>=k){
				time2wait=800;
				music_flag++;
				music();
				return ;
			}
			
			for(int i=0;i<current.length;i++){
				
				if(current[i]>0 ){
					arr[current[i]-1][i].setBackground(cl);
					arr[current[i]][i].setBackground(Color.WHITE);
					if(win(current[i]-1,i,cl)<k){
						f=0;
					}
					arr[current[i]-1][i].setBackground(Color.WHITE);
					arr[current[i]][i].setBackground(cl);
				}
				if(current[i]>-1 ){
					f=0;
					if(win(current[i],i,cl)>=k){
						c++;
						if(	current[i]>0){
							arr[current[i]][i].setBackground(Color.WHITE);
							arr[current[i]-1][i].setBackground(cl);
							if(win(current[i]-1,i,cl)>=k)
							c++;
							arr[current[i]][i].setBackground(cl);
							arr[current[i]-1][i].setBackground(Color.WHITE);
						}
					}
						
					arr[current[i]][i].setBackground(Color.WHITE);
				}
			}
			arr[y][x].setBackground(cl);
			if (c>=2 || f!=0 ){
				
				time2wait=800;
				music_flag++;
				music();
			}
		}
	}
	public void music(){
		File file = new File ("win_theme.wav");
		try {
			AudioInputStream audio =AudioSystem.getAudioInputStream(file);
			clip= AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		}
		 catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public class AL1 implements ActionListener {
		int [] win_arr1;
		public AL1 (int [] arr1 ) {
			win_arr1=new int [arr1.length];
			for(int i=0;i<arr1.length;i++)
				win_arr1[i]=arr1[i];
		}
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			
			for(int i=0;win_arr1[i]!=-1;i+=2) {
				arr[win_arr1[i]][win_arr1[i+1]].repaint();
				
			}
		}

		
	}
	
	public class AL implements ActionListener {
		int x;
		int y;
		public AL (int y, int x) {
			this.x=x;
			this.y=y;
		}
	
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		
					if(x>-1 && arr[y][x].getImg()==noicon.getImage())	{
						
						if(sys==0){	//Player VS Player
							current[x]--;
							if(turn==0){
							
								
							
								make_movment(x,y,1);
								turn++;
									
							
								arr[y][x].setBackground(Color.YELLOW);
							}
							else{
								
						
						
								
								make_movment(x,y,1);
								turn--;
								arr[y][x].setBackground(Color.RED);
							}
							background_mim(x,y,arr[y][x].getBackground());
							new Thread(new Runnable() {
								   public void run() {
									   
										   	try {
												Thread.sleep(150*arr[0].length);
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
								  
										//if win all butttons are not able to click and print winner
											if(win( y, x,arr[y][x].getBackground())>k-1){
												for(int aly=0;aly<arr.length;aly++){
													for(int avy=0;avy<arr[0].length;avy++){
														arr[aly][avy].setEnabled(false);
													}
												}
													label.setText("winner "+(turn+1));
														rematch.setEnabled(true);
														rematch.setForeground(Color.RED);
													
														
													
														if_win(y,x);
														return;
												}
											//if draw all butttons are not able to click and print draw
												if(draw()){
														label.setText("draw");
															rematch.setEnabled(true);
																rematch.setForeground(Color.blue);
																
																	return;
													}
												
														label.setText("turn of player "+(turn+1));
												
												if(y>0)
													arr[y-1][x].setEnabled(true);
						 }
			         }).start();
										
										}	
						//Player VS Computer
						else if(sys==1){
							
								
								turn=1;
								untuchable(0, x);
								make_movment(x,y,0);
								arr[y][x].setBackground(Color.RED);
								current[x]=y-1;
								print_mat();
								background_mim(x,y,arr[y][x].getBackground());
								//if win all butttons are not able to click and print winner
								new Thread(new Runnable() {
									   public void run() {
										   
											   	try {
													Thread.sleep(170*(arr[0].length));
												} catch (InterruptedException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												if(win( y, x,arr[y][x].getBackground())>k-1){
													for(int aly=0;aly<arr.length;aly++){
														for(int avy=0;avy<arr[0].length;avy++){
															arr[aly][avy].setEnabled(false);
														}
													}
													label.setText("winner "+(turn+1));
													rematch.setEnabled(true);
													rematch.setForeground(Color.RED);
													
													if_win(y,x);
													return;
												}
												if(draw()){
													label.setText("draw");
													rematch.setEnabled(true);
													rematch.setForeground(Color.blue);
													return;
												}
												
			
											   	turn=0;
											   	int f=0;
												label.setText("turn of player "+(turn+1));
											   	f=cmp_play(0);
											   if(f==1)
												   return;
											   	try {
													Thread.sleep(time2wait*(arr[0].length-winner_y));
												} catch (InterruptedException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												untuchable(1, x);
										   		
							           
							         }
							         }).start();		
								turn=1;
								label.setText("turn of player "+(turn+1));
								
							}
						
						
		
					}
					else if(e.getSource()==rematch && x==-1){
						
						clip.close();
						if (timer !=null)
							timer.stop();
						if (timer1 !=null)
							timer1.stop();
						if (timer2 !=null)
							timer2.stop();
						if (timer3 !=null)
							timer3.stop();
						insertV newinsertV = new insertV(arr.length,arr[0].length,k);
						frame.setEnabled(false);
					}
					else{
						label.setText("hey,cheating is wrong dont do it again");
							
						}
			
			}
					
						
						

		
			
	
	
	
	
	
	}
	
	
}





















