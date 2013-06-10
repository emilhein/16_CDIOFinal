<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<style type="text/css">
			#tabs{
				padding:0; 
			    margin:0; 
			    font-family:Arial, Helvetica, sans-serif; 
			    font-size:12px; 
			    color:#FFF; 
			    font-weight:bold;
			}
			#tabs ul{
			list-style:none;
			margin:0;
			padding:0;
			}
			#tabs ul li{ 
			    display:inline; 
			    margin:0; 
			    text-transform:capitalize; 
			} 
			#tabs ul li a{ 
			    padding:5px 16px; 
			    color:#FFF; 
			    background:#E7A272; 
			    float:left; 
			    text-decoration:none; 
			    border:1px solid #D17B40; 
			    border-left:0; 
			    margin:0; 
			    text-transform:capitalize; 
			} 
			#tabs ul li a:hover{ 
			    background:#EAEAEA; 
			    color:#7F9298; 
			    text-decoration:none; 
			    border-bottom:1px solid #EAEAEA; 
			} 
			#tabs ul li a.active{ 
			    background:#EAEAEA; 
			    color:#7F9298; 
			    border-bottom:1px solid #EAEAEA; 
			} 
			#content{    
			    background:#EAEAEA; 
			    clear:both; 
			    font-size:11px; 
			    color:#000; 
			    padding:10px; 
			    font-family:Arial, Helvetica, sans-serif; 
			}
		</style>
	</head>
	<body>
		<h2>You're logged in as: Admin</h2>
		<div id='tabs'>
			<ul>
				<li><a href='Home.jsp'>Home</a>
				<li><a href='#' class='active'>User Administration</a>
				<li><a href='#'>Commodity Administration</a>
				<li><a href='#'>Prescription Administration</a>
				<li><a href='#'>Commodity-batch Administration</a>
				<li><a href='#'>Product-batch Administration</a>
			</ul>
		</div>
		
		<div id='content'>
			My main content goes here! USER!
			bla bla bla
			
			<h2>Printing some status messages for the user administration tab</h2>
			
		</div>
	</body>
	</html>