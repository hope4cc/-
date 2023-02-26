package com.paopao.model.modelView;


import com.paopao.model.pojo.Follow;
import com.paopao.util.MyHttpStatus;

public class FollowResponse extends BaseModelView {

    private Follow follow;

    public FollowResponse(MyHttpStatus status) {
        super(status);
    }

    public FollowResponse(MyHttpStatus status, Follow follow) {
        super(status);
        this.follow = follow;
    }

    public Follow getFollow() {
        return follow;
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }
}
