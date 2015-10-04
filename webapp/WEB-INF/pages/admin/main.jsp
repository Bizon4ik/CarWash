<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="admin_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Текущие заказы: </p>
    </div>
  </div>


<c:forEach items="${orderInBoxList}" var="orderInBox" varStatus="row">
  <div class="row">
    <div class="col-xs-12">
      <p class="titleOftables">Бокс №${row.count} (
         <c:forEach items="${orderInBox.washerManSet}" var="washerMan">
           <sub class="firstLetterUppercase">${washerMan.surname}</sub>&nbsp;
         </c:forEach>
        )
      </p>
    </div>
  </div>
  <c:choose>
    <c:when test="${orderInBox.order.id == null}">
      <div class="row">
        <table class="table-bordered table-hover table-striped col-xs-12 table" >
          <tr>
            <td>В боксе нет заказов!</td>
          </tr>
        </table>
      </div>
    </c:when>
    <c:otherwise>
      <div class="row">
        <div class="col-xs-12 xsWithoutPadding" >
          <div class="orderForm">


              <div class="row">
                <div class="col-xs-3 nameOfRowInOrder">Номер:</div>
                <div class="col-xs-9 valueOfRowInOrder" >
                  <c:out value="${orderInBox.carViewWithClient.number}" />
                </div>
              </div>

            <div class="row">
              <div class="col-xs-3 nameOfRowInOrder">Марка:</div>
              <div class="col-xs-9 valueOfRowInOrder" >
                <c:out value="${orderInBox.carViewWithClient.brandName}" />
              </div>
            </div>

            <div class="row">
              <div class="col-xs-3 nameOfRowInOrder">Категория:</div>
              <div class="col-xs-9 valueOfRowInOrder" >
                <c:out value="${orderInBox.carViewWithClient.categoryName}" /> - <c:out value="${orderInBox.carViewWithClient.categotyDescription}" />
              </div>
            </div>

            <div class="orderList">
              <div class="row">

                  <c:forEach items="${orderInBox.orderedServices}" var="service" varStatus="row">
                      <div class="col-xs-3 nameOfRowInOrder">
                          <c:if test="${row.count==1}">
                              Услуги:
                          </c:if>
                      </div>
                      <div class="col-xs-6 valueOfRowInOrder" >
                        <c:out value="${service.name}" />
                        <c:if test="${service.count>1}">
                          (<c:out value="${service.count}" />шт.)
                        </c:if>
                      </div>
                      <div class="col-xs-3 valueOfRowInOrder" >
                        <c:out value="${service.price}" />
                          <c:if test="${service.additionPrice > 0}">
                            +<c:out value="${service.additionPrice}" />
                          </c:if>
                        грн.
                      </div>
                  </c:forEach>

                <div class="row">
                  <div class="bottomLineBlack col-xs-9 col-xs-offset-3"></div>
                </div>

                <row>
                  <div class="col-xs-6 col-xs-offset-3 valueOfRowInOrder" >
                    Итого:
                  </div>
                  <div class="col-xs-3 valueOfRowInOrder" >
                    <c:out value="${orderInBox.totalPrice}" />грн.
                  </div>
                </row>

                <row>
                  <div class="col-xs-6 col-xs-offset-3 valueOfRowInOrder" >
                    Скидка:
                  </div>
                  <div class="col-xs-3 valueOfRowInOrder" >
                    <c:out value="${orderInBox.discount}" />%
                  </div>
                </row>

                <c:if test="${orderInBox.action>0}">
                  <row>
                    <div class="col-xs-6 col-xs-offset-3 valueOfRowInOrder" >
                      Акция:
                    </div>
                    <div class="col-xs-3 valueOfRowInOrder" >
                      -<c:out value="${orderInBox.action}" />грн.
                    </div>
                  </row>
                </c:if>

                <div class="row">
                  <div class="bottomLineBlack col-xs-9 col-xs-offset-3"></div>
                </div>

                <row>
                  <div class="col-xs-6 col-xs-offset-3 valueOfRowInOrder finalPrice" >
                    К оплате:
                  </div>
                  <div class="col-xs-3 valueOfRowInOrder finalPrice" >
                    <c:out value="${orderInBox.finalPrice}" />грн.
                  </div>
                </row>


              </div>
            </div>

            <div class="row">
              <div class="col-xs-3 nameOfRowInOrder">Способ оплаты:</div>
              <div class="col-xs-9 valueOfRowInOrder" >
                <c:out value="${orderInBox.order.paymentMethod.lable}" />
              </div>
            </div>

            <div class="row">
              <div class="col-xs-3 nameOfRowInOrder">Создан:</div>
              <div class="col-xs-9 valueOfRowInOrder" >
                <fmt:formatDate pattern="HH:mm  dd-MM-yyyy" value="${orderInBox.order.dateOfCreation}" />
              </div>
            </div>

            <div class="row">
              <div class="col-xs-3 nameOfRowInOrder">№ заказа:</div>
              <div class="col-xs-9 valueOfRowInOrder" >
                <c:out value="${orderInBox.order.id}" />
              </div>
            </div>

            <div class="row">
              <a class="btn btn-warning col-xs-4" href="/admin/order/edit/${orderInBox.order.id}" role="button">Редактировать</a>
              <a class="btn btn-success col-xs-4" href="/admin/main/close/${orderInBox.order.id}" role="button">Закрыть</a>
              <a class="btn btn-danger col-xs-4" href="/admin/main/delete/${orderInBox.order.id}" role="button">Удалить</a>
            </div>

        </div>
        </div>
      </div>
    </c:otherwise>
  </c:choose>

</c:forEach>





</div>


</body>
</html>






