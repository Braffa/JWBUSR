<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h2>Registered Users</h2>
<div class='center'>
	<table>
		<tr>
			<th>FirstName</th>
			<th>Last Name</th>
			<th>Email</th>
			<th>Phone</th>
		</tr>
		<c:forEach items="${registrationForm.registeredDetails}"
			var="registeredUser" varStatus="status">
			<tr>
				<td><form:input disabled='true'
						path="registrationForm.registeredDetails[${status.index}].firstname"
						CLASS='StdInput' size='20' maxlength='20' /></td>
				<td><form:input disabled='true'
						path="registrationForm.registeredDetails[${status.index}].lastname"
						CLASS='StdInput' size='20' maxlength='20' /></td>
				<td><form:input disabled='true'
						path="registrationForm.registeredDetails[${status.index}].email"
						CLASS='StdInput' size='20' maxlength='20' /></td>
				<td><form:input disabled='true'
						path="registrationForm.registeredDetails[${status.index}].telephone"
						CLASS='StdInput' size='20' maxlength='20' /></td>
			</tr>
		</c:forEach>
	</table>
</div>	
