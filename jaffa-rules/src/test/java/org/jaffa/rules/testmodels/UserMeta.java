package org.jaffa.rules.testmodels;


import org.jaffa.metadata.FieldMetaData;
import org.jaffa.metadata.StringFieldMetaData;

public class UserMeta {
    public static FieldMetaData[] getKeyFields() {
        return new FieldMetaData[]{new StringFieldMetaData("id", "[label.Jaffa.Test.User.UserId]", Boolean.FALSE, null, new Integer(80), null)};
    }

    public static FieldMetaData[] getMandatoryFields() {
        return new FieldMetaData[]{new StringFieldMetaData("id", "[label.Jaffa.Test.User.UserId]", Boolean.TRUE, null, new Integer(80), null)};
    }
}
