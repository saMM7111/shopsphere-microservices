package com.samm7111.order.service;

import com.samm7111.order.model.Order;
import com.samm7111.order.model.request.OrderRequest;
import java.util.List;

public interface OrderService {
    Order create(OrderRequest request);
    Order byOrderNumber(String orderNumber);
    List<Order> byUser(String userId);
}