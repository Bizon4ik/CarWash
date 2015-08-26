<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../admin_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Укажите номер машины и категорию: </p>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1" >
      <form:form method="post" action="/admin/order/getCarNumber" cssClass="form-horizontal" commandName="car">
        <div class="form-group">
          <label for="number" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Номер<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:input path="number" id="number" cssClass="form-control" placeholder="Укажите номер" value="${car.number}"></form:input>
            <form:errors path="number" cssClass="errorMessage"></form:errors>
          </div>
        </div>

        <div class="form-group">
          <label for="category" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Категория<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:select size="1"  multiple="false" path="categoryId" cssClass="form-control" id="category"  itemLabel="${car.categoryId}">
              <c:forEach items="${categoryList}" var="category" varStatus="loop">
                <form:option value="${category.id}"> ${category.name} - ${category.description}</form:option>
              </c:forEach>
            </form:select>
            <form:errors path="categoryId" cssClass="errorMessage"></form:errors>
          </div>
        </div>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
            <p class="asterisk"><sup>*</sup>Эти поля обязательны к заполнению</p>
          </div>
        </div>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-lg-offset-1 ">
            <button type="submit" class="btn btn-primary">&nbsp;&nbsp;Сохранить&nbsp;&nbsp;</button>
          </div>
        </div>


      </form:form>
    </div>
  </div>




</div>


</body>
</html>




