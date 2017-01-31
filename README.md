# CPU Emulator #
The CPU Emulator is a simple Java-application for educational purposes, which demonstrate execution of the commands on x86 CPU architecutre.

# Description #
The emulator demonstrates in outline functioning of x86-architecture. The emulator has the following propertys:
- The Memory (RAM) to store programm command and data
- Set of registers
   - Instruction pointer `IP`
   - Stack pointer `SP`
   - Base pointer `BP`
   - Data registers`AX`, `BX`, `CX`, `DX`
- Set of commands: `out`, `mov`, `call`, `ret`, `mul`, `div`, `jmp`...

![Application screenshot](Screen.png?raw=true "Screenshot")

## Memory ##

The memory in the emulator contains 64 cells, each of which is a simple string. In this way each cell can store any value: a command, `int`, `float`, `String` variables and any other values.

## Operands ##

The following table shows types of operands, which is allowed for using in all commands

Operand type                 |    Description
-----------------------------|----------------------------------------
`"Hallo world"`, `3`, `3.2`  | Immediate value (`string`, `int`, `float`)
`ax`, `bx`, `cx` ...         | Name of a register (all register values is a `int` type)
`[x]`                        | Value at the memory addresse `x` (hier `x` - an immediate value or a register)
`[reg + c]`                  | Value at the memory addresse `reg + c`, where `reg` is a register and `c` is a numerical constant (for ex.: `mov [ax + 1], 2`)

## Commands ##

Command       | Description
------------- | ---------------------------------------------------------------------------------------------------------
`out X`       | Output `X` into console (is a [syntactic sugar](https://en.wikipedia.org/wiki/Syntactic_sugar) for the emulator, not an x86-instruction)
`mov X, Y`    | Copies the second operand `Y` (source operand) to the first operand `X` (destination operand)
`call x`      | Saves procedure linking information on the stack and branches to the procedure (called procedure) specified with the destination (target) operand
`ret`         | Transfers program control to a return address located on the top of the stack
`push X`      | Decrements the stack pointer and then stores the source operand on the top of the stack
`pop X`       | Loads the value from the top of the stack to the location specified with the destination operand and then increments the stack pointer
`loop X`      | Performs a loop operation using the `CX` register as a counter. Each time the `loop` instruction is executed, the count register is decremented, then checked for 0. If the count is 0, the `loop` is terminated and program execution continues with the instruction following the `loop` instruction
`jmp Х`       | Jump to command at the address `X`
`inc X`       | Adds 1 to the destination operand
`dec X`       | Subtracts 1 from the destination operand
`add X, Y`    | Adds the first operand `X` (destination) and the second operand `Y` (source) and stores the result in the destination operand `X`
`sub X, Y`    | Subtracts the second operand `Y` (source operand) from the first operand `X` (destination operand) and stores the result in the destination operand `X`
`div X, Y`    | Divides the first operand `X` (destination) by the second operand `Y` (source) and stores the result in the destination operand `X`
`mul X, Y`    | Performs a multiplication of the first operand `X` (destination operand) and the second operand `Y` (source operand) and stores the result in the destination operand `X`
`cmp Х, Y`    | Compares the first source operand with the second source operand and sets the `ZF`-flag (Zero Flag) according to the results
`je Х`        | Jump to command at the address `X`, if equal (`ZF = 1`)
`jne Х`       | Jump to command at the address `X`, if not equal (`ZF = 0`)
`jl X`        | Jump to command at the address `X`, if less
`jg X`        | Jump to command at the address `X`, if greater

*Note: because the emulator used for demonstration purposes, there are certain differences in the commands towards real x86-instructions. For example, unlike `mov` command in x86, `mov` in the emulator allows copy of some data from memory cell to memory cell: `mov [ax], [bx]`*
