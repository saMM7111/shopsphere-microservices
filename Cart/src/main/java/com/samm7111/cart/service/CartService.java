package com.samm7111.cart.service;

import com.samm7111.cart.model.Cart;
import com.samm7111.cart.model.request.AddCartItemRequest;

public interface CartService {
    Cart addItem(AddCartItemRequest request);
    Cart getCart(String userId);
    void clear(String userId);
}