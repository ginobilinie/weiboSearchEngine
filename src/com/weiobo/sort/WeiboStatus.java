package com.weiobo.sort;
import java.net.*;
import java.io.*;

//��ʾһ��Χ��״̬��Ϣ�ĸ�������
/*
 * *
 *         "created_at": "Tue May 31 17:46:55 +0800 2011",
            "id": 11488058246,
            "text": "���ע��"��
            "source": "<a href=/"http:////weibo.com/" rel=/"nofollow/">����΢��</a>",
            "favorited": false,
            "truncated": false,
            "in_reply_to_status_id": "",
            "in_reply_to_user_id": "",
            "in_reply_to_screen_name": "",
            "geo": null,
            "mid": "5612814510546515491",
            "reposts_count": 8,
            "comments_count": 9,
            "annotations": [],
            " lucenescore ": 15

 */
public class WeiboStatus 
{
	long created_at;
	String id;
	String text;
	String source;
	String favorited;
	String truncated;
	String in_reply_to_status_id;
	String in_reply_to_user_id;
	String in_reply_to_screen_name;
	String geo;//������Ϣ
	String mid;
	int reposts_count;//ת����
	int comments_count;
	double lucenescore;
	User user;

	double score=0;//������������������Χ��������

}


