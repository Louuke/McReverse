package org.jannsen.mcreverse.api.request;

import org.jannsen.mcreverse.annotation.Auth;
import org.jannsen.mcreverse.annotation.SensorRequired;
import org.jannsen.mcreverse.api.entity.login.Credentials;
import org.jannsen.mcreverse.api.entity.profile.Audit;
import org.jannsen.mcreverse.api.entity.profile.Device;
import org.jannsen.mcreverse.api.entity.register.Address;
import org.jannsen.mcreverse.api.entity.register.Policies;
import org.jannsen.mcreverse.api.entity.register.RegisterDevice;
import org.jannsen.mcreverse.api.entity.register.RegisterOptions;

@SensorRequired
@Auth(type = Auth.Type.BasicBearer)
public class RegisterRequest extends Request {

    private Policies policies = new Policies();
    private Audit audit = new Audit();
    private RegisterDevice device;
    private Address address;
    private Credentials credentials;
    private final String emailAddress, firstName, lastName;
    private final boolean optInForMarketing = false;

    public RegisterRequest(String email, String password, RegisterOptions options) {
        this.address = new Address(options.getZipCode());
        this.credentials = new Credentials(email, password, Credentials.Type.EMAIL);
        this.device = new RegisterDevice(options.getDeviceId());
        this.firstName = options.getFirstName();
        this.lastName = options.getLastName();
        this.emailAddress = email;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/registration";
    }
}
