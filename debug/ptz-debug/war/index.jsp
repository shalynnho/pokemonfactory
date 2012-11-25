<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="com.google.appengine.api.channel.ChannelService" %>
<%@ page import="com.google.appengine.api.channel.ChannelServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Query.Filter" %>
<%@ page import="com.google.appengine.api.datastore.Query.FilterOperator" %>
<%@ page import="com.google.appengine.api.datastore.Query.FilterPredicate" %>
<%@ page import="com.google.appengine.api.datastore.Query.SortDirection" %>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>

<%
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
ChannelService channelService = ChannelServiceFactory.getChannelService();

String targetConsole = request.getParameter("console");
boolean channelOpened = false;
String token = "";
String clientID = "";
Filter consoleIDFilter = null;

if(targetConsole != null) {
 // check if console exists
    consoleIDFilter = new FilterPredicate("consoleID", FilterOperator.EQUAL, targetConsole);
    Query q = new Query("console").setFilter(consoleIDFilter);
    PreparedQuery pq = datastore.prepare(q);
    List<Entity> entities = pq.asList(FetchOptions.Builder.withDefaults());
    
    if(entities.size() > 0) {
		clientID = targetConsole + new Date().getTime() + (int)(Math.random()*100);
		token = channelService.createChannel(clientID);
		channelOpened = true;
		
		Entity channel = new Entity("channel");
		channel.setProperty("console", targetConsole);
		channel.setProperty("clientID", clientID);
		datastore.put(channel);
    }
}
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>ptz-debug panel</title>
	<link href='http://fonts.googleapis.com/css?family=Droid+Sans:regular,bold' rel='stylesheet' type='text/css' />
	<link href="assets/style.css" rel="stylesheet" type="text/css" />
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
	<script src="/_ah/channel/jsapi" type="text/javascript"></script>
	<script type="text/javascript">token = "<%=token%>";</script>
	<script src="assets/script.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			<%
				if(targetConsole != null) {
					Query q = new Query("message").setFilter(consoleIDFilter)/*.addSort("time", SortDirection.ASCENDING)*/;
					PreparedQuery pq = datastore.prepare(q);
					for(Entity result : pq.asIterable()) {
					    %>
					    displayMessage("<%=result.getProperty("message")%>");
					    <%
					}
				}
			
			%>
		});
	</script>
</head>
<body>

		<div id="left">
			<div id="logo">
			ptz:<b>debug</b>
			</div>
			<div id="buttons">
				<a class="button" id="showall" href="javascript:void(0)"><b>show all</b></a>
			</div>
			<div id="footer">
				&copy; 2012 Peter Zhang. All Rights Reserved.
			</div>
		</div>
		
		<div id="right">
		
		<% if(!channelOpened) { %>
			<% if(targetConsole != null) { %>
				<div class="box blue">
					<h2>We're sorry, we can't find your target console.</h2>
					<p>Your consoleID <%=targetConsole%> was invalid. Please try again.</p>
				</div>
			<% } %>
			<div class="box blue">
				<h2>Choose your console</h2>
				<form method="get">
					<input type="text" name="console" />
				</form>
			</div>
		<% } else { %>
			<div class="box blue">
				<h2>Target console: <b><%=targetConsole%></b></h2>
				<p id="status">Sit tight while we organize your debug messages...</p>
			</div>
			<div id="messages" class="box blue">
			</div>
		<% } %>
		</div>
</body>
</html>