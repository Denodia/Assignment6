package com.example.assignment6.models;

public class Post {

        private int userId;

        private int id;

        private String title;

        private String body;

        public void setUserId(int userId){
            this.userId = userId;
        }
        public int getUserId(){
            return this.userId;
        }
        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setBody(String body){
            this.body = body;
        }
        public String getBody(){
            return this.body;
        }
    }

