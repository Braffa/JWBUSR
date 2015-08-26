<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>Registration Manager</h2>
<form:form method="post" commandName="registerForm" action="attemptoregister.html">
	<div class='center'>
	<table>
	<tr>
		<td class='register'><form:label path="firstname">First Name</form:label></td>
		<td class='register'><form:input class='StdInput' path="firstname" /></td> 
		<td class='register'><font color=red><form:errors path="firstname" /></font></td>
	</tr>
	<tr>
		<td><form:label path="lastname">Last Name</form:label></td>
		<td><form:input class='StdInput' path="lastname" /></td>
		<td><font color=red><form:errors path="lastname" /></font></td>		
	</tr>
	<tr>
		<td><form:label path="email">Email</form:label></td>
		<td><form:input class='StdInput' path="email" /></td>
		<td><font color=red><form:errors path="email" /></font></td>		
	</tr>
	<tr>
		<td><form:label path="telephone">Telephone</form:label></td>
		<td><form:input class='StdInput' path="telephone" /></td>
		<td><font color=red><form:errors path="telephone" /></font></td>		
	</tr>
	<tr>
		<td><form:label path="userId">user Id</form:label></td>
		<td><form:input class='StdInput' path="userId" /></td>
		<td><font color=red><form:errors path="userId" /></font></td>
	</tr>	
	<tr>
		<td><form:label path="password">password</form:label></td>
		<td><form:input class='StdInput' path="password" /></td>
		<td><font color=red><form:errors path="password" /></font></td>		
	</tr>	
	<tr>
		<td colspan="2">
			<input class= 'StdButton' type="submit" value="Register"/>
		</td>
	</tr>
</table>
</div>	
</form:form>
