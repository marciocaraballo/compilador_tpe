package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderHelper {

	private BufferedReader reader = null;
	private Logger logger = null;
	
	public FileReaderHelper(Logger logger) {
		this.logger = logger;
	};
	
	public boolean open(String filePath) {
		try {
			reader = new BufferedReader(new FileReader(filePath));
			return true;
		} catch (FileNotFoundException e) {
			logger.logStartUpError("No se encontro la ruta del archivo de codigo: " + filePath);
		}
		
		return false;
	}
	
	/**  
	 * Lee el siguiente char y marca la posicion original
	 * En el caso de que necesitemos volver al input original
	 */
	public int nextChar() {
		
		int readChar = 0;
		
		try {
			reader.mark(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			readChar = reader.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return readChar;
	}
	
	/**
	 * Si queremos que el cursor quede en el ultimo char leido,
	 * como ya definimos una mark antes, podemos hacer un reset
	 * de la mark para que el cursor se posicione en el char original.
	 * De este modo, el cursor no avanza y se queda posicionado en el char,
	 * pudiendo volver a leer el mismo.
	 */
	public void reset() {
		try {
			reader.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
