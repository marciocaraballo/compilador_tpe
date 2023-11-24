.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
@aux0 DW ?
a_main DW ?

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
MOV AX, 32767
IMUL AX, 2
JNO CONTINUAR_EJECUCION 
invoke MessageBox, NULL, addr overflow_enteros, addr overflow_enteros, MB_OK
invoke ExitProcess, 0
CONTINUAR_EJECUCION:MOV @aux0, AX
MOV AX, @aux0
MOV a_main, AX
invoke ExitProcess, 0
end main
