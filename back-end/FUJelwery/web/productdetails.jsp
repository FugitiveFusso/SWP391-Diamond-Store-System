<%-- 
    Document   : productdetails
    Created on : Jun 2, 2024, 7:56:34 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/productmenu.jsp" flush="true" />
        <h1>Ring Details </h1>         
        <p> Login username: ${sessionScope.customersession.username}</p>

        <style>
            #searchbox{
                margin-top: 5px;
            }
            body{
                font-size: 16px;
                font-family: Arial, Helvetica, sans-serif;
            }
            table{
                margin-top: 10px
            }
            table, tr, td{
                border-collapse: collapse;
                width: 400px;
                border: 2px solid black;
                text-align: center;
            }
            tr,td{
                padding: 6px 10px;
            }
        </style>
        <table>

            <tr><td>Ring Name</td><td>${requestScope.product.ringName}</td></tr>
            <tr><td>Ring Image</td><td><img src=${requestScope.product.ringImage} width="300px" height="300px"></td></tr>
            <tr><td>Price</td><td>${requestScope.product.price}</td></tr>
            <tr><td>Category</td><td>${requestScope.product.categoryID}</td></tr>
            <tr><td>Collection</td><td>${requestScope.product.collectionID}</td></tr>
            <tr><td>Ring Placement Name</td><td>${requestScope.product.ringPlacementName}</td></tr>
            <tr><td>Material</td><td>${requestScope.product.material}</td></tr>
            <tr><td>Ring Placement Color</td><td>${requestScope.product.ringColor}</td></tr>
            <tr><td>Ring Placement Price</td><td>${requestScope.product.rpPrice}</td></tr>
            <tr><td>Diamond Name</td><td>${requestScope.product.diamondName}</td></tr>
            <tr><td>Diamond Size</td><td>${requestScope.product.diamondSize}</td></tr>
            <tr><td>Carat Weight</td><td>${requestScope.product.caratWeight}</td></tr>
            <tr><td>Color</td><td>${requestScope.product.color}</td></tr>
            <tr><td>Clarity</td><td>${requestScope.product.clarity}</td></tr>
            <tr><td>Cut</td><td>${requestScope.product.cut}</td></tr>
            <tr><td>Diamond Price</td><td>${requestScope.product.diamondPrice}</td></tr>
            <tr><td>Total Price</td><td>${requestScope.product.totalPrice}</td></tr>

        </table>    
        <form action="ProductController" style="padding-top: 10px">
            <input type=hidden name="action" value="list">
            <input type=submit value="Return" ></form>

    </body>
</html>