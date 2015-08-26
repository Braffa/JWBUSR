<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h2>Search Catalogue</h2>
	<form:form method="post" commandName="searchCatalogForm" action="searchCatalog.html">
	<div class='center'>
	<table>
		<tr>
			<td><form:label path="author">Author</form:label></td>
			<td><font color=red><form:errors path="author" /></font></td>
			<td><form:input path="author" CLASS='StdInput' size='20' maxlength='20' /></td>
		</tr>
		<tr>
			<td><form:label path="title">Title</form:label></td>
			<td><font color=red><form:errors path="title" /></font></td>
			<td><form:input path="title" CLASS='StdInput' size='20' maxlength='20' /></td>
		</tr>			
		<tr>
			<td><form:label path="productid">Product Id (isbn)</form:label></td>
			<td><font color=red><form:errors path="productid" /></font></td>
			<td><form:input path="productid" CLASS='StdInput' size='20' maxlength='20' /></td>
		</tr>
		<tr>
			<td><form:label path="manufacturer">Manufacturer</form:label></td>
			<td><font color=red><form:errors path="manufacturer" /></font></td>
			<td><form:input path="manufacturer" CLASS='StdInput' size='20' maxlength='20' /></td>
		</tr>
		<tr>		
			<td></td>
			<td><input class= 'StdButton' type="submit" value="Search"/></td>	
			<td></td>
		</tr>			
	</table>
	</div>
	</form:form>

