<%--
labels.jsp

Generates a javascript 'Labels' object that has a get(token) method for getting a label
It is assumed that all labels for this referencing page are already known and stored
in a text file resource.
If a label is not know it is automatically added to the serverside list, so next time
this JSP is accessed for that page, the label will be available.

@param ref Name of the page to get the labels for
@param token Request for a token by a given page which should be cached for later use

Should be included in the main JSP that loads all the java script using the following...

<script type="text/javascript" src="js/extjs/jaffa/labels.jsp?ref=<%=request.getRequestURI().substring(request.getContextPath().length()+1)%>"></script>

--%><%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.net.URL,java.util.*,javax.servlet.http.*" %>
<%@ page import = "org.jaffa.util.URLHelper,org.jaffa.util.MessageHelper,org.jaffa.security.SecurityTag,org.jaffa.util.StringHelper" %>
<%@ page import = "org.apache.log4j.Logger" %>
<%@ page import="org.jaffa.security.filter.FileFilter" %>
<%!
    private static final Logger log = Logger.getLogger("js.extjs.jaffa.labels");

    private static final String EDIT_PRE1 = "<a target='_blank' href='";
    private static final String EDIT_PRE2 = "/startComponent.do?component=Jaffa.Admin.LabelEditor&finalUrl=jaffa_closeBrowser&labelFilter=";
    private static final String EDIT_PRE3 = "'></a>";

    // Get the message and replace special characters
    String getMsg(String token) {
        return MessageHelper.findMessage(token, null)!=null ? MessageHelper.findMessage(token, null).replace("\"","'").replace("\n", "\\n").replace("\\\n", "\\n") : "{"+token+"}";
    }

    // Get the full path of where the labels are for this refering page
    String getLabelFileName(String ref, HttpServletRequest request) {
        //String root = (String) Config.getProperty(Config.PROP_DEFAULT_GRID_SETTINGS_URI, DEFAULT_SETTINGS_LOCATION);
        String root = request.getSession().getServletContext().getRealPath("/");
        //String s = root+"/labels/"+ref+".labels";
        String s = root+ref.replace("/", File.separator)+".labels";
        return s;
    }

    // Read all the tokens in from the file
    TreeSet<String> readLabelTokens(String page, HttpServletRequest request) {
        TreeSet<String> out = new TreeSet<String>();
        BufferedReader br = null;
        try {
            File f = new File(getLabelFileName(page, request));
            if(f.exists()) {
                br = new BufferedReader(new FileReader(f));
                String line=null;
                int count=0;
                while ( (line = br.readLine()) != null) {
                    out.add(line);
                }
            }
        } catch (IOException ex) {
            log.error("Failed read token file " + request.getRequestURI(),ex);
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                log.error("Failed read token file " + request.getRequestURI(),ex);
            }
        }
        return out;
    }

    // Write a new token to the end of the file
    synchronized void addLabelToken(String page, HttpServletRequest request, String token) {
        BufferedWriter w = null;
        try {
            TreeSet<String> t = readLabelTokens(page,request);
            t.add(token);
            File f = new File(getLabelFileName(page, request));
            if(!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            // Write out the script
            w = new BufferedWriter( new FileWriter(f, false) );
            for (String tk : t) {
                w.write( tk );
                w.newLine();
            }
            log.debug("Saved Token " + token);
        } catch (IOException ex) {
            log.error("Failed to add token " + token + " to " + request.getRequestURI(),ex);
        } finally {
            try {
                if (w != null) w.close();
            } catch (IOException ex) {
              log.error("Failed to add token " + token + " to " + request.getRequestURI(),ex);
            }
        }
    }
%>
<%
boolean editor = SecurityTag.hasComponentAccess(request, "Jaffa.Admin.LabelEditor");
String ref = request.getParameter("ref");
ref = FileFilter.filterUserInputPath(ref);
String token = request.getParameter("token");
if(ref!=null&&token==null) {
    // Read the tokens for this page and create and populate the Labels object
    TreeSet<String> t = readLabelTokens(ref, request);
%>Labels = {
    url: '<%=request.getContextPath()+"/js/extjs/jaffa/metadata/labels.jsp"%>',
    page: '<%=StringHelper.escapeHtml(ref)%>',
    editor: <%= editor %>,
    data : new Ext.util.MixedCollection(),
    conn: new Ext.data.Connection({
      url: '<%=request.getContextPath()+"/js/extjs/jaffa/metadata/labels.jsp"%>'
    }),
    regExps: [/\{0\}/g, /\{1\}/g, /\{2\}/g, /\{3\}/g],
    get: function(x, vs) {
      if(!Labels.data.containsKey(x)) {
        // make a request for x
        var opt = {
          params: {
            ref: this.page,
            token: x,
            tokenClass: 'labelToken'
          }
        };
        this.conn.request(opt);
        // store into the token array
        Labels.data.add(x,'?'+x+'?');
        console.debug('Written referenced for new token ',x,' for ',Labels.page);
      }
      var oo = Labels.data.get(x);
      if (vs && typeof vs != 'object') vs = [vs];
      if (vs && vs.length>0 && oo && oo!='') {
        for (var i=0; i<vs.length; i++) {
          if (! this.regExps[i]) {
            // eval('var kk = /\\\{'+t+'\\\}/g');
            eval('this.regExps['+i+']=/\\\{'+i+'\\\}/g;');
          }
          oo = oo.replace(this.regExps[i], vs[i]);
        }
      }
      return oo;
    },
    /** Return label as link to edit the labels */
    getLinkUrl: function(x) {
        if(this.editor) {
          return "<%=request.getContextPath()%>/startComponent.do?component=Jaffa.Admin.LabelEditor&finalUrl=jaffa_closeBrowser&labelFilter="+x;
        } else {
          return "";
        }
    },
    getWithLink: function(x) {
        var t = this.get(x);
        var l = this.getLinkUrl(x);
        if(l=="")
            return t;
        else
            return '<span>'+t+'</span><a class=edit-label target=_blank href=\''+l+'\'>&nbsp;</a>';
    }
};
<%
    // add in a list of architecture labels that are used by every page
t.add("label.Jaffa.Widgets.UserGrid.NoRecordsKey");
t.add("label.jaffa.jaffaRIA.component.PanelController.saveErrorTitleText");
t.add("label.jaffa.jaffaRIA.component.PanelController.saveFieldErrorMsgTpl");
t.add("label.jaffa.jaffaRIA.component.PanelController.saveErrorMsgText");
t.add("label.jaffa.jaffaRIA.component.PanelController.validateErrorTitleText");
t.add("label.jaffa.jaffaRIA.component.PanelController.validateErrorMsgTpl");
t.add("label.jaffa.jaffaRIA.component.PanelController.mandatoryColErrorMsgTpl");
t.add("label.jaffa.jaffaRIA.component.PanelController.invalidMandatoryText");
t.add("label.jaffa.jaffaRIA.component.PanelController.invalidMinLenText");
t.add("label.jaffa.jaffaRIA.component.PanelController.invalidMaxLenText");
t.add("label.jaffa.jaffaRIA.component.PanelController.invalidComboValueText");
t.add("label.jaffa.jaffaRIA.component.PanelController.invalidDateValueText");
t.add("label.jaffa.jaffaRIA.component.PanelController.gridEditorValidationError");
t.add("label.jaffa.jaffaRIA.component.PanelController.keyCheckAlertMsg");
t.add("label.Jaffa.Finder.Button.loadQuery");
t.add("label.Jaffa.Widgets.Button.Save");
t.add("label.Jaffa.Widgets.Button.Delete");
t.add("label.Jaffa.Widgets.Button.Clear");
t.add("label.Jaffa.Widgets.Button.Search");
t.add("title.Jaffa.Finder.saveQuery");
t.add("label.Jaffa.Finder.SaveQuery.prompt");
t.add("label.Jaffa.Finder.SaveQuery.confirm");
t.add("label.Jaffa.Finder.SaveQuery.confirmDefault");
t.add("label.Jaffa.Widgets.Button.Cancel");
t.add("title.Jaffa.Finder.deleteQuery");
t.add("label.Jaffa.Finder.DeleteQuery.prompt");
t.add("label.Jaffa.Finder.errorTitleText");
t.add("label.Jaffa.Finder.saveErrorText");
t.add("label.Jaffa.Finder.deleteErrorText");
t.add("label.jaffa.jaffaRIA.finder.CriteriaPanel.titleBase");
t.add("label.jaffa.jaffaRIA.finder.CriteriaPanel.validationError");
t.add("label.jaffa.jaffaRIA.finder.CriteriaPanel.invalidSearchCriteria");
t.add("label.jaffa.jaffaRIA.finder.CriteriaPanel.alertMsgQuery");
t.add("label.jaffa.jaffaRIA.finder.CriteriaPanel.alertMsgNotInTheList");
t.add("label.Jaffa.Inquiry.maxRecords");
t.add("label.common.FlexFields");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.Equals");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.NotEquals");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.GreaterThan");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.SmallerThan");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.GreaterThanEqualTo");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.SmallerThanEqualTo");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.IsNotNull");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.IsNull");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.Between");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.In");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.BeginsWith");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.EndsWith");
t.add("label.Jaffa.Inquiry.CriteriaDropDownOption.Like");
t.add("label.Jaffa.Inquiry.BooleanCriteriaDropDownOption.Any");
t.add("label.Jaffa.Inquiry.BooleanCriteriaDropDownOption.True");
t.add("label.Jaffa.Inquiry.BooleanCriteriaDropDownOption.False");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.closeScreenTitleText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.closeScreenText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.closeTipText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.refreshTipText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.optionsTipText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.startAutoRefreshText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.stopAutoRefreshText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.removePersonalizationsText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.personalizationsRemovedReloadText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.personalizationsRemovedTitle");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.personalizationsRemovedMsg");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.emptySearchText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.logoutTitleText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.logoutText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.rootMenuText");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.userId");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.titleNewRecord");
t.add("label.jaffaRIA.MessageBox.alertPromtCloseWindowMsg");
t.add("label.jaffaRIA.TextBox.Search");
t.add("label.jaffaRIA.Button.Logout");
t.add("label.jaffaRIA.Button.Settings");
t.add("label.jaffa.jaffaRIA.finder.FinderHeader.autoRefreshDelaySeconds");
t.add("label.jaffaRIA.DropDownMenu.AllPrograms");
t.add("label.jaffa.jaffaRIA.attachment.Accordion.buttonErrorTitle");
t.add("label.jaffa.jaffaRIA.attachment.Accordion.deleteButtonSelectAtLeastOneRecordText");
t.add("label.jaffa.jaffaRIA.attachment.Accordion.Description");
t.add("label.jaffa.jaffaRIA.attachment.Accordion.Remarks");
t.add("label.JaffaRIA.Maintenance.AttachmentsSection.OpenTip");
t.add("label.jaffa.jaffaRIA.attachment.Container.buttonErrorTitle");
t.add("label.jaffa.jaffaRIA.attachment.Container.deleteButtonSelectAtLeastOneRecordText");
t.add("label.JaffaRIA.Button.Add");
t.add("label.JaffaRIA.Button.Upload");
t.add("label.JaffaRIA.Button.Delete");
t.add("label.JaffaRIA.Button.Modify");
t.add("label.JaffaRIA.Maintenance.DeleteSelected");
t.add("label.jaffa.jaffaRIA.attachment.UploadDialog.contentType_col_title");
t.add("label.jaffa.jaffaRIA.attachment.UploadDialog.description_col_title");
t.add("label.jaffa.jaffaRIA.attachment.UploadDialog.remarks_col_title");
t.add("label.jaffa.jaffaRIA.component.CopyController.cloneErrorTitleText");
t.add("label.jaffa.jaffaRIA.component.CopyController.cloneFieldErrorMsgTpl");
t.add("label.jaffa.jaffaRIA.component.CopyController.cloneErrorMsgText");
t.add("label.jaffa.jaffaRIA.component.CopyController.objectLabel");
t.add("label.jaffa.jaffaRIA.component.CopyController.cloningMaskText");
t.add("title.jaffaRIA.MessageBox.CloneFailed");
t.add("label.jaffa.jaffaRIA.component.CRUDController.objectLabel");
t.add("label.jaffa.jaffaRIA.component.CRUDController.loadMaskText");
t.add("label.jaffa.jaffaRIA.component.CRUDController.saveErrorTitle");
t.add("label.jaffa.jaffaRIA.component.CRUDController.defaultLocalizedErrorMessage");
t.add("label.jaffa.jaffaRIA.component.CRUDController.noChangeToSaveText");
t.add("label.jaffa.jaffaRIA.component.CRUDController.noChangeToSaveTitle");
t.add("label.jaffa.jaffaRIA.component.CRUDController.savingMaskText");
t.add("label.jaffaRIA.MessageBox.PrevalidationFailedMsg");
t.add("title.JaffaRIA.MessageBox.LoadError");
t.add("label.jaffaRIA.MessageBox.loadFailureMsg");
t.add("label.jaffaRIA.MessageBox.alertValidationFailed");
t.add("label.jaffa.jaffaRIA.component.LoadController.objectLabel");
t.add("label.jaffa.jaffaRIA.component.LoadController.loadMaskText");
t.add("label.jaffa.jaffaRIA.component.LoadController.defaultLocalizedErrorMessage");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.updateLabel");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.viewLabel");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.createLabel");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.inquiryLabel");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.criteriaLabel");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.resultsLabel");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.fastAccessLabel");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.saveChangesTitle");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.operationConfirmLabel");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.createNewRecLabel");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.loseChangesMsg");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.deleteTitle");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.deleteSuccessMsg");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.errorTitle");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.warningTitle");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.saveLabel");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.cancelLabel");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.selectAtLeastOneRecMsg");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.selectARecMsg");
t.add("label.jaffa.jaffaRIA.finder.FinderViewport.defaultLocalizedErrorMessage");
t.add("label.JaffaRIA.Button.Add");
t.add("label.JaffaRIA.Button.Delete");
t.add("label.jaffa.jaffaRIA.form.BooleanCombo.anyText");
t.add("label.jaffa.jaffaRIA.form.BooleanCombo.trueText");
t.add("label.jaffa.jaffaRIA.form.BooleanCombo.falseText");
t.add("label.jaffa.jaffaRIA.form.FinderComboBoxPlus.invalidateNotFoundText");
t.add("label.jaffa.jaffaRIA.form.FinderComboBoxPlus.invalidateMultipleRecordsFoundText");
t.add("label.jaffa.jaffaRIA.form.HourMinuteField.invalidText");
t.add("label.jaffa.jaffaRIA.form.HourMinuteField.decimalFormatInvalidText");
t.add("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.waitingText");
t.add("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.loadingText");
t.add("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.duplicateKeyText");
t.add("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.buttonErrorTitle");
t.add("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.removeButtonSelectAtLeastOneRecordText");
t.add("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.isRequired");
t.add("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.TheValueIn");
t.add("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.isInvalid");
t.add("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.TheMaximumLengthFor");
t.add("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.TheMinimumLengthFor");
t.add("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.isZero");
t.add("label.JaffaRIA.Button.Add");
t.add("label.JaffaRIA.Button.Delete");
t.add("title.JaffaRIA.Maintenance.DeleteSelected");
t.add("label.jaffa.jaffaRIA.tree.DwrTreeLoader.loadingMaskText");
t.add("label.jaffa.jaffaRIA.tree.DwrColumnTreeLoader.loadingMaskText");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.cancelText");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.updateText");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.errorText");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.noSelectionErrorText");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.validationErrorText");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.saveErrorsTitleText");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.notSelectedForUpdatedTitleText");
t.add("label.common.FlexFields");
t.add("label.jaffaRIA.Button.Ok");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.errorsTitleText");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.UpdateMultipleRecords");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.modifyButtonText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.buttonErrorTitle");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.modifyButtonSelectOneRecordText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.viewButtonText");
t.add("label.JaffaRIA.Button.Add");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.removeButtonText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.removeButtonSelectAtLeastOneRecordText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.okButtonText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.cancelButtonText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.detailButtonText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.listButtonText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.fixText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.discardText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.confirmText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.warningText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.addRecordText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.modifyRecordText");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.copyErrorTitle");
t.add("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.copySelectOneRecordText");
t.add("label.jaffa.jaffaRIA.maintenance.HeaderPanel.dataNotFoundTitle");
t.add("label.jaffa.jaffaRIA.maintenance.HeaderPanel.dataNotFoundMessage");
t.add("label.jaffa.jaffaRIA.maintenance.PendingEventsWindow.applyToAllText");
t.add("label.jaffa.jaffaRIA.maintenance.PendingEventsWindow.finishButtonText");
t.add("label.jaffa.jaffaRIA.maintenance.PendingEventsWindow.cancelButtonText");
t.add("label.JaffaRIA.Button.Previous");
t.add("label.JaffaRIA.Button.Next");
t.add("label.JaffaRIA.Button.Continue");
t.add("title.jaffa.jaffaRIA.maintenance.PendingEventsWindow.WarningsPendingEvents");
t.add("label.jaffa.jaffaRIA.maintenance.SaveMenuButton.saveBtnText");
t.add("label.jaffa.jaffaRIA.maintenance.SaveMenuButton.saveAsText");
t.add("label.jaffa.jaffaRIA.maintenance.SaveMenuButton.saveAndExitText");
t.add("label.jaffa.jaffaRIA.maintenance.TabPanel.closeWarningText");
t.add("title.jaffaRIA.Confirm");
t.add("label.jaffaRIA.Button.Edit");
t.add("label.jaffaRIA.Button.Edit.ToolTip");
t.add("label.jaffaRIA.Button.Preview");
t.add("label.jaffaRIA.Button.Preview.ToolTip");
t.add("label.jaffaRIA.Button.Cancel");
t.add("label.jaffaRIA.Button.Cancel.ToolTip");
t.add("label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters");
t.add("label.jaffaRIA.MessageBox.DiscardUnsavedDataChangesMsg");
t.add("label.jaffa.jaffaRIA.attachment.Container.WebLink");
t.add("label.JaffaRIA.Button.Modify");
t.add("label.JaffaRIA.Button.Add");
t.add("label.jaffaRIA.Button.Clear");
t.add("label.jaffaRIA.Button.Cancel");
t.add("label.jaffaRIA.Button.Save");
t.add("label.jaffa.jaffaRIA.finder.Criteria.alertMsgSetSortOrderByDisplayValue");
t.add("label.jaffa.jaffaRIA.finder.Criteria.alertMsgSortGrouprFieldNotRegisteredWithQueryManager");
t.add("label.jaffa.jaffaRIA.finder.Criteria.alertMsgSortOrderFieldNotRegisteredWithQueryManager");
t.add("label.jaffa.jaffaRIA.finder.Criteria.alertMsgsetGroupOrderByDisplayValue");
t.add("label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters");
t.add("label.jaffaRIA.Button.SearchAgain");
t.add("label.jaffaRIA.Button.Search");
t.add("label.jaffaRIA.Button.ViewList");
t.add("label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.alertMsgQuery");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.alertMsgNotInTheList");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.LoadQuery");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.titleSavedQueries");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.titleSaveQuery");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.PromtQueryNameToSave");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.PromtMsgEnterQueryNameToSaved");
t.add("label.jaffaRIA.Button.Save");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.saveAsDefaultQueryButtonText");
t.add("label.jaffaRIA.Button.Cancel");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.titleDeleteQuery");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.confirmDeleteQueryConfirmationTitle");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.deleteQueryActionConfirmMsg");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.titleClearCriteria");
t.add("label.jaffaRIA.Button.Search");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.alertMsgNoSavedQuerySelected");
t.add("label.jaffa.jaffaRIA.finder.SavedQueryPanel.alertMsgBasecriteriaObjecNotRegSavedQueryPanel");
t.add("label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.tbfill");
t.add("label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.tbseparator");
t.add("label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.Showing");
t.add("label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.of");
t.add("label.jaffa.jaffaRIA.finder.FinderGrids.ErrorLoadingRecordsMsg");
t.add("title.jaffa.jaffaRIA.finder.FinderGrids.ShowDetails");
t.add("label.jaffa.jaffaRIA.jaffa.finder.grid.confirmSelects");
t.add("label.jaffa.jaffaRIA.jaffa.finder.grid.showQueried");
t.add("label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters");
t.add("label.jaffa.jaffaRIA.jaffa.finder.grid.showSelected");
t.add("label.jaffaRIA.Button.ExportToExcel");
t.add("label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters");
t.add("label.JaffaRIA.Button.Previous");
t.add("label.JaffaRIA.Button.Next");
t.add("label.JaffaRIA.Button.Save");
t.add("label.JaffaRIA.Button.Finish");
t.add("label.jaffaRIA.Button.Ok");
t.add("label.jaffaRIA.Button.Cancel");
t.add("title.jaffa.jaffaRIA.maintenance.WarningPanel.Warning");
t.add("title.jaffa.jaffaRIA.maintenance.NavPanel.Contents");
t.add("title.jaffa.jaffaRIA.maintenance.Panel.DuplicateValue");
t.add("label.jaffa.jaffaRIA.maintenance.Panel.RecordWithEnteredValueAlreadyExists");
t.add("label.jaffaRIA.MessageBox.SimpleExportToExcelPlugin.KeyFieldMsg");
t.add("label.jaffaRIA.MessageBox.SimpleExportToExcelPlugin.NeedsDefinedBaseParamsChildStoreMsg");
t.add("title.jaffaRIA.Error");
t.add("title.jaffaRIA.MessageBox.SaveErrors");
t.add("label.Jaffa.Common.Records");
t.add("label.Jaffa.Common.Record");
t.add("title.JaffaRIA.MessageBox.LoadErrorsText");
t.add("label.jaffa.jaffaRIA.TabCloseMenu.CloseTabText");
t.add("label.jaffa.jaffaRIA.TabCloseMenu.CloseOtherTabsText");
t.add("label.JaffaRIA.Maintenance.AttachmentsTab");
t.add("title.Commons.RelatedLinks");
t.add("title.extjs.ux.UploadDialog.Dialog");
t.add("label.extjs.ux.UploadDialog.Dialog.state_col_title");
t.add("label.extjs.ux.UploadDialog.Dialog.filename_col_title");
t.add("label.extjs.ux.UploadDialog.Dialog.note_col_title");
t.add("label.extjs.ux.UploadDialog.Dialog.add_btn_text");
t.add("label.extjs.ux.UploadDialog.Dialog.add_btn_tip");
t.add("label.extjs.ux.UploadDialog.Dialog.remove_btn_text");
t.add("label.extjs.ux.UploadDialog.Dialog.remove_btn_tip");
t.add("label.extjs.ux.UploadDialog.Dialog.reset_btn_text");
t.add("label.extjs.ux.UploadDialog.Dialog.reset_btn_tip");
t.add("label.extjs.ux.UploadDialog.Dialog.upload_btn_start_text");
t.add("label.extjs.ux.UploadDialog.Dialog.upload_btn_stop_text");
t.add("label.extjs.ux.UploadDialog.Dialog.upload_btn_start_tip");
t.add("label.extjs.ux.UploadDialog.Dialog.upload_btn_stop_tip");
t.add("label.extjs.ux.UploadDialog.Dialog.close_btn_text");
t.add("label.extjs.ux.UploadDialog.Dialog.close_btn_tip");
t.add("label.extjs.ux.UploadDialog.Dialog.progress_waiting_text");
t.add("label.extjs.ux.UploadDialog.Dialog.progress_uploading_text");
t.add("label.extjs.ux.UploadDialog.Dialog.error_msgbox_title");
t.add("label.extjs.ux.UploadDialog.Dialog.err_file_type_not_permitted");
t.add("label.extjs.ux.UploadDialog.Dialog.note_queued_to_upload");
t.add("label.extjs.ux.UploadDialog.Dialog.note_processing");
t.add("label.extjs.ux.UploadDialog.Dialog.note_upload_failed");
t.add("label.extjs.ux.UploadDialog.Dialog.note_upload_success");
t.add("label.extjs.ux.UploadDialog.Dialog.note_upload_error");
t.add("label.extjs.ux.UploadDialog.Dialog.note_aborted");
t.add("label.extjs.ux.UploadDialog.Dialog.note_canceled");
t.add("label.JaffaRIA.Maintenance.AttachmentsSection");
t.add("label.Jaffa.Widgets.Button.Upload");
t.add("label.Jaffa.Widgets.Button.SaveAs");
t.add("title.jaffa.jaffaRIA.labelEditor.EditLabel");
t.add("title.JaffaRIA.Maintenance.DiscardChanges");
t.add("title.Material.Transaction.PickListMonitor");
t.add("label.jaffaRIA.MessageBox.DiscardUnsavedDataChangesMsg");
t.add("label.jaffa.jaffaRIA.jaffa.DWRService.Data.Error");
t.add("label.jaffa.jaffaRIA.jaffa.DWRService.Data.Error.message");
t.add("label.jaffa.jaffaRIA.jaffa.DWRService.Validation.Error");
t.add("label.jaffa.jaffaRIA.jaffa.DWRService.Grid.Validation.ErrorMessage");
t.add("error.framework.general");
t.add("label.jaffaRIA.MessageBox.FollowingFieldsAreMandatory.tooltip");
t.add("label.jaffaRIA.MessageBox.FollowingFieldsAreShorterThanAllowed.tooltip");
t.add("label.jaffaRIA.MessageBox.FollowingFieldsAreLongerThanAllowed.tooltip");
t.add("label.jaffaRIA.MessageBox.FollowingFieldsContainInvalidValues.tooltip");
t.add("label.jaffaRIA.MessageBox.BothKeyAndNatureKeyMustBeDefinedInTheStoreAlongWithMappings.tooltip");
t.add("title.jaffa.jaffaRIA.soa.DWRService.LoadError");
t.add("label.jaffa.jaffaRIA.soa.DWRService.LoadFailure");
t.add("label.jaffa.jaffaRIA.grid.MultiGroupingGrid.clearFiltersMenuText");
t.add("label.jaffa.jaffaRIA.grid.MultiGroupingGrid.emptyToolbarText");
t.add("label.jaffa.jaffaRIA.grid.MultiGroupingGrid.groupColumnsMenuText");
t.add("label.jaffa.jaffaRIA.grid.MultiGroupingGrid.optionMenuText");
t.add("label.jaffa.jaffaRIA.grid.MultiGroupingGrid.restoreMenuText");
t.add("label.jaffaRIA.proxyAction.Saving");
t.add("label.jaffaRIA.ExcelSheetName.GridExport");
t.add("label.Commons.From");
t.add("label.Commons.To");
t.add("label.Common.On");
t.add("label.jaffa.jaffaRIA.finder.FinderComboBox.invalidateNotFoundText");
t.add("label.jaffa.jaffaRIA.finder.FinderComboBox.invalidateMultipleRecordsFoundText");
t.add("label.jaffa.jaffaRIA.finder.FinderComboBox.invalidNumberText");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.Panel.RecordWithTheEntered");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.Panel.Value");
t.add("label.jaffa.jaffaRIA.maintenance.plugins.Panel.AlreadyExistsDuplicatesNotAllowedReEnterInformation");
t.add("label.jaffa.jaffaRIA.maintenance.ViewPort.AlertMsgUnableAutomaticallyCloseWindow");
t.add("title.jaffa.jaffaRIA.maintenance.ViewPort.SaveChanges");
t.add("label.jaffa.jaffaRIA.maintenance.ViewPort.DiscardUnSavedChangesMsg");
t.add("label.Jaffa.Common.NewRecord");
t.add("title.product.maintenance.newrecord.suffix");
t.add("label.jaffa.jaffaRIA.UX.Grid.MultiGroupingView.GroupedBy");
t.add("label.Common.of");
t.add("title.jaffaRIA.common.NEW");
t.add("title.Commons.OtherSections");
t.add("label.Jaffa.Widgets.Button.Update");
t.add("label.Commons.maxIntegralLengthText");
t.add("label.Commons.maxFractionalLengthText");
t.add("label.JaffaCore.PanelHeader.LoadingMaskText");
t.add("label.JaffaCore.PanelHeader.sessionExpired");
t.add("label.Jaffa.Common.LoadMaskText");
t.add("label.Jaffa.Common.CreateNewRecord");
t.add("label.Jaffa.Inquiry.CriteriaOption.Any");
t.add("label.jaffaRIA.MessageBox.AlertErrorMsg");
t.add("label.jaffaRIA.MessageBox.alertErrorMsgText");
t.add("label.jaffaRIA.MessageBox.alertErrorsMsgText");
t.add("label.jaffaRIA.MessageBox.AlertErrorsMsg");
t.add("label.jaffaRIA.MessageBox.AlertInfoMsg");
t.add("label.jaffaRIA.MessageBox.ErrorAlertMsg");
t.add("label.jaffaRIA.MessageBox.StatusAlertMsg");
t.add("label.jaffaRIA.MessageBox.ValidationError");
t.add("label.jaffaRIA.MessageBox.EditAlertMsg");
t.add("label.jaffaRIA.MessageBox.WarningAlertMsg");
t.add("label.jaffaRIA.MessageBox.AreYouSureYouWantToPerformOperationMsg");
t.add("label.jaffa.jaffaRIA.attachment.Container.FileName");
t.add("label.Jaffa.Widgets.Button.Print");
t.add("title.JaffaRIA.Maintenance.SaveErrors");
t.add("title.Commons.SaveError");
t.add("label.jaffaRIA.jaffa.Form.Comment.UserID");
t.add("title.jaffaRIA.Alert");

    // iterate through the label list and generate the labels
    for(String tk : t) {
        //String l = MessageHelper.findMessage(tk, null).replace("\"","'").replace("\n", "\\n").replace("\\\n", "\\n");
%>
Labels.data.add("<%=tk%>","<%=StringHelper.escapeJavascript(getMsg(tk))%>");<%
    }
    // Default special labels into certain ExtJS Widgets
    //String l = MessageHelper.findMessage("label.Jaffa.Widgets.UserGrid.NoRecordsKey", null).replace("\"","'");
