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

  <link rel="stylesheet" type="text/css" href="/resources/css/admin/admin_main.css" />

  <title>CarWash</title>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <ul class="nav navbar-nav">
      <li> <div id="userName"><sub>${authorization.userName}</sub></div></li>

      <li class="active"> <a href="/owner/main">Главная</a> </li>

      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Мойки<span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
          <li><a href="/owner/carwash/all">Все мойки</a></li>
          <li><a href="/owner/carwash/add">Добавить</a></li>
          <li><a href="/owner/carwash/delete">Удалить</a></li>
        </ul>
      </li>

      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Услуги<span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
          <li><a href="/owner/service/carwash/all">Просмотреть услугу на мойке</a></li>
          <li><a href="/owner/service/carwash/add">Добавить услугу на мойку</a></li>
          <li><a href="/owner/service/carwash/delete">Удалить услугу c мойки</a></li>
          <li role="separator" class="divider"></li>
          <li><a class="dropdown-right" href="/owner/service/add">Добавить услугу</a></li>
          <li><a class="dropdown-right" href="/owner/service/all">Все услуги</a></li>
          <li><a class="dropdown-right" href="/owner/service/delete">Удалить услугу</a></li>
        </ul>
      </li>

      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Пользователи<span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
          <li><a href="/adminMain/User?action=all">Все пользователи</a></li>
          <li><a href="/adminMain/User?action=add">Добавить</a></li>
          <li><a href="/adminMain/User?action=delete">Удалить</a></li>
        </ul>
      </li>

      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Категории/Авто<span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
          <li><a href="/owner/category/add">Добавить категорию</a></li>
          <li><a href="/owner/category/all">Все категории</a></li>
          <li><a href="/owner/category/delete">Удалить категории</a></li>
          <li role="separator" class="divider"></li>
          <li><a class="dropdown-right" href="/adminMain/carCategoty?action=allcar">Все АвтоБренды</a></li>
          <li><a class="dropdown-right" href="/adminMain/carCategoty?action=addCar">Добавить АвтоБренд</a></li>
          <li><a class="dropdown-right" href="/adminMain/carCategoty?action=deletecar">Удалить АвтоБренд</a></li>
        </ul>
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
        <a href="/login?exit=1" role="button" aria-expanded="false">Выход</a>
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