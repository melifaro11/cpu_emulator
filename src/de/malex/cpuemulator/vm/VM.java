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
package de.malex.cpuemulator.vm;

import java.util.HashMap;

import de.malex.cpuemulator.constants.Constants;
import de.malex.cpuemulator.constants.Messages;
import de.malex.cpuemulator.constants.Registers;
import de.malex.cpuemulator.vm.commands.ICommand;
import javafx.scene.control.TableView;

/**
 * Virtual machine
 */
@SuppressWarnings("rawtypes")
public class VM {
	/**
	 * Available registers on the VM
	 */
	private HashMap<String, Register> registers;
	
	/**
	 * Available flags on the VM
	 */
	private HashMap<String, Flag> flags;
	
	/**
	 * Command set for the VM
	 */
	private HashMap<String, ICommand> commands;
	
	/**
	 * VM memory
	 */
	private Memory memory;
	
	/**
	 * Create new {@link VM}
	 */
	public VM(TableView memTable) {
		registers = new HashMap<String, Register>();
		flags = new HashMap<String, Flag>();
		
		memory = new Memory(this, memTable);
		
		commands = new HashMap<String, ICommand>();
	}
	
	/**
	 * Add a register to the virtual machine
	 * 
	 * @param register The register to adding
	 */
	public void addRegister(Register register) {
		registers.put(register.getName(), register);
	}
	
	/**
	 * Add a flag to the virtual machine
	 * 
	 * @param flag The flag to adding
	 */
	public void addFlag(Flag flag) {
		flags.put(flag.getName(), flag);
	}
	
	/**
	 * Add command to the VM
	 * 
	 * @param cmd The command to add
	 */
	public void addCommand(ICommand cmd) {
		commands.put(cmd.getName(), cmd);
	}
	
	/**
	 * Returns value of the register
	 * 
	 * @param regName Name of the register
	 * 
	 * @return Value of the register
	 * 
	 * @throws VMException 
	 */
	public Integer getRegisterValue(String regName) throws VMException {
		
		Integer addendum = 0;
		regName = regName.toUpperCase();
		
		if (regName.indexOf('+') != -1) {
			try {
				addendum = Integer.parseInt(regName.substring(3, regName.length()));
			} catch (NumberFormatException e) {
				addendum = 0;
			}
			
			regName = regName.substring(0, 2);
		}
		
		if (registers.containsKey(regName)) {
			return registers.get(regName.toUpperCase()).getValue() + addendum;
		} else {
			throw new VMException(String.format(Messages.ALERT_UNKNOWN_REG, regName));
		}
	}
	
	/**
	 * Store value into a register
	 * 
	 * @param regName Name of the register to store value
	 * @param value Value to store
	 * 
	 * @throws VMException 
	 */
	public void setRegisterValue(String regName, Integer value) throws VMException {
		
		regName = regName.toUpperCase();
		
		if (registers.containsKey(regName)) {
			registers.get(regName).setValue(value);
		} else {
			throw new VMException(String.format(Messages.ALERT_UNKNOWN_REG, regName));	
		}
	}
	
	/**
	 * Return true, if the given parameter is a register, otherwise false
	 * 
	 * @param param The parameter
	 * 
	 * @return True, if the parameter is a register, otherwise false
	 */
	private boolean isRegister(String param) {
		return registers.containsKey(param.toUpperCase());
	}
	
	/**
	 * Return true, if the given parameter is a memory address, otherwise false
	 * 
	 * @param param The parameter
	 * 
	 * @return True, if the parameter is a memory address, otherwise false
	 */
	private boolean isAddress(String param) {
		if (param.length() >= 3 && param.charAt(0) == '[' && param.charAt(param.length() - 1) == ']')
			return true;
		
		return false;
	}
	
	/**
	 * Parse given parameter and get value from it
	 * 
	 * @param param The parameter
	 * @return Value
	 * 
	 * @throws VMException
	 */
	public String getValueFrom(String param) throws VMException {
		if (isRegister(param)) {
			return getRegisterValue(param).toString();
		} else if (isAddress(param)) {
			String param2 = param.substring(1, param.length() - 1);
			Integer memAddress = 0;
			
			if (isRegister(param2)) {
				memAddress = getRegisterValue(param2);
			} else {
				try {
					memAddress = Integer.parseInt(param2);
				} catch (Exception e) {
					throw new VMException(String.format(Messages.MSG_INVALID_ADDR, param2));
				}
			}
			
			if (memAddress > Constants.MEMORY_END_ADDR || memAddress < Constants.MEMORY_START_ADDR) {
				throw new VMException(String.format(Messages.MSG_INVALID_ADDR, param2));
			}
			
			return memory.getValue(memAddress);
		} else {
			return param;
		}
	}
	
