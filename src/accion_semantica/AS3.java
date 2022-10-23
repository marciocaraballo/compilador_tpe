package accion_semantica;

import compilador.FileReaderHelper;

public class AS3 extends AccionSemantica {
	@Override
	public int ejecutar(FileReaderHelper fileHelper, StringBuilder lexema, char nextCharacter) {
		
		lexema.append(nextCharacter);

		return -1;
	}
}
