<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>
	<#setting number_format="#">
</head>
<body>
<form id="actionForm_app"  method="post" type="label" autoclose="true"    closeWindow="true"
			action="<@base/>/apps/basic/update"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table   class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appUpdateCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
				 			<table width="960"   class="table table-bordered"  >
								<tbody>
								<tr>
									<td  colspan=4>
										<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
										<input class="button btn btn-secondary mr-3"  id="backBtn" type="button" value="<@locale code="button.text.close" />"/>		  
									</td>
								</tr>
								
								</tbody>
							  </table>
			  </td>
				</tr>
				</tbody>
				</table>
    		
</form>
</body>
</html>