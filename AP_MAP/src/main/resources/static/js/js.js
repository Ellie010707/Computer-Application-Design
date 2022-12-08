

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(36.6710242, 127.9326146), // 지도의 중심좌표
        level: 10 // 지도의 확대 레벨
    };  
    
// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 


// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
if (navigator.geolocation) {
   
   // GeoLocation을 이용해서 접속 위치를 얻어옵니다
   navigator.geolocation.getCurrentPosition(function(position) {
      var lat = position.coords.latitude; // 위도
      var lon = position.coords.longitude; // 경도
        
        var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
            message = '<div style="padding:5px;">내 위치</div>'; // 인포윈도우에 표시될 내용입니다
        
        // 마커와 인포윈도우를 표시합니다
        displayMarker(locPosition, message);
      });
    
} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
    
    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
        message = 'geolocation을 사용할수 없어요..'
        
    displayMarker(locPosition, message);
}



var markerwindow = false;
function displayMarker(locPosition, data) {
	
	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({  
		map: map, 
		position: locPosition
	}); 
	// 지도 중심좌표를 접속위치로 변경합니다
	 map.setCenter(locPosition);   
	 
	 // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
		var iwContent = data, // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		iwRemoveable = false
		if (data.indexOf('내 위치') >= 0) {
	    	iwRemoveable = true // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
		} 
	// 인포윈도우를 생성합니다
	var infowindow = new kakao.maps.InfoWindow({
	    content : iwContent,
	    removable : iwRemoveable
	});
	
	// 마커에 클릭이벤트를 등록합니다
	kakao.maps.event.addListener(marker, 'click', function() {
		
		if (markerwindow) {
			markerwindow.close();
		}
		  markerwindow = infowindow;
		  
	      // 마커 위에 인포윈도우를 표시합니다
	      infowindow.open(map, marker);  
	});   
}

function infoClose() {
	markerwindow.close();
}


// list 클릭할 경우 위치 이동
var markerlist = false;
var Position = null;

function listClick(nm, lat, logt) {
	if (lat == null) {
		navigator.geolocation.getCurrentPosition(function(position) {
		      var xlat = position.coords.latitude; // 위도
		      var xlon = position.coords.longitude; // 경도
		});
		Position = new kakao.maps.LatLng(xlat, xlon);
	} else {
		Position = new kakao.maps.LatLng(lat, logt);
	}
	var marker = new kakao.maps.Marker({  
			map: map, 
			position: Position
		}); 
	
	var listwindow = new kakao.maps.InfoWindow({
		content : "<div class='Winfo2'>"+nm+"</div>" 
	});      
	
			// marker 위치로 이동
	       map.setCenter(Position);
	       map.setLevel(7);
	        // 다른 list 클릭 시 켜져있는 window 닫기
	       if (markerlist) {
	       		markerlist.close();
	       	}
			
			// list 클릭시 marker 에 window 표시	       	
	       markerlist = listwindow; 
	       listwindow.open(map, marker);
	       marker.setMap(null);
	       
}
	
	// list에 마우스 올리면 이름 표시
function listover(nm, lat, logt) {
	// 검색 결과값 없을경우 내위치로 좌표이동
	if (lat == null) {
		navigator.geolocation.getCurrentPosition(function(position) {
		      var xlat = position.coords.latitude; // 위도
		      var xlon = position.coords.longitude; // 경도
		});
		Position = new kakao.maps.LatLng(xlat, xlon);
	} else {
		Position = new kakao.maps.LatLng(lat, logt);
	}
	
	var marker = new kakao.maps.Marker({  
			map: map, 
			position: Position
	}); 

	var listwindow = new kakao.maps.InfoWindow({
		content : "<div class='Winfo2'>"+nm+"</div>" 
	});      
	
	// list 클릭시 marker 에 window 표시	       	
	   markerlist = listwindow; 
	   listwindow.open(map, marker);
	   marker.setMap(null);
	   map.setCenter(Position);
}

	function listout() {
		markerlist.close();
		map.setCenter(Position);

	}
	

	function ratingToPercent(sc) {
		   const score = sc * 20;
		   return score + 1.5;
		}

	// marker 클릭시 나오는 window에 입력되는 data 값 // displayMarker 실행시키면서 data 넘기기		
	if(JpageL.length >= 0){ 
		for(let i = 0; i < JpageL.length; i++){
			var NM = JpageL[i].BIZPLC_NM;
			var AD = JpageL[i].REFINE_ROADNM_ADDR;
			var Tel = JpageL[i].LOCPLC_FACLT_TELNO;
			var CD = JpageL[i].ROADNM_ZIP_CD;
			if (AD == null) {
				AD = "";
			}
			if (Tel == null) {
				Tel = "";
			}
			var avg = null;
			var count = null;
			if (Jreview[i] != null) {
				avg = Jreview[i].S_avg;
				S_count = Jreview[i].S_count;
				RV_count = Jreview[i].RV_count;
				var ratingRounded = ratingToPercent(Jreview[i].S_avg)+'%';
			} else {
				avg = 0.0;
				count = 0;
				S_count = 0;
			}
			
			lat = JpageL[i].REFINE_WGS84_LAT;
			logt = JpageL[i].REFINE_WGS84_LOGT;
			data = '<div style="padding:10px;" class="Winfo">' 
				+ '<a href="http://localhost:8000/star/' + AD + '" id="winfo">'
				+ NM + '</a>'
				+ '<img class="btn_close" onclick="infoClose()" src="/map/img/close.png";>'
				+ '<div>'
				+ '<span id=Avg>' + avg.toFixed(1) + '</span>' + " "
				+ '<span class="RatingStar">'
				+ 	'<span class="RatingScore">'
				+ 		'<span class="outer-star"><span class="inner-star" style= "width:'+ ratingRounded+'"'+'"></span></span>'
				+ 	'</span>'
				+ '</span>'
				+ '<span style="color:gray">'+ ' (' + S_count + '건)' 
				+ ' | 리뷰 ' + RV_count + '</span><br><br>'
				+ AD + '<br>'
				+ '<span style="color:gray">(우)' + CD + '</span><br>'
				+ '<span style="color:green">' + Tel + '</span>'
				+ '</div>';
			var locPosition = new kakao.maps.LatLng(lat, logt);
			displayMarker(locPosition, data);
			

		}
	} 
	
	// 메뉴bar		
	$(document).ready(function () {
        $("#sidebar").mCustomScrollbar({
            theme: "minimal"
        });

        $('#dismiss, .overlay').on('click', function () {
            $('#sidebar').removeClass('active');
            $('.overlay').removeClass('active');
        });

        $('#sidebarCollapse').on('click', function () {
            $('#sidebar').addClass('active');
            $('.overlay').addClass('active');
            $('.collapse.in').toggleClass('in');
            $('a[aria-expanded=true]').attr('aria-expanded', 'false');
        });
    });
