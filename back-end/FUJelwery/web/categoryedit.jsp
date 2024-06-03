
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category Editing Page</title>
    </head>
    <body>
        <jsp:include page="/menu.jsp" flush="true" />
        

        <h1>Category Edit </h1>
        <p> Login user: ${sessionScope.adminsession.username}</p>
        
        <% String error1 = (String) request.getAttribute("error"); %>
        <% if (error1 != null) {%>
        <h4 style="color: red; text-align: center"> <%= error1%> </h4>
        <% }%>

        <form action="./CategoryController" method="POST">
            <table>

                <tr><td>ID</td><td><input name="id" value="${requestScope.category.categoryID}" required="Please enter"</td></tr>
                <tr><td>Category Name</td><td><input name="categoryName" value="${requestScope.category.categoryName}" required="Please enter" </td></tr>
                <tr><td colspan="2">
                        <input name="action" value="${requestScope.nextaction}" type="hidden">
                        <input type="submit" value="Save">
                    </td></tr>
            </table>

        </form>
    </body>
</html>
