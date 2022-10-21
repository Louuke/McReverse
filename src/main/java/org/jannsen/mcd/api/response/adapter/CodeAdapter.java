package org.jannsen.mcd.api.response.adapter;

import org.jannsen.mcd.api.entity.redeem.Code;

import java.lang.reflect.Field;
import java.time.ZoneId;

import static org.jannsen.mcd.utils.Utils.timeToUnix;

public class CodeAdapter extends AbstractAdapterFactory {

    public CodeAdapter() {
        super(Code.class);
    }

    @Override
    public void modifyPojo(Object pojo) {
        setUnixExpiration((Code) pojo);
    }

    private void setUnixExpiration(Code code) {
        try {
            Field timeUnix = Code.class.getDeclaredField("expirationTimeUnix");
            timeUnix.setAccessible(true);
            timeUnix.set(code, timeToUnix(code.getExpirationTimeUTC(), ZoneId.of("UTC")));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
