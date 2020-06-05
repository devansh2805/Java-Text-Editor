package editortools;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorDialog {
	public static final int FOREGROUNDCOLOR = 0;
	public static final int BACKGROUNDCOLOR = 1;
	private int colorType;
	private JTextPane colorDialogTextPaneReference;
	private StyledDocument colorDialogStyledDocumentReference;
	private JFrame colorDialogFrameReference;

	public ColorDialog(JTextPane textPane, int state, JFrame frameObject) {
		colorDialogTextPaneReference = textPane;
		colorDialogStyledDocumentReference = colorDialogTextPaneReference.getStyledDocument();
		colorDialogFrameReference = frameObject;
		colorType = state;
	}

	public void showColorDialog() {
		JColorChooser colorChooser = new JColorChooser();
		AbstractColorChooserPanel[] panelsList = colorChooser.getChooserPanels();
		for (AbstractColorChooserPanel panel : panelsList) {
			if (!("DefaultSwatchChooserPanel".equals(panel.getClass().getSimpleName()))) {
				colorChooser.removeChooserPanel(panel);
			}
		}

		ActionListener okButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				colorDialogStyledDocumentReference = colorDialogTextPaneReference.getStyledDocument();
				Color selectedColor = colorChooser.getColor();
				if (selectedColor == null) {
					return;
				} else {
					String text = colorDialogTextPaneReference.getSelectedText();
					if (text != null) {
						SimpleAttributeSet sast = new SimpleAttributeSet();
						if (colorType == ColorDialog.FOREGROUNDCOLOR) {
							StyleConstants.setForeground(sast, selectedColor);
						}
						if (colorType == ColorDialog.BACKGROUNDCOLOR) {
							StyleConstants.setBackground(sast, selectedColor);
						}
						colorDialogStyledDocumentReference.setCharacterAttributes(
								colorDialogTextPaneReference.getSelectionStart(),
								colorDialogTextPaneReference.getSelectionEnd()
										- colorDialogTextPaneReference.getSelectionStart(),
								sast, false);
					} else {
						SimpleAttributeSet sast = new SimpleAttributeSet();
						if (colorType == ColorDialog.FOREGROUNDCOLOR) {
							StyleConstants.setForeground(sast, selectedColor);
						}
						if (colorType == ColorDialog.BACKGROUNDCOLOR) {
							StyleConstants.setBackground(sast, selectedColor);
						}
						colorDialogTextPaneReference.setCharacterAttributes(sast, false);
					}
				}
			}
		};

		ActionListener cancelButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				return;
			}
		};
		JDialog colorChooserDialog = JColorChooser.createDialog(colorDialogFrameReference, "Select a Color", false,
				colorChooser, okButtonActionListener, cancelButtonActionListener);
		colorChooserDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		colorChooserDialog.setVisible(true);
	}
}
