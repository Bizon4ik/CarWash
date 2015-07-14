<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../owner_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Добавить услугу: </p>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1" >
      <form class="form-horizontal" action="/owner/service/add" method="post">
        <div class="form-group">
          <label for="serviceName" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Название<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <input type="text" class="form-control" id="serviceName" name="name" value="${service.name}" placeholder="Название услуги">
          </div>
        </div>

        <c:if test="${serviceNameErrors != null}">
          <div class="form-group">
            <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6 errorMessage">
              <p>${serviceNameErrors}</p>
            </div>
          </div>
        </c:if>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
            <p class="asterisk"><sup>*</sup>Эти поля обязательны к заполнению</p>
          </div>
        </div>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
            <button type="submit" class="btn btn-primary">Добавить</button>
          </div>
        </div>
      </form>
    </div>
  </div>


</div>


</body>
</html>
