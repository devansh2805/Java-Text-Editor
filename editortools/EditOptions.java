package editortools;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class EditOptions {
	private JTextPane editTextPaneReference;
	private StyledDocument editStyledDocumentReference;
	protected UndoManager undoManagerObject;

	public EditOptions(JTextPane tp) {
		editTextPaneReference = tp;
		editStyledDocumentReference = editTextPaneReference.getStyledDocument();
		undoManagerObject = new UndoManager();
		editStyledDocumentReference.addUndoableEditListener(undoManagerObject);
	}

	public void cutText() {
		editTextPaneReference.cut();
	}

	public void copyText() {
		editTextPaneReference.copy();
	}

	public void pasteText() {
		editTextPaneReference.paste();
	}

	public void deleteText() {
		if (editTextPaneReference.getSelectedText() != null) {
			editTextPaneReference.replaceSelection("");
		} else {
			try {
				editStyledDocumentReference.remove(editTextPaneReference.getCaretPosition(), 1);
			} catch (BadLocationException e) {

			}
		}
	}

	public void undoChanges() {
		try {
			undoManagerObject.undo();
		} catch (CannotUndoException e) {

		}
	}

	public void redoChanges() {
		try {
			undoManagerObject.redo();
		} catch (CannotRedoException e) {

		}
	}

	public void selectAllText() {
		editTextPaneReference.setSelectionStart(0);
		editTextPaneReference.setSelectionEnd(editStyledDocumentReference.getLength());
		editTextPaneReference.getCaret().setSelectionVisible(true);
	}

	public void timeStamp() {
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String[] timeStampFields = timestamp.split("[.]");
		String timeStamp = timeStampFields[0] + "/" + timeStampFields[1] + "/" + timeStampFields[2] + " "
				+ timeStampFields[3] + ":" + timeStampFields[4] + ":" + timeStampFields[5];
		try {
			editStyledDocumentReference.insertString(editStyledDocumentReference.getLength(), "\n" + timeStamp, null);
		} catch (BadLocationException e) {

		}
	}
}