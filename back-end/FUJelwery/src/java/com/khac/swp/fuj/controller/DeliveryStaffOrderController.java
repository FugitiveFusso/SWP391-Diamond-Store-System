/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khac.swp.fuj.controller;

import com.khac.swp.fuj.order.OrderDAO;
import com.khac.swp.fuj.order.OrderDTO;
import com.khac.swp.fuj.ring.RingDTO;
import com.khac.swp.fuj.utils.DBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
@WebServlet(name = "DeliveryStaffOrderController", urlPatterns = {"/DeliveryStaffOrderController"})
public class DeliveryStaffOrderController extends HttpServlet {

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
            if (keyword == null) {
                keyword = "";
            }
            String sortCol = request.getParameter("colSort");

            OrderDAO orderDAO = new OrderDAO();
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("deliverystaffsession") == null) {
                response.sendRedirect("deliverystafflogin.jsp");
                return;
            } else if (action == null || action.equals("list")) {//lists


                OrderDAO dao = new OrderDAO();
                List<OrderDTO> list = dao.listForDelivery(keyword);                
                request.setAttribute("deliverystafforderlist", list);
                request.getRequestDispatcher("./deliverystafforderlist.jsp").forward(request, response);
            }else if (action.equals("shipping")) {
                Integer orderID = null;
                try {
                    orderID = Integer.parseInt(request.getParameter("orderID"));
                } catch (NumberFormatException ex) {
                    log("Parameter orderID has wrong format.");
                }
                
                if (orderID != null) {
                    try {
                        orderDAO.deliveringOrder(orderID);
                        request.getSession().setAttribute("success", "Received order Successfully!!!");
                    } catch (Exception e) {
                        log("Error processing order with orderID " + orderID + e.getMessage());
                        request.getSession().setAttribute("errorMessage", "Error receiving order.");
                    }
                } else {
                    if (orderID == null) {
                        request.getSession().setAttribute("errorMessage", "Invalid order ID.");
                    }
                }

                try {
                    List<OrderDTO> list = orderDAO.listForDelivery(keyword);
                    request.setAttribute("deliverystafforderlist", list);
                    List<OrderDTO> listHistory = orderDAO.deliveryHistory(keyword);
                    request.setAttribute("deliveryhistory", listHistory);
                    request.getRequestDispatcher("/deliverystafforderlist.jsp").forward(request, response);
                } catch (Exception e) {
                    log("Error forwarding to sales staff order list: " + e.getMessage());
                    request.getSession().setAttribute("errorMessage", "Error loading order list.");
                    response.sendRedirect("errorPage.jsp"); // Redirect to an error page if forwarding fails
                }
            } else if (action.equals("delivered")) {
                Integer orderID = null;
                try {
                    orderID = Integer.parseInt(request.getParameter("orderID"));
                } catch (NumberFormatException ex) {
                    log("Parameter orderID has wrong format.");
                }
                
                if (orderID != null) {
                    try {
                        orderDAO.deliveredOrder(orderID);
                        request.getSession().setAttribute("success", "Received order Successfully!!!");
                    } catch (Exception e) {
                        log("Error processing order with orderID " + orderID + e.getMessage());
                        request.getSession().setAttribute("errorMessage", "Error receiving order.");
                    }
                } else {
                    if (orderID == null) {
                        request.getSession().setAttribute("errorMessage", "Invalid order ID.");
                    }
                }

                try {
                    List<OrderDTO> list = orderDAO.listForDelivery(keyword);
                    request.setAttribute("deliverystafforderlist", list);
                    List<OrderDTO> listHistory = orderDAO.deliveryHistory(keyword);
                    request.setAttribute("deliveryhistory", listHistory);
                    request.getRequestDispatcher("/deliverystafforderlist.jsp").forward(request, response);
                } catch (Exception e) {
                    log("Error forwarding to sales staff order list: " + e.getMessage());
                    request.getSession().setAttribute("errorMessage", "Error loading order list.");
                    response.sendRedirect("errorPage.jsp"); // Redirect to an error page if forwarding fails
                }
            }

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
