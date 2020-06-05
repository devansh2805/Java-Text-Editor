package editortools;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.event.DocumentListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileOptions implements Serializable {
	private static final long serialVersionUID = 3545910356586563394L;
	private JFrame fileOptionsFrameReference;
	private JTextPane fileTextPaneReference;
	private File fileOptionsFileReference;
	private JFileChooser myFileChooser;
	private FileNameExtensionFilter myFileFilter;
	private FileSecurityOptions fileSecurityOptionsObject;
	public Boolean saveasflag = false, saveflag = false, editflag = false;
	protected String extension = "", mypassword = "";
	private DocumentListener fileDocumentListenerReference;
	private EditOptions fileEditOptionsReference;

	public FileOptions(File fob, JTextPane tp, JFrame jf, DocumentListener dl, EditOptions eo) {
		fileEditOptionsReference = eo;
		fileOptionsFrameReference = jf;
		fileTextPaneReference = tp;
		fileOptionsFileReference = fob;
		fileDocumentListenerReference = dl;
		fileSecurityOptionsObject = new FileSecurityOptions(fileTextPaneReference, fileOptionsFrameReference);
		myFileChooser = new JFileChooser();
		myFileChooser.setAcceptAllFileFilterUsed(false);
		myFileFilter = new FileNameExtensionFilter("Password Protected File", "ppf");
		myFileChooser.addChoosableFileFilter(myFileFilter);
	}

	public boolean newFile() {
		if (!saveflag) {
			if (!editflag) {
				JOptionPane.showMessageDialog(fileTextPaneReference, "New File is Already Open!");
			} else {
				Object options[] = { "Save File", "Don't Save", "Cancel" };
				int selectedOption = JOptionPane.showOptionDialog(fileTextPaneReference,
						"Do you want to save changes ?", "You have unsaved changes!", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, null);
				switch (selectedOption) {
				case 0:
					this.saveFile();
					fileOptionsFrameReference.dispose();
					return false;
				case 1:
					fileOptionsFrameReference.dispose();
					return false;
				case 2:
					return true;
				}
			}
			return true;
		} else {
			fileOptionsFrameReference.dispose();
			return false;
		}
	}

	public void openFile() {
		myFileChooser.setDialogTitle("Select File to Open");
		int selectedValue = myFileChooser.showOpenDialog(fileOptionsFrameReference);
		if (selectedValue == JFileChooser.APPROVE_OPTION) {
			fileOptionsFileReference = myFileChooser.getSelectedFile();
			if (fileOptionsFileReference.exists()) {
				int index = fileOptionsFileReference.toString().lastIndexOf(".");
				if (index > 0) {
					extension = fileOptionsFileReference.toString().substring(index + 1);
				} else {
					extension = "";
				}
				if (extension.equals("ppf")) {
					if (fileOptionsFileReference.length() == 0L) {
						JOptionPane.showMessageDialog(fileOptionsFrameReference,
								"It seems like the requested file was not created using ProNote!\nfor Security reasons please set Password",
								"ERROR!!", JOptionPane.ERROR_MESSAGE);
						this.saveAsFile();
						return;
					} else if (fileOptionsFileReference.length() % 8 != 0) {
						JOptionPane.showMessageDialog(fileOptionsFrameReference,
								"It seems like the requested file was not created using ProNote!\nfor Security reasons please set Password",
								"ERROR!!", JOptionPane.ERROR_MESSAGE);
						try (BufferedReader bf = new BufferedReader(new FileReader(fileOptionsFileReference));) {
							String content = "", line = "";
							while ((line = bf.readLine()) != null) {
								content += line + "\n";
							}
							fileTextPaneReference.setText(content);
							this.saveAsFile();
						} catch (IOException e) {

						}
						return;
					} else {
						mypassword = fileSecurityOptionsObject.getPassword();
						boolean decryptFlag = fileSecurityOptionsObject.decryptFileData(mypassword,
								fileOptionsFileReference);
						if (decryptFlag) {
							try (InputStream fileinputstream = new FileInputStream(fileOptionsFileReference);
									ObjectInputStream objectinputstream = new ObjectInputStream(fileinputstream)) {
								DefaultStyledDocument readStyledDocument = (DefaultStyledDocument) objectinputstream
										.readObject();
								fileTextPaneReference.setStyledDocument((StyledDocument) readStyledDocument);
								fileTextPaneReference.getStyledDocument()
										.addDocumentListener(fileDocumentListenerReference);
								fileTextPaneReference.getStyledDocument()
										.addUndoableEditListener(fileEditOptionsReference.undoManagerObject);
								fileSecurityOptionsObject.encryptFileData(mypassword, fileOptionsFileReference);
								fileOptionsFrameReference.setTitle(fileOptionsFileReference.getName());
								saveasflag = true;
								saveflag = true;
								editflag = false;
							} catch (ClassNotFoundException | IOException e) {
								JOptionPane.showMessageDialog(fileTextPaneReference, "Error Reading File", "ERROR!!",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(fileOptionsFrameReference, "Wrong Password Cannot Open File",
									"ERROR!!", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				} else {
					JOptionPane.showMessageDialog(myFileChooser, "Application Can only open .ppf Files!!", "ERROR!!",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else {
				JOptionPane.showMessageDialog(myFileChooser, "File Doesnot Exist!", "ERROR!!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
			return;
		}
	}

	public void openFile(File filePassed) {
		fileOptionsFileReference = filePassed;
		if (fileOptionsFileReference.exists()) {
			int index = fileOptionsFileReference.toString().lastIndexOf(".");
			if (index > 0) {
				extension = fileOptionsFileReference.toString().substring(index + 1);
			} else {
				extension = "";
			}
			if (extension.equals("ppf")) {
				if (fileOptionsFileReference.length() == 0L) {
					JOptionPane.showMessageDialog(fileOptionsFrameReference,
							"It seems like the requested file was not created using ProNote!\nfor Security reasons please set Password",
							"ERROR!!", JOptionPane.ERROR_MESSAGE);
					this.saveAsFile();
					return;
				} else if (fileOptionsFileReference.length() % 8 != 0) {
					JOptionPane.showMessageDialog(fileOptionsFrameReference,
							"It seems like the requested file was not created using ProNote!\nfor Security reasons please set Password",
							"ERROR!!", JOptionPane.ERROR_MESSAGE);
					try (BufferedReader bf = new BufferedReader(new FileReader(fileOptionsFileReference));) {
						String content = "", line = "";
						while ((line = bf.readLine()) != null) {
							content += line + "\n";
						}
						fileTextPaneReference.setText(content);
						this.saveAsFile();
					} catch (IOException e) {

					}
					return;
				} else {
					mypassword = fileSecurityOptionsObject.getPassword();
					boolean decryptFlag = fileSecurityOptionsObject.decryptFileData(mypassword,
							fileOptionsFileReference);
					if (decryptFlag) {
						try (InputStream fileinputstream = new FileInputStream(fileOptionsFileReference);
								ObjectInputStream objectinputstream = new ObjectInputStream(fileinputstream)) {
							DefaultStyledDocument readStyledDocument = (DefaultStyledDocument) objectinputstream
									.readObject();
							fileTextPaneReference.setStyledDocument((StyledDocument) readStyledDocument);
							fileTextPaneReference.getStyledDocument()
									.addDocumentListener(fileDocumentListenerReference);
							fileTextPaneReference.getStyledDocument()
									.addUndoableEditListener(fileEditOptionsReference.undoManagerObject);
							fileSecurityOptionsObject.encryptFileData(mypassword, fileOptionsFileReference);
							fileOptionsFrameReference.setTitle(fileOptionsFileReference.getName());
							saveasflag = true;
							saveflag = true;
							editflag = false;
						} catch (IOException | ClassNotFoundException e) {
							JOptionPane.showMessageDialog(fileTextPaneReference, "Error Reading File", "ERROR!",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(fileOptionsFrameReference,
								"Cannot Open File\nEither Password is wrong\nor File is hampered externally", "ERROR!!",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			} else {
				JOptionPane.showMessageDialog(myFileChooser, "Application Can only open .ppf Files!!", "ERROR!!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
			JOptionPane.showMessageDialog(myFileChooser, "File Doesnot Exist!", "ERROR!!", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void saveFile() {
		if (!saveasflag) {
			this.saveAsFile();
		} else {
			this.save();
		}
	}

	public void save() {
		try (OutputStream fileoutputstream = new FileOutputStream(fileOptionsFileReference);
				ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream)) {
			fileTextPaneReference.getStyledDocument().removeDocumentListener(fileDocumentListenerReference);
			fileTextPaneReference.getStyledDocument()
					.removeUndoableEditListener(fileEditOptionsReference.undoManagerObject);
			objectoutputstream.writeObject((DefaultStyledDocument) fileTextPaneReference.getStyledDocument());
			fileTextPaneReference.getStyledDocument().addDocumentListener(fileDocumentListenerReference);
			fileTextPaneReference.getStyledDocument()
					.addUndoableEditListener(fileEditOptionsReference.undoManagerObject);
			fileSecurityOptionsObject.encryptFileData(mypassword, fileOptionsFileReference);
			fileOptionsFrameReference.setTitle(fileOptionsFileReference.getName());
			saveasflag = true;
			saveflag = true;
			editflag = false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(fileTextPaneReference, "Error Saving File", "ERROR!",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void saveAsFile() {
		myFileChooser.setDialogTitle("Save As");
		int selectedValue = myFileChooser.showSaveDialog(fileOptionsFrameReference);
		if (selectedValue == JFileChooser.APPROVE_OPTION) {
			fileOptionsFileReference = myFileChooser.getSelectedFile();
			int index = fileOptionsFileReference.toString().lastIndexOf(".");
			if (index > 0) {
				extension = fileOptionsFileReference.toString().substring(index + 1);
				if (!extension.equals("ppf")) {
					fileOptionsFileReference = new File(
							fileOptionsFileReference.toString().substring(0, index) + ".ppf");
				}
			} else {
				fileOptionsFileReference = new File(fileOptionsFileReference.toString() + ".ppf");
			}
			if (saveasflag) {
				this.save();
			} else {
				mypassword = fileSecurityOptionsObject.setPassword();
				this.save();
			}
		} else
			return;
	}

	public void exitFile() {
		if (!editflag) {
			fileOptionsFrameReference.dispose();
		} else {
			Object options[] = { "Save File", "Don't Save", "Cancel" };
			int selectedOption = JOptionPane.showOptionDialog(fileTextPaneReference, "Do you want to save changes ?",
					"You have unsaved changes!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
					options, null);
			switch (selectedOption) {
			case 0:
				this.saveFile();
				fileOptionsFrameReference.dispose();
				break;
			case 1:
				fileOptionsFrameReference.dispose();
				break;
			case 2:
				break;
			}
		}
	}
}