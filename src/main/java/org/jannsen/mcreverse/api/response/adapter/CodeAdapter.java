package org.jannsen.mcreverse.api.response.adapter;

import org.jannsen.mcreverse.api.entity.redeem.Code;

import java.time.ZoneId;

import static org.jannsen.mcreverse.utils.Utils.timeToUnix;
import static org.jannsen.mcreverse.utils.ObjectModification.setField;

public class CodeAdapter extends AbstractAdapterFactory {

    public CodeAdapter() {
        super(Code.class);
    }

    @Override
    public void modifyPojo(Object pojo) {
        Code code = (Code) pojo;
        setField(code, "expirationTimeUnix", timeToUnix(code.getExpirationTimeUTC(), ZoneId.of("UTC")));
    }
}