	/**
	 * Parse the parameter and store given value into parsed destination
	 *  
	 * @param param The parameter (for ex.: ax, [123], [bp+1])
	 * @param value The value to store
	 * 
	 * @throws Exception When error
	 */
	public void setValueTo(String param, String value) throws VMException {
		if (isRegister(param)) {
			setRegisterValue(param, Integer.parseInt(value));
		} else if (isAddress(param)) {
			String param2 = param.substring(1, param.length() - 1);
			Integer memAddress = 0;
			
			if (isRegister(param2)) {	
				memAddress = getRegisterValue(param2);
			} else {
				try {
					memAddress = Integer.parseInt(param2);
				} catch (Exception e) {
					throw new VMException(String.format(Messages.MSG_INVALID_ADDR, param2));
				}
			}
			
			if (memAddress > Constants.MEMORY_END_ADDR || memAddress < Constants.MEMORY_START_ADDR) {
				throw new VMException(String.format(Messages.MSG_INVALID_ADDR, param2));
			}
			
			memory.setValue(memAddress, value);

		} else {
			throw new VMException(String.format(Messages.MSG_INVALID_DEST, param));
		}
	}
	
	/**
	 * Set up ZF und SF flags
	 * 
	 * @param zf New value for the ZF flag
	 * @param sf New value for the SF flag
	 */
	public void setFlags(int zf, int sf) {
		setZF(zf);
		setSF(sf);
	}
	
	/**
	 * Set ZF flat to a given value
	 * 
	 * @param zf New value for ZF-flag
	 */
	public void setZF(int zf) {
		flags.get(Registers.REG_ZF).setValue(zf);
	}
	
	/**
	 * Set SF flat to a given value
	 * 
	 * @param sf New value for SF-flag
	 */
	public void setSF(int sf) {
		flags.get(Registers.REG_SF).setValue(sf);
	}
	
	/**
	 * Returns the ZF-flag
	 * 
	 * @return ZF-flag value
	 */
	public int getZF() {
		return flags.get(Registers.REG_ZF).getValue();
	}
	
	/**
	 * Returns the SF-flag
	 * 
	 * @return SF-flag value
	 */
	public int getSF() {
		return flags.get(Registers.REG_SF).getValue();
	}
	
	/**
	 * Reset all registers/flags/memory of the VM
	 */
	public void reset() {
		setFlags(0, 0);
		
		for (Register reg: registers.values()) {
			reg.reset();
		}
		
		memory.reset();
	}
	
	/**
	 * Store value in memory cell
	 * 
	 * @param addr The address of the cell
	 * @param value The value to store
	 * 
	 * @throws VMException If address is out of memory range
	 */
	public void setMem(int addr, String value) throws VMException {
		memory.setValue(addr, value);
		memory.update();
	}
	
	/**
	 * Get value from memory cell
	 * 
	 * @param addr The address of the cell
	 * 
	 * @return The value from a cell
	 * 
	 * @throws VMException If address is out of memory range
	 */
	public String getMem(int addr) throws VMException {
		return memory.getValue(addr);
	}
	
	/**
	 * Execute given command on virtual machine
	 * 
	 * @param cmd The command to execute
	 * 
	 * @throws VMException 
	 */
	public void executeCmd(String cmd) throws VMException {
		String [] cmdTokens = cmd.split(" ", 2);
		cmdTokens[0] = cmdTokens[0].trim();
		
		String params = "";
		if (cmdTokens.length > 1)
			params = cmdTokens[1];
		params = params.trim();
		
		ICommand command = commands.get(cmdTokens[0].toUpperCase());
		
		if (command != null) {
			command.execute(params);
		} else {
			throw new VMException(String.format(Messages.ALERT_UNKNOWN_CMD, cmdTokens[0].toUpperCase()));
		}
	}
}
