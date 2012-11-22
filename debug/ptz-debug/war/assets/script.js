onOpened = function() {
	$("#status").fadeOut(300, function() {
		$("#status").html("The channel has been opened. Listening to messages...");
		$("#status").fadeIn();
	});
}
onMessage = function(msg) {
	console.log(msg.data);
	$("#right").append("<div class=\"box blue\"><p>" + msg.data + "</p></div>");
}

onError = function(err) {
	alert(err);
}

onClose = function() {
    alert("Channel closed!");
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
});