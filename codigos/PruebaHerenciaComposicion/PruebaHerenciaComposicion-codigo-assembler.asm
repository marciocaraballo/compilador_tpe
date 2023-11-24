.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
variable_real_0 REAL4 2.3
variable_real_1 REAL4 1.2

a_main_b1_ca DW ?
c_main_b1_ca DW ?
b_main_b1 REAL4 ?
variable_print_1 DB " HOLA cb ", 0
variable_print_0 DB " HOLA ca ", 0
d_main_b1 REAL4 ?
a_main_a1 DW ?
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
main_ca_m:
CMP recursion_flag, 1
JNE CONTINUAR_EJECUCION_MAIN_CA_M 
invoke MessageBox, NULL, addr error_recursividad, addr error_recursividad, MB_OK
invoke ExitProcess, 0
CONTINUAR_EJECUCION_MAIN_CA_M: 
MOV recursion_flag, 1
invoke MessageBox, NULL, addr variable_print_0, addr variable_print_0, MB_OK
MOV recursion_flag, 0
RET
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main_cb_n:
CMP recursion_flag, 2
JNE CONTINUAR_EJECUCION_MAIN_CB_N 
invoke MessageBox, NULL, addr error_recursividad, addr error_recursividad, MB_OK
invoke ExitProcess, 0
CONTINUAR_EJECUCION_MAIN_CB_N: 
MOV recursion_flag, 2
invoke MessageBox, NULL, addr variable_print_1, addr variable_print_1, MB_OK
MOV recursion_flag, 0
RET
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main:
MOV AX, 3
MOV a_main_a1, AX
MOV AX, 3
MOV a_main_b1_ca, AX
FLD variable_real_0
FSTP d_main_b1
FLD variable_real_1
FSTP b_main_b1
MOV AX, 1
MOV c_main_b1_ca, AX
CALL main_ca_m
CALL main_cb_n
invoke ExitProcess, 0
end main
