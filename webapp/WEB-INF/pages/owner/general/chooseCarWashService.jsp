<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../owner_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Выберите мойку: </p>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1" >
      <form class="form-horizontal" action="${url}" method="post">
        <div class="form-group">
          <label for="carWash" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Название<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <select size="1" name="carWashId" class="form-control" id="carWash">
              <c:forEach items="${carWashList}" var="row" varStatus="loop">
                <option value="${row.id}">${row.name}</option>
              </c:forEach>
            </select>
          </div>
        </div>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
            <p class="asterisk"><sup>*</sup>Эти поля обязательны к заполнению</p>
          </div>
        </div>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
            <button type="submit" class="btn btn-primary">Выбрать</button>
          </div>
        </div>
      </form>
    </div>
  </div>


</div>


</body>
</html>
