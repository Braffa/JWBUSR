<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="currentPage" value="" />
<c:choose>
	<c:when test="${productForm != null && productForm.currentPage == 'newProduct'}">
		<c:set var="currentPage" value="product" />
	</c:when>
	<c:when test="${catalogForm != null && catalogForm.currentPage == 'fullCatalogue'}">
		<c:set var="currentPage" value="fullCatalogue" />
	</c:when>
	<c:when test="${catalogForm != null && catalogForm.currentPage == 'myCatalogue'}">
		<c:set var="currentPage" value="myCatalogue" />
	</c:when>
	<c:when test="${searchCatalogForm != null && searchCatalogForm.currentPage == 'searchCatalogue'}">
		<c:set var="currentPage" value="searchResults" />
	</c:when>	
	<c:when test="${catalogForm != null && catalogForm.currentPage == 'searchResults'}">
		<c:set var="currentPage" value="searchResults" />
	</c:when>	
	<c:when test="${loginForm != null && loginForm.currentPage == 'home'}">
		<c:set var="currentPage" value="home" />
	</c:when>
	<c:when test="${registerForm != null && registerForm.currentPage == 'register'}">
		<c:set var="currentPage" value="register" />
	</c:when>
	<c:when test="${registrationForm != null && registrationForm.currentPage == 'registeredUsers'}">
		<c:set var="currentPage" value="registeredUsers" />
	</c:when>	
	<c:otherwise>
		<c:set var="currentPage" value="" />
	</c:otherwise>
</c:choose>

<div class='menu'>
	<ul>
		<c:choose>
			<c:when test="${currentPage == 'home'}">
				<li class='selected'><a href="gotoPage.html?gotoPage=home">home</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="gotoPage.html?gotoPage=home">home</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${currentPage == 'searchResults'}">
				<li class='selected'><a href="gotoPage.html?gotoPage=searchCatalogue">Search Catalogue</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="gotoPage.html?gotoPage=searchCatalogue">Search Catalogue</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${currentPage == 'fullCatalogue'}">
				<li class='selected'><a href="gotoPage.html?gotoPage=catalogue">Catalogue</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="gotoPage.html?gotoPage=catalogue">Catalogue</a></li>
			</c:otherwise>
		</c:choose>
		<c:if test="${loggedin.authorityLevel != null}">
			<c:choose>
				<c:when test="${currentPage == 'myCatalogue'}">
					<li class='selected'><a href="gotoPage.html?gotoPage=myCatalog">My Catalogue</a></li>	
				</c:when>
				<c:otherwise>
					<li><a href="gotoPage.html?gotoPage=myCatalog">My Catalogue</a></li>	
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${currentPage == 'product'}">
					<li class='selected'><a href="gotoPage.html?gotoPage=addProduct">Add product</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="gotoPage.html?gotoPage=addProduct">Add product</a></li>
				</c:otherwise>
			</c:choose>
		</c:if>	
		<c:choose>
			<c:when test="${currentPage == 'register'}">
				<li class='selected'><a href="gotoPage.html?gotoPage=register">Register</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="gotoPage.html?gotoPage=register">Register</a></li>
			</c:otherwise>
		</c:choose>
		<c:if test="${loggedin.authorityLevel == '99'}">
			<c:choose>
				<c:when test="${currentPage == 'registeredUsers'}">
					<li class='selected'><a href="gotoPage.html?gotoPage=showRegisteredUsers">List Users</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="gotoPage.html?gotoPage=showRegisteredUsers">List Users</a></li>
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${loggedin != null && loggedin.userId != null}">
			<li><a href="gotoPage.html?gotoPage=signOut">Sign Out</a></li>
		</c:if>
	</ul>
</div>
	<c:if test="${loggedin != null && loggedin.userId != null}">
		<div class='signedIn'>Signed in as ${loggedin.userId}</div>
	</c:if>

