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

import de.malex.cpuemulator.constants.Constants;
import de.malex.cpuemulator.constants.Messages;
import de.malex.cpuemulator.vm.VM;
import de.malex.cpuemulator.vm.VMException;

/**
 * Abstract class for a CPU-command
 */
public abstract class ICommand {
	
	/**
	 * Virtual machine to run the command
	 */
	protected VM vm;
	
	/**
	 * Name of the command
	 */
	protected String name;
	
	/**
	 * 1st parameter of the command
	 */
	protected String param1;
	
	/**
	 * 2nd parameter of the command (if exists)
	 */
	protected String param2;
	
	/**
	 * Create new {@link ICommand} with a given name
	 * 
	 * @param name Name of the command
	 */
	public ICommand(VM vm, String name) {
		this.vm = vm;
		this.name = name;
	}
	
	/**
	 * Returns the name of the command
	 * 
	 * @return Name of the command
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Parse parameters and store it into {@link #param1}, {@link #param2}
	 * 
	 * @param param The parameter to parse
	 */
	protected void parseParam(String param) {
		if (param.indexOf(',') != -1) {
			String [] params = param.split(",", 2);
			param1 = params[0].trim();
			param2 = params[1].trim();
		} else {
			param1 = param.trim();
			param2 = "";
		}
	}
	
	/**
	 * Throw the {@link VMException}, if the given address is
	 * out of memory range
	 * 
	 * @param addr The address to check
	 * 
	 * @throws VMException If the address if out of memory range
	 */
	protected void checkMemRange(int addr) throws VMException {
		if (addr < Constants.MEMORY_START_ADDR || addr > Constants.MEMORY_END_ADDR)
			throw new VMException(String.format(Messages.MSG_INVALID_ADDR, addr));
	}
	
	/**
	 * Execute command
	 * 
	 * @param params Parameters of the command
	 * 
	 * @throws VMException if error
	 */
	public abstract void execute(String params) throws VMException;
}
