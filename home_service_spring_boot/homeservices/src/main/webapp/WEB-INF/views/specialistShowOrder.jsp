<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="p" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>show order for specialist</title>
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
    <link href="<p:url value="/static/css/service.css"/>" rel="stylesheet"/>
</head>
<body>
<%

    HttpSession session1 = request.getSession(false);
    Object o = (session1.getAttribute("mySpecialistDto"));
    PrintWriter writer = response.getWriter();
    if (o == null) {
        writer.println("<script type=\"text/javascript\">");
        writer.println("alert('Please login first');");
        writer.println("location='/';");
        writer.println("</script>");
    } else {

%>
<nav class="navbar navbar-expand navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="">
            <img src="data:image/png;base64,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"
                 alt="" width="30" height="25" class="d-inline-block align-text-top">
            Bama</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        ${sessionScope.mySpecialistDto.username}
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
<%--                        <li><a class="dropdown-item" href="#">Account Info</a></li>--%>
                        <li><a class="dropdown-item" href="/specialist/chanePass">Change Password</a></li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item" href="/logout">Logout</a></li>
                    </ul>
                </li>

                <li class="nav-item">
                    <a class="nav-link " aria-current="page" href="/">Home</a>
                </li>

            </ul>

        </div>
        <a class="navbar-brand" href="/specialist/showAllOrders"><img src="/static/image/order.png"
                                                                      alt="" width="30" height="25" class="d-inline-block align-text-top">Orders
        </a>
    </div>
</nav>
<hr>
<div class="m">
    <div class="m1">

        <div class="d-flex flex-column align-items-center mt-5">
            <button type="button" class="btn btn-light btn-lg btn-block w-75 d-flex justify-content-center"
                    onclick="parent.location='/specialist/showOrder'">Show All Orders
            </button>
            <button type="button" class="btn btn-light btn-lg btn-block w-75 d-flex justify-content-center"
                    onclick="parent.location='/specialist/showSpeciality'">Show Speciality
            </button>
            <%--            <button type="button" class="btn btn-light btn-lg btn-block w-75 d-flex justify-content-center"--%>
            <%--                    onclick="parent.location='/specialist/addSpeciality'">Add New Speciality--%>
            <%--            </button>--%>
        </div>

    </div>
    <div class="m11">
        <div>
            <div>
                <div>
                </div>
                <div class="m4">
                    <h5 class="d-flex justify-content-center text-info">${message}</h5>
                    <h5 class="d-flex justify-content-center text-danger">${error}</h5>
                    <h5 class="d-flex justify-content-center text-danger">${alarm}</h5>
                    <form:form modelAttribute="order">

                        <table class="table table-striped table-secondary table-hover">
                            <tr>
                                <td>Id</td>
                                <td>Service</td>
                                <td>Sub Service</td>
                                <td>Work Date</td>
                                <td>Job Description</td>
                                <td>Selection</td>
                            </tr>
                            <c:forEach items="${order}" var="o">
                                <tr>
                                    <td>${o.id}</td>
                                    <td>${o.serviceCategory.name}</td>
                                    <td>${o.subCategory.name}</td>
                                    <td>${o.workDate}</td>
                                    <td>${o.jobDescription}</td>
                                    <td><a name="Select" onclick="choose(${o.id})"><button class="btn btn-info" type="button">Add Suggestion</button></a></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </form:form>
                </div>

            </div>
        </div>

    </div>
        <%
        }
    %>

    <script>
        function choose(id) {
            console.log("in")
           //requestParam-> window.location.href = "http://localhost:8080/suggestion/getSuggestion/?orderId="+id
            window.location.href = "${pageContext.request.contextPath}/suggestion/getSuggestion/"+id

        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"></script>

</body>
</html>
