<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Welcome card reader cloud</title>
<c:import url='theme.jsp'></c:import>   

</head>
<body>
<div class="container">
 <c:import url='nav.jsp'></c:import>   

	<h2>This is home site</h2>

	<p>Welcome, ${name}</p>

	<!-- Button trigger modal -->
	<a type="button" class="btn btn-primary" data-toggle="modal"
		   href="#myModal" >Show information</a>
		    
	  <c:import url='popup.jsp'></c:import>   
	 
	
</div>
</body>
</html>