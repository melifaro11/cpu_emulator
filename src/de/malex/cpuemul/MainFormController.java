package de.malex.cpuemul;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class MainFormController implements Initializable {
	
	public class MemoryCell {
		public SimpleIntegerProperty address;
		
		public SimpleStringProperty value;
		
		public MemoryCell(int address, String value) {
			this.address = new SimpleIntegerProperty(address);
			this.value = new SimpleStringProperty(value);
		}

		public int getAddress() {
			return address.get();
		}

		public void setAddress(int address) {
			this.address.set(address);
		}

		public String getValue() {
			return value.get();
		}

		public void setValue(String value) {
			this.value.set(value);;
		}
	}
	
	/**
	 * Memory table
	 */
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView tblMemory;
	
	@FXML
	private TextField edIP;
	
	@FXML
	private TextField edSP;
	
	@FXML
	private TextField edAX;
	
	@FXML
	private TextField edBX;
	
	@FXML
	private TextField edCX;
	
	@FXML
	private TextField edDX;
	
	@FXML
	private TextField edBP;
	
	@FXML
	private Label lbIP;
	
	@FXML
	private Label lbSP;
	
	@FXML
	private Label lbAX;
	
	@FXML
	private Label lbBX;
	
	@FXML
	private Label lbCX;
	
	@FXML
	private Label lbDX;
	
	@FXML
	private Label lbZF;
	
	@FXML
	private Label lbSF;
	
	@FXML
	private TextArea edOutput;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		edIP.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				Integer value = Integer.parseInt(newValue);
				if (value < 0 || value > 63)
					throw new Exception();
				tblMemory.refresh();
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Invalid value for IP", "Invalid IP");
			}
		});
		
		edSP.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				Integer value = Integer.parseInt(newValue);
				if (value < 0 || value > 63)
					throw new Exception();
				tblMemory.refresh();
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Invalid value for SP", "Invalid SP");
			}
		});
		
		edBP.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				Integer value = Integer.parseInt(newValue);
				if (value < 0 || value > 63)
					throw new Exception();
				tblMemory.refresh();
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Invalid value for BP", "Invalid BP");
			}
		});
		
		TableColumn<MemoryCell, Integer> addressCol = (TableColumn<MemoryCell, Integer>) tblMemory.getColumns().get(0);
		TableColumn<MemoryCell, String> valueCol = (TableColumn<MemoryCell, String>) tblMemory.getColumns().get(1);

		addressCol.setCellValueFactory(new PropertyValueFactory<MemoryCell, Integer>("address"));
		valueCol.setCellValueFactory(new PropertyValueFactory<MemoryCell, String>("value"));
		valueCol.setCellFactory(TextFieldTableCell.forTableColumn());
		valueCol.setOnEditCommit(
                  new EventHandler<CellEditEvent<MemoryCell, String>>() {
                      @Override
                      public void handle(CellEditEvent<MemoryCell, String> t) {
                          ((MemoryCell) t.getTableView().getItems().get(
                                  t.getTablePosition().getRow())).setValue(t.getNewValue());
                          System.out.println(t.getNewValue());
                      }
                  });
		  
		 ObservableList<MemoryCell> data = FXCollections.observableArrayList();
		 for (int i = 0; i < 64; i++)
			 data.add(new MemoryCell(i, "0"));
		 
		 tblMemory.setItems(data);
		 
		 addressCol.setCellFactory(new Callback<TableColumn<MemoryCell, Integer>, TableCell<MemoryCell, Integer>>() {
		      @Override public TableCell<MemoryCell, Integer> call(TableColumn<MemoryCell, Integer> soCalledFriendBooleanTableColumn) {
		        return new TableCell<MemoryCell, Integer>() {
		          @Override public void updateItem(final Integer item, final boolean empty) {
		            super.updateItem(item, empty);
		            
		            
		            if (item != null) {
		            	try {
		            		Integer currentIp = Integer.parseInt(edIP.getText());
		            		Integer currentSp = Integer.parseInt(edSP.getText());
		            		Integer currentBp = Integer.parseInt(edBP.getText());
		            		
		            		if (currentIp.intValue() == item.intValue()) {
		            			getTableRow().setStyle("-fx-background-color: #34d670;");
		            		} else if (currentSp.intValue() == item.intValue()) {
		            			getTableRow().setStyle("-fx-background-color: #ff3db6;");
		            		} else if (currentBp.intValue() == item.intValue()) {
		            			getTableRow().setStyle("-fx-background-color: #13cdee");
		            		} else {
		            			getTableRow().setStyle("");
		            		}
		            	} catch (Exception e) {
		            		
		            	}
		          
		            	setText(item.toString());
		            }
		          }
		        };
		      }
		 });
		 
		 onResetClicked();
	}
	
	public void showAlert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);

		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
	
	/**
	 * Returns value of the given register
	 * 
	 * @param regName Name of the register
	 * 
	 * @return Value of the given register
	 * 
	 * @throws Exception If register with given name not existed
	 */
	public Integer getRegisterValue(String regName) {
		
		if (regName.toUpperCase().startsWith("BP+")) {
			String sum = regName.substring(3, regName.length());
			return Integer.parseInt(edBP.getText()) + Integer.parseInt(sum);
		} else {
			switch (regName.toUpperCase()) {
				case "IP": return Integer.parseInt(edIP.getText());
				case "SP": return Integer.parseInt(edSP.getText());
				case "BP": return Integer.parseInt(edBP.getText());
				case "AX": return Integer.parseInt(edAX.getText());
				case "BX": return Integer.parseInt(edBX.getText());
				case "CX": return Integer.parseInt(edCX.getText());
				case "DX": return Integer.parseInt(edDX.getText());
				default:
					return 0;
			}
		}
	}
	
	/**
	 * Set value to register cell
	 * 
	 * @param regName Name of the register
	 * @param value Value to set
	 * 
	 * @throws Exception
	 */
	public void setRegisterValue(String regName, Integer value) {
		switch (regName.toUpperCase()) {
		case "IP": edIP.setText(value.toString()); break;
		case "SP": edSP.setText(value.toString()); break;
		case "BP": edBP.setText(value.toString()); break;
		case "AX": edAX.setText(value.toString()); break;
		case "BX": edBX.setText(value.toString()); break;
		case "CX": edCX.setText(value.toString()); break;
		case "DX": edDX.setText(value.toString()); break;
		default:
		}
	}
	
	/**
	 * Return true, if param is the register
	 * @param param
	 * @return
	 */
	public boolean isRegister(String param) {
		param = param.toUpperCase();
		if (param.equals("IP") || param.equals("SP") || param.equals("AX") || param.equals("BX") ||
				param.equals("CX") || param.equals("DX") || param.startsWith("BP"))
			return true;
		
		return false;
	}
	
	/**
	 * Return true, if param is the register
	 * 
	 * @param param
	 * @return
	 */
	public boolean isAddress(String param) {
		if (param.length() >= 3 && param.charAt(0) == '[' && param.charAt(param.length() - 1) == ']')
			return true;
		
		return false;
	}
	
	public String getValueFrom(String param) throws Exception {
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
					throw new Exception("Unknow addresse: " + param2);
				}
			}
			
			if (memAddress > 63 || memAddress < 0) {
				throw new Exception("Invalid addresse: " + param2);
			}
			
			MemoryCell cell = (MemoryCell)tblMemory.getItems().get(memAddress);
			return cell.getValue();
		} else {
			return param;
		}
	}
	
	public void setValueTo(String param, String value) throws Exception {
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
					throw new Exception("Unknow addresse: " + param2);
				}
			}
			
			if (memAddress > 63 || memAddress < 0) {
				throw new Exception("Invalid addresse: " + param2);
			}
			
			MemoryCell cell = (MemoryCell)tblMemory.getItems().get(memAddress);
			cell.setValue(value);
		} else {
			throw new Exception("Invalid destination: " + param);
		}
	}
	
	@FXML
	public void onRunClicked() {
		int ip = Integer.parseInt(edIP.getText());
		
		MemoryCell cell = (MemoryCell) tblMemory.getItems().get(ip);
		
		setRegisterValue("IP", getRegisterValue("IP") + 1);
		
		String cmd = cell.getValue();
		
		String [] cmdTokens = cmd.split(" ", 2);
		cmdTokens[0] = cmdTokens[0].trim();
		String params = "";
		if (cmdTokens.length > 1)
			params = cmdTokens[1];
		params = params.trim();
		
		switch (cmdTokens[0].toUpperCase()) {
		
		case "MOV":
			String [] p = params.split(",", 2);
			p[0] = p[0].trim();
			p[1] = p[1].trim();
			if (p.length == 2) {
				try {
					String valueFrom = getValueFrom(p[1]);
					setValueTo(p[0], valueFrom);
					
					if (valueFrom.equals("0")) {
						setFlags(1, 0);
					} else {
						setFlags(0, 0);
					}
				} catch (Exception e) {
					showAlert(AlertType.ERROR, "Error", "Error executing command", e.getMessage());
				}
			} else {
				showAlert(AlertType.ERROR, "Error", "Invalid mov parameters", "Invalid parameters: " + params);
			}
			break;
			
		case "PUSH":
			try {
				String val = getValueFrom(params);
				Integer stack = getRegisterValue("SP") - 1;
				
				if (stack < 0 || stack > 63) {
					showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, "Invalid SP value");
					break;
				}
				setRegisterValue("SP", stack);
				
				cell = (MemoryCell)tblMemory.getItems().get(stack);
				cell.setValue(val);
				
				if (val.equals("0"))
					setFlags(1, 0);
				else
					setFlags(0, 0);
				
				tblMemory.refresh();
				
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, "Invalid parameter: " + params);
			}
			break;
			
		case "POP":
			try {
				Integer stack = getRegisterValue("SP");
				if (stack < 0 || stack > 63) {
					showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, "Invalid SP value");
					break;
				}
				
				setRegisterValue("SP", stack + 1);
				
				cell = (MemoryCell)tblMemory.getItems().get(stack);
				setValueTo(params, cell.getValue());
				
				if (cell.getValue().equals("0"))
					setFlags(1, 0);
				else
					setFlags(0, 0);
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, "Invalid parameter: " + params);
			}

			break;
			
		case "CALL":
			try {
				Integer address = Integer.parseInt(getValueFrom(params));
				if (address < 0 || address > 63)
					throw new Exception("Invalid address: " + address);
				
				Integer stack = getRegisterValue("SP") - 1;
				if (stack < 0 || stack > 63) {
					showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, "Invalid SP value");
					break;
				}
				setRegisterValue("SP", stack);
				
				cell = (MemoryCell)tblMemory.getItems().get(stack);
				cell.setValue(String.valueOf(getRegisterValue("IP")));
				
				setRegisterValue("IP", address);
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, e.getMessage());
			}
			setFlags(0, 0);
			break;
			
		case "RET":
			try {
				Integer stack = getRegisterValue("SP");
				if (stack < 0 || stack > 63) {
					showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, "Invalid SP value");
					break;
				}
				setRegisterValue("SP", stack + 1);
				
				cell = (MemoryCell)tblMemory.getItems().get(stack);
				setRegisterValue("IP", Integer.parseInt(cell.getValue()));
			} catch (Exception e) {
				
			}
			setFlags(0, 0);
			break;
		
		case "JMP":
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
				
				if (params.toUpperCase().equals("IP"))
					setRegisterValue("IP", address - 1);
				else
					setRegisterValue("IP", address);
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, e.getMessage());
			}
			setFlags(0, 0);
			break;
			
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
				
				if (lbZF.getText() == "1")
					setRegisterValue("IP", address);
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, e.getMessage());
			}
			setFlags(0, 0);
			break;
			
		case "JNE":
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
				
				if (lbZF.getText() == "0")
					setRegisterValue("IP", address);
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, e.getMessage());
			}
			setFlags(0, 0);
			break;			
			
		case "LOOP":
			try {
				String val = getValueFrom(params);
				
				Integer cx = getRegisterValue("CX") - 1;
				setRegisterValue("CX", cx);
				
				if (cx != 0) {
					setRegisterValue("IP", Integer.parseInt(val));
					setFlags(1, 0);
				} else {
					setFlags(0, 0);
				}
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, e.getMessage());
			}
			break;
			
		case "INC":
			try {
				Integer val = Integer.parseInt(getValueFrom(params));
				setValueTo(params, String.valueOf(val + 1));
				
				if (val + 1 == 0) {
					setFlags(1, 0);
				} else {
					setFlags(0, 0);
				}
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, e.getMessage());
			}
			break;
			
		case "DEC":
			try {
				Integer val = Integer.parseInt(getValueFrom(params));
				setValueTo(params, String.valueOf(val - 1));
				
				if (val - 1 == 0) {
					setFlags(1, 0);
				} else {
					setFlags(0, 0);
				}
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, e.getMessage());
			}
			break;
			
		case "ADD":
			String [] ops = params.split(",", 2);
			ops[0] = ops[0].trim();
			ops[1] = ops[1].trim();
			if (ops.length == 2) {
				try {
					Integer value1 = Integer.parseInt(getValueFrom(ops[1]));
					Integer value2 = Integer.parseInt(getValueFrom(ops[0]));
					
					setValueTo(ops[0], String.valueOf(value1 + value2));
					if (value1 + value2 == 0) {
						setFlags(1, 0);
					} else {
						setFlags(0, 0);
					}
				} catch (Exception e) {
					showAlert(AlertType.ERROR, "Error", "Error executing command", e.getMessage());
				}
			} else {
				showAlert(AlertType.ERROR, "Error", "Invalid mov parameters", "Invalid parameters: " + params);
			}
			break;
			
		case "SUB":
			ops = params.split(",", 2);
			ops[0] = ops[0].trim();
			ops[1] = ops[1].trim();
			if (ops.length == 2) {
				try {
					Integer value1 = Integer.parseInt(getValueFrom(ops[1]));
					Integer value2 = Integer.parseInt(getValueFrom(ops[0]));
					
					setValueTo(ops[0], String.valueOf(value2 - value1));
					
					if (value2 - value1 == 0) {
						setFlags(1, 0);
					} else if (value2 < value1) {
						setFlags(0, 1);
					} else {
						setFlags(0, 0);
					}
				} catch (Exception e) {
					showAlert(AlertType.ERROR, "Error", "Error executing command", e.getMessage());
				}
			} else {
				showAlert(AlertType.ERROR, "Error", "Invalid mov parameters", "Invalid parameters: " + params);
			}
			break;			
			
		case "CMP":
			ops = params.split(",", 2);
			ops[0] = ops[0].trim();
			ops[1] = ops[1].trim();
			if (ops.length == 2) {
				try {
					Integer value1 = Integer.parseInt(getValueFrom(ops[1]));
					Integer value2 = Integer.parseInt(getValueFrom(ops[0]));
					
					if (value1.intValue() == value2.intValue()) {
						setFlags(1, 0);
					} else {
						if (value2.intValue() < value1.intValue())
							setFlags(0, 1);
						else
							setFlags(0, 0);
					}
				} catch (Exception e) {
					showAlert(AlertType.ERROR, "Error", "Error executing command", e.getMessage());
				}
			} else {
				showAlert(AlertType.ERROR, "Error", "Invalid mov parameters", "Invalid parameters: " + params);
			}
			break;					
			
		case "OUT":
			try {
				String val = getValueFrom(params);
				edOutput.appendText(val + "\n");
			} catch (Exception e) {
				showAlert(AlertType.ERROR, "Error", "Error executing command " + cmd, e.getMessage());
			}
			setFlags(0, 0);
			break;
			
		default:
			showAlert(AlertType.ERROR, "Error", "Error executing command", "Unknown command: " + cmdTokens[0]);
		}
		
		tblMemory.refresh();
	}
	
	public void setFlags(int zf, int sf) {
		lbZF.setText(String.valueOf(zf));
		lbSF.setText(String.valueOf(sf));
	}
	
	/**
	 * Reset all components
	 */
	@FXML
	public void onResetClicked() {
		edIP.setText("0");
		edSP.setText("15");
		edBP.setText("15");
		edAX.setText("0");
		edBX.setText("0");
		edCX.setText("0");
		edDX.setText("0");
		setFlags(0, 0);
		
		for (int i = 0; i < tblMemory.getItems().size(); i++) {
			MemoryCell cell = (MemoryCell)tblMemory.getItems().get(i);
			cell.setValue("0");
		}
		
		tblMemory.refresh();
		edOutput.clear();
	}
}
