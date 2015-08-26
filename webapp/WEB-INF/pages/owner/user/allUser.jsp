<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../owner_header.jsp" />

<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Все пользователи: </p>
    </div>
  </div>

  <div class="row">

      <c:forEach items="${allUsers}" var="map" varStatus="mapLoop">

        <table class="table-bordered table-hover table-striped col-xs-12 table" >
          <caption class="firstLetterUppercase">${map.key.name}:</caption>
          <thead>
          <tr>
            <th>№</th>
            <th>Пользователь</th>
            <th>Роль</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${map.value}" var="row" varStatus="loop">
            <tr>
              <td class="col-xs-1">${loop.count}</td>
              <td class="col-xs-4"><a href="/owner/user/user/${row.id}"><span class="firstLetterUppercase">${row.surname}</span> <span class="firstLetterUppercase">${row.name}</span></a> </td>
              <td class="col-xs-3">${row.role}</td>
            </tr>

          </c:forEach>
          </tbody>
        </table>
        <p></p>
      </c:forEach>

  </div>


</div>


</body>
</html>