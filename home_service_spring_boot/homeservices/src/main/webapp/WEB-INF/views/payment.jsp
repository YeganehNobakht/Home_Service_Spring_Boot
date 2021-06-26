
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
    <link href="<c:url value="/static/css/firstPage.css"/>" rel="stylesheet"/>
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
<h5 class="d-flex justify-content-center text-info">${message}</h5>
<h5 class="d-flex justify-content-center text-danger">${error}</h5>

<div class="d-flex justify-content-center align-items-center text-black timeBox">
    <p id="mins" class="text-danger"> </p>
    <p id="secs" class="text-danger"> </p>
</div>
<form:form modelAttribute="paymentDto" method="post" action="/payment/complete">

    <p class="d-flex justify-content-center align-items-center text-black">The amount payable is ${sessionScope.price}</p>
    <table class="table table-striped table-secondary d-flex justify-content-center align-items-center p-3 m-3">
        <tr>
            <td><form:label path="cardNumber">Card Number</form:label></td>

            <td colspan="2"><form:input path="cardNumber" placeHolder="Card Number"/></td>
        </tr>
        <tr>
            <td></td>
            <td colspan="2">
                <p class="text-danger">${cardNumber}</p>
            </td>
        </tr>
        <tr>
            <td><form:label path="ccv2" >CCV2</form:label></td>

            <td colspan="2"><form:input path="ccv2" placeHolder="CCV2"/></td>
        </tr>
        <tr>
            <td></td>
            <td colspan="2">
                <p class="text-danger">${ccv2}</p>
            </td>
        </tr>
        <tr>
            <td><form:label path="" >Expire card Date</form:label></td>

            <td><form:input path="cardExpirationMonth" placeHolder="month"/></td>
            <td><form:input path="cardExpirationYear" placeHolder="year"/></td>
        </tr>
        <tr>
            <td></td>

            <td><p class="text-danger">${cardExpirationMonth}</p></td>
            <td><p class="text-danger">${cardExpirationYear}</p></td>
        </tr>
        <tr>
            <td><form:label path="captcha" >Security Code</form:label></td>
            <td colspan="2">
<%--                <img src="${pageContext.request.contextPath }/captcha">--%>
                <img src="/captcha" alt="">
                <form:input type="text" path="captcha" required="required" style="margin-top: 5px;"/>
        </tr>
        <tr>
            <td></td>
            <td colspan="2" cssClass="text-danger">
                    ${error}
            </td>
        </tr>
        <tr>
            <td><form:label path="dynamicPassword" >Dynamic Password</form:label></td>

            <td colspan="2"><form:input path="dynamicPassword" placeHolder="Dynamic Password"/></td>
        </tr>
        <tr>
            <td></td>

            <td colspan="2"><p class="text-danger">${dynamicPassword}</p></td>
        </tr>
        <tr>
            <td><form:label path="email" >Email</form:label></td>

            <td colspan="2"><form:input path="email" placeHolder="email"/></td>
        </tr>
        <tr>
            <td></td>
            <td colspan="2"><form:button id="Pay" name="Pay">Pay</form:button></td>
        </tr>




    </table>

</form:form>

<%
    }
%>

<script>
    //JavaScript doesn't have a "time period" object, so I'm assuming it get it as a string
    var timePeriod = "00:10:00"; //I assume this is 10 minutes, so the format is HH:MM:SS

    var parts = timePeriod.split(/:/);
    var timePeriodMillis = (parseInt(parts[0], 10) * 60 * 60 * 1000) +
        (parseInt(parts[1], 10) * 60 * 1000) +
        (parseInt(parts[2], 10) * 1000);
    // The time we want to countdown to
    var now1 = new Date().getTime();
    var newDate  = new Date();
    var countDownDate = newDate.setTime(now1 + timePeriodMillis);

    // Run myfunc every second
    var myfunc = setInterval(function() {

        var now = new Date().getTime();

        var timeleft = countDownDate - now;
        console.log("left:  "+timeleft)

        // Calculating the minutes and seconds left
        var minutes = Math.floor((timeleft % (1000 * 60 * 60)) / (1000 * 60));
        console.log("min:  "+minutes)
        var seconds = Math.floor((timeleft % (1000 * 60)) / 1000);
        // Result is output to the specific element
        document.getElementById("mins").innerHTML = minutes + " min  "
        document.getElementById("secs").innerHTML = seconds + " sec  "

        // Redirect when countdown is over
        if (timeleft < 0) {
            window.location.href="http://localhost:8080/payment/timeout"
        }
    }, 1000);
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>

</body>
</html>
