package bo.custom.impl;

import bo.custom.OrderDetailBo;
import dao.DaoFactory;
import dao.custom.OrderDetailsDao;
import dao.util.DaoType;
import dto.OrderDetailDto;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo{
    private OrderDetailsDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDERDETAIL);
    @Override
    public List<OrderDetailDto> allOrderDetails() throws SQLException, ClassNotFoundException {
        List<OrderDetail> entityList = orderDetailDao.getAll();
        List<OrderDetailDto> list = new ArrayList<>();

        for (OrderDetail orderDetail:entityList) {
            list.add(new OrderDetailDto(
                    orderDetail.getOrders().getOrderId(),
                    orderDetail.getItem().getItemCode(),
                    orderDetail.getDescription(),
                    orderDetail.getAdvancePrice(),
                    orderDetail.getStatus(),
                    orderDetail.getIssue()
            ));
        }
        return list;
    }
}
