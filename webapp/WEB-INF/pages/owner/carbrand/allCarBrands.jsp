<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../owner_header.jsp" />

<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Все автобренды: </p>
    </div>
  </div>

  <div class="row">

      <table class="table-bordered table-hover table-striped col-xs-12 table" >
        <thead>
        <tr>
          <th>№</th>
          <th>Автобренд</th>
          <th>Добавлен</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${carBrandList}" var="row" varStatus="loop">
          <tr>
            <td class="col-xs-1">${loop.count}</td>
            <td class="col-xs-4"><span style="text-transform: uppercase">${row.name}</span></td>
            <td class="col-xs-3">
              <fmt:formatDate value="${row.dateOfcreation}" pattern="dd-MM-yyyy" />
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