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
				<div id="popupbody" class="modal-body">
				 
				<video id="video" width="468" height="320" autoplay></video>
				
				<button id="snap">Snap Photo</button>
				<!-- canvas id="canvas" width="468" height="320"></canvas -->
				
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button id="save" type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	
	<script>
	var localStream;

	 
	
	$('#myModal').on('hide.bs.modal', function (e) {
		 
		localStream.getVideoTracks()[0].stop();
		if($("#canvas"))$("#canvas").remove();
	})
	$('#myModal').on('show.bs.modal', function (e) {
		// Grab elements, create settings, etc.
		 var video = document.getElementById('video');
			
		 // Get access to the camera!
		 if(navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
		     // Not adding `{ audio: true }` since we only want video now
		     navigator.mediaDevices.getUserMedia({ video: true }).then(function(stream) {
		         video.src = window.URL.createObjectURL(stream);
		         video.play();
		         localStream = stream ;
		     });
		 }
		
	 
		// Elements for taking the snapshot
		if($("#canvas"))$("#canvas").remove();
		var canvas = document.createElement('canvas');
		canvas.id     = "canvas";
		canvas.width  = 100;
		canvas.height = 80;
		$("#popupbody").append(canvas);
		//var canvas = document.getElementById('canvas');
		var context = canvas.getContext('2d');
		var video = document.getElementById('video');

		// Trigger photo take
		document.getElementById("snap").addEventListener("click", function() {
			context.drawImage(video, 0, 0, 100, 80);
			$("#avata").attr("src",canvas.toDataURL("image/png"));
		});
	});
	$("#save").on("click", function() {
		
		 
		    var canvas  = document.getElementById("canvas");
		    ctx = canvas.getContext('2d') 
		    
		   var dataUrl = canvas.toDataURL("image/png");		    
		   // var dataUrl = canvas.toDataURL("image/jpeg");
	    var xmlHttpReq = false;       
	    if (window.XMLHttpRequest) {
	        ajax = new XMLHttpRequest();
	    }

	    else if (window.ActiveXObject) {
	        ajax = new ActiveXObject("Microsoft.XMLHTTP");
	    }
	   ajax.open('POST', 'ajaxupload', false);
	   ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	   ajax.onreadystatechange = function() {
	        console.log(ajax.responseText);
	    }
	   ajax.send("imageBase64="+dataUrl);
	   
	});
	
	 
	</script>
	
	<!--
		var canvas = document.getElementById('canvas');
		 var imageData = canvas.toDataUrl();

		    $.ajax({
		        url:'ajaxupload',
		        data:{imageBase64: imageData},
		        type: 'post',
		        dataType: 'json',
		        timeout: 10000,
		        async: false,
		        error: function(){
		            alert('error')
		        },
		        success: function(res){
		            if(res.ret==0){
		                console.log("SUCCESS");
		            }else{
		                console.log("FAIL : " + res.msg);
		            }
		        }
		    });
		    
		    -->