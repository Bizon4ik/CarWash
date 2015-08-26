<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../admin_header.jsp" />

<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Все мойщики: </p>
    </div>
  </div>

  <div class="row">


      <table class="table-bordered table-hover table-striped col-xs-12 table" >
        <thead>
        <tr>
          <th>№</th>
          <th>Пользователь</th>
          <th>Принят на работу</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userSet}" var="row" varStatus="loop">
          <tr>
            <td class="col-xs-1">${loop.count}</td>
            <td class="col-xs-4"><a href="/owner/user/user/${row.id}"><span class="firstLetterUppercase">${row.surname}</span> <span class="firstLetterUppercase">${row.name}</span></a> </td>
            <td class="col-xs-3">
              <fmt:formatDate value="${row.dateOfCreation}" pattern="dd-MM-yyyy" />
            </td>
          </tr>

        </c:forEach>
        </tbody>
      </table>
      <p></p>

  </div>


</div>


</body>
</html>