package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

import javax.swing.JSlider;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Klasa koja predstavlja GUI i komunikaciju sa klijentom. Sadrzi main metodu i omogucava pokretanje aplikacije.
 * Poziva metodu iz klase Klijent izracunaj na osnovu popunjenih polja u GUIu
 * @author neverne bede
 * @version 1.1
 */
public class KlijentGUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblIzaberiteValutu;
	private JComboBox cmbValuta;
	private JLabel lblKonvertuj;
	private JComboBox cmbKonvert;
	private JLabel lblIznos;
	private JTextField iznos;
	private JButton btnIzracunaj;
	private JLabel lblRezultat;
	private JTextField rezultat;

	Klijent klijent = new Klijent();
	private JLabel lblGreska;
	private JSlider slider;
	private JLabel lblIzaberiteIznos;
	
	static  String[] nazivi = { "RSD", "EUR", "USD", "CAD", "GBP", "RUB", "BAM",
			"SEK", "AUD" };
	private JButton btnIzaberi;
	
	/**
	 * Main metoda za pokretanje aplikacije
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KlijentGUI frame = new KlijentGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * Kreira se prozor
	 */
	public KlijentGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int o = JOptionPane.showOptionDialog(null, 
				        "Da li zaista zelite da napustite Web Menjacnicu?", 
				        "Izlazak iz Web Menjacnice", 
				        JOptionPane.OK_CANCEL_OPTION, 
				        JOptionPane.INFORMATION_MESSAGE, 
				        null, 
				        new String[]{"Da", "Ne"},
				        "default");
				if (o == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});
		setResizable(false);
		setTitle("Web Menjacnica");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 528, 378);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblIzaberiteValutu());
		contentPane.add(getCmbValuta());
		contentPane.add(getLblKonvertuj());
		contentPane.add(getCmbKonvert());
		contentPane.add(getLblIznos());
		contentPane.add(getIznos());
		contentPane.add(getBtnIzracunaj());
		contentPane.add(getLblRezultat());
		contentPane.add(getRezultat());
		contentPane.add(getLblGreska());
		contentPane.add(getSlider());
		contentPane.add(getLblIzaberiteIznos());
		contentPane.add(getBtnIzaberi());
	}
	private JLabel getLblIzaberiteValutu() {
		if (lblIzaberiteValutu == null) {
			lblIzaberiteValutu = new JLabel("Izaberite valutu");
			lblIzaberiteValutu.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
			lblIzaberiteValutu.setBounds(28, 37, 180, 51);
		}
		return lblIzaberiteValutu;
	}
	private JComboBox getCmbValuta() {
		
		if (cmbValuta == null) {
			cmbValuta = new JComboBox();
			cmbValuta.setModel(new DefaultComboBoxModel(new String[] {"RSD", "EUR", "USD", "CAD", "GBP", "RUB", "BAM", "SEK", "AUD"}));
			cmbValuta.setBounds(28, 83, 116, 26);
			
		}
		return cmbValuta;
	}
	private JLabel getLblKonvertuj() {
		if (lblKonvertuj == null) {
			lblKonvertuj = new JLabel("Konvertuj");
			lblKonvertuj.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
			lblKonvertuj.setBounds(358, 44, 116, 36);
		}
		return lblKonvertuj;
	}
	private JComboBox getCmbKonvert() {
		if (cmbKonvert == null) {
			cmbKonvert = new JComboBox();
			cmbKonvert.setEnabled(false);
			cmbKonvert.setBounds(358, 83, 116, 26);
		
		}
		return cmbKonvert;
	}
	private JLabel getLblIznos() {
		if (lblIznos == null) {
			lblIznos = new JLabel("Iznos");
			lblIznos.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
			lblIznos.setBounds(28, 136, 86, 30);
		}
		return lblIznos;
	}
	private JTextField getIznos() {
		if (iznos == null) {
			iznos = new JTextField();
			iznos.setBounds(28, 166, 115, 20);
			iznos.setColumns(10);
		}
		return iznos;
	}
	/**
	 * Button koji poziva metodu izracunaj klase Klijent i prikazuje odgovor u polju "rezultat"
	 * U slucaju da metoda vrati String koji pocinje sa "Morate", postavlja lblGreska na vidljivo i korisniku se ispisuje poruka o gresci
	 * @exception 
	 */
	private JButton getBtnIzracunaj() {
		if (btnIzracunaj == null) {
			btnIzracunaj = new JButton("Izracunaj");
			btnIzracunaj.setEnabled(false);
			btnIzracunaj.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String valuta = getCmbValuta().getSelectedItem().toString();
					String valutaKonvert = getCmbKonvert().getSelectedItem().toString();
					String iznos = getIznos().getText();
					
					cmbValuta.setEnabled(true);
					cmbKonvert.setEnabled(false);
					btnIzracunaj.setEnabled(false);
					
					try {
						String resenje = klijent.izracunaj(valuta, valutaKonvert, iznos);
						if(resenje.startsWith("Morate"))
							lblGreska.setVisible(true);
						else {
							lblGreska.setVisible(false);
							rezultat.setText(resenje);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
										
					
				}
			});
			btnIzracunaj.setBounds(195, 156, 117, 40);
		}
		return btnIzracunaj;
	}
	private JLabel getLblRezultat() {
		if (lblRezultat == null) {
			lblRezultat = new JLabel("Rezultat");
			lblRezultat.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
			lblRezultat.setBounds(358, 144, 98, 14);
		}
		return lblRezultat;
	}
	private JTextField getRezultat() {
		if (rezultat == null) {
			rezultat = new JTextField();
			rezultat.setBounds(359, 166, 115, 20);
			rezultat.setColumns(10);
		}
		return rezultat;
	}
	private JLabel getLblGreska() {
		if (lblGreska == null) {
			lblGreska = new JLabel("Morate uneti broj!");
			lblGreska.setForeground(Color.RED);
			lblGreska.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblGreska.setBounds(28, 190, 144, 26);
			lblGreska.setVisible(false);
		}
		return lblGreska;
	}
	private JSlider getSlider() {
		if (slider == null) {
			slider = new JSlider();
			slider.setBackground(Color.WHITE);
			slider.setMajorTickSpacing(200);
			slider.setPaintLabels(true);
			slider.setPaintTicks(true);
			slider.setMinorTickSpacing(100);
			slider.setMaximum(1000);
			slider.setValue(500);
			slider.setToolTipText("");
			slider.setBounds(87, 251, 346, 51);
			slider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					iznos.setText(""+slider.getValue());
				}
			});
		}
		return slider;
	}
	private JLabel getLblIzaberiteIznos() {
		if (lblIzaberiteIznos == null) {
			lblIzaberiteIznos = new JLabel("Izaberite iznos");
			lblIzaberiteIznos.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
			lblIzaberiteIznos.setBounds(211, 226, 117, 14);
		}
		return lblIzaberiteIznos;
	}
	private JButton getBtnIzaberi() {
		if (btnIzaberi == null) {
			btnIzaberi = new JButton("Izaberi");
			btnIzaberi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cmbKonvert.removeAllItems();
					for(int i = 0; i < nazivi.length;i++) {
						if(!getCmbValuta().getSelectedItem().equals(nazivi[i])) {
							cmbKonvert.addItem(nazivi[i]);
						}
					}
					cmbKonvert.setEnabled(true);
					cmbValuta.setEnabled(false);
					btnIzracunaj.setEnabled(true);
				}
			});
			btnIzaberi.setBounds(48, 112, 74, 23);
		}
		return btnIzaberi;
	}
}
