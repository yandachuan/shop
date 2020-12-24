package com.neuedu.service;

import com.neuedu.entity.Comment;
import com.neuedu.vo.AgainCommentVO;
import com.neuedu.vo.CommentVO;

import java.util.List;

public interface CommentService {

    List<CommentVO> findCommentVOList(Long goodsid);

    boolean addComment(Comment comment);

    boolean existComment(Long userid,Long goodsid,Long lastid);

    boolean deleteComment(Long commentid);

    List<AgainCommentVO> findAgainList(Long goodsid,Long lastid);

}
