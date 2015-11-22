<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../admin_header.jsp" />

<div class="container-fluid" id="body">

  <!--------------------Бокс 1 ------------------------ -->
  <div class="row">
    <div class="col-xs-10 col-xs-offset-1" >
      <fmt:formatDate value="${fromD}" var="from" pattern="HH:mm dd-MM-yyyy" />
      <fmt:formatDate value="${toD}" var="to" pattern="HH:mm dd-MM-yyyy" />
      <h1 class="userTitle">Статистика по боксу ${boxNumber} за период с ${from} по ${to}</h1>
    </div>
  </div>


  <div class="row">
    <table class="table-bordered table-hover table-striped col-xs-12 table" >
      <caption>Бокс ${boxNumber}:</caption>

      <thead>
      <tr class="info">
        <th>#</th>
        <th>id</th>
        <th>Открыт</th>
        <th>Закрыт</th>
        <th>Стоимость</th>
        <th>ЗП</th>
      </tr>
      </thead>
      <tbody>

      <c:forEach items="${detailStatInBoxes}" var="order" varStatus="loop">
        <tr>
          <td class="col-xs-1">${loop.count} </td>
          <td class="col-xs-1"><a href="#">${order.id}</a></td>
          <td class="col-xs-2">
            <fmt:formatDate pattern="HH:mm  dd-MM-yyyy" value="${order.dateOfCreation}" />
          </td>
          <td class="col-xs-2">
            <fmt:formatDate pattern="HH:mm  dd-MM-yyyy" value="${order.dateOfClose}" />
          </td>
          <td class="col-xs-2">${order.totaCostOfOrder} </td>
          <td class="col-xs-2">${order.salary} </td>
        </tr>
      </c:forEach>
      </tbody>



    </table>
  </div>




</div>


</body>
</html>
