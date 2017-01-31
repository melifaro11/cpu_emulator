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

import de.malex.cpuemulator.constants.Messages;
import de.malex.cpuemulator.vm.VM;
import de.malex.cpuemulator.vm.VMException;

/**
 * MOV a, b
 * 
 * Copies the second operand (source operand) to the first operand (destination operand)
 */
public class CommandMov extends ICommand {
	
	/**
	 * Name of the command
	 */
	private static final String COMMAND_NAME		=			"MOV";

	/**
	 * Create new {@link CommandMov} command
	 * 
	 * @param vm The {@link VM} to add this command
	 */
	public CommandMov(VM vm) {
		super(vm, COMMAND_NAME);
		
		vm.addCommand(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(String params) throws VMException {
		parseParam(params);
		
		try {
			String valueFrom = vm.getValueFrom(param2);
			vm.setValueTo(param1, valueFrom);
				
			if (valueFrom.equals("0")) {
				vm.setFlags(1, 0);
			} else {
				vm.setFlags(0, 0);
			}
		} catch (Exception e) {
			throw new VMException(String.format(Messages.ALERT_EXEC_ERROR, name));
		}
	}
}
