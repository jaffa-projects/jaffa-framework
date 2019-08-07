package org.jaffa.modules.printing.components.printerdefinitionmaintenance.tx;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.printing.components.printerdefinitionmaintenance.dto.PrinterDefinitionMaintenanceDeleteInDto;
import org.jaffa.persistence.UOW;

public interface IPrinterDefinitionMaintenanceTxHandler {

	 public void validate(UOW uow, PrinterDefinitionMaintenanceDeleteInDto printerDefinition) throws FrameworkException, ApplicationExceptions;

}
