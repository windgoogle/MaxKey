
<!--table-->
<table width="960"   class="table table-bordered" >
   <tbody>
      <tr>
         <td colspan=4><@locale code="apps.basic.info"/></td>
      </tr>
      <tr>
         <th><@locale code="apps.id"/>：</th>
         <td>
         	<span id="id_text"  style="width:100%;font-weight: bold;">${model.id!}</span>
            <input type="hidden"  class="form-control" id="id" name="id"  title="" value="${model.id!}"/>
         </td>
         <th><@locale code="apps.secret"/>：</th>
         <td>
         	<span id="secret_text"  style="width:100%;font-weight: bold;">${model.secret!}</span>
            <input type="hidden"class="form-control"  id="secret" name="secret"  title="" value="${model.secret!}"/>
         </td>
      </tr>
       <tr>
         <th><@locale code="apps.name"/>：</th>
         <td colspan="3">
            <input type="text"class="form-control"  id="name" name="name"  size="100"  title="" value=""  required=""   />
         </td>
      </tr>
      <tr>
         <th></th>
         <td colspan="3">
            <input type="text" class="form-control"  id="loginUrl" name="loginUrl" size="100"  title="" value=""  required=""   />
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.loginUrl"/>：</th>
         <td>
         	<input type="text" class="form-control"  id="logoutUrl" name="logoutUrl" size="100"  title="" value=""   />
         </td>
         <th><@locale code="apps.logoutType"/></th>
         <td>
         	<select  id="logoutType" name="logoutType" class="form-control  form-select" >
				<option value="0" selected ><@locale code="apps.logoutType.none"/></option>
				<option value="1" ><@locale code="apps.logoutType.back_channel"/></option>
				<option value="2" ><@locale code="apps.logoutType.front_channel"/></option>
			</select>
         </td>
      </tr>
      <tr>
         <th style="width:15%;"><@locale code="apps.protocol"/>：</th>
         <td style="width:35%;" id="app_protocol_control">
         	<span  id="protocol_text" >${model.protocol!}</span>
            <input type="hidden"class="form-control"  id="protocol" name="protocol"  title="" value="${model.protocol!}"/>
         </td>
         <th style="width:15%;"><@locale code="apps.category"/>：</th>
         <td style="width:35%;">
            <input type="text"class="form-control"  id="category" name="category"  title="" value="${model.category!}"/>
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.icon"/>：</th>
         <td>
            <input  type="file" id="iconFile" class="form-control"  name="iconFile"  title="" value=""/>
         </td>
         <th><@locale code="common.text.sortindex"/></th>
         <td>
         	<input  type="text" id="sortIndex" class="form-control"  name="sortIndex"  title="" value="1"  required=""    />
         </td>
      </tr>
      <tr>
         <th><@locale code="apps.vendor"/>：</th>
         <td>
            <input  type="text" class="form-control"  id="vendor" name="vendor"  title="" value=""/>
         </td>
         <th><@locale code="apps.vendor.url"/>：</th>
         <td>
            <input type="text" class="form-control"  id="vendorUrl" name="vendorUrl"  title="" value=""/>
         </td>
      </tr>
      <tr>
      	<th><@locale code="apps.visible"/></th>
         <td>
         	<select  id="visible" name="visible" class="form-control  form-select" >
				<option value="0" ><@locale code="apps.visible.hidden"/></option>
				<option value="1"  selected><@locale code="apps.visible.all"/></option>
				<option value="2" ><@locale code="apps.visible.internet"/></option>
				<option value="3" ><@locale code="apps.visible.intranet"/></option>
			</select>
         </td>
         <th><@locale code="common.text.description"/>：</th>
         <td>
            <input  type="text" class="form-control"  id="description" name="description"  title="" value=""/>
         </td>
      </tr>
      <tr>
		<th><@locale code="apps.isAdapter" />：</th>
		<td>
			<select  id="isAdapter" name="isAdapter"  class="form-control  form-select"  >
				<option value="0"  selected><@locale code="apps.isAdapter.no" /></option>
				<option value="1"><@locale code="apps.isAdapter.yes" /></option>
			</select>
		</td>
		<th><@locale code="apps.adapter" />：</th>
		<td >
			<input type="hidden" class="form-control"   id="adapterId" name="adapterId"  title="" value=""/>
			<input type="text"  class="form-control"    id="adapterName" name="adapterName"  title="" value="" style="width: 80%;float: left;"/>
			<input type="hidden" class="form-control"   id="adapter" name="adapter"  title="" value=""/>
			
			<input class="button btn btn-success mr-3 window" style="float: left;" id="selectAdaptersBtn" type="button" 
         			value="<@locale code="button.text.select"/>" 
		 		    wurl="<@base/>/apps/adapters/selectAdaptersList?protocol=${model.protocol!}"
		 		    wwidth="750"
		 		    wheight="600"
	 		    	target="window">
		</td>
	</tr>
	<tr>
         <th><@locale code="common.text.description"/>：</th>
         <td colspan =3>
            <input  type="text"  class="form-control" id="description" name="description"  title="" value=""/>
         </td>
      </tr>
   </tbody>
</table>