%>
Ext.override(Ext.grid.GridView, {emptyText:'<%=getMsg("label.Jaffa.Widgets.UserGrid.NoRecordsKey")%>', deferEmptyText:false});

if(Jaffa && Jaffa.component && Jaffa.component.PanelController)
  Ext.override(Jaffa.component.PanelController, {
   'saveErrorTitleText': 			'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.saveErrorTitleText")%>',
	'saveFieldErrorMsgTpl': 		'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.saveFieldErrorMsgTpl")%>',
	'saveErrorMsgText': 			'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.saveErrorMsgText")%>',
	'validateErrorTitleText': 		'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.validateErrorTitleText")%>',
	'validateErrorMsgTpl': 			'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.validateErrorMsgTpl")%>',
	'mandatoryColErrorMsgTpl': 		'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.mandatoryColErrorMsgTpl")%>',
	'invalidMandatoryText' : 		'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.invalidMandatoryText")%>',
	'invalidMinLenText' : 			'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.invalidMinLenText")%>',
	'invalidMaxLenText' : 			'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.invalidMaxLenText")%>',
	'invalidComboValueText' : 		'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.invalidComboValueText")%>',
	'invalidDateValueText' : 		'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.invalidDateValueText")%>',
	'gridEditorValidationError' : 	'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.gridEditorValidationError")%>',
	'keyCheckAlertMsgText' :		'<%=getMsg("label.jaffa.jaffaRIA.component.PanelController.keyCheckAlertMsg")%>'
  });
  Ext.override(Jaffa.finder.CriteriaPanel, {
			  loadButtonText : 				'<%=getMsg("label.Jaffa.Finder.Button.loadQuery")%>',
			  saveButtonText : 				'<%=getMsg("label.Jaffa.Widgets.Button.Save")%>',
			  deleteButtonText: 			'<%=getMsg("label.Jaffa.Widgets.Button.Delete")%>',
			  clearButtonText: 				'<%=getMsg("label.Jaffa.Widgets.Button.Clear")%>',
			  searchButtonText:				'<%=getMsg("label.Jaffa.Widgets.Button.Search")%>',
			  saveTitleText: 				'<%=getMsg("title.Jaffa.Finder.saveQuery")%>',
			  saveMsgText : 				'<%=getMsg("label.Jaffa.Finder.SaveQuery.prompt")%>',
			  saveMsgYesText : 				'<%=getMsg("label.Jaffa.Finder.SaveQuery.confirm")%>',
			  saveMsgNoText: 				'<%=getMsg("label.Jaffa.Finder.SaveQuery.confirmDefault")%>',
			  saveMsgCancelText: 			'<%=getMsg("label.Jaffa.Widgets.Button.Cancel")%>',
			  deleteTitleText: 				'<%=getMsg("title.Jaffa.Finder.deleteQuery")%>',
			  deleteMsgTpl: 				'<%=getMsg("label.Jaffa.Finder.DeleteQuery.prompt")%>',
			  errorTitleText: 				'<%=getMsg("label.Jaffa.Finder.errorTitleText")%>',
			  saveErrorText: 				'<%=getMsg("label.Jaffa.Finder.saveErrorText")%>',
			  deleteErrorText: 				'<%=getMsg("label.Jaffa.Finder.deleteErrorText")%>',
			  titleBase:					'<%=getMsg("label.jaffa.jaffaRIA.finder.CriteriaPanel.titleBase")%>',
			  validationError:				'<%=getMsg("label.jaffa.jaffaRIA.finder.CriteriaPanel.validationError")%>',
			  invalidSearchCriteria:		'<%=getMsg("label.jaffa.jaffaRIA.finder.CriteriaPanel.invalidSearchCriteria")%>',
			  alertMsgQueryMsgText : 		'<%=getMsg("label.jaffa.jaffaRIA.finder.CriteriaPanel.alertMsgQuery")%>',
			  alertMsgNotInTheListMsgText : '<%=getMsg("label.jaffa.jaffaRIA.finder.CriteriaPanel.alertMsgNotInTheList")%>',
			  maxRecordsTitleText : 		'<%=getMsg("label.Jaffa.Inquiry.maxRecords")%>',
			  alertMsgNoSavedQuerySelected: '<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.alertMsgNoSavedQuerySelected")%>'
			  
  });
  Ext.apply(Jaffa.finder.CriteriaOptionsFactory.labels , {
    'Equals' :              '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.Equals")%>',
    'NotEquals' :           '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.NotEquals")%>',
    'GreaterThan' :         '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.GreaterThan")%>',
    'SmallerThan' :         '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.SmallerThan")%>',
    'GreaterThanOrEquals' : '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.GreaterThanEqualTo")%>',
    'SmallerThanOrEquals' : '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.SmallerThanEqualTo")%>',
    'IsNotNull' :           '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.IsNotNull")%>',
    'IsNull' :              '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.IsNull")%>',
    'Between' :             '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.Between")%>',
    'In' :                  '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.In")%>',
    'BeginsWith' :          '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.BeginsWith")%>',
    'EndWith'  :            '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.EndsWith")%>',
    'Like' :                '<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.Like")%>',
    '' :                 	'<%=getMsg("label.Jaffa.Inquiry.BooleanCriteriaDropDownOption.Any")%>',
    'True' :                '<%=getMsg("label.Jaffa.Inquiry.BooleanCriteriaDropDownOption.True")%>',
    'False' :               '<%=getMsg("label.Jaffa.Inquiry.BooleanCriteriaDropDownOption.False")%>'
  });
  
  <%-- Hardcoded texts are tokenized in the class FinderHeader.js are overridden by the below variables --%>
  Ext.override(Jaffa.finder.FinderHeader , {
    'closeScreenTitleText': 				'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.closeScreenTitleText")%>',
    'closeScreenText':      				'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.closeScreenText")%>',
    'closeTipText':         				'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.closeTipText")%>',
    'refreshTipText':       				'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.refreshTipText")%>',
	'optionsTipText':						'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.optionsTipText")%>',
	'startAutoRefreshText' : 				'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.startAutoRefreshText")%>',
	'stopAutoRefreshText' : 				'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.stopAutoRefreshText")%>',
	'removePersonalizationsText': 			'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.removePersonalizationsText")%>',
	'personalizationsRemovedReloadText': 	'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.personalizationsRemovedReloadText")%>',
	'personalizationsRemovedTitle': 		'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.personalizationsRemovedTitle")%>',
	'personalizationsRemovedMsg': 			'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.personalizationsRemovedMsg")%>',
    'emptySearchText':      				'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.emptySearchText")%>',
    'logoutTitleText':      				'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.logoutTitleText")%>',
    'logoutText':           				'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.logoutText")%>',
	'rootMenuText':							'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.rootMenuText")%>',
	'userId':								'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.userId")%>',
	'titleNewRecordText':					'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.titleNewRecord")%>',
	'alertPromtCloseWindowMsg':				'<%=getMsg("label.jaffaRIA.MessageBox.alertPromtCloseWindowMsg")%>',
	'searchInputTitleText':					'<%=getMsg("label.jaffaRIA.TextBox.Search")%>',
	'logoutButtonText':						'<%=getMsg("label.jaffaRIA.Button.Logout")%>',
	'settingsButtonText':					'<%=getMsg("label.jaffaRIA.Button.Settings")%>',
	'autoRefreshDelaySeconds':				'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderHeader.autoRefreshDelaySeconds")%>',
	'allProgramsButtonText':				'<%=getMsg("label.jaffaRIA.DropDownMenu.AllPrograms")%>',
	'serverIdText':							'<%=getMsg("label.ContractWarehouse.Core.DataInstance.InstanceId")%>'
  });

  Ext.override(Jaffa.component.CopyController, {
	'cloneErrorTitleText':	 	'<%=getMsg("label.jaffa.jaffaRIA.component.CopyController.cloneErrorTitleText")%>',
	'cloneFieldErrorMsgTpl':	'<%=getMsg("label.jaffa.jaffaRIA.component.CopyController.cloneFieldErrorMsgTpl")%>',
	'cloneErrorMsgText':	 	'<%=getMsg("label.jaffa.jaffaRIA.component.CopyController.cloneErrorMsgText")%>',
	'objectLabel': 				'<%=getMsg("label.jaffa.jaffaRIA.component.CopyController.objectLabel")%>',
	'cloningMaskText' : 		'<%=getMsg("label.jaffa.jaffaRIA.component.CopyController.cloningMaskText")%>',
	'cloneFailureTitleText' :   '<%=getMsg("title.jaffaRIA.MessageBox.CloneFailed")%>'
  });

  Ext.override(Jaffa.component.CRUDController, {
	'objectLabel' : 					'<%=getMsg("label.jaffa.jaffaRIA.component.CRUDController.objectLabel")%>',
	'loadMaskText': 					'<%=getMsg("label.jaffa.jaffaRIA.component.CRUDController.loadMaskText")%>',
	'saveErrorTitle': 					'<%=getMsg("label.jaffa.jaffaRIA.component.CRUDController.saveErrorTitle")%>',
	'defaultLocalizedErrorMessage': 	'<%=getMsg("label.jaffa.jaffaRIA.component.CRUDController.defaultLocalizedErrorMessage")%>',
	'noChangeToSaveText': 				'<%=getMsg("label.jaffa.jaffaRIA.component.CRUDController.noChangeToSaveText")%>',
	'noChangeToSaveTitle': 				'<%=getMsg("label.jaffa.jaffaRIA.component.CRUDController.noChangeToSaveTitle")%>',
	'savingMaskText':					'<%=getMsg("label.jaffa.jaffaRIA.component.CRUDController.savingMaskText")%>',
	'PreValidationFailedMsgText':		'<%=getMsg("label.jaffaRIA.MessageBox.PrevalidationFailedMsg")%>',
	'loadErrorTitleText':				'<%=getMsg("title.JaffaRIA.MessageBox.LoadError")%>',
	'loadFailureMsgText':				'<%=getMsg("label.jaffaRIA.MessageBox.loadFailureMsg")%>',
	'alertValidationFailedTitleText':	'<%=getMsg("label.jaffaRIA.MessageBox.alertValidationFailed")%>'
  });

  Ext.override(Jaffa.component.LoadController, {
	'objectLabel' : 				'<%=getMsg("label.jaffa.jaffaRIA.component.LoadController.objectLabel")%>',
	'loadMaskText': 				'<%=getMsg("label.jaffa.jaffaRIA.component.LoadController.loadMaskText")%>',
	'defaultLocalizedErrorMessage': '<%=getMsg("label.jaffa.jaffaRIA.component.LoadController.defaultLocalizedErrorMessage")%>'
  });

  Ext.override(Jaffa.form.FinderViewport, {
	updateLabel:							'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.updateLabel")%>',
	viewLabel:					            '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.viewLabel")%>',
	createLabel:					        '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.createLabel")%>',
	inquiryLabel:					        '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.inquiryLabel")%>',
	criteriaLabel:				            '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.criteriaLabel")%>',
	resultsLabel:					        '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.resultsLabel")%>',
	fastAccessLabel:				        '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.fastAccessLabel")%>',
	saveChangesTitle:				        '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.saveChangesTitle")%>',
	operationConfirmLabel:		            '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.operationConfirmLabel")%>',
	createNewRecLabel:			            '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.createNewRecLabel")%>',
	loseChangesMsg:				            '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.loseChangesMsg")%>',
	deleteTitle:					        '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.deleteTitle")%>',
	deleteSuccessMsg:				        '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.deleteSuccessMsg")%>',
	errorTitle:					            '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.errorTitle")%>',
	warningTitle:					        '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.warningTitle")%>',
	saveLabel:					            '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.saveLabel")%>',
	cancelLabel:					        '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.cancelLabel")%>',
	selectAtLeastOneRecMsg:		            '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.selectAtLeastOneRecMsg")%>',
	selectARecMsg:				            '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.selectARecMsg")%>',
	defaultLocalizedErrorMessage:           '<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.defaultLocalizedErrorMessage")%>',
	addButtonText: 							'<%=getMsg("label.JaffaRIA.Button.Add")%>',
	deleteButtonText:						'<%=getMsg("label.JaffaRIA.Button.Delete")%>'
		
  });
  Ext.override(Jaffa.form.BooleanComboBox, {
	anyText:		'<%=getMsg("label.jaffa.jaffaRIA.form.BooleanCombo.anyText")%>',
	trueText:		'<%=getMsg("label.jaffa.jaffaRIA.form.BooleanCombo.trueText")%>',
	falseText:		'<%=getMsg("label.jaffa.jaffaRIA.form.BooleanCombo.falseText")%>'
	
  });
  Ext.override(Jaffa.form.FinderComboBoxPlus, {
	invalidateNotFoundText: 			'<%=getMsg("label.jaffa.jaffaRIA.form.FinderComboBoxPlus.invalidateNotFoundText")%>',
	invalidateMultipleRecordsFoundText: '<%=getMsg("label.jaffa.jaffaRIA.form.FinderComboBoxPlus.invalidateMultipleRecordsFoundText")%>'
	
  });
	
  Ext.override(Jaffa.form.HourMinuteField, {
	invalidText : 				'<%=getMsg("label.jaffa.jaffaRIA.form.HourMinuteField.invalidText")%>',
	decimalFormatInvalidText : 	'<%=getMsg("label.jaffa.jaffaRIA.form.HourMinuteField.decimalFormatInvalidText")%>'
	
  });

  Ext.override(Jaffa.maintenance.plugins.InlineRowEditor, {
	waitingText: 							'<%=getMsg("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.waitingText")%>',
	loadingText: 							'<%=getMsg("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.loadingText")%>',
	duplicateKeyText: 						'<%=getMsg("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.duplicateKeyText")%>',
	buttonErrorTitle: 						'<%=getMsg("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.buttonErrorTitle")%>',
	removeButtonSelectAtLeastOneRecordText:	'<%=getMsg("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.removeButtonSelectAtLeastOneRecordText")%>',
	isRequiredText: 						'<%=getMsg("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.isRequired")%>',
	theValueInText: 						'<%=getMsg("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.TheValueIn")%>',
	isInvalidText: 							'<%=getMsg("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.isInvalid")%>',
	maximumLengthMsgText: 					'<%=getMsg("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.TheMaximumLengthFor")%>',
	minimumLengthMsgText: 					'<%=getMsg("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.TheMinimumLengthFor")%>',
	isZeroText: 							'<%=getMsg("label.jaffa.jaffaRIA.maintenance.InlineRowEditor.isZero")%>',
	addButtonText: 							'<%=getMsg("label.JaffaRIA.Button.Add")%>',
	deleteButtonText: 						'<%=getMsg("label.JaffaRIA.Button.Delete")%>',
	deleteSelectedTitleText: 				'<%=getMsg("title.JaffaRIA.Maintenance.DeleteSelected")%>',
	deletePromptMessageText: 				'<%=getMsg("label.JaffaRIA.Maintenance.DeleteSelected")%>',
	saveErrorsTitleText:					'<%=getMsg("title.jaffaRIA.MessageBox.SaveErrors")%>'
	
  });

  Ext.override(Jaffa.tree.DwrTreeLoader, {
	loadingMaskText : '<%=getMsg("label.jaffa.jaffaRIA.tree.DwrTreeLoader.loadingMaskText")%>'
  });

  Ext.override(Jaffa.tree.DwrColumnTreeLoader, {
	loadingMaskText: '<%=getMsg("label.jaffa.jaffaRIA.tree.DwrColumnTreeLoader.loadingMaskText")%>'
  });


  Ext.override(Jaffa.maintenance.plugins.MultiRowUpdate, {
	cancelText:						'<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.cancelText")%>',
	updateText:						'<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.updateText")%>',
	errorText : 					'<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.errorText")%>',
	noSelectionErrorText : 			'<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.noSelectionErrorText")%>',
	validationErrorText : 			'<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.validationErrorText")%>',
	saveErrorsTitleText: 			'<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.saveErrorsTitleText")%>',
	notSelectedForUpdatedTitleText: '<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.notSelectedForUpdatedTitleText")%>',
	flexFieldsTitleText: 			'<%=getMsg("label.common.FlexFields")%>',
	okButtonText: 					'<%=getMsg("label.jaffaRIA.Button.Ok")%>',
	errorsTitleText: 				'<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.errorsTitleText")%>',
	defaultUpdateButtonConfig:	{
    xtype: 'button',
    iconCls : 'save-all',
    text: '<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.MultiRowUpdate.UpdateMultipleRecords")%>',
    itemId: 'multiupdatebutton',
    ref : 'multiupdatebutton',
    disabled : false,
    hidden : false
								}
 });


  Ext.override(Jaffa.maintenance.GridDetailContainer, {
    modifyButtonText: 						'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.modifyButtonText")%>',
    buttonErrorTitle: 						'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.buttonErrorTitle")%>',
    modifyButtonSelectOneRecordText: 			'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.modifyButtonSelectOneRecordText")%>',
    viewButtonText: 							'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.viewButtonText")%>',
    addButtonText:							'<%=getMsg("label.JaffaRIA.Button.Add")%>',
    removeButtonText:							'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.removeButtonText")%>',
    removeButtonSelectAtLeastOneRecordText:	'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.removeButtonSelectAtLeastOneRecordText")%>',
    okButtonText:								'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.okButtonText")%>',
    cancelButtonText: 						'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.cancelButtonText")%>',
    detailButtonText: 						'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.detailButtonText")%>',
    listButtonText: 							'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.listButtonText")%>',
    fixText: 									'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.fixText")%>',
    discardText:								'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.discardText")%>',
    confirmText: 								'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.confirmText")%>',
    warningText:								'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.warningText")%>',
    addRecordText: 							'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.addRecordText")%>',
    modifyRecordText: 						'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.modifyRecordText")%>',
    copyErrorTitle: 							'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.copyErrorTitle")%>',
    copySelectOneRecordText:					'<%=getMsg("label.jaffa.jaffaRIA.maintenance.GridDetailContainer.copySelectOneRecordText")%>'
  });

  Ext.override(Jaffa.maintenance.HeaderPanel, {

  dataNotFoundTitle: 	'<%=getMsg("label.jaffa.jaffaRIA.maintenance.HeaderPanel.dataNotFoundTitle")%>',
  dataNotFoundMessage: 	'<%=getMsg("label.jaffa.jaffaRIA.maintenance.HeaderPanel.dataNotFoundMessage")%>'

  });
  
  Ext.override(Jaffa.maintenance.PendingEventsWindow , {
    applyToAllText: 				'<%=getMsg("label.jaffa.jaffaRIA.maintenance.PendingEventsWindow.applyToAllText")%>',
    finishButtonText: 				'<%=getMsg("label.jaffa.jaffaRIA.maintenance.PendingEventsWindow.finishButtonText")%>',
    cancelButtonText: 				'<%=getMsg("label.jaffa.jaffaRIA.maintenance.PendingEventsWindow.cancelButtonText")%>',
    previousButtonText:				'<%=getMsg("label.JaffaRIA.Button.Previous")%>',
    nextButtonText: 				'<%=getMsg("label.JaffaRIA.Button.Next")%>',
    continueButtonText:				'<%=getMsg("label.JaffaRIA.Button.Continue")%>',
    warningsPendingEventsTitleText:	'<%=getMsg("title.jaffa.jaffaRIA.maintenance.PendingEventsWindow.WarningsPendingEvents")%>'
  });

  Ext.override(Jaffa.maintenance.SaveMenuButton, {
    saveBtnText: 		'<%=getMsg("label.jaffa.jaffaRIA.maintenance.SaveMenuButton.saveBtnText")%>',
    saveAsText : 		'<%=getMsg("label.jaffa.jaffaRIA.maintenance.SaveMenuButton.saveAsText")%>',
    saveAndExitText : '<%=getMsg("label.jaffa.jaffaRIA.maintenance.SaveMenuButton.saveAndExitText")%>'
  });
  

  Ext.override(Jaffa.maintenance.TabPanel, {
    closeWarningText: 	'<%=getMsg("label.jaffa.jaffaRIA.maintenance.TabPanel.closeWarningText")%>',
    confirmActionMsgText: '<%=getMsg("title.jaffaRIA.Confirm")%>'
  });

  Ext.override(Ext.ux.form.HtmlEditor, {
	editButton: {
      text: '<%=getMsg("label.jaffaRIA.Button.Edit")%>',
      iconCls: 'edit',
      tooltip: '<%=getMsg("label.jaffaRIA.Button.Edit.ToolTip")%>',
      previewVisible: true,
      handler: function(btn, e){
        btn.editor.togglePreview(false);
        btn.editor.origValue = btn.editor.getValue();
        btn.editor.cancelButton.setDisabled(false);
        btn.editor.previewButton.setDisabled(false);
      }
    },
	previewButton: {
      text: '<%=getMsg("label.jaffaRIA.Button.Preview")%>',
      iconCls: 'next',
      tooltip: '<%=getMsg("label.jaffaRIA.Button.Preview.ToolTip")%>',
      previewVisible: false,
      handler: function(btn, e){
        btn.editor.togglePreview(true);
        btn.editor.editButton.setDisabled(false);
      }
    },
  cancelButton: {
    text: '<%=getMsg("label.jaffaRIA.Button.Cancel")%>',
    iconCls: 'cancel',
    tooltip: '<%=getMsg("label.jaffaRIA.Button.Cancel.ToolTip")%>',
    previewVisible: false,
    handler: function(btn, e){
      btn.editor.setValue(btn.editor.origValue);
      btn.editor.togglePreview(true);
      btn.editor.editButton.setDisabled(false);
    }
  }
});

