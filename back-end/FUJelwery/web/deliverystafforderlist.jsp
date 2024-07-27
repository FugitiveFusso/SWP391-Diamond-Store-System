<%@page import="com.khac.swp.fuj.order.OrderDTO"%>
<%@page import="com.khac.swp.fuj.ring.RingDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Delivery Management</title>
        <link rel="icon" type="image/x-icon" href="images/Screenshot__656_-removebg-preview.png">
        <link rel="stylesheet" href="css/navbar.css">
        <script src="https://unpkg.com/@phosphor-icons/web"></script>
        <link rel="stylesheet" href="css/customer_list.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <script src="js/pagination.js"></script>
        <link rel="stylesheet" href="css/pagination.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.js"
                integrity="sha512-8Z5++K1rB3U+USaLKG6oO8uWWBhdYsM3hmdirnOEWp8h2B1aOikj5zBzlXs8QOrvY9OxEnD2QDkbSKKpfqcIWw=="
        crossorigin="anonymous"></script>
        <script src="js/sidenav.js"></script>
        <script src="js/delivery_confirmation_deliverystaff.js"></script>
        <style>
            .pagination {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 20px;
            }

            .pagination a, .pagination span {
                text-decoration: none;
                color: #1A1A3D;
                background-color: #fff;
                border: 1px solid #1A1A3D;
                border-radius: 50%;
                padding: 10px;
                width: 40px;
                height: 40px;
                margin: 0 5px;
                display: flex;
                justify-content: center;
                align-items: center;
                font-size: 16px;
                transition: background-color 0.3s, color 0.3s;
            }

            .pagination a:hover {
                background-color: #1A1A3D;
                color: #fff;
            }

            .pagination a.disabled, .pagination span.disabled {
                pointer-events: none;
                opacity: 0.5;
            }

            .pagination a.active, .pagination span.active {
                background-color: #1A1A3D;
                color: #fff;
            }

        </style>
    </head>
    <body>

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
                <div class="item"><a href="DeliveryStaffOrderController"><i class="fas fa-layer-group"></i>Delivery Order</a></div>
                <div class="item"><a href="DeliveryHistory"><i class="fas fa-clock-rotate-left"></i>Delivery History</a></div>
                <div class="item"><a href="deliverystaffaccount.jsp"><i class="fas fa-user"></i>Account</a></div>
                <div class="item"><a href="deliverystafflogin?action=logout"><i class="fas fa-right-from-bracket"></i>Logout</a></div>
            </div>
        </div>

        <div class="list-container">
            <div class="smaller-container">
                <div class="list1">
                    <div class="list-intro-left">
                        <div class="left-icon">
                            <i class='bx bxs-package'></i>
                        </div>
                        <div class="left-info">
                            <div class="list-title">Order Delivery</div>
                            <div class="">List of Orders for Delivery</div>
                        </div>
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
                                <th>Order ID</th>
                                <th>Username</th>
                                <th>Ring Name</th>
                                <th>Ring Size</th>
                                <th>Date of Purchase</th>
                                <th>Destination</th>
                                <th>Total Price</th>
                                <th>Status</th>
                                <th>Delivery Status</th>                      
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<OrderDTO> list = (List<OrderDTO>) request.getAttribute("deliverystafforderlist");
                                for (OrderDTO deliveryorder : list) {
                                    pageContext.setAttribute("deliveryorder", deliveryorder);
                            %>
                            <tr>
                                <td>${deliveryorder.orderID}</td>
                                <td><a href="Delivery_Customer_Controller?action=details&id=${deliveryorder.userID}">${deliveryorder.userName}</a></td>
                                <td>${deliveryorder.ringName}</td>
                                <td>${deliveryorder.ringSize}</td>
                                <td>${deliveryorder.orderDate}</td>
                                <td>${deliveryorder.address}</td>
                                <td>${deliveryorder.totalPrice} VND</td>
                                <td class="<%= "verified".equals(deliveryorder.getStatus()) ? "status-verified" : "shipping".equals(deliveryorder.getStatus()) ? "status-shipping" : "status-default"%>">
                                    ${deliveryorder.status}
                                </td>
                                <td>
                                    <form id="form-shipping-${deliveryorder.orderID}" action="DeliveryStaffOrderController" method="POST">
                                        <input name="action" value="shipping" type="hidden">
                                        <input name="orderID" value="${deliveryorder.orderID}" type="hidden">
                                        <button type="button" class="btn" onclick="confirmAction('shipping', '${deliveryorder.orderID}', '${deliveryorder.status}')">On shipping</button>
                                    </form>
                                    <form id="form-delivered-${deliveryorder.orderID}" action="DeliveryStaffOrderController" method="POST">
                                        <input name="action" value="delivered" type="hidden">
                                        <input name="orderID" value="${deliveryorder.orderID}" type="hidden">
                                        <input name="ringID" value="${deliveryorder.ringID}" type="hidden">
                                        <button type="button" class="btn2" onclick="confirmAction('delivered', '${deliveryorder.orderID}', '${deliveryorder.status}')">Delivered</button>
                                    </form>
                                </td>
                            </tr>
                            <%
                                }
                            %>    
                        </tbody>
                    </table>    
                    <!-- Pagination controls -->
                    <div class="pagination">
                        <% int currentPage = (Integer) request.getAttribute("currentPage");
                            int totalPages = (Integer) request.getAttribute("totalPages");
                            String sortCol = (String) request.getAttribute("sortCol");
                            String keyword = (String) request.getAttribute("keyword");
                            int maxPagesToShow = 5; // Adjust this to change how many pages to show around current page
                        %>

                        <% if (currentPage > 1) {%>
                        <a href="?page=<%= currentPage - 1%><%= !sortCol.isEmpty() ? "&colSort=" + sortCol : ""%><%= !keyword.isEmpty() ? "&keyword=" + keyword : ""%>" class="pagination-arrow">&#8249;</a>
                        <% } else { %>
                        <span class="pagination-arrow disabled">&#8249;</span>
                        <% } %>

                        <%
                            int startPage = Math.max(1, currentPage - (maxPagesToShow / 2));
                            int endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

                            if (startPage > 1) {%>
                        <a href="?page=1<%= !sortCol.isEmpty() ? "&colSort=" + sortCol : ""%><%= !keyword.isEmpty() ? "&keyword=" + keyword : ""%>" class="pagination-number">1</a>
                        <% if (startPage > 2) { %>
                        <span class="pagination-ellipsis">...</span>
                        <% }
                            }

                            for (int i = startPage; i <= endPage; i++) {%>
                        <a href="?page=<%= i%><%= !sortCol.isEmpty() ? "&colSort=" + sortCol : ""%><%= !keyword.isEmpty() ? "&keyword=" + keyword : ""%>"
                           class="pagination-number <%= (i == currentPage) ? "active" : ""%>"><%= i%></a>
                        <% }

                            if (endPage < totalPages) { %>
                        <% if (endPage < totalPages - 1) { %>
                        <span class="pagination-ellipsis">...</span>
                        <% }%>
                        <a href="?page=<%= totalPages%><%= !sortCol.isEmpty() ? "&colSort=" + sortCol : ""%><%= !keyword.isEmpty() ? "&keyword=" + keyword : ""%>" class="pagination-number"><%= totalPages%></a>
                        <% }
                        %>

                        <% if (currentPage < totalPages) {%>
                        <a href="?page=<%= currentPage + 1%><%= !sortCol.isEmpty() ? "&colSort=" + sortCol : ""%><%= !keyword.isEmpty() ? "&keyword=" + keyword : ""%>" class="pagination-arrow">&#8250;</a>
                        <% } else { %>
                        <span class="pagination-arrow disabled">&#8250;</span>
                        <% }%>
                    </div>
                </div>
            </div>
        </div>


        <script src="js/pagination.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.js" integrity="sha512-8Z5++K1rB3U+USaLKG6oO8uWWBhdYsM3hmdirnOEWp8h2B1aOikj5zBzlXs8QOrvY9OxEnD2QDkbSKKpfqcIWw==" crossorigin="anonymous"></script>
        <script src="js/sidenav.js"></script>
    </body>

</html>
