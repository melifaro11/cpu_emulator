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

import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * Class to represent a CPU flag
 */
public class Flag {

	/**
	 * The {@link TextField} to display value
	 */
	private Label label;
	
	/**
	 * Name of the flag
	 */
	private String name;
	
	/**
	 * Create new {@link Flag} object
	 * 
	 * @param name Name of the flag
	 * @param label The {@link Label} to display flag value
	 */
	public Flag(String name, Label label) {
		this.name = name;
		this.label = label;
		
	}
	
	/**
	 * Return name of the flag
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set value of the flag
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		label.setText(String.valueOf(value));
	}
	
	/**
	 * Return value of the flag
	 * 
	 * @return Value of the flag
	 */
	public int getValue() {
		return Integer.parseInt(label.getText());
	}
	
	/**
	 * Reset flag to 0
	 */
	public void reset() {
		label.setText("0");
	}
}
