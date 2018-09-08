package polskiedev.country_list_from_api;

import org.json.JSONObject;

import java.util.List;

public class Country {
    private String countryName;
    private String countryFlag;
    private String countryAbbrev;
    private String countryPopulation;
    private String countryCapital;
    private String countryRegion;
    private String countrySubRegion;

    private JSONObject countryCurrency;

    //Searchable by name and abbr
    //Capital, Region, Abbv, Calling Codes, Currency, Inglat, Languages, Borders
    //Display location on map view & display other details available

    private Address address;
    private List<PhoneNumber> phoneList;

    //JSONObject phone = person.getJSONObject("phone")
    //Setter
    public void setName(String countryName) {
        this.countryName = countryName;
    }

    public void setAbbrev(String countryAbbrev) {
        this.countryAbbrev = countryAbbrev;
    }

    public void setFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public void setPopulation(String countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    public void setCapital(String countryCapital) {
        this.countryCapital = countryCapital;
    }

    public void setRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    public void setSubRegion(String countrySubRegion) {
        this.countrySubRegion = countrySubRegion;
    }

    public void setCurrency(JSONObject countryCurrency) {
        this.countryCurrency = countryCurrency;
    }

    //Getter
    public String getName() {
        return this.countryName;
    }

    public String getAbbrev() {
        return this.countryAbbrev;
    }

    public String getFlag() {
        return this.countryFlag;
    }

    public String getPopulation() {
        return this.countryPopulation;
    }

    public String getCapital() {
        return this.countryCapital;
    }

    public String getRegion() {
        return this.countryRegion;
    }

    public String getSubRegion() {
        return this.countrySubRegion;
    }

    public JSONObject getCurrency() {
        return this.countryCurrency;
    }

    //Others
    public class PhoneNumber {
        private String type;
        private String number;
    }

    public class Address {
        private String address;
        private String city;
        private String state;
    }


}
