<%@ taglib prefix="p" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link href="<p:url value="/static/css/verifySuccess.css"/>" rel="stylesheet"/>

</head>
<body>
<%--<span class="border border-success">--%>
<%--<div class="container text-center d-flex justify-content-center align-items-center">--%>
<%--    <h3>Congratulations, your account has been verified.</h3>--%>
<%--    <h4><a href="/login">Click here to Login</a></h4>--%>
<%--    <a name="Select" href="/login"><button type="button" class="btn btn-success">Click here to Login</button></a>--%>
<%--</div>--%>
<%--    </span>--%>





<div class="container">
    <div class="row">
        <div class="col-md-6 mx-auto mt-5">
            <div class="payment">
                <div class="payment_header">
                    <div class="check"><i class="fa fa-check" aria-hidden="true"></i></div>
                </div>
                <div class="content">
                    <h1>Verification Successful !</h1>
                    <p>You can login with the following link. </p>
                    <a href="/login">Login</a>
                </div>

            </div>
        </div>
    </div>
</div>





<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>
</body>
</html>




