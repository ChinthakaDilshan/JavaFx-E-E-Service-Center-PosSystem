package dao.custom.impl;

import dao.custom.OrderDetailsDao;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.OrderDetailDto;
import dto.OrderDto;
import dao.custom.OrderDao;
import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private OrderDetailsDao orderDetailDao = new OrderDetailDaoImpl();

    @Override
    public boolean save(OrderDto dto) throws SQLException, ClassNotFoundException {

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Orders order = new Orders(
                dto.getOrderId(),
                dto.getDate()
        );
        order.setCustomer(session.find(Customer.class, dto.getCustId()));
        session.save(order);

        List<OrderDetailDto> list = dto.getList(); //dto type

        for (OrderDetailDto detailDto : list) {
            OrderDetail orderDetail = new OrderDetail(
                    new OrderDetailsKey(detailDto.getOrderId(), detailDto.getItemCode()),
                    session.find(Item.class, detailDto.getItemCode()),
                    order,
                    detailDto.getDescription(),
                    detailDto.getAdvancePrice(),
                    detailDto.getStatus(),
                    detailDto.getIssue()
            );
            session.save(orderDetail);
        }

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(OrderDto entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDto getLastOrder() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders ORDER BY orderId DESC LIMIT 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    null
            );
        }
        return null;
    }
}