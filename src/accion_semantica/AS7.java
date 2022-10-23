package accion_semantica;

import compilador.FileReaderHelper;

public class AS7 extends AccionSemantica {
	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {	
		fileHelper.reset();
		return (int) lexema.charAt(0);
	}
}
