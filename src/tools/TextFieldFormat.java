package tools;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TextFieldFormat extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int limit;
	private String type;
	private char c;
	private byte n;

	public TextFieldFormat(int limit, String type) {
		super();
		this.limit = limit;
		this.type = type;
	}

	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		}

		if ((getLength() + str.length()) <= limit) {
			switch (type) {
				case "integer":
					if (validInteger(str)) {
						super.insertString(offset, str, attr);
					}
				default:
					super.insertString(offset, str, attr);
			}
		}
	}

	public Boolean validInteger(String str) {
		n = 1;
		for (byte i = 0; i < str.length(); i++) {
			c = str.charAt(i);

			if (!Character.isDigit(c)) {
				n = 0;
			}
		}
		if (n != 0) {
			return true;
		} else {
			return false;
		}
	}
}