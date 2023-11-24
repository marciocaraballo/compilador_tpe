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
@aux1 REAL4 ?
variable_print_1 DB " HOLA desde fun1() en m() en ca ", 0
variable_print_0 DB " HOLA DESDE q() ", 0
ww_main_ca_m_fun1 REAL4 ?
variable_print_2 DB " HOLA DESDE s() ", 0

zz_main_ca_m_fun1 REAL4 ?
xx_main_ca_m REAL4 ?
z_main
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
CALL main_ca_m_fun1
MOV recursion_flag, 0
RET
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main_a_q:
CMP recursion_flag, 2
JNE CONTINUAR_EJECUCION_MAIN_A_Q 
invoke MessageBox, NULL, addr error_recursividad, addr error_recursividad, MB_OK
invoke ExitProcess, 0
CONTINUAR_EJECUCION_MAIN_A_Q: 
MOV recursion_flag, 2
invoke MessageBox, NULL, addr variable_print_0, addr variable_print_0, MB_OK
MOV recursion_flag, 0
RET
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main:
CALL main_a_q
CALL main_a_s
CALL main_ca_m
invoke ExitProcess, 0
end main
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main_ca_m_fun1:
CMP recursion_flag, 3
JNE CONTINUAR_EJECUCION_MAIN_CA_M_FUN1 
invoke MessageBox, NULL, addr error_recursividad, addr error_recursividad, MB_OK
invoke ExitProcess, 0
CONTINUAR_EJECUCION_MAIN_CA_M_FUN1: 
MOV recursion_flag, 3
FLD xx_main_ca_m
FLD zz_main_ca_m_fun1
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
FLD a_main_ca
FLD @aux0
FADD 
FSTP @aux1
FLD @aux1
FLD zero
FCOMPP
FSTSW AX
SAHF
JE FIN_SUMA_2
FLD @aux1
FLD maximo_rango_positivo
FCOMPP
FSTSW AX
SAHF
JB ERROR_SUMA_FLOTANTE_2
FLD @aux1
FLD minimo_rango_negativo
FCOMPP
FSTSW AX
SAHF
JA ERROR_SUMA_FLOTANTE_2
FLD @aux1
FLD maximo_rango_negativo
FCOMPP
FSTSW AX
SAHF
JA FIN_SUMA_2
FLD @aux1
FLD minimo_rango_positivo
FCOMPP
FSTSW AX
SAHF
JB FIN_SUMA_2
ERROR_SUMA_FLOTANTE_2:
invoke MessageBox, NULL, addr overflow_flotantes, addr overflow_flotantes, MB_OK
invoke ExitProcess, 0
FIN_SUMA_2:
FLD @aux1
FSTP ww_main_ca_m_fun1
invoke MessageBox, NULL, addr variable_print_1, addr variable_print_1, MB_OK
MOV recursion_flag, 0
RET
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main_a_s:
CMP recursion_flag, 4
JNE CONTINUAR_EJECUCION_MAIN_A_S 
invoke MessageBox, NULL, addr error_recursividad, addr error_recursividad, MB_OK
invoke ExitProcess, 0
CONTINUAR_EJECUCION_MAIN_A_S: 
MOV recursion_flag, 4
invoke MessageBox, NULL, addr variable_print_2, addr variable_print_2, MB_OK
MOV recursion_flag, 0
RET
