<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../admin_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Заказ: </p>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1" >
      <form:form method="post" action="/admin/order/add" cssClass="form-horizontal " commandName="orderForm">
      <div class="orderForm">
        <div class="form-group">
          <label class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Номер:</label>
          <div class="col-xs-8 col-md-5 col-lg-4 staticTextInForm" >
            <c:out value="${orderForm.carNumber}" />
            <form:input path="carNumber" value="${orderForm.carNumber}" type="hidden" />
          </div>
        </div>

        <div class="form-group">
          <label for="carBrand" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Марка:<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <c:choose>
              <c:when test="${orderFormData.carBrandList != null}">
                <form:select size="1"  multiple="false" path="carBrandId" cssClass="form-control" id="carBrand"  itemLabel="${orderForm.carBrandId}">
                  <form:option value="-1">&ensp;</form:option>
                  <c:forEach items="${orderFormData.carBrandList}" var="carBrand" varStatus="loop">
                    <form:option value="${carBrand.id}"> ${carBrand.name} </form:option>
                  </c:forEach>
                </form:select>
                <form:errors path="carBrandId" cssClass="errorMessage"></form:errors>
              </c:when>
              <c:otherwise>
                <div class="staticTextInForm">
                  <c:out value="${orderFormData.carBrandName}" />
                </div>
                <form:input path="carBrandId" value="${orderForm.carBrandId}" type="hidden" />
              </c:otherwise>
            </c:choose>


          </div>
        </div>

        <div class="form-group">
          <label class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Категория:</label>
          <div class="col-xs-8 col-md-5 col-lg-4 staticTextInForm" >
            <c:out value="${orderFormData.category.name} - ${orderFormData.category.description}" />
            <form:input path="categoryId" value="${orderForm.categoryId}" type="hidden" />
          </div>
        </div>

        <div class="form-group">
          <label for="carBrand" class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Бокс:<sup>*</sup>:</label>
          <div class="col-xs-8 col-md-5 col-lg-4">
            <form:select size="1"  multiple="false" path="boxNumber" cssClass="form-control" id="carBrand"  itemLabel="${orderForm.boxNumber}">
              <form:option value=""> </form:option>
              <c:forEach items="${orderFormData.availableBoxList}" var="box" varStatus="loop">
                <form:option value="${box}"> ${box} </form:option>
              </c:forEach>
            </form:select>
            <form:errors path="boxNumber" cssClass="errorMessage"></form:errors>
          </div>
        </div>

        <div class="form-group">
          <label class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Оплата:</label>
          <div class="col-xs-8 col-md-5 col-lg-4 staticTextInForm" >
                <c:out value="${orderForm.paymentMethod.lable}" />
                <form:input path="paymentMethod" value="${orderForm.paymentMethod}" type="hidden" />
          </div>
        </div>

        <div class="orderList">
        <c:forEach items="${orderForm.serviceIdList}" var="ServiceInOrder" varStatus="row">


            <div class="form-group" ${row.count==1 ? "id=\"firstService\"" : ""}>
              <label class="col-xs-4 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2">Услуги:<sup>*</sup>:</label>
              <div class="col-xs-8 col-md-5 col-lg-4">

                <form:select size="1"  multiple="false" path="serviceIdList[${row.index}].CarWashServiceId" cssClass="form-control"
                                                      itemLabel="serviceIdList[${row.index}].CarWashServiceId" onChange="SelectService(this)">
                  <c:forEach items="${orderFormData.carWashServices}" var="service" varStatus="loop">
                    <form:option value="${service.id}" countable="${service.serviceName.countable}" additionPrice="${service.serviceName.additionPrice}">
                                  ${service.serviceName.name} - ${service.price} грн.
                    </form:option>
                  </c:forEach>
                </form:select>


                <form:select size="1" multiple="false" path="serviceIdList[${row.index}].count" cssClass="form-control count"
                                                      cssStyle="display: none" itemLabel="serviceIdList[${row.index}].count" >
                    <c:forEach begin="1" end="10" varStatus="count">
                      <form:option value="${count.current}" >${count.current} шт.</form:option>
                    </c:forEach>
                </form:select>

                <form:select size="1" multiple="false" path="serviceIdList[${row.index}].additionPrice" cssClass="form-control additionPrice"
                             cssStyle="display: none" itemLabel="serviceIdList[${row.index}].additionPrice">
                  <c:forEach begin="0" end="1000" step="50" varStatus="count">
                    <form:option value="${count.current}" >${count.current} грн.</form:option>
                  </c:forEach>
                </form:select>

              </div>
            </div>



        </c:forEach>
        </div>

        <div class="form-group">
          <div class="col-xs-12 col-md-offset-1 col-lg-offset-1 ">
            <button type="button" onclick="add_input()" class="btn btn-success " value="">&nbsp;&nbsp;Добавить поле&nbsp;&nbsp;</button>
            <button type="button" onclick="del_input()" class="btn btn-danger  ">&nbsp;&nbsp;Удалить поле&nbsp;&nbsp;</button>
          </div>
        </div>


      </div>
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

<script language="javascript">
  var selectCount;

  $( window ).load(function() {
    selectCount = $(".orderList .form-group").length;

    $(".count").each(function (sel) {
      if ($(this).val() != 1) {
        $(this).show();
      };

    });

    $(".additionPrice").each(function (sel) {
      if ($(this).val() != 0) {
        $(this).show();
      };

    });

  });


  function SelectService(obj)
  {
    $(obj).nextAll().hide();

    var isCountable = obj.options[obj.selectedIndex].getAttribute("countable");

        if (isCountable == "true") {
            $(obj).nextAll(".count").show();
        }

    var isAdditionPrice = obj.options[obj.selectedIndex].getAttribute("additionPrice");

        if (isAdditionPrice == "true") {
          $(obj).nextAll(".additionPrice").show();

          selectCount = selectCount+1;
          console.log(selectCount);
        }
  }

  function add_input()
  {
    $("#firstService").clone().removeAttr('id').addClass("addblock af").appendTo(".orderList");
    selectCount = $(".orderList .form-group").length;

    var i = selectCount -1;

    $(".orderList .form-group:last-child select").each(function(index) {
        if (index == 0 ) {
          $(this).attr("name", "serviceIdList[" + i + "].CarWashServiceId");
          $(this).attr("id", "serviceIdList" + i + ".CarWashServiceId");
        }

        if (index == 1) {
          $(this).attr("name", "serviceIdList[" + i + "].count");
          $(this).attr("id", "serviceIdList" + i + ".count");
        }

        if (index == 2) {
          $(this).attr("name", "serviceIdList["+ i + "].additionPrice");
          $(this).attr("id", "serviceIdList" + i + ".additionPrice");
        }
    });

  }

  function del_input()
  {
    $(".addblock").last().remove();
    selectCount = $(".orderList .form-group").length;

  }

</script>





