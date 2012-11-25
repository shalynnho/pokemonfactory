var sources = new Array();
var showAll = true;
var showing = "showall";


onOpened = function() {
	$("#status").fadeOut(300, function() {
		$("#status").html("The channel has been opened. Listening to messages...");
		$("#status").fadeIn();
	});
}
onMessage = function(msg) {
	console.log(msg.data);
	displayMessage(msg.data);
	$('html, body').animate({scrollTop: $(document).height()-$(window).height()}, 0);
}

onError = function(err) {
	alert(err);
}

onClose = function() {
    alert("Channel closed!");
}

function displayMessage(msg) {
	var source = msg.match(/\[(.*)\]/i)[1];
	var sourceWithoutID = source.replace(/\d/i, "").toLowerCase();
	msg = msg.replace(/\[(.*)\]/i, "");
	
	if(showing != "showall" && showing != "show" + sourceWithoutID) { 
		display = " style=\"display: none;\"";
	} else {
		display = "";
	}
	$("#messages").append("<div class=\"message " + sourceWithoutID + "\"" + display + ">" +
			"<div class=\"source_wrapper " + sourceWithoutID + "\"><b>" + source + "</b></div>" +
			msg +
		"</div>");
	
	if($.inArray(sourceWithoutID, sources) == -1) {
		sources.push(sourceWithoutID);
		$("#buttons").append("<a class=\"button\" id=\"show" + sourceWithoutID + "\" href=\"javascript:void(0)\">" + sourceWithoutID + "</a>");
		$("#show" + sourceWithoutID).click(function() {
			showing = $(this).attr('id');
			if(showAll) {
				$(".message:not(."+sourceWithoutID+")").hide(0);
			} else {
				$(".message").show(0);
				$(".message:not(."+sourceWithoutID+")").hide(0);
			}
			showAll = false;
		});
	}
}

$(document).ready(function() {
	$("#right").css("min-height", $(window).height());
	
	//start a channel
	if(token != "") {
		channel = new goog.appengine.Channel(token);
	    socket = channel.open();
	    socket.onopen = onOpened;
	    socket.onmessage = onMessage;
	    socket.onerror = onError;
	    socket.onclose = onClose;
	}
	
	$("#showall").click(function() {
		showAll = true;
		showing = "showall";
		$(".message").show(0);
	});
});