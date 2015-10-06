<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../admin_header.jsp" />

<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Выберите период: </p>
    </div>
  </div>


  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1" >

      <form:form method="post" action="/admin/statistic/period" cssClass="form-horizontal" commandName="calendarPeriod">
        <div class="form-group">
          <label for="fromDate" class="col-xs-2 control-label">От</label>
          <div class="col-xs-3">
            <fmt:formatDate value="${calendarPeriod.fromDate}" var="dateStringFrom" pattern="dd-MM-yyyy" />
            <form:input path="fromDate" cssClass="form-control" id="fromDate" value="${dateStringFrom}" placeholder="От" />
            <form:errors path="fromDate" cssClass="errorMessage"/>
          </div>
          <div class="col-xs-5">
            <form:select size="1"  multiple="false" path="fromHour" cssClass="form-control timeInForm" itemLabel="${calendarPeriod.fromHour}">
              <c:forEach begin="0" end="23" varStatus="loop">
                <c:choose>
                  <c:when test="${loop.current<10}">
                    <option value="0${loop.current}">0${loop.current}</option>
                  </c:when>
                  <c:otherwise>
                    <option value="${loop.current}">${loop.current}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </form:select>

            <label for="fromMinute" class="control-label">:</label>
            <form:select size="1"  multiple="false" path="fromMinute" id="fromMinute" cssClass="form-control timeInForm" itemLabel="${calendarPeriod.fromMinute}">

              <c:forEach begin="0" end="59" varStatus="loop">
                <c:choose>
                  <c:when test="${loop.current<10}">
                    <option value="0${loop.current}">0${loop.current}</option>
                  </c:when>
                  <c:otherwise>
                    <option value="${loop.current}">${loop.current}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>

            </form:select>
          </div>
        </div>

        <div class="form-group">
          <label class="col-xs-2 control-label" for="toDate">До:</label>
          <div class="col-xs-3">
            <fmt:formatDate value="${calendarPeriod.toDate}" var="dateStringTo" pattern="dd-MM-yyyy" />
            <form:input path="toDate" cssClass="form-control" id="toDate" value="${dateStringTo}" placeholder="До" />
            <form:errors path="toDate" cssClass="errorMessage"/>
          </div>
          <div class="col-xs-5">
            <form:select size="1"  multiple="false" path="toHour" cssClass="form-control timeInForm" itemLabel="${calendarPeriod.toHour}">
              <c:forEach begin="0" end="23" varStatus="loop">
                <c:choose>
                  <c:when test="${loop.current<10}">
                    <option value="0${loop.current}">0${loop.current}</option>
                  </c:when>
                  <c:otherwise>
                    <option value="${loop.current}">${loop.current}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </form:select>
            <label for="toMinute" class="control-label">:</label>

            <form:select size="1"  multiple="false" path="toMinute" cssClass="form-control timeInForm" itemLabel="${calendarPeriod.toMinute}">
              <c:forEach begin="0" end="59" varStatus="loop">
                <c:choose>
                  <c:when test="${loop.current<10}">
                    <option value="0${loop.current}">0${loop.current}</option>
                  </c:when>
                  <c:otherwise>
                    <option value="${loop.current}">${loop.current}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </form:select>

          </div>

        </div>

        <button type="submit" class="btn btn-primary col-xs-offset-2 col-xs-3">Ввести</button>

      </form:form>
    </div>
  </div>
</div>


</body>
</html>


<script>
    $(document).ready(function() {
      $("#fromDate").datepicker({ dateFormat: 'dd-mm-yy' });
    });

    $(document).ready(function() {
      $("#toDate").datepicker({ dateFormat: 'dd-mm-yy' });
    });

</script>

