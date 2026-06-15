package com.diora.core.android;

public interface AndroidBilling {

    public void buy(String skuId);

    public void consume(String skuId);

    public void consumeAll();
}
