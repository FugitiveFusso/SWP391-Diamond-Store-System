
<%@page import="com.khac.swp.fuj.ring.RingDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category Editing Page</title>
        <link rel="stylesheet" href="css/navbar_admin.css">
        <link rel="stylesheet" href="css/post_edit.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

        <script>
            function isValidImageUrlFormat(url) {
                const regex = /^https:\/\/.*\.(jpg|jpeg|png|gif|bmp|webp)$/i;
                return regex.test(url);
            }

            function validateForm() {
                const postImageInput = document.querySelector('input[name="postImage"]');
                const imageUrl = postImageInput.value;

                if (!isValidImageUrlFormat(imageUrl)) {
                    alert('Invalid image URL. It must start with "https://" and end with one of the following extensions: .jpg, .jpeg, .png, .gif, .bmp, .webp.');
                    return false;
                }
                return true;
            }
        </script>
        <script type="text/javascript">
            function validateInput() {
                var input = document.getElementsByName('id')[0];
                var value = input.value;
                if (value === "" || isNaN(value) || parseInt(value) <= 1) {
                    alert("Please enter an integer larger than one.");
                    return false;
                }
                return true;
            }
        </script>
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
                <div class="item"><a class="sub-btn"><i class="fas fa-ring"></i>View Product
                        <i class="fas fa-angle-right dropdown"></i>
                        <div class="sub-menu">
                            <a href="DiamondController" class="sub-item">Diamond List</a>
                            <a href="RingController" class="sub-item">Ring List</a>
                            <a href="CollectionController" class="sub-item">Collection List</a>

                        </div>
                    </a>
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


        <div class="title">
            <h1>Category Edit</h1>
            <p> Login user: ${sessionScope.salessession.username}</p>
        </div>
        <% String error1 = (String) request.getAttribute("error"); %>
        <% if (error1 != null) {%>
        <h4 style="color: red; text-align: center"> <%= error1%> </h4>
        <% }%>

        <% String success = (String) request.getAttribute("success"); %>
        <% if (success != null) {%>
        <h4 style="color: green; text-align: center"> <%= success%> </h4>
        <% }%>

        <div class="container content">
            <form action="./CategoryController" method="POST" onsubmit="return validateForm()" onsubmit="return validateInput()">
                <input type="number" name="id" value="${requestScope.category.categoryID}" hidden="" class="form-control">                           

                <div class="row content-info">
                    <div class="col-md-12">
                        <div class="info-input">                                                      
                            <h2>Category Name</h2>
                            <input name="categoryName" value="${requestScope.category.categoryName}" required="Please enter" class="form-control">
                        </div>                       
                    </div>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-4">
                        <div class="button text-center">
                            <input name="action" value="${requestScope.nextaction}" type="hidden">
                            <input type="submit" value="Save" class="btn btn-primary">
                        </div>
                    </div>

            </form>

            <div class="col-md-4">
                <div class="button text-center">
                    <form action="CategoryController">
                        <input type="hidden" name="action" value="list">
                        <input type="submit" class="btn btn-primary" value="Return">
                    </form>
                </div>                
            </div>
        </div>

    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.js"
            integrity="sha512-8Z5++K1rB3U+USaLKG6oO8uWWBhdYsM3hmdirnOEWp8h2B1aOikj5zBzlXs8QOrvY9OxEnD2QDkbSKKpfqcIWw=="
    crossorigin="anonymous"></script>
    <script src="js/sidenav.js"></script>                        

</body>
</html>
