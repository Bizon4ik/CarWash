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
      <h1 class="userTitle">Выберите бокс для смены</h1>
    </div>
  </div>


    <c:forEach items="${washerManInBoxesWithNames}" var="box" varStatus="BoxLoop">
      <div class="row">
      <table class="table-bordered table-hover table-striped col-xs-12 table" >
        <caption><a href="/admin/gangs/box/${BoxLoop.count}" >Бокс:${BoxLoop.count}</a></caption>
              <c:choose>
                <c:when test="${box != null}" >

                              <thead>
                              <tr>
                                <th>№</th>
                                <th>Мойщик</th>
                                <th>Вышел на смену</th>
                              </tr>
                              </thead>
                              <tbody>

                              <c:forEach items="${box}" var="washerman" varStatus="loop">
                                  <tr>
                                      <td class="col-xs-1">${loop.count}</td>
                                      <td class="col-xs-4"><a href="#"><span class="firstLetterUppercase">${washerman.surname}</span> <span class="firstLetterUppercase">${washerman.name}</span></a> </td>
                                      <td class="col-xs-3">
                                          <fmt:formatDate value="${washerman.startInBox}" pattern="HH:mm dd-MM-yyyy" />
                                      </td>
                                  </tr>
                              </c:forEach>

                              </tbody>

                </c:when>

                <c:otherwise>
                         <tr>
                           <td>В боксе нет мойщиков!</td>
                         </tr>
                </c:otherwise>
              </c:choose>
        </table>
      </div>
    </c:forEach>



</div>


</body>
</html>
