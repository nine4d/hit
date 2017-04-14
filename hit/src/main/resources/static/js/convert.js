var diff = [0.003, -0.002]
$(document).ready(function() {
    $.ajax({
        url: 'js/skorea_submunicipalities_geo.json',
        type: 'get',
        success: function (data) {

        	data.features.forEach( function (item, idx) {
        		var locs = [];
        		var multi = [];
        		
        		if (item.geometry.type == "Polygon") {
        			var coords = item.geometry.coordinates; 
        			for (var i = 0; i < coords.length; i++) { 
        				for (var j = 0; j < coords[i].length; j++) { 
        					locs.push({
	                        	lon : coords[i][j][0] + diff[1],
	                        	lat : coords[i][j][1] + diff[0]        						
        					});
        				}
        			}
        		} else if (item.geometry.type == "MultiPolygon") {
        			var coords = item.geometry.coordinates; 
        			for (var i = 0; i < coords.length; i++) { 
        				for (var j = 0; j < coords[i].length; j++) { 
		                    var path = [];
		                    for (var k = 0; k < coords[i][j].length; k++) { 
		                        path.push({
		                        	lon : coords[i][j][k][0] + diff[1],
		                        	lat : coords[i][j][k][1] + diff[0]
		                        }); 
		                    } 
		                    multi.push(path); 
    	                } 
    				}
        		}
        		
        		var datatmp = [{
        				type : item.geometry.type,
        				code : item.properties.code,
        				name : item.properties.name,
        				locations : locs,
        				multiLocations : multi
        		}];
        		
        		var request = {
        				draw : 1,
        				action : 'create',
        				data : datatmp
        		}
        		
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
        }
    });	
});

