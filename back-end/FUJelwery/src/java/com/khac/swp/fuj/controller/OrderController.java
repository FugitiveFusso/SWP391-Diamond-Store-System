/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khac.swp.fuj.controller;

import com.khac.swp.fuj.order.OrderDAO;
import com.khac.swp.fuj.order.OrderDTO;
import com.khac.swp.fuj.order.Transactions;
import com.khac.swp.fuj.ring.RingDTO;
import com.khac.swp.fuj.users.UserDAO;
import com.khac.swp.fuj.users.UserDTO;
import com.khac.swp.fuj.utils.DBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dell
 */
@WebServlet(name = "OrderController", urlPatterns = {"/OrderController"})
public class OrderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            String keyword = request.getParameter("keyword");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.now();
            String purchasedDate = localDate.format(formatter);
            Transactions transaction = new Transactions();
            OrderDAO DAO = new OrderDAO();
            String codeGenerator;
            do {
                codeGenerator = CodeGenerator.generateCodeST(8);
            } while (DAO.isCodeDuplicate(codeGenerator));
            String codeGenerator1;
            do {
                codeGenerator1 = CodeGenerator.generateCodeDO(8);
            } while (DAO.isCodeDuplicate(codeGenerator1));
            if (keyword == null) {
                keyword = "";
            }
            String sortCol = request.getParameter("colSort");

            OrderDAO orderDAO = new OrderDAO();
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usersession") == null) {
                response.sendRedirect("userlogin.jsp");
                return;
            } else if (action == null || action.equals("list")) {//lists
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }
                if (id != null) {
                    OrderDAO dao = new OrderDAO();
                    List<OrderDTO> list = dao.list(id);
                    request.setAttribute("cartlist", list);

                    String totalPrice = orderDAO.totalAllProduct(id);
                    request.setAttribute("totalPrice", totalPrice);
                }

                request.getRequestDispatcher("/cartlist.jsp").forward(request, response);

            } else if (action.equals("details")) {//details

                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }

                OrderDTO order = null;
                if (id != null) {
                    order = orderDAO.load(id);
                }

                request.setAttribute("order", order);//object
                request.getRequestDispatcher("/cartdetails.jsp").forward(request, response);
            } else if (action.equals("update")) {//update
                Integer orderID = null;
                try {
                    orderID = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter ringSize has wrong format.");
                }

                Integer ringSize = null;
                try {
                    ringSize = Integer.parseInt(request.getParameter("ringSize"));
                } catch (NumberFormatException ex) {
                    log("Parameter ringSize has wrong format.");
                }

                OrderDTO order = null;
                if (orderID != null) {
                    order = orderDAO.load(orderID);
                }
                order.setOrderID(orderID);
                order.setRingSize(ringSize);

                orderDAO.update(order);
                request.setAttribute("order", order);
                RequestDispatcher rd = request.getRequestDispatcher("cartdetails.jsp");
                rd.forward(request, response);

            } else if (action.equals("purchaseatstore")) {//purchase
                Integer userID = null;
                try {
                    userID = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter UserID has wrong format.");
                }

                String purchaseMethod = request.getParameter("purchaseMethod");

                switch (purchaseMethod) {
                    case "Received at store": {
                        if (userID != null) {
                            try {
                                boolean redirect = false;
                                String redirectUrl = request.getContextPath() + "/user_homepage.jsp";

                                if (orderDAO.checkTotalPendingOrder(userID) != 0) {
                                    if (orderDAO.checkRingActiveInCart(userID) == null) {
                                        orderDAO.updateScore(userID);
                                        orderDAO.outOfStockRing(userID);
                                        orderDAO.purchase(codeGenerator, purchaseMethod, userID, purchasedDate);
                                        request.getSession().setAttribute("success", "Purchase Successfully!!!");
                                    } else {
                                        request.getSession().setAttribute("failed", "One of the rings has been purchased!!!");
                                        redirect = true;
                                    }
                                } else {
                                    request.getSession().setAttribute("failed", "There is no Ring in your Cart to purchase!!!");
                                    redirect = true;
                                }

                                if (redirect) {
                                    response.sendRedirect(redirectUrl);
                                    return; // Ensure no further processing
                                }

                            } catch (Exception e) {
                                log("Error deleting order: " + e.getMessage());
                                request.getSession().setAttribute("errorMessage", "Error deleting order.");
                            }
                        } else {
                            request.getSession().setAttribute("errorMessage", "Invalid order ID.");
                        }

                        response.sendRedirect(request.getContextPath() + "/user_homepage.jsp");
                        break;
                    }
                    case "Door-to-door delivery service": {
                        if (userID != null) {
                            try {
                                boolean redirect = false;
                                String redirectUrl = request.getContextPath() + "/user_homepage.jsp";

                                if (orderDAO.checkTotalPendingOrder(userID) != 0) {
                                    if (orderDAO.checkRingActiveInCart(userID) == null) {
                                        orderDAO.updateScore(userID);
                                        orderDAO.outOfStockRing(userID);
                                        orderDAO.purchase(codeGenerator1, purchaseMethod, userID, purchasedDate);
                                        request.getSession().setAttribute("success", "Purchase Successfully!!!");
                                    } else {
                                        request.getSession().setAttribute("failed", "One of the rings has been purchased!!!");
                                        redirect = true;
                                    }
                                } else {
                                    request.getSession().setAttribute("failed", "There is no Ring in your Cart to purchase!!!");
                                    redirect = true;
                                }

                                if (redirect) {
                                    response.sendRedirect(redirectUrl);
                                    return; // Ensure no further processing
                                }

                            } catch (Exception e) {
                                log("Error deleting order: " + e.getMessage());
                                request.getSession().setAttribute("errorMessage", "Error deleting order.");
                            }
                        } else {
                            request.getSession().setAttribute("errorMessage", "Invalid order ID.");
                        }
                        response.sendRedirect(request.getContextPath() + "/user_homepage.jsp");

                        break;
                    }
                }
            } else if (action.equals("purchasewithcredit")) {//lists
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }
                String paymentMethod = request.getParameter("purchaseMethod");
                switch (paymentMethod) {
                    case "Received at store": {
                        if (id != null) {
                            UserDAO user = new UserDAO();
                            boolean redirect = false;
                            String redirectUrl = request.getContextPath() + "/user_homepage.jsp";

                            try {
                                if (orderDAO.checkTotalPendingOrder(id) != 0) {
                                    if (orderDAO.checkRingActiveInCart(id) == null) {
                                        UserDTO userDTO = user.load_Normal(id);
                                        transaction.updateOrder(codeGenerator, paymentMethod, purchasedDate, id);
                                        request.setAttribute("customer", userDTO);
                                        String totalPrice = orderDAO.totalAllProduct(id);
                                        request.setAttribute("totalPrice", totalPrice);
                                        request.getRequestDispatcher("/paymentgateway.jsp").forward(request, response);
                                        return; // Ensure no further processing
                                    } else {
                                        request.getSession().setAttribute("failed", "One of the rings has been purchased!!!");
                                        redirect = true;
                                    }
                                } else {
                                    request.getSession().setAttribute("failed", "There is no Ring in your Cart to purchase!!!");
                                    redirect = true;
                                }

                                if (redirect) {
                                    response.sendRedirect(redirectUrl);
                                    return; // Ensure no further processing
                                }

                            } catch (Exception e) {
                                log("Error processing order: " + e.getMessage());
                                request.getSession().setAttribute("errorMessage", "Error processing order.");
                                response.sendRedirect(redirectUrl);
                            }
                        } else {
                            request.getSession().setAttribute("errorMessage", "Invalid order ID.");
                            response.sendRedirect(request.getContextPath() + "/user_homepage.jsp");
                        }

                        break;
                    }
                    case "Door-to-door delivery service": {
                        if (id != null) {
                            UserDAO user = new UserDAO();
                            boolean redirect = false;
                            String redirectUrl = request.getContextPath() + "/user_homepage.jsp";

                            try {
                                if (orderDAO.checkTotalPendingOrder(id) != 0) {
                                    if (orderDAO.checkRingActiveInCart(id) == null) {
                                        UserDTO userDTO = user.load_Normal(id);
                                        transaction.updateOrder(codeGenerator1, paymentMethod, purchasedDate, id);
                                        request.setAttribute("customer", userDTO);
                                        String totalPrice = orderDAO.totalAllProduct(id);
                                        request.setAttribute("totalPrice", totalPrice);
                                        request.getRequestDispatcher("/paymentgateway.jsp").forward(request, response);
                                        return; // Ensure no further processing after forward
                                    } else {
                                        request.getSession().setAttribute("failed", "One of the rings has been purchased!!!");
                                        redirect = true;
                                    }
                                } else {
                                    request.getSession().setAttribute("failed", "There is no Ring in your Cart to purchase!!!");
                                    redirect = true;
                                }

                                if (redirect) {
                                    response.sendRedirect(redirectUrl);
                                    return; // Ensure no further processing after redirect
                                }

                            } catch (Exception e) {
                                log("Error processing order: " + e.getMessage());
                                request.getSession().setAttribute("errorMessage", "Error processing order.");
                                response.sendRedirect(redirectUrl);
                            }
                        } else {
                            request.getSession().setAttribute("errorMessage", "Invalid order ID.");
                            response.sendRedirect(request.getContextPath() + "/user_homepage.jsp");
                        }

                        break;
                    }
                }
            } else if (action.equals("pay")) {
                Connection conn = null;
                conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT MAX(transactionID) FROM [Transactions]");
                ResultSet rs = ps.executeQuery();

                Integer userID = null;
                try {
                    userID = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter UserID has wrong format.");
                }
                String totalPrice = orderDAO.totalAllProduct(userID);

                if (userID != null) {
                    try {
                        Integer transactionID = null;
                        if (rs.next()) {
                            transactionID = rs.getInt(1);
                            transactionID++;
                        }
                        orderDAO.updateScore(userID);
                        transaction.purchaseOrder(userID);
                        transaction.insert(transactionID, userID, totalPrice, purchasedDate);
                        orderDAO.outOfStockRing(userID);

                        request.getSession().setAttribute("success", "Purchase Successfully!!!");
                    } catch (Exception e) {
                        log("Error deleting order: " + e.getMessage());
                        request.getSession().setAttribute("errorMessage", "Error deleting order.");
                    }
                } else {
                    request.getSession().setAttribute("errorMessage", "Invalid order ID.");
                }

                response.sendRedirect(request.getContextPath() + "/user_homepage.jsp");
            } else if (action.equals("applyVoucher")) {
                String code = request.getParameter("coupon");
                Integer userID = null;
                try {
                    userID = Integer.parseInt(request.getParameter("userid"));
                } catch (NumberFormatException ex) {
                    log("Parameter UserID has wrong format.");
                }
                int voucherID = orderDAO.searchVoucherID(code);
                if (voucherID != 0) {
                    try {
                        orderDAO.applyVoucher(voucherID, userID);
                        request.setAttribute("success", "Apply Voucher Successfully!!!");
                    } catch (Exception e) {
                        log("Error Apply Coupon order: " + e.getMessage());
                        request.setAttribute("errorMessage", "Error applying voucher.");
                    }
                } else {
                    request.setAttribute("errorMessage", "Invalid voucher code.");
                }

                if (userID != null) {
                    OrderDAO dao = new OrderDAO();
                    List<OrderDTO> list = dao.list(userID);
                    request.setAttribute("cartlist", list);

                    String totalPrice = orderDAO.totalAllProduct(userID);
                    request.setAttribute("totalPrice", totalPrice);
                }

                request.getRequestDispatcher("/cartlist.jsp").forward(request, response);

            } else if (action.equals("removeVoucher")) {
                Integer userID = null;
                try {
                    userID = Integer.parseInt(request.getParameter("userid"));
                } catch (NumberFormatException ex) {
                    log("Parameter UserID has wrong format.");
                }

                if (userID != null) {
                    try {
                        orderDAO.removeVoucher(userID);
                        request.setAttribute("success", "Remove Voucher Successfully!!!");
                    } catch (Exception e) {
                        log("Error Remove Coupon order: " + e.getMessage());
                        request.setAttribute("errorMessage", "Error removing voucher.");
                    }
                } else {
                    request.setAttribute("errorMessage", "Invalid order ID.");
                }

                if (userID != null) {
                    OrderDAO dao = new OrderDAO();
                    List<OrderDTO> list = dao.list(userID);
                    request.setAttribute("cartlist", list);

                    String totalPrice = orderDAO.totalAllProduct(userID);
                    request.setAttribute("totalPrice", totalPrice);
                }

                request.getRequestDispatcher("/cartlist.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }

                if (id != null) {
                    try {
                        orderDAO.delete(id);
                        request.getSession().setAttribute("success", "Delete Successfully!!!");
                    } catch (Exception e) {
                        log("Error deleting order: " + e.getMessage());
                        request.getSession().setAttribute("errorMessage", "Error deleting order.");
                    }
                } else {
                    request.getSession().setAttribute("errorMessage", "Invalid order ID.");
                }

                response.sendRedirect(request.getContextPath() + "/user_homepage.jsp");
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
