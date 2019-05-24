
package com.android.testtasktotango.core_comp.data.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllAwarding {

    @SerializedName("is_enabled")
    @Expose
    private Boolean isEnabled;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("subreddit_id")
    @Expose
    private Object subredditId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("coin_reward")
    @Expose
    private Integer coinReward;
    @SerializedName("icon_width")
    @Expose
    private Integer iconWidth;
    @SerializedName("icon_url")
    @Expose
    private String iconUrl;
    @SerializedName("days_of_premium")
    @Expose
    private Integer daysOfPremium;
    @SerializedName("icon_height")
    @Expose
    private Integer iconHeight;
    @SerializedName("resized_icons")
    @Expose
    private List<ResizedIcon> resizedIcons = null;
    @SerializedName("days_of_drip_extension")
    @Expose
    private Integer daysOfDripExtension;
    @SerializedName("award_type")
    @Expose
    private String awardType;
    @SerializedName("coin_price")
    @Expose
    private Integer coinPrice;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(Object subredditId) {
        this.subredditId = subredditId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCoinReward() {
        return coinReward;
    }

    public void setCoinReward(Integer coinReward) {
        this.coinReward = coinReward;
    }

    public Integer getIconWidth() {
        return iconWidth;
    }

    public void setIconWidth(Integer iconWidth) {
        this.iconWidth = iconWidth;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getDaysOfPremium() {
        return daysOfPremium;
    }

    public void setDaysOfPremium(Integer daysOfPremium) {
        this.daysOfPremium = daysOfPremium;
    }

    public Integer getIconHeight() {
        return iconHeight;
    }

    public void setIconHeight(Integer iconHeight) {
        this.iconHeight = iconHeight;
    }

    public List<ResizedIcon> getResizedIcons() {
        return resizedIcons;
    }

    public void setResizedIcons(List<ResizedIcon> resizedIcons) {
        this.resizedIcons = resizedIcons;
    }

    public Integer getDaysOfDripExtension() {
        return daysOfDripExtension;
    }

    public void setDaysOfDripExtension(Integer daysOfDripExtension) {
        this.daysOfDripExtension = daysOfDripExtension;
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public Integer getCoinPrice() {
        return coinPrice;
    }

    public void setCoinPrice(Integer coinPrice) {
        this.coinPrice = coinPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
