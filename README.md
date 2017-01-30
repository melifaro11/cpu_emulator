# CPU Emulator #
The CPU Emulator is a simple Java-application for educational purposes, which demonstrate execution of the commands on CPU x86-architecutre.

# Description #

## Memory ##
...

## Available commands ##

Command       | Description                                     | Usage example
------------- | ---------------------------------------------------------------------------------------------------------
out val       | Output val into console (is a [syntactic sugar](https://en.wikipedia.org/wiki/Syntactic_sugar) for emulator)
mov op1, op2  | Copies the value of op2 into op1                | mov ax, 3
                                                                | mov cx, [ax]
                                                                | mov [1], "Hallo world"
call x        | 
ret           | Return from subroutine unconditionally to an address from a stack head
push op1      | 
pop X         | 
loop X        | 
jmp Х         | 
inc X         | 
dec X         | 
add X, Y      | 
sub X, Y      | 
cmp Х, Y      | 
je Х          | 
jne Х         | 
jl X          | 
jg X          | 
