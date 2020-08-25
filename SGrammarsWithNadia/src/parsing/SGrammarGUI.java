package parsing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SGrammarGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtNT;
	private JTextField txtT;
	private JButton btnAddTerminals;
	private JTextField txtPR;
	private JTextField txtNTV;
	private JLabel label;
	private JLabel lblNonTerminalVariable;
	private JLabel lblProductionRule;
	private JButton btnAddRule;
	private JButton btnCheckSgrammar;
	private JTextField txtCheckString;
	private JButton btnCheckString;
	private JLabel lblDescription;
	private JLabel lblSeperateEachVarible;
	private JLabel lblNonterminalEg;
	private JLabel lblEgS;
	private JLabel lblEgXab;

	/**
	 * Launch the application.
	 */
	Set<NonTerminal> V = new HashSet<NonTerminal>();
	Set<Terminal> T = new HashSet<Terminal>();
	List<Rule> rules = new ArrayList<Rule>();
	NonTerminal startSymbol;
	private JLabel lblValid;
	private JLabel lblString;
	
	public static void main(String[] args) {
		

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SGrammarGUI frame = new SGrammarGUI();
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
	public SGrammarGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_heading = new JLabel("Solving s-grammars with Nadia");
		lbl_heading.setForeground(new Color(0, 51, 102));
		lbl_heading.setFont(new Font("Ubuntu", Font.BOLD, 21));
		lbl_heading.setBounds(12, -14, 576, 53);
		contentPane.add(lbl_heading);
		
		txtNT = new JTextField();
		txtNT.setBounds(32, 77, 311, 19);
		contentPane.add(txtNT);
		txtNT.setColumns(10);
		
		txtT = new JTextField();
		txtT.setBounds(32, 108, 311, 19);
		contentPane.add(txtT);
		txtT.setColumns(10);
		
		JButton btnAddNonterminals = new JButton("add nonterminals");
		btnAddNonterminals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sV=txtNT.getText();
				List<String> items = Arrays.asList(sV.split("\\s*,\\s*"));
				for(int i=0; i<items.size(); i++) {
					if(items.get(i).equals("S")) {
						startSymbol= new NonTerminal("S");
						V.add(startSymbol);
						startSymbol.makeStartVar();
					}else {
						NonTerminal Temp = new NonTerminal(items.get(i));
						V.add(Temp);	
					}
				}
				txtNT.setText("");
			}
		});
		btnAddNonterminals.setFont(new Font("Ubuntu", Font.BOLD, 14));
		btnAddNonterminals.setForeground(new Color(0, 51, 102));
		btnAddNonterminals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddNonterminals.setBounds(368, 74, 170, 25);
		contentPane.add(btnAddNonterminals);
		
		btnAddTerminals = new JButton("add terminals");
		btnAddTerminals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sT = txtT.getText();
				List<String> items = Arrays.asList(sT.split("\\s*,\\s*"));
				for(int i=0; i<items.size(); i++) {
					Terminal Temp = new Terminal(items.get(i));
					T.add(Temp);
				}
				txtT.setText("");
				
			}
		});
		btnAddTerminals.setFont(new Font("Ubuntu", Font.BOLD, 14));
		btnAddTerminals.setForeground(new Color(0, 51, 102));
		btnAddTerminals.setBounds(368, 102, 170, 25);
		contentPane.add(btnAddTerminals);
		
		txtPR = new JTextField();
		txtPR.setBounds(316, 214, 243, 19);
		contentPane.add(txtPR);
		txtPR.setColumns(10);
		
		txtNTV = new JTextField();
		txtNTV.setBounds(32, 214, 243, 19);
		contentPane.add(txtNTV);
		txtNTV.setColumns(10);
		
		label = new JLabel("->");
		label.setBounds(293, 216, 70, 15);
		contentPane.add(label);
		
		lblNonTerminalVariable = new JLabel("Non Terminal Variable");
		lblNonTerminalVariable.setFont(new Font("Ubuntu", Font.BOLD, 14));
		lblNonTerminalVariable.setForeground(new Color(0, 51, 102));
		lblNonTerminalVariable.setBounds(32, 197, 183, 15);
		contentPane.add(lblNonTerminalVariable);
		
		lblProductionRule = new JLabel("Production Rule");
		lblProductionRule.setFont(new Font("Ubuntu", Font.BOLD, 14));
		lblProductionRule.setForeground(new Color(0, 51, 102));
		lblProductionRule.setBounds(320, 197, 207, 15);
		contentPane.add(lblProductionRule);
		
		btnAddRule = new JButton("add rule");
		btnAddRule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nT= txtNTV.getText();
				String pR=txtPR.getText();
				NonTerminal n= new NonTerminal(nT);
				List<String> items = Arrays.asList(pR.split("\\s*\\s*"));
				List<Symbol> sList = new ArrayList<Symbol>();
				List<Symbol> sList2 = new ArrayList<Symbol>();
				
				for(int i=0; i<items.size(); i++) {
					char c =items.get(i).charAt(0);

					if(items.get(i).equalsIgnoreCase("|")) {
							i++;
							int start = i;
							for(int j=start ; j<items.size(); j++) {
								char c1 =items.get(i).charAt(0);
	
								
								if(Character.isUpperCase(c1)) {
									NonTerminal symb = new NonTerminal(items.get(i));
									sList2.add(symb);
								}else {
									Terminal symb = new Terminal(items.get(i));
									sList2.add(symb);
								}
								
							}
						
						break;
					}
					if(Character.isUpperCase(c)) {
						NonTerminal symb = new NonTerminal(items.get(i));
						sList.add(symb);
					}else {
						Terminal symb = new Terminal(items.get(i));
						sList.add(symb);
					}
					Rule ru = new Rule(n, sList);
					rules.add(ru);
	
					if(sList2.isEmpty()==false) {
						Rule ru2 = new Rule(n, sList2);
						rules.add(ru2);
	
					}
					
				
				
			}
				txtNTV.setText("");
				txtPR.setText("");
			}
				
		});
		btnAddRule.setFont(new Font("Ubuntu", Font.BOLD, 14));
		btnAddRule.setForeground(new Color(0, 51, 102));
		btnAddRule.setBounds(237, 245, 117, 25);
		contentPane.add(btnAddRule);
		
		btnCheckSgrammar = new JButton("check s-grammar");
		btnCheckSgrammar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (NonTerminal nV : V) {
					for (Rule rule : rules) {
						if (rule.getValue().getName().equals(nV.getName())) {
							nV.addToRules(rule);
		                }
		            }
		        }
				Parser p= new Parser(V,T,rules,startSymbol);
				p.getFirstSets();
				p.fillTable();
				if(p.isSGrammar==true) {
					lblValid.setText("s-grammar is valid! :)");
				}else {
					lblValid.setText("s-grammar is not valid! :(");
				}
				
			}
		});
		btnCheckSgrammar.setFont(new Font("Ubuntu", Font.BOLD, 14));
		btnCheckSgrammar.setForeground(new Color(0, 51, 102));
		btnCheckSgrammar.setBounds(219, 282, 154, 25);
		contentPane.add(btnCheckSgrammar);
		
		txtCheckString = new JTextField();
		txtCheckString.setBounds(32, 357, 348, 19);
		contentPane.add(txtCheckString);
		txtCheckString.setColumns(10);
		
		btnCheckString = new JButton("check string");
		btnCheckString.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (NonTerminal nV : V) {
					for (Rule rule : rules) {
						if (rule.getValue().getName().equals(nV.getName())) {
							nV.addToRules(rule);
		                }
		            }
		        }
				String text = txtCheckString.getText();
				Parser p= new Parser(V,T,rules,startSymbol);
				p.getFirstSets();
				p.fillTable();
				
				//System.out.println(p.isStringValid(text));
				if(p.isStringValid(text)==true) {
					lblString.setText("String is valid! :)");
				}else {
					lblString.setText("String is not valid! :(");
				}
				
				
			}
		});
		btnCheckString.setFont(new Font("Ubuntu", Font.BOLD, 14));
		btnCheckString.setForeground(new Color(0, 51, 102));
		btnCheckString.setBounds(421, 354, 154, 25);
		contentPane.add(btnCheckString);
		
		lblDescription = new JLabel("Enter a list of Nonterminal and Terminal variables.");
		lblDescription.setFont(new Font("Ubuntu", Font.BOLD, 16));
		lblDescription.setForeground(new Color(0, 51, 102));
		lblDescription.setBounds(32, 51, 556, 15);
		contentPane.add(lblDescription);
		
		lblSeperateEachVarible = new JLabel("Seperate each varible using a comma \",\"");
		lblSeperateEachVarible.setFont(new Font("Ubuntu", Font.BOLD, 14));
		lblSeperateEachVarible.setForeground(new Color(0, 51, 102));
		lblSeperateEachVarible.setBounds(32, 128, 366, 32);
		contentPane.add(lblSeperateEachVarible);
		
		lblNonterminalEg = new JLabel("Nonterminal e.g :  S, A, B     or      Terminal e.g: x, y, + ");
		lblNonterminalEg.setFont(new Font("Ubuntu", Font.BOLD, 14));
		lblNonterminalEg.setForeground(new Color(0, 51, 102));
		lblNonterminalEg.setBounds(32, 153, 411, 32);
		contentPane.add(lblNonterminalEg);
		
		lblEgS = new JLabel("E.g: S");
		lblEgS.setFont(new Font("Ubuntu", Font.BOLD, 14));
		lblEgS.setForeground(new Color(0, 51, 102));
		lblEgS.setBounds(42, 228, 70, 25);
		contentPane.add(lblEgS);
		
		lblEgXab = new JLabel("E.g : xAB");
		lblEgXab.setForeground(new Color(0, 51, 102));
		lblEgXab.setFont(new Font("Ubuntu", Font.BOLD, 14));
		lblEgXab.setBounds(494, 224, 70, 36);
		contentPane.add(lblEgXab);
		
		lblValid = new JLabel("   ");
		lblValid.setFont(new Font("Ubuntu", Font.BOLD, 14));
		lblValid.setForeground(new Color(255, 51, 102));
		lblValid.setBounds(219, 319, 329, 15);
		contentPane.add(lblValid);
		
		lblString = new JLabel("");
		lblString.setFont(new Font("Ubuntu", Font.BOLD, 14));
		lblString.setForeground(new Color(255, 51, 102));
		lblString.setBounds(219, 404, 329, 15);
		contentPane.add(lblString);
	}
}
