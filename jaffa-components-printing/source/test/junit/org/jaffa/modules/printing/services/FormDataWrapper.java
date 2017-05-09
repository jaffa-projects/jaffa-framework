/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package org.jaffa.modules.printing.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import junit.framework.Test;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;
import org.jaffa.modules.printing.domain.FormGroup;
import org.jaffa.unittest.AbstractDataWrapper;
import org.jaffa.unittest.UnitTestUtil;

import org.jaffa.modules.printing.services.*;
import org.jaffa.util.URLHelper;

/** This class has the methods for one time setup/cleanup of form printint data,
 * before all the tests executed by the suite utilising this class.
 *
 * @author  PaulE
 */
public class FormDataWrapper extends AbstractDataWrapper {

    private static final Logger log = Logger.getLogger(FormDataWrapper.class);
    
    // Test Data Statics
    public static final String FORM_NAME = "Invoice";
    public static final String FORM_NAME2 = "Shipping Label";
    public static final String FORM_NAME3 = "Form Group";
    public static final String PDF_TEMPLATE = "file:///" + UnitTestUtil.getDataDirectory() + "/resources/templates/InvoiceTemplate.pdf";
    public static final String PDF_TEMPLATE3 = "file:///" + UnitTestUtil.getDataDirectory() + "/resources/templates/FormGroup_blocks.pdf";
    public static final String VELOCITY_TEMPLATE = "file:///" + UnitTestUtil.getDataDirectory() + "/resources/templates/ShippingLabelTemplate.vtl";
    
    public static final String VAILD_PDF_PRINTER = "Savin";
    public static final String REAL_PDF_PRINTER = /* "////iolite2//savin" */ "PrimoPDF"; // Use this fake printer to save paper!
    
    public static final String VAILD_LABEL_PRINTER = "Barcode1";
    
    /** The constructor.
     * @param test The Test class, for which the Wrapper will be utilised.
     */
    public FormDataWrapper(Test test) {
        super(test);
    }
    
    protected void setUpData(Connection connection)
    throws Exception {
        addOutputTypes(connection);
        addOutputCommands(connection);
        addFormGroups(connection);
        addFormDefinitions(connection);
        addFormTemplates(connection);
        addFormEvents(connection);
        addFormUsages(connection);
        addPrinterDefinitions(connection);
    }

