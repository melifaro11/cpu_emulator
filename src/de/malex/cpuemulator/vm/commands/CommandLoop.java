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
 * LOOP a
 * 
 * Performs a loop operation using the CX register as a counter.
 * Each time the LOOP instruction is executed, the count register
 * is decremented, then checked for 0. If the count is 0, the loop
 * is terminated and program execution continues with the instruction
 * following the LOOP instruction.
 */
public class CommandLoop extends ICommand {
	
	/**
	 * Name of the command
	 */
	private static final String COMMAND_NAME		=			"LOOP";

	/**
	 * Create new {@link CommandLoop} command
	 * 
	 * @param vm The {@link VM} to add this command
	 */
	public CommandLoop(VM vm) {
		super(vm, COMMAND_NAME);
		
		vm.addCommand(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(String params) throws VMException {

		String val = vm.getValueFrom(params);
			
		Integer cx = vm.getRegisterValue(Registers.REG_CX) - 1;
		vm.setRegisterValue(Registers.REG_CX, cx);
			
		if (cx != 0) {
			vm.setRegisterValue(Registers.REG_IP, Integer.parseInt(val));
			vm.setFlags(1, 0);
		} else {
			vm.setFlags(0, 0);
		}
	}
}
