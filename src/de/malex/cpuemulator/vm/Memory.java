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

import de.malex.cpuemulator.MemoryCell;
import de.malex.cpuemulator.constants.Constants;
import de.malex.cpuemulator.constants.Messages;
import de.malex.cpuemulator.constants.Registers;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * RAM
 */
@SuppressWarnings("rawtypes")
public class Memory {

	/**
	 * The {@link TextField} to display value
	 */
	private TableView memTable;
	
	/**
	 * Create new {@link Register} object
	 * 
	 * @param name Name of the register
	 * @param field The {@link TextField} to display value
	 */
	public Memory(VM vm, int size, TableView memTable) {
		
		this.memTable = memTable;
		
		TableColumn<MemoryCell, Integer> addressCol = (TableColumn<MemoryCell, Integer>) memTable.getColumns().get(0);
		TableColumn<MemoryCell, String> valueCol = (TableColumn<MemoryCell, String>) memTable.getColumns().get(1);

		addressCol.setCellValueFactory(new PropertyValueFactory<MemoryCell, Integer>("address"));
		valueCol.setCellValueFactory(new PropertyValueFactory<MemoryCell, String>("value"));
		
		valueCol.setCellFactory(TextFieldTableCell.forTableColumn());
		valueCol.setOnEditCommit(
                  new EventHandler<CellEditEvent<MemoryCell, String>>() {
                      @Override
                      public void handle(CellEditEvent<MemoryCell, String> t) {
                          ((MemoryCell) t.getTableView().getItems().get(
                                  t.getTablePosition().getRow())).setValue(t.getNewValue());
                      }
                  });
		  
		 ObservableList<MemoryCell> data = FXCollections.observableArrayList();
		 for (int i = 0; i < size; i++)
			 data.add(new MemoryCell(i, "0"));
		 
		 memTable.setItems(data);
		 
		 addressCol.setCellFactory(new Callback<TableColumn<MemoryCell, Integer>, TableCell<MemoryCell, Integer>>() {
		      @Override public TableCell<MemoryCell, Integer> call(TableColumn<MemoryCell, Integer> soCalledFriendBooleanTableColumn) {
		        return new TableCell<MemoryCell, Integer>() {
		          @Override public void updateItem(final Integer item, final boolean empty) {
		            super.updateItem(item, empty);
		            
		            if (item != null) {
		            	try {
		            		Integer currentIp = vm.getRegisterValue(Registers.REG_IP);
		            		Integer currentSp = vm.getRegisterValue(Registers.REG_SP);
		            		Integer currentBp = vm.getRegisterValue(Registers.REG_BP);
		            		
		            		if (currentIp.intValue() == item.intValue()) {
		            			getTableRow().setStyle(Constants.ROW_STYLE_IP);
		            		} else if (currentSp.intValue() == item.intValue()) {
		            			getTableRow().setStyle(Constants.ROW_STYLE_SP);
		            		} else if (currentBp.intValue() == item.intValue()) {
		            			getTableRow().setStyle(Constants.ROW_STYLE_BP);
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
	}
	
	/**
	 * Get value from a memory cell
	 * 
	 * @param addr Address of the cell
	 * 
	 * @return Value from a memory cell
	 * 
	 * @throws VMException If address is out of range
	 */
	public String getValue(int addr) throws VMException {
		if (addr < Constants.MEMORY_START_ADDR || addr > Constants.MEMORY_END_ADDR) {
			throw new VMException(String.format(Messages.MSG_INVALID_ADDR, String.valueOf(addr)));
		}
		
		MemoryCell cell = (MemoryCell)memTable.getItems().get(addr);
		
		return cell.getValue();
	}
	
	/**
	 * Store value to a memory cell
	 * 
	 * @param addr Address of the cell
	 * @param value Value to the store
	 * 
	 * @throws VMException 
	 */
	public void setValue(int addr, String value) throws VMException {
		if (addr < Constants.MEMORY_START_ADDR || addr > Constants.MEMORY_END_ADDR) {
			throw new VMException(String.format(Messages.MSG_INVALID_ADDR, String.valueOf(addr)));
		}
		
		MemoryCell cell = (MemoryCell)memTable.getItems().get(addr);
		cell.setValue(value);
	}
	
	/**
	 * Reset memory content to 0
	 */
	public void reset() {
		for (int i = 0; i < memTable.getItems().size(); i++) {
			MemoryCell cell = (MemoryCell)memTable.getItems().get(i);
			cell.setValue("0");
		}
		
		update();
	}
	
	/**
	 * Update content of the memory table
	 */
	public void update() {
		memTable.refresh();
	}
}
