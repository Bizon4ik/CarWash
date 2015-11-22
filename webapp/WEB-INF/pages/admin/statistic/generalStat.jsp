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
        <fmt:formatDate value="${generalStatInCarWash.from}" var="from" pattern="HH:mm dd-MM-yyyy" />
        <fmt:formatDate value="${generalStatInCarWash.to}" var="to" pattern="HH:mm dd-MM-yyyy" />
      <h1 class="userTitle">Статистика за период с ${from} по ${to}</h1>
    </div>
  </div>


    <div class="row">
      <table class="table-bordered table-hover table-striped col-xs-12 table" >
        <caption>Мойка:</caption>

            <thead>
            <tr class="info">
              <th>Бокс№</th>
              <th>Заказы(шт.)</th>
              <th>Нал(грн.)</th>
              <th>Безнал(грн.)</th>
              <th>Всего(грн.)</th>
              <th>ЗП(грн.)</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${generalStatInCarWash.generalStatInBoxes}" var="box" varStatus="loop">
              <tr>
                <td class="col-xs-1"><a href="/admin/statistic/detailStatForBox/${loop.count}/${generalStatInCarWash.from.time}/${generalStatInCarWash.to.time}">Бокс-${loop.count}</a></td>
                <td class="col-xs-4">${box.orderAmount} </td>
                  <c:choose>
                      <c:when test="${box.moneyInCash != null}">
                          <td class="col-xs-3">${box.moneyInCash}</td>
                      </c:when>
                      <c:otherwise>
                          <td class="col-xs-3">0</td>
                      </c:otherwise>
                  </c:choose>

                  <c:choose>
                      <c:when test="${box.moneyBeznal != null}">
                          <td class="col-xs-3">${box.moneyBeznal} </td>
                      </c:when>
                      <c:otherwise>
                          <td class="col-xs-3">0</td>
                      </c:otherwise>
                  </c:choose>

                  <c:choose>
                      <c:when test="${box.moneyTotal != null}">
                          <td class="col-xs-3">${box.moneyTotal} </td>
                      </c:when>
                      <c:otherwise>
                          <td class="col-xs-3">0</td>
                      </c:otherwise>
                  </c:choose>

                  <c:choose>
                      <c:when test="${box.salary != null}">
                          <td class="col-xs-3">${box.salary} </td>
                      </c:when>
                      <c:otherwise>
                          <td class="col-xs-3">0</td>
                      </c:otherwise>
                  </c:choose>

              </tr>
            </c:forEach>
            <tr class="success bold">
                <td class="col-xs-1">Всего:</td>
                <td class="col-xs-4">${generalStatInCarWash.totalOrderQuantity} </td>
                <td class="col-xs-3">${generalStatInCarWash.totalMoneyCash} </td>
                <td class="col-xs-3">${generalStatInCarWash.totalMoneyBeznal} </td>
                <td class="col-xs-3">${generalStatInCarWash.totalMoneyCarWash} </td>
                <td class="col-xs-3">${generalStatInCarWash.totalSalary} </td>
            </tr>
            </tbody>



      </table>
    </div>




</div>


</body>
</html>
