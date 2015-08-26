<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../admin_header.jsp" />

<div class="container-fluid" id="body">

  <!--------------------Бокс 1 ------------------------ -->
  <div class="row">
    <div class="col-xs-10 col-xs-offset-1" >
      <h1 class="userTitle">Мойщики в Боксе №${boxNumber}:</h1>
    </div>
  </div>

  <div class="row">
    <form class="form-horizontal" action="/admin/gangs/box/${boxNumber}" method="post">
        <table class="table-bordered table-hover table-striped table " id="gangTable" >
           <thead>
              <tr>
                <th>№</th>
                <th>Мойщик</th>
              </tr>
              </thead>
              <tbody>

              <c:forEach items="${washerManInBoxNow}" var="washerMan" varStatus="loop">
                  <c:choose>
                      <c:when test="${loop.count == 1}">
                          <tr id="firstLine">
                      </c:when>
                      <c:otherwise>
                          <tr class="addblock">
                      </c:otherwise>
                  </c:choose>

                          <td class="col-xs-1">${loop.count}</td>
                          <td class="col-xs-4">
                              <select size="1" name="washerManIds">
                                  <option value="-1"></option>
                                  <c:forEach items="${allowedWasherMansInBoxSet}" var="row">
                                      <option value="${row.userId}" ${washerMan.userId == row.userId ? "selected" : ""}>${row.surname} ${row.name}</option>
                                  </c:forEach>
                              </select>
                          </td>
                          </tr>

              </c:forEach>


              </tbody>
        </table>

      <div class="form-group">
        <div class="col-xs-12 col-md-offset-1 col-lg-offset-1 ">
          <button type="button" onclick="add_input()" class="btn btn-success " value="">&nbsp;&nbsp;Добавить поле&nbsp;&nbsp;</button>
          <button type="button" onclick="del_input()" class="btn btn-danger  ">&nbsp;&nbsp;Удалить поле&nbsp;&nbsp;</button>
          <button type="submit" class="btn btn-primary">&nbsp;&nbsp;Сохранить&nbsp;&nbsp;</button>
        </div>
      </div>

    </form>
  </div>





</div>


</body>
</html>

<script language="javascript">
  function add_input()
  {
    $("#firstLine").clone().removeAttr('id').addClass("addblock").appendTo("#gangTable");
  }

  function del_input()
  {
    $(".addblock").last().remove();
  }

</script>
