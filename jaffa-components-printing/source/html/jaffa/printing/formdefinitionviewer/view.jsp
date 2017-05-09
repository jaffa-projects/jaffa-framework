 <%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.modules.printing.components.formdefinitionviewer.ui.FormDefinitionViewerForm" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<Portlet:FoldingSection id='GeneralDetails' label='<%= MessageHelper.findMessage("title.Jaffa.Common.GeneralDetails", null)  %>' closed='false'>
<table>
  <Portlet:Property field='formName'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormGroup.FormName]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='formAlternate'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.FormAlternate]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='formVariation'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.FormVariation]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='description'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.Description]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='remarks'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.Remarks]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='outputType'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.OutputType]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='followOnFormName'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.FollowOnFormName]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  
  <Portlet:Property field='followOnFormAlternate'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.FollowOnFormAlternate]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  
</table>
</Portlet:FoldingSection>  

<Portlet:FoldingSection id='Template' label='<%= MessageHelper.findMessage("title.Jaffa.Common.Template", null)  %>' closed='false'>
<table>
  <Portlet:Property field='formTemplate'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.FormTemplate]"/>:
      </td>
      <td>
        <% String formTemplate =((FormDefinitionViewerForm)TagHelper.getFormBase(pageContext)).getFormTemplate();          
        if(formTemplate!=null){%>
        <Portlet:Button type='link' target='_blank' preprocess='false' field='ViewFileContents' label='<%=formTemplate%>'/>
        <% } %>
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='fieldLayout'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.FieldLayout]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
</table>
</Portlet:FoldingSection>   
<Portlet:FoldingSection id='DataAccess' label='<%= MessageHelper.findMessage("title.Jaffa.Common.DataAccess", null)  %>' closed='false'>
<table>
  <Portlet:Property field='additionalDataComponent'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.AdditionalDataComponent]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='domFactory'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.DomFactory]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='domClass'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.DomClass]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='domKey1'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.DomKey1]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='domKey2'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.DomKey2]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='domKey3'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.DomKey3]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='domKey4'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.DomKey4]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='domKey5'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.DomKey5]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='domKey6'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.DomKey6]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property> 
</table>
</Portlet:FoldingSection>


