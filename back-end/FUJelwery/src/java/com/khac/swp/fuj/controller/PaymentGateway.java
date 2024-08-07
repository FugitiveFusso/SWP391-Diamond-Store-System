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
@WebServlet(name = "PaymentGateway", urlPatterns = {"/PaymentGateway"})
public class PaymentGateway extends HttpServlet {

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
                codeGenerator = CodeGenerator.generateCodeST(10);
            } while (DAO.isCodeDuplicate(codeGenerator));
            if (keyword == null) {
                keyword = "";
            }
            if (keyword == null) {
                keyword = "";
            }
            String sortCol = request.getParameter("colSort");

            OrderDAO orderDAO = new OrderDAO();
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usersession") == null) {
                response.sendRedirect("userlogin.jsp");
                return;
            } else if (action.equals("purchasewithcredit")) {//lists
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }
                String paymentMethod = request.getParameter("purchaseMethod");
                if (id != null) {
                    UserDAO user = new UserDAO();
                    UserDTO userDTO = user.load_Normal(id);
//                    transaction.updateOrder(paymentMethod, purchasedDate, id);
                    request.setAttribute("customer", userDTO);

                    String totalPrice = orderDAO.totalAllProduct(id);
                    request.setAttribute("totalPrice", totalPrice);
                }

                request.getRequestDispatcher("/paymentgateway.jsp").forward(request, response);
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

                String purchaseMethod = request.getParameter("purchaseMethod");
                String totalPrice = orderDAO.totalAllProduct(userID);

                if (userID != null) {
                    try {
                        Integer transactionID = null;
                        if (rs.next()) {
                            transactionID = rs.getInt(1);
                            transactionID++;
                        }
                        orderDAO.updateScore(userID);
                        orderDAO.purchase(codeGenerator, purchaseMethod, userID, purchasedDate);
                        transaction.insert(transactionID, userID, totalPrice, purchasedDate);

                        request.getSession().setAttribute("success", "Purchase Successfully!!!");
                    } catch (Exception e) {
                        log("Error deleting order: " + e.getMessage());
                        request.getSession().setAttribute("errorMessage", "Error deleting order.");
                    }
                } else {
                    request.getSession().setAttribute("errorMessage", "Invalid order ID.");
                }

                response.sendRedirect(request.getContextPath() + "/user_homepage.jsp");
            } else if (action.equals("cancel")) {
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
                    request.getSession().setAttribute("success", "Purchase Cancelled!!!");
                }

                request.getRequestDispatcher("/cartlist.jsp").forward(request, response);
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
