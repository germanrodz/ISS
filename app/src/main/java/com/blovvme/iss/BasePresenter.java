package com.blovvme.iss;

/**
 * Created by Blovvme on 1/9/18.
 */

public interface BasePresenter <V extends BaseView>{
    void attach(V view);
    void detach();
}
