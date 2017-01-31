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

import java.net.URL;
import java.util.ResourceBundle;

import de.malex.cpuemulator.constants.Constants;
import de.malex.cpuemulator.constants.Registers;
import de.malex.cpuemulator.vm.Flag;
import de.malex.cpuemulator.vm.Register;
import de.malex.cpuemulator.vm.VM;
import de.malex.cpuemulator.vm.VMException;
import de.malex.cpuemulator.vm.commands.*;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * Controller class for MainForm.xml-Form
 * 
 * @author Alexandr Mitiaev
 */
public class MainFormController implements Initializable {
	
	/**
	 * The {@link TableView} to display memory data
	 */
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView tblMemory;
	
	/**
	 * The {@link TextField} for the instruction pointer (IP) register
	 */
	@FXML
	private TextField edIP;
	
	/**
	 * The {@link TextField} for the stack pointer (SP) register
	 */
	@FXML
	private TextField edSP;
	
	/**
	 * The {@link TextField} for the accumulator (AX) register
	 */
	@FXML
	private TextField edAX;
	
	/**
	 * The {@link TextField} for the base (BX) register
	 */
	@FXML
	private TextField edBX;
	
	/**
	 * The {@link TextField} for the counter (CX) register
	 */
	@FXML
	private TextField edCX;
	
	/**
	 * The {@link TextField} for the data (DX) register
	 */
	@FXML
	private TextField edDX;
	
	/**
	 * The {@link TextField} for the base pointer (BP) register
	 */
	@FXML
	private TextField edBP;

	/**
	 * Label to display ZF-flag value
	 */
	@FXML
	private Label lbZF;
	
	/**
	 * Label to display SF-flag value
	 */
	@FXML
	private Label lbSF;
	
	/**
	 * Output console
	 */
	@FXML
	private TextArea edOutput;
	
	/**
	 * The virtual machine
	 */
	private VM vm;
	
	/**
	 * Listener, which updates the memory table
	 */
	private ChangeListener<String> memUpdateListener =
                (observable, oldValue, newValue) -> tblMemory.refresh();
        
	/**
	 * Initialize form
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		vm = new VM(tblMemory);

		initVM();
		
		edIP.textProperty().addListener(memUpdateListener);
		edSP.textProperty().addListener(memUpdateListener);
		edBP.textProperty().addListener(memUpdateListener);
		
		onResetClicked();
	}
	
	/**
	 * Virtual machine initialization: adding registers and flags
	 * to the virtual machine
	 */
	private void initVM() {
		vm.addRegister(new Register(Registers.REG_IP, 0, edIP, Constants.MEMORY_START_ADDR, Constants.MEMORY_END_ADDR));
		vm.addRegister(new Register(Registers.REG_SP, 15, edSP, Constants.MEMORY_START_ADDR, Constants.MEMORY_END_ADDR));
		vm.addRegister(new Register(Registers.REG_BP, 15, edBP, Constants.MEMORY_START_ADDR, Constants.MEMORY_END_ADDR));
		vm.addRegister(new Register(Registers.REG_AX, 0, edAX));
		vm.addRegister(new Register(Registers.REG_BX, 0, edBX));
		vm.addRegister(new Register(Registers.REG_CX, 0, edCX));
		vm.addRegister(new Register(Registers.REG_DX, 0, edDX));
		
		vm.addFlag(new Flag(Registers.REG_ZF, lbZF));
		vm.addFlag(new Flag(Registers.REG_SF, lbSF));
		
		vm.addCommand(new CommandAdd(vm));
		vm.addCommand(new CommandCall(vm));
		vm.addCommand(new CommandCmp(vm));
		vm.addCommand(new CommandDec(vm));
		vm.addCommand(new CommandInc(vm));
		vm.addCommand(new CommandJe(vm));
		vm.addCommand(new CommandJg(vm));
		vm.addCommand(new CommandJl(vm));
		vm.addCommand(new CommandJmp(vm));
		vm.addCommand(new CommandJne(vm));
		vm.addCommand(new CommandLoop(vm));
		vm.addCommand(new CommandMov(vm));
		vm.addCommand(new CommandOut(vm, edOutput));
		vm.addCommand(new CommandPop(vm));
		vm.addCommand(new CommandPush(vm));
		vm.addCommand(new CommandRet(vm));
		vm.addCommand(new CommandSub(vm));
		vm.addCommand(new CommandMul(vm));
		vm.addCommand(new CommandDiv(vm));
	}
	
	/**
	 * Execute next command from memory
	 */
	@FXML
	public void onRunClicked() {
		try {
			int ip = Integer.parseInt(edIP.getText());
			MemoryCell cell = (MemoryCell) tblMemory.getItems().get(ip);
			
			vm.setRegisterValue(Registers.REG_IP, vm.getRegisterValue(Registers.REG_IP) + 1);
			String cmd = cell.getValue();
			
			vm.executeCmd(cmd);
			tblMemory.refresh();
		} catch (VMException e) {
			Main.showError(e.getMessage(), e.getMessage());
		}
		
		
	}
	
	/**
	 * Reset all components
	 */
	@FXML
	public void onResetClicked() {
		vm.reset();
		edOutput.clear();
	}
}
