.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
b_main DT ?
z_main DW ?
a_main DT ?
d_main DT ?
c_main DW ?
e_main DD ?

recursion_flag DW 0 
error_recursividad db " No se admite recursividad de invocación a funciones " , 0
overflow_flotantes db " La suma de los valores ha sobrepasado el rango " , 0
overflow_enteros db " El producto de los valores ha sobrepasado el rango " , 0
minimo_rango_positivo DT 1.17549435E-38
maximo_rango_positivo DT 3.40282347E+38
aux_mem DW ?
.code
;-------------------------- ESTO ES PARA MEJORAR VISUALIZACION ----------------- 
main:
invoke ExitProcess, 0
end main
