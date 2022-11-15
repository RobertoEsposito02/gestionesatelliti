<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>

	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	
	   <title>Eliminazione Elemento</title>
	   
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class='card'>
					    <div class='card-header'>
					        <h5>Visualizza dettaglio elemento da eliminare</h5>
					    </div>
					    
					
					    <div class='card-body'>
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Id:</dt>
							  <dd class="col-sm-9">${delete_satellite_attr.id}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Denominazione:</dt>
							  <dd class="col-sm-9">${delete_satellite_attr.denominazione}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Codice:</dt>
							  <dd class="col-sm-9">${delete_satellite_attr.codice}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data di Lancio:</dt>
							  <dd class="col-sm-9"><fmt:formatDate type="date" value = "${delete_satellite_attr.dataLancio}" /></dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data di Rientro:</dt>
							  <dd class="col-sm-9"><fmt:formatDate type="date" value = "${delete_satellite_attr.dataRientro}" /></dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Stato :</dt>
							  <dd class="col-sm-9">${delete_satellite_attr.stato}</dd>
					    	</dl>
					    	
					    	
					    </div>
					    <!-- end card body -->
					    
					    <div class='card-footer'>
					    	<div class="row">					
						    	<div class="cl-2">
							        <form action="${pageContext.request.contextPath}/satellite/saveDelete" method="post">
							        	<button type="submit" name="submit" value="submit" id="submit" class="btn btn-danger">Conferma</button>
							        	<input type="hidden" value="${delete_satellite_attr.id}" name="idSatellite" >
							        </form>
						        </div>
						        <div class="cl-2">
							        <a href="${pageContext.request.contextPath}/satellite" class='btn btn-outline-secondary' style='width:80px'>
							            <i class='fa fa-chevron-left'></i> Back
							        </a>
						        </div>
					        </div>
					    </div>
					<!-- end card -->
					</div>	
			  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>