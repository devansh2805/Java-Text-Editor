package editortools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JTextPane;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class FileSecurityOptions {
	private JFrame fileSecurityFrameReference;
	private JTextPane fileSecurityTextPaneReference;
	private JPasswordField fileSecurityPasswordFieldObject = new JPasswordField(40);
	private static final String ALGORITHM = "Blowfish";
	private String keyString = "";
	private String mypass = "";

	public FileSecurityOptions(JTextPane tp, JFrame fr) {
		fileSecurityFrameReference = fr;
		fileSecurityTextPaneReference = tp;
	}

	public String setPassword() {
		int pass = JOptionPane.showConfirmDialog(fileSecurityTextPaneReference, fileSecurityPasswordFieldObject,
				"Set Password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (pass == JOptionPane.OK_OPTION) {
			char[] gpass = fileSecurityPasswordFieldObject.getPassword();
			if (gpass.length == 0) {
				JOptionPane.showMessageDialog(fileSecurityFrameReference, "Entering Password is Mandatory",
						"WARNING!!!", JOptionPane.WARNING_MESSAGE);
				mypass = setPassword();
			} else {
				mypass = String.valueOf(gpass);
			}
		} else {
			JOptionPane.showMessageDialog(fileSecurityFrameReference, "Entering Password is Mandatory", "WARNING!!!",
					JOptionPane.WARNING_MESSAGE);
			mypass = setPassword();
		}
		fileSecurityPasswordFieldObject.setText("");
		return mypass;
	}

	public String getPassword() {
		int pass = JOptionPane.showConfirmDialog(fileSecurityTextPaneReference, fileSecurityPasswordFieldObject,
				"Enter Password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (pass == JOptionPane.OK_OPTION) {
			char[] gpass = fileSecurityPasswordFieldObject.getPassword();
			mypass = String.valueOf(gpass);
		} else {
			JOptionPane.showMessageDialog(fileSecurityFrameReference, "Entering Password is Mandatory", "WARNING!!!",
					JOptionPane.WARNING_MESSAGE);
			mypass = getPassword();
		}
		fileSecurityPasswordFieldObject.setText("");
		return mypass;
	}

	public boolean encryptFileData(String strkey, File filetobeenc) {
		keyString = strkey;
		return doCrypto(Cipher.ENCRYPT_MODE, filetobeenc);
	}

	public boolean decryptFileData(String strkey, File filetobedec) {
		keyString = strkey;
		return doCrypto(Cipher.DECRYPT_MODE, filetobedec);
	}

	public boolean doCrypto(int cipherMode, File filepassed) {
		try {
			FileInputStream fileInputStreamObject = new FileInputStream(filepassed);
			Key skeyspec = new SecretKeySpec(keyString.getBytes(), FileSecurityOptions.ALGORITHM);
			Cipher cipherObject = Cipher.getInstance(FileSecurityOptions.ALGORITHM);
			cipherObject.init(cipherMode, skeyspec);
			byte[] inpbytes = new byte[(int) filepassed.length()];
			fileInputStreamObject.read(inpbytes);
			fileInputStreamObject.close();
			byte[] opbytes = cipherObject.doFinal(inpbytes);
			FileOutputStream fosob = new FileOutputStream(filepassed);
			fosob.write(opbytes);
			fosob.close();
			return true;
		} catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException e) {
			return false;
		}
	}
}