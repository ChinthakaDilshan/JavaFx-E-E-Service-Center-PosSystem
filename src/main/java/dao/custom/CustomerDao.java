package dao.custom;

import dao.CrudDao;
import dto.CustomerDto;
import dto.ItemDto;
import entity.Customer;

public interface CustomerDao extends CrudDao<Customer> {
    CustomerDto searchCustomer(String customerCode);
}
