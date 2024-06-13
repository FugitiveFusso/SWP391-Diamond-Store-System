
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link rel="stylesheet" href="css/navbaruser.css">
        <link rel="stylesheet" href="css/user_mainpage.css">
        <style>
            /* The popup (background) */
            .popup {
                display: none;
                position: fixed; 
                z-index: 1; 
                left: 0;
                top: 0;
                width: 100%; 
                height: 100%; 
                overflow: auto; 
                background-color: rgba(0, 0, 0, 0.4); 
            }

            /* Popup content */
            .popup-content {
                background-color: #fefefe;
                margin: 15% auto; 
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 300px;
                text-align: center;
                border-radius: 5px;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
            }

            /* Close button */
            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }

            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }

            h4 {
                color: green;
            }

        </style>
        <script>
            function closePopup() {
                document.getElementById("successPopup").style.display = "none";
            }
        </script>
    </head>
    <body>
        <%
            String success = (String) session.getAttribute("success");
            if (success != null) {
                session.removeAttribute("success");
        %>
        <div id="successPopup" class="popup">
            <div class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>
                <h4><%= success%></h4>
            </div>
        </div>
        <script>
            // Display the pop-up
            document.getElementById("successPopup").style.display = "block";
        </script>
        <% }%>
        <div class="menu">
            <ul class="navbar">
                <li class="navbar__link">
                    <a href="#">Jewelry</a>
                    <div class="sub-menu-1">
                        <ul>
                            <li><a href='./ProductController'>Ring</a></li>
                            <li><a href='./UserCollectionController'>Collection</a></li>
                            <li><a href='OrderController?action=list&id=${sessionScope.usersession.userid}'>Cart</a></li>
                        </ul>
                    </div>                  
                </li>
                <li class="navbar__link"><a href='./UserVoucherController'>Voucher</a></li>
                <li class="navbar__link">
                    <a href="static_webpages/certificate_edu.jsp">Education</a>
                    <div class="sub-menu-1">
                        <ul>
                            <li><a href='UserPostController'>Blog</a></li>
                            <li><a href='static_webpages/ringmeasuring.jsp'>Ring Measuring Guide</a></li>          
                            <li><a href='static_webpages/faqs.jsp'>Frequently Asking Questions</a></li>
                        </ul>
                    </div>
                </li>
                <a href="user_homepage.jsp"><img src="images/Screenshot (656).png"></a>
                <li class="navbar__link"><a href="user_aboutus.jsp">About Us</a></li>
                <li class="navbar__link"><a href="#">Order</a></li>
                <li class="navbar__link">
                    <a href="#">Account</a>
                    <div class="sub-menu-1">
                        <ul>
                            <li><a href='userlogin?action=logout'>Logout</a></li>          
                        </ul>
                    </div>
                </li>

            </ul>
        </div> 

        <div class="main-container">
            <div class="Main-hero">
                <div class="hero-details">
                    <h2 style="font-family: Inter; font-weight: bold;">UP TO 50% OFF</h2>
                    <h1 style="font-family: Inika;">To Mom, With Love</h1>
                    <h3>A mother’s love is deep and everlasting. <br>Celebrate it with something that will always shin like she does (and save up to 50%)</h3>
                    <div class="button">
                        <button id="button1" > <a href="#">Shop Sale Jewelry</a></button>
                        <button><a href="#">Shop Engagement Rings</a></button>
                    </div>
                </div>
                <img src="images/hero1.jpg" alt="" srcset="">
            </div>

            <div class="trending">
                <div class="title">
                    <h1>MOSTLY SEARCH</h1>
                </div>
                <div class="list">
                    <div class="swiper">
                        <a href="">
                            <img src="images/img1.jpeg" alt="">
                        </a>
                        <h2>Diamond</h2>
                    </div>
                    <div class="swiper">
                        <a href="">
                            <img src="images/img1.jpeg" alt="">
                        </a>
                        <h2>Diamond</h2>
                    </div>
                    <div class="swiper">
                        <a href="">
                            <img src="images/img1.jpeg" alt="">
                        </a>
                        <h2>Diamond</h2>
                    </div>
                    <div class="swiper">
                        <a href="">
                            <img src="images/img1.jpeg" alt="">
                        </a>
                        <h2>Diamond</h2>
                    </div>
                </div>

            </div>
        </div>

        <div class="guarantee-container">
            <div class="guarantee-details">
                <h1>BEST QUALITY GUARANTEE</h1>
                <p>Our products come dipped 5 times in 14K or 18K gold or rhodium plating to prevent tarnishing, while our .925 sterling silver necklaces and pendants provide style and quality you can trust.</p>
                <p>With pendants and chains using hand-set CZ stones that come placed along a micro pave setting, and earrings using real diamonds, our products include the highest quality of materials.</p>
            </div>
            <img src="images/image_5.png" alt="" width="auto">

        </div>

        <div class="footer">
            <div class="footer-content">
                <div class="info">
                    <div class="info-img">
                        <img src="images/Screenshot (659).png" />
                    </div>

                    <div class="info-text">
                        <p>
                            Address: FPT University, District 9, Ho Chi Minh City
                        </p>
                        <p>
                            Email: fuj.khac.diamondshopsystem@gmail.com
                        </p>
                        <p>
                            Phone: (+ 84) 898876512
                        </p>
                        <p>
                            © Copyright 2024
                        </p>
                    </div>
                </div>

                <div class="customer-service">
                    <div class="customer-service-title">
                        Customer service
                    </div>

                    <div class="customer-service-text">
                        <p><a href="static_webpages/ringmeasuring.jsp">Instructions for measuring rings</a></p>
                        <p><a href="static_webpages/consulation.jsp">Product consultation by month of birth</a></p>
                        <p><a href="static_webpages/faqs.jsp">Frequently asked questions</a></p>
                    </div>
                </div>

                <div class="policy">
                    <div class="policy-title">
                        Policy
                    </div>

                    <div class="policy-text">
                        <p><a href="static_webpages/warrantyPolicy.jsp">Warranty Policy</a></p>
                        <p><a href="static_webpages/deliveryPolicy.jsp">Delivery Policy</a></p>
                        <p><a href="static_webpages/returnPolicy.jsp">Return Policy</a></p>
                        <p><a href="static_webpages/privatePolicy.jsp">Privacy policy</a></p>
                    </div>
                </div>
            </div>  


        </div>
    </body>
</html>
