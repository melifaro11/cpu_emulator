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
 * RET
 * 
 * Transfers program control to a return address located
 * on the top of the stack.
 */
public class CommandRet extends ICommand {
	
	/**
	 * Name of the command
	 */
	private static final String COMMAND_NAME		=			"RET";

	/**
	 * Create new {@link CommandRet} command
	 * 
	 * @param vm The {@link VM} to add this command
	 */
	public CommandRet(VM vm) {
		super(vm, COMMAND_NAME);
		
		vm.addCommand(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(String params) throws VMException {

		Integer stack = vm.getRegisterValue(Registers.REG_SP);
		checkMemRange(stack);

		vm.setRegisterValue(Registers.REG_SP, stack + 1);
			
		vm.setRegisterValue(Registers.REG_IP, Integer.parseInt(vm.getMem(stack)));
			
		vm.setFlags(0, 0);
	}
}