Ext.override(Jaffa.form.FinderComboGrid,{
	clearFiltersMenuText: '<%=getMsg("label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters")%>'
});

Ext.override(Jaffa.data.BaseDwrCriteria, {
	alertMsgsetGroupOrderByDisplayValueMsgText: 					'<%=getMsg("label.jaffa.jaffaRIA.finder.Criteria.alertMsgSetSortOrderByDisplayValue")%>',
	alertMsgSortGrouprFieldNotRegisteredWithQueryManagerMsgText: 	'<%=getMsg("label.jaffa.jaffaRIA.finder.Criteria.alertMsgSortGrouprFieldNotRegisteredWithQueryManager")%>',
	alertMsgSortOrderFieldNotRegisteredWithQueryManagerMsgText: 	'<%=getMsg("label.jaffa.jaffaRIA.finder.Criteria.alertMsgSortOrderFieldNotRegisteredWithQueryManager")%>',
	alertMsgSetSortOrderByDisplayValueMsgText: 						'<%=getMsg("label.jaffa.jaffaRIA.finder.Criteria.alertMsgsetGroupOrderByDisplayValue")%>'
});

Ext.override(Jaffa.finder.FinderContainer,{
	searchAgainButtonText: 	'<%=getMsg("label.jaffaRIA.Button.SearchAgain")%>',
	searchButtonText:		'<%=getMsg("label.jaffaRIA.Button.Search")%>',
	viewListButtonText:		'<%=getMsg("label.jaffaRIA.Button.ViewList")%>'
});  

