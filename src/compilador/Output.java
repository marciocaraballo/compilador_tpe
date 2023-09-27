package compilador;

import java.io.FileWriter;
import java.io.IOException;

public class Output {

	String path;

	public Output(String path) {
		this.path = path;
	}

	public void saveFile(String file, String content) {
		FileWriter fw;
		try {

			String parsedPath = path.replace(".txt", "") + "-";

			fw = new FileWriter(parsedPath + file);
			fw.append(content);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
