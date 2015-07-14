<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../owner_header.jsp" />

<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Все категории: </p>
    </div>
  </div>

  <div class="row">

    <c:if test="${delete != null}">
    <form action="/owner/category/delete" method="post">
      </c:if>


      <table class="table-bordered table-hover table-striped col-xs-12 table" >
        <thead>
        <tr>
          <th>№</th>
          <th>Имя</th>
          <th>Описание</th>
          <th>Создана</th>
          <th>id</th>
          <c:if test="${delete != null}">
            <th>Удалить</th>
          </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${categoryList}" var="row" varStatus="loop">

          <tr>
            <td>${loop.count}</td>
            <td>${row.name}</td>
            <td>${row.description}</td>
            <td>
              <fmt:formatDate value="${row.dateOfCreation}" pattern="HH:mm dd-MM-yyyy" />
            </td>
            <td>${row.id}</td>
            <c:if test="${delete != null}">
              <td><input type="checkbox" name="listIdCategory" value="${row.id}"/></td>
            </c:if>

          </tr>

        </c:forEach>
        </tbody>

      </table>

      <c:if test="${delete != null}" >
      <input type="submit" class="btn btn-danger col-xs-5 col-xs-offset-7 col-md-2 col-md-offset-10 deleteButton" value="delete">
    </form>
    </c:if>

  </div>



</div>


</body>
</html>
