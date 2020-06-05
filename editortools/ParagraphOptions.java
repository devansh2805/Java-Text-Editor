package editortools;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Element;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ParagraphOptions {
	private static final int FORWARD = 0;
	private static final int BACKWARD = 1;
	private JFrame paragraphOptionsFrameReference;
	private JTextPane paragraphOptionsTextPaneReference;
	private StyledDocument paragraphOptionsStyledDocumentReference;
	private boolean subscflag = false, supscflag = false;

	public ParagraphOptions(JFrame fr, JTextPane tp) {
		paragraphOptionsFrameReference = fr;
		paragraphOptionsTextPaneReference = tp;
		paragraphOptionsStyledDocumentReference = paragraphOptionsTextPaneReference.getStyledDocument();
	}

	public void searchText() {
		paragraphOptionsStyledDocumentReference = paragraphOptionsTextPaneReference.getStyledDocument();
		paragraphOptionsTextPaneReference.select(0, 0);
		JDialog searchDialog = new JDialog(paragraphOptionsFrameReference);
		try {
			ImageIcon proNoteLogo = new ImageIcon(ImageIO.read(getClass().getResource("PRONOTE_ICON.png")));
			searchDialog.setIconImage(proNoteLogo.getImage());
		} catch (IOException e) {

		}
		searchDialog.setTitle("Search");
		JPanel searchPanel = new JPanel();
		JLabel findLabel = new JLabel("Find What: ");
		findLabel.setBounds(20, 20, 70, 30);
		searchPanel.add(findLabel);
		JTextField inputField = new JTextField();
		inputField.setBounds(95, 20, 370, 30);
		searchPanel.add(inputField);
		JRadioButton caseSensitiveRadioButton = new JRadioButton("Case Sensitive Search");
		caseSensitiveRadioButton.setBounds(20, 120, 200, 30);
		caseSensitiveRadioButton.setFocusPainted(false);
		searchPanel.add(caseSensitiveRadioButton);
		JButton findNextButton = new JButton("Find Next");
		findNextButton.setBounds(95, 70, 110, 30);
		findNextButton.setFocusPainted(false);
		findNextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputField.getText() != null)
					searchFunction(searchDialog, inputField, caseSensitiveRadioButton, ParagraphOptions.FORWARD);
				else
					JOptionPane.showMessageDialog(searchDialog, "Cannot execute Find of no word", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		searchPanel.add(findNextButton);
		JButton findPrevButton = new JButton("Find Previous");
		findPrevButton.setBounds(225, 70, 110, 30);
		findPrevButton.setFocusPainted(false);
		findPrevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputField.getText() != null)
					searchFunction(searchDialog, inputField, caseSensitiveRadioButton, ParagraphOptions.BACKWARD);
				else
					JOptionPane.showMessageDialog(searchDialog, "Cannot execute Find of no word", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		searchPanel.add(findPrevButton);
		JButton findAllButton = new JButton("Find All");
		findAllButton.setBounds(355, 70, 110, 30);
		findAllButton.setFocusPainted(false);
		findAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputField.getText() != null)
					searchAllFunction(searchDialog, caseSensitiveRadioButton, inputField);
				else
					JOptionPane.showMessageDialog(searchDialog, "Cannot execute Find of no word", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		searchPanel.add(findAllButton);

		searchPanel.setLayout(null);
		searchDialog.getContentPane().add(searchPanel);
		searchDialog.setSize(500, 200);
		searchDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		searchDialog.setLocationRelativeTo(null);
		searchDialog.setResizable(false);
		searchDialog.setVisible(true);
	}

	public void searchFunction(JDialog paragraphOptionsDialogReference, JTextField searchDialogTextFieldReference,
			JRadioButton caseSensitiveButton, int searchDirection) {
		paragraphOptionsStyledDocumentReference = paragraphOptionsTextPaneReference.getStyledDocument();
		int indexVariable = -1;
		String whereToBeSearched = "", whatToBeSearched = "";
		if (!caseSensitiveButton.isSelected()) {
			String[] tempTextArray = paragraphOptionsTextPaneReference.getText().toLowerCase().split("\r");
			for (String value : tempTextArray)
				whereToBeSearched += value;
			whatToBeSearched = searchDialogTextFieldReference.getText().toLowerCase();
		} else {
			String[] tempTextArray = paragraphOptionsTextPaneReference.getText().split("\r");
			for (String value : tempTextArray)
				whereToBeSearched += value;
			whatToBeSearched = searchDialogTextFieldReference.getText();
		}

		if (paragraphOptionsTextPaneReference.getSelectedText() == null) {
			paragraphOptionsTextPaneReference.setSelectionStart(0);
			paragraphOptionsTextPaneReference.setSelectionEnd(0);
		}
		if (searchDirection == ParagraphOptions.FORWARD)
			indexVariable = whereToBeSearched.indexOf(whatToBeSearched,
					paragraphOptionsTextPaneReference.getSelectionEnd());
		if (searchDirection == ParagraphOptions.BACKWARD)
			indexVariable = whereToBeSearched.lastIndexOf(whatToBeSearched,
					paragraphOptionsTextPaneReference.getSelectionStart() - 1);

		if (indexVariable != -1) {
			paragraphOptionsTextPaneReference.setCaretPosition(indexVariable);
			paragraphOptionsTextPaneReference.moveCaretPosition(indexVariable + whatToBeSearched.length());
			paragraphOptionsTextPaneReference.getCaret().setSelectionVisible(true);
		} else
			JOptionPane.showMessageDialog(paragraphOptionsDialogReference, "No Matches", "Error",
					JOptionPane.ERROR_MESSAGE);
	}

	public void searchAllFunction(JDialog paragraphOptionDialogReference, JRadioButton caseSensitiveButton,
			JTextField searchDialogTextFieldReference) {
		paragraphOptionsStyledDocumentReference = paragraphOptionsTextPaneReference.getStyledDocument();
		int indexVariable = 0;
		String whereToBeSearched = "", whatToBeSearched = "";
		if (!caseSensitiveButton.isSelected()) {
			String[] tempTextArray = paragraphOptionsTextPaneReference.getText().toLowerCase().split("\r");
			for (String value : tempTextArray)
				whereToBeSearched += value;
			whatToBeSearched = searchDialogTextFieldReference.getText().toLowerCase();
		} else {
			String[] tempTextArray = paragraphOptionsTextPaneReference.getText().split("\r");
			for (String value : tempTextArray)
				whereToBeSearched += value;
			whatToBeSearched = searchDialogTextFieldReference.getText();
		}
		Highlighter textHighlighter = paragraphOptionsTextPaneReference.getHighlighter();
		HighlightPainter textHighlightPainter = new DefaultHighlighter.DefaultHighlightPainter(
				paragraphOptionsTextPaneReference.getSelectionColor());
		paragraphOptionsTextPaneReference.setSelectionStart(0);
		paragraphOptionsTextPaneReference.setSelectionEnd(0);
		paragraphOptionsTextPaneReference.setCaretPosition(0);
		indexVariable = whereToBeSearched.indexOf(whatToBeSearched,
				paragraphOptionsTextPaneReference.getSelectionEnd());
		if (indexVariable == -1) {
			JOptionPane.showMessageDialog(paragraphOptionsTextPaneReference, "No Matches", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		while (indexVariable != -1) {
			if (indexVariable != -1) {
				try {
					textHighlighter.addHighlight(indexVariable, indexVariable + whatToBeSearched.length(),
							textHighlightPainter);
				} catch (BadLocationException e) {
					JOptionPane.showMessageDialog(paragraphOptionsTextPaneReference, "No Matches", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				indexVariable = whereToBeSearched.indexOf(whatToBeSearched, indexVariable + whatToBeSearched.length());
			}
		}
	}

	public void replaceText() {
		paragraphOptionsStyledDocumentReference = paragraphOptionsTextPaneReference.getStyledDocument();
		paragraphOptionsTextPaneReference.select(0, 0);
		JDialog replaceDialog = new JDialog(paragraphOptionsFrameReference);
		try {
			ImageIcon proNoteLogo = new ImageIcon(ImageIO.read(getClass().getResource("PRONOTE_ICON.png")));
			replaceDialog.setIconImage(proNoteLogo.getImage());
		} catch (IOException e) {

		}
		replaceDialog.setTitle("Replace");
		JPanel replacePanel = new JPanel();
		JLabel findLabel = new JLabel("Find What:");
		findLabel.setBounds(20, 20, 90, 30);
		replacePanel.add(findLabel);
		JTextField findInputField = new JTextField();
		findInputField.setBounds(105, 20, 370, 30);
		replacePanel.add(findInputField);
		JLabel replaceLabel = new JLabel("Replace With:");
		replaceLabel.setBounds(20, 70, 90, 30);
		replacePanel.add(replaceLabel);
		JTextField replaceInputField = new JTextField();
		replaceInputField.setBounds(105, 70, 370, 30);
		replacePanel.add(replaceInputField);
		JRadioButton caseSensitiveRadioButton = new JRadioButton("Case Sensitive Search");
		caseSensitiveRadioButton.setBounds(20, 170, 200, 30);
		caseSensitiveRadioButton.setFocusPainted(false);
		replacePanel.add(caseSensitiveRadioButton);
		JButton findNextButton = new JButton("Find Next");
		findNextButton.setBounds(105, 120, 110, 30);
		findNextButton.setFocusPainted(false);
		findNextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (findInputField.getText() != null)
					searchFunction(replaceDialog, findInputField, caseSensitiveRadioButton, ParagraphOptions.FORWARD);
				else
					JOptionPane.showMessageDialog(replaceDialog, "Cannot execute Find of no word", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		replacePanel.add(findNextButton);
		JButton replaceButton = new JButton("Replace");
		replaceButton.setBounds(235, 120, 110, 30);
		replaceButton.setFocusPainted(false);
		replaceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (findInputField.getText() != null)
					replaceFunction(replaceInputField);
				else
					JOptionPane.showMessageDialog(replaceDialog, "Cannot execute Find of no word", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		replacePanel.add(replaceButton);
		JButton replaceAllButton = new JButton("Replace All");
		replaceAllButton.setBounds(365, 120, 110, 30);
		replaceAllButton.setFocusPainted(false);
		replaceAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (findInputField.getText() != null) {
					searchAllFunction(replaceDialog, caseSensitiveRadioButton, findInputField);
					replaceAllFunction(replaceInputField);
				} else {
					JOptionPane.showMessageDialog(replaceDialog, "Cannot execute Find of no word", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		replacePanel.add(replaceAllButton);
		replacePanel.setLayout(null);
		replaceDialog.getContentPane().add(replacePanel);
		replaceDialog.setSize(520, 250);
		replaceDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		replaceDialog.setLocationRelativeTo(null);
		replaceDialog.setResizable(false);
		replaceDialog.setVisible(true);
	}

	public void replaceFunction(JTextField replaceInputFieldReference) {
		if (paragraphOptionsTextPaneReference.getSelectedText() == null)
			JOptionPane.showMessageDialog(paragraphOptionsTextPaneReference,
					"No word Found to Replace\nNote: Use Find Next Button to Find word", "Error Finding",
					JOptionPane.INFORMATION_MESSAGE);
		else
			paragraphOptionsTextPaneReference.replaceSelection(replaceInputFieldReference.getText());
	}

	public void replaceAllFunction(JTextField replaceInputFieldReference) {
		paragraphOptionsStyledDocumentReference = paragraphOptionsTextPaneReference.getStyledDocument();
		Highlight[] allHighlights = paragraphOptionsTextPaneReference.getHighlighter().getHighlights();
		paragraphOptionsTextPaneReference.getHighlighter().removeAllHighlights();
		for (Highlight highlightedText : allHighlights) {
			paragraphOptionsTextPaneReference.setSelectionStart(highlightedText.getStartOffset());
			paragraphOptionsTextPaneReference.setSelectionEnd(highlightedText.getEndOffset());
			paragraphOptionsTextPaneReference.replaceSelection(replaceInputFieldReference.getText());
		}
	}

	public void centerAlignText() {
		paragraphOptionsStyledDocumentReference = paragraphOptionsTextPaneReference.getStyledDocument();
		String text = paragraphOptionsTextPaneReference.getSelectedText();
		if (text != null) {
			MutableAttributeSet sast = new SimpleAttributeSet();
			StyleConstants.setAlignment(sast, StyleConstants.ALIGN_CENTER);
			paragraphOptionsStyledDocumentReference.setParagraphAttributes(
					paragraphOptionsTextPaneReference.getSelectionStart(),
					paragraphOptionsTextPaneReference.getSelectionEnd()
							- paragraphOptionsTextPaneReference.getSelectionStart(),
					sast, false);
		} else {
			SimpleAttributeSet sast = new SimpleAttributeSet();
			StyleConstants.setAlignment(sast, StyleConstants.ALIGN_CENTER);
			paragraphOptionsTextPaneReference.setParagraphAttributes(sast, false);
		}
	}

	public void leftAlignText() {
		paragraphOptionsStyledDocumentReference = paragraphOptionsTextPaneReference.getStyledDocument();
		String text = paragraphOptionsTextPaneReference.getSelectedText();
		if (text != null) {
			SimpleAttributeSet sast = new SimpleAttributeSet();
			StyleConstants.setAlignment(sast, StyleConstants.ALIGN_LEFT);
			paragraphOptionsStyledDocumentReference.setParagraphAttributes(
					paragraphOptionsTextPaneReference.getSelectionStart(),
					paragraphOptionsTextPaneReference.getSelectionEnd()
							- paragraphOptionsTextPaneReference.getSelectionStart(),
					sast, false);
		} else {
			SimpleAttributeSet sast = new SimpleAttributeSet();
			StyleConstants.setAlignment(sast, StyleConstants.ALIGN_LEFT);
			paragraphOptionsTextPaneReference.setParagraphAttributes(sast, false);
		}
	}

	public void rightAlignText() {
		paragraphOptionsStyledDocumentReference = paragraphOptionsTextPaneReference.getStyledDocument();
		String text = paragraphOptionsTextPaneReference.getSelectedText();
		if (text != null) {
			SimpleAttributeSet sast = new SimpleAttributeSet();
			StyleConstants.setAlignment(sast, StyleConstants.ALIGN_RIGHT);
			paragraphOptionsStyledDocumentReference.setParagraphAttributes(
					paragraphOptionsTextPaneReference.getSelectionStart(),
					paragraphOptionsTextPaneReference.getSelectionEnd()
							- paragraphOptionsTextPaneReference.getSelectionStart(),
					sast, false);
		} else {
			SimpleAttributeSet sast = new SimpleAttributeSet();
			StyleConstants.setAlignment(sast, StyleConstants.ALIGN_RIGHT);
			paragraphOptionsTextPaneReference.setParagraphAttributes(sast, false);
		}
	}

	public void superScriptText() {
		paragraphOptionsStyledDocumentReference = paragraphOptionsTextPaneReference.getStyledDocument();
		boolean superscriptflag = false;
		String text = paragraphOptionsTextPaneReference.getSelectedText();
		if (text != null) {
			for (int i = paragraphOptionsTextPaneReference.getSelectionStart()
					+ 1; i < paragraphOptionsTextPaneReference.getSelectionEnd(); i++) {
				Element element = paragraphOptionsStyledDocumentReference.getCharacterElement(i);
				AttributeSet aset = element.getAttributes();
				if (aset.containsAttribute(StyleConstants.Superscript, Boolean.TRUE)) {
					aset = null;
					superscriptflag = true;
				} else {
					superscriptflag = false;
					break;
				}
			}
			if (!superscriptflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Superscript, Boolean.TRUE);
				paragraphOptionsStyledDocumentReference.setCharacterAttributes(
						paragraphOptionsTextPaneReference.getSelectionStart(),
						paragraphOptionsTextPaneReference.getSelectionEnd()
								- paragraphOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Superscript, Boolean.FALSE);
				paragraphOptionsStyledDocumentReference.setCharacterAttributes(
						paragraphOptionsTextPaneReference.getSelectionStart(),
						paragraphOptionsTextPaneReference.getSelectionEnd()
								- paragraphOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			}
		} else {
			if (!supscflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Superscript, Boolean.TRUE);
				paragraphOptionsTextPaneReference.setCharacterAttributes(sast, false);
				supscflag = true;
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Superscript, Boolean.FALSE);
				paragraphOptionsTextPaneReference.setCharacterAttributes(sast, false);
				supscflag = false;
			}
		}
	}

	public void subScriptText() {
		paragraphOptionsStyledDocumentReference = paragraphOptionsTextPaneReference.getStyledDocument();
		boolean subscriptflag = false;
		String text = paragraphOptionsTextPaneReference.getSelectedText();
		if (text != null) {
			for (int i = paragraphOptionsTextPaneReference.getSelectionStart()
					+ 1; i < paragraphOptionsTextPaneReference.getSelectionEnd(); i++) {
				Element element = paragraphOptionsStyledDocumentReference.getCharacterElement(i);
				AttributeSet aset = element.getAttributes();
				if (aset.containsAttribute(StyleConstants.Subscript, Boolean.TRUE)) {
					aset = null;
					subscriptflag = true;
				} else {
					subscriptflag = false;
					break;
				}
			}
			if (!subscriptflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Subscript, Boolean.TRUE);
				paragraphOptionsStyledDocumentReference.setCharacterAttributes(
						paragraphOptionsTextPaneReference.getSelectionStart(),
						paragraphOptionsTextPaneReference.getSelectionEnd()
								- paragraphOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Subscript, Boolean.FALSE);
				paragraphOptionsStyledDocumentReference.setCharacterAttributes(
						paragraphOptionsTextPaneReference.getSelectionStart(),
						paragraphOptionsTextPaneReference.getSelectionEnd()
								- paragraphOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			}
		} else {
			if (!subscflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Subscript, Boolean.TRUE);
				paragraphOptionsTextPaneReference.setCharacterAttributes(sast, false);
				subscflag = true;
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Subscript, Boolean.FALSE);
				paragraphOptionsTextPaneReference.setCharacterAttributes(sast, false);
				subscflag = false;
			}
		}
	}
}
