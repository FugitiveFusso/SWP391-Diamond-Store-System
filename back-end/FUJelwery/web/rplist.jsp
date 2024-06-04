

<%@page import="com.khac.swp.fuj.ringplacementprice.RingPlacementPriceDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/customer_list.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <script src="js/pagination.js"></script>
        <link rel="stylesheet" href="css/pagination.css">

    </head>
    <body>
        <%@ include file="/salesmenu.jsp" %>

        <div class="list-container">
            <div class="smaller-container">
                <div class="list1">
                    <div class="list-intro-left">
                        <div class="left-icon">
                            <i class="fa-solid fa-ring"></i>
                        </div>
                        <div class="left-info">
                            <div class="list-title">Ring Price List</div>
                            <div class="">List of Ring Price</div>
                        </div>
                    </div>
                    <div class="list-intro-right">
                        <form action="RingPlacementPriceController" method="POST" class="input1">
                            <input name="action" value="create" type="hidden">
                            <button type="submit" class="styled-button3">
                                <span>Add a Price</span>
                            </button>
                        </form>
                    </div>
                </div>

                <div class="list">        
                    <form action='' method=GET id="searchbox">
                        <input name=keyword type=text class="search-input" value="<%=request.getParameter("keyword") != null ? request.getParameter("keyword") : ""%>">
                        <button type="submit" class="search-button"><i class="fas fa-search"></i></button>
                    </form>

                    <table id="pagination">
                        <thead>
                            <tr>
                                <th>Ring Placement Price ID</th>
                                <th><a href=?colSort=rName>Ring Placement Name</a></th>
                                <th><a href=?colSort=material>Material</a></th>
                                <th><a href=?colSort=color>Color</a></th>
                                <th><a href=?colSort=rpPrice>Price</a></th>
                                <th>Delete</th>

                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<RingPlacementPriceDTO> list = (List<RingPlacementPriceDTO>) request.getAttribute("rplist");
                                for (RingPlacementPriceDTO rp : list) {
                                    pageContext.setAttribute("rp", rp);
                            %>
                            <tr>
                                <td>
                                    <a href="RingPlacementPriceController?action=details&id=${rp.id}">   ${rp.id}</td>
                                <td>${rp.name}</td>    
                                <td>${rp.material}</td>  
                                <td>${rp.color}</td>  
                                <td>${rp.price}</td>  

                                <td>
                                    <form action="RingPlacementPriceController" method="POST" class="input">
                                        <input name="action" value="delete" type="hidden">
                                        <input name="id" value="${rp.id}" type="hidden">
                                        <input type="submit" value="Delete">
                                    </form>
                                </td>

                            </tr>
                            <%
                                }
                            %>    
<!--                            <tr><td colspan="10">
                                    <form action="RingPlacementPriceController" method="POST">
                                        <input name="action" value="create" type="hidden">
                                        <input type="submit" value="Create">
                                    </form>
                                </td></tr>-->
                        </tbody>
                    </table>
                    <div id="paginationControls" class="pagination-controls">
                        <button id="prevButton" class="pagination-button"><i class="fas fa-chevron-left"></i></button>
                        <div id="pageNumbers"></div>
                        <button id="nextButton" class="pagination-button"><i class="fas fa-chevron-right"></i></button>
                    </div>
                </div>
            </div>
        </div>


        <script src="js/pagination.js"></script>

    </body>
</html>
