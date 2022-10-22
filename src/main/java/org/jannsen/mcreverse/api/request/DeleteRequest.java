package org.jannsen.mcreverse.api.request;

import org.jannsen.mcreverse.annotation.SensorRequired;

@SensorRequired
public class DeleteRequest extends Request {

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/identity";
    }
}
