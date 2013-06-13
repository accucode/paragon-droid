package com.kodemore.test;

public class TyPersonObject
{
    //##################################################
    //# variables
    //##################################################
    private String uid;
    private String firstName;
    private String lastName;
    private String email;
    private String vocation;
    private String organization;
    private String phone;
    private String city;
    private String state;
    private String orgEmail;
    private String mailingList;
    private String currentProject;

    //##################################################
    //# accessing
    //##################################################

    public String getUid()
    {
        return uid;
    }

    public void setUid(String e)
    {
        uid = e;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String e)
    {
        firstName = e;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String e)
    {
        lastName = e;
    }

    public String getFullName()
    {
        //        String first = firstName.substring(0, 1).toUpperCase().concat(firstName.substring(1));
        //        String last = lastName.substring(0, 1).toUpperCase().concat(lastName.substring(1));
        if ( firstName == null && lastName == null )
            return "[none]";
        if ( firstName == null )
            return lastName;
        if ( lastName == null )
            return firstName;
        return firstName + " " + lastName;
    }

    public String getFullNameLastFirst()
    {
        //        String first = firstName.substring(0, 1).toUpperCase().concat(firstName.substring(1));
        //        String last = lastName.substring(0, 1).toUpperCase().concat(lastName.substring(1));
        if ( firstName == null && lastName == null )
            return "[none]";
        if ( firstName == null )
            return lastName;
        if ( lastName == null )
            return "[none], " + firstName;
        return lastName + ", " + firstName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String e)
    {
        email = e;
    }

    public String getVocation()
    {
        return vocation;
    }

    public void setVocation(String e)
    {
        vocation = e;
    }

    public String getOrganization()
    {
        return organization;
    }

    public void setOrganization(String e)
    {
        organization = e;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String e)
    {
        phone = e;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String e)
    {
        city = e;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String e)
    {
        state = e;
    }

    public String getOrgEmail()
    {
        return orgEmail;
    }

    public void setOrgEmail(String e)
    {
        orgEmail = e;
    }

    public String getMailingList()
    {
        return mailingList;
    }

    public void setMailingList(String e)
    {
        mailingList = e;
    }

    public String getCurrentProject()
    {
        return currentProject;
    }

    public void setCurrentProject(String e)
    {
        currentProject = e;
    }

}