Ext.override(Jaffa.form.FinderGridWindow , {
	deactivateFiltersText : '<%=getMsg("label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters")%>'
});

Ext.override(LoadQueryMenu, Ext.menu.Menu, {
	alertMsgQueryText : 								'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.alertMsgQuery")%>',
	alertMsgNotInTheListText : 							'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.alertMsgNotInTheList")%>',
	loadQueryTitleText: 								'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.LoadQuery")%>',
	savedQueriesTitleText: 								'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.titleSavedQueries")%>',
	saveQueryTitleText: 								'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.titleSaveQuery")%>',
	promtQueryNameToSaveTitleText: 						'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.PromtQueryNameToSave")%>',
	promtMsgEnterQueryNameToSavedText: 					'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.PromtMsgEnterQueryNameToSaved")%>',
	saveButtonText: 									'<%=getMsg("label.jaffaRIA.Button.Save")%>',
	saveAsDefaultQueryButtonText: 						'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.saveAsDefaultQueryButtonText")%>',
	cancelButtonText: 									'<%=getMsg("label.jaffaRIA.Button.Cancel")%>',
	deleteQueryTitleText: 								'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.titleDeleteQuery")%>',
	confirmDeleteQueryConfirmationTitleText: 			'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.confirmDeleteQueryConfirmationTitle")%>',
	deleteQueryActionConfirmMsgText: 					'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.deleteQueryActionConfirmMsg")%>',
	clearCriteriaTitleText: 							'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.titleClearCriteria")%>',
	searchTitleText: 									'<%=getMsg("label.jaffaRIA.Button.Search")%>',
	alertMsgNoSavedQuerySelectedText: 					'<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.alertMsgNoSavedQuerySelected")%>',
	alertMsgBasecriteriaObjecNotRegSavedQueryPanelText: '<%=getMsg("label.jaffa.jaffaRIA.finder.SavedQueryPanel.alertMsgBasecriteriaObjecNotRegSavedQueryPanel")%>'
});

