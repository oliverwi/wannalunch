package com.wannalunch.util

import java.util.Comparator;

import com.wannalunch.domain.Comment;

class CommentComparator implements Comparator<Comment> {

  int compare(Comment comment1, Comment comment2) {
    int result = comment1.date.compareTo(comment2.date)
    if (result == 0) {
      result = comment1.time.compareTo(comment2.time)
    }
    result 
  }
}
