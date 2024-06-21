<%@page import="com.khac.swp.fuj.order.OrderDTO"%>
<%@page import="com.khac.swp.fuj.ring.RingDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/navbar.css">
        <script src="https://unpkg.com/@phosphor-icons/web"></script>
        <link rel="stylesheet" href="css/customer_list.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <script src="js/pagination.js"></script>
        <link rel="stylesheet" href="css/pagination.css">



    </head>
    <body>
        <%--<%@ include file="/productmenu.jsp" %>--%>

        <div class="menu-btn">
            <div class="btn-cover">
                <i class="fas fa-bars"></i>
            </div>            
        </div>

        <div class="side-bar">
            <header>
                <div class="close-btn">
                    <i class="fa-solid fa-xmark"></i>
                </div>
                <img src="images/Screenshot (656).png">
                <h1>${sessionScope.salessession.lastname} ${sessionScope.salessession.firstname}</h1>
            </header>

            <div class="menu">               
                <div class="item"><a class="sub-btn"><i class="fas fa-ring"></i>View Product
                        <i class="fas fa-angle-right dropdown"></i>
                        <div class="sub-menu">
                            <a href="DiamondController" class="sub-item">Diamond List</a>
                            <a href="RingController" class="sub-item">Ring List</a>
                            <a href="CollectionController" class="sub-item">Collection List</a>

                        </div>
                    </a>
                    <a href="warrantydetails.jsp"></a>
                </div>
                <div class="item"><a class="sub-btn"><i class="fas fa-tags"></i>View Product Price
                        <i class="fas fa-angle-right dropdown"></i>
                        <div class="sub-menu">
                            <a href="DiamondPriceController" class="sub-item">Diamond Price List</a>
                            <a href="RingPlacementPriceController" class="sub-item">Ring Price List</a>                                                    
                        </div>
                    </a>
                </div>
                <div class="item"><a class="sub-btn"><i class="fas fa-folder"></i>View Document
                        <i class="fas fa-angle-right dropdown"></i>
                        <div class="sub-menu">
                            <a href="VoucherController" class="sub-item">Voucher List</a>
                            <a href="WarrantyController" class="sub-item">Warranty List</a> 
                            <a href="CertificateController" class="sub-item">Certificate List</a>  
                        </div>
                    </a>
                </div>

                <div class="item"><a href="CategoryController"><i class="fas fa-layer-group"></i>View Category</a></div>

                <div class="item"><a href="salesstaffaccount.jsp"><i class="fas fa-user"></i>Account</a></div>
                <div class="item"><a href="saleslogin?action=logout"><i class="fas fa-right-from-bracket"></i>Logout</a></div>

            </div>
        </div>



        <div class="list-container">
            <div class="smaller-container">
                <div class="list1">
                    <div class="list-intro-left">
                        <div class="left-icon">
                            <i class='bx bxs-check-shield'></i>
                        </div>
                        <div class="left-info">
                            <div class="list-title">Ring List</div>
                            <div class="">List of Ring</div>
                        </div>
                    </div>
                    
                </div>
                <div class="list">
                    <form action='' method=GET id="searchbox"> 
                        <input name=keyword type=text value="<%=request.getParameter("keyword") != null ? request.getParameter("keyword") : ""%>">
                        <input type=submit value=Search >
                    </form>

                    <table>
                        <tr>
                            <th>Order ID</th>
                            <th>Username</th>
                            <th>Ring Name</th>
                            <th>Ring Size</th>
                            <th>Date of Purchase</th>
                            <th>Destination</th>
                            <th>Warranty ID</th>
                            <th>Price</th>
                            <th>Accept</th>
                        </tr>
                        <%
                            List<OrderDTO> list = (List<OrderDTO>) request.getAttribute("salesorderlist");
                            for (OrderDTO salesorder : list) {
                                pageContext.setAttribute("salesorder", salesorder);
                        %>
                        <tr>
                            <td>${salesorder.orderID}</td>
                            <td><a href="Staff_Customer_Controller?action=details&id=${salesorder.userID}">${salesorder.userName}</td>
                            <td>${salesorder.ringName}</td>
                            <td>${salesorder.ringSize}</td>
                            <td>${salesorder.orderDate}</td>
                            <td>${salesorder.address}</td>
                            <td>
                                <input type="text" name="warrantyID" id="warrantyID_${salesorder.warrantyID}" placeholder="Enter Warranty ID" required>
                            </td>
                            <td>${salesorder.totalPrice}</td>
                            <td>
                                <form action="SalesStaffController" method="POST">
                                    <input name="action" value="accept" type="hidden">
                                    <input name="id" value="${salesorder.orderID}" type="hidden">
                                    <input type="submit" value="Accept">
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>    


                        </td></tr>
                    </table>
                    <script src="js/pagination.js"></script>
                    <script src="js/pagination.js"></script><script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.js"
                                                                    integrity="sha512-8Z5++K1rB3U+USaLKG6oO8uWWBhdYsM3hmdirnOEWp8h2B1aOikj5zBzlXs8QOrvY9OxEnD2QDkbSKKpfqcIWw=="
                    crossorigin="anonymous"></script>
                    <script src="js/sidenav.js"></script>
                    </body>
                    </html>