Ext.override(Jaffa.form.FinderLovComboGrid , {
	confirmSelectsText: 	'<%=getMsg("label.jaffa.jaffaRIA.jaffa.finder.grid.confirmSelects")%>',
	showQueriedText: 		'<%=getMsg("label.jaffa.jaffaRIA.jaffa.finder.grid.showQueried")%>',
	deactivateFiltersText:	'<%=getMsg("label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters")%>',
	showSelectedText: 		'<%=getMsg("label.jaffa.jaffaRIA.jaffa.finder.grid.showSelected")%>'
});

Ext.override(Jaffa.form.FinderComboGrid, {
	deactivateFiltersText : '<%=getMsg("label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters")%>'
});

Ext.override(Jaffa.grid.plugins.SimpleExportToExcelPlugin,{
	exportToExcelText : '<%=getMsg("label.jaffaRIA.Button.ExportToExcel")%>'
});

Ext.override(Jaffa.maintenance.WizardPanel , {
	previousButtonText: '<%=getMsg("label.JaffaRIA.Button.Previous")%>',
	nextButtonText: 	'<%=getMsg("label.JaffaRIA.Button.Next")%>',
	saveButtonText: 	'<%=getMsg("label.JaffaRIA.Button.Save")%>',
	finishButtonText: 	'<%=getMsg("label.JaffaRIA.Button.Finish")%>'
});

