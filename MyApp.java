package edu.neu.csye6200.ca;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.util.logging.Logger;


public class MyApp extends CAApp {
	private JFrame mainFrame;
	private JPanel controlPanel;
	static CACanvas caCanvas;
	
	static JComboBox<String> ruleList;
	private JLabel ruleLabel;
	static JButton startBtn;
	static JButton stopBtn;
	static JButton pauseBtn;
	static JButton restartBtn;
	private JLabel genLabel;
	static JTextField genNumField;
	
	private CAGenerationSet caGen;
	private CARule caRule;
	static int rowAmount;
	static int ruleIndex;
	static Logger log;
	
	
	static boolean stop  = false;
	static boolean suspend = false;
	static boolean exit = false;
	static boolean redraw = false;

	public MyApp(){
		this.caGen = new CAGenerationSet();
		this.caRule = new CARule();
		setLogger();
		initMainFrame();
		log.info("MyApp Constructed!");
	}
	
	@Override
	void initMainFrame() {
		//initialize the main frame
		mainFrame = new JFrame();
		mainFrame.setTitle("Cellular Automation");
		
		//set layout of the main frame
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(initControlPanel(), BorderLayout.NORTH);
		
		caCanvas = new CACanvas();
		mainFrame.add(caCanvas, BorderLayout.CENTER);
		
		
		
		//set properties of main frame
		mainFrame.setSize(750, 800);
		mainFrame.setResizable(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
	}


	@Override
	JPanel initControlPanel() {
		controlPanel = new JPanel();
		
		ruleLabel = new JLabel("CA Rules: ");
		ruleList = new JComboBox<String>();
		ruleList.addItem("Rule_90");
		ruleList.addItem("Rule_222");
		ruleList.addItem("Custom_Rule");
		ruleList.setSelectedIndex(0);
		
		startBtn = new JButton("Start");
		stopBtn = new JButton("Stop");
		pauseBtn = new JButton("Pause");
		restartBtn = new JButton("Restart");
		restartBtn.setEnabled(false);
		pauseBtn.setEnabled(false);
		stopBtn.setEnabled(false);
		genLabel = new JLabel("Row Generation: ");
		genNumField = new JTextField();
		genNumField.setColumns(2);
		
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(ruleLabel);
		controlPanel.add(ruleList);
		controlPanel.add(genLabel);
		controlPanel.add(genNumField);
		controlPanel.add(startBtn);
		controlPanel.add(stopBtn);
		controlPanel.add(pauseBtn);
		controlPanel.add(restartBtn);
		
		
		ruleList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ruleIndex = ruleList.getSelectedIndex();
				
			}
		});
		
		genNumField.addCaretListener(new CaretListener(){
			public void  caretUpdate(CaretEvent e){
				try {
					if(genNumField.getText().isEmpty()){
						rowAmount = 0;
						genLabel.setText("Must input a number!");
					}else{
						rowAmount  = Integer.parseInt(genNumField.getText());
						if(rowAmount > 64){
							genLabel.setText("must be less than 65");
						}else{
							genLabel.setText("Total row number is ");
						}
					}
				}catch(NumberFormatException efx){
					System.out.println("Error: please input a number !");
				}
			}
		});
		
		startBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				CACanvas.state = true;
				stop = false;
				startBtn.setEnabled(false);
				ruleList.setEnabled(false);
				genNumField.setEnabled(false);
				stopBtn.setEnabled(true);
				pauseBtn.setEnabled(true);
				try{
				Thread t = new Thread(caCanvas);
				t.start();
				log.info("Press Start!");
				}catch(IllegalThreadStateException ex){
					log.severe("Thread State Exception!");
				}
			}
		});
		
		stopBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pauseBtn.setEnabled(false);
				restartBtn.setEnabled(false);
				stopBtn.setEnabled(false);
				startBtn.setEnabled(true);
				ruleList.setEnabled(true);
				genNumField.setEnabled(true);
				stop = true;
				log.info("Press Stop");
			}
		});
		
		pauseBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pauseBtn.setEnabled(false);
				restartBtn.setEnabled(true);
				suspend = true;
				log.info("Press Pause");
			}
		});
		
		restartBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				restartBtn.setEnabled(false);
				pauseBtn.setEnabled(true);
				synchronized(caCanvas){
					suspend = false;
					try{
						caCanvas.notify();
					}catch(IllegalMonitorStateException ex){
						log.severe("nofity illegal minitor state exception");
					}
				}
				log.info("Press Restart");
			}
		});
		return controlPanel;
	}
	
	@Override
	public void setLogger() {
		log = Logger.getLogger(MyApp.class.getName());
		
	}

	public static void main(String[] args){
		
		MyApp app = new MyApp();
		
		
	}

		
}



	

