package mainpackage;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.SwingUtilities;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.imageio.ImageIO;
import javax.swing.text.Highlighter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.io.File;
import java.io.IOException;
import editortools.FileOptions;
import editortools.EditOptions;
import editortools.ParagraphOptions;
import editortools.FontOptions;
import editortools.HelpOptions;

class ProNote {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
					if (args.length == 0 || args == null) {
						MyTextEditor myedit = new MyTextEditor();
						myedit.runTextEditor();
					} else {
						MyTextEditor myedit = new MyTextEditor(new File(args[0]));
						myedit.runTextEditor();
					}
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					JOptionPane.showMessageDialog(null, "Error Opening ProNote", "ERROR!!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public static void copyMain() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
					MyTextEditor myedit = new MyTextEditor();
					myedit.runTextEditor();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					JOptionPane.showMessageDialog(null, "Error Opening ProNote", "ERROR!!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}

class MyTextEditor {
	public JFrame myFrame;
	public JPanel myPanel;
	public JTextPane myTextPane;
	public JScrollPane myScrollPane;
	public JToolBar fileToolBar, editToolBar, paragraphToolBar, fontToolBar, helpToolBar;
	public JTabbedPane myTabbedPane;
	public Color toolBarColor;
	public File myFile;
	public Font myFont;
	public DocumentListener myTextPaneDocumentListener;
	public FileOptions myFileOptionsObject;
	public EditOptions myEditOptionsObject;
	public ParagraphOptions myParagraphOptionsObject;
	public FontOptions myFontOptionsObject;
	public HelpOptions myHelpOptionsObject;
	public Highlighter myHighlighter;
	public boolean myFlag;

	MyTextEditor() {
		myFrame = new JFrame();
		myPanel = new JPanel();
		myTextPane = new JTextPane();
		myScrollPane = new JScrollPane(myTextPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		myScrollPane.setViewportView(myTextPane);
		toolBarColor = new Color(235, 239, 249);
		fileToolBar = new JToolBar();
		editToolBar = new JToolBar();
		paragraphToolBar = new JToolBar();
		fontToolBar = new JToolBar();
		helpToolBar = new JToolBar();
		myTabbedPane = new JTabbedPane();
		myFile = new File(System.getProperty("user.dir"));
		myFont = new Font("Arial", Font.PLAIN, 18);
		myEditOptionsObject = new EditOptions(myTextPane);
		myFileOptionsObject = new FileOptions(myFile, myTextPane, myFrame, myTextPaneDocumentListener,
				myEditOptionsObject);
		myParagraphOptionsObject = new ParagraphOptions(myFrame, myTextPane);
		myFontOptionsObject = new FontOptions(myTextPane, myFrame);
		myHelpOptionsObject = new HelpOptions(myFrame);
		myHighlighter = myTextPane.getHighlighter();
		myFlag = false;
	}

	MyTextEditor(File openWithFile) {
		myFrame = new JFrame();
		myPanel = new JPanel();
		myTextPane = new JTextPane();
		myScrollPane = new JScrollPane(myTextPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		myScrollPane.setViewportView(myTextPane);
		toolBarColor = new Color(235, 239, 249);
		fileToolBar = new JToolBar();
		editToolBar = new JToolBar();
		paragraphToolBar = new JToolBar();
		fontToolBar = new JToolBar();
		helpToolBar = new JToolBar();
		myTabbedPane = new JTabbedPane();
		myFile = openWithFile;
		myFont = new Font("Arial", Font.PLAIN, 18);
		myEditOptionsObject = new EditOptions(myTextPane);
		myFileOptionsObject = new FileOptions(myFile, myTextPane, myFrame, myTextPaneDocumentListener,
				myEditOptionsObject);
		myParagraphOptionsObject = new ParagraphOptions(myFrame, myTextPane);
		myFontOptionsObject = new FontOptions(myTextPane, myFrame);
		myHelpOptionsObject = new HelpOptions(myFrame);
		myHighlighter = myTextPane.getHighlighter();
		myFlag = true;
	}

	void runTextEditor() {
		try {
			ImageIcon proNoteLogo = new ImageIcon(ImageIO.read(getClass().getResource("PRONOTE_ICON.png")));
			myFrame.setIconImage(proNoteLogo.getImage());
		} catch (IOException e1) {
			try {
				ImageIcon proNoteLogo1 = new ImageIcon(ImageIO.read(getClass().getResource("PRONOTE_ICON.png")));
				myFrame.setIconImage(proNoteLogo1.getImage());
			} catch (IOException e2) {

			}
		}
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setTitle("New-ProNote");
		myPanel.setLayout(new GridBagLayout());
		myTabbedPane.setFont(new Font("Arial", Font.PLAIN, 15));
		myTabbedPane.setFocusable(false);
		GridBagConstraints myTabbedPaneConstraints = new GridBagConstraints();
		myTabbedPaneConstraints.gridx = 0;
		myTabbedPaneConstraints.gridy = 0;
		myTabbedPaneConstraints.gridwidth = 1;
		myTabbedPaneConstraints.gridheight = 1;
		myTabbedPaneConstraints.weightx = 1;
		myTabbedPaneConstraints.weighty = 0.02;
		myTabbedPaneConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		myTabbedPaneConstraints.fill = GridBagConstraints.BOTH;
		myTabbedPaneConstraints.insets = new Insets(0, 0, 0, 0);
		myTabbedPaneConstraints.ipadx = 0;
		myTabbedPaneConstraints.ipady = 0;

		GridBagConstraints myScrollPaneConstraints = new GridBagConstraints();
		myScrollPaneConstraints.gridx = 0;
		myScrollPaneConstraints.gridy = 1;
		myScrollPaneConstraints.gridwidth = 1;
		myScrollPaneConstraints.gridheight = 4;
		myScrollPaneConstraints.weightx = 1;
		myScrollPaneConstraints.weighty = 1;
		myScrollPaneConstraints.anchor = GridBagConstraints.LINE_START;
		myScrollPaneConstraints.fill = GridBagConstraints.BOTH;
		myScrollPaneConstraints.insets = new Insets(0, 0, 0, 0);
		myScrollPaneConstraints.ipadx = 0;
		myScrollPaneConstraints.ipady = 0;

		// FilePane Settings
		fileToolBar.setBackground(toolBarColor);
		fileToolBar.addSeparator();
		ImageIcon newImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("NEW_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton newButton = new JButton(newImageIcon);
		newButton.setToolTipText("New File");
		newButton.setFocusPainted(false);
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Boolean mynewflag = myFileOptionsObject.newFile();
				if (!mynewflag) {
					ProNote.copyMain();
				}
			}
		});
		fileToolBar.add(newButton);
		fileToolBar.addSeparator();
		ImageIcon openImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("OPEN_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton openButton = new JButton(openImageIcon);
		openButton.setToolTipText("Open File");
		openButton.setFocusPainted(false);
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myFileOptionsObject.openFile();
			}
		});
		fileToolBar.add(openButton);
		fileToolBar.addSeparator();
		ImageIcon saveImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("SAVE_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton saveButton = new JButton(saveImageIcon);
		saveButton.setToolTipText("Save File");
		saveButton.setFocusPainted(false);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myFileOptionsObject.saveFile();
			}
		});
		fileToolBar.add(saveButton);
		fileToolBar.addSeparator();
		ImageIcon saveAsImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("SAVEAS_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton saveAsButton = new JButton(saveAsImageIcon);
		saveAsButton.setToolTipText("Save As");
		saveAsButton.setFocusPainted(false);
		saveAsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myFileOptionsObject.saveAsFile();
			}
		});
		fileToolBar.add(saveAsButton);
		fileToolBar.addSeparator();
		ImageIcon exitImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("EXIT_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton exitButton = new JButton(exitImageIcon);
		exitButton.setToolTipText("Exit");
		exitButton.setFocusPainted(false);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myFileOptionsObject.exitFile();
			}
		});
		fileToolBar.add(exitButton);
		fileToolBar.setFloatable(false);
		myTabbedPane.addTab("File", fileToolBar);

		// EditPane Settings
		editToolBar.setBackground(toolBarColor);
		editToolBar.addSeparator();
		ImageIcon cutImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("CUT_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton cutButton = new JButton(cutImageIcon);
		cutButton.setToolTipText("Cut");
		cutButton.setFocusPainted(false);
		cutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myEditOptionsObject.cutText();
			}
		});
		editToolBar.add(cutButton);
		editToolBar.addSeparator();
		ImageIcon copyImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("COPY_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton copyButton = new JButton(copyImageIcon);
		copyButton.setToolTipText("Copy");
		copyButton.setFocusPainted(false);
		copyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myEditOptionsObject.copyText();
			}
		});
		editToolBar.add(copyButton);
		editToolBar.addSeparator();
		ImageIcon pasteImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("PASTE_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton pasteButton = new JButton(pasteImageIcon);
		pasteButton.setToolTipText("Paste");
		pasteButton.setFocusPainted(false);
		pasteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myEditOptionsObject.pasteText();
			}
		});
		editToolBar.add(pasteButton);
		editToolBar.addSeparator();
		ImageIcon deleteImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("DELETE_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton deleteButton = new JButton(deleteImageIcon);
		deleteButton.setToolTipText("Delete");
		deleteButton.setFocusPainted(false);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myEditOptionsObject.deleteText();
			}
		});
		editToolBar.add(deleteButton);
		editToolBar.addSeparator();
		ImageIcon selectAllImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("SELECTALL_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton selectAllButton = new JButton(selectAllImageIcon);
		selectAllButton.setToolTipText("Select All");
		selectAllButton.setFocusPainted(false);
		selectAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myEditOptionsObject.selectAllText();
			}
		});
		editToolBar.add(selectAllButton);
		editToolBar.addSeparator();
		ImageIcon undoImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("UNDO_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton undoButton = new JButton(undoImageIcon);
		undoButton.setToolTipText("Undo");
		undoButton.setFocusPainted(false);
		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myEditOptionsObject.undoChanges();
			}
		});
		editToolBar.add(undoButton);
		editToolBar.addSeparator();
		ImageIcon redoImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("REDO_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton redoButton = new JButton(redoImageIcon);
		redoButton.setToolTipText("Redo");
		redoButton.setFocusPainted(false);
		redoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myEditOptionsObject.redoChanges();
			}
		});
		editToolBar.add(redoButton);
		editToolBar.addSeparator();
		ImageIcon timeStampImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("TIMESTAMP_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton timeStampButton = new JButton(timeStampImageIcon);
		timeStampButton.setToolTipText("Time Stamp");
		timeStampButton.setFocusPainted(false);
		timeStampButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myEditOptionsObject.timeStamp();
			}
		});
		editToolBar.add(timeStampButton);
		editToolBar.addSeparator();
		editToolBar.setFloatable(false);
		myTabbedPane.addTab("Edit", editToolBar);

		// SearchPane Settings
		paragraphToolBar.setBackground(toolBarColor);
		paragraphToolBar.addSeparator();
		ImageIcon findImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("FIND_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton findButton = new JButton(findImageIcon);
		findButton.setToolTipText("Find");
		findButton.setFocusPainted(false);
		findButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myParagraphOptionsObject.searchText();
			}
		});
		paragraphToolBar.add(findButton);
		paragraphToolBar.addSeparator();
		ImageIcon replaceImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("REPLACE_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton replaceButton = new JButton(replaceImageIcon);
		replaceButton.setToolTipText("Replace");
		replaceButton.setFocusPainted(false);
		replaceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myParagraphOptionsObject.replaceText();
			}
		});
		paragraphToolBar.add(replaceButton);
		paragraphToolBar.addSeparator();
		ImageIcon leftAlignImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("LEFTALIGN_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton leftAlignButton = new JButton(leftAlignImageIcon);
		leftAlignButton.setToolTipText("Left Align");
		leftAlignButton.setFocusPainted(false);
		leftAlignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myParagraphOptionsObject.leftAlignText();
			}
		});
		paragraphToolBar.add(leftAlignButton);
		paragraphToolBar.addSeparator();
		ImageIcon centerAlignImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("CENTERALIGN_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton centerAlignButton = new JButton(centerAlignImageIcon);
		centerAlignButton.setToolTipText("Center Align");
		centerAlignButton.setFocusPainted(false);
		centerAlignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myParagraphOptionsObject.centerAlignText();
			}
		});
		paragraphToolBar.add(centerAlignButton);
		paragraphToolBar.addSeparator();
		ImageIcon rightAlignImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("RIGHTALIGN_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton rightAlignButton = new JButton(rightAlignImageIcon);
		rightAlignButton.setToolTipText("Right Align");
		rightAlignButton.setFocusPainted(false);
		rightAlignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myParagraphOptionsObject.rightAlignText();
			}
		});
		paragraphToolBar.add(rightAlignButton);
		paragraphToolBar.addSeparator();
		ImageIcon subScriptImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("SUBSCRIPT_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton subScriptButton = new JButton(subScriptImageIcon);
		subScriptButton.setToolTipText("Sub Script");
		subScriptButton.setFocusPainted(false);
		subScriptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myParagraphOptionsObject.subScriptText();
			}
		});
		paragraphToolBar.add(subScriptButton);
		paragraphToolBar.addSeparator();
		ImageIcon superScriptImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("SUPERSCRIPT_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton superScriptButton = new JButton(superScriptImageIcon);
		superScriptButton.setToolTipText("Super Script");
		superScriptButton.setFocusPainted(false);
		superScriptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myParagraphOptionsObject.superScriptText();
			}
		});
		paragraphToolBar.add(superScriptButton);
		paragraphToolBar.addSeparator();
		paragraphToolBar.setFloatable(false);
		myTabbedPane.addTab("Paragraph", paragraphToolBar);

		// FontPane Settings
		fontToolBar.setBackground(toolBarColor);
		fontToolBar.addSeparator();
		ImageIcon fontSettingsImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("FONT_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton fontSettingsButton = new JButton(fontSettingsImageIcon);
		fontSettingsButton.setToolTipText("Font Settings");
		fontSettingsButton.setFocusPainted(false);
		fontSettingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myFontOptionsObject.fontSelectionSettings();
			}
		});
		fontToolBar.add(fontSettingsButton);
		fontToolBar.addSeparator();
		ImageIcon boldImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("BOLD_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton boldButton = new JButton(boldImageIcon);
		boldButton.setToolTipText("Bold");
		boldButton.setFocusPainted(false);
		boldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myFontOptionsObject.boldSettings();
			}
		});
		fontToolBar.add(boldButton);
		fontToolBar.addSeparator();
		ImageIcon italicImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("ITALIC_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton italicButton = new JButton(italicImageIcon);
		italicButton.setToolTipText("Italic");
		italicButton.setFocusPainted(false);
		italicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myFontOptionsObject.italicSettings();
			}
		});
		fontToolBar.add(italicButton);
		fontToolBar.addSeparator();
		ImageIcon underlineImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("UNDERLINE_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton underlineButton = new JButton(underlineImageIcon);
		underlineButton.setToolTipText("Underline");
		underlineButton.setFocusPainted(false);
		underlineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myFontOptionsObject.underlineSettings();
			}
		});
		fontToolBar.add(underlineButton);
		fontToolBar.addSeparator();
		ImageIcon strikeThroughImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("STRIKETHROUGH_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton strikeThroughButton = new JButton(strikeThroughImageIcon);
		strikeThroughButton.setToolTipText("Strike Through");
		strikeThroughButton.setFocusPainted(false);
		strikeThroughButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myFontOptionsObject.strikeThroughSettings();
			}
		});
		fontToolBar.add(strikeThroughButton);
		fontToolBar.addSeparator();
		ImageIcon fontColorImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("FONTCOLOR_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton fontColorButton = new JButton(fontColorImageIcon);
		fontColorButton.setToolTipText("Text Color");
		fontColorButton.setFocusPainted(false);
		fontColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myFontOptionsObject.foregroundColorSettings();
			}
		});
		fontToolBar.add(fontColorButton);
		fontToolBar.addSeparator();
		ImageIcon highlightImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("HIGHLIGHT_ICON.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton highlightButton = new JButton(highlightImageIcon);
		highlightButton.setToolTipText("Highlight Text");
		highlightButton.setFocusPainted(false);
		highlightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myFontOptionsObject.backgroundColorSettings();
			}
		});
		fontToolBar.add(highlightButton);
		fontToolBar.addSeparator();
		fontToolBar.setFloatable(false);
		myTabbedPane.addTab("Font", fontToolBar);

		// HelpPane Settings
		helpToolBar.setBackground(toolBarColor);
		helpToolBar.addSeparator();
		ImageIcon helpImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("HELP_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton helpButton = new JButton(helpImageIcon);
		helpButton.setToolTipText("Help");
		helpButton.setFocusPainted(false);
		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myHelpOptionsObject.helpOption();
			}
		});
		helpToolBar.add(helpButton);
		helpToolBar.addSeparator();
		ImageIcon aboutImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("ABOUT_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton aboutButton = new JButton(aboutImageIcon);
		aboutButton.setToolTipText("About ProNote");
		aboutButton.setFocusPainted(false);
		aboutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myHelpOptionsObject.aboutOption();
			}
		});
		helpToolBar.add(aboutButton);
		helpToolBar.addSeparator();
		ImageIcon mailImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("MAIL_ICON.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton mailButton = new JButton(mailImageIcon);
		mailButton.setToolTipText("Contact");
		mailButton.setFocusPainted(false);
		mailButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				myHelpOptionsObject.developerContact();
			}
		});
		helpToolBar.add(mailButton);
		helpToolBar.addSeparator();
		helpToolBar.setFloatable(false);
		myTabbedPane.addTab("Help", helpToolBar);

		myTextPane.setContentType("text/plain;charset=UTF-8");
		myTextPane.setFont(myFont);
		myPanel.add(myTabbedPane, myTabbedPaneConstraints);
		myPanel.add(myScrollPane, myScrollPaneConstraints);
		myFrame.add(myPanel);
		myFrame.setSize(new Dimension(1000, 600));
		myFrame.setMaximumSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);

		myTextPaneDocumentListener = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				myFileOptionsObject.saveflag = false;
				myFileOptionsObject.editflag = true;
				if (myFrame.getTitle().charAt(0) != '*') {
					myFrame.setTitle("*" + myFrame.getTitle());
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				myFileOptionsObject.saveflag = false;
				myFileOptionsObject.editflag = true;
				if (myFrame.getTitle().charAt(0) != '*') {
					myFrame.setTitle("*" + myFrame.getTitle());
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				myFileOptionsObject.saveflag = false;
				myFileOptionsObject.editflag = true;
				if (myFrame.getTitle().charAt(0) != '*') {
					myFrame.setTitle("*" + myFrame.getTitle());
				}
			}
		};
		myTextPane.getStyledDocument().addDocumentListener(myTextPaneDocumentListener);
		myTextPane.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_N) {
					Boolean mynewflag = myFileOptionsObject.newFile();
					if (!mynewflag) {
						ProNote.copyMain();
					}
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_O) {
					myFileOptionsObject.openFile();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_S && !keyEvent.isShiftDown()) {
					myFileOptionsObject.saveFile();
				}
				if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_S) {
					myFileOptionsObject.saveAsFile();
				}
				if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_E) {
					myFileOptionsObject.exitFile();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_X) {
					myEditOptionsObject.cutText();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_C && !keyEvent.isShiftDown()) {
					myEditOptionsObject.copyText();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_Z) {
					myEditOptionsObject.undoChanges();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_Y) {
					myEditOptionsObject.redoChanges();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_A && !keyEvent.isShiftDown()) {
					myEditOptionsObject.selectAllText();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_D) {
					myEditOptionsObject.timeStamp();
				}
				if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_F) {
					myFontOptionsObject.fontSelectionSettings();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_B && !keyEvent.isShiftDown()) {
					myFontOptionsObject.boldSettings();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_I) {
					myFontOptionsObject.italicSettings();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_U) {
					myFontOptionsObject.underlineSettings();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_T) {
					myFontOptionsObject.strikeThroughSettings();
				}
				if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_C) {
					myFontOptionsObject.foregroundColorSettings();
				}
				if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_B) {
					myFontOptionsObject.backgroundColorSettings();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_F && !keyEvent.isShiftDown()) {
					myParagraphOptionsObject.searchText();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_H && !keyEvent.isShiftDown()) {
					myParagraphOptionsObject.replaceText();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_L) {
					myParagraphOptionsObject.leftAlignText();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_E) {
					myParagraphOptionsObject.centerAlignText();
				}
				if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_R) {
					myParagraphOptionsObject.rightAlignText();
				}
				if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_UP) {
					myParagraphOptionsObject.superScriptText();
				}
				if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
					myParagraphOptionsObject.subScriptText();
				}

				if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_H) {
					myHelpOptionsObject.helpOption();
				}
				if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_A) {
					myHelpOptionsObject.aboutOption();
				}
				if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getKeyCode() == KeyEvent.VK_M) {
					myHelpOptionsObject.developerContact();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		myTextPane.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				myHighlighter.removeAllHighlights();
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		myFrame.addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent we) {
				if (!myFileOptionsObject.editflag) {
					myFrame.dispose();
				} else {
					Object options[] = { "Save File", "Don't Save", "Cancel" };
					int selectedOption = JOptionPane.showOptionDialog(myTextPane, "Do you want to save changes ?",
							"You have unsaved changes!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
							null, options, null);
					switch (selectedOption) {
					case 0:
						myFileOptionsObject.saveFile();
						myFrame.dispose();
						break;
					case 1:
						myFrame.dispose();
						break;
					case 2:
						myFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						return;
					}
				}
			}

			@Override
			public void windowDeactivated(WindowEvent we) {

			}

			@Override
			public void windowActivated(WindowEvent we) {

			}

			@Override
			public void windowDeiconified(WindowEvent we) {

			}

			@Override
			public void windowIconified(WindowEvent we) {

			}

			@Override
			public void windowClosed(WindowEvent we) {

			}

			@Override
			public void windowOpened(WindowEvent we) {

			}
		});
		if (myFlag) {
			myFileOptionsObject.openFile(myFile);
			myFlag = false;
		}
	}
}