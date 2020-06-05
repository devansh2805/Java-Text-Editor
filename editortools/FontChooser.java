package editortools;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.Font;

public class FontChooser implements ActionListener, ListSelectionListener {
	private JTextPane fontChooserTextPaneReference;
	private JDialog fontChooserDialog;
	private JPanel fontChooserPanel;
	private JButton okbtn, cancelbtn;
	private JList<Object> fontlist, sizelist;
	private JScrollPane fontscr, sizescr, prevscr;
	private StyledDocument fcsd;
	private JTextArea prevarea;
	private JLabel fontNameLabel, fontSizeLabel, previewAreaLabel;
	private String fontname = "Arial", fontsize = "15";
	private boolean fontnameflag, fontsizeflag;

	public FontChooser(JTextPane tp, JFrame fr) {
		fontChooserTextPaneReference = tp;
		fontChooserDialog = new JDialog(fr);
		fontChooserPanel = new JPanel();
		okbtn = new JButton("Apply Font Changes");
		okbtn.addActionListener(this);
		cancelbtn = new JButton("Cancel");
		cancelbtn.addActionListener(this);

		fontlist = new JList<Object>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		fontscr = new JScrollPane(fontlist);
		fontlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fontlist.addListSelectionListener(this);

		String[] sizesdata = { "8", "9", "10", "11", "12", "15", "18", "24", "28", "32", "36", "48", "54", "60", "66", "72" };
		sizelist = new JList<Object>(sizesdata);
		sizelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sizelist.addListSelectionListener(this);
		sizescr = new JScrollPane(sizelist);

		fcsd = fontChooserTextPaneReference.getStyledDocument();
		prevarea = new JTextArea();
		prevscr = new JScrollPane(prevarea);
		fontNameLabel = new JLabel("Fonts:");
		fontSizeLabel = new JLabel("Sizes:");
		previewAreaLabel = new JLabel("Preview:");
		fontnameflag = fontsizeflag = false;
	}

	public void showFontsAndFontSizes() {
		try {
			ImageIcon proNoteLogo = new ImageIcon(ImageIO.read(getClass().getResource("PRONOTE_ICON.png")));
			fontChooserDialog.setIconImage(proNoteLogo.getImage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(fontChooserDialog, "Error Loading Icon", "Error", JOptionPane.ERROR_MESSAGE);
		}
		fontChooserDialog.setTitle("Font Name and Font Size Selection");
		fontNameLabel.setBounds(20, 10, 150, 20);
		fontChooserPanel.add(fontNameLabel);
		fontscr.setBounds(20, 40, 150, 150);
		fontChooserPanel.add(fontscr);
		fontSizeLabel.setBounds(220, 10, 150, 20);
		fontChooserPanel.add(fontSizeLabel);
		sizescr.setBounds(220, 40, 120, 150);
		fontChooserPanel.add(sizescr);
		previewAreaLabel.setBounds(20, 200, 150, 20);
		fontChooserPanel.add(previewAreaLabel);
		prevarea.setText("AaBbCcDd......xXyYzZ");
		prevarea.setEditable(false);
		prevarea.setLineWrap(true);
		prevarea.setFont(new Font("Arial", Font.PLAIN, 15));
		prevscr.setBounds(20, 220, 320, 80);
		fontChooserPanel.add(prevscr);
		okbtn.setBounds(40, 320, 130, 30);
		okbtn.setFocusPainted(false);
		fontChooserPanel.add(okbtn);
		cancelbtn.setBounds(190, 320, 130, 30);
		cancelbtn.setFocusPainted(false);
		fontChooserPanel.add(cancelbtn);
		fontChooserDialog.getContentPane().add(fontChooserPanel);
		fontChooserPanel.setLayout(null);
		fontChooserDialog.setSize(380, 400);
		fontChooserDialog.setResizable(false);
		fontChooserDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		fontChooserDialog.setLocationRelativeTo(null);
		fontChooserDialog.setVisible(true);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!fontlist.isSelectionEmpty()) {
			fontnameflag = true;
			fontname = (String) fontlist.getSelectedValue();
			prevarea.setFont(new Font(fontname, Font.PLAIN, Integer.parseInt(fontsize)));
		}
		if (!sizelist.isSelectionEmpty()) {
			fontsizeflag = true;
			fontsize = (String) sizelist.getSelectedValue();
			prevarea.setFont(new Font(fontname, Font.PLAIN, Integer.parseInt(fontsize)));
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String arg = ae.getActionCommand();
		if (arg.equals("Apply Font Changes")) {
			String text = fontChooserTextPaneReference.getSelectedText();
			if (text != null) {
				if (fontnameflag) {
					SimpleAttributeSet sast = new SimpleAttributeSet();
					StyleConstants.setFontFamily(sast, fontname);
					fcsd.setCharacterAttributes(fontChooserTextPaneReference.getSelectionStart(),
							fontChooserTextPaneReference.getSelectionEnd()
									- fontChooserTextPaneReference.getSelectionStart(),
							sast, false);
				}
				if (fontsizeflag) {
					SimpleAttributeSet sast = new SimpleAttributeSet();
					StyleConstants.setFontSize(sast, Integer.parseInt(fontsize));
					fcsd.setCharacterAttributes(fontChooserTextPaneReference.getSelectionStart(),
							fontChooserTextPaneReference.getSelectionEnd()
									- fontChooserTextPaneReference.getSelectionStart(),
							sast, false);
				}
			} else {
				if (fontnameflag) {
					SimpleAttributeSet sast = new SimpleAttributeSet();
					StyleConstants.setFontFamily(sast, fontname);
					fontChooserTextPaneReference.setCharacterAttributes(sast, false);
				}
				if (fontsizeflag) {
					SimpleAttributeSet sast = new SimpleAttributeSet();
					StyleConstants.setFontSize(sast, Integer.parseInt(fontsize));
					fontChooserTextPaneReference.setCharacterAttributes(sast, false);
				}
			}
			fontChooserDialog.dispose();
		}
		if (arg.equals("Cancel")) {
			fontnameflag = false;
			fontsizeflag = false;
			fontChooserDialog.dispose();
		}
	}
}