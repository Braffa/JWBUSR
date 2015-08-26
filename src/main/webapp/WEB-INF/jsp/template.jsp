<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link REL="StyleSheet" TYPE="text/css" HREF="css/style.css">

<style>

</style>

<script src='javascript/generic.js'></script>
<script>
</script>


<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
<div id="main_container">
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="menu" />
	<div class="center_content">
		<tiles:insertAttribute name="body" />
	</div>
	<tiles:insertAttribute name="footer" />
</div>
</body>
</html>
