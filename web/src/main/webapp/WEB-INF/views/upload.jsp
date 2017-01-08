<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Upload File Request Page</title>
<c:import url='theme.jsp'></c:import>
</head>
<body>
	<div class="container">
		<c:import url='nav.jsp'></c:import>
		<div class="card" style="width: 30em; height:30em; float:left;">
		  <h4 class="card-title">Reading card</h4>
		<form id ="upload" method="POST" action="upload" enctype="multipart/form-data">
			<input  id="file" type="file" name="file" style="display:none;">
			<input id="submit" type="submit" value="Process"  style="display:none;" >		
		 <label for="file"><img id="preview" src="${fileUrl}" class="card-img-top" alt="Click to select image" style="max-width: 30em; max-height:20em;"/></label> 
		  <div class="card-block">
		  
		    <p id="content" class="card-text">${message}</p>
		     <label  class="btn btn-primary" for="submit">Process</label>
		     <label  class="btn btn-primary" for="">Save</label>
		  </div>
		  </form>
		</div>
		
		<div class="card-block" style="width: 30em; float:left;">
		 
		<form action="">
			<div class="form-group row">
				<label for="example-text-input" class="col-2 col-form-label">Name</label>
				<div class="col-10">
					<input class="form-control" type="text" value=""
						id="example-text-input">
				</div>
			</div>
			 <div class="form-group row">
				<label for="example-text-input" class="col-2 col-form-position">Position</label>
				<div class="col-10">
					<input class="form-control" type="text" value=""
						id="example-text-input">
				</div>
			</div>
			<div class="form-group row">
				<label for="example-text-input" class="col-2 col-form-company">Company</label>
				<div class="col-10">
					<input class="form-control" type="text" value=""
						id="example-text-input">
				</div>
			</div>
			<div class="form-group row">
				<label for="example-email-input" class="col-2 col-form-label">Email</label>
				<div class="col-10">
					<input class="form-control" type="email"
						value="" id="example-email-input">
				</div>
			</div>
			<div class="form-group row">
				<label for="example-url-input" class="col-2 col-form-label">Address</label>
				<div class="col-10">
					<input class="form-control" type="url"
						value="" id="example-address-input">
				</div>
			</div>
			<div class="form-group row">
				<label for="example-tel-input" class="col-2 col-form-label">Telephone</label>
				<div class="col-10">
					<input class="form-control" type="tel" value=""
						id="example-tel-input">
				</div>
			</div>			 
			 
			<div class="form-group row">
				<label for="example-color-input" class="col-2 col-form-label">Color</label>
				<div class="col-10">
					<input class="form-control" type="color" value="#563d7c"
						id="example-color-input">
				</div>
			</div>
		</form>
		</div>
	</div>

	<script type="text/javascript">
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#preview').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#file").change(function() {
			readURL(this);
			$('#content').html("");
		});
	</script>
</body>
</html>