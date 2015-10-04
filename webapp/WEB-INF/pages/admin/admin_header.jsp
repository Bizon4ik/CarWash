<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
  <c:if test="${calendar != null}" >
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
  </c:if>

  <link rel="stylesheet" type="text/css" href="/resources/css/owner/admin_main.css" />
  <link rel="stylesheet" type="text/css" href="/resources/css/admin/admin.css" />

  <title>CarWash</title>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <ul class="nav navbar-nav">
      <li> <div id="userName"><sub class="firstLetterUppercase">${sessionScope.CurrentCarWashUser.name}</sub></div></li>

      <li class="active"> <a href="/admin/main">Главная</a> </li>

      <li class="dropdown" id="addOrder">
        <a href="/admin/order/getCarNumber" role="button" aria-expanded="false" >Добавить</a>
      </li>

      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Мойщики<span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
          <li><a href="/admin/user/all">Все мойщики</a></li>
          <li><a href="">Удалить мойщика</a></li>
          <li role="separator" class="divider"></li>
          <li><a href="/admin/user/addWasherMan">Добавить мойщика</a></li>
        </ul>
      </li>

      <li class="dropdown">
        <a href="/admin/gangs/chooseBox" role="button">Смены</a>
      </li>

      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Статистика<span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
          <li><a href="/adminMain/statistic?action=generalforcarwash">Общая</a></li>
          <li><a href="/adminMain/statistic?action=sericeforcarwash">По услугам</a></li>
        </ul>
      </li>
    </ul>

    <ul class="nav navbar-nav navbar-right" id="logout">
      <li>
        <a href="/logout" role="button" aria-expanded="false">Выход</a>
      </li>
    </ul>

  </div><!-- /.container-fluid -->
</nav>

<div class="container-fluid" >
  <c:if test='${globalError != null }' >
    <div class="row">
      <div class="col-xs-12" >
        <div class="bg-danger globarError">
          <c:forEach var="row" items="${globalError}" >
            <p>${row}</p>
          </c:forEach>

        </div>
      </div>
    </div>



  </c:if>

  <c:if test="${globalMsg != null}"  >
    <div class="row">
      <div class="col-xs-12" >
        <p class="bg-success globalMsg">
          <c:out value="${globalMsg}" />
        </p>
      </div>
    </div>
  </c:if>

</div>