package model.serialization.db;

import java.io.File;

public class JDBCFixture {
	
	private JDBCWrapper wrapper;

	public JDBCFixture() {
		File file = new File("test.sqlite");
		if(file.exists()) {
			file.delete();
		}
		wrapper = new JDBCWrapper("test.sqlite");
	}
	
	public JDBCWrapper getWrapper() {
		return wrapper;
	}

}
