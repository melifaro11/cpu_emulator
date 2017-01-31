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
import javafx.scene.control.TextArea;

/**
 * OUT a
 * 
 * Output `a` into console (is a syntactic sugar for
 * the emulator, not an x86-instruction)
 */
public class CommandOut extends ICommand {
	
	/**
	 * Name of the command
	 */
	private static final String COMMAND_NAME		=			"OUT";
	
	/**
	 * Output component
	 */
	private TextArea output;

	/**
	 * Create new {@link CommandOut} command
	 * 
	 * @param vm The {@link VM} to add this command
	 */
	public CommandOut(VM vm, TextArea console) {
		super(vm, COMMAND_NAME);
		this.output = console;
		
		vm.addCommand(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(String params) throws VMException {
		try {
			String val = vm.getValueFrom(params);
			output.appendText(val + "\n");
		} catch (Exception e) {
			throw new VMException(String.format(Messages.ALERT_EXEC_ERROR, name));
		}
		
		vm.setFlags(0, 0);
	}
}
