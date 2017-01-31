# CPU Emulator #
The CPU Emulator is a simple Java-application for educational purposes, which demonstrate execution of the commands on CPU x86-architecutre.

# Description #

## Memory ##
...

## Operands ##

The following table shows types of operands, which is allowed for using in all commands

Operand type             |    Description
-------------------------|----------------------------------------
`"Hallo world"`, `3`     | Immediate value
`ax`, `bx`, `cx` ...     | Name of a register
`[x]`                    | Value at the memory addresse `x` (`x` - immediate value or a register)
`[reg + c]`              | Value at the memory addresse `reg + c`, where `reg` is a register and `c` a numerical constant (For ex.: `mov [ax + 1], 2`

## Commands ##

Command       | Description
------------- | ---------------------------------------------------------------------------------------------------------
out val       | Output val into console (is a [syntactic sugar](https://en.wikipedia.org/wiki/Syntactic_sugar) for the emulator)
mov op1, op2  | Copies the value of op2 into op1
`call x`      | Saves procedure linking information on the stack and branches to the procedure (called procedure) specified with the destination (target) operand.
ret           | Return from subroutine unconditionally to an address from a stack head
push op1      | 
pop X         | 
loop X        | 
jmp Х         | 
`inc X`       | Adds 1 to the destination operand
dec X         | 
`add X, Y`    | Adds the first operand X (destination) and the second operand Y (source) and stores the result in the destination operand X
sub X, Y      | 
`cmp Х, Y`    | Compares the first source operand with the second source operand and sets the ZF-flag (Zero Flag) according to the results.
je Х          | 
jne Х         | 
jl X          | 
jg X          | 

*Note: because the emulator has a "simplified" x86 instructions, there are certain differences in the commands. For example, unlike "mov" command in x86, "mov" in the emulator allows you to use 2 memory cells in command: `mov [ax], [bx]`*
