<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function setAction() {
		document.getElementById('action').value = 'ISBN';
		document.getElementById('productForm').submit();
	}
</script>
<c:set var="disabled" value="true" />
<c:if test="${loggedin.authorityLevel == '99'}">
	<c:set var="disabled" value="false" />
</c:if>
<h2>Product Registration</h2>
<form:form id='productForm' method="post" commandName="productForm"
	action="lookUpProduct.html">
	<c:if
		test="${productForm.errorMessage != null && productForm.errorMessage.length() > 0}">
		<p>
			<font color=red><h2>${productForm.errorMessage}</h2></font>
		</p>
	</c:if>
	<form:hidden id='action' path="action" />
	<div class='center'>
		<table>
			<tr>
				<td><form:label path="productid">product (isbn)</form:label></td>
				<td title="${productForm.productid}"><form:input
						class='StdInput' path="productid" /></td>
				<td><font color=red><form:errors path="productid" /></font></td>
			</tr>
			<tr>
				<td><form:label path="productidtype">product Id Type</form:label></td>
				<td title="${productForm.productidtype}"><form:input
						class='StdInput' disabled='${disabled}' path="productidtype" /></td>
				<td><font color=red><form:errors path="productidtype" /></font></td>
			</tr>
			<tr>
				<td colspan="2"><input class='StdButton' type="submit"
					value="ISBN Search" onclick='setAction()'/></td>
				<td></td>
				<td colspan="2"></td>
			</tr>
		</table>
	</div>
</form:form>
