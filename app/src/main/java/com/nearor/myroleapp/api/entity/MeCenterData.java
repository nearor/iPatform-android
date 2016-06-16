package com.nearor.myroleapp.api.entity;

import com.nearor.framwork.ValueObject;

import java.util.List;

/**
 * Created by Nearor on 16/6/14.
 */
public class MeCenterData extends ValueObject {

    /**
     * designerId : 47
     * img : http://*.jpg
     * name : 小雪
     * score : 2.3
     * motto : 做一个快乐的射鸡狮
     * encourage : 太棒了,你的评分高于平均续保持哦
     * mobilephone : 13918758899
     * location : 上海
     * scoreItem : [{"score1":3,"score2":2,"score3":2,"score4":2}]
     * caseList : [{"orderId":"Test_Designer_014","orderImg":"http://*.jpg","villageName":"锦秀华城 2房2厅2卫","houseArea":112,"bedroomNum":3,"hallNum":2,"bathroomNum":2}]
     * incomes : [{}]
     */
        private int designerId;
        private String img;
        private String name;
        private double score;
        private String motto;
        private String encourage;
        private String mobilephone;
        private String location;
        /**
         * score1 : 3
         * score2 : 2
         * score3 : 2
         * score4 : 2
         */

        private List<ScoreItemBean> scoreItem;
        /**
         * orderId : Test_Designer_014
         * orderImg : http://*.jpg
         * villageName : 锦秀华城 2房2厅2卫
         * houseArea : 112
         * bedroomNum : 3
         * hallNum : 2
         * bathroomNum : 2
         */

        private List<CaseListBean> caseList;

        public int getDesignerId() {
            return designerId;
        }

        public void setDesignerId(int designerId) {
            this.designerId = designerId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getMotto() {
            return motto;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }

        public String getEncourage() {
            return encourage;
        }

        public void setEncourage(String encourage) {
            this.encourage = encourage;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public List<ScoreItemBean> getScoreItem() {
            return scoreItem;
        }

        public void setScoreItem(List<ScoreItemBean> scoreItem) {
            this.scoreItem = scoreItem;
        }

        public List<CaseListBean> getCaseList() {
            return caseList;
        }

        public void setCaseList(List<CaseListBean> caseList) {
            this.caseList = caseList;
        }

    }

