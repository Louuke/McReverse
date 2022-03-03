package icu.jnet.mcd.api.request;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.UrlEncodedContent;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EasterUploadRequest implements Request {

    public String userId, userName, token, deviceId;
    private final UploadData data;

    public EasterUploadRequest(String userId, String userName, String token, String deviceId, UploadData data) {
        this.userId = userId;
        this.userName = userName;
        this.token = token;
        this.deviceId = deviceId;
        this.data = data;
    }

    @Override
    public String getUrl() {
        return "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/easter/uploadAddress";
    }

    @Override
    public HttpContent getContent() {
        Map<String, Object> form = new HashMap<>();
        try {
            for(Field field : getClass().getFields()) {
                form.put(field.getName(), field.get(this));
            }
            for(Field field : data.getClass().getFields()) {
                form.put(field.getName(), field.get(data));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new UrlEncodedContent(form);
    }

    public static class UploadData {

        public String firstName, lastName, salutation, address, zipCode, city, emailAddress, telephoneNumber, prizeId;

        public UploadData setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UploadData setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UploadData setAddress(String address) {
            this.address = address;
            return this;
        }

        public UploadData setSalutation(String salutation) {
            this.salutation = salutation;
            return this;
        }

        public UploadData setZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public UploadData setCity(String city) {
            this.city = city;
            return this;
        }

        public UploadData setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public UploadData setTelephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
            return this;
        }

        public UploadData setPrizeId(String prizeId) {
            this.prizeId = prizeId;
            return this;
        }
    }
}
