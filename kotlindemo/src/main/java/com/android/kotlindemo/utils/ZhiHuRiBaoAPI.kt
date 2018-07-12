package com.android.kotlindemo.utils

import java.net.URL

class ZhiHuRiBaoAPI {
    companion object {
        val NEWS_BASE_URL = "https://news-at.zhihu.com/api/4/news/"
        /**
         * 獲取最新的新闻列表，也就是今天的日报
         */
        val NEWS_LATEST_URL = NEWS_BASE_URL+"latest"
        /**
         * 获取之前的新闻列表，参数为日期，格式是yyyyMMdd
         * 例如：今天是2018年7月12号
         * 如果要查询昨天的日报信息，需要加上20180712
         */
        val NEWS_BEFORE = NEWS_BASE_URL+"before/%s"

        /**
         * 获取新闻的额外信息，包括点赞数、长评、短评等
         *  long_comments : 长评论总数
            popularity : 点赞总数
            short_comments : 短评论总数
            comments : 评论总数
         */
        val NEWS_EXTRA_INFO = "https://news-at.zhihu.com/api/4/story-extra/%s"


        /**
         * 获取日报主题列表
         */
        val NEWS_THEMES_LIST = "https://news-at.zhihu.com/api/4/themes"

        /**
         * 根据主题ID，获取主题内容列表
         * 参数为主题id
         */

        val NEWS_LIST_BY_THEME = "https://news-at.zhihu.com/api/4/theme/%s"

        /**
         * 根据主题ID，获取之前的主题内容列表
         * 第一个参数为主题id
         * 第二个参数为获取的主题内容列表最后一个内容的id
         */
        val NEWS_LIST_BY_THEME_BEFORE = "https://news-at.zhihu.com/api/4/theme/%1s/before/%2s"

        /**
         * 获取长评的内容
         * 第一个参数为内容id
         */
        val NEWS_LONG_COMMENTS = "https://news-at.zhihu.com/api/4/story/%1s/long-comments"
        /**
         * 获取短评的内容
         * 第一个参数为内容id
         */
        val NEWS_SHORT_COMMENTS = "https://news-at.zhihu.com/api/4/story/%1s/short-comments"

        /**
         * 获取消息内容
         */
        val NEWS_BODY_URL = "https://news-at.zhihu.com/api/4/news/%s"

    }
}