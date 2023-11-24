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
f_main DW ?
@aux1 DW ?
variable_print_0 DB " Se llamo a m() de la CLASS ca ", 0
a_main DW ?
variable_print_1 DB " Se llamo a fun1() de de main ", 0
variable_real_0 REAL4 1.0
c_main_fun1 DW ?
b_main REAL4 ?
c_main REAL4 ?
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
FLD variable_real_0
FSTP b_main
invoke MessageBox, NULL, addr variable_print_0, addr variable_print_0, MB_OK
MOV recursion_flag, 0
RET
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main_fun1:
CMP recursion_flag, 2
JNE CONTINUAR_EJECUCION_MAIN_FUN1 
invoke MessageBox, NULL, addr error_recursividad, addr error_recursividad, MB_OK
invoke ExitProcess, 0
CONTINUAR_EJECUCION_MAIN_FUN1: 
MOV recursion_flag, 2
MOV AX, 3
MOV c_main_fun1, AX
MOV AX, a_main
ADD AX, c_main_fun1
MOV @aux0, AX
MOV AX, @aux0
ADD AX, 1
MOV @aux1, AX
MOV AX, @aux1
MOV a_main, AX
invoke MessageBox, NULL, addr variable_print_1, addr variable_print_1, MB_OK
MOV recursion_flag, 0
RET
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main:
CALL main_fun1
MOV AX, 10
MOV f_main, AX
L5: CALL main_fun1
CALL main_ca_m
MOV AX, a_main
CMP AX, f_main
JGE L16
JMP L5
L16: invoke ExitProcess, 0
end main
