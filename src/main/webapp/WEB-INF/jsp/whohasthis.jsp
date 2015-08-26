<!-- whohasthis.jsp -->

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="features">
	<div class="product_box">
		<div class="product_icon">
			<img src="${whoHasThisForm.product.imageURL}">
		</div>
		<div class="product_content">
			<div class="user_product_details">
				<p title="${whoHasThisForm.product.author}">
					<c:out value="${whoHasThisForm.product.author}" />
				</p>
				<p title="${whoHasThisForm.product.title}">
					<c:out value="${whoHasThisForm.product.title}" />
				</p>
				<p title="${whoHasThisForm.product.productid}">
					<c:out value="${whoHasThisForm.product.productid}" />
				</p>
			</div>
		</div>
	</div>
	<h2>${whoHasThisForm.header}</h2>
	<div class="product_box">
		<div class="product_content">
			<div class="user_product_details">
				<table>
					<tr>
						<th>User Id</th>
						<th>Email Address</th>
						<th>Date</th>
						<c:forEach items="${whoHasThisForm.lOfRegisteredUserToProduct}"
							var="registeredUserToProduct" varStatus="user">
							<tr>
								<td>${registeredUserToProduct.userId}</td>
								<td>${registeredUserToProduct.email}</td>
								<td><fmt:formatDate type="both" dateStyle="short"
										timeStyle="short" value="${registeredUserToProduct.crDate}" />
								</td>
							</tr>
						</c:forEach>
				</table>
			</div>
		</div>
	</div>

</div>
<div class="clear"></div>
