// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formselectionmaintenance.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.*;
import org.jaffa.components.finder.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.util.StringHelper;
import org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutDto;
import org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto;
import org.jaffa.modules.printing.domain.FormUsageMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import org.jaffa.modules.printing.domain.PrinterDefinitionMeta;

// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support FormSelectionMaintenance.
 */
public class FormSelectionMaintenanceForm extends FinderForm {

    private static Logger log = Logger.getLogger(FormSelectionMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:event_1_be
    /** Getter for property event.
     * @return Value of property event.
     */
    public String getEvent() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getEvent();
    }

    /** Setter for property event.
     * @param event New value of property event.
     */
    public void setEvent(String event) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setEvent(event);
    }

    /** Getter for property event. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property event.
     */
    public EditBoxModel getEventWM() {
        EditBoxModel eventModel = (EditBoxModel) getWidgetCache().getModel("event");
        if (eventModel == null) {
            if (getEvent() != null)
                eventModel = new EditBoxModel( getEvent() );
            else
                eventModel = new EditBoxModel();

            // .//GEN-END:event_1_be
            // Add custom code//GEN-FIRST:event_1


            // .//GEN-LAST:event_1
            // .//GEN-BEGIN:event_2_be
            getWidgetCache().addModel("event", eventModel);
        }
        return eventModel;
    }

    /** Setter for property event. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property event.
     */
    public void setEventWV(String value) {
        EditBoxController.updateModel(value, getEventWM());
    }

    /** Getter for DropDown property event.
     * @return Value of property eventDd.
     */
    public String getEventDd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getEventDd();
    }

    /** Setter for DropDown property event.
     * @param eventDd New value of property eventDd.
     */
    public void setEventDd(String eventDd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setEventDd(eventDd);
    }

    /** Getter for DropDown property event. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property eventDd.
     */
    public DropDownModel getEventDdWM() {
        DropDownModel eventDdModel = (DropDownModel) getWidgetCache().getModel("eventDd");
        if (eventDdModel == null) {
            if (getEventDd() != null)
                eventDdModel = new DropDownModel( getEventDd() );
            else
                eventDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                eventDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("eventDd", eventDdModel);
        }
        return eventDdModel;
    }

    /** Setter for DropDown property event. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property eventDd.
     */
    public void setEventDdWV(String value) {
        DropDownController.updateModel(value, getEventDdWM());
    }

    // .//GEN-END:event_2_be
    // .//GEN-BEGIN:key1_1_be
    /** Getter for property key1.
     * @return Value of property key1.
     */
    public String getKey1() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey1();
    }

    /** Setter for property key1.
     * @param key1 New value of property key1.
     */
    public void setKey1(String key1) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey1(key1);
    }

    /** Getter for property key1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key1.
     */
    public EditBoxModel getKey1WM() {
        EditBoxModel key1Model = (EditBoxModel) getWidgetCache().getModel("key1");
        if (key1Model == null) {
            if (getKey1() != null)
                key1Model = new EditBoxModel( getKey1() );
            else
                key1Model = new EditBoxModel();

            // .//GEN-END:key1_1_be
            // Add custom code//GEN-FIRST:key1_1


            // .//GEN-LAST:key1_1
            // .//GEN-BEGIN:key1_2_be
            getWidgetCache().addModel("key1", key1Model);
        }
        return key1Model;
    }

    /** Setter for property key1. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key1.
     */
    public void setKey1WV(String value) {
        EditBoxController.updateModel(value, getKey1WM());
    }

    /** Getter for DropDown property key1.
     * @return Value of property key1Dd.
     */
    public String getKey1Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey1Dd();
    }

    /** Setter for DropDown property key1.
     * @param key1Dd New value of property key1Dd.
     */
    public void setKey1Dd(String key1Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey1Dd(key1Dd);
    }

    /** Getter for DropDown property key1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key1Dd.
     */
    public DropDownModel getKey1DdWM() {
        DropDownModel key1DdModel = (DropDownModel) getWidgetCache().getModel("key1Dd");
        if (key1DdModel == null) {
            if (getKey1Dd() != null)
                key1DdModel = new DropDownModel( getKey1Dd() );
            else
                key1DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                key1DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("key1Dd", key1DdModel);
        }
        return key1DdModel;
    }

    /** Setter for DropDown property key1. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key1Dd.
     */
    public void setKey1DdWV(String value) {
        DropDownController.updateModel(value, getKey1DdWM());
    }

    // .//GEN-END:key1_2_be
    // .//GEN-BEGIN:key2_1_be
    /** Getter for property key2.
     * @return Value of property key2.
     */
    public String getKey2() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey2();
    }

    /** Setter for property key2.
     * @param key2 New value of property key2.
     */
    public void setKey2(String key2) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey2(key2);
    }

    /** Getter for property key2. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key2.
     */
    public EditBoxModel getKey2WM() {
        EditBoxModel key2Model = (EditBoxModel) getWidgetCache().getModel("key2");
        if (key2Model == null) {
            if (getKey2() != null)
                key2Model = new EditBoxModel( getKey2() );
            else
                key2Model = new EditBoxModel();

            // .//GEN-END:key2_1_be
            // Add custom code//GEN-FIRST:key2_1


            // .//GEN-LAST:key2_1
            // .//GEN-BEGIN:key2_2_be
            getWidgetCache().addModel("key2", key2Model);
        }
        return key2Model;
    }

    /** Setter for property key2. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key2.
     */
    public void setKey2WV(String value) {
        EditBoxController.updateModel(value, getKey2WM());
    }

    /** Getter for DropDown property key2.
     * @return Value of property key2Dd.
     */
    public String getKey2Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey2Dd();
    }

    /** Setter for DropDown property key2.
     * @param key2Dd New value of property key2Dd.
     */
    public void setKey2Dd(String key2Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey2Dd(key2Dd);
    }

    /** Getter for DropDown property key2. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key2Dd.
     */
    public DropDownModel getKey2DdWM() {
        DropDownModel key2DdModel = (DropDownModel) getWidgetCache().getModel("key2Dd");
        if (key2DdModel == null) {
            if (getKey2Dd() != null)
                key2DdModel = new DropDownModel( getKey2Dd() );
            else
                key2DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                key2DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("key2Dd", key2DdModel);
        }
        return key2DdModel;
    }

    /** Setter for DropDown property key2. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key2Dd.
     */
    public void setKey2DdWV(String value) {
        DropDownController.updateModel(value, getKey2DdWM());
    }

    // .//GEN-END:key2_2_be
    // .//GEN-BEGIN:key3_1_be
    /** Getter for property key3.
     * @return Value of property key3.
     */
    public String getKey3() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey3();
    }

    /** Setter for property key3.
     * @param key3 New value of property key3.
     */
    public void setKey3(String key3) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey3(key3);
    }

    /** Getter for property key3. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key3.
     */
    public EditBoxModel getKey3WM() {
        EditBoxModel key3Model = (EditBoxModel) getWidgetCache().getModel("key3");
        if (key3Model == null) {
            if (getKey3() != null)
                key3Model = new EditBoxModel( getKey3() );
            else
                key3Model = new EditBoxModel();

            // .//GEN-END:key3_1_be
            // Add custom code//GEN-FIRST:key3_1


            // .//GEN-LAST:key3_1
            // .//GEN-BEGIN:key3_2_be
            getWidgetCache().addModel("key3", key3Model);
        }
        return key3Model;
    }

    /** Setter for property key3. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key3.
     */
    public void setKey3WV(String value) {
        EditBoxController.updateModel(value, getKey3WM());
    }

    /** Getter for DropDown property key3.
     * @return Value of property key3Dd.
     */
    public String getKey3Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey3Dd();
    }

    /** Setter for DropDown property key3.
     * @param key3Dd New value of property key3Dd.
     */
    public void setKey3Dd(String key3Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey3Dd(key3Dd);
    }

    /** Getter for DropDown property key3. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key3Dd.
     */
    public DropDownModel getKey3DdWM() {
        DropDownModel key3DdModel = (DropDownModel) getWidgetCache().getModel("key3Dd");
        if (key3DdModel == null) {
            if (getKey3Dd() != null)
                key3DdModel = new DropDownModel( getKey3Dd() );
            else
                key3DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                key3DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("key3Dd", key3DdModel);
        }
        return key3DdModel;
    }

    /** Setter for DropDown property key3. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key3Dd.
     */
    public void setKey3DdWV(String value) {
        DropDownController.updateModel(value, getKey3DdWM());
    }

    // .//GEN-END:key3_2_be

        // .//GEN-BEGIN:key4_1_be
    /** Getter for property key4.
     * @return Value of property key4.
     */
    public String getKey4() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey4();
    }

    /** Setter for property key4.
     * @param key4 New value of property key4.
     */
    public void setKey4(String key4) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey4(key4);
    }

    /** Getter for property key4. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key4.
     */
    public EditBoxModel getKey4WM() {
        EditBoxModel key4Model = (EditBoxModel) getWidgetCache().getModel("key4");
        if (key4Model == null) {
            if (getKey4() != null)
                key4Model = new EditBoxModel( getKey4() );
            else
                key4Model = new EditBoxModel();

            // .//GEN-END:key4_1_be
            // Add custom code//GEN-FIRST:key4_1


            // .//GEN-LAST:key4_1
            // .//GEN-BEGIN:key4_2_be
            getWidgetCache().addModel("key4", key4Model);
        }
        return key4Model;
    }

    /** Setter for property key4. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key4.
     */
    public void setKey4WV(String value) {
        EditBoxController.updateModel(value, getKey4WM());
    }

    /** Getter for DropDown property key4.
     * @return Value of property key4Dd.
     */
    public String getKey4Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey4Dd();
    }

    /** Setter for DropDown property key4.
     * @param key4Dd New value of property key4Dd.
     */
    public void setKey4Dd(String key4Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey4Dd(key4Dd);
    }

    /** Getter for DropDown property key4. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key4Dd.
     */
    public DropDownModel getKey4DdWM() {
        DropDownModel key4DdModel = (DropDownModel) getWidgetCache().getModel("key4Dd");
        if (key4DdModel == null) {
            if (getKey4Dd() != null)
                key4DdModel = new DropDownModel( getKey4Dd() );
            else
                key4DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                key4DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("key4Dd", key4DdModel);
        }
        return key4DdModel;
    }

    /** Setter for DropDown property key4. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key4Dd.
     */
    public void setKey4DdWV(String value) {
        DropDownController.updateModel(value, getKey4DdWM());
    }

    // .//GEN-END:key4_2_be
    // .//GEN-BEGIN:key5_1_be
    /** Getter for property key5.
     * @return Value of property key5.
     */
    public String getKey5() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey5();
    }

    /** Setter for property key5.
     * @param key5 New value of property key5.
     */
    public void setKey5(String key5) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey5(key5);
    }

    /** Getter for property key5. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key5.
     */
    public EditBoxModel getKey5WM() {
        EditBoxModel key5Model = (EditBoxModel) getWidgetCache().getModel("key5");
        if (key5Model == null) {
            if (getKey5() != null)
                key5Model = new EditBoxModel( getKey5() );
            else
                key5Model = new EditBoxModel();

            // .//GEN-END:key5_1_be
            // Add custom code//GEN-FIRST:key5_1


            // .//GEN-LAST:key5_1
            // .//GEN-BEGIN:key5_2_be
            getWidgetCache().addModel("key5", key5Model);
        }
        return key5Model;
    }

    /** Setter for property key5. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key5.
     */
    public void setKey5WV(String value) {
        EditBoxController.updateModel(value, getKey5WM());
    }

    /** Getter for DropDown property key5.
     * @return Value of property key5Dd.
     */
    public String getKey5Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey5Dd();
    }

    /** Setter for DropDown property key5.
     * @param key5Dd New value of property key5Dd.
     */
    public void setKey5Dd(String key5Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey5Dd(key5Dd);
    }

    /** Getter for DropDown property key5. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key5Dd.
     */
    public DropDownModel getKey5DdWM() {
        DropDownModel key5DdModel = (DropDownModel) getWidgetCache().getModel("key5Dd");
        if (key5DdModel == null) {
            if (getKey5Dd() != null)
                key5DdModel = new DropDownModel( getKey5Dd() );
            else
                key5DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                key5DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("key5Dd", key5DdModel);
        }
        return key5DdModel;
    }

    /** Setter for DropDown property key5. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key5Dd.
     */
    public void setKey5DdWV(String value) {
        DropDownController.updateModel(value, getKey5DdWM());
    }

    // .//GEN-END:key5_2_be
    // .//GEN-BEGIN:key6_1_be
    /** Getter for property key6.
     * @return Value of property key6.
     */
    public String getKey6() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey6();
    }

    /** Setter for property key6.
     * @param key6 New value of property key6.
     */
    public void setKey6(String key6) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey6(key6);
    }

    /** Getter for property key6. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key6.
     */
    public EditBoxModel getKey6WM() {
        EditBoxModel key6Model = (EditBoxModel) getWidgetCache().getModel("key6");
        if (key6Model == null) {
            if (getKey6() != null)
                key6Model = new EditBoxModel( getKey6() );
            else
                key6Model = new EditBoxModel();

            // .//GEN-END:key6_1_be
            // Add custom code//GEN-FIRST:key6_1


            // .//GEN-LAST:key6_1
            // .//GEN-BEGIN:key6_2_be
            getWidgetCache().addModel("key6", key6Model);
        }
        return key6Model;
    }

    /** Setter for property key6. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key6.
     */
    public void setKey6WV(String value) {
        EditBoxController.updateModel(value, getKey6WM());
    }

    /** Getter for DropDown property key6.
     * @return Value of property key6Dd.
     */
    public String getKey6Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getKey6Dd();
    }

    /** Setter for DropDown property key6.
     * @param key6Dd New value of property key6Dd.
     */
    public void setKey6Dd(String key6Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setKey6Dd(key6Dd);
    }

    /** Getter for DropDown property key6. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property key6Dd.
     */
    public DropDownModel getKey6DdWM() {
        DropDownModel key6DdModel = (DropDownModel) getWidgetCache().getModel("key6Dd");
        if (key6DdModel == null) {
            if (getKey6Dd() != null)
                key6DdModel = new DropDownModel( getKey6Dd() );
            else
                key6DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                key6DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("key6Dd", key6DdModel);
        }
        return key6DdModel;
    }

    /** Setter for DropDown property key6. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property key6Dd.
     */
    public void setKey6DdWV(String value) {
        DropDownController.updateModel(value, getKey6DdWM());
    }

    // .//GEN-END:key6_2_be

    
    // .//GEN-BEGIN:value1_1_be
    /** Getter for property value1.
     * @return Value of property value1.
     */
    public String getValue1() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue1();
    }

    /** Setter for property value1.
     * @param value1 New value of property value1.
     */
    public void setValue1(String value1) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue1(value1);
    }

    /** Getter for property value1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value1.
     */
    public EditBoxModel getValue1WM() {
        EditBoxModel value1Model = (EditBoxModel) getWidgetCache().getModel("value1");
        if (value1Model == null) {
            if (getValue1() != null)
                value1Model = new EditBoxModel( getValue1() );
            else
                value1Model = new EditBoxModel();

            // .//GEN-END:value1_1_be
            // Add custom code//GEN-FIRST:value1_1


            // .//GEN-LAST:value1_1
            // .//GEN-BEGIN:value1_2_be
            getWidgetCache().addModel("value1", value1Model);
        }
        return value1Model;
    }

    /** Setter for property value1. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value1.
     */
    public void setValue1WV(String value) {
        EditBoxController.updateModel(value, getValue1WM());
    }

    /** Getter for DropDown property value1.
     * @return Value of property value1Dd.
     */
    public String getValue1Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue1Dd();
    }

    /** Setter for DropDown property value1.
     * @param value1Dd New value of property value1Dd.
     */
    public void setValue1Dd(String value1Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue1Dd(value1Dd);
    }

    /** Getter for DropDown property value1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value1Dd.
     */
    public DropDownModel getValue1DdWM() {
        DropDownModel value1DdModel = (DropDownModel) getWidgetCache().getModel("value1Dd");
        if (value1DdModel == null) {
            if (getValue1Dd() != null)
                value1DdModel = new DropDownModel( getValue1Dd() );
            else
                value1DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                value1DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("value1Dd", value1DdModel);
        }
        return value1DdModel;
    }

    /** Setter for DropDown property value1. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value1Dd.
     */
    public void setValue1DdWV(String value) {
        DropDownController.updateModel(value, getValue1DdWM());
    }

    // .//GEN-END:value1_2_be
    // .//GEN-BEGIN:value2_1_be
    /** Getter for property value2.
     * @return Value of property value2.
     */
    public String getValue2() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue2();
    }

    /** Setter for property value2.
     * @param value2 New value of property value2.
     */
    public void setValue2(String value2) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue2(value2);
    }

    /** Getter for property value2. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value2.
     */
    public EditBoxModel getValue2WM() {
        EditBoxModel value2Model = (EditBoxModel) getWidgetCache().getModel("value2");
        if (value2Model == null) {
            if (getValue2() != null)
                value2Model = new EditBoxModel( getValue2() );
            else
                value2Model = new EditBoxModel();

            // .//GEN-END:value2_1_be
            // Add custom code//GEN-FIRST:value2_1


            // .//GEN-LAST:value2_1
            // .//GEN-BEGIN:value2_2_be
            getWidgetCache().addModel("value2", value2Model);
        }
        return value2Model;
    }

    /** Setter for property value2. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value2.
     */
    public void setValue2WV(String value) {
        EditBoxController.updateModel(value, getValue2WM());
    }

    /** Getter for DropDown property value2.
     * @return Value of property value2Dd.
     */
    public String getValue2Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue2Dd();
    }

    /** Setter for DropDown property value2.
     * @param value2Dd New value of property value2Dd.
     */
    public void setValue2Dd(String value2Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue2Dd(value2Dd);
    }

    /** Getter for DropDown property value2. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value2Dd.
     */
    public DropDownModel getValue2DdWM() {
        DropDownModel value2DdModel = (DropDownModel) getWidgetCache().getModel("value2Dd");
        if (value2DdModel == null) {
            if (getValue2Dd() != null)
                value2DdModel = new DropDownModel( getValue2Dd() );
            else
                value2DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                value2DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("value2Dd", value2DdModel);
        }
        return value2DdModel;
    }

    /** Setter for DropDown property value2. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value2Dd.
     */
    public void setValue2DdWV(String value) {
        DropDownController.updateModel(value, getValue2DdWM());
    }

    // .//GEN-END:value2_2_be
    // .//GEN-BEGIN:value3_1_be
    /** Getter for property value3.
     * @return Value of property value3.
     */
    public String getValue3() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue3();
    }

    /** Setter for property value3.
     * @param value3 New value of property value3.
     */
    public void setValue3(String value3) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue3(value3);
    }

    /** Getter for property value3. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value3.
     */
    public EditBoxModel getValue3WM() {
        EditBoxModel value3Model = (EditBoxModel) getWidgetCache().getModel("value3");
        if (value3Model == null) {
            if (getValue3() != null)
                value3Model = new EditBoxModel( getValue3() );
            else
                value3Model = new EditBoxModel();

            // .//GEN-END:value3_1_be
            // Add custom code//GEN-FIRST:value3_1


            // .//GEN-LAST:value3_1
            // .//GEN-BEGIN:value3_2_be
            getWidgetCache().addModel("value3", value3Model);
        }
        return value3Model;
    }

    /** Setter for property value3. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value3.
     */
    public void setValue3WV(String value) {
        EditBoxController.updateModel(value, getValue3WM());
    }

    /** Getter for DropDown property value3.
     * @return Value of property value3Dd.
     */
    public String getValue3Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue3Dd();
    }

    /** Setter for DropDown property value3.
     * @param value3Dd New value of property value3Dd.
     */
    public void setValue3Dd(String value3Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue3Dd(value3Dd);
    }

    /** Getter for DropDown property value3. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value3Dd.
     */
    public DropDownModel getValue3DdWM() {
        DropDownModel value3DdModel = (DropDownModel) getWidgetCache().getModel("value3Dd");
        if (value3DdModel == null) {
            if (getValue3Dd() != null)
                value3DdModel = new DropDownModel( getValue3Dd() );
            else
                value3DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                value3DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("value3Dd", value3DdModel);
        }
        return value3DdModel;
    }

    /** Setter for DropDown property value3. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value3Dd.
     */
    public void setValue3DdWV(String value) {
        DropDownController.updateModel(value, getValue3DdWM());
    }

    // .//GEN-END:value3_2_be


        // .//GEN-BEGIN:value4_1_be
    /** Getter for property value4.
     * @return Value of property value4.
     */
    public String getValue4() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue4();
    }

    /** Setter for property value4.
     * @param value4 New value of property value4.
     */
    public void setValue4(String value4) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue4(value4);
    }

    /** Getter for property value4. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value4.
     */
    public EditBoxModel getValue4WM() {
        EditBoxModel value4Model = (EditBoxModel) getWidgetCache().getModel("value4");
        if (value4Model == null) {
            if (getValue4() != null)
                value4Model = new EditBoxModel( getValue4() );
            else
                value4Model = new EditBoxModel();

            // .//GEN-END:value4_1_be
            // Add custom code//GEN-FIRST:value4_1


            // .//GEN-LAST:value4_1
            // .//GEN-BEGIN:value4_2_be
            getWidgetCache().addModel("value4", value4Model);
        }
        return value4Model;
    }

    /** Setter for property value4. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value4.
     */
    public void setValue4WV(String value) {
        EditBoxController.updateModel(value, getValue4WM());
    }

    /** Getter for DropDown property value4.
     * @return Value of property value4Dd.
     */
    public String getValue4Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue4Dd();
    }

    /** Setter for DropDown property value4.
     * @param value4Dd New value of property value4Dd.
     */
    public void setValue4Dd(String value4Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue4Dd(value4Dd);
    }

    /** Getter for DropDown property value4. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value4Dd.
     */
    public DropDownModel getValue4DdWM() {
        DropDownModel value4DdModel = (DropDownModel) getWidgetCache().getModel("value4Dd");
        if (value4DdModel == null) {
            if (getValue4Dd() != null)
                value4DdModel = new DropDownModel( getValue4Dd() );
            else
                value4DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                value4DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("value4Dd", value4DdModel);
        }
        return value4DdModel;
    }

    /** Setter for DropDown property value4. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value4Dd.
     */
    public void setValue4DdWV(String value) {
        DropDownController.updateModel(value, getValue4DdWM());
    }

    // .//GEN-END:value4_2_be
    // .//GEN-BEGIN:value5_1_be
    /** Getter for property value5.
     * @return Value of property value5.
     */
    public String getValue5() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue5();
    }

    /** Setter for property value5.
     * @param value5 New value of property value5.
     */
    public void setValue5(String value5) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue5(value5);
    }

    /** Getter for property value5. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value5.
     */
    public EditBoxModel getValue5WM() {
        EditBoxModel value5Model = (EditBoxModel) getWidgetCache().getModel("value5");
        if (value5Model == null) {
            if (getValue5() != null)
                value5Model = new EditBoxModel( getValue5() );
            else
                value5Model = new EditBoxModel();

            // .//GEN-END:value5_1_be
            // Add custom code//GEN-FIRST:value5_1


            // .//GEN-LAST:value5_1
            // .//GEN-BEGIN:value5_2_be
            getWidgetCache().addModel("value5", value5Model);
        }
        return value5Model;
    }

    /** Setter for property value5. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value5.
     */
    public void setValue5WV(String value) {
        EditBoxController.updateModel(value, getValue5WM());
    }

    /** Getter for DropDown property value5.
     * @return Value of property value5Dd.
     */
    public String getValue5Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue5Dd();
    }

    /** Setter for DropDown property value5.
     * @param value5Dd New value of property value5Dd.
     */
    public void setValue5Dd(String value5Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue5Dd(value5Dd);
    }

    /** Getter for DropDown property value5. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value5Dd.
     */
    public DropDownModel getValue5DdWM() {
        DropDownModel value5DdModel = (DropDownModel) getWidgetCache().getModel("value5Dd");
        if (value5DdModel == null) {
            if (getValue5Dd() != null)
                value5DdModel = new DropDownModel( getValue5Dd() );
            else
                value5DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                value5DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("value5Dd", value5DdModel);
        }
        return value5DdModel;
    }

    /** Setter for DropDown property value5. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value5Dd.
     */
    public void setValue5DdWV(String value) {
        DropDownController.updateModel(value, getValue5DdWM());
    }

    // .//GEN-END:value5_2_be
    // .//GEN-BEGIN:value6_1_be
    /** Getter for property value6.
     * @return Value of property value6.
     */
    public String getValue6() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue6();
    }

    /** Setter for property value6.
     * @param value6 New value of property value6.
     */
    public void setValue6(String value6) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue6(value6);
    }

    /** Getter for property value6. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value6.
     */
    public EditBoxModel getValue6WM() {
        EditBoxModel value6Model = (EditBoxModel) getWidgetCache().getModel("value6");
        if (value6Model == null) {
            if (getValue6() != null)
                value6Model = new EditBoxModel( getValue6() );
            else
                value6Model = new EditBoxModel();

            // .//GEN-END:value6_1_be
            // Add custom code//GEN-FIRST:value6_1


            // .//GEN-LAST:value6_1
            // .//GEN-BEGIN:value6_2_be
            getWidgetCache().addModel("value6", value6Model);
        }
        return value6Model;
    }

    /** Setter for property value6. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value6.
     */
    public void setValue6WV(String value) {
        EditBoxController.updateModel(value, getValue6WM());
    }

    /** Getter for DropDown property value6.
     * @return Value of property value6Dd.
     */
    public String getValue6Dd() {
        return ( (FormSelectionMaintenanceComponent) getComponent() ).getValue6Dd();
    }

    /** Setter for DropDown property value6.
     * @param value6Dd New value of property value6Dd.
     */
    public void setValue6Dd(String value6Dd) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setValue6Dd(value6Dd);
    }

    /** Getter for DropDown property value6. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property value6Dd.
     */
    public DropDownModel getValue6DdWM() {
        DropDownModel value6DdModel = (DropDownModel) getWidgetCache().getModel("value6Dd");
        if (value6DdModel == null) {
            if (getValue6Dd() != null)
                value6DdModel = new DropDownModel( getValue6Dd() );
            else
                value6DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                value6DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("value6Dd", value6DdModel);
        }
        return value6DdModel;
    }

    /** Setter for DropDown property value6. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property value6Dd.
     */
    public void setValue6DdWV(String value) {
        DropDownController.updateModel(value, getValue6DdWM());
    }

    // .//GEN-END:value6_2_be



    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        String value = null;
        StringBuffer buf = null;

        value = getEventWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setEvent(value);
        setEventDd(getEventDdWM().getValue());

        value = getKey1WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setKey1(value);
        setKey1Dd(getKey1DdWM().getValue());

        value = getKey2WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setKey2(value);
        setKey2Dd(getKey2DdWM().getValue());

        value = getKey3WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setKey3(value);
        setKey3Dd(getKey3DdWM().getValue());

        value = getKey4WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setKey4(value);
        setKey4Dd(getKey4DdWM().getValue());

        value = getKey5WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setKey5(value);
        setKey5Dd(getKey5DdWM().getValue());

        value = getKey6WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setKey6(value);
        setKey6Dd(getKey6DdWM().getValue());

        value = getValue1WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setValue1(value);
        setValue1Dd(getValue1DdWM().getValue());

        value = getValue2WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setValue2(value);
        setValue2Dd(getValue2DdWM().getValue());

        value = getValue3WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setValue3(value);
        setValue3Dd(getValue3DdWM().getValue());

        value = getValue4WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setValue4(value);
        setValue4Dd(getValue4DdWM().getValue());

        value = getValue5WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setValue5(value);
        setValue5Dd(getValue5DdWM().getValue());

        value = getValue6WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setValue6(value);
        setValue6Dd(getValue6DdWM().getValue());
        
        // .//GEN-END:_doValidate_1_be
        // Add custom code//GEN-FIRST:_doValidate_1

        // .//GEN-LAST:_doValidate_1
        // .//GEN-BEGIN:_doValidate_2_be
        return valid;
    }
    // .//GEN-END:_doValidate_2_be
    // .//GEN-BEGIN:_populateRows_1_be
    /** This will populate the input GridModel with the data in the finderOutDto of the Component.
     * @param rows The GridModel object to populate.
     */
    public void populateRows(GridModel rows) {
        rows.clearRows();
        FormSelectionMaintenanceOutDto outputDto = (FormSelectionMaintenanceOutDto) ((FormSelectionMaintenanceComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                FormSelectionMaintenanceOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement("select", new CheckBoxModel( (rowDto.getSelect() != null ? rowDto.getSelect() : Boolean.FALSE) ));
                row.addElement("errMessage", rowDto.getErrMessage());
                row.addElement("formName", rowDto.getFormName());
                row.addElement("event", rowDto.getEvent());
                row.addElement("key1", rowDto.getKey1());
                row.addElement("key2", rowDto.getKey2());
                row.addElement("key3", rowDto.getKey3());
                row.addElement("key4", rowDto.getKey4());
                row.addElement("key5", rowDto.getKey5());
                row.addElement("key6", rowDto.getKey6());
                row.addElement("value1", rowDto.getValue1());
                row.addElement("value2", rowDto.getValue2());
                row.addElement("value3", rowDto.getValue3());
                row.addElement("value4", rowDto.getValue4());
                row.addElement("value5", rowDto.getValue5());
                row.addElement("value6", rowDto.getValue6());
                row.addElement("printer", rowDto.getPrinter());
                row.addElement("email", rowDto.getEmail());
                row.addElement("publish", new CheckBoxModel( (rowDto.getPublish() != null ? rowDto.getPublish() : Boolean.FALSE) ));
                row.addElement("copies", rowDto.getCopies());
                // .//GEN-END:_populateRows_1_be
                // Add custom code for the row//GEN-FIRST:_populateRows_1
                EditBoxModel printerModel = new EditBoxModel(rowDto.getPrinter(), PrinterDefinitionMeta.META_PRINTER_ID);
                printerModel.setMandatory(false);
                row.addElement("printer", printerModel);
                row.addElement("email", new EditBoxModel(rowDto.getEmail()));
                row.addElement("copies", new EditBoxModel(rowDto.getCopies()));
                row.addElement("formType", rowDto.getFormType());
                row.addElement("object", rowDto);
                row.addElement("additionalDataComponent", rowDto.getAdditionalDataComponent() );

                // .//GEN-LAST:_populateRows_1
                // .//GEN-BEGIN:_populateRows_2_be
            }
        }
    }
    // .//GEN-END:_populateRows_2_be
    // All the custom code goes here//GEN-FIRST:_custom

    /** Getter for property autoDisplayAll.
     * @return Value of property autoDisplayAll.
     */
    public boolean getAutoDisplayAll() {
        return ((FormSelectionMaintenanceComponent) getComponent()).getAutoDisplayAll();
    }

    /** Setter for property autoDisplayAll.
     * @param autoDisplayAll New value of property autoDisplayAll.
     */
    public void setAutoDisplayAll(boolean autoDisplayAll) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setAutoDisplayAll(autoDisplayAll);
    }

    /** Getter for property directDisplay.
     * @return Value of property directDisplay.
     */
    public boolean getDirectDisplay() {
        return ((FormSelectionMaintenanceComponent) getComponent()).getDirectDisplay();
    }

    /** Setter for property directDisplay.
     * @param directDisplay New value of property directDisplay.
     */
    public void setDirectDisplay(boolean directDisplay) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setDirectDisplay(directDisplay);
    }

    /** Getter for property printing.
     * @return Value of property printing.
     */
    public boolean getPrinting() {
        return ((FormSelectionMaintenanceComponent) getComponent()).getPrinting();
    }

    /** Setter for property printing.
     * @param printing New value of property printing.
     */
    public void setPrinting(boolean printing) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setPrinting(printing);
    }    

    /** Getter for property email.
     * @return Value of property email.
     */
    public boolean getEmail() {
        return ((FormSelectionMaintenanceComponent) getComponent()).getEmail();
    }

    /** Setter for property email.
     * @param email New value of property email.
     */
    public void setEmail(boolean email) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setEmail(email);
    }     

    /** Getter for property webPublish.
     * @return Value of property webPublish.
     */
    public boolean getWebPublish() {
        return ((FormSelectionMaintenanceComponent) getComponent()).getWebPublish();
    }

    /** Setter for property webPublish.
     * @param webPublish New value of property webPublish.
     */
    public void setWebPublish(boolean webPublish) {
        ( (FormSelectionMaintenanceComponent) getComponent() ).setWebPublish(webPublish);
    } 
    
    public static final String NAME = "jaffa_printing_formSelectionMaintenanceDisplay";
    // .//GEN-LAST:_custom
}