Ext.override(Jaffa.maintenance.WarningPanel , {
	warningTitleText: '<%=getMsg("title.jaffa.jaffaRIA.maintenance.WarningPanel.Warning")%>'
});

Ext.override(Jaffa.maintenance.Panel , {
	duplicateValueTitleText: 					'<%=getMsg("title.jaffa.jaffaRIA.maintenance.Panel.DuplicateValue")%>',
	recordWithEnteredValueAlreadyExistsMsgText: '<%=getMsg("label.jaffa.jaffaRIA.maintenance.Panel.RecordWithEnteredValueAlreadyExists")%>'
});

Ext.override(Ext.ux.grid.MultiGroupingPagingStore, {
	loadErrorsTitleText: 			'<%=getMsg("title.JaffaRIA.MessageBox.LoadErrorsText")%>',
	defaultLocalizedErrorMessage: 	'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderViewport.defaultLocalizedErrorMessage")%>'
});


Ext.ux.UploadDialog.Dialog.prototype.i18n.title='<%=getMsg("title.extjs.ux.UploadDialog.Dialog")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.state_col_title='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.state_col_title")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.filename_col_title='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.filename_col_title")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.note_col_title='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.note_col_title")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.add_btn_text='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.add_btn_text")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.add_btn_tip='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.add_btn_tip")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.remove_btn_text='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.remove_btn_text")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.remove_btn_tip='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.remove_btn_tip")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.reset_btn_text='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.reset_btn_text")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.reset_btn_tip='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.reset_btn_tip")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.upload_btn_start_text='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.upload_btn_start_text")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.upload_btn_stop_text='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.upload_btn_stop_text")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.upload_btn_start_tip='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.upload_btn_start_tip")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.upload_btn_stop_tip='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.upload_btn_stop_tip")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.close_btn_text='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.close_btn_text")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.close_btn_tip='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.close_btn_tip")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.progress_uploading_text='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.progress_uploading_text")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.error_msgbox_title='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.error_msgbox_title")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.err_file_type_not_permitted='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.err_file_type_not_permitted")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.note_queued_to_upload='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.note_queued_to_upload")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.note_processing='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.note_processing")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.note_upload_failed='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.note_upload_failed")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.note_upload_success='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.note_upload_success")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.note_upload_error='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.note_upload_error")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.note_aborted='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.note_aborted")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.note_canceled='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.note_canceled")%>';
Ext.ux.UploadDialog.Dialog.prototype.i18n.progress_waiting_text='<%=getMsg("label.extjs.ux.UploadDialog.Dialog.progress_waiting_text")%>';

