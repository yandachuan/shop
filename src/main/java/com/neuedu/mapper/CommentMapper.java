package com.neuedu.mapper;

import com.neuedu.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Long commentid);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long commentid);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);


    List<Comment>findCommentList(Long goodsid);

    int findCommentCount(@Param("userid") Long userid, @Param("goodsid") Long goodsid,@Param("lastid")Long lastid);

    List<Comment> findAgainList(@Param("goodsid")Long goodsid,@Param("lastid")Long lastid);

    int deleteAgain(Long lastid);



    List<Long> findRecommendId();

    Long findFiveStar(Long goodsId);//找该商品五星的评论数量

    Long findTotal(Long goodsId);//找该商品的总评论数
}