package com.paopao.model.modelView;


import com.paopao.util.MyHttpStatus;

public class LikeResponse extends BaseModelView {


    public LikeResponse(MyHttpStatus status) {
        super(status);
    }

    public LikeResponse(MyHttpStatus status, String msg) {
        super(status, msg);
    }
}
