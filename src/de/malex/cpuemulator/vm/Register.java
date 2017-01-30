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

import de.malex.cpuemulator.Main;
import de.malex.cpuemulator.constants.Messages;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * Class to represent a CPU register
 */
public class Register {

	/**
	 * The {@link TextField} to display value
	 */
	private TextField textField;
	
	/**
	 * Name of the register
	 */
	private String name;
	
	/**
	 * Default value of the register
	 */
	private Integer defValue;
	
	/**
	 * Create new {@link Register} object
	 * 
	 * @param name Name of the register
	 * @param defValue Default value of the register
	 * @param field The referenced {@link TextField} to display value
	 */
	public Register(String name, Integer defValue, TextField field) {
		this.name = name;
		this.textField = field;
		this.defValue = defValue;
		
		reset();
	}
	
	/**
	 * Create new {@link Register} object with a range limit
	 * 
	 * @param name Name of the register
	 * @param defValue Default value of the register
	 * @param field The {@link TextField} to display value
	 * @param min The lower limit of the acceptable range
	 * @param max The higher limit of the acceptable range
	 */
	public Register(String name, Integer defValue, TextField field, Integer min, Integer max) {
		this(name, defValue, field);
		
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				Integer value = Integer.parseInt(newValue);
				
				if (value < min || value > max)
					throw new Exception();
				
			} catch (Exception e) {
				Main.showAlert(AlertType.ERROR, Messages.ALERT_ERROR_TITLE,
						String.format(Messages.MSG_INVALID_VALUE, name + " register"),
						String.format(Messages.MSG_NOT_ALLOWED_VALUE, newValue, name + " register"));
			}
		});
	}
	
	/**
	 * Return name of the register
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set new value of the register
	 * 
	 * @param value Value to set
	 * 
	 * @throws VMException If error
	 */
	public void setValue(Integer value) throws VMException {
		if (value != null) {
			textField.setText(value.toString());
		} else {
			throw new VMException(String.format(Messages.ALERT_SET_REG_ERROR, name));
		}
	}
	
	/**
	 * Return current value of the register
	 * 
	 * @return Current value of the register
	 * 
	 * @throws VMException If reading error 
	 */
	public Integer getValue() throws VMException {
		try {
			return Integer.parseInt(textField.getText());
		} catch (NumberFormatException e) {
			throw new VMException(String.format(Messages.ALERT_GET_REG_ERROR, name));
		}
	}
	
	/**
	 * Reset register to default value
	 */
	public void reset() {
		try {
			setValue(defValue);
		} catch (VMException e) {
			// Suppress exception by reset
		}
	}
}
