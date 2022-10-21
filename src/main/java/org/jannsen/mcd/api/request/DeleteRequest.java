package org.jannsen.mcd.api.request;

import org.jannsen.mcd.annotation.SensorRequired;

@SensorRequired
public class DeleteRequest extends Request {

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/identity";
    }
}
