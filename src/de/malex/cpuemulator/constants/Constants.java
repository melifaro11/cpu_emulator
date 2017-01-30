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
package de.malex.cpuemulator.constants;

/**
 * Global application constants
 */
public class Constants {
	/**
	 * Size of the memory
	 */
	public static final int MEMORY_SIZE			=			64;
	
	/**
	 * Start address of the memory
	 */
	public static final int MEMORY_START_ADDR	=			0;
	
	/**
	 * End address of the memory
	 */
	public static final int MEMORY_END_ADDR		=			MEMORY_SIZE - 1;
	
	/**
	 * Style for the row, which is pointed by IP-register
	 */
	public static final String ROW_STYLE_IP		=			"-fx-background-color: #34d670;";
	
	/**
	 * Style for the row, which is pointed by SP-register
	 */
	public static final String ROW_STYLE_SP		=			"-fx-background-color: #ff3db6;";
	
	/**
	 * Style for the row, which is pointed by BP-register
	 */
	public static final String ROW_STYLE_BP		=			"-fx-background-color: #13cdee";
}
