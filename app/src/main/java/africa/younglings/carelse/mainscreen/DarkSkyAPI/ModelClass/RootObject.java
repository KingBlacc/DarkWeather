package africa.younglings.carelse.mainscreen.DarkSkyAPI.ModelClass;

public class RootObject
{
    private Currently currently;

    private String offset;

    private String timezone;

    private String latitude;

    private Daily daily;

    private Flags flags;

    private Hourly hourly;

    private String longitude;

    public Currently getCurrently ()
    {
        return currently;
    }

    public void setCurrently (Currently currently)
    {
        this.currently = currently;
    }

    public String getOffset ()
    {
        return offset;
    }

    public void setOffset (String offset)
    {
        this.offset = offset;
    }

    public String getTimezone ()
    {
        return timezone;
    }

    public void setTimezone (String timezone)
    {
        this.timezone = timezone;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public Daily getDaily ()
    {
        return daily;
    }

    public void setDaily (Daily daily)
    {
        this.daily = daily;
    }

    public Flags getFlags ()
    {
        return flags;
    }

    public void setFlags (Flags flags)
    {
        this.flags = flags;
    }

    public Hourly getHourly ()
    {
        return hourly;
    }

    public void setHourly (Hourly hourly)
    {
        this.hourly = hourly;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [currently = "+currently+", offset = "+offset+", timezone = "+timezone+", latitude = "+latitude+", daily = "+daily+", flags = "+flags+", hourly = "+hourly+", longitude = "+longitude+"]";
    }
}