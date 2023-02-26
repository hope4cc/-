package com.paopao.model.modelView;




import com.paopao.model.pojo.Comment;
import com.paopao.util.MyHttpStatus;

import java.util.List;

public class CommentResponse extends BaseModelView {
    private List<Comment> comments;

    public CommentResponse(MyHttpStatus status) {
        super(status);
    }

    public CommentResponse(MyHttpStatus status, List<Comment> comments) {
        super(status);
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
