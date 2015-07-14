<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../../owner_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Добавить услугу на мойку: </p>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1" >
      <form class="form-horizontal" action="/owner/service/carwash/add" method="post">
        <div class="form-group">
          <label for="carWashName" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Мойка<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <select size="1" name="carwashid" class="form-control" id="carWashName">
              <c:forEach items="${carWashList}" var="carWash" varStatus="loop">
                <option value="${carWash.id}" ${carWash.id == carWashAddServiceForm.carwashid ? "selected" : ""}>${carWash.name}</option>
              </c:forEach>
            </select>
          </div>
        </div>

        <div class="form-group">
          <label for="category" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Категория<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <select size="1" name="categoryid" class="form-control" id="category">
              <c:forEach items="${categoryList}" var="category" varStatus="loop">
                <option value="${category.id}" ${category.id == carWashAddServiceForm.categoryid ? "selected" : ""}>${category.name} - ${category.description}</option>
              </c:forEach>
            </select>
          </div>
        </div>

        <div id="serviceLine">

        <div class="form-group">
          <label class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Услуга<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <select size="1" name="serviceNameIdList" class="form-control">
              <c:forEach items="${serviceNameList}" var="serviceName" varStatus="loop">
                <option value="${serviceName.id}" ${serviceName.id == carWashAddServiceForm.serviceNameIdList[0] ? "selected" : ""}>${serviceName.name}</option>
              </c:forEach>
            </select>
          </div>
        </div>

          <c:if test="${serviceFormError.nameExistErros[0] != null}">
            <div class="form-group">
              <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6 errorMessage">
                <p>${serviceFormError.nameExistErros[0]}</p>
              </div>
            </div>
          </c:if>

        <div class="form-group">
            <label for="price" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Цена(грн)<sup>*</sup>:</label>
            <div class="col-xs-8 col-md-5 col-lg-4">
                <input type="text" class="form-control" name="priceList" id="price" placeholder="Укажите цену" value="${carWashAddServiceForm.priceList[0]}">
            </div>
        </div>

          <c:if test="${serviceFormError.priceErrors[0] != null}">
            <div class="form-group">
              <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6 errorMessage">
                <p>${serviceFormError.priceErrors[0]}</p>
              </div>
            </div>
          </c:if>


        <div class="form-group bottomLine">
          <label class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">День/Ночь %<sup>*</sup>:</label>
          <div class="col-xs-4 col-md-2 col-lg-2">
            <select size="1" name="dayCommissionList" class="form-control">
              <c:forEach begin="0" end="50" varStatus="loop">
                <c:choose>
                  <c:when test="${carWashAddServiceForm == null}">
                    <option  value="${loop.current}" ${loop.current == 20 ? "selected" : ""}>${loop.current}</option>
                  </c:when>
                  <c:otherwise>
                    <option  value="${loop.current}" ${loop.current == carWashAddServiceForm.dayCommissionList[0] ? "selected" : ""}>${loop.current}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>

          </div>

          <div class="col-xs-4 col-md-2 col-lg-2">
            <select size="1" name="nightCommissionList" class="form-control">
              <c:forEach begin="0" end="50" varStatus="loop">
                <c:choose>
                  <c:when test="${carWashAddServiceForm == null}">
                    <option  value="${loop.current}" ${loop.current == 30 ? "selected" : ""}>${loop.current}</option>
                  </c:when>
                  <c:otherwise>
                    <option  value="${loop.current}" ${loop.current == carWashAddServiceForm.nightCommissionList[0] ? "selected" : ""}>${loop.current}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
          </div>

        </div>
        </div>

        <div id="servicePlace">
          <c:if test="${carWashAddServiceForm != null && fn:length(carWashAddServiceForm.serviceNameIdList) > 1}">
            <c:forEach begin="1" end="${fn:length(carWashAddServiceForm.serviceNameIdList)-1}" varStatus="mainLoop">
              <div class="addblock">

                <div class="form-group">
                  <label class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Услуга<sup>*</sup>:</label>
                  <div class="col-xs-8 col-md-5 col-lg-4">
                    <select size="1" name="serviceNameIdList" class="form-control">
                      <c:forEach items="${serviceNameList}" var="serviceName" varStatus="loop">
                        <option value="${serviceName.id}" ${serviceName.id == carWashAddServiceForm.serviceNameIdList[mainLoop.current] ? "selected" : ""}>${serviceName.name}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>

                  <c:if test="${serviceFormError.nameExistErros[mainLoop.current] != null}">
                    <div class="form-group">
                      <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6 errorMessage">
                        <p>${serviceFormError.nameExistErros[mainLoop.current]}</p>
                      </div>
                    </div>
                  </c:if>

                <div class="form-group">
                  <label  class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Цена(грн)<sup>*</sup>:</label>
                  <div class="col-xs-8 col-md-5 col-lg-4">
                    <input type="text" class="form-control" name="priceList"  placeholder="Укажите цену" value="${carWashAddServiceForm.priceList[mainLoop.current]}">
                  </div>
                </div>

                  <c:if test="${serviceFormError.priceErrors[mainLoop.current] != null}">
                    <div class="form-group">
                      <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6 errorMessage">
                        <p>${serviceFormError.priceErrors[mainLoop.current]}</p>
                      </div>
                    </div>
                  </c:if>


                <div class="form-group bottomLine">
                  <label class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">День/Ночь %<sup>*</sup>:</label>
                  <div class="col-xs-4 col-md-2 col-lg-2">
                    <select size="1" name="dayCommissionList" class="form-control">
                      <c:forEach begin="0" end="50" varStatus="loop">
                            <option  value="${loop.current}" ${loop.current == carWashAddServiceForm.dayCommissionList[mainLoop.current] ? "selected" : ""}>${loop.current}</option>
                      </c:forEach>
                    </select>

                  </div>

                  <div class="col-xs-4 col-md-2 col-lg-2">
                    <select size="1" name="nightCommissionList" class="form-control">
                      <c:forEach begin="0" end="50" varStatus="loop">
                            <option  value="${loop.current}" ${loop.current == carWashAddServiceForm.nightCommissionList[mainLoop.current] ? "selected" : ""}>${loop.current}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
              </div>
            </c:forEach>
          </c:if>

        </div>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-md-7 col-lg-offset-1 col-lg-6">
            <p class="asterisk"><sup>*</sup>Эти поля обязательны к заполнению</p>
          </div>
        </div>

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


</div>


</body>
</html>




<script language="javascript">
  function add_input()
  {
    $("#serviceLine").clone().removeAttr('id').addClass("addblock").appendTo("#servicePlace");
  }

  function del_input()
  {
    $(".addblock").last().remove();
  }

</script>
