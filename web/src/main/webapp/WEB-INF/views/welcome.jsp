<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Spring 4 MVC Hello World Example with Maven Eclipse</title>

<link rel='stylesheet' href='<c:url value="/resources/css/style.css" />'
	type='text/css' media='all' />
<link rel='stylesheet'
	href='<c:url value="/resources/bootstrap-4.0.0/css/bootstrap.min.css" />'
	type='text/css' media='all' />

<script type="text/javascript"
	src='<c:url value="/resources/jquery-3.1.1/jquery.min.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/resources/jquery-3.1.1/jquery.slim.min.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/resources/tether-1.4.0/tether.min.js"/>'></script>

<script type="text/javascript"
	src='<c:url value="/resources/bootstrap-4.0.0/js/bootstrap.min.js"/>'></script>
</head>
<body>
<div class="container">
	<nav class="navbar navbar-toggleable-md navbar-light bg-primary">
		<button class="navbar-toggler navbar-toggler-right" type="button"
			data-toggle="collapse" data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<a class="navbar-brand" href="#">Navbar</a>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="#">Home
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
				<li class="nav-item"><a class="nav-link disabled" href="#">Disabled</a>
				</li>
			</ul>
			<form class="form-inline my-2 my-lg-0">
				<input class="form-control mr-sm-2" type="text" placeholder="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
			</form>
		</div>
	</nav>

	<h2>Hello World, Spring MVC</h2>

	<p>Welcome, ${name}</p>

	<!-- Button trigger modal -->
	<a type="button" class="btn btn-primary" data-toggle="modal"
		   href="#myModal" >Launch demo modal</a>
	 
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">...</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>