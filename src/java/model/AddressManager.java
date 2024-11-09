package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddressManager {
    private HashMap<String, List<Address>> userAddresses;

    public AddressManager() {
        userAddresses = new HashMap<>();
    }

    public void addAddress(String email, Address address) {
        List<Address> addresses = userAddresses.getOrDefault(email, new ArrayList<>());
        addresses.add(address);
        userAddresses.put(email, addresses);
    }

    public void deleteAddress(String email, int index) {
        List<Address> addresses = userAddresses.get(email);
        if (addresses != null && index >= 0 && index < addresses.size()) {
            addresses.remove(index);
        }
    }

    public List<Address> getAddresses(String email) {
        return userAddresses.get(email);
    }
}
