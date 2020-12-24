package com.neuedu.service.impl;

import com.neuedu.entity.Comment;
import com.neuedu.entity.User;
import com.neuedu.mapper.CommentMapper;
import com.neuedu.mapper.UserMapper;
import com.neuedu.service.CommentService;
import com.neuedu.util.StringUtil;
import com.neuedu.vo.AgainCommentVO;
import com.neuedu.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<CommentVO> findCommentVOList(Long goodsid) {
        //查询commentList
        List<Comment> commentList = commentMapper.findCommentList(goodsid);

        //创建commentVOList
        List<CommentVO> commentVOList = new ArrayList<>();

        //遍历commentList
        for (Comment comment : commentList) {
            //创建一个commentVO
            CommentVO commentVO = new CommentVO();

           /* 填充commentVO对象*/
                //填充相同字段
            BeanUtils.copyProperties(comment, commentVO);
                //填充用户昵称和用户头像
            User user = userMapper.selectByPrimaryKey(comment.getUserid());
            commentVO.setNickname(user.getNickname());
            commentVO.setUserheadportrait(user.getUserheadportrait());
                //填充发表时间
            commentVO.setCreatetime(StringUtil.convertDatetime(comment.getCreatetime()));


            //将commentVO填入commentVOList中
            commentVOList.add(commentVO);

        }

        return commentVOList;
    }

    @Override
    public boolean addComment(Comment comment) {
        return commentMapper.insert(comment)>0;
    }

    @Override
    public boolean existComment(Long userid, Long goodsid,Long lastid) {
        return commentMapper.findCommentCount(userid, goodsid,lastid)>0;
    }

    @Override
    public boolean deleteComment(Long commentid) {
        return commentMapper.deleteByPrimaryKey(commentid)>0 && commentMapper.deleteAgain(commentid)>0;
    }

    @Override
    public List<AgainCommentVO> findAgainList(Long goodsid,Long lastid) {
        List<Comment> againList = commentMapper.findAgainList(goodsid,lastid);
        List<AgainCommentVO> againCommentVOList = new ArrayList<>();
        for (Comment comment : againList) {
            AgainCommentVO againCommentVO = new AgainCommentVO();
            againCommentVO.setContent(comment.getContent());
            User user = userMapper.selectByPrimaryKey(comment.getUserid());
            againCommentVO.setNickname(user.getNickname());
            againCommentVOList.add(againCommentVO);
        }
        return againCommentVOList;
    }



}
