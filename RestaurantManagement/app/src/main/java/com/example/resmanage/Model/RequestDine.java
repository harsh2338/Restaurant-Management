package com.example.resmanage.Model;

public class RequestDine {

    private String foodDetail, identity, time;

    public RequestDine() {
    }

    public RequestDine(String foodDetail, String identity, String time) {
        this.foodDetail = foodDetail;
        this.identity = identity;
        this.time = time;
    }

    public String getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(String foodDetail) {
        this.foodDetail = foodDetail;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



   /* public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    //private String Category;
    private String TableName;
    //private List<Order> foods;
    private String timings;
    private String foodDetails;







    public RequestDine(String Category,String TableName,String foodDetails, String timings) {
        this.Category=Category;
        this.TableName=TableName;
        this.timings = timings;
        this.foodDetails=foodDetails;
        //this.foods = foods;
    }


    public String getTimings() { return timings; }

    public void setTimings(String timings) { timings = timings; }

    public String getName() {
        return TableName;
    }

    public void setName(String name) {
        this.TableName = TableName;
    }

    */
}