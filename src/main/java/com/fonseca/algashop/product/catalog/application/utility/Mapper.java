package com.fonseca.algashop.product.catalog.application.utility;

public interface Mapper {
    <T> T convert(Object o, Class<T> destinationClass);
}