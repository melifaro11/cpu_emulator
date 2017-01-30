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
package de.malex.cpuemulator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class to represent a memory cell
 */
public class MemoryCell {

	/**
	 * Address of the cell
	 */
	public SimpleIntegerProperty address;
		
	/**
	 * Stored value
	 */
	public SimpleStringProperty value;
		
	/**
	 * Create new {@link MemoryCell}
	 * 
	 * @param address Address
	 * @param value Value to store
	 */
	public MemoryCell(int address, String value) {
		this.address = new SimpleIntegerProperty(address);
		this.value = new SimpleStringProperty(value);
	}

	/**
	 * Return address of the cell
	 * 
	 * @return Address of the cell
	 */
	public int getAddress() {
		return address.get();
	}

	/**
	 * Set new address of the cell
	 * 
	 * @param address Address to set
	 */
	public void setAddress(int address) {
		this.address.set(address);
	}

	/**
	 * Return stored value from cell
	 * 
	 * @return Stored value as {@link String}
	 */
	public String getValue() {
		return value.get();
	}

	/**
	 * Store value into the cell
	 * 
	 * @param value Value to store
	 */
	public void setValue(String value) {
		this.value.set(value);;
	}
}
