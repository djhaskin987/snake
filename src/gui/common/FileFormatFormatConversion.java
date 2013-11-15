package gui.common;

import model.Format;

public class FileFormatFormatConversion {
	
	private FileFormatFormatConversion() {
		// no instantiation 
	}
	
	public static Format toFormat(FileFormat ff) {
		Format format;
		switch (ff) {
			case PDF:
				format = Format.PDF;
				break;
			case HTML:
				format = Format.HTML;
				break;
			default:
				format = null;
				break;
		}
		return format;
	}
}
