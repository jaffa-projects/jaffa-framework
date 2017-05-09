// This function will display the relevant attachmentType field, hiding all others
function hideUnhideAttachmentTypeOptions(attachmentType, formName) {
  if (attachmentType[1].checked == true) {
    document.getElementById('attachmentTypeL').style.display='none';
    document.getElementById('attachmentTypeW').style.display='block';
    document.getElementById('attachmentTypeE').style.display='none';
    document.getElementById(formName + '_localLink').removeAttribute("rules-mandatory");
    document.getElementById(formName + '_webLink').setAttribute("rules-mandatory","");
  } else if (attachmentType[2].checked == true) {
    document.getElementById('attachmentTypeL').style.display='none';
    document.getElementById('attachmentTypeW').style.display='none';
    document.getElementById('attachmentTypeE').style.display='block';
    document.getElementById(formName + '_localLink').removeAttribute("rules-mandatory");
    document.getElementById(formName + '_webLink').removeAttribute("rules-mandatory");
  } else {
    // if attachmentType[0].checked == true OR if nothing has been selected
    document.getElementById('attachmentTypeL').style.display='block';
    document.getElementById('attachmentTypeW').style.display='none';
    document.getElementById('attachmentTypeE').style.display='none';
    document.getElementById(formName + '_localLink').setAttribute("rules-mandatory","");
    document.getElementById(formName + '_webLink').removeAttribute("rules-mandatory");
  } 
}
