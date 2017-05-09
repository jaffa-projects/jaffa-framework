// This function will display the relevant attachmentType field, hiding all others
function remoteOptions(remote, formName) {

// Use this code to make realPrinter Mandatory when type is Local
//  if (remote[0].checked == true) {
//    document.getElementById(formName + '_realPrinterName').setAttribute("rules-mandatory","");
//  }  else if (remote[1].checked == true) {
//    document.getElementById(formName + '_realPrinterName').removeAttribute("rules-mandatory");
//  } 
//In the meantime always set it mandatory
    document.getElementById(formName + '_realPrinterName').setAttribute("rules-mandatory","");

}
