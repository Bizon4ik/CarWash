<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../owner_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">${addUserFormView.title}: </p>
    </div>
  </div>

  <div class="row">
      <div class="col-xs-12 col-md-10 col-md-offset-1" >
          <form:form method="post" action="/owner/user/${addUserFormView.url}" cssClass="form-horizontal" commandName="addUserForm">
            <div class="form-group">
              <label for="name" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Имя<sup>*</sup>:</label>
              <div class="col-xs-8 col-md-5 col-lg-4">
                <form:input path="name" id="name" cssClass="form-control" placeholder="Укажите имя" value="${addUserForm.name}"></form:input>
                <form:errors path="name" cssClass="errorMessage"></form:errors>
              </div>
           </div>

            <div class="form-group">
              <label for="surname" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Фамилия<sup>*</sup>:</label>
              <div class="col-xs-8 col-md-5 col-lg-4">
                <form:input path="surname" id="surname" cssClass="form-control" placeholder="Укажите фамилию" value="${addUserForm.surname}"></form:input>
                <form:errors path="surname" cssClass="errorMessage"></form:errors>
              </div>
            </div>

            <div class="form-group">
              <label for="salary" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">ЗП(грн)<sup>*</sup>:</label>
              <div class="col-xs-8 col-md-5 col-lg-4">
                <form:input path="salary" id="salary" cssClass="form-control" placeholder="Укажите зарплату" value="${addUserForm.salary}"></form:input>
                <form:errors path="salary" cssClass="errorMessage"></form:errors>
              </div>
            </div>

            <div class="form-group">
              <label for="phoneNumber" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Телефон:</label>
              <div class="col-xs-8 col-md-5 col-lg-4">
                <form:input path="phoneNumber" id="phoneNumber" cssClass="form-control" placeholder="Укажите номер телефона" value="${addUserForm.phoneNumber}"></form:input>
                <form:errors path="phoneNumber" cssClass="errorMessage"></form:errors>
              </div>
            </div>

            <div class="form-group">
              <label for="carWash" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Мойка<sup>*</sup>:</label>
              <div class="col-xs-8 col-md-5 col-lg-4">
                  <form:select size="1"  multiple="false" path="carWashId" cssClass="form-control" id="carWash"  itemLabel="${addUserForm.carWashId}">
                      <c:forEach items="${addUserFormView.carWashList}" var="carWash" varStatus="loop">
                        <form:option value="${carWash.id}"> ${carWash.name}</form:option>
                      </c:forEach>
                  </form:select>
                  <form:errors path="carWashId" cssClass="errorMessage"></form:errors>
              </div>
            </div>


            <div class="form-group">
              <label class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">День/Ночь %<sup>*</sup>:</label>
                  <div class="col-xs-4 col-md-2 col-lg-2">
                      <form:select path="dayCommission" size="1" cssClass="form-control" itemLabel="${addUserForm.dayCommission}" >
                          <c:forEach begin="0" end="150"  varStatus="loop">
                              <form:option value="${loop.current}">${loop.current}</form:option>
                          </c:forEach>
                      </form:select>
                  </div>

                  <div class="col-xs-4 col-md-2 col-lg-2">
                      <form:select path="nightCommission" size="1" cssClass="form-control" itemLabel="${addUserForm.nightCommission}" >
                          <c:forEach begin="0" end="150"  varStatus="loop">
                              <form:option value="${loop.current}">${loop.current}</form:option>
                          </c:forEach>
                      </form:select>
                  </div>
            </div>

            <c:if test="${addUserFormView.logPassPart}">
                <div class="form-group">
                  <label for="login" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Login:</label>
                  <div class="col-xs-8 col-md-5 col-lg-4">
                      <form:input path="login" id="login" cssClass="form-control" placeholder="Укажите login" value="${addUserForm.login}"></form:input>
                      <form:errors path="login" cssClass="errorMessage"></form:errors>
                  </div>
                </div>

                <div class="form-group bottomLine">
                  <label for="password" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Пароль<sup>*</sup>:</label>
                  <div class="col-xs-8 col-md-5 col-lg-4">
                      <form:input path="password" id="password" cssClass="form-control" placeholder="Укажите пароль" value="${addUserForm.password}"></form:input>
                      <form:errors path="password" cssClass="errorMessage"></form:errors>
                  </div>
                </div>
            </c:if>


            <div class="form-group">
              <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
                <p class="asterisk"><sup>*</sup>Эти поля обязательны к заполнению</p>
              </div>
            </div>

            <div class="form-group">
              <div class="col-xs-12 col-md-offset-1 col-lg-offset-1 ">
                <button type="submit" class="btn btn-primary">&nbsp;&nbsp;Сохранить&nbsp;&nbsp;</button>
              </div>
            </div>


          </form:form>
      </div>
  </div>




</div>


</body>
</html>



