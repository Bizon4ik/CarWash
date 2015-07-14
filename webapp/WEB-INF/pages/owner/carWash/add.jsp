<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../owner_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Добавить мойку: </p>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1" >
      <form class="form-horizontal" action="/owner/carwash/add" method="post">
        <div class="form-group">
          <label for="carWashName" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Название<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <input type="text" class="form-control" id="carWashName" name="name" value="${carWash.name}" placeholder="Название мойки">
          </div>
        </div>

        <c:if test="${carWashFormErrors.nameErrorMsg != null}">
          <div class="form-group">
            <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6 errorMessage">
              <p>${carWashFormErrors.nameErrorMsg}</p>
            </div>
          </div>
        </c:if>

        <div class="form-group">
          <label for="box" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Боксы<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <select size="1" name="boxAmount" class="form-control" id="box">
              <c:forEach begin="1" end="15" varStatus="loop">
                <option ${loop.current == carWash.boxAmount ? "selected" : ""} value="${loop.current}">${loop.current}</option>
              </c:forEach>
            </select>

          </div>

        </div>

        <div class="form-group">
          <label class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Начало смены<sup>*</sup>:</label>
          <div class="col-xs-4 col-md-2 col-lg-2">
            <select size="1" name="startHours" class="form-control">
              <c:forEach begin="0" end="12" varStatus="loop">
                <option  value="${loop.current}">${loop.current}</option>
              </c:forEach>
            </select>

          </div>

          <div class="col-xs-4 col-md-2 col-lg-2">
            <select size="1" name="startMin" class="form-control">
              <c:forEach begin="0" end="5" varStatus="loop">
                <option  value="${loop.current}">${loop.current}0</option>
              </c:forEach>
            </select>

          </div>

        </div>

        <div class="form-group">
          <label class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Конец смены<sup>*</sup>:</label>
          <div class="col-xs-4 col-md-2 col-lg-2">
            <select size="1" name="finishHours" class="form-control">
              <c:forEach begin="13" end="23" varStatus="loop">
                <option  value="${loop.current}">${loop.current}</option>
              </c:forEach>
            </select>

          </div>

          <div class="col-xs-4 col-md-2 col-lg-2">
            <select size="1" name="finishMin" class="form-control">
              <option  value="59">59</option>
              <c:forEach begin="0" end="5" varStatus="loop">
                <option  value="${loop.current}">${loop.current}0</option>
              </c:forEach>
            </select>

          </div>

        </div>

        <div class="form-group">
          <label for="address" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Адрес:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <input type="text" class="form-control" id="address" name="address" value="${carWash.address}" placeholder="Адрес мойки">
          </div>
        </div>

        <c:if test="${carWashFormErrors.addressErrorMsg != null}">
          <div class="form-group">
            <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6 errorMessage">
              <p>${carWashFormErrors.addressErrorMsg}</p>
            </div>
          </div>
        </c:if>

        <div class="form-group">
          <label for="phoneNumber" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Телефон:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${carWash.phoneNumber}" placeholder="Номер телефона">
          </div>
        </div>

        <c:if test="${carWashFormErrors.phoneNumberErrorMsg != null}">
          <div class="form-group">
            <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6 errorMessage">
              <p>${carWashFormErrors.phoneNumberErrorMsg}</p>
            </div>
          </div>
        </c:if>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
            <p class="asterisk"><sup>*</sup>Эти поля обязательны к заполнению</p>
          </div>
        </div>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
            <button type="submit" class="btn btn-primary">Добавить</button>
          </div>
        </div>
      </form>
    </div>
  </div>


</div>


</body>
</html>