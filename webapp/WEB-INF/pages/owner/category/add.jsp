<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../owner_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Добавить категорию: </p>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1" >
      <form class="form-horizontal" action="/owner/category/add" method="post">
        <div class="form-group">
          <label for="categoryName" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Название<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <input type="text" class="form-control" id="categoryName" name="name" value="${category.name}" placeholder="Название категории">
          </div>
        </div>

        <c:if test="${categoryFormErrors.nameErrorMsg != null}">
          <div class="form-group">
            <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6 errorMessage">
              <p>${categoryFormErrors.nameErrorMsg}</p>
            </div>
          </div>
        </c:if>

        <div class="form-group">
          <label for="categoryDescription" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Описание<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <input type="text" class="form-control" id="categoryDescription" name="description" value="${category.description}" placeholder="Описание категории">
          </div>
        </div>

        <c:if test="${categoryFormErrors.descriptionErrorMsg != null}">
          <div class="form-group">
            <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6 errorMessage">
              <p>${categoryFormErrors.descriptionErrorMsg}</p>
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