/**
 * Copyright 2016-2017 Alexandr Mitiaev
 * 
 * This file is part of CPU Emulator.
 * 
 * CPU Emulator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CPU Emulator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CPU Emulator. If not, see <http://www.gnu.org/licenses/>.
 */
package de.malex.cpuemulator.vm.commands;

import de.malex.cpuemulator.constants.Registers;
import de.malex.cpuemulator.vm.VM;
import de.malex.cpuemulator.vm.VMException;

/**
 * PUSH a
 * 
 * Decrements the stack pointer and then stores the source operand on the top of the stack.
 */
public class CommandPush extends ICommand {
	
	/**
	 * Name of the command
	 */
	private static final String COMMAND_NAME		=			"PUSH";

	/**
	 * Create new {@link CommandPush} command
	 * 
	 * @param vm The {@link VM} to add this command
	 */
	public CommandPush(VM vm) {
		super(vm, COMMAND_NAME);
		
		vm.addCommand(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(String params) throws VMException {
		parseParam(params);
		
		String val = vm.getValueFrom(params);
		Integer stack = vm.getRegisterValue(Registers.REG_SP) - 1;
			
		checkMemRange(stack);
		
		vm.setRegisterValue(Registers.REG_SP, stack);
			
		vm.setMem(stack, val);
			
		if (val.equals("0")) {
			vm.setFlags(1, 0);
		} else {
			vm.setFlags(0, 0);
		}
	}
}
