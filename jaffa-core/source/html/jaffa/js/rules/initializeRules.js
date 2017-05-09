//create a new rules object
var validationRules = new rules();

//Register Field Rules
validationRules.addFieldRule("rules-trim", "trimWhitespace");
validationRules.addFieldRule("rules-datatype", "checkDataType");

//Register Page Rules.
validationRules.addGlobalRule("rules-mandatory" , "mandatory");
