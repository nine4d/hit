var map;
function initMap() {
	map = new google.maps.Map(document.getElementById('map'), {
		center : {
			lat : 37.574625,
			lng : 126.978733
		},
		zoom : 13
	});
	
	google.maps.event.addListener(map, "click", function (e) {
		$('div#address').html(''); 
	    var latLng = e.latLng;
	    
	    $.ajax({
	        url: 'coordinates/getAddress',
	        type: 'get',
	        success: function (data) {
	        	if (data.resultCode == 0)
	        		$('div#address').html(data.data[0]); 
	        },
	        data: {x : latLng.lng(), y : latLng.lat() }
	    });	
	});
}

var ViewEvent = {
		
};

$(document).ready(function() {
	
});