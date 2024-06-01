<%-- 
    Document   : postdetails
    Created on : May 26, 2024, 12:26:47 PM
    Author     : phucu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/post_detail.css"/>
    </head>
    <body>
        <jsp:include page="/menu.jsp" flush="true" />

        <div class="post-title">
            <h1>Post Details </h1>         
            <p> Login username: ${sessionScope.adminsession.username}</p>
        </div>

        <div class="content">
            <div class="content1">
                <div class="small-content">
                    <div class="content-left">
                        <div class="content-img">
                            <img src=${requestScope.post.image} width="300px" height="300px">
                        </div>
                    </div>
                    <div class="content-right">
                        <div class="content-intro">
                            <div class="intro-details">
                                <p class="title">ID: ${requestScope.post.id}</p>
                                <p class="name">${requestScope.post.name}</p>
                            </div>                       
                        </div>
                        <div class="description">
                            <p class="description-title">Description</p>
                            <p class="title">${requestScope.post.description}</p>
                        </div>    
                    </div>
                </div>
                <div class="buttons">
                    <form action="PostController" style="padding-top: 10px">
                        <input type=hidden name="action" value="list">
                        <input type=submit value="Return" ></form>

                    <form action="PostController" style="padding-top: 10px">
                        <input type=hidden name="id" value="${requestScope.post.id}">
                        <input type=hidden name="action" value="edit">
                        <input type=submit value="Edit" ></form>     
                </div>
            </div>                   
        </div>

        <!--        <table>
        
                    <tr><td>Post ID</td><td>${requestScope.post.id}</td></tr>
                    <tr><td>Post Name</td><td>${requestScope.post.name}</td></tr>
                    <tr><td>Post Image</td><td><img src=${requestScope.post.image} width="300px" height="300px"></td></tr>
                    <tr><td>Description</td><td>${requestScope.post.description}</td></tr>
        
                </table>
        
                <form action="PostController" style="padding-top: 10px">
                    <input type=hidden name="action" value="list">
                    <input type=submit value="Return" ></form>
        
                <form action="PostController" style="padding-top: 10px">
                    <input type=hidden name="id" value="${requestScope.post.id}">
                    <input type=hidden name="action" value="edit">
                    <input type=submit value="Edit" ></form>-->
    </body>
</html>
