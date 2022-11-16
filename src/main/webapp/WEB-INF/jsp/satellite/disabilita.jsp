<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
		<!-- Custom styles per le features di bootstrap 'Columns with icons' -->
	   <link href="${pageContext.request.contextPath}/assets/css/features.css" rel="stylesheet" type="text/css">
	   
	   <title>DISABILITA Satelliti</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   		
	   		
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
	    	
			 <div class="container"> 
			     <div class="p-5 mb-4 bg-light rounded-3">
				      <div class="container-fluid py-5">
				        <h1 class="display-5 fw-bold">Sta per essere applicata la procedura di emergenza. Si è sicuri di voler procedere?</h1>
				        <h3>Numero Voci totali presenti nel sistema:</h3> <h3> ${listAll_attr.size()}</h3>
				        <h3>Numero Voci che verranno modificate in seguito alla procedura:</h3> <h3> ${satellitiDisabilitati_attr.size()}</h3>
				        <form:form modelAttribute="update_satellite_attr" method="post" action="${pageContext.request.contextPath}/satellite/confermaDisabilita" class="row g-3" novalidate="novalidate">
				      		<button type="submit" name="submit" value="submit" id="submit" class="btn btn-danger btn-lg" style="width: 200px">DISABILITA TUTTI</button>
				      	</form:form>
				      </div>
			    </div>  
			  </div>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>