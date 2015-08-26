<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../owner_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Добавить услугу: </p>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1" >
      <form:form method="post" action="/owner/service/add" cssClass="form-horizontal" commandName="serviceName">
        <div class="form-group">
          <label for="serviceName" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Название<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:input path="name" id="serviceName" cssClass="form-control" placeholder="Название услуги" value="${serviceName.name}"></form:input>
            <form:errors path="name" cssClass="errorMessage"></form:errors>
          </div>
        </div>

        <div class="form-group">
          <label for="countable" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Повторение:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:checkbox path="countable" id="countable" cssClass="form-control" value="${serviceName.countable}"></form:checkbox>
          </div>
        </div>

        <div class="form-group">
          <label for="additionPrice" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Доп цена:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:checkbox path="additionPrice" id="additionPrice" cssClass="form-control" value="${serviceName.additionPrice}"></form:checkbox>
          </div>
        </div>

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
      </form:form>
    </div>
  </div>


</div>


</body>
</html>