Ext.override(Ext.ux.grid.MultiGroupingGrid,{
	clearFiltersMenuText: 	'<%=getMsg("label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters")%>',
	emptyToolbarText: 		'<%=getMsg("label.jaffa.jaffaRIA.grid.MultiGroupingGrid.emptyToolbarText")%>',
	optionMenuText:			'<%=getMsg("label.jaffa.jaffaRIA.grid.MultiGroupingGrid.optionMenuText")%>',
	restoreMenuText:		'<%=getMsg("label.jaffa.jaffaRIA.grid.MultiGroupingGrid.restoreMenuText")%>',
	groupColumnsMenuText:	'<%=getMsg("label.jaffa.jaffaRIA.grid.MultiGroupingGrid.groupColumnsMenuText")%>'
});

Ext.override(Ext.ux.grid.filter.StringFilter, {
	beginsWithFiltersMenu:	'<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.BeginsWith")%>',
	endsWithFiltersMenu:	'<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.EndsWith")%>',
	likeFiltersMenu:		'<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.Like")%>',
	equalsFiltersMenu:		'<%=getMsg("label.Jaffa.Inquiry.CriteriaDropDownOption.Equals")%>'
});

Ext.override(Ext.ux.grid.filter.DateFilter, {
	afterText :  '<%=getMsg("label.Commons.From")%>',
	beforeText : '<%=getMsg("label.Commons.To")%>',
	onText : '<%=getMsg("label.Common.On")%>'
});


