<%-- 
    Document   : managerdetails
    Created on : May 25, 2024, 11:16:13 AM
    Author     : phucu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manager Management Page</title>
        <link rel="stylesheet" href="css/navbar.css">
        <!--<link rel="stylesheet" href="css/staff_detail.css">-->

        <link rel="stylesheet" href="css/staff_details.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/MaterialDesign-Webfont/3.6.95/css/materialdesignicons.css" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/MaterialDesign-Webfont/3.6.95/css/materialdesignicons.css" />
    </head>
    <body>
        
         <div class="menu">
            <!--you will code the horizontal menu here-->
            <ul class="navbar">
                
                <li class="navbar__link">
                    <a href="#">Products</a>
                    <div class="sub-menu-1">
                        <ul>
                            <li><a href='DiamondController'>Diamond</a></li>
                            <li><a href='RingController'>Ring</a></li>
                            <li><a href='CollectionController'>Collection</a></li>
                        </ul>
                    </div>                  
                </li>
                <li class="navbar__link"><a href='VoucherController'>Voucher</a></li>
                <img src="./images/Screenshot (656).png">
                <li class="navbar__link">
                    <a href="#">Dashboard</a>
                    <div class="sub-menu-1">
                        <ul>
                            <li><a href='AdminController'>View Admins</a></li>   
                            <li><a href='DeliveryStaffController'>View Delivery Staffs</a></li>
                            <li><a href='SalesController'>View Sale Staffs</a></li>
                            <li><a href='CertificateController'>View Certificate</a></li>
                            
                        </ul>
                    </div>
                </li>
                <li class="navbar__link">
                    <a href="#">Account</a>
                    <div class="sub-menu-1">
                        <ul>
                            <li><a href='adminlogin?action=logout'>Logout</a></li>          
                        </ul>
                    </div>
                </li>               
            </ul>
        </div>
<!--        <jsp:include page="/menu.jsp" flush="true" />

        <h1>Manager Details </h1>         
        <p> Login username: ${sessionScope.adminsession.username}</p>-->

        <div class="container">
            <div class="padding">
                <div class="row d-flex justify-content-center">
                    <div class="col-xl-12 col-md-12">
                        <div class="card user-card-full">
                            <div class="row no-gutters">
                                <div class="col-md-3 bg-c-lite-green user-profile">
                                    <div class="card-block text-center text-white">
                                        <div class="m-b-25">
                                            <img src="https://img.icons8.com/bubbles/100/000000/user.png" class="img-radius" alt="User-Profile-Image">
                                        </div>
                                        <h6 class="f-w-600">${requestScope.manager.lastname} ${requestScope.manager.firstname}</h6>
                                        <p>Manager of FUJ</p>
                                        <p>${requestScope.manager.username}</p>
                                    </div>
                                </div>
                                <div class="col-md-9">
                                    <div class="card-block">
                                        <h2 class="m-b-20 p-b-5 b-b-default f-w-600">Personal Information</h2>
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <p class="m-b-10 f-w-600">First Name</p>
                                                <h6 class="text-muted f-w-400">${requestScope.manager.firstname}</h6>
                                            </div>
                                            <div class="col-sm-6">
                                                <p class="m-b-10 f-w-600">Last Name</p>
                                                <h6 class="text-muted f-w-400">${requestScope.manager.lastname}</h6>
                                            </div>
                                            <div class="col-sm-6">
                                                <p class="m-b-10 f-w-600">Email</p>
                                                <h6 class="text-muted f-w-400">${requestScope.manager.email}</h6>
                                            </div>
                                            <div class="col-sm-6">
                                                <p class="m-b-10 f-w-600">Phone Number</p>
                                                <h6 class="text-muted f-w-400">${requestScope.manager.phonenumber}</h6>
                                            </div>

                                        </div>
                                        <div class="row" style="margin-top: 20px; justify-content: center">
                                            <div class="col-sm-4">
                                                <form action="ManagerController" style="padding-top: 10px">
                                                    <input type="hidden" name="id" value="${requestScope.manager.userid}">
                                                    <input type="hidden" name="action" value="edit">
                                                    <button type="submit" class="btn btn-custom">Edit</button>
                                                </form>
                                            </div>
                                            <div class="col-sm-4">
                                                <form action="ManagerController" style="padding-top: 10px">
                                                    <button type="submit" class="btn btn-custom">Return</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--        <table>
        
                    <tr><td>User Name</td><td>${requestScope.manager.username}</td></tr>
                    <tr><td>Password</td><td>${requestScope.manager.password}</td></tr>
                    <tr><td>First Name</td><td></td></tr>
                    <tr><td>Last Name</td><td></td></tr>
                    <tr><td>Phone Number</td><td></td></tr>
                    <tr><td>Email</td><td></td></tr>
                    <tr><td>Address</td><td>${requestScope.manager.address}</td></tr>
                    <tr><td>Point</td><td>${requestScope.manager.point}</td></tr>
                </table>
        
                <form action="ManagerController" style="padding-top: 10px">
                    <input type=hidden name="action" value="list">
                    <input type=submit value="Return" ></form>
        
                <form action="ManagerController" style="padding-top: 10px">
                    <input type=hidden name="id" value="${requestScope.manager.userid}">
                    <input type=hidden name="action" value="edit">
                    <input type=submit value="Edit" ></form>-->
    </body>
</html>
