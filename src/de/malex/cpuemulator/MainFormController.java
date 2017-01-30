package de.malex.cpuemulator;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import de.malex.cpuemulator.constants.Constants;
import de.malex.cpuemulator.constants.Messages;
import de.malex.cpuemulator.constants.Registers;
import de.malex.cpuemulator.vm.Flag;
import de.malex.cpuemulator.vm.Register;
import de.malex.cpuemulator.vm.VM;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

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
	@SuppressWarnings("unchecked")
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
	}
	
	/**
	 * Process one step
	 */
	@FXML
	public void onRunClicked() {
		int ip = Integer.parseInt(edIP.getText());
		
		MemoryCell cell = (MemoryCell) tblMemory.getItems().get(ip);
		
		vm.setRegisterValue(Registers.REG_IP, vm.getRegisterValue(Registers.REG_IP) + 1);
		
		String cmd = cell.getValue();
		
		String [] cmdTokens = cmd.split(" ", 2);
		cmdTokens[0] = cmdTokens[0].trim();
		String params = "";
		if (cmdTokens.length > 1)
			params = cmdTokens[1];
		params = params.trim();
		
		switch (cmdTokens[0].toUpperCase()) {
		
		
		case "JMP":

			
		case "JL":
			try {
				String val = getValueFrom(params);
				Integer address = 0;
				try {
					address = Integer.parseInt(val);
					if (address < 0 || address > 63)
						throw new Exception();
				} catch (Exception e) {
					showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, "Invalid jump address: " + val);
				}
				
				if (lbSF.getText() == "1")
					setRegisterValue("IP", address);
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, e.getMessage());
			}
			setFlags(0, 0);
			break;
			
		case "JG":
			try {
				String val = getValueFrom(params);
				Integer address = 0;
				try {
					address = Integer.parseInt(val);
					if (address < 0 || address > 63)
						throw new Exception();
				} catch (Exception e) {
					showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, "Invalid jump address: " + val);
				}
				
				if (lbSF.getText() == "0")
					setRegisterValue("IP", address);
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, e.getMessage());
			}
			setFlags(0, 0);
			break;
			
		case "JE":

			
		case "JNE":
		
			
		case "LOOP":



			
		case "OUT":
			try {
				String val = getValueFrom(params);
				edOutput.appendText(val + "\n");
			} catch (Exception e) {
				Main.showError("Error executing command " + cmd, e.getMessage());
			}
			setFlags(0, 0);
			break;
			
		default:
			Main.showError("Error executing command", "Unknown command: " + cmdTokens[0]);
		}
		
		tblMemory.refresh();
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
