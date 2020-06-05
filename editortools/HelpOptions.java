package editortools;

import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;

public class HelpOptions {
	private JFrame helpOptionsFrameReference;
	public HelpOptions(JFrame fr)
	{
		helpOptionsFrameReference = fr;
	}
	public void aboutOption() {
		JDialog aboutDialog = new JDialog(helpOptionsFrameReference);
		JPanel aboutPanel = new JPanel();
		JTextArea aboutTextArea = new JTextArea();
		String text = "ProNote is a Notepad type TextEditor with additional FileSecurity Mechanism and Font Settings.\n"
				+ "ProNote is a light weight software which includes Font and Paragraph Selections, this is unique\n"
				+ "since such range softwares do not provide such options of File Security and Font Selectivity.\n"
				+ "This Application is made for editing and saving files on local PC and user should avoid sharing \n"
				+ ".ppf files because security of file may be at risk.\n\n"
				+ "ProNote as name suggests is an advance notepad which aims to secure Normal Text Documents.\n"
				+ "Remember file will always be stored in .ppf format and is intented for writing and editing only.\n"
				+ "I Hope you guys love editing Documents in a safe manner with lovely fonts and styles!!\n\n"
				+ "Programmed and Developed By:"
				+ "\nDevansh Shah\nEmail: help.pronote@gmail.com\n© 2020 All Rights Reserved.";
		aboutTextArea.setBounds(10, 10, 60, 60);
		aboutPanel.add(aboutTextArea);
		aboutTextArea.setText(text);
		aboutTextArea.setFont(new Font(Font.SERIF, Font.ITALIC, 18));
		aboutTextArea.setEditable(false);
		aboutTextArea.setLineWrap(false);

		try {
			ImageIcon proNoteLogo = new ImageIcon(ImageIO.read(getClass().getResource("PRONOTE_ICON.png")));
			aboutDialog.setIconImage(proNoteLogo.getImage());
		} catch (IOException e) {
			//
		}
		aboutDialog.setTitle("About ProNote");
		aboutDialog.setSize(800, 400);
		aboutDialog.getContentPane().add(aboutPanel);
		aboutDialog.setResizable(false);
		aboutDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		aboutDialog.setLocationRelativeTo(null);
		aboutDialog.setVisible(true);
	}

	public void helpOption() {
		try {
			Desktop desktopReference = Desktop.getDesktop();
			URI siteurl = new URI("https://devansh2805.github.io/ProNote/documentation.html");
			desktopReference.browse(siteurl);
		} catch (URISyntaxException | IOException e) {
			JOptionPane.showMessageDialog(helpOptionsFrameReference, "Error Connecting tu URL", "Error!!", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void developerContact() {
		try {
			Desktop desktopReference = Desktop.getDesktop();
			URI siteurl = new URI("https://devansh2805.github.io/ProNote/contact.html");
			desktopReference.browse(siteurl);
		} catch (URISyntaxException | IOException e) {
			JOptionPane.showMessageDialog(helpOptionsFrameReference, "Error Connecting tu URL", "Error!!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
