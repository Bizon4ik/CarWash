<%@  page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
  <link rel="stylesheet" type="text/css" href="/resources/css/login/login.css" />
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <title>CarWash</title>
</head>
<body>

<div id="caption">
  created by Bizon4ik
</div>

<div class = "row">
    <img class="img-responsive" id="logo" src="/resources/img/logo.png" alt="logo" />
</div>

<div class = "row">
    <form class="form-horizontal
                col-lg-4 col-lg-offset-4
                col-md-6 col-md-offset-3
                col-xs-12
                col-sm-8 col-sm-offset-2" method="post" action="/login">

      <div class="form-group">
        <label for="login" class="col-xs-4 col-md-3 control-label">Login</label>
        <div class="col-xs-8 col-md-9">
          <input type="text" class="form-control" id="login" placeholder="login" name="login" value="${param.login}">
        </div>
      </div>

      <div class="form-group">
        <label for="password" class="col-xs-4 col-md-3 control-label">Password</label>
        <div class="col-xs-8 col-md-9">
          <input type="password" class="form-control" id="password" placeholder="password" name="password" value="${param.password}">
        </div>
      </div>

      <c:if test="${incorrectLogin != null}" >
          <div class="col-xs-12 errorMessage">
              <p>${incorrectLogin}</p>
          </div>
      </c:if>

      <div class="form-group">
        <div class="col-xs-offset-4 col-xs-4 col-md-offset-3 col-md-4">
          <button type="submit" class="btn btn-default">Sign in</button>

        </div>
      </div>
    </form>
  </div>
</div>

<h1> </h1>


</body>
</html>