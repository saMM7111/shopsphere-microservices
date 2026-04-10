package com.samm7111.cart.service;

import com.samm7111.cart.model.Cart;
import com.samm7111.cart.model.CartItem;
import com.samm7111.cart.model.request.AddCartItemRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class InMemoryCartService implements CartService {

    private final Map<String, List<CartItem>> carts = new ConcurrentHashMap<>();

    @Override
    public Cart addItem(AddCartItemRequest request) {
        List<CartItem> items = carts.computeIfAbsent(request.userId(), ignored -> new ArrayList<>());
        items.add(new CartItem(request.skuCode(), request.productName(), request.quantity(), request.price()));
        return buildCart(request.userId(), items);
    }

    @Override
    public Cart getCart(String userId) {
        List<CartItem> items = carts.getOrDefault(userId, List.of());
        return buildCart(userId, items);
    }

    @Override
    public void clear(String userId) {
        carts.remove(userId);
    }

    private Cart buildCart(String userId, List<CartItem> items) {
        BigDecimal total = items.stream()
            .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Cart(userId, List.copyOf(items), total);
    }
}