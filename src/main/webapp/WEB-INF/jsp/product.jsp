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
	action="saveProduct.html">
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
				<td><form:label path="author">Author</form:label></td>
				<td title="${productForm.author}"><form:input class='StdInput' disabled='${disabled}'
						path="author" /></td>
				<td><font color=red><form:errors path="author" /></font></td>
			</tr>
			<tr>
				<td><form:label path="title">Title</form:label></td>
				<td title="${productForm.title}"><form:input class='StdInput' disabled='${disabled}'
						path="title" /></td>
				<td><font color=red><form:errors path="title" /></font></td>
			</tr>
			<tr>
				<td><form:label path="productid">product (isbn)</form:label></td>
				<td title="${productForm.productid}"><form:input
						class='StdInput' path="productid" /></td>
				<td><font color=red><form:errors path="productid" /></font></td>
			</tr>
			<tr>
				<td><form:label path="manufacturer">manufacturer</form:label></td>
				<td title="${productForm.manufacturer}"><form:input
						class='StdInput' disabled='${disabled}' path="manufacturer" /></td>
				<td><font color=red><form:errors path="manufacturer" /></font></td>
			</tr>
			<tr>
				<td><form:label path="productgroup">Product Group</form:label></td>
				<td title="${productForm.productgroup}"><form:input
						class='StdInput' disabled='${disabled}' path="productgroup" /></td>
				<td><font color=red><form:errors path="productgroup" /></font></td>
			</tr>
			<tr>
				<td><form:label path="productidtype">product Id Type</form:label></td>
				<td title="${productForm.productidtype}"><form:input
						class='StdInput' disabled='${disabled}' path="productidtype" /></td>
				<td><font color=red><form:errors path="productidtype" /></font></td>
			</tr>
			<tr>
				<td><form:label path="productIndex">product Index</form:label></td>
				<td title="${productForm.productIndex}"><form:input
						class='StdInput' disabled='${disabled}' path="productIndex" /></td>
				<td><font color=red><form:errors path="productIndex" /></font></td>
			</tr>
			<tr>
				<td><form:label path="imageURL">image URL</form:label></td>
				<td title="${productForm.imageURL}"><form:input
						class='StdInput' disabled='${disabled}' path="imageURL" /></td>
				<td><font color=red><form:errors path="imageURL" /></font></td>
			</tr>
			<tr>
				<td><form:label path="imageLargeURL">Large Image URL</form:label></td>
				<td title="${productForm.imageLargeURL}"><form:input
						class='StdInput' disabled='${disabled}' path="imageLargeURL" /></td>
				<td><font color=red><form:errors path="imageLargeURL" /></font></td>
			</tr>
			<tr>
				<td><form:label path="source">source</form:label></td>
				<td title="${productForm.source}"><form:input class='StdInput' disabled='${disabled}'
						path="source" /></td>
				<td><font color=red><form:errors path="source" /></font></td>
			</tr>
			<tr>
				<td><form:label path="sourceid">source Id</form:label></td>
				<td title="${productForm.sourceid}"><form:input
						class='StdInput' disabled='${disabled}' path="sourceid" /></td>
				<td><font color=red><form:errors path="sourceid" /></font></td>
			</tr>
			<tr>
				<td colspan="2"><input class='StdButton' type="submit"
					value="Save" /></td>
				<td></td>
				<td colspan="2"><input class='StdButton' type="button"
					value="ISBN Search" onclick='setAction()' /></td>
			</tr>
		</table>
	</div>
</form:form>
