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
 * Text strings
 */
public class Messages {

	/**
	 * Title of the "Error" dialog box
	 */
	public static final String ALERT_ERROR_TITLE		=			"Error";
	
	/**
	 * Message "Invalid value in ..."
	 */
	public static final String MSG_INVALID_VALUE		=			"Invalid value in %s";
	
	/**
	 * Message "Value ... not allowed for ..."
	 */
	public static final String MSG_NOT_ALLOWED_VALUE	=			"Value %s is not allowed for %s";
	
	/**
	 * Message text "Error launching the app"
	 */
	public static final String ALERT_START_ERROR		=			"Error launching the application";

	/**
	 * Message text "Invalid address: ..."
	 */
	public static final String MSG_INVALID_ADDR			=			"Invalid address: %s";
	
	/**
	 * Message text "Invalid destination: ..."
	 */
	public static final String MSG_INVALID_DEST			=			"Invalid destination: %s";
	
	/**
	 * Message text "Error by reading ... register"
	 */
	public static final String ALERT_GET_REG_ERROR		=			"Error by reading %s register";
	
	/**
	 * Message text "Error by setting ... register"
	 */
	public static final String ALERT_SET_REG_ERROR		=			"Error by setting %s register";
	
	/**
	 * Message text "Unknown register: ..."
	 */
	public static final String ALERT_UNKNOWN_REG		=			"Unknown register: %s";
	
	/**
	 * Message text "Error executing ..."
	 */
	public static final String ALERT_EXEC_ERROR			=			"Error by executing command %s";
	
	/**
	 * Message text "Unknown command..."
	 */
	public static final String ALERT_UNKNOWN_CMD		=			"Unknown command: %s";
}
