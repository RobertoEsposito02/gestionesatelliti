<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
		<style>
			.error_field {
				color: red;
			}
		</style>

<title>Inserisci Nuovo Elemento</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<%-- se l'attributo in request ha errori --%>
					<spring:hasBindErrors  name="satellite_insert_attr">
						<%-- alert errori --%>
						<div class="alert alert-danger " role="alert">
							Attenzione!! Sono presenti errori di validazione
						</div>
					</spring:hasBindErrors>
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					<div class="alert alert-danger alert-dismissible fade show d-none" role="alert">
					  Esempio di operazione fallita!
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					<div class="alert alert-info alert-dismissible fade show d-none" role="alert">
					  Aggiungere d-none nelle class per non far apparire
					   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Inserisci nuovo elemento</h5> 
				    </div>
				    <div class='card-body'>
		
							<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		
		
							<form:form modelAttribute="satellite_insert_attr" method="post" action="save" class="row g-3" novalidate="novalidate">
							
							
								<div class="col-md-6">
									<label for="denominazione" class="form-label">Denominazione <span class="text-danger">*</span></label>
									<spring:bind path="denominazione">
										<input type="text" name="denominazione" id="denominazione" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire la denominazione" value="${satellite_insert_attr.denominazione }" required>
									</spring:bind>
									<form:errors  path="denominazione" cssClass="error_field" />
								</div>
								
								<div class="col-md-6">
									<label for="codice" class="form-label">Codice <span class="text-danger">*</span></label>
									<spring:bind path="codice">
										<input type="text" name="codice" id="codice" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire il codice" value="${satellite_insert_attr.codice }" required>
									</spring:bind>
									<form:errors  path="codice" cssClass="error_field" />
								</div>
							
								<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate" type='date' value='${satellite_insert_attr.dataLancio}' />
								<div class="col-md-3">
									<label for="dataLancio" class="form-label">Data di Lancio</label>
                        			<spring:bind path="dataLancio">
	                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataLancio" type="date" placeholder="dd/MM/yy"
	                            		title="formato : gg/mm/aaaa"  name="dataLancio" required 
	                            		value="${parsedDate}" >
		                            </spring:bind>
	                            	<form:errors  path="dataLancio" cssClass="error_field" />
								</div>
								
								<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate" type='date' value='${satellite_insert_attr.dataRientro}' />
								<div class="col-md-3">
									<label for="dataRientro" class="form-label">Data di Rientro </label>
                        			<spring:bind path="dataRientro">
	                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataRientro" type="date" placeholder="dd/MM/yy"
	                            		title="formato : gg/mm/aaaa"  name="dataRientro" required 
	                            		value="${parsedDate}" >
		                            </spring:bind>
	                            	<form:errors  path="dataRientro" cssClass="error_field" />
								</div>
								
								<div class="col-md-3">
									<label for="stato" class="form-label">Stato </label>
								    <spring:bind path="stato">
									    <select class="form-select ${status.error ? 'is-invalid' : ''}" id="stato" name="stato" required>
									    	<option value="" selected> - Selezionare - </option>
									    	<option value="IN_MOVIMENTO" ${satellite_insert_attr.stato == 'IN_MOVIMENTO'?'selected':''}>IN_MOVIMENTO</option>
									      	<option value="FISSO" ${satellite_insert_attr.stato == 'FISSO'?'selected':''}>FISSO</option>
									      	<option value="DISABILITATO" ${satellite_insert_attr.stato == 'DISABILITATO'?'selected':''}>DISABILITATO</option>
								    	</select>
								    </spring:bind>
								    <form:errors  path="stato" cssClass="error_field" />
								</div>
								
								
							<div class="col-12">
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
							</div>
		
						</form:form>
  
				    
				    
					<!-- end card-body -->			   
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