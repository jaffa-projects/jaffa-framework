<%@ page import='org.jaffa.util.MessageHelper' %>
<script type='text/javascript'>
if (foldingSectionList.length>0) {
  newHTML =  "<table cellpadding='0' cellspacing='0' align='center' class='sectionNav'>";
  newHTML += " <tr>";
  newHTML += "  <td colSpan='4' valign='bottom' width='100%'>";
  newHTML += "   <table class='sectionNavHeader' width='100%'>";
  newHTML += "    <tr>";
  newHTML += "     <td class='sectionNavTitle'><%=MessageHelper.replaceTokens(pageContext, "[title.Jaffa.SectionNav]")%></td>";
  newHTML += "     <td align='right'>";
  newHTML += "      <a href=\"javascript:expand('targets','targetsExpand')\" >";
  newHTML += "      <img src='jaffa/imgs/foldingsection/arrowminimize.gif' border='0' name='targetsExpand'></a>"
  newHTML += "     </td>";
  newHTML += "    </tr>";
  newHTML += "   </table>";
  newHTML += "  </td>";
  newHTML += " </tr>";
  newHTML += " <tr align='middle'>";
  newHTML += "  <td colspan='1'><span id='targets' style='display: block'>";
  newHTML += "   <table class='sectionNavBody' cellspacing='0' cellpadding='0' width='100%'>";
  newHTML += "    <tr>";
  newHTML += "     <td>";
  newHTML += "      <table class='sectionNavBody' width='100%' cellspacing='0' align='center' cellpadding='0' border='0'>";
  for(i = 0;i<foldingSectionList.length;i++){
    newHTML += "        <tr align='middle'>";
    newHTML += "         <td align='left'>";
    newHTML += "          <a href=\"javascript:expand('"+ foldingSectionList[i].id +"','"+ foldingSectionList[i].id +"Expand','only')\">";
    newHTML += foldingSectionList[i].getAttribute('name') + "</a></td>";
    newHTML += "        </tr>";
  }
  newHTML += "      </table>";
  newHTML += "     </td>";
  newHTML += "    </tr>";
  newHTML += "   </table></span>";
  newHTML += "  </td>";
  newHTML += " </tr>";
  newHTML += "</table><br>";

  document.getElementById("SectionMenu").insertAdjacentHTML("afterBegin", newHTML);

}
try {
    document.getElementById('ContextMenu').insertAdjacentHTML("afterBegin",document.getElementById('ContextMenuContents').innerHTML.replace("^span","div"));
} catch (e) {}
</script>