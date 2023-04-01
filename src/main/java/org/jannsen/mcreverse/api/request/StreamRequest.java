package org.jannsen.mcreverse.api.request;

import org.jannsen.mcreverse.annotation.Auth;

@Auth(type = Auth.Type.Non)
public abstract class StreamRequest extends Request {
}
