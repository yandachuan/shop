package com.neuedu.controller;


import com.neuedu.common.ServerResponse;
import com.neuedu.entity.Comment;
import com.neuedu.entity.Goods;
import com.neuedu.entity.User;
import com.neuedu.service.CommentService;
import com.neuedu.vo.AgainCommentVO;
import com.neuedu.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //展示商品评论列表
    @RequestMapping("/list/{goodsid}")
    public ServerResponse list(@PathVariable("goodsid") Long goodsid){
        System.out.println("goodid-->"+goodsid);
        List<CommentVO> commentVOList = commentService.findCommentVOList(goodsid);
        System.out.println("commentVOList-->"+commentVOList);
        if(commentVOList.size()>0){
            return ServerResponse.success("评论列表获取成功").data("commentVOList",commentVOList);
        }else{
            return ServerResponse.error("该商品暂无评论~");
        }


    }
    //添加一条商品评论
    @RequestMapping("/add")
    public ServerResponse add(Comment comment, HttpSession session) {

        User user1 = new User();
        user1.setUserid(1l);
        session.setAttribute("user", user1);

        //从session中取出当前登录的用户对象
        User user = (User)session.getAttribute("user");
        //填充用户编号userId

        /*//判断用户是否发表过评论
        commentService.existComment(user.getUserId(), comment.getGoodsid());*/
        //填充上级评论编号lastId
        if(commentService.existComment(user.getUserid(), comment.getGoodsid(),0L)){
            return ServerResponse.error("你已评论过该商品");
        }

        if (comment.getContent()==null){
            return ServerResponse.error("评论内容不能为空");
        }
            comment.setUserid(user.getUserid());
            //填充评论时间createTime
            comment.setCreatetime(new Date());
            comment.setLastid(0L);

            commentService.addComment(comment);
            //Long commentid = comment.getCommentid();.data("commentid", commentid)
            return ServerResponse.success("添加评论成功");

        }

    //删除当前登录用户的评论
    @RequestMapping("/delete")
    public ServerResponse delete(Comment comment ,HttpSession session){

        User user1 = new User();
        user1.setUserid(10L);
        session.setAttribute("user", user1);

        //从session中取出当前登录的用户对象
        User user = (User)session.getAttribute("user");

            if (comment.getUserid()!=user.getUserid()){
                return ServerResponse.error("非本人评论不能删除");
            }
            commentService.deleteComment(comment.getCommentid());
            return ServerResponse.success("删除评论成功");

    }

    //展示追评列表
    @RequestMapping("/again_list/{goodsid}")
    public ServerResponse againComment(@PathVariable("goodsid")Long goodsid,HttpSession session,Long commentid){

        List<AgainCommentVO> againList = commentService.findAgainList(goodsid,commentid);

        if (againList.size()>0){
            return ServerResponse.success("追评列表获取成功").data("againList",againList);
        }else{
            return ServerResponse.error("追评列表获取失败");
        }
    }


    //添加追评
    @RequestMapping("/again")
    public ServerResponse again(HttpSession session,Comment comment,Long lastCommentid){
        User user1 = new User();
        user1.setUserid(1l);
        session.setAttribute("user", user1);
        //从session中取出当前登录的用户对象
        User user = (User)session.getAttribute("user");
        comment.setUserid(user.getUserid());
        //填充评论时间createTime
        comment.setCreatetime(new Date());
        comment.setLastid(lastCommentid);
        if (commentService.addComment(comment)){
            return ServerResponse.success("添加追评成功");
        }else{
            return ServerResponse.error("添加追评失败");
        }

    }
}
