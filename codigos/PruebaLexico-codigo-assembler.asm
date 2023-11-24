.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data

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
main_ca_m_n_z:
CMP recursion_flag, 1
JNE CONTINUAR_EJECUCION_MAIN_CA_M_N_Z 
invoke MessageBox, NULL, addr error_recursividad, addr error_recursividad, MB_OK
invoke ExitProcess, 0
CONTINUAR_EJECUCION_MAIN_CA_M_N_Z: 
MOV recursion_flag, 1
MOV recursion_flag, 0
RET
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main_ca_m:
CMP recursion_flag, 2
JNE CONTINUAR_EJECUCION_MAIN_CA_M 
invoke MessageBox, NULL, addr error_recursividad, addr error_recursividad, MB_OK
invoke ExitProcess, 0
CONTINUAR_EJECUCION_MAIN_CA_M: 
MOV recursion_flag, 2
MOV recursion_flag, 0
RET
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main:
invoke ExitProcess, 0
end main
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main_ca_m_n:
CMP recursion_flag, 3
JNE CONTINUAR_EJECUCION_MAIN_CA_M_N 
invoke MessageBox, NULL, addr error_recursividad, addr error_recursividad, MB_OK
invoke ExitProcess, 0
CONTINUAR_EJECUCION_MAIN_CA_M_N: 
MOV recursion_flag, 3
MOV recursion_flag, 0
RET
