package editortools;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.AttributeSet;

public class FontOptions {
	private JTextPane fontOptionsTextPaneReference;
	private StyledDocument fontOptionsStyledDocumentReference;
	private JFrame fontOptionsFrameReference;
	private boolean bflag = false, iflag = false, uflag = false, stflag = false;

	public FontOptions(JTextPane jtp, JFrame fr) {
		fontOptionsFrameReference = fr;
		fontOptionsTextPaneReference = jtp;
		fontOptionsStyledDocumentReference = fontOptionsTextPaneReference.getStyledDocument();
	}

	public void boldSettings() {
		fontOptionsStyledDocumentReference = fontOptionsTextPaneReference.getStyledDocument();
		boolean boldflag = false;
		String text = fontOptionsTextPaneReference.getSelectedText();
		if (text != null) {
			for (int i = fontOptionsTextPaneReference.getSelectionStart() + 1; i < fontOptionsTextPaneReference
					.getSelectionEnd(); i++) {
				Element element = fontOptionsStyledDocumentReference.getCharacterElement(i);
				AttributeSet aset = element.getAttributes();
				if (aset.containsAttribute(StyleConstants.Bold, Boolean.TRUE)) {
					aset = null;
					boldflag = true;
				} else {
					boldflag = false;
					break;
				}
			}
			if (!boldflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Bold, Boolean.TRUE);
				fontOptionsStyledDocumentReference.setCharacterAttributes(
						fontOptionsTextPaneReference.getSelectionStart(), fontOptionsTextPaneReference.getSelectionEnd()
								- fontOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Bold, Boolean.FALSE);
				fontOptionsStyledDocumentReference.setCharacterAttributes(
						fontOptionsTextPaneReference.getSelectionStart(), fontOptionsTextPaneReference.getSelectionEnd()
								- fontOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			}
		} else {
			if (!bflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Bold, Boolean.TRUE);
				fontOptionsTextPaneReference.setCharacterAttributes(sast, false);
				bflag = true;
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Bold, Boolean.FALSE);
				fontOptionsTextPaneReference.setCharacterAttributes(sast, false);
				bflag = false;
			}
		}
	}

	public void italicSettings() {
		fontOptionsStyledDocumentReference = fontOptionsTextPaneReference.getStyledDocument();
		boolean italicflag = false;
		String text = fontOptionsTextPaneReference.getSelectedText();
		if (text != null) {
			for (int i = fontOptionsTextPaneReference.getSelectionStart() + 1; i < fontOptionsTextPaneReference
					.getSelectionEnd(); i++) {
				Element element = fontOptionsStyledDocumentReference.getCharacterElement(i);
				AttributeSet aset = element.getAttributes();
				if (aset.containsAttribute(StyleConstants.Italic, Boolean.TRUE)) {
					aset = null;
					italicflag = true;
				} else {
					italicflag = false;
					break;
				}
			}
			if (!italicflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Italic, Boolean.TRUE);
				fontOptionsStyledDocumentReference.setCharacterAttributes(
						fontOptionsTextPaneReference.getSelectionStart(), fontOptionsTextPaneReference.getSelectionEnd()
								- fontOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Italic, Boolean.FALSE);
				fontOptionsStyledDocumentReference.setCharacterAttributes(
						fontOptionsTextPaneReference.getSelectionStart(), fontOptionsTextPaneReference.getSelectionEnd()
								- fontOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			}
		} else {
			if (!iflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Italic, Boolean.TRUE);
				fontOptionsTextPaneReference.setCharacterAttributes(sast, false);
				iflag = true;
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Italic, Boolean.FALSE);
				fontOptionsTextPaneReference.setCharacterAttributes(sast, false);
				iflag = false;
			}
		}
	}

	public void underlineSettings() {
		fontOptionsStyledDocumentReference = fontOptionsTextPaneReference.getStyledDocument();
		boolean underlineflag = false;
		String text = fontOptionsTextPaneReference.getSelectedText();
		if (text != null) {
			for (int i = fontOptionsTextPaneReference.getSelectionStart() + 1; i < fontOptionsTextPaneReference
					.getSelectionEnd(); i++) {
				Element element = this.fontOptionsStyledDocumentReference.getCharacterElement(i);
				AttributeSet aset = element.getAttributes();
				if (aset.containsAttribute(StyleConstants.Underline, Boolean.TRUE)) {
					aset = null;
					underlineflag = true;
				} else {
					underlineflag = false;
					break;
				}
			}
			if (!underlineflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Underline, Boolean.TRUE);
				fontOptionsStyledDocumentReference.setCharacterAttributes(
						fontOptionsTextPaneReference.getSelectionStart(), fontOptionsTextPaneReference.getSelectionEnd()
								- fontOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Underline, Boolean.FALSE);
				fontOptionsStyledDocumentReference.setCharacterAttributes(
						fontOptionsTextPaneReference.getSelectionStart(), fontOptionsTextPaneReference.getSelectionEnd()
								- fontOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			}
		} else {
			if (!uflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Underline, Boolean.TRUE);
				fontOptionsTextPaneReference.setCharacterAttributes(sast, false);
				uflag = true;
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.Underline, Boolean.FALSE);
				fontOptionsTextPaneReference.setCharacterAttributes(sast, false);
				uflag = false;
			}
		}
	}

	public void strikeThroughSettings() {
		fontOptionsStyledDocumentReference = fontOptionsTextPaneReference.getStyledDocument();
		boolean strikethroughflag = false;
		String text = fontOptionsTextPaneReference.getSelectedText();
		if (text != null) {
			for (int i = fontOptionsTextPaneReference.getSelectionStart() + 1; i < fontOptionsTextPaneReference
					.getSelectionEnd(); i++) {
				Element element = fontOptionsStyledDocumentReference.getCharacterElement(i);
				AttributeSet aset = element.getAttributes();
				if (aset.containsAttribute(StyleConstants.StrikeThrough, Boolean.TRUE)) {
					aset = null;
					strikethroughflag = true;
				} else {
					strikethroughflag = false;
					break;
				}
			}
			if (!strikethroughflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.StrikeThrough, Boolean.TRUE);
				fontOptionsStyledDocumentReference.setCharacterAttributes(
						fontOptionsTextPaneReference.getSelectionStart(), fontOptionsTextPaneReference.getSelectionEnd()
								- fontOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.StrikeThrough, Boolean.FALSE);
				fontOptionsStyledDocumentReference.setCharacterAttributes(
						fontOptionsTextPaneReference.getSelectionStart(), fontOptionsTextPaneReference.getSelectionEnd()
								- fontOptionsTextPaneReference.getSelectionStart(),
						sast, false);
			}
		} else {
			if (!stflag) {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.StrikeThrough, Boolean.TRUE);
				fontOptionsTextPaneReference.setCharacterAttributes(sast, false);
				stflag = true;
			} else {
				SimpleAttributeSet sast = new SimpleAttributeSet();
				sast.addAttribute(StyleConstants.StrikeThrough, Boolean.FALSE);
				fontOptionsTextPaneReference.setCharacterAttributes(sast, false);
				stflag = false;
			}
		}
	}

	public void foregroundColorSettings() {
		ColorDialog foregroundColorDialog = new ColorDialog(fontOptionsTextPaneReference, ColorDialog.FOREGROUNDCOLOR,
				fontOptionsFrameReference);
		foregroundColorDialog.showColorDialog();
	}

	public void backgroundColorSettings() {
		ColorDialog backgroundColorDialog = new ColorDialog(fontOptionsTextPaneReference, ColorDialog.BACKGROUNDCOLOR,
				fontOptionsFrameReference);
		backgroundColorDialog.showColorDialog();
	}

	public void fontSelectionSettings() {
		FontChooser myfontchooser = new FontChooser(fontOptionsTextPaneReference, fontOptionsFrameReference);
		myfontchooser.showFontsAndFontSizes();
	}
}