Ext.override(Jaffa.form.FinderComboBox, {
	invalidateNotFoundText:				'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderComboBox.invalidateNotFoundText")%>',
	invalidateMultipleRecordsFoundText:	'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderComboBox.invalidateMultipleRecordsFoundText")%>',
	invalidNumberText:					'<%=getMsg("label.jaffa.jaffaRIA.finder.FinderComboBox.invalidNumberText")%>'
	
});

Ext.override(Jaffa.maintenance.plugins.Panel, {
	duplicateValueText:											'<%=getMsg("title.jaffa.jaffaRIA.maintenance.Panel.DuplicateValue")%>',
	recordWithTheEnteredText:                                   '<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.Panel.RecordWithTheEntered")%>',
	valueText:                                                  '<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.Panel.Value")%>',
	alreadyExistsDuplicatesNotAllowedReEnterInformationTextMsg: '<%=getMsg("label.jaffa.jaffaRIA.maintenance.plugins.Panel.AlreadyExistsDuplicatesNotAllowedReEnterInformation")%>'
});

Ext.override(Jaffa.maintenance.Viewport,{
	alertMsgUnableAutomaticallyCloseWindow:	'<%=getMsg("label.jaffa.jaffaRIA.maintenance.ViewPort.AlertMsgUnableAutomaticallyCloseWindow")%>',
	confirmSaveChange:						'<%=getMsg("title.jaffa.jaffaRIA.maintenance.ViewPort.SaveChanges")%>',	
	discardUnSavedChangesMsg:				'<%=getMsg("label.jaffa.jaffaRIA.maintenance.ViewPort.DiscardUnSavedChangesMsg")%>'
});

Ext.override(Ext.form.NumberField, {
	maxIntegralLengthText:						'<%=getMsg("label.Commons.maxIntegralLengthText")%>',	
	maxFractionalLengthText:					'<%=getMsg("label.Commons.maxFractionalLengthText")%>'	
});

<%--  TODO
Jaffa.form.FinderComboBox
  invalidateNotFoundText: 'Record not found',
  invalidateMultipleRecordsFoundText: 'Multiple records found that match the value in this field',
Jaffa.form.FinderComboBoxPlus
  invalidateNotFoundText: 'Record not found',
  invalidateMultipleRecordsFoundText: 'Multiple records found that match the value in this field',
Jaffa.form.HourMinuteField.invalidText = 'The input must be in h*:mm format.';
Ext.ux.grid.MultiGroupingGrid.emptyToolbarText = 'Drag Columns Here to Group'
--%>
<%
} else if(ref!=null && token!=null && "labelToken".equals((String)request.getParameter("tokenClass"))) {
    // Add a new token for this page to the server-side file
    addLabelToken(ref, request, token);
}
%>
