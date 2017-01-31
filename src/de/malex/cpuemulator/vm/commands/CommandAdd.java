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

import de.malex.cpuemulator.vm.VM;
import de.malex.cpuemulator.vm.VMException;

/**
 * ADD a, b
 * 
 * Adds the first operand 'a' (destination operand) and the second operand 'b'
 * (source operand) and stores the result in the destination operand 'a'
 */
public class CommandAdd extends ICommand {
	
	/**
	 * Name of the command
	 */
	private static final String COMMAND_NAME		=			"ADD";

	/**
	 * Create new {@link CommandAdd} command
	 * 
	 * @param vm The {@link VM} to add this command
	 */
	public CommandAdd(VM vm) {
		super(vm, COMMAND_NAME);
		
		vm.addCommand(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(String params) throws VMException {
		parseParam(params);
		
		Integer value1 = Integer.parseInt(param2);
		Integer value2 = Integer.parseInt(param1);
				
		vm.setValueTo(param1, String.valueOf(value1 + value2));
		
		if (value1 + value2 == 0) {
			vm.setFlags(1, 0);
		} else {
			vm.setFlags(0, 0);
		}
	}
}