    private void addOutputTypes(Connection connection)
    throws SQLException {
        //1=OUTPUT_TYPE, DESCRIPTION, DIRECT_PRINTING, CREATED_ON, CREATED_BY
        //6=LAST_CHANGED_ON, LAST_CHANGED_BY
        String sql = "insert into J_OUTPUT_TYPES values (?,?,?,?,?,  ?,?)";
        PreparedStatement pstmnt = connection.prepareStatement(sql);
        
        // Create first record
        pstmnt.setString(1, "PDF");
        pstmnt.setString(2, "Portable Document Format");
        pstmnt.setBoolean(3, false);
        pstmnt.setTimestamp(4, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(5, "user2");
        pstmnt.setTimestamp(6,(new DateTime()).timestamp());
        pstmnt.setString(7, "user3");
        pstmnt.execute();
        pstmnt.clearParameters();
        
        // Create second record
        pstmnt.setString(1, "Intermec 3400D");
        pstmnt.setString(2, "Intermec 3400D Label Printer");
        pstmnt.setBoolean(3, true);
        pstmnt.setTimestamp(4, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(5, "user2");
        pstmnt.setTimestamp(6,null);
        pstmnt.setString(7, null);
        pstmnt.execute();
        pstmnt.close();
    }
    
    private void addOutputCommands(Connection connection)
    throws SQLException {
        //1=OUTPUT_COMMAND_ID, OUTPUT_TYPE, SEQUENCE_NO, OS_PATTERN, COMMAND_LINE
        //6=CREATED_ON, CREATED_BY, LAST_CHANGED_ON, LAST_CHANGED_BY
        String sql = "insert into J_OUTPUT_COMMANDS values (?,?,?,?,?,  ?,?,?,?)";
        PreparedStatement pstmnt = connection.prepareStatement(sql);
        
        // Create first record
        pstmnt.setLong(1, 1000000);
        pstmnt.setString(2, "PDF");
        pstmnt.setLong(3, 1);
        pstmnt.setString(4, "Windows.*");
        //pstmnt.setString(5, "\"c:\\Program Files\\Adobe\\Acrobat 5.0\\Acrobat\\Acrobat.exe\" /t {0} {1} {2} {3}");
        pstmnt.setString(5, "\"C:\\Program Files\\Ghostgum\\gsview\\gsprint\" -printer \"{1}\" {2} {3} {0}");
        pstmnt.setTimestamp(6, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(7, "user2");
        pstmnt.setTimestamp(8,(new DateTime()).timestamp());
        pstmnt.setString(9, "user3");
        pstmnt.execute();
        pstmnt.clearParameters();
        
        // Create second record
        pstmnt.setLong(1, 1000001);
        pstmnt.setString(2, "PDF");
        pstmnt.setLong(3, 2);
        pstmnt.setString(4, ".*");
        pstmnt.setString(5, "/usr/gs/gsview \"{0}\" -p\"{1}\" {2} {3}");
        pstmnt.setTimestamp(6, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(7, "user2");
        pstmnt.setTimestamp(8,null);
        pstmnt.setString(9, null);
        pstmnt.execute();
        pstmnt.clearParameters();


        // Print on unix
        //pstmnt.setString(5, "lp -d\"{1}\" {2} {3} \"{0}\" ");

        pstmnt.close();
    }
    
    private void addFormGroups(Connection connection)
    throws SQLException {
        String sql = "insert into J_FORM_GROUPS values (?,?,?,?,?,?,?)";
        PreparedStatement pstmnt = connection.prepareStatement(sql);
        
        // Create first record
        pstmnt.setString(1, FORM_NAME);
        pstmnt.setString(2, FORM_NAME + " Description");
        pstmnt.setString(3, FormPrintFactory.ENGINE_TYPE_ITEXT);
        pstmnt.setTimestamp(4, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(5, "user2");
        pstmnt.setTimestamp(6,(new DateTime()).timestamp());
        pstmnt.setString(7, "user3");
        pstmnt.execute();
        pstmnt.clearParameters();
        
        // Create second record
        pstmnt.setString(1, FORM_NAME2);
        pstmnt.setString(2, FORM_NAME2 + " Description");
        pstmnt.setString(3, FormPrintFactory.ENGINE_TYPE_VELOCITY);
        pstmnt.setTimestamp(4, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(5, "user2");
        pstmnt.setTimestamp(6,null);
        pstmnt.setString(7, null);
        pstmnt.execute();
        pstmnt.clearParameters();

        // Create thrid record
        pstmnt.setString(1, FORM_NAME3);
        pstmnt.setString(2, FORM_NAME3 + " Description");
        pstmnt.setString(3, FormPrintFactory.ENGINE_TYPE_ITEXT);
        pstmnt.setTimestamp(4, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(5, "user2");
        pstmnt.setTimestamp(6,(new DateTime()).timestamp());
        pstmnt.setString(7, "user3");
        pstmnt.execute();
        pstmnt.clearParameters();
        
        pstmnt.close();
    }

    private void addFormDefinitions(Connection connection)
    throws SQLException {
        //1=FORM_ID, FORM_NAME, FORM_ALTERNATE, FORM_VARIATION, OUTPUT_TYPE
        //6=FORM_TEMPLATE, FIELD_LAYOUT, DESCRIPTION, REMARKS, DOM_FACTORY
        //11=DOM_CLASS, DOM_KEY1, DOM_KEY2, DOM_KEY3, ADDITIONAL_DATA_COMPONENT
        //16=FOLLOW_ON_FORM_NAME, FOLLOW_ON_FORM_ALTERNATE, CREATED_ON, CREATED_BY, LAST_CHANGED_ON
        //21=LAST_CHANGED_BY
        String sql = "insert into J_FORM_DEFINITIONS values (?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?)";
        PreparedStatement pstmnt = connection.prepareStatement(sql);
        
        // Create first record 
        //1-5
        pstmnt.setLong(1, 1000000);
        pstmnt.setString(2, FORM_NAME);
        pstmnt.setString(3, null);
        pstmnt.setString(4, null);
        pstmnt.setString(5, "PDF");
        //6-10
        pstmnt.setString(6, PDF_TEMPLATE);
        pstmnt.setString(7, PDF_TEMPLATE+".csv");
        pstmnt.setString(8, "Standard Invoice Version");
        pstmnt.setString(9, null);
        pstmnt.setString(10, CustomDataBeanFactory.class.getName());
        //11-15
        pstmnt.setString(11, InvoiceDataBean.class.getName());
        pstmnt.setString(12, "orderNo");
        pstmnt.setString(13, null);
        pstmnt.setString(14, null);
        pstmnt.setString(15, null);
        //16-21
        pstmnt.setString(16, null);
        pstmnt.setString(17, null);
        pstmnt.setTimestamp(18, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(19, "user2");
        pstmnt.setTimestamp(20,(new DateTime()).timestamp());
        pstmnt.setString(21, "user3");
        pstmnt.execute();
        pstmnt.clearParameters();

        // Create second record 
        //1-5
        pstmnt.setLong(1, 1000001);
        pstmnt.setString(2, FORM_NAME2);
        pstmnt.setString(3, null);
        pstmnt.setString(4, null);
        pstmnt.setString(5, "Intermec 3400D");
        //6-10
        pstmnt.setString(6, VELOCITY_TEMPLATE);
        pstmnt.setString(7, null);
        pstmnt.setString(8, "Standard Shipping Label");
        pstmnt.setString(9, null);
        pstmnt.setString(10, CustomDataBeanFactory.class.getName());
        //11-15
        pstmnt.setString(11, InvoiceDataBean.class.getName());
        pstmnt.setString(12, "orderNo");
        pstmnt.setString(13, null);
        pstmnt.setString(14, null);
        pstmnt.setString(15, null);
        //16-21
        pstmnt.setString(16, null);
        pstmnt.setString(17, null);
        pstmnt.setTimestamp(18, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(19, "user2");
        pstmnt.setTimestamp(20,null);
        pstmnt.setString(21, null);
        pstmnt.execute();
        pstmnt.clearParameters();

        // Create third record 
        //1-5
        pstmnt.setLong(1, 1000002);
        pstmnt.setString(2, FORM_NAME3);
        pstmnt.setString(3, null);
        pstmnt.setString(4, null);
        pstmnt.setString(5, "PDF");
        //6-10
        pstmnt.setString(6, PDF_TEMPLATE3);
        pstmnt.setString(7, PDF_TEMPLATE3+".csv");
        pstmnt.setString(8, "Standard Invoice Version");
        pstmnt.setString(9, null);
        pstmnt.setString(10, DomainDataBeanFactory.class.getName());
        //11-15
        pstmnt.setString(11, FormGroup.class.getName());
        pstmnt.setString(12, "formName");
        pstmnt.setString(13, null);
        pstmnt.setString(14, null);
        pstmnt.setString(15, null);
        //16-21
        pstmnt.setString(16, null);
        pstmnt.setString(17, null);
        pstmnt.setTimestamp(18, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(19, "user2");
        pstmnt.setTimestamp(20,(new DateTime()).timestamp());
        pstmnt.setString(21, "user3");
        pstmnt.execute();
        pstmnt.clearParameters();

        pstmnt.close();
    }

    private void addFormTemplates(Connection connection)
    throws Exception {
        //1=FORM_ID, TEMPLATE_DATA, LAYOUT_DATA
        String sql = "insert into J_FORM_TEMPLATES values (?,?,?)";
        PreparedStatement pstmnt = connection.prepareStatement(sql);
        
        // Create first record 
        pstmnt.setLong(1, 1000000);
        URL u = URLHelper.newExtendedURL(PDF_TEMPLATE);
        InputStream is = u.openStream();
        pstmnt.setBinaryStream(2,is,is.available());
        u = URLHelper.newExtendedURL(PDF_TEMPLATE+".csv");
        is = u.openStream();
        pstmnt.setBinaryStream(3,is,is.available());
        pstmnt.execute();
        pstmnt.clearParameters();

        // Create second record 
        pstmnt.setLong(1, 1000001);
        u = URLHelper.newExtendedURL(VELOCITY_TEMPLATE);
        is = u.openStream();
        pstmnt.setBinaryStream(2,is,is.available());
        pstmnt.setObject(3,null);
        pstmnt.execute();
        pstmnt.clearParameters();

        // Create third record 
        pstmnt.setLong(1, 1000002);
        u = URLHelper.newExtendedURL(PDF_TEMPLATE3);
        is = u.openStream();
        pstmnt.setBinaryStream(2,is,is.available());
        u = URLHelper.newExtendedURL(PDF_TEMPLATE3+".csv");
        is = u.openStream();
        pstmnt.setBinaryStream(3,is,is.available());
        pstmnt.execute();
        pstmnt.clearParameters();

        pstmnt.close();
    }
    
    private void addFormEvents(Connection connection)
    throws SQLException {
        //1=EVENT_NAME, DESCRIPTION, CREATED_ON, CREATED_BY, LAST_CHANGED_ON,
        //6=LAST_CHANGED_BY
        String sql = "insert into J_FORM_EVENTS values (?,?,?,?,?,  ?)";
        PreparedStatement pstmnt = connection.prepareStatement(sql);
 
        // Create first record
        pstmnt.setString(1, "PO");
        pstmnt.setString(2, "Create Purchase Order");
        pstmnt.setTimestamp(3, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(4, "user2");
        pstmnt.setTimestamp(5,(new DateTime()).timestamp());
        pstmnt.setString(6, "user3");
        pstmnt.execute();
        pstmnt.clearParameters();
        
        // Create second record
        pstmnt.setString(1, "Ship");
        pstmnt.setString(2, "Ship Material");
        pstmnt.setTimestamp(3, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(4, "user2");
        pstmnt.setTimestamp(5,null);
        pstmnt.setString(6, null);
        pstmnt.execute();
        pstmnt.clearParameters();

        // Create thrid record
        pstmnt.setString(1, "Form Group");
        pstmnt.setString(2, "Complete Group Definition");
        pstmnt.setTimestamp(3, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(4, "user2");
        pstmnt.setTimestamp(5,(new DateTime()).timestamp());
        pstmnt.setString(6, "user3");
        pstmnt.execute();
        pstmnt.clearParameters();

        pstmnt.close();
    }

    private void addFormUsages(Connection connection)
    throws SQLException {
        //1=FORM_NAME, EVENT_NAME, FORM_ALTERNATE, COPIES, CREATED_ON
        //6=CREATED_BY, LAST_CHANGED_ON, LAST_CHANGED_BY
        String sql = "insert into J_FORM_USAGES values (?,?,?,?,?,  ?,?,?)";
        PreparedStatement pstmnt = connection.prepareStatement(sql);
 
        // Create first record
        pstmnt.setString(1, FORM_NAME);
        pstmnt.setString(2, "PO");
        pstmnt.setString(3, null);
        pstmnt.setLong(4, 1);
        pstmnt.setTimestamp(5, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(6, "user2");
        pstmnt.setTimestamp(7,(new DateTime()).timestamp());
        pstmnt.setString(8, "user3");
        pstmnt.execute();
        pstmnt.clearParameters();
        
        // Create second record
        pstmnt.setString(1, FORM_NAME2);
        pstmnt.setString(2, "Ship");
        pstmnt.setString(3, null);
        pstmnt.setLong(4, 2);
        pstmnt.setTimestamp(5, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(6, "user2");
        pstmnt.setTimestamp(7,null);
        pstmnt.setString(8, null);
        pstmnt.execute();
        pstmnt.clearParameters();

        // Create third record
        pstmnt.setString(1, FORM_NAME3);
        pstmnt.setString(2, "Form Group");
        pstmnt.setString(3, null);
        pstmnt.setLong(4, 1);
        pstmnt.setTimestamp(5, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(6, "user2");
        pstmnt.setTimestamp(7,(new DateTime()).timestamp());
        pstmnt.setString(8, "user3");
        pstmnt.execute();
        pstmnt.clearParameters();
        
        pstmnt.close();
    }
    
    private void addPrinterDefinitions(Connection connection)
    throws SQLException {
        //1=PRINTER_ID, DESCRIPTION, SITE_CODE, LOCATION_CODE, REMOTE_PRINTER,
        //6=REAL_PRINTER_NAME, PRINTER_OPTION1, PRINTER_OPTION2, OUTPUT_TYPE, CREATED_ON
        //11=CREATED_BY, LAST_CHANGED_ON, LAST_CHANGED_BY
        String sql = "insert into J_PRINTER_DEFINITIONS values (?,?,?,?,?,  ?,?,?,?,?,  ?,?,?)";
        PreparedStatement pstmnt = connection.prepareStatement(sql);
 
        // Create first record
        pstmnt.setString(1, VAILD_PDF_PRINTER);
        pstmnt.setString(2, "Main Laser Printer");
        pstmnt.setString(3, "UTC");
        pstmnt.setString(4, "Copier Room");
        pstmnt.setBoolean(5, false);
        pstmnt.setString(6, REAL_PDF_PRINTER);
        pstmnt.setString(7, "-color");
        pstmnt.setString(8, null);
        pstmnt.setString(9, "PDF");
        pstmnt.setTimestamp(10, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(11, "user2");
        pstmnt.setTimestamp(12,(new DateTime()).timestamp());
        pstmnt.setString(13, "user3");
        pstmnt.execute();
        pstmnt.clearParameters();
        
        // Create second record
        pstmnt.setString(1, VAILD_LABEL_PRINTER);
        pstmnt.setString(2, "Intermec 3400D Label Printer");
        pstmnt.setString(3, "UTC");
        pstmnt.setString(4, "Main Hall");
        pstmnt.setBoolean(5, false);
        pstmnt.setString(6, "\\\\Hematite\\barcode1");
        pstmnt.setString(7, null);
        pstmnt.setString(8, null);
        pstmnt.setString(9, "Intermec 3400D");
        pstmnt.setTimestamp(10, DateTime.addMonth(new  DateTime(), -2).timestamp() );
        pstmnt.setString(11, "user2");
        pstmnt.setTimestamp(12, null);
        pstmnt.setString(13, null);
        pstmnt.execute();
        pstmnt.close();
    }
    
    
    protected void tearDownData(Connection connection)
    throws Exception {
        executeSql(connection, "delete from J_PRINTER_DEFINITIONS");
        executeSql(connection, "delete from J_FORM_USAGES");
        executeSql(connection, "delete from J_FORM_EVENTS");
        executeSql(connection, "delete from J_FORM_TEMPLATES");
        executeSql(connection, "delete from J_FORM_DEFINITIONS");
        executeSql(connection, "delete from J_FORM_GROUPS");
        executeSql(connection, "delete from J_OUTPUT_COMMANDS");
        executeSql(connection, "delete from J_OUTPUT_TYPES");
    }
    
}
