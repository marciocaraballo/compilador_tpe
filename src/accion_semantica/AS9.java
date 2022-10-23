package accion_semantica;

import compilador.FileReaderHelper;

public class AS9 extends AccionSemantica {
	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		lexema.setLength(0); 
		
		return -1;
	}
}
