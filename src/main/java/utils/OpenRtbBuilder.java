package utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class OpenRtbBuilder {

    private final JSONArray mOpenRtbArray = new JSONArray();

    public enum Ethnicity {
        AFRICAN_AMERICAN,
        ASIAN,
        HISPANIC,
        WHITE,
        OTHER
    }

    public enum MaritalStatus {
        SINGLE,
        MARRIED,
        DIVORCED
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public static class ObjectBuilder {

        private final OpenRtbBuilder mOpenRtbBuilder;
        private final JSONObject mParentObject = new JSONObject();
        private final JSONObject mOpenRtbObject = new JSONObject();

        //private static final String KEY_AD_UNIT_ID = "auid";
       //private static final String KEY_AD_UNIT_GROUP_ID = "pgid";
        private static final String KEY_OPEN_RTB = "openRtb";

        private static final String KEY_AGE = "age";
        private static final String KEY_APPSTORE_URL = "url";
        private static final String KEY_CARRIER = "crr";
        private static final String KEY_MARKET_ARE = "dma";
        private static final String KEY_ETHNICITY = "eth";
        private static final String KEY_INCOME = "inc";
        private static final String KEY_IP_ADDRESS = "ip";
        private static final String KEY_MARITAL_STATUS = "mar";
        private static final String KEY_USER_ID = "xid";
        private static final String KEY_GENDER = "gen";
        private static final String KEY_BUYER_ID = "buyerid";
        private static final String KEY_CUSTOM_DATA = "customdata";
        private static final String KEY_KEYWORDS = "keywords";
        private static final String KEY_GEO = "geo";
        private static final String KEY_EXT = "ext";
        private static final String KEY_PUBLISHER_NAME = "publisherName";

        private ObjectBuilder(OpenRtbBuilder openRtbBuilder) {
            mOpenRtbBuilder = openRtbBuilder;
        }

        private ObjectBuilder(OpenRtbBuilder openRtbBuilder, JSONObject object) {
            this(openRtbBuilder);
            for (String key : object.keySet()) {
                if (!key.equals(KEY_OPEN_RTB)) {
                    mParentObject.put(key, object.get(key));
                } else {
                    JSONObject openRtbObject = object.getJSONObject(key);
                    for (String nestedKey : openRtbObject.keySet()) {
                        mOpenRtbObject.put(nestedKey, openRtbObject.get(nestedKey));
                    }
                }
            }
        }

        /*public ObjectBuilder setTargetAdUnitId(String adUnitId) {
            mParentObject.put(KEY_AD_UNIT_ID, adUnitId);
            return this;
        }*/

        /*public ObjectBuilder setTargetAdUnitGroupId(String adUnitGroupId) {
            mParentObject.put(KEY_AD_UNIT_GROUP_ID, adUnitGroupId);
            return this;
        }*/

        /*public ObjectBuilder setOpenRtbAdUnitId(String adUnitId) {
            mOpenRtbObject.put(KEY_AD_UNIT_ID, adUnitId);
            return this;
        }*/

        public ObjectBuilder setAge(int age) {
            mOpenRtbObject.put(KEY_AGE, age);
            return this;
        }

        public ObjectBuilder setAppstoreUrl(String url) {
            mOpenRtbObject.put(KEY_APPSTORE_URL, url);
            return this;
        }

        public ObjectBuilder setCarrier(String carrier) {
            mOpenRtbObject.put(KEY_CARRIER, carrier);
            return this;
        }

        public ObjectBuilder setMarketArea(String marketArea) {
            mOpenRtbObject.put(KEY_MARKET_ARE, marketArea);
            return this;
        }

        public ObjectBuilder setEthnicity(Ethnicity ethnicity) {
            mOpenRtbObject.put(KEY_ETHNICITY, ethnicity.name());
            return this;
        }

        public ObjectBuilder setIncome(int income) {
            mOpenRtbObject.put(KEY_INCOME, income);
            return this;
        }

        public ObjectBuilder setIpAddress(String ipAddress) {
            mOpenRtbObject.put(KEY_IP_ADDRESS, ipAddress);
            return this;
        }

        public ObjectBuilder setMaritalStatus(MaritalStatus maritalStatus) {
            mOpenRtbObject.put(KEY_MARITAL_STATUS, maritalStatus.name());
            return this;
        }

        public ObjectBuilder setGender(Gender gender) {
            mOpenRtbObject.put(KEY_GENDER, gender.name());
            return this;
        }

        public ObjectBuilder setBuyerId(String buyerId) {
            mOpenRtbObject.put(KEY_BUYER_ID, buyerId);
            return this;
        }

        public ObjectBuilder setCustomData(String customData) {
            mOpenRtbObject.put(KEY_CUSTOM_DATA, customData);
            return this;
        }

        public ObjectBuilder setKeywords(String keywords) {
            mOpenRtbObject.put(KEY_KEYWORDS, keywords);
            return this;
        }

        public ObjectBuilder setGeo(float lat, float lon) {
            JSONObject geoObject = new JSONObject();
            geoObject.put("lat", lat);
            geoObject.put("lon", lon);
            mOpenRtbObject.put(KEY_GEO, geoObject);
            return this;
        }

        public ObjectBuilder setExt(JSONObject extObject) {
            mOpenRtbObject.put(KEY_EXT, extObject);
            return this;
        }

        public ObjectBuilder setUserId(String userId) {
            mOpenRtbObject.put(KEY_USER_ID, userId);
            return this;
        }

        public ObjectBuilder setPublisherName(String publisherName) {
            mOpenRtbObject.put(KEY_PUBLISHER_NAME, publisherName);
            return this;
        }

        public OpenRtbBuilder finalizeObject() {
            mParentObject.put(KEY_OPEN_RTB, mOpenRtbObject);
            mOpenRtbBuilder.mOpenRtbArray.put(mParentObject);
            return mOpenRtbBuilder;
        }
    }

    public ObjectBuilder createNewOpenRtbObject() {
        return new ObjectBuilder(this);
    }

    public ObjectBuilder copyAndEditPreviousWithNewId(String newAdUnitId, String newAdUnitGroupId) {
        ObjectBuilder objectBuilder;
        if (mOpenRtbArray.length() != 0) {
            JSONObject object = mOpenRtbArray.getJSONObject(mOpenRtbArray.length() - 1);
            objectBuilder = new ObjectBuilder(this, object);
        } else {
            objectBuilder = new ObjectBuilder(this);
        }
        //objectBuilder.setTargetAdUnitId(newAdUnitId);
        //objectBuilder.setTargetAdUnitGroupId(newAdUnitGroupId);
        return objectBuilder;
    }

    public String build() {
        return mOpenRtbArray.toString().replace("\"", "\\\"");
    }
}
