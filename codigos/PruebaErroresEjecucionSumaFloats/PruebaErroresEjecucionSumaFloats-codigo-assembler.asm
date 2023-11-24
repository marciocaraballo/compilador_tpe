.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
@aux0 REAL4 ?
b_main REAL4 ?
a_main REAL4 ?

variable_real_0 REAL4 3.40282347E+38
recursion_flag DW 0 
error_recursividad db " No se admite recursividad de invocación a funciones " , 0
overflow_flotantes db " La suma de los valores ha sobrepasado el rango " , 0
overflow_enteros db " El producto de los valores ha sobrepasado el rango " , 0
zero REAL4 0.0
minimo_rango_negativo REAL4 -3.40282347E+38
maximo_rango_negativo REAL4 -1.17549435E-38
minimo_rango_positivo REAL4 1.17549435E-38
maximo_rango_positivo REAL4 3.40282347E+38
aux_mem REAL4 ?
.code
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main:
FLD variable_real_0
FSTP a_main
FLD variable_real_0
FSTP b_main
FLD a_main
FLD a_main
FADD 
FSTP @aux0
FLD @aux0
FLD zero
FCOMPP
FSTSW AX
SAHF
JE FIN_SUMA_1
FLD @aux0
FLD maximo_rango_positivo
FCOMPP
FSTSW AX
SAHF
JB ERROR_SUMA_FLOTANTE_1
FLD @aux0
FLD minimo_rango_negativo
FCOMPP
FSTSW AX
SAHF
JA ERROR_SUMA_FLOTANTE_1
FLD @aux0
FLD maximo_rango_negativo
FCOMPP
FSTSW AX
SAHF
JA FIN_SUMA_1
FLD @aux0
FLD minimo_rango_positivo
FCOMPP
FSTSW AX
SAHF
JB FIN_SUMA_1
ERROR_SUMA_FLOTANTE_1:
invoke MessageBox, NULL, addr overflow_flotantes, addr overflow_flotantes, MB_OK
invoke ExitProcess, 0
FIN_SUMA_1:
FLD @aux0
FSTP b_main
invoke ExitProcess, 0
end main
