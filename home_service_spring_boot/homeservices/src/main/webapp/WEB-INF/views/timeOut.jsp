
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>payment</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body style="background-color: #ecdc2f" >
<%
    HttpSession session1 = request.getSession(false);
    Object o = (session1.getAttribute("myCustomerDto"));
    Object allow = (session1.getAttribute("price"));
    PrintWriter writer = response.getWriter();
    if (o == null) {
        writer.println("<script type=\"text/javascript\">");
        writer.println("alert('Please login first');");
        writer.println("location='/';");
        writer.println("</script>");
    }
    if (allow == null) {
        writer.println("<script type=\"text/javascript\">");
        writer.println("alert('Permission denied!');");
        writer.println("location='/customer/register';");
        writer.println("</script>");
    }
    else {

%>
<div class="d-flex justify-content-center align-items-center text-danger">
    <h1>
        Time Out
    </h1>
</div>
    <br>
    <button type="button" width="300px" class="btn btn-secondary btn-lg btn-block" name="currentOrder"
            id="currentOrder" onClick="parent.location='/customer/paymentInformations/'+'${sessionScope.customerOrderDto.id}'">Back to payment information
    </button>



<%
    }
%>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>

</body>
</html>
