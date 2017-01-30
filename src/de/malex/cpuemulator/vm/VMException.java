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

/**
 * Virtual machine exception class
 */
public class VMException extends Exception {
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -1238771471158227516L;

	/**
	 * Default constructor
	 */
	public VMException() {
		super();
	}
	
	/**
	 * Create new {@link VMException} with a given message
	 */
	public VMException(String message) {
		super(message);
	}
}
