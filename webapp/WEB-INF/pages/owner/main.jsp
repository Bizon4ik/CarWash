<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="owner_header.jsp" />


<div class="container-fluid" id="body">

  <div class="row">
    <div class="col-xs-12 col-md-10 col-md-offset-1">
      <p class="userTitle">Статистика за сегодня: </p>
    </div>
  </div>

  <div class="row">
    <table class="table-bordered table-hover table-striped col-xs-12 table">
      <thead>
        <tr>
          <th>Имя мойки</th>
          <th>Количесво текущих заказов</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th><a href="#">Чапаевка</a></th>
          <th>3</th>
        </tr>
        <tr>
          <th><a href="#">Петровка</a></th>
          <th>2</th>
        </tr>
        <tr>
          <th><a href="#">Дарница</a></th>
          <th>Машин нет</th>
        </tr>
      </tbody>
    </table>

  </div>


</div>


</body>
</html>