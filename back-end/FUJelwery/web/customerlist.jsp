

<%@page import="com.khac.swp.fuj.users.UserDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customers Management Page</title>
        <link rel="stylesheet" href="css/navbar.css">
        <link rel="stylesheet" href="css/customer_list.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <script src="js/pagination.js"></script>
        <link rel="stylesheet" href="css/pagination.css">
        <script src="https://unpkg.com/@phosphor-icons/web"></script>

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
                <h1>${requestScope.admin.lastname} ${requestScope.admin.firstname}</h1>
            </header>

            <div class="menu">               
                <div class="item"><a class="sub-btn"><i class="fas fa-table"></i>View List
                        <i class="fas fa-angle-right dropdown"></i>
                        <div class="sub-menu">
                            <a href="CustomerController" class="sub-item">Customer List</a>
                            <a href="AdminController" class="sub-item">Administrator List</a>
                            <a href="DeliveryStaffController" class="sub-item">Delivery Staff List</a>
                            <a href="SalesController" class="sub-item">Sale Staff List</a>
                            <a href="ManagerController" class="sub-item">Manager List</a>
                        </div>
                    </a>
                </div>

                <div class="item"><a href="PostController"><i class="fas fa-file"></i>Posts</a></div>

                <div class="item"><a href="adminaccount.jsp"><i class="fas fa-user"></i>Account</a></div>
                <div class="item"><a href="adminlogin?action=logout"><i class="fas fa-right-from-bracket"></i>Logout</a></div>

            </div>
        </div>

        <!--            <div class="header_menu">
                    <div id="mySidenav" class="sidenav menu">
                        <a href="javascript:void(0)" id="closebtn" class="closebtn" onclick="closeNav()">&times;</a>
                        <ul>                   
                            <li>
                                <a href="#">
                                    <i class="icon ph-bold ph-user"></i>
                                    <span class="text">View List</span>
                                    <i class="arrow ph-bold ph-caret-down"></i>
                                </a>
                                <ul class="sub-menu">
                                    <li>
                                        <a href="CustomerController">
                                            <span class="text">Customer List</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="AdminController">
                                            <span class="text">Administrator List</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="DeliveryStaffController">
                                            <span class="text">Delivery Staff List</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="SalesController">
                                            <span class="text">Sale Staff List</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="ManagerController">
                                            <span class="text">Manager List</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="active">
                                <a href="PostController">
                                    <i class="icon ph-bold ph-file-text"></i>
                                    <span class="text">Posts</span>
                                </a>
                            </li>                   
                        </ul>
                        <div class="menu">                  
                            <ul>
                                <li>
                                    <a href="adminaccount.jsp">
                                        <i class="icon ph-bold ph-user"></i>
                                        <span class="text">Account</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="adminlogin?action=logout">
                                        <i class="icon ph-bold ph-sign-out"></i>
                                        <span class="text">Logout</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    
        
                    <span class="cainut" style="font-size:30px;cursor:pointer;" onclick="openNav()">&#9776; Menu</span>
                </div>-->


        <div class="list-container">

            <div class="smaller-container">
                <div class="list1">
                    <div class="list-intro-left">
                        <div class="left-icon">
                            <i class='bx bx-user'></i>
                        </div>
                        <div class="left-info">
                            <div class="list-title">Customer List</div>
                            <div class="">List of Customer</div>
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
                                <th>Customer ID</th>
                                <th><a href=?colSort=username>User Name</a></th>
                                <th><a href=?colSort=firstname>First Name</a></th>
                                <th><a href=?colSort=lastname>Last Name</a></th>
                                <th><a href=?colSort=email>Email</a></th>
                                <th>Address</th>
                                <th>Status</th>
                                <th>Active</th>
                                <th>Banned</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<UserDTO> list = (List<UserDTO>) request.getAttribute("customerlist");
                                for (UserDTO customer : list) {
                                    pageContext.setAttribute("customer", customer);
                            %>
                            <tr>
                                <td>
                                    <a href="CustomerController?action=details&id=${customer.userid}">   ${customer.userid}</td>
                                <td>${customer.username}</td>
                                <td>${customer.firstname}</td>
                                <td>${customer.lastname}</td>
                                <td>${customer.email}</td>
                                <td>${customer.address}</td>
                                <td style="${customer.status == 'active' ? 'color: green; font-weight: bold;' : (customer.status == 'banned' ? 'color: red; font-weight: bold;' : '')}">
                                    ${customer.status}
                                </td>
                                <!--<td>${customer.status}</td>-->
                                <td>
                                    <form action="CustomerController" method="POST" class="input">
                                        <input name="action" value="active" type="hidden">
                                        <input name="id" value="${customer.userid}" type="hidden">
                                        <input type="submit" value="Active">
                                    </form>
                                </td>
                                <td>
                                    <form action="CustomerController" method="POST" class="input">
                                        <input name="action" value="banned" type="hidden">
                                        <input name="id" value="${customer.userid}" type="hidden">
                                        <input type="submit" value="Banned">
                                    </form>
                                </td>
                                <td>
                                    <form action="CustomerController" method="POST" class="input">
                                        <input name="action" value="delete" type="hidden">
                                        <input name="id" value="${customer.userid}" type="hidden">
                                        <input type="submit" value="Delete">
                                    </form>
                                </td>
                            </tr>
                            <%
                                }
                            %>    
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

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.js"
                integrity="sha512-8Z5++K1rB3U+USaLKG6oO8uWWBhdYsM3hmdirnOEWp8h2B1aOikj5zBzlXs8QOrvY9OxEnD2QDkbSKKpfqcIWw=="
        crossorigin="anonymous"></script>
        <script src="js/sidenav.js"></script>

    </body>
</html>
