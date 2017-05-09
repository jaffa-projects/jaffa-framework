// This function will display the relevant Recurrence options, hiding all others
function hideUnhideRecurrenceOptions(recurrence, formName) {
  if (recurrence[1].checked == true) {
    document.getElementById('recurrenceOften').style.display='block';
    document.getElementById('recurrenceDaily').style.display='none';
    document.getElementById('recurrenceWeekly').style.display='none';
    document.getElementById('recurrenceMonthly').style.display='none';
    document.getElementById('recurrenceYearly').style.display='none';
    document.getElementById('recurrenceCustom').style.display='none';
    document.getElementById(formName + '_yearlyFrequency').removeAttribute("rules-mandatory");
    document.getElementById(formName + '_customPattern').removeAttribute("rules-mandatory");
  } else if (recurrence[2].checked == true) {
    document.getElementById('recurrenceOften').style.display='none';
    document.getElementById('recurrenceDaily').style.display='block';
    document.getElementById('recurrenceWeekly').style.display='none';
    document.getElementById('recurrenceMonthly').style.display='none';
    document.getElementById('recurrenceYearly').style.display='none';
    document.getElementById('recurrenceCustom').style.display='none';
    document.getElementById(formName + '_yearlyFrequency').removeAttribute("rules-mandatory");
    document.getElementById(formName + '_customPattern').removeAttribute("rules-mandatory");
  } else if (recurrence[3].checked == true) {
    document.getElementById('recurrenceOften').style.display='none';
    document.getElementById('recurrenceDaily').style.display='none';
    document.getElementById('recurrenceWeekly').style.display='block';
    document.getElementById('recurrenceMonthly').style.display='none';
    document.getElementById('recurrenceYearly').style.display='none';
    document.getElementById('recurrenceCustom').style.display='none';
    document.getElementById(formName + '_yearlyFrequency').removeAttribute("rules-mandatory");
    document.getElementById(formName + '_customPattern').removeAttribute("rules-mandatory");
  } else if (recurrence[4].checked == true) {
    document.getElementById('recurrenceOften').style.display='none';
    document.getElementById('recurrenceDaily').style.display='none';
    document.getElementById('recurrenceWeekly').style.display='none';
    document.getElementById('recurrenceMonthly').style.display='block';
    document.getElementById('recurrenceYearly').style.display='none';
    document.getElementById('recurrenceCustom').style.display='none';
    document.getElementById(formName + '_yearlyFrequency').removeAttribute("rules-mandatory");
    document.getElementById(formName + '_customPattern').removeAttribute("rules-mandatory");
  } else if (recurrence[5].checked == true) {
    document.getElementById('recurrenceOften').style.display='none';
    document.getElementById('recurrenceDaily').style.display='none';
    document.getElementById('recurrenceWeekly').style.display='none';
    document.getElementById('recurrenceMonthly').style.display='none';
    document.getElementById('recurrenceYearly').style.display='block';
    document.getElementById('recurrenceCustom').style.display='none';
    document.getElementById(formName + '_yearlyFrequency').setAttribute("rules-mandatory","");
    document.getElementById(formName + '_customPattern').removeAttribute("rules-mandatory");
  } else if (recurrence[6].checked == true) {
    document.getElementById('recurrenceOften').style.display='none';
    document.getElementById('recurrenceDaily').style.display='none';
    document.getElementById('recurrenceWeekly').style.display='none';
    document.getElementById('recurrenceMonthly').style.display='none';
    document.getElementById('recurrenceYearly').style.display='none';
    document.getElementById('recurrenceCustom').style.display='block';
    document.getElementById(formName + '_yearlyFrequency').removeAttribute("rules-mandatory");
    document.getElementById(formName + '_customPattern').setAttribute("rules-mandatory","");
  } else {
    // if recurrence[0].checked == true OR if nothing has been selected
    document.getElementById('recurrenceOften').style.display='none';
    document.getElementById('recurrenceDaily').style.display='none';
    document.getElementById('recurrenceWeekly').style.display='none';
    document.getElementById('recurrenceMonthly').style.display='none';
    document.getElementById('recurrenceYearly').style.display='none';
    document.getElementById('recurrenceCustom').style.display='none';
    document.getElementById(formName + '_yearlyFrequency').removeAttribute("rules-mandatory");
    document.getElementById(formName + '_customPattern').removeAttribute("rules-mandatory");
  } 
}


// This function will enable the relevant MonthlyType options, disabling all others
function enableDisableMonthlyTypeOptions(monthlyType, formName) {
  if (monthlyType[1].checked == true) {
    document.getElementById(formName + '_monthlyDay').disabled=true;
    document.getElementById(formName + '_monthlyWeekFrequency').disabled=false;
    document.getElementById(formName + '_monthlyWeekDay').disabled=false;
  } else {
    // if monthlyType[0].checked == true OR if nothing has been selected
    document.getElementById(formName + '_monthlyDay').disabled=false;
    document.getElementById(formName + '_monthlyWeekFrequency').disabled=true;
    document.getElementById(formName + '_monthlyWeekDay').disabled=true;
  } 
}

