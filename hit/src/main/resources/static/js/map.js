var map;
var polygon=null;
var table;
var columns = [ 
	{data : "id"},
	{data : "code"},	
	{data : "name"},
	{data : "type"},
	{data : "locations", 
		render : function(data, type, row) {
    		return data.length;
    	}
	},
	{data : "multiLocations",
		render : function(data, type, row) {
    		return data.length;
    	} 
	}
	];


function initMap() {
	map = new google.maps.Map(document.getElementById('map'), {
		center : {
			lat : 37.574625,
			lng : 126.978733
		},
		zoom : 13
	});
	
/*	  // Define the LatLng coordinates for the polygon's path.
	  var triangleCoords = [
	    {lat: 25.774, lng: -80.190},
	    {lat: 18.466, lng: -66.118},
	    {lat: 32.321, lng: -64.757},
	    {lat: 25.774, lng: -80.190}
	  ];

	  // Construct the polygon.
	  polygon = new google.maps.Polygon({
	    paths: triangleCoords,
	    strokeColor: '#FF0000',
	    strokeOpacity: 0.8,
	    strokeWeight: 2,
	    fillColor: '#FF0000',
	    fillOpacity: 0.35,
	    draggable: true,
	    editable: true
	  });
	  polygon.setMap(map);	*/
}

var ViewEvent = {
	btnEvent : function() {
		$("#btnNew").click(function(){
			var request = {
		    		draw : 1,
		    		action : 'create',
		    		data : [{ 
		    			type : 'circle', 
		    			name: 'ㄹㄹㄹ' ,
		    			locations: [
		    				{lat : 1, lon : 1, radius :10}
		    			]

		    		}]
		    };
		    
		    $.ajax({
		        url: 'coordinates/control',
		        type: 'post',
		        contentType: "application/json; charset=utf-8",
		        dataType: 'json',
		        success: function (data) {
		        	console.log(data); 
		        },
		        data: JSON.stringify(request)
		    });			
		});
	},
	listClickEvent : function() {
		var data = table.rows(".selected").data()[0];
		if (data == null)
			return;
		
		if (polygon != null)
			polygon.setMap(null);
		
		if (data.type == "polygon") {
			var path = [];
			data.locations.forEach(function(tmp, idx) {
				path.push({
					lat : tmp.lat,
					lng : tmp.lon
				});
			});
			
			polygon = new google.maps.Polygon({
				  map: map,
				  paths: path,
				  strokeColor: '#FF0000',
				  strokeOpacity: 0.8,
				  strokeWeight: 2,
				  fillColor: '#FF0000',
				  fillOpacity: 0.35,
				  editable: true,
				  geodesic: true
				});
			console.debug(polygon);
		} else if (data.type == "multipolygon") {
			
		}
	},
	loadingImgStart : function(){
		var loading = '<div id="loadingImg" style="position: absolute; top: 30%; left: 50%; z-index:9999;">';
		loading += '<img src="images/loadingImg.gif"></div>';
		$("body").append(loading);
	},
	
	loadingImgEnd : function(){
		$("#loadingImg").remove();
	},
	
	showDimmed : function(){
		$("body").append("<div class=\"dimmed\"></div>");
	},

	removeDimmed : function(){
		$(".dimmed").remove();
	},	
};


$(document).ready(function() {
	ViewEvent.showDimmed();
	ViewEvent.loadingImgStart();
    $.ajax({
        url: 'coordinates/getDataTables',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        success: function (data) {
        	
        	table = $('#example').DataTable({
        		"columns" : columns,
        		"data" : data.data
        	});

        	ViewEvent.loadingImgEnd();        	
        	ViewEvent.removeDimmed();        	
			$('#example tbody').on( 'click', 'tr', function (e) {
				if ( $(this).hasClass('selected') ) {
					$(this).removeClass('selected');
				} else {
					table.$('tr.selected').removeClass('selected');
					$(this).addClass('selected');
					ViewEvent.listClickEvent();
			    }
			});
        },
        error : function() {
        	ViewEvent.loadingImgEnd();        	
        	ViewEvent.removeDimmed();          	
        }
    });	

	ViewEvent.btnEvent();
	

});
