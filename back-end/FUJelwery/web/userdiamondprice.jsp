
<%@page import="com.khac.swp.fuj.diamondprice.DiamondPriceDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Diamond Price Page</title>
        <link rel="icon" type="image/x-icon" href="images/Screenshot__656_-removebg-preview.png">
        <link rel="stylesheet" href="css/user_diamondprice.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link
            href="https://fonts.googleapis.com/css2?family=Gantari:ital,wght@0,100..900;1,100..900&family=Inika:wght@400;700&family=Inria+Serif:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&family=Inter:wght@100..900&family=Raleway:ital,wght@0,100..900;1,100..900&family=Syne:wght@400..800&display=swap"
            rel="stylesheet">
        <script src="js/pagination.js"></script>
        <link rel="stylesheet" href="css/pagination.css">
        <link rel="stylesheet" href="./css/navigation_bar.css">
    </head>
    <body>
        <div class="header">
            <div class="header-top">
                <div class="top-info-left">
                    <ul>
                        <li>Timeless elegance and craftsmanship</li>
                        <li style="margin-left: 5px">at KHAC Diamond Ring Online Shop.</li>                         
                    </ul>
                </div>
                <a href="user_homepage.jsp"><img src="./images/Screenshot (656).png"></a>
                <div class="top-info-right">
                    <ul>
                        <li>KHAC Diamond Ring Shop - <strong>(+ 84) 898876512</strong></li>
                    </ul>
                </div>
            </div>
            <hr>
            <div class="header-bottom">
                <ul class="navbar">
                    <li class="navbar-link">
                        <a href="#" class="line">Jewelry</a>
                        <div class="sub-menu-1">
                            <ul>
                                <li><a href='./ProductController'>Ring</a></li>
                                <li><a href='./UserCategoryController'>Category</a></li>  
                                <li><a href='./UserCollectionController'>Collection</a></li>  


                            </ul>
                        </div>   
                    </li>
                    <li class="navbar-link"><a href='./UserDiamondPriceController' class="line">Diamond Price</a></li>
                    <li class="navbar-link"><a href='./UserVoucherController' class="line">Voucher</a></li>
                    <li class="navbar-link"><a href="#" class="line">Order Monitor</a>                       
                        <div class="sub-menu-1">
                            <ul>
                                <li><a href='OrderController?action=list&id=${sessionScope.usersession.userid}'>Cart</a></li>
                                <li><a href='BillController?action=list&id=${sessionScope.usersession.userid}'>Tracking Orders</a></li>
                                <li><a href='TransactionTracking?action=list&id=${sessionScope.usersession.userid}'>Transactions</a></li>
                            </ul>
                        </div>
                    </li>
                    <li class="navbar-link">
                        <a href="static_webpages/certificate_edu.jsp" class="line">Education</a>
                        <div class="sub-menu-1">
                            <ul>
                                <li><a href='./UserPostController'>Blog</a></li>
                                <li><a href='static_webpages/ringmeasuring.jsp'>Ring Measuring Guide</a></li>          
                                <li><a href='static_webpages/faqs.jsp'>Frequently Asking Questions</a></li>
                            </ul>
                        </div>
                    </li>
                    <li class="navbar-link"><a href="user_aboutus.jsp" class="line">About Us</a></li>                    
                    <li class="navbar-link">
                        <a href="user_accountdetails.jsp" class="line">Account</a>
                        <div class="sub-menu-1">
                            <ul>                               
                                <li><a href='HistoryController?action=list&id=${sessionScope.usersession.userid}'>History</a></li>
                                <li><a href='./userlogin?action=logout'>Logout</a></li> 
                            </ul>
                        </div>
                    </li>
                </ul>

            </div>
        </div>

        <div class="bannerContainer">
            <div class="bannerContent">
                <div class="bannerText">
                    <h1>Discover the Value of Timeless Elegance</h1>
                    <div class="description_hero">
                        <span>Explore our expertly curated diamond selection, where 
                            competitive prices meet unparalleled quality. Whether you're 
                            seeking the perfect engagement ring or a dazzling piece to add 
                            to your collection, find the best value for your investment with 
                            our transparent pricing and exquisite gems.</span>
                    </div>                   
                </div>
                <div class="bannerImage">
                    <img src="images/Men's_Wedding_Bands.jpeg" id="diamondHero" style="width: 432px; height: 432px">
                </div>
            </div>
        </div>

        <div class="introContainer">
            <div class="introContent">
                <h1>Latest Natural Diamond Price List</h1>
                <h2>Round Brilliant Cut – 03 Excellent – Fluorescence</h2>
                <p>Our diamond price list is meticulously calculated based on carat weight, 
                    color grade, and clarity. If you're looking to purchase diamond jewelry 
                    such as rings, necklaces, or earrings, stay informed with our latest 
                    price updates. Refer to the current price quotes below to make an informed 
                    decision on your investment.</p>
                <p>{Unit of Currency: VND}</p> 
            </div>
        </div>



        <div class="list-container">
            <div class="smaller-container">
                <div class="list">
                    <div class="introContainer" style="">
                        <div class="introContent1">
                            <h1>0.36 Carat Diamond</h1>                               
                        </div>
                    </div>
                    <table class="diamond-table">
                        <thead>
                            <tr>                               
                                <th>Diamond Size</th>                               
                                <th>Color</th>
                                <th>Clarity</th>                               
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody>

                            <%
                                List<DiamondPriceDTO> list = (List<DiamondPriceDTO>) request.getAttribute("dplist");
                                for (DiamondPriceDTO dp : list) {
                                    pageContext.setAttribute("dp", dp);
                            %>
                            <tr> 
                                <td>${dp.size}</td>                                   
                                <td>${dp.color}</td>  
                                <td>${dp.clarity}</td>   
                                <td>${dp.price}</td>  
                            </tr>
                            <%
                                }
                            %>    


                    </table>
                </div>
            </div>
        </div>  
        <div class="list-container">
            <div class="smaller-container">
                <div class="list">  
                    <div class="introContainer" style="">
                        <div class="introContent1">
                            <h1>0.7 Carat Diamond</h1>                               
                        </div>
                    </div>
                    <table class="diamond-table">
                        <thead>
                            <tr>
                                <th>Diamond Size</th>                                
                                <th>Color</th>
                                <th>Clarity</th>
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<DiamondPriceDTO> list1 = (List<DiamondPriceDTO>) request.getAttribute("dplist1");
                                for (DiamondPriceDTO dp : list1) {
                                    pageContext.setAttribute("dp", dp);
                            %>
                            <tr>
                                <td>${dp.size}</td>                                   
                                <td>${dp.color}</td>  
                                <td>${dp.clarity}</td>  
                                <td>${dp.price}</td>  
                            </tr>
                            <%
                                }
                            %>    
                        </tbody>
                    </table>
                    <p>Note: The diamond price chart may fluctuate over time. 
                        For the most accurate diamond price quote, 
                        please contact KHAC Diamond Ring Shop directly.</p>
                </div>
            </div>
        </div> 

        <div class="outtro">
            <div class="outtro-info">
                <p>Loose diamonds or natural diamonds with international 
                    inspection certificates today range in price , with particularly 
                    large diamonds costing up to billions of copper. 
                    According to market research experts, prices depend 
                    primarily on the 4C standards (cut, color, clarity, 
                    and carat weight), as well as other factors including 
                    symmetry, fluorescence, luminescence, cleanliness, certificates and many more factors.</p>
                <br>
                <p>At KHAC Diamond Ring Shop, every single diamond that we offer comes with certification documents 
                    from the most renowned institutions, not to mention the Gemological Institute of America (GIA). 
                </p>
                <div>
                    <img src="images/ATTIC_GIA_Report.webp">
                </div>
                <p>To explore these bespoke options further or to receive expert 
                    guidance on selecting the perfect diamond, we invite you to contact 
                    KHAC Diamond Ring Shop via our hotline at <strong>(+84) 898876512</strong>. Our team of experts 
                    is ready to provide explicit detail and give personalized advice in order to assist you.</p>
            </div>
        </div>                

        <div class="footer">
            <div class="footer-content">
                <div class="info">
                    <div class="info-img">
                        <img src="images/Screenshot (659).png" />
                    </div>

                    <div class="info-text">
                        <p>
                            Address: FPT University, District 9, HCMC
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

        <script src="js/pagination.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.js"
                integrity="sha512-8Z5++K1rB3U+USaLKG6oO8uWWBhdYsM3hmdirnOEWp8h2B1aOikj5zBzlXs8QOrvY9OxEnD2QDkbSKKpfqcIWw=="
        crossorigin="anonymous"></script>
        <script src="js/sidenav.js"></script>

    </body>
</html>
